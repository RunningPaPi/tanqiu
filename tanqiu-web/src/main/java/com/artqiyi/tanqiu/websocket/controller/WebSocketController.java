/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: tanqiu-web
 * Author: author  wushyue@gmail.com
 * Create On: May 2, 2018 4:57:22 PM
 * Modify On: May 2, 2018 4:57:22 PM by wushyue@gmail.com
 */
package com.artqiyi.tanqiu.websocket.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;

import com.artqiyi.tanqiu.common.constant.RedisFiledConstant;
import com.artqiyi.tanqiu.common.util.JSONUtil;
import com.artqiyi.tanqiu.redis.RedisService;
import com.artqiyi.tanqiu.websocket.TanqiuWebSocketHandler;
import com.artqiyi.tanqiu.websocket.client.TanqiuWebSocketClient;
import com.artqiyi.tanqiu.websocket.service.WebSocketClientService;
import com.artqiyi.tanqiu.websocket.service.WebSocketHandlerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import java.net.URI;

/** 
 * WebSocket实例类
 *
 * @author wushuang
 * @since 2018-05-02
 */
@Controller
public class WebSocketController {
	private static Logger logger = LoggerFactory.getLogger(WebSocketController.class);

    @Autowired
    WebSocketHandlerService webSocketHandlerService;
    @Autowired
    WebSocketClientService webSocketClientService;

    @Autowired
    private RedisService redisService;
  
   // @RequestMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getParameter("username");
        String group = request.getParameter("group");
        logger.info(username + "登录");  
        HttpSession session = request.getSession();  
        session.setAttribute("WEBSOCKET_USERNAME", username);
        session.setAttribute("WEBSOCKET_GROUP", group);
        WebSocketContainer conmtainer = ContainerProvider.getWebSocketContainer();
        TanqiuWebSocketClient client = new TanqiuWebSocketClient();
        conmtainer.connectToServer(webSocketClientService,
                new URI("ws://localhost/websocket:8080/socketServer"));
//        webSocketClientService.createWebSocketClient("ws://jijiatmatm.3322.org:49080/websocket/socketServer");
//        webSocketClientService.send("hello");
        response.sendRedirect("websocket.jsp");  
    }  
  
   // @RequestMapping("/send")
    @ResponseBody  
    public String send(HttpServletRequest request) {  
        String username = request.getParameter("username");  
        //getHandler().sendMessageToUser(username, new TextMessage("你好，欢迎测试！！！！"));
//        webSocketHandlerService.sendMessageToUser(username,new TextMessage("你好，欢迎测试！！！！"));
        return null;  
    } 
}
