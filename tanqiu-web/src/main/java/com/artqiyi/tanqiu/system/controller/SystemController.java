package com.artqiyi.tanqiu.system.controller;

import com.artqiyi.tanqiu.common.constant.ResponseCodeConstant;
import com.artqiyi.tanqiu.response.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/system")
public class SystemController {

    @GetMapping("/now")
    public UserResponse nowTime(){
        UserResponse response = new UserResponse();
        response.setMsg("系统时间");
        Map map = new HashMap(2);
        map.put("systemTime", System.currentTimeMillis());
        response.setResult(map);
        response.setCode(ResponseCodeConstant.SUCCESS);
        return response;
    }
}
