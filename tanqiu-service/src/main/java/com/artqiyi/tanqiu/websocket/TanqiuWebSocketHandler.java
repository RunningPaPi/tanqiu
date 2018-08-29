package com.artqiyi.tanqiu.websocket;

import com.alibaba.fastjson.JSON;
import com.artqiyi.tanqiu.redis.RedisClient;
import com.artqiyi.tanqiu.websocket.service.WebSocketSessionService;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.Map;

/**
 * 趣电玩的websocket服务类
 *
 * @author wushuang
 * @since 2018-05-02
 */
@Component
public class TanqiuWebSocketHandler extends TextWebSocketHandler {
	private Logger logger = LoggerFactory.getLogger(TanqiuWebSocketHandler.class);
	@Autowired
	private RedisClient redisClient;
	
	private WebSocketSessionService sessionService = WebSocketSessionService.INSTANCE;

	/**
	 * 处理前端发送的文本信息 js调用websocket.send时候, 会调用该方法
	 *
	 * @param session
	 * @param message
	 * @throws Exception
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		Map paramsMap = (Map)JSON.parse(message.getPayload());
		String code = MapUtils.getString(paramsMap,"code");
		Map dataMap = (Map)paramsMap.get("params");
		if (code != null) {
			switch (code) {

				default:
					break;
			}
		}
	}

	/**
	 * 当新连接建立的时候, 连接成功时候,会触发页面上onOpen方法
	 *
	 * @param session
	 * @throws Exception
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		UserGroup userGroup = getUserIdAndGroupName(session);
		sessionService.addUser(userGroup.getUserName(), session);
		sessionService.addGroupUser(userGroup.getGroupName(), userGroup.getUserName(), session);
		logger.info("在线用户：{}", sessionService.getOnlineCount());
	}

	private UserGroup getUserIdAndGroupName(WebSocketSession session){
		URI uri = session.getUri();
		String[] uriSeg = uri.getPath().replace("/websocket/","").split("/");
		return new UserGroup(uriSeg[0], uriSeg[1]);
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
		UserGroup userGroup = getUserIdAndGroupName(session);
		sessionService.removeUser(userGroup.getUserName());
		sessionService.removeGroupUser(userGroup.getGroupName(), userGroup.getUserName());
		logger.info("用户 " + userGroup.getUserName() + " Connection closed. Status: " + status);
		logger.info("在线用户：{}", sessionService.getOnlineCount());
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
		UserGroup userGroup = getUserIdAndGroupName(session);
		if (session.isOpen()) {
			session.close();
		}
		logger.debug("用户: " + userGroup.getUserName() + " websocket connection closed......");
		sessionService.removeUser(userGroup.getUserName());
		sessionService.removeGroupUser(userGroup.getGroupName(), userGroup.getUserName());
	}

	/**
	 * 给所有在线用户发送消息
	 *
	 * @param message
	 */
	public void sendMessageToUsers(TextMessage message) {
		for (Map.Entry<String, WebSocketSession> sessionEntry : sessionService.getAllUsers().entrySet()) {
			try {
				if (sessionEntry.getValue().isOpen()) {
					sessionEntry.getValue().sendMessage(message);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
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
		WebSocketSession session = sessionService.getUser(userName);
		if (session != null) {
			try {
				if (session.isOpen()) {
					session.sendMessage(message);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
		}
	}

	public class UserGroup{
		//userName
		private String userName;
		//groupName
		private String groupName;

		public UserGroup(String userName, String groupName){
			this.userName = userName;
			this.groupName = groupName;
		}

		public String getUserName() {
			return userName;
		}
		public String getGroupName() {
			return groupName;
		}
	}
	
}
