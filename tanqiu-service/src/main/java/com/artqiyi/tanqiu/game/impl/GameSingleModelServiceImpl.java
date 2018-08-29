package com.artqiyi.tanqiu.game.impl;
import com.artqiyi.tanqiu.game.IGameSinglePrizeService;
import com.artqiyi.tanqiu.game.domain.GameSinglePrize;
import com.artqiyi.tanqiu.game.mapper.GameSinglePrizeMapper;
import com.artqiyi.tanqiu.game.vo.*;
import com.artqiyi.tanqiu.payment.service.TransLogService;
import com.artqiyi.tanqiu.user.domain.UserInfo;
import com.artqiyi.tanqiu.user.service.IUserInfoService;
import com.google.common.collect.Lists;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/03
 * Modify On: 2018/07/03 18:31 by wufuchang
 */

import com.artqiyi.tanqiu.common.constant.GameConstants;
import com.artqiyi.tanqiu.common.constant.RedisFiledConstant;
import com.artqiyi.tanqiu.common.constant.SystemConstant;
import com.artqiyi.tanqiu.common.util.DateUtil;
import com.artqiyi.tanqiu.game.IGameConfigService;
import com.artqiyi.tanqiu.game.IGameRankRecordService;
import com.artqiyi.tanqiu.game.IGameSingleModelService;
import com.artqiyi.tanqiu.game.domain.GameConfig;
import com.artqiyi.tanqiu.game.domain.GameRankRecord;
import com.artqiyi.tanqiu.redis.RedisClient;
import com.artqiyi.tanqiu.redis.RedisService;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 单人模式业务实现类
 */
@Service
public class GameSingleModelServiceImpl implements IGameSingleModelService {
    public static final Logger logger = LoggerFactory.getLogger(GameSingleModelServiceImpl.class);

    @Autowired
    RedisService redisService;
    @Autowired
    RedisClient redisClient;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    IGameConfigService gameConfigService;
    @Autowired
    IGameRankRecordService gameRankRecordService;
    @Autowired
    IGameSinglePrizeService gameSinglePrizeService;
    @Autowired
    IUserInfoService userInfoService;
    @Autowired
    TransLogService transLogService;

    /**
     * 游戏开始
     * @param userId
     * @return
     */
    @Override
    public GameBeginVo gameBegin(Long userId) {
        //从redis中获取该用户的弹球数，存放的只是在当天的好友助力的增加的弹球数，不包含初始配置的球个数
        Integer ballNumAward = (Integer) redisService.getHashObject(RedisFiledConstant.TANQIU_GAME_MODEL_SINGLE_BALLNUM_AWARD, userId.toString());
        //查询单人模式配置的默认初始弹球数
        Integer ballNumDefault = 0;
        List<GameConfig> gameConfigFilterForBallNum = gameConfigService.getConfigsByModelKeyAndCode(GameConstants.TANQIU_GAME_MODEL_KEY_SINGLE,
                                                                                GameConstants.TANQIU_GAME_CONFIG_CODE_BALLNUM_DEFAULT);
        if (gameConfigFilterForBallNum.size()==0) {
            logger.warn("后台没有配置单机模式下" +
                    "用户默认可用弹球数");
        }else{
            ballNumDefault = Integer.parseInt(gameConfigFilterForBallNum.get(0).getTypeValue());
        }
        GameBeginVo beginVo = new GameBeginVo();
        //返回的为两者总和
        beginVo.setBallNum((ballNumAward == null ? 0 : ballNumAward) + ballNumDefault);
        return beginVo;
    }

    /**
     * 好友助力
     * @param userId
     */
    @Override
    public AssistanceResulltVo assistanceByFriend(Long userId) {
        //查询单人模式配置的好友助力奖励的弹球数
        Integer ballNumAwardEach = 0;
        List<GameConfig> gameConfigFilterForBallNum = gameConfigService.getConfigsByModelKeyAndCode(GameConstants.TANQIU_GAME_MODEL_KEY_SINGLE,
                GameConstants.TANQIU_GAME_CONFIG_CODE_BALLNUM_AWARD_FROM_FRIEND);
        if (gameConfigFilterForBallNum.size()==0) {
            logger.warn("后台没有配置单机模式下好友助力奖励的弹球数");
        }else{
            ballNumAwardEach = Integer.parseInt(gameConfigFilterForBallNum.get(0).getTypeValue());
        }
        //获取上限
        Integer ballNumLimit = 0;
        List<GameConfig> gameConfigFilterForBallNumLimit = gameConfigService.getConfigsByModelKeyAndCode(GameConstants.TANQIU_GAME_MODEL_KEY_SINGLE,
                GameConstants.TANQIU_GAME_CONFIG_CODE_BALLNUM_AWARD_LIMIT);
        if (gameConfigFilterForBallNumLimit.size()==0) {
            logger.warn("后台没有配置单机模式下弹球数上限");
        }else{
            ballNumLimit = Integer.parseInt(gameConfigFilterForBallNumLimit.get(0).getTypeValue());
        }
        //获取redis存放的单机模式下，该用户的累计奖励分数
            //先判断redis下是否有该key
        Map entries = redisTemplate.opsForHash().entries(RedisFiledConstant.TANQIU_GAME_MODEL_SINGLE_BALLNUM_AWARD);
        Integer ballNumAward = (Integer) redisService.getHashObject(RedisFiledConstant.TANQIU_GAME_MODEL_SINGLE_BALLNUM_AWARD, userId.toString());
        ballNumAward = (ballNumAward == null ? 0 : ballNumAward) + ballNumAwardEach;
        ballNumAward = ballNumAward > ballNumLimit ? ballNumLimit : ballNumAward;//不能大于上限
        //重新放到redis中
        redisService.setHashObject(RedisFiledConstant.TANQIU_GAME_MODEL_SINGLE_BALLNUM_AWARD, userId.toString(),ballNumAward,-1);
        if (entries==null||entries.size()==0) {
            redisTemplate.expireAt(RedisFiledConstant.TANQIU_GAME_MODEL_SINGLE_BALLNUM_AWARD, DateUtil.getCurrentEndTimeDaily());
        }
        //获取开局默认
        Integer ballNumDefault = 0;
        List<GameConfig> gameConfigForBallNumDefault = gameConfigService.getConfigsByModelKeyAndCode(GameConstants.TANQIU_GAME_MODEL_KEY_SINGLE,
                GameConstants.TANQIU_GAME_CONFIG_CODE_BALLNUM_DEFAULT);
        if (gameConfigForBallNumDefault.size()==0) {
            logger.warn("后台没有配置单机模式下" +
                    "用户默认可用弹球数");
        }else{
            ballNumDefault = Integer.parseInt(gameConfigForBallNumDefault.get(0).getTypeValue());
        }
        //封装数据
        Integer currentBallNum = ballNumDefault + ballNumAward;
        AssistanceResulltVo resulltVo = new AssistanceResulltVo();
        resulltVo.setAssistantOutCome(true);
        resulltVo.setCurrentBallNum(currentBallNum);
        logger.info("用户{}因好友助力，获得弹球数{}，本局总共有弹球数{}",userId,ballNumAwardEach,currentBallNum);
        return resulltVo;
    }

    /**
     * 游戏结算
     * @param userId
     * @param userNickName
     * @param gameModelKey
     * @param gameModelId
     * @param score
     * @return
     */
    @Override
    public GameEndVo gameEnd(Long userId, String userNickName, String gameModelKey, Long gameModelId, Integer score) {
        //封装数据初始化
        boolean isSelfWeekHighest = false;
        //添加游戏记录
        gameRankRecordService.addGameRecord(userId, userNickName, gameModelKey, gameModelId, score);
        //获取本人本周最高分数
        Integer selfWeekHighestScore = gameRankRecordService.getSelfWeekHighestScore(userId, gameModelId, score);
        if (selfWeekHighestScore == null) {
            //本周没有数据，加入redis中 score为double类型，精度只有16位 由于是周排行榜 采用后7位为距离周末的时间搓加入分数中
            isSelfWeekHighest = true;
            setToRedisZset(userId, score);
        }else{
            //本周有数据，如果score是超过了之前的最高分，更新数据，并将分数设置到redis中的sets数据结构
            if (selfWeekHighestScore<score) {
                isSelfWeekHighest = true;
                //更新至redis中
                setToRedisZset(userId, score);
            }
        }//本周有数据
        //根据分数，获取对应的奖励
        Integer prizeCoin = gameSinglePrizeService.getPrizeCoinByScore(score);
        //如果有趣币奖励，趣币入账，记录流水
        if (prizeCoin!=null && prizeCoin > 0) {
            UserInfo userInfo = userInfoService.selectByUserId(userId);
            userInfo.setCoin(userInfo.getCoin()+prizeCoin);
            userInfoService.saveOrUpdate(userInfo);
            transLogService.generateTransLogForGameSingleEnd(prizeCoin,userNickName,userInfo);
        }
        //获取本周好友排行榜
        //RankBoardVo rankBoardVo = gameRankRecordService.friendRankBoard(userId, null,null);
        //封装数据
        GameEndVo gameEndVo = new GameEndVo();
        //gameEndVo.setMyGameRankVo(rankBoardVo.getMyGameRankVo());
        gameEndVo.setScore(score);
        gameEndVo.setSelfWeekHighest(isSelfWeekHighest);
        gameEndVo.setPrizeCoin(prizeCoin);
        return gameEndVo;
    }


    /**
     * 将分数加入或更新至排行榜中
     * @param userId
     * @param score
     */
    private void setToRedisZset(Long userId, Integer score) {
        long timeStampGap = DateUtil.getNowThisWeekSunDay().getTime()/1000 - new Date().getTime()/1000;
        double scoreWithTimeStamp = Double.valueOf(score *10000000L+timeStampGap);
        //判断是否有东西
        Long size = redisTemplate.opsForZSet().size(RedisFiledConstant.TANQIU_GAME_MODEL_SINGLE_RANK_SCORE_WEEK);
        //System.out.println(size);
        redisTemplate.opsForZSet().add(RedisFiledConstant.TANQIU_GAME_MODEL_SINGLE_RANK_SCORE_WEEK, userId, scoreWithTimeStamp);
        //如果设置之前没有内容，那么是新建的key，设置过期时间 每周日
        if (size==null||size==0) {
            Boolean expireAt = redisTemplate.expireAt(RedisFiledConstant.TANQIU_GAME_MODEL_SINGLE_RANK_SCORE_WEEK, DateUtil.getNowThisWeekSunDay());
            //System.out.println(expireAt);
        }
    }

    public static void main(String[] args) {
        System.out.println(LocalDateTime.now().getDayOfWeek());
        System.out.println(DateUtil.maxDayOfMonth(DateTime.now(),"yyyy-MM-dd HH"));
        long b  = (DateUtil.getNowThisWeekSunDay().getTime()/1000 - new Date().getTime()/1000);
        System.out.println(b);
        /*Long c = 1234567*10000000L;
        System.out.println(c);
        Double a = Double.valueOf(DateUtil.getNowThisWeekSunDay().getTime() / 1000 - new Date().getTime() / 1000 + 123456789 * 10000000L+"");
        System.out.println(a);
        System.out.println(a.intValue());
        System.out.println(a.longValue());*/

    }


}
