package com.artqiyi.tanqiu.game;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/23
 * Modify On: 2018/07/23 17:52 by wufuchang
 */

import com.artqiyi.tanqiu.aspect.AuthPassport;
import com.artqiyi.tanqiu.common.constant.MsgConstant;
import com.artqiyi.tanqiu.common.constant.RedisFiledConstant;
import com.artqiyi.tanqiu.common.constant.ResponseCodeConstant;
import com.artqiyi.tanqiu.common.util.DateUtil;
import com.artqiyi.tanqiu.game.domain.GameRedpacketUserRecords;
import com.artqiyi.tanqiu.game.vo.*;
import com.artqiyi.tanqiu.redis.RedisClient;
import com.artqiyi.tanqiu.response.ApiResponse;
import com.artqiyi.tanqiu.response.DefaultResponse;
import com.artqiyi.tanqiu.user.domain.User;
import com.artqiyi.tanqiu.user.domain.UserInfo;
import com.artqiyi.tanqiu.user.service.IUserInfoService;
import com.artqiyi.tanqiu.user.service.IUserService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@RestController
@RequestMapping("/api/gameRedpack")
public class GameRedpacketController {
    private static Logger log = LoggerFactory.getLogger(GameRedpacketController.class);
    @Autowired
    private IGameRedpackService gameRedpackService;
    @Autowired
    private IGameRPRankRecordService gameRPRankRecordService;
    @Autowired
    private IGameRedpacketRewardRecordService gameRedpacketRewardRecordService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IGameRedpacketUserRecordsService gameRedpacketUserRecordsService;



    //主页，获取用户免费试玩次数与游戏配置接口
    @AuthPassport(checkSign = true)
    @GetMapping("/getRedpacketModelInfo")
    public ApiResponse getRedpacketModelInfo(Long userId) {
        ApiResponse response = new DefaultResponse();
        if (userId==null&&userId<=0) {
            response.setCode(ResponseCodeConstant.PARAM_FAIL);
            response.setMsg("Bad Request Parameter:userId can not be null,or,less than 0");
            return response;
        }
        RedpacketModelInfoVo resulltVo = null;
        try {
            resulltVo = gameRedpackService.getRedpacketModelInfo4User(userId);
            response.setCode(ResponseCodeConstant.SUCCESS);
            response.setMsg(MsgConstant.SUCCESS_SEARCH);
            response.setResult(resulltVo);
        } catch (Exception e) {
            log.error("异常"+e);
            response.setCode(ResponseCodeConstant.SERVICE_FAIL);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    //进入红包赛接口，获取游戏数据包配置
    @AuthPassport(checkSign = true)
    @PostMapping("/redpacketGameBegin")
    public ApiResponse redpacketGameBegin(Long userId,String nickName,String headUrl,Long gameModelId,String gameModelKey) {
        ApiResponse response = new DefaultResponse();
        if (userId==null&&userId<=0) {
            response.setCode(ResponseCodeConstant.PARAM_FAIL);
            response.setMsg("Bad Request Parameter:userId can not be null,or,less than 0");
            return response;
        }
        Map resultVo = null;
        try {
            resultVo = gameRedpackService.redpacketGameBegin(userId,nickName,headUrl,gameModelId,gameModelKey);
            response.setCode(ResponseCodeConstant.SUCCESS);
            if (MapUtils.getBoolean(resultVo, "isLackofCoin")) {
                response.setMsg(MsgConstant.SUCCESS_TANQIU_BEGIN_FAIL);
            }else{
                response.setMsg(MsgConstant.SUCCESS_TANQIU_BEGIN);
            }
            response.setResult(resultVo);
        } catch (Exception e) {
            log.error("异常"+e);
            response.setCode(ResponseCodeConstant.SERVICE_FAIL);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    //游戏结束接口
    @AuthPassport(checkSign = true)
    @PostMapping("/userEnd")
    public ApiResponse userEnd(Long userId,Integer score,String gameNo) {
        ApiResponse response = new DefaultResponse();
        if (userId==null&&userId<=0) {
            response.setCode(ResponseCodeConstant.PARAM_FAIL);
            response.setMsg("Bad Request Parameter:userId can not be null,or,less than 0");
            return response;
        }
        Map resulltVo = null;
        try {
            resulltVo = gameRedpackService.redpacketUserEnd(userId,score,gameNo);
            response.setCode(ResponseCodeConstant.SUCCESS);
            response.setMsg(MsgConstant.SUCCESS_TANQIU_END);
            response.setResult(resulltVo);
        } catch (Exception e) {
            log.error("异常"+e);
            response.setCode(ResponseCodeConstant.SERVICE_FAIL);
            response.setMsg(e.getMessage());
        }
        return response;
    }


    //全球排行榜接口
    @AuthPassport(checkSign = true)
    @GetMapping("/worldRankBoard")
    public ApiResponse worldRankBoard(@RequestParam(value = "userId",required = true)Long userId,
                                      @RequestParam(value = "page",required = false)Integer page,
                                      @RequestParam(value = "pageSize",required = false)Integer pageSize){
        ApiResponse response = new DefaultResponse();
        if (userId==null&&userId<=0) {
            response.setCode(ResponseCodeConstant.PARAM_FAIL);
            response.setMsg("Bad Request Parameter:userId can not be null,or,less than 0");
            return response;
        }

        RPRankBoardVo rankBoardVo = null;
        try {
            rankBoardVo = gameRPRankRecordService.worldRankBoard(userId,page,pageSize);
            response.setCode(ResponseCodeConstant.SUCCESS);
            response.setMsg(MsgConstant.SUCCESS_SEARCH);
            response.setResult(rankBoardVo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("异常"+e);
            response.setCode(ResponseCodeConstant.SERVICE_FAIL);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    //登录红包赛奖励发放
    @AuthPassport(checkSign = true)
    @GetMapping("/repacketRewardNotice")
    public ApiResponse repacketRewardNotice(@RequestParam(value = "userId",required = true)Long userId){
        ApiResponse response = new DefaultResponse();
        if (userId==null&&userId<=0) {
            response.setCode(ResponseCodeConstant.PARAM_FAIL);
            response.setMsg("Bad Request Parameter:userId can not be null,or,less than 0");
            return response;
        }
        try {
            Map noticeVo = gameRedpacketRewardRecordService.repacketRewardNotice(userId);
            response.setCode(ResponseCodeConstant.SUCCESS);
            response.setMsg(MsgConstant.SUCCESS_SEARCH);
            response.setResult(noticeVo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("异常"+e);
            response.setCode(ResponseCodeConstant.SERVICE_FAIL);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    //已阅登录红包赛奖励发放消息
    @AuthPassport(checkSign = true)
    @PostMapping("/repacketRewardNoticeRead")
    public ApiResponse repacketRewardNoticeRead(@RequestParam(value = "recordId",required = true)Long recordId){
        ApiResponse response = new DefaultResponse();
        if (recordId==null&&recordId<=0) {
            response.setCode(ResponseCodeConstant.PARAM_FAIL);
            response.setMsg("Bad Request Parameter:userId can not be null,or,less than 0");
            return response;
        }
        try {
            gameRedpacketRewardRecordService.repacketRewardNoticeRead(recordId);
            response.setCode(ResponseCodeConstant.SUCCESS);
            response.setMsg(MsgConstant.SUCCESS_SAVE);
            response.setResult(true);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("异常"+e);
            response.setResult(false);
            response.setCode(ResponseCodeConstant.SERVICE_FAIL);
            response.setMsg(e.getMessage());
        }
        return response;
    }

    /**
     * 创建机器人 测试用
     */
    //@GetMapping("/createRobots")
    public ApiResponse worldRankBoard(){
        ApiResponse response = new DefaultResponse();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            UserInfo userInfo = new UserInfo();
            user.setNickName("robot"+i);
            user.setIsRobot(true);
            user.setStatus((short) 1);
            userService.saveOrUpdate(user);
            userInfo.setUserId(user.getId());
            userInfo.setHeadPicUrl("http://robot"+i+".jpg");
            userInfo.setCoin(0);
            userInfo.setBalance(0L);
            userInfo.setPoint(0);
            userInfoService.saveOrUpdate(userInfo);
        }
        response.setCode(ResponseCodeConstant.SUCCESS);
        return response;
    }
    /**
     * 手动结算场次 测试
     */
    @PostMapping("/redpacketGameEnd")
    public ApiResponse redpacketGameEnd(){
        ApiResponse response = new DefaultResponse();
        gameRedpackService.redpacketGameEnd(new HashMap());
        response.setCode(ResponseCodeConstant.SUCCESS);
        return response;
    }
    /**
     * 移除reids 相关数据测试
     */
    @PostMapping("/removeRedisData")
    public ApiResponse removeRedisData(String gameNo,String gameModelKey){
        ApiResponse response = new DefaultResponse();
        //删除redis数据 排行榜
        redisTemplate.delete(RedisFiledConstant.TANQIU_GAME_MODEL_REDPACKET_RANK_SCORE);

        //删除redis数据 gameRedpacketUserRecords
        redisClient.del(RedisFiledConstant.REDPACKET_GAME_USER_RECORDS+"_"+gameNo);

        //删除redis数据 gameRedpacketRecords
        redisClient.hDel(RedisFiledConstant.REDPACKET_GAME_RECORD, gameModelKey);
        response.setCode(ResponseCodeConstant.SUCCESS);
        return response;
    }

    /**
     * 加入排行榜 测试
     *
     */
    //@PostMapping("/addRankBoard")
    public ApiResponse addRankBoard(String gameNo,String gameModelKey) throws Exception {
        ApiResponse response = new DefaultResponse();
        int id = 612;
        for (int i = 0; i < 10; i++) {
            GameRedpacketUserRecords userRecords = new GameRedpacketUserRecords();
            userRecords.setGameModelId((long) 3);
            userRecords.setGameModelKey("REDPACKET");
            userRecords.setGameNo("REDPACKET_201808061400_140000421");
            userRecords.setUserId((long) id);
            userRecords.setNickName("cheat"+id);
            userRecords.setHeadUrl("");
            userRecords.setScore(RandomUtils.nextInt(979,1080));
            userRecords.setIsCheated(false);
            userRecords.setIsRobot(true);
            userRecords.setNote("");
            userRecords.setCreateTime(new Date());
            gameRedpacketUserRecordsService.saveOrUpdate(userRecords);
            //redisClient.hSet(RedisFiledConstant.REDPACKET_GAME_USER_RECORDS+"_"+gameRedpacketRecords.getGameNo(), userId.toString(),userRecords);
            //直接加入排行榜
            Date endTime = DateUtil.parseDate("2018-08-06 17:00:00", "yyyy-MM-dd HH:mm:ss");
            gameRPRankRecordService.setToRedisZset((long) id,endTime, userRecords.getScore(), RedisFiledConstant.TANQIU_GAME_MODEL_REDPACKET_RANK_SCORE);
            id++;
        }
        int id2 = 632;
        for (int i = 0; i < 10; i++) {
            GameRedpacketUserRecords userRecords = new GameRedpacketUserRecords();
            userRecords.setGameModelId((long) 3);
            userRecords.setGameModelKey("REDPACKET");
            userRecords.setGameNo("REDPACKET_201808061400_140000421");
            userRecords.setUserId((long) id2);
            userRecords.setNickName("cheat"+id2);
            userRecords.setHeadUrl("");
            userRecords.setScore(RandomUtils.nextInt(979,1080));
            userRecords.setIsCheated(false);
            userRecords.setIsRobot(true);
            userRecords.setNote("");
            userRecords.setCreateTime(DateUtils.addDays(new Date(),6));
            gameRedpacketUserRecordsService.saveOrUpdate(userRecords);
            //redisClient.hSet(RedisFiledConstant.REDPACKET_GAME_USER_RECORDS+"_"+gameRedpacketRecords.getGameNo(), userId.toString(),userRecords);
            //直接加入排行榜
            Date endTime = DateUtil.parseDate("2018-08-06 17:00:00", "yyyy-MM-dd HH:mm:ss");
            gameRPRankRecordService.setToRedisZset2((long) id2, endTime,userRecords.getScore(), RedisFiledConstant.TANQIU_GAME_MODEL_REDPACKET_RANK_SCORE);
            id2++;
        }
        response.setCode(ResponseCodeConstant.SUCCESS);
        return response;
    }

}
