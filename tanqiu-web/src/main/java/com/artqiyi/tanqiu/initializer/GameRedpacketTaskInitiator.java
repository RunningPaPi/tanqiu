package com.artqiyi.tanqiu.initializer;

import com.artqiyi.tanqiu.common.constant.BreakGameConstants;
import com.artqiyi.tanqiu.common.constant.GameConstants;
import com.artqiyi.tanqiu.common.constant.RedisFiledConstant;
import com.artqiyi.tanqiu.game.*;
import com.artqiyi.tanqiu.game.domain.GameBreakRecords;
import com.artqiyi.tanqiu.game.domain.GameModel;
import com.artqiyi.tanqiu.game.domain.GameRedpacketRecords;
import com.artqiyi.tanqiu.game.domain.GameRedpacketRecordsExample;
import com.artqiyi.tanqiu.redis.RedisClient;
import com.ctc.wstx.sw.EncodingXmlWriter;
import org.apache.commons.collections.map.HashedMap;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GameRedpacketTaskInitiator implements ApplicationListener<ContextRefreshedEvent> {
    private static Logger log = LoggerFactory.getLogger(GameRedpacketTaskInitiator.class);

    @Autowired
    private IGameJobService gameJobService;
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private IGameConfigService gameConfigService;
    @Autowired
    private IGameRedpacketRecordsService redpacketRecordsService;
    @Autowired
    private IGameRedpackService gameRedpackService;
    @Autowired
    private IGameModelService gameModelService;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            try {
                //执行超时结算任务或重新设置定时结算任务
                GameRedpacketRecords gameRedpacketRecords = redisClient.hGet(RedisFiledConstant.REDPACKET_GAME_RECORD, GameConstants.TANQIU_GAME_MODEL_KEY_REDPACKET,GameRedpacketRecords.class);
                if (gameRedpacketRecords == null) {
                    log.warn("【红包赛启动任务】redis没有找到任何房间");
                    //创建新房间
                    GameModel game = gameModelService.getByGameModelKey(GameConstants.TANQIU_GAME_MODEL_KEY_REDPACKET);
                    if (game == null) {
                        log.error("【红包赛启动任务】 表game_model未配置红包赛");
                        return;
                    }
                    log.info("【红包赛启动任务】开始创建新房间");
                    gameRedpackService.createRedpacketRecord(game.getId(),game.getGameModelKey());
                    log.info("【红包赛启动任务】创建新房间完毕");
                    return ;
                }
                String gameNo = gameRedpacketRecords.getGameNo();
                Date endTime = gameRedpacketRecords.getEndTime();
                Date now = new Date();
                GameRedpacketRecordsExample example = new GameRedpacketRecordsExample();
                example.or().andGameNoEqualTo(gameNo);
                List<GameRedpacketRecords> records = redpacketRecordsService.selectByExample(example);
                if (records.size()==0) {
                    throw new RuntimeException("【红包赛结算异常】，数据库没有找到房间");
                }
                GameRedpacketRecords redpacketRecords = records.get(0);
                Short gameStatus = redpacketRecords.getGameStatus();

                if (gameStatus!=2) {
                    if (now.compareTo(endTime) >= 0) {
                        //超时没结算：服务器停机过久
                        gameRedpackService.redpacketGameEnd(new HashMap());
                    }else{
                        log.info("【服务器重启，检查红包赛还没到结算时间】");
                        //还没到结算时间
                        //设置结算任务
                        gameJobService.createRepacketEndJob(redpacketRecords);
                        //设置作弊人数增加任务
                        //当前有多少作弊用户 剩余多少，根据时间再次设置
                        redpacketRecordsService.addCheatedPlayersByTaskReboot(redpacketRecords);
                    }
                }
            } catch (Exception e) {
                log.error("【定时任务】 红包游戏结算定时任务启动出错。Exception:{}", e.toString());
            }
        }
    }

}
