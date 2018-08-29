package com.artqiyi.tanqiu.game.impl;

import com.artqiyi.tanqiu.common.constant.BreakGameConstants;
import com.artqiyi.tanqiu.common.constant.GameConstants;
import com.artqiyi.tanqiu.common.constant.SystemConstant;
import com.artqiyi.tanqiu.common.util.DateUtil;
import com.artqiyi.tanqiu.game.IGameConfigService;
import com.artqiyi.tanqiu.game.IGameJobService;
import com.artqiyi.tanqiu.game.domain.GameBreakRecords;
import com.artqiyi.tanqiu.game.domain.GameConfig;
import com.artqiyi.tanqiu.game.domain.GameModel;
import com.artqiyi.tanqiu.game.domain.GameRedpacketRecords;
import com.artqiyi.tanqiu.game.vo.GameBreakUserRecordsVo;
import com.artqiyi.tanqiu.game.vo.GameFightUserRecordsVo;
import com.artqiyi.tanqiu.job.domain.ScheduleJob;
import com.artqiyi.tanqiu.service.IQuartzService;
import com.artqiyi.tanqiu.user.domain.User;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 游戏相关定时任务
 */
@Service
public class GameJobServiceImpl implements IGameJobService{
    private static final Logger log = LoggerFactory.getLogger(GameJobServiceImpl.class);
    @Autowired
    private IQuartzService quartzService;

    @Autowired
    private IGameConfigService gameConfigService;

    private static int OVERTIME=30*1000;//超时时间


    /**
     * 闯关游戏用户超时监测任务
     * @param gameBreakUserRecordsVo
     */
    @Override
    public void breakGameOvertimeTask(GameBreakUserRecordsVo gameBreakUserRecordsVo) {
        //参数封装
        Map paramMap=new HashMap<>();
        paramMap.put("userId",gameBreakUserRecordsVo.getUserId());
        paramMap.put("againstId",gameBreakUserRecordsVo.getAgainstId());
        paramMap.put("gameNo",gameBreakUserRecordsVo.getGameNo());
        ScheduleJob jobForEnd = new ScheduleJob();
        jobForEnd.setClazzName(null);
        jobForEnd.setIsConcurrent(false);
        long time=System.currentTimeMillis()+OVERTIME;
        jobForEnd.setCronExpression(DateUtil.formatDate(time, DateUtil.CRON_FORMAT));
        jobForEnd.setDescription("闯关-超时监测定时任务开启"+gameBreakUserRecordsVo.getGameNo()+"_"+gameBreakUserRecordsVo.getUserId()+gameBreakUserRecordsVo.getAgainstId());
        jobForEnd.setIsSpringbean(true);
        jobForEnd.setJobGroup(SystemConstant.TASK_GROUP_BREAK_GAME_OVERTIME);
        jobForEnd.setJobName(gameBreakUserRecordsVo.getGameNo()+"_"+gameBreakUserRecordsVo.getUserId());
        jobForEnd.setJobstatus(SystemConstant.TASK_STATUS_READY);
        jobForEnd.setTargetMethod("overtimeTask");
        jobForEnd.setTargetObject("gameBreakService");
        jobForEnd.setParam(paramMap);
        quartzService.deletJob(jobForEnd);
        quartzService.addJob(jobForEnd);
        log.info("闯关-超时监测任务开启"+gameBreakUserRecordsVo.getGameNo()+"_"+gameBreakUserRecordsVo.getUserId()+gameBreakUserRecordsVo.getAgainstId());
    }

    /**
     * 好友对战游戏PK结算
     * @param gameFightUserRecordsVo
     */
    @Override
    public void fightGameEndPkTask(GameFightUserRecordsVo gameFightUserRecordsVo) {
        //参数封装
        Map paramMap=new HashMap<>();
        paramMap.put("userId",gameFightUserRecordsVo.getUserId());
        paramMap.put("roomNo",gameFightUserRecordsVo.getRoomNo());
        ScheduleJob jobForEnd = new ScheduleJob();
        jobForEnd.setClazzName(null);
        jobForEnd.setIsConcurrent(false);
        long extraTime;
        List<GameConfig> configs = gameConfigService.getConfigsByModelKeyAndCode(GameConstants.TANQIU_GAME_MODEL_KEY_FIGHT, GameConstants.TANQIU_GAME_CONFIG_CODE_END_PK_TASK_EXTRA_TIME);
        if (CollectionUtils.isEmpty(configs)) {
            extraTime = 5*1000;
        } else {
            extraTime = Long.valueOf(configs.get(0).getTypeValue())*1000;
        }
        long time=System.currentTimeMillis()+gameFightUserRecordsVo.getGameTime()*1000+extraTime;
        jobForEnd.setCronExpression(DateUtil.formatDate(time, DateUtil.CRON_FORMAT));
        jobForEnd.setDescription("好友对战-单轮PK结算定时任务开启"+gameFightUserRecordsVo.getRoomNo());
        jobForEnd.setIsSpringbean(true);
        jobForEnd.setJobGroup(SystemConstant.TASK_GROUP_FIGHT_GAME_PK);
        jobForEnd.setJobName(gameFightUserRecordsVo.getRoomNo());
        jobForEnd.setJobstatus(SystemConstant.TASK_STATUS_READY);
        jobForEnd.setTargetMethod("endFightPkTask");
        jobForEnd.setTargetObject("gameFightServiceImpl");
        jobForEnd.setParam(paramMap);
        quartzService.deletJob(jobForEnd);
        quartzService.addJob(jobForEnd);
        log.info("好友对战-单轮PK结算定时任务开启"+gameFightUserRecordsVo.getRoomNo());
    }

    /**
     * 好友对战用户超时监测任务
     * @param gameFightUserRecordsVo
     */
    @Override
    public void fightGameOvertimeTask(GameFightUserRecordsVo gameFightUserRecordsVo) {
        //参数封装
        Map paramMap=new HashMap<>();
        paramMap.put("userId",gameFightUserRecordsVo.getUserId());
        paramMap.put("roomNo",gameFightUserRecordsVo.getRoomNo());
        ScheduleJob jobForEnd = new ScheduleJob();
        jobForEnd.setClazzName(null);
        jobForEnd.setIsConcurrent(false);
        long overTime;
        List<GameConfig> configs = gameConfigService.getConfigsByModelKeyAndCode(GameConstants.TANQIU_GAME_MODEL_KEY_FIGHT, GameConstants.TANQIU_GAME_CONFIG_CODE_GAME_DATA_OVER_TIME);
        if (CollectionUtils.isEmpty(configs)) {
            overTime = 20*1000;
        } else {
            overTime = Long.valueOf(configs.get(0).getTypeValue())*1000;
        }
        log.debug("好友对战超时检测时间为"+overTime+"毫秒");
        long time=System.currentTimeMillis()+overTime;
        jobForEnd.setCronExpression(DateUtil.formatDate(time, DateUtil.CRON_FORMAT));
        jobForEnd.setDescription("好友对战-用户超时监测定时任务开启"+gameFightUserRecordsVo.getRoomNo());
        jobForEnd.setIsSpringbean(true);
        jobForEnd.setJobGroup(SystemConstant.TASK_GROUP_FIGHT_GAME_OVERTIME);
        jobForEnd.setJobName(gameFightUserRecordsVo.getRoomNo()+"_"+gameFightUserRecordsVo.getUserId());
        jobForEnd.setJobstatus(SystemConstant.TASK_STATUS_READY);
        jobForEnd.setTargetMethod("overtimeTask");
        jobForEnd.setTargetObject("gameFightServiceImpl");
        jobForEnd.setParam(paramMap);
        quartzService.deletJob(jobForEnd);
        quartzService.addJob(jobForEnd);
        log.debug("好友对战-用户超时监测任务开启"+gameFightUserRecordsVo.getRoomNo());
    }

    /**
     * 游戏结算任务移除
     * @param groupName
     * @param JobName
     */
    @Override
    public void removeGameJob(String groupName, String JobName) {
        ScheduleJob jobForEnd = new ScheduleJob();
        jobForEnd.setJobName(JobName);
        jobForEnd.setJobGroup(groupName);
        quartzService.deletJob(jobForEnd);
    }

    @Override
    public void removeGroupGameJob(String groupName) {
        log.info("【开始移除组任务】{}",groupName);
        List<String> groupJobBefore = quartzService.getGroupJob(groupName);
        log.info("【开始移除组任务】移除前的job有"+groupJobBefore);
        quartzService.deletGroupJob(groupName);
        List<String> groupJobAfter = quartzService.getGroupJob(groupName);
        log.info("【开始移除组任务】移除后的job有"+groupJobAfter);
    }

    /**
     * 创建结算定时任务
     *
     * @param params
     */
    public void addCloseBreakGameJob(Map<String, Object> params) {
        GameModel game = (GameModel) params.get("game");
        GameBreakRecords gameRecord = (GameBreakRecords) params.get("gameRecord");

        Map<String, String> configs = gameConfigService.getByGameModelKey(game.getGameModelKey());
        String awardTime = configs.get(BreakGameConstants.TIME);
        if (awardTime == null) {
            log.error("【定时任务】结算定时任务，系统参数表break_game_config未配置参数BIG_BONUS_SYSTEM_TIME");
            throw new RuntimeException("【定时任务】结算定时任务，系统参数表break_game_config未配置参数'开奖时间'");
        }

        String cronExp = getCron(awardTime);
        log.info("【定时任务】 结算cron={}",cronExp);

        //格式"0 0 21 1 6 ? 2018"-- 2018年6月1号21点0分0秒触发任务
        log.info("【弹球结算】 设置" + game.getGameModelName() + "结算任务");
        ScheduleJob job = new ScheduleJob();
        job.setClazzName(null);
        job.setIsConcurrent(false);
        job.setCronExpression(cronExp);
        job.setDescription("closeBreakGame");
        job.setIsSpringbean(true);
        job.setJobGroup(SystemConstant.TASK_GROUP_BREAK_GAME_CLOSE);
        job.setJobName(gameRecord.getGameNo());
        job.setJobstatus(SystemConstant.TASK_STATUS_READY);
        job.setTargetMethod("closeGame");
        job.setTargetObject("gameBreakService");
        job.setParam(game.getId());
        quartzService.addJob(job);
        log.info("【弹球结算】 设置" + game.getGameModelName() + "结算任务完毕");
    }

    @Override
    public void createRepacketEndJob(GameRedpacketRecords gameRedpacketRecords) {
        //参数封装
        Map paramMap=new HashMap<>();
        paramMap.put("gameRedpacketRecords",gameRedpacketRecords);
        ScheduleJob jobForEnd = new ScheduleJob();
        jobForEnd.setClazzName(null);
        jobForEnd.setIsConcurrent(false);
        jobForEnd.setCronExpression(DateUtil.formatDate(gameRedpacketRecords.getEndTime(), DateUtil.CRON_FORMAT));
        jobForEnd.setDescription("红包赛定时结算任务开启"+gameRedpacketRecords.getGameNo());
        jobForEnd.setIsSpringbean(true);
        jobForEnd.setJobGroup(SystemConstant.TASK_GROUP_REDPACKET_GAME_END);
        jobForEnd.setJobName("红包赛定时结算任务开启"+gameRedpacketRecords.getGameNo());
        jobForEnd.setJobstatus(SystemConstant.TASK_STATUS_READY);
        jobForEnd.setTargetMethod("redpacketGameEnd");
        jobForEnd.setTargetObject("gameRedpackServiceImpl");
        jobForEnd.setParam(paramMap);
        quartzService.deletJob(jobForEnd);
        quartzService.addJob(jobForEnd);
        log.info("红包赛定时结算任务开启"+gameRedpacketRecords.getGameNo()+"cron为"+jobForEnd.getCronExpression());
    }

    /**
     * 创建分批导入作弊用户任务  红包赛
     * @param gameRedpacketRecords
     * @param i
     * @param cronDate
     * @param users
     */
    @Override
    public void createCheatedPlayersJob(GameRedpacketRecords gameRedpacketRecords, int i, Date cronDate, List<User> users) {
        Map paramMap=new HashMap<>();
        paramMap.put("users",users);
        paramMap.put("gameRedpacketRecords",gameRedpacketRecords);
        paramMap.put("index",i);
        ScheduleJob jobForEnd = new ScheduleJob();
        jobForEnd.setClazzName(null);
        jobForEnd.setIsConcurrent(false);
        jobForEnd.setCronExpression(DateUtil.formatDate(cronDate, DateUtil.CRON_FORMAT));
        jobForEnd.setDescription("红包赛作弊-作弊账户定时加入排行榜任务开启"+gameRedpacketRecords.getGameNo()+"cron为"+jobForEnd.getCronExpression());
        jobForEnd.setIsSpringbean(true);
        jobForEnd.setJobGroup(SystemConstant.TASK_GROUP_REDPACKET_GAME_CHEAT);
        jobForEnd.setJobName("红包赛作弊-作弊账户定时加入排行榜"+gameRedpacketRecords.getGameNo()+"_"+i);
        jobForEnd.setJobstatus(SystemConstant.TASK_STATUS_READY);
        jobForEnd.setTargetMethod("addCheatedPlays");
        jobForEnd.setTargetObject("gameRedpacketRecordsServiceImpl");
        jobForEnd.setParam(paramMap);
        quartzService.deletJob(jobForEnd);
        quartzService.addJob(jobForEnd);
        log.info("红包赛作弊-作弊账户定时加入排行榜任务开启"+gameRedpacketRecords.getGameNo()+"_"+i+"cron为"+jobForEnd.getCronExpression());
        List<String> groupJobs = quartzService.getGroupJob(jobForEnd.getJobGroup());
        log.info("红包赛作弊-作弊账户定时任务有"+groupJobs);
    }


    /**
     * 获取cron
     *
     * @param awardTime
     * @return
     */
    private static String getCron(String awardTime) {
        DateTime time = new DateTime();
        int hour = time.getHourOfDay();
        int minute = time.minuteOfHour().get();
        DateTime plusDays = time.plusDays(1);
        String cron = "ss mm HH dd MM ? yyyy";
        //默认次日21点
        String cronExp = null;


        String[] split = awardTime.split(":");
        int h = Integer.parseInt(split[0]);
        int m = Integer.parseInt(split[1]);

        //如果当前时间小于定时任务配置的时间,在当天开启结算任务
        if (hour < h || (hour == h && m - minute > BreakGameConstants.THRESHOLD)) {
            cronExp = cron.replace("yyyy", time.getYear() + "")
                    .replace("MM", time.getMonthOfYear() + "")
                    .replace("dd", time.getDayOfMonth() + "")
                    .replace("HH", h + "")
                    .replace("mm", m + "")
                    .replace("ss", "0");
        } else {
            //否则第二天结算
            cronExp = cron.replace("yyyy", plusDays.getYear() + "")
                    .replace("MM", plusDays.getMonthOfYear() + "")
                    .replace("dd", plusDays.getDayOfMonth() + "")
                    .replace("HH", h + "")
                    .replace("mm", m + "")
                    .replace("ss", "0");
        }

        return cronExp;
    }

    @Override
    public void robotMacthTask(GameBreakUserRecordsVo userRecordsVo, int time) {
        removeGameJob(SystemConstant.TASK_GROUP_BREAK_GAME_ROBOT_MATCH, userRecordsVo.getGameNo() + userRecordsVo.getUserId());
        log.info("设置闯关弹球游戏--" + userRecordsVo.getNickName() + " -匹配机器人任务");
        ScheduleJob job = new ScheduleJob();
        job.setClazzName(null);
        job.setIsConcurrent(false);
        String cronExp = DateUtil.formatDate(DateUtils.addMilliseconds(new Date(), time), DateUtil.CRON_FORMAT);
        job.setCronExpression(cronExp);
        job.setDescription("弹球游戏匹配机器人任务");
        job.setIsSpringbean(true);
        job.setJobGroup(SystemConstant.TASK_GROUP_BREAK_GAME_ROBOT_MATCH);
        job.setJobName(userRecordsVo.getGameNo() + userRecordsVo.getUserId());
        job.setJobstatus(SystemConstant.TASK_STATUS_READY);
        job.setTargetMethod("robotMatch");
        job.setTargetObject("gameBreakService");
        job.setParam(userRecordsVo);
        quartzService.addJob(job);
    }

    @Override
    public void robotDeadTask(GameBreakUserRecordsVo userRecordsVo, GameBreakUserRecordsVo ruserRecordsVo, int time) {
        removeGameJob(SystemConstant.TASK_GROUP_BREAK_GAME_ROBOT_DEAD, userRecordsVo.getGameNo()  + "_" + userRecordsVo.getUserId());
        log.info("设置闯关弹球游戏--" + userRecordsVo.getNickName() + " -机器人死亡任务");
        ScheduleJob job = new ScheduleJob();
        job.setClazzName(null);
        job.setIsConcurrent(false);
        String cronExp = DateUtil.formatDate(DateUtils.addSeconds(new Date(), time), DateUtil.CRON_FORMAT);
        job.setCronExpression(cronExp);
        job.setDescription("弹球游戏机器人死亡任务");
        job.setIsSpringbean(true);
        job.setJobGroup(SystemConstant.TASK_GROUP_BREAK_GAME_ROBOT_DEAD);
        job.setJobName(userRecordsVo.getGameNo()  + "_" + userRecordsVo.getUserId());
        job.setJobstatus(SystemConstant.TASK_STATUS_READY);
        job.setTargetMethod("robotDead");
        job.setTargetObject("gameBreakService");
        Map map = new HashMap();
        map.put("userRecordsVo", userRecordsVo);
        map.put("robotRecordsVo", ruserRecordsVo);
        job.setParam(map);
        quartzService.addJob(job);
    }
}
