package com.artqiyi.tanqiu.game.impl;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/03
 * Modify On: 2018/07/03 20:44 by wufuchang
 */

import com.artqiyi.tanqiu.common.constant.GameConstants;
import com.artqiyi.tanqiu.common.constant.RedisFiledConstant;
import com.artqiyi.tanqiu.common.util.DateUtil;
import com.artqiyi.tanqiu.game.IGameRPRankRecordService;
import com.artqiyi.tanqiu.game.IGameRedpacketPrizeConfigService;
import com.artqiyi.tanqiu.game.domain.GameRedpacketPrizeConfig;
import com.artqiyi.tanqiu.game.vo.RPGameRankVo;
import com.artqiyi.tanqiu.game.vo.RPRankBoardVo;
import com.artqiyi.tanqiu.user.domain.User;
import com.artqiyi.tanqiu.user.domain.UserInfo;
import com.artqiyi.tanqiu.user.service.IUserInfoService;
import com.artqiyi.tanqiu.user.service.IUserService;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 红包赛排行
 */
@Service
public class GameRPRankRecordServiceImpl implements IGameRPRankRecordService {
    private static Logger log = LoggerFactory.getLogger(GameRPRankRecordServiceImpl.class);
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IGameRedpacketPrizeConfigService prizeConfigService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserInfoService userInfoService;

    /**
     * 世界排行榜单
     * @return
     */
    @Override
    public RPRankBoardVo worldRankBoard(Long userId, Integer page, Integer pageSize) {
        //封装数据
        RPRankBoardVo rankBoardVo = new RPRankBoardVo();
        List<RPGameRankVo> list = new ArrayList<>();
        //过滤分页条件
        Set<ZSetOperations.TypedTuple<Object>> tuples;
        int startIndex = 0;
        if (page != null && pageSize != null) {
            startIndex = (page - 1) * pageSize;
            int toIndex = startIndex + pageSize -1;//包含该索引位置
            //最多显示前100条
            if (toIndex >= GameConstants.TANQIU_REDPACKET_MODEL_RANKBOARD_TOP_LIMIT) {
                toIndex = GameConstants.TANQIU_REDPACKET_MODEL_RANKBOARD_TOP_LIMIT -1;
            }
            if (startIndex > toIndex) {
                tuples = Collections.EMPTY_SET;
            }else{
                tuples = redisTemplate.opsForZSet().reverseRangeWithScores(RedisFiledConstant.TANQIU_GAME_MODEL_REDPACKET_RANK_SCORE, startIndex, toIndex);
            }
        }else{
            tuples = redisTemplate.opsForZSet().reverseRangeWithScores(RedisFiledConstant.TANQIU_GAME_MODEL_REDPACKET_RANK_SCORE, 0, GameConstants.TANQIU_SINGLE_MODEL_RANKBOARD_TOP_LIMIT -1);
        }

        List<GameRedpacketPrizeConfig> prizeList = prizeConfigService.getPirzeListSortedByRank();
        if (prizeList.size()==0) {
            log.warn("【红包赛结算异常】，没有配置奖励");
        }
        Iterator<ZSetOperations.TypedTuple<Object>> iterator = tuples.iterator();
        while (iterator.hasNext()) {
            ZSetOperations.TypedTuple<Object> typedTuple = iterator.next();
            Long userIdWorld = (Long) typedTuple.getValue();
            Double scoreWithTimeStamp = typedTuple.getScore();
            Integer score = Integer.valueOf(scoreWithTimeStamp.longValue() / 10000000 + "");
            System.out.println("value:" +userIdWorld + " score:" + score);

            RPGameRankVo gameRankVo = new RPGameRankVo();
            gameRankVo.setUserId(userIdWorld);
            User user = userService.selectById(userIdWorld);
            UserInfo userInfo = userInfoService.selectByUserId(userIdWorld);
            gameRankVo.setUserNickName(user.getNickName());
            gameRankVo.setUserHeadPicUrl(userInfo.getHeadPicUrl());
            gameRankVo.setScore(score);
            list.add(gameRankVo);
        }
        //设置世界排名
        for (int i = 0; i < list.size(); i++) {
            RPGameRankVo gameRankVo = list.get(i);
            int worldRank = startIndex + i + 1;
            gameRankVo.setWorldRank(worldRank);
            if (worldRank > prizeList.size()) {
                gameRankVo.setPrizeNum(0);
            }else{
                gameRankVo.setPrizeNum(prizeList.get(worldRank-1).getPrizeNum());
            }
        }
        //获取个人排行
        RPGameRankVo myRPGameRankVo = generateWeekWorldRank(userId,prizeList);
        rankBoardVo.setGameRankVoList(list);
        rankBoardVo.setMyGameRankVo(myRPGameRankVo);
        return rankBoardVo;
    }



    /**
     * 获取指定userId的世界排名信息
     * @param userId
     * @param prizeList
     */
    private RPGameRankVo generateWeekWorldRank(Long userId, List<GameRedpacketPrizeConfig> prizeList) {
        Long rank = redisTemplate.opsForZSet().reverseRank(RedisFiledConstant.TANQIU_GAME_MODEL_REDPACKET_RANK_SCORE, userId);
        if (rank != null) {
            Double scoreWithTimeStamp = redisTemplate.opsForZSet().score(RedisFiledConstant.TANQIU_GAME_MODEL_REDPACKET_RANK_SCORE, userId);
            Integer score = Integer.valueOf(scoreWithTimeStamp.longValue() / 10000000+ "") ;
            RPGameRankVo gameRankVo = new RPGameRankVo();
            gameRankVo.setUserId(userId);
            User user = userService.selectById(userId);
            UserInfo userInfo = userInfoService.selectByUserId(userId);
            gameRankVo.setUserNickName(user.getNickName());
            gameRankVo.setUserHeadPicUrl(userInfo.getHeadPicUrl());
            gameRankVo.setWorldRank(Integer.valueOf(rank+1+""));
            gameRankVo.setScore(score);
            if (rank+1>prizeList.size()) {
                gameRankVo.setPrizeNum(0);
            }else{
                gameRankVo.setPrizeNum(prizeList.get(Integer.valueOf(rank+"")).getPrizeNum());
            }
            return gameRankVo;
        }
        return null;
    }



    /**
     * 将分数加入或更新至排行榜中
     * @param userId
     * @param endTime
     * @param score
     */
    public void setToRedisZset(Long userId, Date endTime, Integer score, String redisField) {
        long timeStampGap = endTime.getTime()/1000 - new Date().getTime()/1000;
        double scoreWithTimeStamp = Double.valueOf(score *10000000L+timeStampGap);
        redisTemplate.opsForZSet().add(redisField, userId, scoreWithTimeStamp);
    }

    /**
     * 将分数加入或更新至排行榜中
     * @param userId
     * @param score
     */
    public void setToRedisZset2(Long userId,Date endTime,Integer score,String redisField) {
        long timeStampGap = endTime.getTime()/1000 - DateUtils.addDays(new Date(),1).getTime()/1000;
        System.err.println(timeStampGap);
        double scoreWithTimeStamp = Double.valueOf(score *10000000L+timeStampGap);
        System.err.println(scoreWithTimeStamp);
        redisTemplate.opsForZSet().add(redisField, userId, scoreWithTimeStamp);
    }

    /**
     * 获取本人本周最高分数
     * @param userId
     * @return
     */
    public Integer getSelfHighestScore(Long userId) {
        Double scoreWithTimeStamp = redisTemplate.opsForZSet().score(RedisFiledConstant.TANQIU_GAME_MODEL_REDPACKET_RANK_SCORE, userId);
        //解析出真实分数
        if (scoreWithTimeStamp!=null) {
            Integer scoreHighest = Integer.valueOf(scoreWithTimeStamp.longValue()/ 10000000 + "") ;
            return scoreHighest;
        }
        return null;
    }


    public static void main(String[] args) {
        long timeStampGap = DateUtil.getNowThisWeekSunDay().getTime()/1000 - DateUtils.addDays(new Date(),6).getTime()/1000;
        System.out.println(timeStampGap);
        double scoreWithTimeStamp = Double.valueOf(1000 *10000000L+timeStampGap);
        System.out.println(scoreWithTimeStamp);
    }

}
