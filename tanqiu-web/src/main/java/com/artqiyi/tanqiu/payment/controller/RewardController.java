package com.artqiyi.tanqiu.payment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.artqiyi.tanqiu.aspect.AuthPassport;
import com.artqiyi.tanqiu.common.exception.TaskException;
import com.artqiyi.tanqiu.common.exception.UserException;
import com.artqiyi.tanqiu.payment.domain.CoinTranslog;
import com.artqiyi.tanqiu.payment.service.TransLogService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.artqiyi.tanqiu.common.constant.MsgConstant;
import com.artqiyi.tanqiu.common.constant.ResponseCodeConstant;
import com.artqiyi.tanqiu.response.ApiResponse;
import com.artqiyi.tanqiu.response.DefaultResponse;
import com.artqiyi.tanqiu.user.domain.UserInfo;
import com.artqiyi.tanqiu.user.service.IUserInfoService;


@Controller
@RequestMapping("/api/reward")
public class RewardController {
    private static Logger logger = LoggerFactory.getLogger(RewardController.class);

    @Autowired
    private TransLogService transLogService;
    
    @Autowired
    private IUserInfoService userInfoService;
    
    
    /**
     * 获取用户的总奖励--奖金
     * @param userId
     * @return
     */
	@AuthPassport(checkSign = true)
    @RequestMapping(value = "getSumRewardByUserId",method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse getSumRewardByUserId(@RequestParam(value = "userId",required = true)Long userId){

        ApiResponse response = new DefaultResponse();
        Map<String, Object> data = new HashMap<String, Object>();
        try {
			Integer sumReward = transLogService.getSumRewardByUserId(userId);
			if(sumReward == null) {
				sumReward = 0;
			}
			
			
			Long balance = 0L;
			UserInfo userInfo = userInfoService.selectByUserId(userId);
			if(userInfo != null) {
				balance = userInfo.getBalance();
			}
			
			
			
			data.put("sumReward", sumReward);
			data.put("leftBalance", balance);
			
			response.setCode(ResponseCodeConstant.SUCCESS);
			response.setMsg(MsgConstant.SUCCESS_SEARCH);
			response.setResult(data);  //单位:分
		} catch (Exception e) {
			logger.error("【获取用户总奖励异常】",e);
			response.setCode(ResponseCodeConstant.SERVICE_FAIL);
			response.setMsg(MsgConstant.FAIL_SEARCH);
		}
		return response;

    }

	@AuthPassport(checkSign = true)
    @RequestMapping(value = "getRewardDetail",method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse getRewardDetail(@RequestParam(required = false, defaultValue = "1")Integer pageNum,
										@RequestParam(required = false, defaultValue = "20")  Integer pageSize,
										@RequestParam(value = "userId",required = true)Long userId){

        ApiResponse response = new DefaultResponse();
    	//参数限制
		if(pageNum < 1) {
			pageNum = 1;
		}
		if(pageSize > 100) {
			pageSize = 100;
		}
        
        try {
			List<CoinTranslog> coinTranslogList = transLogService.findTransLogList(pageNum, pageSize, userId, 3, null, null);
			
			response.setCode(ResponseCodeConstant.SUCCESS);
			response.setMsg(MsgConstant.SUCCESS_SEARCH);
			response.setResult(coinTranslogList);
		} catch (Exception e) {
			logger.error("【获取用户奖金明细异常 】",e);
			response.setCode(ResponseCodeConstant.SERVICE_FAIL);
			response.setMsg(MsgConstant.FAIL_SEARCH);
		}
		return response;

    }
    
    

   
}
