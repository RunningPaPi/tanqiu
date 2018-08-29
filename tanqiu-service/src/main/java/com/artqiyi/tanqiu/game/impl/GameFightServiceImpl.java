package com.artqiyi.tanqiu.game.impl;

import com.artqiyi.tanqiu.common.constant.GameConstants;
import com.artqiyi.tanqiu.common.constant.RedisFiledConstant;
import com.artqiyi.tanqiu.common.constant.SystemConstant;
import com.artqiyi.tanqiu.common.socket.SocketConstant;
import com.artqiyi.tanqiu.common.socket.SocketResponseMsg;
import com.artqiyi.tanqiu.common.util.DateUtil;
import com.artqiyi.tanqiu.common.util.JSONUtil;
import com.artqiyi.tanqiu.game.IGameConfigService;
import com.artqiyi.tanqiu.game.IGameFightService;
import com.artqiyi.tanqiu.game.IGameJobService;
import com.artqiyi.tanqiu.game.IGameRedpacketRecordsService;
import com.artqiyi.tanqiu.game.domain.GameConfig;
import com.artqiyi.tanqiu.game.vo.FightResultVo;
import com.artqiyi.tanqiu.game.vo.GameDataVo;
import com.artqiyi.tanqiu.game.vo.GameFightUserRecordsVo;
import com.artqiyi.tanqiu.game.vo.LayerVo;
import com.artqiyi.tanqiu.redis.RedisLock;
import com.artqiyi.tanqiu.redis.RedisService;
import com.artqiyi.tanqiu.user.domain.User;
import com.artqiyi.tanqiu.user.domain.UserInfo;
import com.artqiyi.tanqiu.user.service.IUserInfoService;
import com.artqiyi.tanqiu.user.service.IUserService;
import com.artqiyi.tanqiu.websocket.service.WebSocketHandlerService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: tanqiu
 * Author: chencunjun  <1078027943@qq.com>
 * Create On: 2018/7/4
 * Modify On: 2018/7/4 by chencunjun
 */

/**
 * 好友对战模式
 */
@Service
public class GameFightServiceImpl implements IGameFightService{
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RedisService redisService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private WebSocketHandlerService webSocketHandlerService;
    @Autowired
    private IGameJobService gameJobService;
    @Autowired
    private IGameRedpacketRecordsService gameRedpacketRecordsService;
    @Autowired
    private IGameConfigService gameConfigService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisLock redisLock;
    /**
     * 开房间邀请好友对战(http接口)
     * @param userId 用户ID
     */
    @Override
    public String newRoom(long userId) {
        String roomNo= "tq_fight_room_"+DateUtil.formatDate(new Date(),DateUtil.DATESEC_FORMAT_NO_DASH)+"_"+userId;//房间号
        GameFightUserRecordsVo gameFightUserRecordsVo=new GameFightUserRecordsVo();
        gameFightUserRecordsVo.setRoomNo(roomNo);
        gameFightUserRecordsVo.setCreateTime(new Date());
        gameFightUserRecordsVo.setUpdateTime(new Date());
        gameFightUserRecordsVo.setUserId(userId);
        gameFightUserRecordsVo.setScore(0);
        User user = userService.selectById(userId);
        if (user != null) {
            UserInfo userInfo = userInfoService.selectByUserId(userId);
            gameFightUserRecordsVo.setHeadUrl(userInfo.getHeadPicUrl());
            gameFightUserRecordsVo.setNickName(user.getNickName());
            gameFightUserRecordsVo.setGender(userInfo.getGender());
        }
        redisService.setHashObject(roomNo,String.valueOf(userId),JSONUtil.toJson(gameFightUserRecordsVo),-1);
        redisTemplate.expire(roomNo, 1, TimeUnit.DAYS);//一天后过期
        logger.info("【房间创建】gameNo为{}", roomNo);
        return roomNo;
        //推送创建房间成功后的消息
      //  sendMessage(gameFightUserRecordsVo,roomNo,userId, SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_ENTER_ROOM);
    }

    /**
     * 加入房间
     * @param userId 用户id
     * @param roomNo 房间编码
     */
    @Override
    public void enterRoom(long userId, String roomNo) {
        //获取本赛场场次所有用户对战记录
        String recordListJson = String.valueOf(redisService.getHashObject(roomNo));
        if (StringUtils.isEmpty(recordListJson)) {
            logger.error("【游戏正常】该游戏房间不存在！roomNo={}", roomNo);
            Map resultMap=new HashMap<>();
            resultMap.put("failCode",1);
            resultMap.put("failReason","该房间已解散");
            sendMessage(resultMap,roomNo,userId, SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_ENTER_ROOM_FAIL);
            return;
        }
        List<GameFightUserRecordsVo> recordsList = JSONUtil.toBeanList(recordListJson, GameFightUserRecordsVo.class);
        if (recordsList.size() == 0) {
            logger.error("【游戏正常】该游戏房间不存在！roomNo={}", roomNo);
            Map resultMap=new HashMap<>();
            resultMap.put("failCode",1);
            resultMap.put("failReason","该房间已解散");
            sendMessage(resultMap,roomNo,userId, SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_ENTER_ROOM_FAIL);
            return;
        }
        if(recordsList.size()>1){
            logger.error("【游戏正常】该游戏房间已满人！roomNo={}", roomNo);
            Map resultMap=new HashMap<>();
            resultMap.put("failCode",2);
            resultMap.put("failReason","该房间已满人");
            sendMessage(resultMap,roomNo,userId, SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_ENTER_ROOM_FAIL);
            return;
        }
        if (recordsList.get(0).getUserId() == userId) {
            logger.error("【游戏正常】该游戏房间创建者是自己！roomNo={}", roomNo);
            Map resultMap=new HashMap<>();
            resultMap.put("failCode",3);
            resultMap.put("failReason","自己不能和自己对战");
            sendMessage(resultMap,roomNo,userId, SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_ENTER_ROOM_FAIL);
            return;
        }
        //并发控制人数
        Long size = redisTemplate.opsForValue().increment(RedisFiledConstant.FIGHT_ENTER_ROON_CONCURRENT_CONTROLL+"_"+roomNo,1);
        if (size>1) {
            logger.error("【游戏正常】该游戏房间已满人！roomNo={}", roomNo);
            Map resultMap=new HashMap<>();
            resultMap.put("failCode",2);
            resultMap.put("failReason","该房间已满人");
            sendMessage(resultMap,roomNo,userId, SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_ENTER_ROOM_FAIL);
            return;
        }else{
            redisTemplate.expire(RedisFiledConstant.FIGHT_ENTER_ROON_CONCURRENT_CONTROLL + "_" + roomNo, 1, TimeUnit.DAYS);
        }
        List<GameConfig> gameTimeConfigs = gameConfigService.getConfigsByModelKeyAndCode(GameConstants.TANQIU_GAME_MODEL_KEY_FIGHT, GameConstants.TANQIU_GAME_CONFIG_CODE_GAME_DURING_TIME);
        int gameTime;
        if (!CollectionUtils.isEmpty(gameTimeConfigs)) {
            gameTime = Integer.valueOf(gameTimeConfigs.get(0).getTypeValue());
        }else{
            gameTime = 60;
        }
        List<List<LayerVo>> data = gameRedpacketRecordsService.generateDataForFight(1, GameConstants.TANQIU_GAME_MODEL_KEY_FIGHT);//比赛渲染数据
        GameFightUserRecordsVo gameFightUserRecordsVoA=recordsList.get(0);
        //创建用户比赛记录
        GameFightUserRecordsVo gameFightUserRecordsVoB=new GameFightUserRecordsVo();
        gameFightUserRecordsVoB.setRoomNo(roomNo);
        gameFightUserRecordsVoB.setCreateTime(new Date());
        gameFightUserRecordsVoB.setUpdateTime(new Date());
        gameFightUserRecordsVoB.setUserId(userId);
        gameFightUserRecordsVoB.setScore(0);
        gameFightUserRecordsVoB.setAgainstId(gameFightUserRecordsVoA.getUserId());
        gameFightUserRecordsVoB.setAgaistHeadUrl(gameFightUserRecordsVoA.getHeadUrl());
        gameFightUserRecordsVoB.setAgaistGender(gameFightUserRecordsVoA.getGender());
        gameFightUserRecordsVoB.setAgaistNickName(gameFightUserRecordsVoA.getNickName());
        gameFightUserRecordsVoB.setData(data);
        gameFightUserRecordsVoB.setAgaistData(data);
        gameFightUserRecordsVoB.setGameTime(gameTime);
        User user = userService.selectById(userId);
        if (user != null) {
            UserInfo userInfo = userInfoService.selectByUserId(userId);
            gameFightUserRecordsVoB.setHeadUrl(userInfo.getHeadPicUrl());
            gameFightUserRecordsVoB.setNickName(user.getNickName());
            gameFightUserRecordsVoB.setGender(userInfo.getGender());
        }
        redisService.setHashObject(roomNo,String.valueOf(gameFightUserRecordsVoB.getUserId()),JSONUtil.toJson(gameFightUserRecordsVoB),-1);
        //推送比赛开始消息
        sendMessage(gameFightUserRecordsVoB,roomNo,gameFightUserRecordsVoB.getUserId(), SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_BEGIN);

        gameFightUserRecordsVoA.setData(data);
        gameFightUserRecordsVoA.setAgaistData(data);
        gameFightUserRecordsVoA.setAgaistHeadUrl(gameFightUserRecordsVoB.getHeadUrl());
        gameFightUserRecordsVoA.setAgaistGender(gameFightUserRecordsVoB.getGender());
        gameFightUserRecordsVoA.setAgainstId(gameFightUserRecordsVoB.getUserId());
        gameFightUserRecordsVoA.setAgaistNickName(gameFightUserRecordsVoB.getNickName());
        gameFightUserRecordsVoA.setGameTime(gameTime);
        redisService.setHashObject(roomNo,String.valueOf(gameFightUserRecordsVoA.getUserId()),JSONUtil.toJson(gameFightUserRecordsVoA),-1);
        //推送比赛开始消息
        sendMessage(gameFightUserRecordsVoA,roomNo,gameFightUserRecordsVoA.getUserId(), SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_BEGIN);
        //开启定时阶段任务
        gameJobService.fightGameEndPkTask(gameFightUserRecordsVoA);
        //添加超时任务
        gameJobService.fightGameOvertimeTask(gameFightUserRecordsVoA);
        gameJobService.fightGameOvertimeTask(gameFightUserRecordsVoB);

    }

    /**
     * 放弃比赛
     * @param userId 用户id
     * @param roomNo 房间编码
     */
    @Override
    public void giveUp(long userId, String roomNo) {
        //获取当前用户对战记录
        String recordJsonStr = (String) redisService.getHashObject(roomNo,String.valueOf(userId));
        if (StringUtils.isEmpty(recordJsonStr)) {
            logger.error("【游戏正常】该游戏用户记录不存在！userId={}", userId);
            return;
        }
        GameFightUserRecordsVo currentGameFightUserRecordsVo=JSONUtil.toBean(recordJsonStr, GameFightUserRecordsVo.class);
        //获取对手用户对战记录
        String againstRecordJsonStr = (String) redisService.getHashObject(roomNo,String.valueOf(currentGameFightUserRecordsVo.getAgainstId()));
        if (StringUtils.isEmpty(againstRecordJsonStr)) {
            logger.error("【游戏正常】该游戏用户记录不存在！userId={}", currentGameFightUserRecordsVo.getAgainstId());
            return;
        }
        GameFightUserRecordsVo againtGameFightUserRecordsVo=JSONUtil.toBean(againstRecordJsonStr, GameFightUserRecordsVo.class);

        win(againtGameFightUserRecordsVo,currentGameFightUserRecordsVo,SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_GIVE_UP);
    }

    /**
     * 表情
     * @param userId 当前用户id
     * @param roomNo 房间编码
     */
    @Override
    public void emoticon(long userId, String roomNo,String emoticon) {
        //获取当前用户对战记录
        String recordJsonStr = (String) redisService.getHashObject(roomNo,String.valueOf(userId));
        if (StringUtils.isEmpty(recordJsonStr)) {
            logger.error("【游戏正常】该游戏用户记录不存在！userId={}", userId);
            return;
        }
        GameFightUserRecordsVo gameFightUserRecordsVo=JSONUtil.toBean(recordJsonStr, GameFightUserRecordsVo.class);
        //推送消息
        sendMessage(emoticon,roomNo,gameFightUserRecordsVo.getAgainstId(), SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_EMOTICON);
    }

    /**
     * 比赛数据传输
     * @param userId
     * @param roomNo
     * @param data
     * @param score
     */
    @Override
    public void gameData(long userId, String roomNo,String data,int score) {
        //获取当前用户对战记录
        String recordJsonStr = (String) redisService.getHashObject(roomNo,String.valueOf(userId));
        if (StringUtils.isEmpty(recordJsonStr)) {
            logger.error("【游戏正常】该游戏用户记录不存在！userId={}", userId);
            return;
        }
        GameFightUserRecordsVo gameFightUserRecordsVo=JSONUtil.toBean(recordJsonStr, GameFightUserRecordsVo.class);
        gameFightUserRecordsVo.setScore(score);
        gameFightUserRecordsVo.setUpdateTime(new Date());
        redisService.setHashObject(roomNo,String.valueOf(gameFightUserRecordsVo.getUserId()),JSONUtil.toJson(gameFightUserRecordsVo),-1);
        GameDataVo gameDataVo=new GameDataVo();
        gameDataVo.setAgainstScore(score);
        gameDataVo.setAgainstGameData(data);
        //推送消息给对手
        sendMessage(gameDataVo,roomNo,gameFightUserRecordsVo.getAgainstId(),SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_DATA);
        //移除计数器 断线重连用
        redisService.removeHashObject(roomNo,userId+"overTimes");
    }

    /**
     * 比赛死亡
     * @param userId
     * @param roomNo
     */
    @Override
    public void death(long userId, String roomNo) {
        //获取当前用户对战记录
        String recordJsonStr = (String) redisService.getHashObject(roomNo,String.valueOf(userId));
        if (StringUtils.isEmpty(recordJsonStr)) {
            logger.error("【游戏正常】该游戏用户记录不存在！userId={}", userId);
            return;
        }
        GameFightUserRecordsVo currentGameFightUserRecordsVo=JSONUtil.toBean(recordJsonStr, GameFightUserRecordsVo.class);
        //获取对手用户对战记录
        String againstRecordJsonStr = (String) redisService.getHashObject(roomNo,String.valueOf(currentGameFightUserRecordsVo.getAgainstId()));
        if (StringUtils.isEmpty(againstRecordJsonStr)) {
            logger.error("【游戏正常】该游戏用户记录不存在！userId={}", currentGameFightUserRecordsVo.getAgainstId());
            return;
        }
        GameFightUserRecordsVo againtGameFightUserRecordsVo=JSONUtil.toBean(againstRecordJsonStr, GameFightUserRecordsVo.class);

        win(againtGameFightUserRecordsVo,currentGameFightUserRecordsVo,SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_DEATH);
    }

    /**
     * 倒计时内完成比赛
     *
     * @param userId
     * @param roomNo
     */
    @Override
    public void complete(long userId, String roomNo, String data, int score) {
        //redis分布式锁 加锁
        String requestId = UUID.randomUUID().toString();
        int expireTime = 30000;//过期时间毫秒数 30秒
        while (!redisLock.tryGetDistributedLock(RedisFiledConstant.FIGHT_GAME_COMPLETE_LOCK+"_"+roomNo, requestId, expireTime)){
            try {
                int time = RandomUtils.nextInt(100, 500);
                Thread.sleep(Long.valueOf(time+""));
            } catch (Exception e) {
                logger.error("【对战游戏】倒计时内完成比赛获取锁异常Exception={}",e);
            }
        }

        //获取当前用户对战记录
        String recordJsonStr = (String) redisService.getHashObject(roomNo, String.valueOf(userId));
        if (StringUtils.isEmpty(recordJsonStr)) {
            logger.error("【游戏正常】该游戏用户记录不存在！userId={}", userId);
            return;
        }
        GameFightUserRecordsVo currentGameFightUserRecordsVo = JSONUtil.toBean(recordJsonStr, GameFightUserRecordsVo.class);
        //获取对手用户对战记录
        String againstRecordJsonStr = (String) redisService.getHashObject(roomNo, String.valueOf(currentGameFightUserRecordsVo.getAgainstId()));
        if (StringUtils.isEmpty(againstRecordJsonStr)) {
            logger.error("【游戏正常】该游戏用户记录不存在！userId={}", currentGameFightUserRecordsVo.getAgainstId());
            return;
        }
        GameFightUserRecordsVo againtGameFightUserRecordsVo = JSONUtil.toBean(againstRecordJsonStr, GameFightUserRecordsVo.class);
        if (!againtGameFightUserRecordsVo.isComplete()) {
            currentGameFightUserRecordsVo.setScore(score);
            currentGameFightUserRecordsVo.setComplete(true);
            currentGameFightUserRecordsVo.setUpdateTime(new Date());
            redisService.setHashObject(roomNo,String.valueOf(currentGameFightUserRecordsVo.getUserId()),JSONUtil.toJson(currentGameFightUserRecordsVo),-1);
            //给当前玩家推送等待好友完成比赛
            sendMessage(null,roomNo,currentGameFightUserRecordsVo.getUserId(),SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_WAIT_FOR_END);
            //给好友推送分数
            GameDataVo gameDataVo=new GameDataVo();
            gameDataVo.setAgainstScore(score);
            gameDataVo.setAgainstGameData(data);
            sendMessage(gameDataVo,roomNo,againtGameFightUserRecordsVo.getUserId(),SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_DATA);
        } else {
            currentGameFightUserRecordsVo.setScore(score);
            currentGameFightUserRecordsVo.setComplete(true);
            currentGameFightUserRecordsVo.setUpdateTime(new Date());
            winOrLose(currentGameFightUserRecordsVo, againtGameFightUserRecordsVo);
        }

        //释放锁
        redisLock.releaseDistributedLock(RedisFiledConstant.FIGHT_GAME_COMPLETE_LOCK+"_"+roomNo, requestId);
    }

    private void winOrLose(GameFightUserRecordsVo currentGameFightUserRecordsVo, GameFightUserRecordsVo againtGameFightUserRecordsVo) {
        //结算规则：1.分数高者获胜 2.分数相同者，先达到该分数者获胜 3.两者都不得分，先进入游戏者获胜
        if (currentGameFightUserRecordsVo.getScore() > againtGameFightUserRecordsVo.getScore()) {
            win(currentGameFightUserRecordsVo, againtGameFightUserRecordsVo, SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_RESULT);
        } else if (currentGameFightUserRecordsVo.getScore() == againtGameFightUserRecordsVo.getScore()) {
            if (currentGameFightUserRecordsVo.getUpdateTime().getTime() >= againtGameFightUserRecordsVo.getUpdateTime().getTime()) {
                win(againtGameFightUserRecordsVo, currentGameFightUserRecordsVo, SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_RESULT);
            } else {
                win(currentGameFightUserRecordsVo, currentGameFightUserRecordsVo, SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_RESULT);
            }
        } else {
            win(againtGameFightUserRecordsVo, currentGameFightUserRecordsVo, SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_RESULT);
        }
    }

    /**
     * 再来一局  由于结算已经把房间销毁了，但是socket未断开，通知只能通过相同的房间号来设置，不然就需要
     * @TODO
     * @param userId
     * @param roomNo
     */
    @Override
    public void again(long userId,long againstId, String roomNo,String data) {
        boolean isAgainstSessionOpen = webSocketHandlerService.isUserSessionOpen(roomNo, "user" + againstId);
        if (!isAgainstSessionOpen) {
            sendMessage("对手已离开房间",roomNo,userId,SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_LEAVE);
            return ;
        }
        //获取该房间是否有玩家，无则创建房间，有则加入房间进行匹配开始游戏
        String recordListJson = String.valueOf(redisService.getHashObject(roomNo));
        if (StringUtils.isEmpty(recordListJson) || "[]".equals(recordListJson)) {
            //重新创建房间(房间号不变)
            GameFightUserRecordsVo gameFightUserRecordsVo=new GameFightUserRecordsVo();
            gameFightUserRecordsVo.setRoomNo(roomNo);
            gameFightUserRecordsVo.setCreateTime(new Date());
            gameFightUserRecordsVo.setUpdateTime(new Date());
            gameFightUserRecordsVo.setUserId(userId);
            gameFightUserRecordsVo.setScore(0);
            User user = userService.selectById(userId);
            if (user != null) {
                UserInfo userInfo = userInfoService.selectByUserId(userId);
                gameFightUserRecordsVo.setHeadUrl(userInfo.getHeadPicUrl());
                gameFightUserRecordsVo.setNickName(user.getNickName());
                gameFightUserRecordsVo.setGender(userInfo.getGender());
            }
            redisService.setHashObject(roomNo,String.valueOf(userId),JSONUtil.toJson(gameFightUserRecordsVo),-1);
            redisTemplate.expire(roomNo, 1, TimeUnit.DAYS);//一天后过期
            Map map=new HashMap();
            map.put("msg",data);
            sendMessage(map,roomNo,againstId,SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_AGAIN);
        }else{
            //如果是再来一局，的应战，删除key，使得该好友可以进来，其他好友还是不能抢到位置
            redisTemplate.delete(RedisFiledConstant.FIGHT_ENTER_ROON_CONCURRENT_CONTROLL+"_"+roomNo);
            enterRoom(userId,roomNo);
        }
    }


    /**
     * 玩家离开房间  从而按钮从“再来一局”到“邀请好友”
     * @param userId
     * @param roomNo
     */
    @Override
    public void leave(long userId,long againstId, String roomNo) {
        //移除记录 移除的是个人的记录，可能对手已经点击再来一局，创建好了房间（但是对于重新邀请好友是不同房间号的情况，就可以直接将房间销毁）
        redisService.removeObject(roomNo);
        //推送通知对方已离开房间
        sendMessage("对手已离开房间",roomNo,againstId,SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_LEAVE);
    }

    /**
     * 退出等待             房主开始创建后，没人进来，退出等待
     * @param userId
     * @param roomNo
     */
    @Override
    public void quit(long userId, String roomNo) {
        //获取当前用户对战记录
        String recordJsonStr = (String) redisService.getHashObject(roomNo,String.valueOf(userId));
        if (StringUtils.isEmpty(recordJsonStr)) {
            logger.error("【游戏正常】该游戏用户记录不存在！userId={}", userId);
            return;
        }
        //销毁房间
        redisService.removeObject(roomNo);
    }

    /**
     * 赢得比赛PK
     * @param winPlayer 赢家记录
     * @param failPlayer 输家记录
     */
    @Override
    public void win(GameFightUserRecordsVo winPlayer, GameFightUserRecordsVo failPlayer,String msgCode) {
        //移除结算任务
        gameJobService.removeGameJob(SystemConstant.TASK_GROUP_FIGHT_GAME_PK,winPlayer.getRoomNo());
        //移除超时监测任务
        gameJobService.removeGameJob(SystemConstant.TASK_GROUP_FIGHT_GAME_OVERTIME,winPlayer.getRoomNo()+"_"+winPlayer.getUserId());
        gameJobService.removeGameJob(SystemConstant.TASK_GROUP_FIGHT_GAME_OVERTIME,failPlayer.getRoomNo()+"_"+failPlayer.getUserId());
        //赢家消息封装推送
        FightResultVo winFightResultVo=new FightResultVo();
        winFightResultVo.setScore(winPlayer.getScore());
        winFightResultVo.setAgainstScore(failPlayer.getScore());
        winFightResultVo.setWin(true);
        sendMessage(winFightResultVo,winPlayer.getRoomNo(),winPlayer.getUserId(),msgCode);
        //输家消息封装推送
        FightResultVo failFightResultVo=new FightResultVo();
        failFightResultVo.setScore(failPlayer.getScore());
        failFightResultVo.setAgainstScore(winPlayer.getScore());
        failFightResultVo.setWin(false);
        sendMessage(failFightResultVo,failPlayer.getRoomNo(),failPlayer.getUserId(),msgCode);
        //销毁房间
        redisService.removeObject(winPlayer.getRoomNo());
    }

    /**
     * 结算对战PK任务
     * @param map
     */
    @Override
    public void endFightPkTask(Map map) {
        long userId= MapUtils.getLongValue(map,"userId");
        String roomNo= MapUtils.getString(map,"roomNo");
        //获取当前用户对战记录
        String recordJsonStr = (String) redisService.getHashObject(roomNo,String.valueOf(userId));
        if (StringUtils.isEmpty(recordJsonStr)) {
            logger.error("【游戏正常】该游戏用户记录不存在！userId={}", userId);
            return;
        }
        GameFightUserRecordsVo currentGameFightUserRecordsVo=JSONUtil.toBean(recordJsonStr, GameFightUserRecordsVo.class);
        //获取对手用户对战记录
        String againstRecordJsonStr = (String) redisService.getHashObject(roomNo,String.valueOf(currentGameFightUserRecordsVo.getAgainstId()));
        if (StringUtils.isEmpty(againstRecordJsonStr)) {
            logger.error("【游戏正常】该游戏用户记录不存在！userId={}", currentGameFightUserRecordsVo.getAgainstId());
            return;
        }
        GameFightUserRecordsVo againtGameFightUserRecordsVo=JSONUtil.toBean(againstRecordJsonStr, GameFightUserRecordsVo.class);
        //结算规则：1.分数高者获胜 2.分数相同者，先达到该分数者获胜 3.两者都不得分，先进入游戏者获胜
        winOrLose(currentGameFightUserRecordsVo, againtGameFightUserRecordsVo);
    }

    /**
     * 超时监测任务
     * @param map
     */
    @Override
    public void overtimeTask(Map map) {
        long userId= MapUtils.getLongValue(map,"userId");
        String roomNo= MapUtils.getString(map,"roomNo");
        //获取当前用户对战记录
        String recordJsonStr = (String) redisService.getHashObject(roomNo,String.valueOf(userId));
        if (StringUtils.isEmpty(recordJsonStr)) {
            logger.error("【游戏正常】该游戏用户记录不存在！userId={}", userId);
            return;
        }
        GameFightUserRecordsVo currentGameFightUserRecordsVo=JSONUtil.toBean(recordJsonStr, GameFightUserRecordsVo.class);
        //获取对手用户对战记录
        String againstRecordJsonStr = (String) redisService.getHashObject(roomNo,String.valueOf(currentGameFightUserRecordsVo.getAgainstId()));
        if (StringUtils.isEmpty(againstRecordJsonStr)) {
            logger.error("【游戏正常】该游戏用户记录不存在！userId={}", currentGameFightUserRecordsVo.getAgainstId());
            return;
        }
        GameFightUserRecordsVo againtGameFightUserRecordsVo=JSONUtil.toBean(againstRecordJsonStr, GameFightUserRecordsVo.class);
        win(againtGameFightUserRecordsVo,currentGameFightUserRecordsVo,SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_OVERTIME);
        //断线重连用
        /*logger.info("【用户{}失去链接】","user"+userId);
        try {
            //关闭链接，以便客户端再次进行链接
            webSocketHandlerService.close(roomNo,"user"+userId);
            Integer overTimes = 0;
            //5秒后查看，如果客户端链接成功,重新发送心跳，将计数器移除
            while (overTimes != null) {
                overTimes++;
                if (overTimes > 3) {
                    //认为超时作弊
                    GameFightUserRecordsVo againtGameFightUserRecordsVo=JSONUtil.toBean(againstRecordJsonStr, GameFightUserRecordsVo.class);
                    win(againtGameFightUserRecordsVo,currentGameFightUserRecordsVo,SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_OVERTIME);
                    //移除计数器
                    redisService.removeHashObject(roomNo,userId+"overTimes");
                    return;
                }
                redisService.setHashObject(roomNo,userId+"overTimes",overTimes,-1);
                Thread.sleep(7*1000);
                logger.info("上次overtimes为{}，重新检测用户{}是否连接上",overTimes,"user"+userId);
                overTimes = (Integer) redisService.getHashObject(roomNo, userId+"overTimes");
                logger.info("重新获取到的overtimes为{}",overTimes);
            }
        } catch (Exception e) {
            logger.error("【异常】",e);
        }*/
    }

    /**
     * 心跳 非游戏中，用于保活链接
     * @param userId
     * @param roomNo
     */
    @Override
    public void heartBeat(Long userId, String roomNo) {
        //如果nginx反向代理的服务器没有发出任何数据，链接将会被关闭，默认一分钟
        if (userId != null && roomNo != null) {
            sendMessage(null,roomNo,userId,SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_HEART_BEAT);
        }
    }

    /**
     * 心跳 游戏中，用于重连与作弊
     * @param userId
     * @param roomNo
     */
    @Override
    public void heartBeatDuringCompetetion(Long userId, String roomNo) {
        //如果nginx反向代理的服务器没有发出任何数据，链接将会被关闭，默认一分钟
        if (userId != null && roomNo != null) {
            sendMessage(null,roomNo,userId,SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_HEART_BEAT_COMPETE);
        }
        //获取当前用户对战记录
        String recordJsonStr = (String) redisService.getHashObject(roomNo,String.valueOf(userId));
        if (StringUtils.isEmpty(recordJsonStr)) {
            logger.error("【游戏正常】该游戏用户记录不存在！userId={}", userId);
            return;
        }
        GameFightUserRecordsVo gameFightUserRecordsVo=JSONUtil.toBean(recordJsonStr, GameFightUserRecordsVo.class);
        //添加超时任务 如果时间内没有接收到消息，计数器加1，则每隔一定时间检查计数器，计数器达到3，则认为作弊，判负
        gameJobService.fightGameOvertimeTask(gameFightUserRecordsVo);
        //收到消息，将计数器删除  断线重连用
        //redisService.removeHashObject(roomNo,userId+"overTimes");
    }

    /**
     * 推送消息
     */
    public void sendMessage(Object data, String group, long userId, String msgCode) {
        SocketResponseMsg msg = new SocketResponseMsg();
        msg.setCode(msgCode);
        msg.setSuccess(true);
        msg.setResult(data);
        webSocketHandlerService.sendMessageToUser(group, "user" + userId, new TextMessage(JSONUtil.toJson(msg)));
        //如果推送结果时，玩家处于掉线状态  断线重连用
//        if (!webSocketHandlerService.isUserSessionOpen(group,"user"+userId)) {
//            if (msgCode.equals(SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_RESULT)
//                    || msgCode.equals(SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_GIVE_UP)
//                    || msgCode.equals(SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_DEATH)
//                    || msgCode.equals(SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_OVERTIME)) {
//                //设置到redis中
//                logger.info("【用户{}】有未发送消息{}",userId,msg);
//                Map<String, Object> unsentMessage = new HashMap<>();
//                unsentMessage.put("data", data);
//                unsentMessage.put("msgCode", msgCode);
//                redisService.setObject(RedisFiledConstant.FIGHT_MODEL_UNSENT_MESSAGE+"_"+group+"_"+"user"+userId, JSONUtil.toJson(unsentMessage));
//                redisTemplate.expire(RedisFiledConstant.FIGHT_MODEL_UNSENT_MESSAGE+"_"+group+"_"+"user"+userId, 200, TimeUnit.SECONDS);
//            }
//        }
    }

    /**
     * 随机获取提示文案
     * @return
     */
    public String getText(){
        String[] templetText=new String[]{"来战来战~","不要走，决战到天亮！","我准备好啦，快点进来~","再来一局啊~"};
        Random rand = new Random();
        int randomNum=rand.nextInt(4);
        return templetText[randomNum];
    }

}
