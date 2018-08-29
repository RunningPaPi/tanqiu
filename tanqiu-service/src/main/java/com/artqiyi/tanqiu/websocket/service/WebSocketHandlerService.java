package com.artqiyi.tanqiu.websocket.service;


import com.artqiyi.tanqiu.common.constant.RedisFiledConstant;
import com.artqiyi.tanqiu.common.constant.SystemConstant;
import com.artqiyi.tanqiu.common.socket.SocketConstant;
import com.artqiyi.tanqiu.common.socket.SocketRequestMsg;
import com.artqiyi.tanqiu.game.IGameBreakService;
import com.artqiyi.tanqiu.game.IGameFightService;
import com.artqiyi.tanqiu.game.domain.GameBreakRecords;
import com.artqiyi.tanqiu.game.vo.GameBreakUserRecordsVo;
import com.artqiyi.tanqiu.job.domain.ScheduleJob;
import com.artqiyi.tanqiu.redis.RedisClient;
import com.artqiyi.tanqiu.redis.RedisService;
import com.artqiyi.tanqiu.service.IQuartzService;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.EOFException;
import java.util.Map;

/**
 * webSocket业务服务类
 */
@Component
public class WebSocketHandlerService extends TextWebSocketHandler {
    private static Logger logger = LoggerFactory.getLogger(WebSocketHandlerService.class);
    @Autowired
    private IQuartzService quartzService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private IGameBreakService gameBreakService;
    @Autowired
    private IGameFightService gameFightService;
    @Autowired
    private RedisClient redisClient;

    private WebSocketSessionService sessionService = WebSocketSessionService.INSTANCE;

    /**
     * 拦截客户端每次请求进行业务处理
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String username = (String) session.getAttributes().get("WEBSOCKET_USERNAME");
        // 获取提交过来的消息详情
        logger.info("收到用户 " + username + "的消息:" + message.getPayload()+" sessionId:"+session.getId());

        //业务处理
        String payload = message.getPayload();
        if(StringUtils.isNotBlank(payload)){
            SocketRequestMsg requestMsg = new SocketRequestMsg(payload);
            String code = requestMsg.getCode();
            Map paramMap=requestMsg.getParamMap();
            switch (code) {
                /*=============================弹球闯关游戏socket相关操作=========================================*/
                case SocketConstant.SOCKET_OPERATE_SEND_BREAK_GAME_BEGIN : //闯关游戏开始
                    gameBreakService.startGame(MapUtils.getLong(paramMap,"userId"),MapUtils.getLong(paramMap,"gameId"),MapUtils.getString(paramMap,"gameNo"));
                    break;
                case SocketConstant.SOCKET_OPERATE_SEND_BREAK_GAME_CONTINUE : //继续闯关
                    gameBreakService.continueGame(MapUtils.getLong(paramMap,"userId"),MapUtils.getLong(paramMap,"gameId"),MapUtils.getString(paramMap,"gameNo"));
                    break;
                case SocketConstant.SOCKET_OPERATE_SEND_BREAK_GAME_GIVE_UP : //放弃比赛
                    gameBreakService.giveUp(MapUtils.getLong(paramMap,"userId"),MapUtils.getLong(paramMap,"againstId"),MapUtils.getString(paramMap,"gameNo"));
                    break;
                case SocketConstant.SOCKET_OPERATE_SEND_BREAK_GAME_RECOVER : //比赛复活
                    gameBreakService.recover(MapUtils.getLong(paramMap,"userId"),MapUtils.getLong(paramMap,"gameId"),MapUtils.getString(paramMap,"gameNo"),MapUtils.getIntValue(paramMap,"round"));
                    break;
                case SocketConstant.SOCKET_OPERATE_SEND_BREAK_GAME_EMO: //表情
                    gameBreakService.emoticon(MapUtils.getLong(paramMap,"userId"),MapUtils.getLong(paramMap,"againstId"),MapUtils.getString(paramMap,"gameNo"),MapUtils.getString(paramMap,"data"));
                    break;
                case SocketConstant.SOCKET_OPERATE_SEND_BREAK_GAME_DATA : //比赛数据
                    gameBreakService.gameData(MapUtils.getLong(paramMap,"userId"),MapUtils.getLong(paramMap,"againstId"),MapUtils.getString(paramMap,"gameNo"),MapUtils.getIntValue(paramMap,"score"),MapUtils.getString(paramMap,"data"));
                    break;
                case SocketConstant.SOCKET_OPERATE_SEND_BREAK_GAME_QUIT_WAIT : //退出等待
                    gameBreakService.quit(MapUtils.getLong(paramMap,"userId"),MapUtils.getLong(paramMap,"gameId"),MapUtils.getString(paramMap,"gameNo"),MapUtils.getBoolean(paramMap,"isBegin"));
                    break;
                case SocketConstant.SOCKET_OPERATE_SEND_BREAK_GAME_DEATH : //死亡
                    gameBreakService.death(MapUtils.getLong(paramMap,"userId"),MapUtils.getLong(paramMap,"againstId"),MapUtils.getString(paramMap,"gameNo"));
                    break;
                case SocketConstant.SOCKET_OPERATE_SEND_BREAK_GAME_OVER : //200秒已过
                    gameBreakService.gameOver(MapUtils.getLong(paramMap,"userId"),MapUtils.getLong(paramMap,"againstId"),MapUtils.getString(paramMap,"gameNo"));
                    break;
                     /*=============================弹球好友对战游戏socket相关操作=========================================*/
                case SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_CREATE_ROOM : //创建房间
                    gameFightService.newRoom(MapUtils.getLong(paramMap,"userId"));
                    break;
                case SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_ENTER_ROOM : //加入房间
                    gameFightService.enterRoom(MapUtils.getLong(paramMap,"userId"),MapUtils.getString(paramMap,"roomNo"));
                    break;
                case SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_GIVE_UP : //放弃比赛
                    gameFightService.giveUp(MapUtils.getLong(paramMap,"userId"),MapUtils.getString(paramMap,"roomNo"));
                    break;
                case SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_EMOTICON : //表情
                    gameFightService.emoticon(MapUtils.getLong(paramMap,"userId"),MapUtils.getString(paramMap,"roomNo"),MapUtils.getString(paramMap,"emoticon"));
                    break;
                case SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_DATA : //比赛数据
                    gameFightService.gameData(MapUtils.getLong(paramMap,"userId"),MapUtils.getString(paramMap,"roomNo"),MapUtils.getString(paramMap,"data"),MapUtils.getIntValue(paramMap,"score"));
                    break;
                case SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_QUIT_WAIT : //退出等待
                    gameFightService.quit(MapUtils.getLong(paramMap,"userId"),MapUtils.getString(paramMap,"roomNo"));
                    break;
                case SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_DEATH : //死亡
                    gameFightService.death(MapUtils.getLong(paramMap,"userId"),MapUtils.getString(paramMap,"roomNo"));
                    break;
                case SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_COMPLETE : //完成比赛  倒计时结束
                    gameFightService.complete(MapUtils.getLong(paramMap,"userId"),MapUtils.getString(paramMap,"roomNo"),MapUtils.getString(paramMap,"data"),MapUtils.getIntValue(paramMap,"score"));
                    break;
                case SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_AGAIN : //再来一局
                    gameFightService.again(MapUtils.getLong(paramMap,"userId"),MapUtils.getLong(paramMap,"againstId"),MapUtils.getString(paramMap,"roomNo"),MapUtils.getString(paramMap,"data"));
                    break;
                case SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_LEAVE : //离开
                    gameFightService.leave(MapUtils.getLong(paramMap,"userId"),MapUtils.getLong(paramMap,"againstId"),MapUtils.getString(paramMap,"roomNo"));
                    break;
                case SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_HEART_BEAT : //心跳
                    gameFightService.heartBeat(MapUtils.getLong(paramMap,"userId"),MapUtils.getString(paramMap,"roomNo"));
                    break;
                case SocketConstant.SOCKET_OPERATE_SEND_FIGHT_GAME_HEART_BEAT_COMPETE : //心跳 比赛期间
                    gameFightService.heartBeatDuringCompetetion(MapUtils.getLong(paramMap,"userId"),MapUtils.getString(paramMap,"roomNo"));
                    break;
                default:
                    logger.info("心跳： message={}", message.getPayload());
                    break;
            }
        }


        // 响应用户信息
      //  session.sendMessage(new TextMessage(JSONUtil.toJson("handleTextMessage reply msg:" + message.getPayload())));
    }

    /**
     * 当新连接建立的时候, 连接成功时候,会触发页面上onOpen方法(第一次调用的时候才调用这个方法)
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String username = String.valueOf(session.getAttributes().get("WEBSOCKET_USERNAME"));
        String groupName= String.valueOf(session.getAttributes().get("WEBSOCKET_GROUP"));
        //先关闭链接并删除之前的会话，如果有的话
        //用户会话session信息保存
        WebSocketSession oldSession = sessionService.getUser(username);
        if (oldSession != null && oldSession.isOpen()){
            oldSession.close();
        }

        if (null != groupName ){
            if (groupName.equals(sessionService.getGroup("BREAK"))) {
                heartBeat4Break(groupName);
            }
            if(sessionService.getGroup("BREAK")==null){
                GameBreakRecords records = redisClient.hGet(RedisFiledConstant.BREAK_GAME_RECORD, "2", GameBreakRecords.class);
                String gameNo = records.getGameNo();
                sessionService.addGroup("BREAK",gameNo);
                if (groupName.equals(gameNo)){
                    heartBeat4Break(groupName);
                }
            }
        }
        sessionService.addUser(username, session);
        sessionService.addGroupUser(groupName, username, session);

        logger.info("用户 " + username + " Connection Established"+" sessionId:"+session.getId());

        //如果对于好友对战模式，重连后如果有未发送消息，则发送过去 断线重连用
//        if (groupName.contains("tq_fight")) {
//            String unsentMsgStr = (String) redisService.getObject(RedisFiledConstant.FIGHT_MODEL_UNSENT_MESSAGE+"_"+groupName+"_"+username);
//            if (!StringUtils.isEmpty(unsentMsgStr)) {
//                logger.info("【用户{}重连】有未发送消息{}",username,unsentMsgStr);
//                Map unsentMsg = JSONUtil.toBean(unsentMsgStr, Map.class);
//                SocketResponseMsg msg = new SocketResponseMsg();
//                msg.setCode(MapUtils.getString(unsentMsg,"msgCode"));
//                msg.setResult(MapUtils.getObject(unsentMsg,"data"));
//                msg.setSuccess(true);
//                sendMessageToUser(groupName, username, new TextMessage(JSONUtil.toJson(msg)));
//                redisService.removeObject(RedisFiledConstant.FIGHT_MODEL_UNSENT_MESSAGE+"_"+groupName+"_"+username);
//            }
//        }
    }

    private void heartBeat4Break(String groupName) {
        if (!sessionService.getAndSetStarted(true)) {
            new Thread(() -> {
                while (true) {
                    TextMessage message = new TextMessage("{\"code\":\"ping\",\"msg\":\"areyouok\"}");
                    sendMessageToGroupUsers(groupName, message);
                    try {
                        Thread.currentThread().sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * 当连接关闭时被调用
     *
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String username = (String) session.getAttributes().get("WEBSOCKET_USERNAME");
        String groupName= (String) session.getAttributes().get("WEBSOCKET_GROUP");
        logger.info("socket用户:" + username + " Connection closed. Status: " + status+" sessionId:"+session.getId());

        if (groupName.equals(sessionService.getGroup("BREAK"))){
            logger.info("玩家掉线--");
            Long userId = Long.valueOf(username.replace("user",""));
            GameBreakUserRecordsVo recordsVo = redisClient.hGet(groupName, String.valueOf(userId), GameBreakUserRecordsVo.class);
            if (recordsVo != null && recordsVo.getAgainstId() != 0) {
                gameBreakService.giveUp(userId, recordsVo.getAgainstId(), groupName);
            }

        }

        //缓存中移除用户信息
        sessionService.removeUser(username);
        sessionService.removeGroupUser(groupName, username);
    }

    /**
     * 传输错误时调用
     *
     * @param session
     * @param exception
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (exception instanceof EOFException) {
            logger.error("【socket传输异常】{}",exception);
            return;
        }
        logger.error("【非socket传输异常】{}",exception);
        String username = (String) session.getAttributes().get("WEBSOCKET_USERNAME");
        String groupName= (String) session.getAttributes().get("WEBSOCKET_GROUP");
        if (session.isOpen()) {
            session.close();
        }
        String sessionId = session.getId();
        logger.info("socket用户: " +" sessionId:"+sessionId+ username + "连接关闭成功......");
        //缓存中移除用户信息
        sessionService.removeUser(username);
        sessionService.removeGroupUser(groupName, username);
    }

    /**
     * 给某个分组用户发信息
     * @param message
     */
    public void sendMessageToGroupUsers(String groupName,TextMessage message){
        Map<String, WebSocketSession> groupUsers = sessionService.getGroupUsers(groupName);
        if(groupUsers==null){
            return;
        }
        for (Map.Entry<String,WebSocketSession> entry : groupUsers.entrySet()) {
            WebSocketSession userSession = entry.getValue();
            try {
                if (null!=userSession && userSession.isOpen()) {
                    userSession.sendMessage(message);
                    String username = (String) userSession.getAttributes().get("WEBSOCKET_USERNAME");
                    logger.info("分组: " + groupName + " 信息发送，用户："+username+"连接正常，消息发送成功："+message.getPayload());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        Map<String,WebSocketSession> userMap = sessionService.getAllUsers();
        if(userMap==null){
            return;
        }
        for (Map.Entry<String,WebSocketSession> entry : userMap.entrySet()) {
            WebSocketSession userSession = entry.getValue();
            try {
                if (null!=userSession && userSession.isOpen()) {
                    userSession.sendMessage(message);
                    String username = (String) userSession.getAttributes().get("WEBSOCKET_USERNAME");
                    logger.info("全部在线用户信息发送，用户："+username+"连接正常，消息发送成功："+message.getPayload());
                }
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 给某个用户发送消息
     *
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        WebSocketSession userSession = sessionService.getUser(userName);
        try {
            if (null!=userSession && userSession.isOpen()) {
                userSession.sendMessage(message);
                logger.info("socket用户: " + userName + " 连接正常，消息发送成功："+message.getPayload());
            }else{
                logger.info("socket用户: " + userName + " 连接已断开，消息发送失败："+message.getPayload());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 给某个分组某个用户发送消息
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String groupName,String userName, TextMessage message) {
        WebSocketSession userSession = sessionService.getGroupUser(groupName, userName);
        try {
           if (null!=userSession && userSession.isOpen()) {
                userSession.sendMessage(message);
               String userSessionId = userSession.getId();
               logger.info("分组:"+groupName+" sessionId: "+userSessionId+" socket用户: " + userName + " 连接正常，消息发送成功："+message.getPayload());
            }else{
                logger.info("分组:"+groupName+"socket用户: " + userName + " 连接已断开，消息发送失败："+message.getPayload());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 给某个分组某个用户发送消息
     * @param data
     */
    public void sendMessageToGroupUser(Map<String,Object> data) {
        String groupName= MapUtils.getString(data,"group");
        String userName= MapUtils.getString(data,"userName");
        String msg= MapUtils.getString(data,"message");
        WebSocketSession userSession = sessionService.getGroupUser(groupName, userName);
        try {
            if (null!=userSession && userSession.isOpen()) {
                userSession.sendMessage(new TextMessage(msg));
                String userSessionId = userSession.getId();
                logger.info("分组:"+groupName+" sessionId: "+userSessionId+" socket用户: " + userName + " 连接正常，消息发送成功："+msg);
            }else{
                String userSessionId = userSession.getId();
                logger.info("分组:"+groupName+"socket用户: "+userSessionId+" socket用户: " + userName + " 连接已断开，消息发送失败："+msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 给某个分组某个用户发送消息任务
     * @param data
     */
    public void sendMessageToGroupUserTask(Map<String,Object> data){
        int maxTimes=MapUtils.getIntValue(data,"times");//最大发送次数
        String groupName= MapUtils.getString(data,"group");
        String userName= MapUtils.getString(data,"userName");
        String msg= MapUtils.getString(data,"message");
        int times=0;
        String timeStr=String.valueOf(redisService.getHashObject(RedisFiledConstant.GAME_MESSAGE_TASK,userName));
        if(StringUtils.isNotBlank(timeStr) && !"null".equals(timeStr)){
            times=Integer.parseInt(timeStr);
        }
        if(times<=maxTimes){//已推送超过10次则不再推送
            WebSocketSession userSession = sessionService.getGroupUser(groupName, userName);
            try {
                if (null!=userSession && userSession.isOpen()) {
                    userSession.sendMessage(new TextMessage(msg));
                    logger.info("分组:"+groupName+" socket用户: " + userName + " 连接正常，消息发送成功："+msg);
                }else{
                    logger.info("分组:"+groupName+"socket用户: " + userName + " 连接已断开，消息发送失败："+msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            times+=1;
            redisService.setHashObject(RedisFiledConstant.GAME_MESSAGE_TASK,userName,times,-1);
        }else{
            deleleMsgTask(userName,SystemConstant.TASK_GROUP_SEND_MESSAGE);
        }

    }

    public void deleleMsgTask(String userName,String groupName){
        ScheduleJob scheduleJob=new ScheduleJob();
        scheduleJob.setJobName("消息推送：" + userName);
        scheduleJob.setJobGroup(groupName);
        quartzService.deletJob(scheduleJob);//移除该用户推送信息
        redisService.removeHashObject(RedisFiledConstant.GAME_MESSAGE_TASK,userName);
    }



    /**
     * 断开某个用户链接
     * @param userName
     * @throws Exception
     */
    public void close(String userName) throws Exception{
        WebSocketSession userSession = sessionService.getUser(userName);
        if(userSession==null){
            return;
        }
        if(userSession.isOpen()){
            String groupName= (String) userSession.getAttributes().get("WEBSOCKET_GROUP");
            userSession.close();
            sessionService.removeUser(userName);
            sessionService.removeGroupUser(groupName, userName);
        }
    }

    /**
     * 断开某个分组下的某个用户链接
     * @param userName
     * @throws Exception
     */
    public void close(String groupName,String userName) throws Exception{
        WebSocketSession userSession = sessionService.getGroupUser(groupName, userName);
        if(userSession==null){
            return;
        }
        if(userSession.isOpen()){
            userSession.close();
            sessionService.removeUser(userName);
            sessionService.removeGroupUser(groupName, userName);
        }
    }

    /**
     * 判断某分组下某用户是否还在线
     */
    public boolean isUserSessionOpen(String groupName,String userName) {
        WebSocketSession userSession = sessionService.getGroupUser(groupName, userName);
        return null != userSession && userSession.isOpen();
    }
}
