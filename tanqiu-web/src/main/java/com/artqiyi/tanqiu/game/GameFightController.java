package com.artqiyi.tanqiu.game;

/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: tanqiu
 * Author: chencunjun  <1078027943@qq.com>
 * Create On: 2018/7/5
 * Modify On: 2018/7/5 by chencunjun
 */

import com.artqiyi.tanqiu.aspect.AuthPassport;
import com.artqiyi.tanqiu.common.constant.MsgConstant;
import com.artqiyi.tanqiu.response.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 好友对战模式
 */
@RestController
@RequestMapping("/api/gameFight")
public class GameFightController {
    private static final Logger log = LoggerFactory.getLogger(GameBreakController.class);
    @Autowired
    private IGameFightService gameFightService;

    /**
     * 创建游戏房间
     * @param userId
     * @return
     */
   // @AuthPassport(checkLogin = false,checkSign = true)
    @RequestMapping(value = "/createRoom", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public UserResponse getBigBonusRules(@RequestParam(value = "userId",required = true)Long userId) {
        UserResponse response = new UserResponse();
        String roomNo=gameFightService.newRoom(userId);
        Map map=new HashMap<>();
        map.put("roomNo",roomNo);
        response.setResult(map);
        response.setMsg("房间创建成功");
        response.setCode(MsgConstant.SUCCESS_SEARCH);
        return response;
    }
}
