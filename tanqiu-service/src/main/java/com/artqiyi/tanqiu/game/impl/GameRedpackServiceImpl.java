package com.artqiyi.tanqiu.game.impl;
import java.lang.reflect.Type;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.artqiyi.tanqiu.common.constant.RedisFiledConstant;
import com.artqiyi.tanqiu.common.constant.SystemConstant;
import com.artqiyi.tanqiu.common.util.DateUtil;
import com.artqiyi.tanqiu.common.util.JSONUtil;
import com.artqiyi.tanqiu.game.*;
import com.artqiyi.tanqiu.game.domain.*;
import com.artqiyi.tanqiu.game.vo.*;
import com.artqiyi.tanqiu.payment.service.TransLogService;
import com.artqiyi.tanqiu.redis.RedisClient;
import com.artqiyi.tanqiu.redis.RedisLock;
import com.artqiyi.tanqiu.service.IQuartzService;
import com.artqiyi.tanqiu.user.domain.User;
import com.artqiyi.tanqiu.user.domain.UserInfo;
import com.artqiyi.tanqiu.user.service.IUserInfoService;
import com.artqiyi.tanqiu.user.service.IUserService;
import com.google.common.collect.Lists;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/23
 * Modify On: 2018/07/23 18:22 by wufuchang
 */

import com.artqiyi.tanqiu.common.constant.GameConstants;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class GameRedpackServiceImpl implements IGameRedpackService {
    private static Logger log = LoggerFactory.getLogger(GameRedpackServiceImpl.class);
    @Autowired
    private IGameConfigService gameConfigService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private TransLogService transLogService;
    @Autowired
    private IGameRedpacketRecordsService redpacketRecordsService;
    @Autowired
    private IGameRedpacketUserRecordsService redpacketUserRecordsService;
    @Autowired
    private IGameRedpacketPrizeConfigService redpacketPrizeConfigService;
    @Autowired
    private IGameDifficultyConfigService gameDifficultyConfigService;
    @Autowired
    private IGameModelService gameModelService;
    @Autowired
    private RedisLock redisLock;
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private IGameRPRankRecordService gameRPRankRecordService;
    @Autowired
    private IGameJobService gameJobService;
    @Autowired
    private IGameRedpacketRewardRecordService gameRedpacketRewardRecordService;


    @Override
    public RedpacketModelInfoVo getRedpacketModelInfo4User(Long userId) {
        //获取配置信息
        List<GameConfig> gameConfigs = gameConfigService.getConfigsByModelKeyAndCode(GameConstants.TANQIU_GAME_MODEL_KEY_REDPACKET,null);
        if (gameConfigs==null||gameConfigs.size()==0) {
            throw new RuntimeException("后台没有配置该模式的配置信息");
        }
        RedpacketModelInfoVo infoVo = new RedpacketModelInfoVo();

        List<GameConfig> ruleList = new ArrayList<>();
        List<String> timeList;
        Integer freePlayTimes = 0;
        for (GameConfig gameConfig : gameConfigs) {
            String typeKey = gameConfig.getTypeKey();
            String code = gameConfig.getCode();
            switch (typeKey) {
                case GameConstants.TANQIU_GAME_CONFIG_TYPE_KEY_REDPACKET_SYSTEM:
                    switch (code) {
                        case GameConstants.TANQIU_GAME_CONFIG_CODE_FREE_PLAY_TIMES:
                            freePlayTimes =Integer.valueOf(gameConfig.getTypeValue());
                            infoVo.setDefaultFreePlayTimes(freePlayTimes);
                            break;
                        case GameConstants.TANQIU_GAME_CONFIG_CODE_PLAY_COST:
                            infoVo.setCoinCost(Integer.valueOf(gameConfig.getTypeValue()));
                            break;
                        case GameConstants.TANQIU_GAME_CONFIG_LEVEL:
                            infoVo.setLevel(Integer.valueOf(gameConfig.getTypeValue()));
                            break;
                        case GameConstants.TANQIU_GAME_CONFIG_TIME:
                            String timesStr = gameConfig.getTypeValue();
                            String[] times = timesStr.split(",");
                            timeList = Arrays.asList(times);
                            infoVo.setCastTimes(timeList);
                            break;
                        default:break;
                    }
                    break;
                case GameConstants.TANQIU_GAME_CONFIG_TYPE_KEY_REDPACKET_RULE:
                    ruleList.add(gameConfig);
                    break;
                default:break;
            }
        }
        List<String> rules = ruleList.stream().sorted(Comparator.comparing(GameConfig::getSort)).map(gameConfig -> gameConfig.getTypeValue()).collect(Collectors.toList());
        infoVo.setGameRules(rules);

        //获取用户试玩次数
        Integer freePlayTimesRedis = getUserFreePlayTimes(userId, freePlayTimes);
        infoVo.setUserFreePlayTimes(freePlayTimesRedis);
        GameModel gameModel = gameModelService.getByGameModelKey(GameConstants.TANQIU_GAME_MODEL_KEY_REDPACKET);
        if (gameModel!=null) {
            infoVo.setGameModelKey(gameModel.getGameModelKey());
            infoVo.setGameModelId(gameModel.getId());
        }
        return infoVo;
    }


    private Integer getUserFreePlayTimes(Long userId, Integer freePlayTimes) {
        Integer freePlayTimesRedis = (Integer) redisTemplate.opsForHash().get(RedisFiledConstant.TANQIU_GAME_MODEL_REDPACKET_FREE_PALY_TIMES, userId.toString());
        if (freePlayTimesRedis==null) {
            //今日还没玩
            freePlayTimesRedis = freePlayTimes;
            redisTemplate.opsForHash().put(RedisFiledConstant.TANQIU_GAME_MODEL_REDPACKET_FREE_PALY_TIMES,userId.toString(),freePlayTimesRedis);
            Long size = redisTemplate.opsForHash().size(RedisFiledConstant.TANQIU_GAME_MODEL_REDPACKET_FREE_PALY_TIMES);
            if (size==1) {
                //今日第一个用户，设置该key的过期时间
                redisTemplate.expireAt(RedisFiledConstant.TANQIU_GAME_MODEL_REDPACKET_FREE_PALY_TIMES, DateUtil.getCurrentEndTimeDaily());
            }
        }
        return freePlayTimesRedis;
    }

    private Integer getUserFreePlayTimes(Long userId) {
        List<GameConfig> gameConfigs = gameConfigService.getConfigsByModelKeyAndCode(GameConstants.TANQIU_GAME_MODEL_KEY_REDPACKET,
                                                                                    GameConstants.TANQIU_GAME_CONFIG_CODE_FREE_PLAY_TIMES);
        if (gameConfigs.size()!=0) {
            return getUserFreePlayTimes(userId, Integer.valueOf(gameConfigs.get(0).getTypeValue()));
        }
        return 0;

    }

    private Integer getRedpacketModelCost() {
        List<GameConfig> gameConfigs = gameConfigService.getConfigsByModelKeyAndCode(GameConstants.TANQIU_GAME_MODEL_KEY_REDPACKET,
                GameConstants.TANQIU_GAME_CONFIG_CODE_PLAY_COST);
        if (gameConfigs.size()!=0) {
            return Integer.valueOf(gameConfigs.get(0).getTypeValue());
        }
        return 0;
    }


    @Override
    public Map redpacketGameBegin(Long userId,String nickName,String headUrl,Long gameModelId,String gameModelKey) {
        Map<String, Object> map = new HashMap<>();
        List<List> data = new ArrayList<>();
        map.put("isSignUpSuccess", false);
        map.put("isLackofCoin", false);
        map.put("cost", 0);
        map.put("gameNo", null);
        map.put("data", null);
        //判断是否为免费试玩
        Integer userFreePlayTimes = getUserFreePlayTimes(userId);
        if (userFreePlayTimes!=null&&userFreePlayTimes>0) {
            //免费试玩
            userFreePlayTimes--;
            redisTemplate.opsForHash().put(RedisFiledConstant.TANQIU_GAME_MODEL_REDPACKET_FREE_PALY_TIMES, userId.toString(), userFreePlayTimes);
            log.info("【参加红包赛】用户{}使用免费次数，剩余次数{}",userId,userFreePlayTimes);
        }else{
            //非免费试玩
            User user = userService.selectById(userId);
            UserInfo userInfo = userInfoService.selectByUserId(userId);
            if (userInfo != null&&user!=null) {
                Integer cost = getRedpacketModelCost();
                if (userInfo.getCoin() < cost) {
                    map.put("isLackofCoin", true);
                    return map;
                }
                userInfo.setCoin(userInfo.getCoin()-cost);
                userInfoService.saveOrUpdate(userInfo);
                //写流水
                transLogService.generateTransLog4RedpacketModelBegin(user,userInfo,cost);
                map.put("cost", cost);
                log.info("【参加红包赛】用户{}消耗趣币{}",userId,cost);
            }
        }
        //获取gameRedpacketRecords对象
        GameRedpacketRecords gameRedpacketRecords = redisClient.hGet(RedisFiledConstant.REDPACKET_GAME_RECORD, gameModelKey.toString(),GameRedpacketRecords.class);
        if (gameRedpacketRecords == null) {
            gameRedpacketRecords = createRedpacketRecord(gameModelId, gameModelKey);
        }
        data = JSON.parseArray(gameRedpacketRecords.getGameData(), List.class);


        //创建game_redpacket_user_records数据
        GameRedpacketUserRecords userRecords = new GameRedpacketUserRecords();
        userRecords.setGameModelId(gameModelId);
        userRecords.setGameModelKey(gameModelKey);
        userRecords.setGameNo(gameRedpacketRecords.getGameNo());
        userRecords.setUserId(userId);
        userRecords.setNickName(nickName);
        userRecords.setHeadUrl(headUrl);
        userRecords.setScore(0);
        userRecords.setIsCheated(false);
        userRecords.setIsRobot(false);
        userRecords.setNote("");
        userRecords.setCreateTime(new Date());
        redpacketUserRecordsService.saveOrUpdate(userRecords);
        redisClient.hSet(RedisFiledConstant.REDPACKET_GAME_USER_RECORDS+"_"+gameRedpacketRecords.getGameNo(), userId.toString(),userRecords);
        //封装数据
        map.put("isSignUpSuccess", true);
        map.put("data", data);
        map.put("gameNo", gameRedpacketRecords.getGameNo());
        return map;
    }

    /**
     * 创建游戏记录
     * @param gameModelId
     * @param gameModelKey
     * @param now
     * @return
     */
    public GameRedpacketRecords createRedpacketRecord(Long gameModelId, String gameModelKey) {
        //redis分布式锁 加锁
        String requestId = UUID.randomUUID().toString();
        int expireTime = 30000;//过期时间毫秒数 30秒
        while (!redisLock.tryGetDistributedLock(RedisFiledConstant.CREATE_REDPACKET_GAME_LOCK, requestId, expireTime)){
            try {
                int time = RandomUtils.nextInt(100, 500);
                Thread.sleep(Long.valueOf(time+""));
            } catch (Exception e) {
                log.error("【红包游戏】 创建游戏获取锁异常Exception={}",e);
            }
        }
        //如果获取到了，说明已经创建，返回即可
        GameRedpacketRecords gameRedpacketRecords = redisClient.hGet(RedisFiledConstant.REDPACKET_GAME_RECORD, gameModelKey.toString(),GameRedpacketRecords.class);
        if (gameRedpacketRecords!=null) {
            redisLock.releaseDistributedLock(RedisFiledConstant.CREATE_REDPACKET_GAME_LOCK, requestId);
            return gameRedpacketRecords;
        }
        //创建游戏数据包,游戏对象 加入redis中
        gameRedpacketRecords =  redpacketRecordsService.addRedpacketRecords(gameModelId,gameModelKey);
        redisClient.hSet(RedisFiledConstant.REDPACKET_GAME_RECORD, gameModelKey,gameRedpacketRecords);
        //创建作弊人 分时间段，使用随机定时任务加入
        redpacketRecordsService.addCheatedPlayersByTask(gameRedpacketRecords);
        //创建结算任务
        gameJobService.createRepacketEndJob(gameRedpacketRecords);
        //释放锁
        redisLock.releaseDistributedLock(RedisFiledConstant.CREATE_REDPACKET_GAME_LOCK, requestId);
        return gameRedpacketRecords;
    }



    /**
     * 红包赛结算
     */
    @Override
    @Transactional
    public void redpacketGameEnd(Map param) {
        String requestId = UUID.randomUUID().toString();
        //redis分布式锁 加锁
        if (!redisLock.tryGetDistributedLock(RedisFiledConstant.CLOSE_REDPACKET_GAME_LOCK, requestId, SystemConstant.REDIS_LOCK_EXPIRE_TIME_REDPACKET_END)){
            return;
        }
        log.info("【红包赛开始结算】");


        //获取当前的gameRedpacketRecords
        GameRedpacketRecords gameRedpacketRecords = redisClient.hGet(RedisFiledConstant.REDPACKET_GAME_RECORD, GameConstants.TANQIU_GAME_MODEL_KEY_REDPACKET,GameRedpacketRecords.class);
        if (gameRedpacketRecords == null) {
            throw new RuntimeException("【红包赛结算异常】，没有找到房间");
        }
        String gameNo = gameRedpacketRecords.getGameNo();
        //移除任务，如果是人工结算的话
        gameJobService.removeGameJob(SystemConstant.TASK_GROUP_REDPACKET_GAME_END,"红包赛定时结算任务开启"+gameNo);

        gameJobService.removeGroupGameJob(SystemConstant.TASK_GROUP_REDPACKET_GAME_CHEAT);
        //如果是人工手动结算的话，在作弊帐号还没有加满就已经结算，则补足人数
        redpacketRecordsService.addCheatedPlayersWhenEnded(gameRedpacketRecords);
        //获取参赛人数
        //Integer contestNum = redpacketUserRecordsService.getContestNum(gameNo);//真实参赛人次
        Map<String, GameRedpacketUserRecords> users = redisClient.hGetAll(RedisFiledConstant.REDPACKET_GAME_USER_RECORDS + "_" + gameRedpacketRecords.getGameNo(),GameRedpacketUserRecords.class);
        Integer contestNum = users.size();//真实参赛人数
        Integer cheatedNum = redpacketUserRecordsService.getCheatedNum(gameNo);
        gameRedpacketRecords.setContestNum(contestNum);
        gameRedpacketRecords.setCheatNum(cheatedNum);
        gameRedpacketRecords.setGameStatus((short) 2);
        //获取奖励配置
        List<GameRedpacketPrizeConfig> prizeList = redpacketPrizeConfigService.getPirzeListSortedByRank();
        if (prizeList.size()==0) {
            log.warn("【红包赛结算异常】，没有配置奖励");
        }else{
            //获取世界排行榜
            Set<ZSetOperations.TypedTuple<Object>> tuples = redisTemplate.opsForZSet().reverseRangeWithScores(RedisFiledConstant.TANQIU_GAME_MODEL_REDPACKET_RANK_SCORE, 0, prizeList.size()-1);

            //遍历，发奖励
            int i = 0;
            Iterator<ZSetOperations.TypedTuple<Object>> iterator = tuples.iterator();
            while (iterator.hasNext()) {
                ZSetOperations.TypedTuple<Object> typedTuple = iterator.next();
                Long userIdWorld = (Long) typedTuple.getValue();
                GameRedpacketPrizeConfig prizeConfig = prizeList.get(i);
                Integer prizeNum = prizeConfig.getPrizeNum();
                User user = userService.selectById(userIdWorld);
                if (!user.getIsRobot()) {
                    UserInfo userInfo = userInfoService.selectByUserId(userIdWorld);
                    //非机器人才发奖励
                    userInfo.setBalance(userInfo.getBalance() + prizeNum);
                    userInfoService.saveOrUpdate(userInfo);
                    transLogService.generateTransLog4RedpacketModelEnd(userInfo,user,prizeNum,i+1);
                    //记录到获奖记录表中
                    GameRedpacketRewardRecord rewardRecord = new GameRedpacketRewardRecord();
                    rewardRecord.setGameModelKey(gameRedpacketRecords.getGameModelKey());
                    rewardRecord.setGameNo(gameNo);
                    rewardRecord.setUserId(userInfo.getUserId());
                    rewardRecord.setNickName(user.getNickName());
                    rewardRecord.setHeadUrl(userInfo.getHeadPicUrl());
                    rewardRecord.setRank(i+1);
                    Double scoreWithTimeStamp = typedTuple.getScore();
                    Integer score = Integer.valueOf(scoreWithTimeStamp.longValue() / 10000000 + "");
                    rewardRecord.setMaxScore(score);
                    rewardRecord.setRewardType((short) 3);//红包
                    rewardRecord.setRewardNum(prizeNum);
                    rewardRecord.setRankedTime(new Date());
                    rewardRecord.setIsRead(false);
                    gameRedpacketRewardRecordService.saveOrUpdate(rewardRecord);
                }
                i++;
            }
        }

        //更新数据库数据
        redpacketRecordsService.saveOrUpdate(gameRedpacketRecords);

        //删除redis数据 排行榜
        redisTemplate.delete(RedisFiledConstant.TANQIU_GAME_MODEL_REDPACKET_RANK_SCORE);

        //删除redis数据 gameRedpacketUserRecords
        redisClient.del(RedisFiledConstant.REDPACKET_GAME_USER_RECORDS+"_"+gameRedpacketRecords.getGameNo());

        //删除redis数据 gameRedpacketRecords
        redisClient.hDel(RedisFiledConstant.REDPACKET_GAME_RECORD, gameRedpacketRecords.getGameModelKey());

        //更新redis数据 gameRedpacketRecords，即创建新的房间
        createRedpacketRecord(gameRedpacketRecords.getGameModelId(), gameRedpacketRecords.getGameModelKey());

        log.info("【红包赛成功结算】"+gameNo);
    }

    /**
     * 用户完成单次游戏结算
     * @param userId
     * @param score
     * @param gameNo
     * @return
     */
    @Override
    public Map redpacketUserEnd(Long userId, Integer score, String gameNo) {
        Map<String, Object> result = new HashMap<>();
        result.put("isSelfHighest",false);
        result.put("selfWorldRank", -1);
        result.put("isGameNoExpired", false);
        result.put("isCheated", false);

        GameRedpacketUserRecords userRecords = redisClient.hGet(RedisFiledConstant.REDPACKET_GAME_USER_RECORDS + "_" + gameNo, userId.toString(), GameRedpacketUserRecords.class);
        if (userRecords == null) {
            log.error("【用户结算异常】，没有在redis中找到id为{}的用户",userId);
            throw new RuntimeException("没有找到id为" + userId + "的用户");
        }

        //获取当前房间的maxScore，判定是否作弊了
        GameRedpacketRecordsExample example = new GameRedpacketRecordsExample();
        example.or().andGameNoEqualTo(gameNo);
        List<GameRedpacketRecords> records = redpacketRecordsService.selectByExample(example);
        if (records.size()==0) {
            log.error("【用户结算异常】，没有找到gameNo为"+gameNo+"的场次");
            throw new RuntimeException("没有找到gameNo为"+gameNo+"的场次");
        }
        GameRedpacketRecords gameRedpacketRecords = records.get(0);
        Date endTime = gameRedpacketRecords.getEndTime();
        Integer maxScore = gameRedpacketRecords.getMaxScore();
        userRecords.setScore(score);
        if (endTime.compareTo(new Date())<=0) {
            //说明该用户所在的场次房间已经开始结算
            userRecords.setNote("房间已经开始结算，该次分数不计入排行榜");
            result.put("isGameNoExpired", true);
        }else{
            //房间还没开始结算，那么redis中的排行榜数据就是该场次的，获取自己的最高分
            if (maxScore < score) {
                //作弊用户
                userRecords.setIsCheated(true);
                result.put("isCheated", true);
            }else{
                //不是作弊行为，才可以列入排行榜
                Integer selfHighestScore = gameRPRankRecordService.getSelfHighestScore(userId);
                if (selfHighestScore==null||selfHighestScore<score) {
                    result.put("isSelfHighest",true);
                    gameRPRankRecordService.setToRedisZset(userId, gameRedpacketRecords.getEndTime(),userRecords.getScore(), RedisFiledConstant.TANQIU_GAME_MODEL_REDPACKET_RANK_SCORE);
                }
            }
            //获取个人最佳排名
            Long rank = redisTemplate.opsForZSet().reverseRank(RedisFiledConstant.TANQIU_GAME_MODEL_REDPACKET_RANK_SCORE, userId);
            result.put("selfWorldRank", rank == null?-1:rank+1);
        }
        //更新用户表
        redpacketUserRecordsService.saveOrUpdate(userRecords);

        return result;
    }
}
