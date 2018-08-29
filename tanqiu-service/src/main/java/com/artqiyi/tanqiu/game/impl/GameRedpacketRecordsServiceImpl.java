package com.artqiyi.tanqiu.game.impl;
import java.util.Date;

import com.artqiyi.tanqiu.common.constant.RedisFiledConstant;
import com.artqiyi.tanqiu.common.constant.SystemConstant;
import com.artqiyi.tanqiu.common.util.RandomUtil;
import com.artqiyi.tanqiu.game.*;
import com.artqiyi.tanqiu.game.domain.*;
import com.artqiyi.tanqiu.game.vo.PositionVo;
import com.artqiyi.tanqiu.job.domain.ScheduleJob;
import com.artqiyi.tanqiu.payment.service.TransLogService;
import com.artqiyi.tanqiu.redis.RedisClient;
import com.artqiyi.tanqiu.service.IQuartzService;
import com.artqiyi.tanqiu.user.domain.User;
import com.artqiyi.tanqiu.user.domain.UserExample;
import com.artqiyi.tanqiu.user.domain.UserInfo;
import com.artqiyi.tanqiu.user.service.IUserInfoService;
import com.artqiyi.tanqiu.user.service.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/24
 * Modify On: 2018/07/24 11:17 by wufuchang
 */

import com.alibaba.fastjson.JSON;
import com.artqiyi.tanqiu.common.constant.GameConstants;
import com.artqiyi.tanqiu.common.util.DateUtil;
import com.artqiyi.tanqiu.common.util.StringUtils;
import com.artqiyi.tanqiu.game.mapper.GameRedpacketRecordsMapper;
import com.artqiyi.tanqiu.game.vo.BrickVo;
import com.artqiyi.tanqiu.game.vo.LayerVo;
import com.artqiyi.tanqiu.game.vo.RedpacketGameBeginVo;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.quartz.ee.jmx.jboss.QuartzService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class GameRedpacketRecordsServiceImpl implements IGameRedpacketRecordsService {
    private static Logger log = LoggerFactory.getLogger(GameRedpacketRecordsServiceImpl.class);

    @Autowired
    private IGameModelService gameModelService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private GameRedpacketRecordsMapper mapper;
    @Autowired
    private IGameConfigService gameConfigService;
    @Autowired
    private IGameDifficultyConfigService gameDifficultyConfigService;
    @Autowired
    private IGameRedpacketUserRecordsService gameRedpacketUserRecordsService;
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IQuartzService quartzService;
    @Autowired
    private TransLogService transLogService;
    @Autowired
    private IGameRPRankRecordService gameRPRankRecordService;
    @Autowired
    private IGameJobService gameJobService;

    @Override
    public PageInfo<GameRedpacketRecords> page(int page, int pageSize, GameRedpacketRecordsExample example) {
        PageHelper.startPage(page,pageSize);
        List<GameRedpacketRecords> gameRedpacketRecords = mapper.selectByExample(example);
        return new PageInfo<>(gameRedpacketRecords);
    }

    @Override
    public long saveOrUpdate(GameRedpacketRecords gameRedpacketRecords) {
        if(null==gameRedpacketRecords.getId() || gameRedpacketRecords.getId()==0){
            mapper.insertSelective(gameRedpacketRecords);
        }else{
            mapper.updateByPrimaryKeySelective(gameRedpacketRecords);
        }
        return gameRedpacketRecords.getId();
    }

    @Override
    public int deleteById(long id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteByExample(GameRedpacketRecordsExample gameRedpacketRecordsExample) {
        return mapper.deleteByExample(gameRedpacketRecordsExample);
    }

    @Override
    public int updateByExample(GameRedpacketRecords gameRedpacketRecords, GameRedpacketRecordsExample gameRedpacketRecordsExample) {
        return mapper.updateByExample(gameRedpacketRecords,gameRedpacketRecordsExample);
    }

    @Override
    public GameRedpacketRecords selectById(long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public long countByExample(GameRedpacketRecordsExample gameRedpacketRecordsExample) {
        return mapper.countByExample(gameRedpacketRecordsExample);
    }


    @Override
    public List<GameRedpacketRecords> selectByExample(GameRedpacketRecordsExample gameRedpacketRecordsExample) {
        return mapper.selectByExample(gameRedpacketRecordsExample);
    }


    /**
     * 创建新赛场
     * @param gameModelId
     * @param gameModelKey
     * @return
     */
    @Override
    public GameRedpacketRecords addRedpacketRecords(Long gameModelId, String gameModelKey) {
        GameModel gameModel = gameModelService.getByGameModelKey(gameModelKey);
        String gameModelName = gameModel.getGameModelName();

        //获取配置
        Map<String, String> gameConfigs = gameConfigService.getByGameModelKey(GameConstants.TANQIU_GAME_MODEL_KEY_REDPACKET);
        Integer totalRound  = Integer.valueOf(gameConfigs.get(GameConstants.TANQIU_GAME_CONFIG_LEVEL));
        String timesStr  = gameConfigs.get(GameConstants.TANQIU_GAME_CONFIG_TIME);
        Integer cheatNum = Integer.valueOf(gameConfigs.get(GameConstants.TANQIU_GAME_CONFIG_CODE_CHEAT_PLAYER_NUM));
        if (StringUtils.isEmpty(timesStr)) {
            log.error("【创建新赛场失败】：没有配置结算时间");
            throw new RuntimeException("【创建新赛场失败】：没有配置结算时间");
        }
        String[] times = timesStr.split(",");
        List<String> timeList = Arrays.asList(times);
        String gameNo;
        Date startTime;
        Date endTime;
        Date now = new Date();
        if (timeList.size()==1) {
            //每天只有一场
            String timeOne = timeList.get(0);
            String[] hourAndMinute = timeOne.split(":");
            Date date = DateUtils.addMinutes(DateUtils.addHours(DateUtil.getCurrentStartTimeDaily(), Integer.valueOf(hourAndMinute[0])), Integer.valueOf(hourAndMinute[1]));
            if (now.compareTo(date)<=0) {
                startTime = DateUtils.addDays(date, -1);
                endTime = date;
            }else{
                startTime = date;
                endTime = DateUtils.addDays(date, 1);
            }
            gameNo = generateGameNo(gameModelKey, startTime);
        } else if (timeList.size() > 1) {
            //每天超过一场
            String timeOne = timeList.get(0);
            String timeTwo = timeList.get(1);
            String[] hourAndMinuteOne = timeOne.split(":");
            String[] hourAndMinuteTwo = timeTwo.split(":");
            Date dateOne = DateUtils.addMinutes(DateUtils.addHours(DateUtil.getCurrentStartTimeDaily(), Integer.valueOf(hourAndMinuteOne[0])), Integer.valueOf(hourAndMinuteOne[1]));
            Date dateTwo = DateUtils.addMinutes(DateUtils.addHours(DateUtil.getCurrentStartTimeDaily(), Integer.valueOf(hourAndMinuteTwo[0])), Integer.valueOf(hourAndMinuteTwo[1]));
            if (now.compareTo(dateOne)<=0) {
                //今日早场
                startTime = DateUtils.addDays(dateTwo, -1);
                endTime = dateOne;
            } else if (now.compareTo(dateTwo)>0) {
                //明日早场
                startTime = dateTwo;
                endTime = DateUtils.addDays(dateOne, 1);
            }else {
                //今日晚场
                startTime = dateOne;
                endTime = dateTwo;
            }
            gameNo = generateGameNo(gameModelKey, startTime);
        }else{
            log.error("【创建新赛场失败】：没有配置结算时间");
            throw new RuntimeException("【创建新赛场失败】：没有配置结算时间");
        }

        //生成data
        List<List<LayerVo>> data = generateData(totalRound,gameModelKey);
        //根据data计算maxScore
        Integer maxScore = calMaxScoreByData(data);

        GameRedpacketRecords records = new GameRedpacketRecords();
        records.setGameNo(gameNo);
        records.setGameModelId(gameModelId);
        records.setGameModelKey(gameModelKey);
        records.setGameFiledName(gameModelName);
        records.setTotalRound(totalRound);
        records.setGameData(JSON.toJSONString(data));
        records.setMaxScore(maxScore);
        records.setContestNum(0);
        records.setCheatNum(cheatNum);
        records.setGameStatus((short) 1);
        records.setStartTime(startTime);
        records.setEndTime(endTime);
        records.setCreateTime(new Date());

        mapper.insertSelective(records);
        return records;

    }

    private String generateGameNo(String gameModelKey, Date startTime) {
        String gameNo;
        gameNo = gameModelKey + "_" + DateUtil.formatDate(startTime, "yyyyMMddHHmm")+"_"+DateUtil.formatDate(new Date(), "HHmmssSSS");
        return gameNo;
    }


    /**
     * 增加作弊用户并使用定时任务加入 初始
     */
    @Override
    public void addCheatedPlayersByTask(GameRedpacketRecords gameRedpacketRecords) {
        Integer cheatNum = gameRedpacketRecords.getCheatNum();
        addCheatedPlayersByTask(gameRedpacketRecords,cheatNum,null);
    }
    /**
     * 增加作弊用户并使用定时任务加入 服务器重启，需要重新制定定时任务
     * 账户不能重复
     */
    @Override
    public void addCheatedPlayersByTaskReboot(GameRedpacketRecords gameRedpacketRecords) {
        log.info("【服务器启动检查作弊任务】检查中");
        String gameNo = gameRedpacketRecords.getGameNo();
        Integer cheatedNum = gameRedpacketUserRecordsService.getCheatedNum(gameNo);
        Integer neededCheatNum = gameRedpacketRecords.getCheatNum();
        if (neededCheatNum<=cheatedNum) {
            log.info("【服务器启动检查作弊任务】不需要启动红包作弊人数增加任务，作弊人数已全部添加");
            return;
        }
        log.info("【服务器启动检查作弊任务】检查完毕，需要设置作弊人数任务，现有作弊人数{}，还需要增加作弊人数{}",cheatedNum,neededCheatNum-cheatedNum);
        //获取已经添加进去的机器账户
        List<GameRedpacketUserRecords> robots = gameRedpacketUserRecordsService.getCheatedRobot(gameNo);
        addCheatedPlayersByTask(gameRedpacketRecords,neededCheatNum-cheatedNum,robots);
    }

    /**
     * 结算时增加作弊用户，如果手动结算或出现定时任务加入作弊人数异常时
     * 账户不能重复
     */
    @Override
    public void addCheatedPlayersWhenEnded(GameRedpacketRecords gameRedpacketRecords) {
        log.info("【结算加入作弊人数】检查人数中");
        String gameNo = gameRedpacketRecords.getGameNo();
        Integer cheatedNum = gameRedpacketUserRecordsService.getCheatedNum(gameNo);
        Integer neededCheatNum = gameRedpacketRecords.getCheatNum();
        if (neededCheatNum<=cheatedNum) {
            log.info("【结算加入作弊人数】作弊人数已足够，不需要添加");
            return;
        }
        int stillNeedNum = neededCheatNum - cheatedNum;
        log.info("【结算加入作弊人数】检查完毕，需要添加作弊人数，现有作弊人数{}，还需要增加作弊人数{}",cheatedNum,stillNeedNum);
        //获取已经添加进去的机器账户
        List<GameRedpacketUserRecords> robotsExisted = gameRedpacketUserRecordsService.getCheatedRobot(gameNo);
        //加入作弊用户
        UserExample userExample = new UserExample();
        PageHelper.startPage(1, stillNeedNum*2);
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andIsRobotEqualTo(true);
        if (robotsExisted!=null&&robotsExisted.size()>0) {
            List<Long> robotIdsExisted = robotsExisted.stream().map(userRecords -> userRecords.getUserId()).collect(Collectors.toList());
            criteria.andIdNotIn(robotIdsExisted);
        }
        List<User> users = userService.selectByExample(userExample);
        if (users.size()==0) {
            log.error("【结算增加作弊用户异常】，没有机器人帐号");
            return ;
        }
        Collections.shuffle(users);
        if (stillNeedNum > users.size()) {
            log.warn("【结算增加作弊用户异常】机器人人数不足作弊数量，将以现有人数为准");
            stillNeedNum = users.size();
        }
        List<User> robotList= users.subList(0, stillNeedNum);
        Map paramMap=new HashMap<>();
        paramMap.put("users",robotList);
        paramMap.put("gameRedpacketRecords",gameRedpacketRecords);
        addCheatedPlays(paramMap);
    }

    /**
     * 增加作弊用户并使用定时任务加入
     */
    @Override
    public void addCheatedPlayersByTask(GameRedpacketRecords gameRedpacketRecords, Integer cheatNum, List<GameRedpacketUserRecords> robotsExisted) {
        log.info("【分配红包赛作弊账户集合，分批设置任务】");
        UserExample userExample = new UserExample();
        PageHelper.startPage(1, cheatNum*2);
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andIsRobotEqualTo(true);
        if (robotsExisted!=null&&robotsExisted.size()>0) {
            List<Long> robotIdsExisted = robotsExisted.stream().map(userRecords -> userRecords.getUserId()).collect(Collectors.toList());
            criteria.andIdNotIn(robotIdsExisted);
        }
        List<User> users = userService.selectByExample(userExample);
        if (users.size()==0) {
            log.error("【增加作弊用户异常】，没有机器人帐号");
            return ;
        }
        Collections.shuffle(users);
        if (cheatNum > users.size()) {
            log.warn("【机器人人数不足作弊数量】将以现有人数为准");
            cheatNum = users.size();
        }

        long endTime = gameRedpacketRecords.getEndTime().getTime();
        long startTime = gameRedpacketRecords.getStartTime().getTime();
        int gap = (int) ((endTime - startTime-1) / (60 * 60 * 1000));//结算时间与开始时间相隔多少个小时  14点开始 21点结束 15 16 17 18 19 20

        int eachShareNum = cheatNum / (gap==0?1:gap);
        eachShareNum = eachShareNum< 10 ? 10 : eachShareNum;//至少每次加入10个人

        List<List<User>> robotJobUserList = new ArrayList<>();
        if (cheatNum <= eachShareNum) {
            List<User> robotList= users.subList(0, cheatNum);
            robotJobUserList.add(robotList);
        }else{
            int share = cheatNum / eachShareNum;
            for (int i = 0; i < share; i++) {
                robotJobUserList.add(users.subList(i*eachShareNum, (i+1) * eachShareNum));
            }
            if (cheatNum % eachShareNum != 0) {
                robotJobUserList.add(users.subList(share * eachShareNum, cheatNum));
            }
        }
        log.info("【分配红包赛作弊账户集合，分批完毕】");
        addCheatedPlayersJob(robotJobUserList,gameRedpacketRecords);
    }

    private void addCheatedPlayersJob(List<List<User>> robotJobUserList, GameRedpacketRecords gameRedpacketRecords) {
        log.info("【设置增加红包赛作弊账户任务】");
        long endTime = gameRedpacketRecords.getEndTime().getTime();
        long now = new Date().getTime();
        int size = robotJobUserList.size();
        long gap = (endTime - now) / (60 * 60 * 1000);
        log.info("【设置增加红包赛作弊账户任务】,距离结算时间还有{}小时",gap);
        int times;
        if (gap == 0) {
            //距离结算时间不到一个小时，那么一次性直接加进去
            robotJobUserList.forEach(userList -> {
                Map paramMap=new HashMap<>();
                paramMap.put("users",userList);
                paramMap.put("gameRedpacketRecords",gameRedpacketRecords);
                addCheatedPlays(paramMap);
            });
            return;
        } else if (gap <= size) {
            if ((endTime - now) % (60 * 60 * 1000) != 0) {
                //说明距离整点还有时间
                times = Integer.valueOf(gap + "");
            }else{
                times = Integer.valueOf(gap - 1 + "");
            }
        } else {
            times = size;
        }
        log.info("【设置增加红包赛作弊账户任务】将分成{}加入",times);
        for (int i = 0; i < robotJobUserList.size(); i++) {
            Date cronDate = DateUtils.addHours(gameRedpacketRecords.getEndTime(), 0 - (times > 0 ? times : 1));
            times--;
            List<User> users = robotJobUserList.get(i);
            //参数封装
            gameJobService.createCheatedPlayersJob(gameRedpacketRecords, i, cronDate, users);
        }
        log.info("【设置增加红包赛作弊账户任务完毕】");

    }


    @Override
    public void addCheatedPlays(Map param) {
        log.info("【增加红包赛作弊账户】");
        List<User> users = (List<User>) param.get("users");
        GameRedpacketRecords gameRedpacketRecords = (GameRedpacketRecords) param.get("gameRedpacketRecords");
        Integer index = (Integer) param.get("index");
        gameJobService.removeGameJob(SystemConstant.TASK_GROUP_REDPACKET_GAME_CHEAT,"红包赛作弊-作弊账户定时加入排行榜"+gameRedpacketRecords.getGameNo()+"_"+index);
        List<GameConfig> gameConfigs = gameConfigService.getConfigsByModelKeyAndCode(GameConstants.TANQIU_GAME_MODEL_KEY_REDPACKET, GameConstants.TANQIU_GAME_CONFIG_CODE_CHEAT_SCORE_FLOOR_RANGE);
        if (gameConfigs.size()==0) {
            log.error("【增加作弊用户异常】，没有配置作弊分数段");
            return ;
        }
        Integer floorRange = Integer.valueOf(gameConfigs.get(0).getTypeValue());
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            Long userId = user.getId();
            UserInfo userInfo = userInfoService.selectByUserId(userId);
            GameRedpacketUserRecords userRecords = new GameRedpacketUserRecords();
            userRecords.setGameModelId(gameRedpacketRecords.getGameModelId());
            userRecords.setGameModelKey(gameRedpacketRecords.getGameModelKey());
            userRecords.setGameNo(gameRedpacketRecords.getGameNo());
            userRecords.setUserId(userId);
            userRecords.setNickName(user.getNickName());
            userRecords.setHeadUrl(userInfo.getHeadPicUrl());
            userRecords.setScore(RandomUtils.nextInt(gameRedpacketRecords.getMaxScore()-floorRange,gameRedpacketRecords.getMaxScore()+1));
            userRecords.setIsCheated(false);
            userRecords.setIsRobot(true);
            userRecords.setNote("");
            userRecords.setCreateTime(new Date());
            gameRedpacketUserRecordsService.saveOrUpdate(userRecords);
            //redisClient.hSet(RedisFiledConstant.REDPACKET_GAME_USER_RECORDS+"_"+gameRedpacketRecords.getGameNo(), userId.toString(),userRecords);
            //直接加入排行榜
            gameRPRankRecordService.setToRedisZset(userId, gameRedpacketRecords.getEndTime(),userRecords.getScore(), RedisFiledConstant.TANQIU_GAME_MODEL_REDPACKET_RANK_SCORE);
            log.info("【增加红包赛作弊账户完毕】");
        }
    }


    /**
     * 生成渲染数据
     * @param totalRound
     * @param gameModelKey
     * @return
     */
    public List<List<LayerVo>> generateData(Integer totalRound,String gameModelKey) {
        List<List<LayerVo>> data = new ArrayList<>(totalRound);
        int scoreSum = 0;
        //读取难度设置
        GameDifficultyConfigExample configExample = new GameDifficultyConfigExample();
        configExample.setOrderByClause("game_round ASC");
        configExample.or().andGameModelKeyEqualTo(gameModelKey).andStatusEqualTo((short) 1);
        List<GameDifficultyConfig> configs = gameDifficultyConfigService.selectByExample(configExample);
        if (configs.size()< totalRound) {
            log.warn("【生成数据异常】，配置表中的轮数与难度配置的关数不一致，将以难度配置为准");
            totalRound = configs.size();
        }
        if (configs.size()==0) {
            log.warn("【生成数据异常】，配置表中没有该gameKey的难度配置");
            return data;
        }

        for (Integer i = 0; i < totalRound; i++) {
            GameDifficultyConfig config = configs.get(i);
            Integer minRow = config.getMinRow();
            Integer maxRow = config.getMaxRow();
            Integer minScore = config.getMinScore();
            Integer maxScore = config.getMaxScore();
            Integer totalNum = config.getTotalNum();

            if (totalNum > ((maxRow - minRow + 1) * 4)) {
                log.warn("【生成数据警告】砖块总个数大于允许的个数，将以最大个数为准");
                totalNum = (maxRow - minRow + 1) * 4;
            }

            //随机创建每行的砖块数量
            List<Integer> createNumList = generateCreateNum(totalNum, maxRow,minRow);
            List<LayerVo> layerVos = new ArrayList<>(maxRow);
            //隔行的posXY的x正负相间，同行为同为正或同为负
            int randomNum = RandomUtils.nextInt(0, 2);
            int randomPosXYFactor;
            if (randomNum == 0) {
                randomPosXYFactor = -1;
            }else{
                randomPosXYFactor = 1;
            }
            for (int j = 0; j < maxRow; j++) {
                randomPosXYFactor = -1 * randomPosXYFactor;

                LayerVo layerVo = new LayerVo();
                Integer createNum = createNumList.get(j);
                layerVo.setCreatNum(createNum);

                List<BrickVo> brickData = new ArrayList<>(createNum);
                Set<Integer> brickPosSet = new HashSet<>();
                for (int k = 0; k < createNum; k++) {
                    BrickVo brickVo = new BrickVo();
                    int brickPos = RandomUtils.nextInt(1, 5);
                    while (brickPosSet.contains(brickPos)) {
                        brickPos = RandomUtils.nextInt(1, 5);
                    }
                    brickPosSet.add(brickPos);
                    brickVo.setPos(brickPos);
                    brickVo.setBrickType(RandomUtils.nextInt(1,5));
                    PositionVo posXY = new PositionVo();
                    posXY.setX(randomPosXYFactor*RandomUtil.randomNum(0, 51,1));
                    posXY.setY(RandomUtils.nextInt(0,20));
                    brickVo.setPosXY(posXY);
                    int score = RandomUtils.nextInt(minScore, maxScore + 1);
                    brickVo.setLifeVal(score);
                    scoreSum += score;
                    brickVo.setRotation(RandomUtils.nextInt(0,361));
                    brickData.add(k, brickVo);
                }
                layerVo.setData(brickData);
                layerVos.add(j, layerVo);
            }
            for (int m = layerVos.size() - 1; m >= 0; m--) {
                if (layerVos.get(m).getCreatNum() == 0) {
                    layerVos.remove(m);
                }else{
                    break;
                }
            }
            data.add(layerVos);
        }
        log.info("总分数为{}",scoreSum);
        log.info("【生成数据】"+data);
        return data;
    }


    /**
     * 生成砖块数
     * @param totalNum
     * @param maxRow
     * @param minRow
     * @return
     */
    private static List<Integer> generateCreateNum(Integer totalNum, Integer maxRow, Integer minRow) {
        //允许一行的砖块为0，但是最好按照上到下，逐步增多
        //根据需要的行数，分配概率区间
        int rows = maxRow - minRow + 1;
        List<Integer> randomFactors = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            if (i < rows - 1) {
                randomFactors.add(100 *(i+1)/ rows);
            }else{
                randomFactors.add(100);
            }
        }
        List<Integer> createNums = new ArrayList<>(maxRow);
        for (Integer integer = 0; integer < maxRow; integer++) {
            createNums.add(0);
        }
        while (totalNum > 0) {
            int randomFactor = RandomUtils.nextInt(1, 101);
            int rowToAdd;
            long count = randomFactors.stream().filter(factor -> factor >= randomFactor).count();
            rowToAdd = RandomUtils.nextInt(minRow, (int) (minRow+count));

            Integer rowBrickNum = createNums.get(rowToAdd - 1);
            while(rowBrickNum+1>4) {
                rowToAdd = RandomUtils.nextInt(minRow, maxRow + 1);
                rowBrickNum = createNums.get(rowToAdd - 1);
            }
            createNums.set(rowToAdd - 1, rowBrickNum + 1);
            totalNum--;
        }
        return createNums;
    }

    @Override
    public List<List<LayerVo>> generateDataForFight(int totalRound, String gameModelKey) {
        List<List<LayerVo>> data = new ArrayList<>(totalRound);
        int scoreSum = 0;
        //读取难度设置
        GameDifficultyConfigExample configExample = new GameDifficultyConfigExample();
        configExample.setOrderByClause("game_round ASC");
        configExample.or().andGameModelKeyEqualTo(gameModelKey).andStatusEqualTo((short) 1);
        List<GameDifficultyConfig> configs = gameDifficultyConfigService.selectByExample(configExample);
        if (configs.size()< totalRound) {
            log.warn("【生成数据异常】，配置表中的轮数与难度配置的关数不一致，将以难度配置为准");
            totalRound = configs.size();
        }
        if (configs.size()==0) {
            log.warn("【生成数据异常】，配置表中没有该gameKey的难度配置");
            return data;
        }

        for (Integer i = 0; i < totalRound; i++) {
            GameDifficultyConfig config = configs.get(i);
            Integer maxRow = config.getMaxRow();
            List<LayerVo> layerVos = new ArrayList<>(maxRow);
            //隔行的posXY的x正负相间，同行为同为正或同为负
            int randomNum = RandomUtils.nextInt(0, 2);
            int randomPosXYFactor;
            if (randomNum == 0) {
                randomPosXYFactor = -1;
            }else{
                randomPosXYFactor = 1;
            }
            for (int j = 0; j < maxRow; j++) {
                randomPosXYFactor = -1 * randomPosXYFactor;
                //创建每行的砖块数，完全随机
                LayerVo layerVo = new LayerVo();
                Integer createNum = RandomUtils.nextInt(1, 5);
                layerVo.setCreatNum(createNum);

                List<BrickVo> brickData = new ArrayList<>(createNum);
                Set<Integer> brickPosSet = new HashSet<>();
                for (int k = 0; k < createNum; k++) {
                    BrickVo brickVo = new BrickVo();
                    int brickPos = RandomUtils.nextInt(1, 5);
                    while (brickPosSet.contains(brickPos)) {
                        brickPos = RandomUtils.nextInt(1, 5);
                    }
                    brickPosSet.add(brickPos);
                    brickVo.setPos(brickPos);
                    Integer brickType = generateBrickType();
                    brickVo.setBrickType(brickType);
                    PositionVo posXY = new PositionVo();
                    posXY.setX(randomPosXYFactor*RandomUtil.randomNum(0, 51,1));
                    posXY.setY(RandomUtils.nextInt(0,20));
                    brickVo.setPosXY(posXY);
                    Integer score = generateScore(brickType,j+1);
                    brickVo.setLifeVal(score);
                    scoreSum += score;
                    brickVo.setRotation(RandomUtils.nextInt(0,361));
                    brickData.add(k, brickVo);
                }
                layerVo.setData(brickData);
                layerVos.add(j, layerVo);
            }
            data.add(layerVos);
        }
        log.info("总分数为{}",scoreSum);
        log.info("【生成数据】"+data);
        return data;
    }

    /**
     * 根据行数生成生命值，控制难度
     * @param brickType
     * @param i
     * @return
     */
    private static Integer generateScore(Integer brickType, int layerNum) {
        Integer life;
        int basicNum = 5;
        if (brickType >= 5 && brickType <= 8) {
            life = 1;
        } else {
            life =Double.valueOf(Math.random() * (layerNum * basicNum * 2)).intValue() + 1;
        }
        return life;
    }

    /**
     * 生成砖块类型
     *  DSC-SUB:
     *  DSC-VAL: 1  ——   Circle
     *  DSC-VAL: 2  ——   FiveStar
     *  DSC-VAL: 3  ——   Square
     *  DSC-VAL: 4  ——   Triangle
     *  DSC-VAL: 5  ——   MagicCube
     *  DSC-VAL: 6  ——   Bomb
     *  DSC-VAL: 7  ——   FrozenCube
     *  DSC-VAL: 8  ——   AddCube
     * @return
     */
    private static Integer generateBrickType() {
        Integer type;
        int typeRate = RandomUtils.nextInt(1, 5);
        if (typeRate > 1) {
            int brickTypeRate = RandomUtils.nextInt(1, 5);
            switch (brickTypeRate) {
                case 1:type= 1;break;
                case 2:type= 2;break;
                case 3:type= 3;break;
                default:type= 4;break;
            }
        }else{
            int brickPropertyRate = RandomUtils.nextInt(5, 9);
            switch (brickPropertyRate) {
                case 5:type= 5;break;
                case 6:type= 6;break;
                case 7:type= 7;break;
                default:type= 8;break;
            }
        }
        return type;
    }


    private  Integer calMaxScoreByData(List<List<LayerVo>> data) {
        Integer scoreSum =  Integer.valueOf(data.stream().mapToLong(layerVos ->
                                layerVos.stream().mapToLong(layerVo ->
                                layerVo.getData().stream().mapToInt(BrickVo::getLifeVal).summaryStatistics().getSum()).summaryStatistics().getSum()).summaryStatistics().getSum()+"");
        log.info("计算的总分数为{}",scoreSum);
        return scoreSum;
    }

    public static void main(String[] args) {
        /*for (int i = 0; i < 10000; i++) {
        System.out.println(RandomUtil.randomNum(-1, 0,1));

        }*/
        /*List<Integer> sum = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            sum.add(0);
        }
        for (int i = 10000; i > 0; i--) {
            List<Integer> integers = generateCreateNum(22, 7, 1);
            System.out.println(integers);
            for (int j = 0; j < integers.size(); j++) {
                sum.set(j, sum.get(j) + integers.get(j));
            }
        }
        System.out.println(sum);*/

/*        int totalNum = 10;
        int maxRow = 7;
        int minRow = 3;
        int minScore = 10;
        int maxScore = 15;
        int scoreSum = 0;
        List<List> data = new ArrayList<>();

        List<Integer> createNumList = generateCreateNum(totalNum, maxRow,minRow);
        List<LayerVo> layerVos = new ArrayList<>(maxRow);
        //隔行的posXY的x正负相间，同行为同为正或同为负
        int randomNum = RandomUtils.nextInt(0, 2);
        int randomPosXYFactor;
        if (randomNum == 0) {
            randomPosXYFactor = -1;
        }else{
            randomPosXYFactor = 1;
        }
        for (int j = 0; j < maxRow; j++) {
            randomPosXYFactor = -1 * randomPosXYFactor;

            LayerVo layerVo = new LayerVo();
            Integer createNum = createNumList.get(j);
            layerVo.setCreatNum(createNum);

            List<BrickVo> brickData = new ArrayList<>(createNum);
            Set<Integer> brickPosSet = new HashSet<>();
            for (int k = 0; k < createNum; k++) {
                BrickVo brickVo = new BrickVo();
                int brickPos = RandomUtils.nextInt(1, 5);
                while (brickPosSet.contains(brickPos)) {
                    brickPos = RandomUtils.nextInt(1, 5);
                }
                brickPosSet.add(brickPos);
                brickVo.setPos(brickPos);
                brickVo.setBrickType(RandomUtils.nextInt(1,5));
                PositionVo posXY = new PositionVo();
                posXY.setX(randomPosXYFactor*RandomUtil.randomNum(0, 51,1));
                posXY.setY(RandomUtils.nextInt(0,20));
                brickVo.setPosXY(posXY);
                int score = RandomUtils.nextInt(minScore, maxScore + 1);
                brickVo.setLifeVal(score);
                scoreSum += score;
                brickVo.setRotation(RandomUtils.nextInt(0,361));
                brickData.add(k, brickVo);
            }
            layerVo.setData(brickData);
            layerVos.add(j, layerVo);
        }
        for (int m = layerVos.size() - 1; m >= 0; m--) {
            if (layerVos.get(m).getCreatNum() == 0) {
                layerVos.remove(m);
            }else{
                break;
            }
        }
        data.add(layerVos);*/


        int maxRow = 50;
        List<List> data = new ArrayList<>();

            List<LayerVo> layerVos = new ArrayList<>(maxRow);
            //隔行的posXY的x正负相间，同行为同为正或同为负
            int randomNum = RandomUtils.nextInt(0, 2);
            int randomPosXYFactor;
            if (randomNum == 0) {
                randomPosXYFactor = -1;
            }else{
                randomPosXYFactor = 1;
            }
            for (int j = 0; j < maxRow; j++) {
                randomPosXYFactor = -1 * randomPosXYFactor;
                //创建每行的砖块数，完全随机
                LayerVo layerVo = new LayerVo();
                Integer createNum = RandomUtils.nextInt(1, 5);
                layerVo.setCreatNum(createNum);

                List<BrickVo> brickData = new ArrayList<>(createNum);
                Set<Integer> brickPosSet = new HashSet<>();
                for (int k = 0; k < createNum; k++) {
                    BrickVo brickVo = new BrickVo();
                    int brickPos = RandomUtils.nextInt(1, 5);
                    while (brickPosSet.contains(brickPos)) {
                        brickPos = RandomUtils.nextInt(1, 5);
                    }
                    brickPosSet.add(brickPos);
                    brickVo.setPos(brickPos);
                    Integer brickType = generateBrickType();
                    brickVo.setBrickType(brickType);
                    PositionVo posXY = new PositionVo();
                    posXY.setX(randomPosXYFactor*RandomUtil.randomNum(0, 51,1));
                    posXY.setY(RandomUtils.nextInt(0,20));
                    brickVo.setPosXY(posXY);
                    Integer score = generateScore(brickType,j+1);
                    brickVo.setLifeVal(score);
                    brickVo.setRotation(RandomUtils.nextInt(0,361));
                    brickData.add(k, brickVo);
                }
                layerVo.setData(brickData);
                layerVos.add(j, layerVo);
            }
            data.add(layerVos);
            System.out.println(JSON.toJSONString(data));
    }
}
