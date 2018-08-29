package com.artqiyi.tanqiu.welfare.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.artqiyi.tanqiu.aspect.AuthPassport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.artqiyi.tanqiu.common.constant.ResponseCodeConstant;
import com.artqiyi.tanqiu.common.constant.SystemConstant;
import com.artqiyi.tanqiu.common.util.DateUtil;
import com.artqiyi.tanqiu.payment.domain.CoinTranslog;
import com.artqiyi.tanqiu.payment.service.TransLogService;
import com.artqiyi.tanqiu.response.ApiResponse;
import com.artqiyi.tanqiu.response.DefaultResponse;
import com.artqiyi.tanqiu.user.domain.User;
import com.artqiyi.tanqiu.user.domain.UserInfo;
import com.artqiyi.tanqiu.user.domain.UserInvite;
import com.artqiyi.tanqiu.user.service.IUserInfoService;
import com.artqiyi.tanqiu.user.service.IUserInviteService;
import com.artqiyi.tanqiu.user.service.IUserService;
import com.artqiyi.tanqiu.welfare.domain.Welfare;
import com.artqiyi.tanqiu.welfare.domain.WelfareReceiveLog;
import com.artqiyi.tanqiu.welfare.service.WelfareService;

@Controller
@RequestMapping("/api/welfare")
public class WelfareController {
	
	private static Logger logger = LoggerFactory.getLogger(WelfareController.class);

    @Autowired
    private WelfareService welfareService;
    @Autowired
    private TransLogService transLogService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IUserInviteService userInviteService;

	@AuthPassport(checkSign = true)
    @RequestMapping(value = "getRedpackWelfareInfo",method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse getRedpackWelfareInfo(@RequestParam(value = "userId",required = true)  Long userId){

        ApiResponse apiResponse = new DefaultResponse();
        if(null==userId||0L>userId){
            apiResponse.setCode(ResponseCodeConstant.PARAM_ID_FAIL);
            apiResponse.setMsg("用户ID不正确");
            return apiResponse;
        }
        
        HashMap<String, Object> data = null;
        List<HashMap<String, Object>> list = new ArrayList<>();
        
        //TODO 显示数量
        List<Long> friendUserIdList = userInviteService.getFriendIdListInDay(userId);
        int friendUserIdNum = friendUserIdList.size();
        
        List<Welfare> welfareList = welfareService.getRedpackWelfareList();
        for(int i=0; i<welfareList.size(); i++) {
        	Welfare welfare = welfareList.get(i);
        	data = new HashMap<String, Object>();
        	data.put("welfareId", welfare.getId());
        	//data.put("welfareName", welfare.getWelfareName());
        	//data.put("welfareDesc", welfare.getWelfareDesc());
        	data.put("welfareType", welfare.getWelfareType());
        	data.put("awardType", welfare.getAwardType());
        	data.put("awardNum", welfare.getAwardNum());
        	data.put("nickName", "");
        	data.put("hearPicUrl", "");
        	data.put("receiveStatus", 0); //不可领取
        	
        	if(welfare.getWelfareType() == 1) {
        		//邀请好友的头像和昵称
            	if(friendUserIdNum != 0 && i+1<=friendUserIdNum) {
            		Long friendUserId = friendUserIdList.get(i);
            		UserInfo userInfo = userInfoService.selectByUserId(friendUserId);
            		User user = userService.selectById(friendUserId);
            		if(user != null) {
            			data.put("nickName", user.getNickName());
            		}
            		if(userInfo != null) {
            			data.put("hearPicUrl", userInfo.getHeadPicUrl());
            		}
            		//查记录
            		WelfareReceiveLog receiveLog = null;
            		List<WelfareReceiveLog> receiveLogList = welfareService.getReceiveLogList(userId, welfare.getId(), 
            				DateUtil.getCurrentStartTimeDaily(), DateUtil.getCurrentEndTimeDaily());
            		//插记录
            		if(receiveLogList != null && receiveLogList.size() > 0) {
            			receiveLog = receiveLogList.get(0);
            			data.put("receiveStatus", receiveLog.getStatus());
            		}else {
            			receiveLog = new WelfareReceiveLog();
            			BeanUtils.copyProperties(welfare, receiveLog);
            			receiveLog.setWelfareId(welfare.getId());
            			receiveLog.setUserId(userId);
            			receiveLog.setFriendUserId(friendUserId);
            			receiveLog.setStatus(1);  //已完成未领取
            			receiveLog.setCreateTime(new Date());
            			welfareService.addWelfareLog(receiveLog);
            			data.put("receiveStatus", 1); 
            		}
            	 }
        	}else if(welfare.getWelfareType() == 2){  //集齐红包福利
        		if(friendUserIdList.size() == 5) {
        			//查记录
        			WelfareReceiveLog receiveLog = null;
            		List<WelfareReceiveLog> receiveLogList = welfareService.getReceiveLogList(userId, welfare.getId(), 
            				DateUtil.getCurrentStartTimeDaily(), DateUtil.getCurrentEndTimeDaily());
            		if(receiveLogList != null && receiveLogList.size() > 0) {
            			receiveLog = receiveLogList.get(0);
            			data.put("receiveStatus", receiveLog.getStatus());
            		}else {
            			receiveLog = new WelfareReceiveLog();
            			BeanUtils.copyProperties(welfare, receiveLog);
            			receiveLog.setWelfareId(welfare.getId());
            			receiveLog.setUserId(userId);
            			receiveLog.setStatus(1);  //已完成未领取
            			receiveLog.setCreateTime(new Date());
            			welfareService.addWelfareLog(receiveLog);
            			data.put("receiveStatus", 1); 
            		}
        		}
        	}
        	list.add(data);
        }
        
        apiResponse.setCode(ResponseCodeConstant.SUCCESS);
        apiResponse.setMsg("success");
        apiResponse.setResult(list);

        return apiResponse;
    }

	@AuthPassport(checkSign = true)
    @RequestMapping(value = "drawRedpack",method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse drawRedpack(@RequestParam(value = "userId",required = true)  Long userId,
    		@RequestParam(value = "welfareId",required = true)  Long welfareId){
    	Date now = new Date();
    	HashMap<String, Object> data = new HashMap<String, Object>();
    	Boolean isNewFriend = false;
    	
        ApiResponse apiResponse = new DefaultResponse();
        if(null==userId||0L>userId){
            apiResponse.setCode(ResponseCodeConstant.PARAM_ID_FAIL);
            apiResponse.setMsg("用户ID不正确");
            return apiResponse;
        }
        
        Welfare welfare =  welfareService.getWelfare(welfareId);
        if(welfare == null || welfare.getId() <= 0) {
        	 apiResponse.setCode(ResponseCodeConstant.PARAM_ID_FAIL);
             apiResponse.setMsg("该福利不存在");
             return apiResponse;
        }
        
        WelfareReceiveLog welfareReceiveLog = null;
        List<WelfareReceiveLog> logList = welfareService.getRedpackReceiveLogList(userId, welfareId, 
        		DateUtil.getCurrentStartTimeDaily(), DateUtil.getCurrentEndTimeDaily());
        if(logList == null || logList.size() == 0) {
        	 apiResponse.setCode(ResponseCodeConstant.SERVICE_FAIL);
             apiResponse.setMsg("请邀请一位好友后再领取");
             if(welfare.getWelfareType() == 2) {
            	 apiResponse.setMsg("请邀请5个好友，进入游戏后再领取");
             }
             return apiResponse;
        }
        
        welfareReceiveLog = logList.get(0);
        
        if(welfare.getWelfareType() == 1) {
        	UserInvite userInvite = userInviteService.findUserInvite(welfareReceiveLog.getFriendUserId(), userId, null, 
     	    		DateUtil.getCurrentStartTimeDaily(), DateUtil.getCurrentEndTimeDaily());
     	    if(userInvite == null) {
     	    	 apiResponse.setCode(ResponseCodeConstant.SERVICE_FAIL);
                 apiResponse.setMsg("请邀请一位好友后再领取");
                 return apiResponse;
     	    }
     	    if(userInvite.getIsNew()) {
     	    	isNewFriend = true;
     	    }
        }
        
    	if(welfareReceiveLog.getStatus() == 2) {
    		apiResponse.setCode(ResponseCodeConstant.SERVICE_FAIL);
            apiResponse.setMsg("今天已经领取过了");
            return apiResponse;
    	}
     
        //正常领取
    	Integer awardNum = welfare.getAwardNum();
    	UserInfo userInfo = userInfoService.selectByUserId(userId);
    	User user = userService.selectById(userId);
	    if (user == null || userInfo == null || userInfo.getId() <= 0) {// 找不到用户信息
	    	apiResponse.setCode(ResponseCodeConstant.PARAM_ID_FAIL);
	        apiResponse.setMsg("用户信息不存在");
	        return apiResponse;
	    }
	    
	    if(welfare.getWelfareType() == 1 && isNewFriend) {  //针对拆红包的新好友用户
	    	awardNum = awardNum * 3;
	    }
	    
	    //用户增加趣币或人民币
	    if(welfare.getAwardType() == 1) {
	    	 userInfo.setCoin(userInfo.getCoin() + awardNum);
	    }else if(welfare.getAwardType() == 2) { //后台配置的单位是元
	    	 userInfo.setBalance(userInfo.getBalance() + awardNum * 100); //用户账户存储单位是分
	    }
	    userInfo.setUpdateTime(now);
	    userInfoService.saveOrUpdate(userInfo);
	    
	    
	    //添加交易流水
	    String remark = "";
	    String transType = "";
	    if(welfare.getWelfareType() == 1) {
	    	remark = "拆红包奖励";
	    	transType = SystemConstant.TRANS_TYPE_WELFARE_REDPACK;
	    }else if(welfare.getWelfareType() == 2) {
	    	remark = "集齐5个红包奖励";
	    	transType = SystemConstant.TRANS_TYPE_WELFARE_COLLECT;
	    }
	    
	    Integer accountType = 0;
	    if(welfare.getAwardType() == 1) { //加趣币
	    	accountType = 1;
	    }else if(welfare.getAwardType() == 2) {//加人民币
	    	accountType = 3;
	    }
	    if(accountType != 0) {
	    	transLogService.generateTransLog4Welfare(accountType, awardNum, transType, remark, user, userInfo);
	    }
	    
    	//领取记录状态修改
    	welfareReceiveLog.setStatus(2);
    	welfareService.updateWelfareLog(welfareReceiveLog);
        
    	data.put("awardType", welfare.getAwardType());
    	data.put("awardNum", awardNum);
        apiResponse.setCode(ResponseCodeConstant.SUCCESS);
        apiResponse.setMsg("success");
        apiResponse.setResult(data);

        return apiResponse;
    }


	@AuthPassport(checkSign = true)
    @RequestMapping(value = "getDailyWelfare",method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse getDailyWelfare(@RequestParam(value = "userId",required = true)  Long userId){

    	ApiResponse apiResponse = new DefaultResponse();
        if(null==userId||0L>userId){
            apiResponse.setCode(ResponseCodeConstant.PARAM_ID_FAIL);
            apiResponse.setMsg("用户ID不正确");
            return apiResponse;
        }
        
        Welfare welfare = welfareService.getDailyWelfare();
        if(welfare == null) {
        	 apiResponse.setCode(ResponseCodeConstant.FAIL);
             apiResponse.setMsg("每日福利不存在");
             return apiResponse;
        }
        
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("welfareId", welfare.getId());
        //data.put("welfareName", welfare.getWelfareName());
        //data.put("welfareDesc", welfare.getWelfareDesc());
        data.put("welfareType", welfare.getWelfareType());
        data.put("awardType", welfare.getAwardType());
        data.put("awardNum", welfare.getAwardNum());
        
        
        //当天的领奖次数,即该任务的当天记录次数	
        int num =  welfareService.countWelfareReceiveLog(userId, welfare.getId(), 
        			DateUtil.getCurrentStartTimeDaily(), DateUtil.getCurrentEndTimeDaily());
        
        Date now = new Date();
        int hour = DateUtil.getHour(now);
        if(hour < 10) {
        	data.put("receiveStatus", 0); //不可领取
        }else if(hour >= 10 && hour < 18){
        	if(num < 1) {
        		data.put("receiveStatus", 1); //可领取
       	 	}else {
       	 		data.put("receiveStatus", 2); //已领取
       	 	}
        }else { //hour>=18
        	List<WelfareReceiveLog> welfareReceiveLogList = welfareService.getReceiveLogList(userId, welfare.getId(),
        			DateUtil.getSubsectionHourBegin(now, 18), DateUtil.getCurrentEndTimeDaily());
            if(welfareReceiveLogList != null && welfareReceiveLogList.size() > 0) {  //当天18点后有记录
            	data.put("receiveStatus", 2); //已领取
            }else {
            	data.put("receiveStatus", 1); //可领取
            }
        }

        apiResponse.setCode(ResponseCodeConstant.SUCCESS);
        apiResponse.setMsg("success");
        apiResponse.setResult(data);

        return apiResponse;
    }


	@AuthPassport(checkSign = true)
    @RequestMapping(value = "drawDailyWelfare",method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse drawDailyWelfare(@RequestParam(value = "userId",required = true)  Long userId){
    	HashMap<String, Object> data = new HashMap<String, Object>();
    	ApiResponse apiResponse = new DefaultResponse();
    	apiResponse.setCode(ResponseCodeConstant.PARAM_ID_FAIL);
        if(null==userId||0L>userId){
            apiResponse.setMsg("用户ID不正确");
            return apiResponse;
        }
        
        Welfare welfare = welfareService.getDailyWelfare();
        if(welfare == null) {
            apiResponse.setMsg("每日福利不存在");
            return apiResponse;
        }
        
        if(welfare.getWelfareType() != 3) {
            apiResponse.setMsg("该福利不是每日登陆福利");
            return apiResponse;
        }
        
        Date now = new Date();
        int hour = DateUtil.getHour(now);
        WelfareReceiveLog welfareReceiveLog = null;
        if(hour < 10) {
        	//不是每日领奖的时间段
        	apiResponse.setCode(ResponseCodeConstant.SERVICE_FAIL);
        	apiResponse.setMsg("该时间段不可领取");
        	return apiResponse;
        }else if(hour >= 10 && hour < 18) {
        	List<WelfareReceiveLog> logList = welfareService.getReceiveLogList(userId, welfare.getId(), 
     	   			DateUtil.getSubsectionHourBegin(now, 10), DateUtil.getSubsectionHourEnd(now, 17));
     	   	if(logList != null && logList.size() > 0) {
     	   		apiResponse.setCode(ResponseCodeConstant.SERVICE_FAIL);
     	   		apiResponse.setMsg("该时间段已领取过了");
     	   		return apiResponse;
     	   	}
        }else { //hour>=18
        	List<WelfareReceiveLog> logList = welfareService.getReceiveLogList(userId, welfare.getId(), 
        			DateUtil.getSubsectionHourBegin(now, 18), null);
        	if(logList != null && logList.size() > 0) {
        		apiResponse.setCode(ResponseCodeConstant.SERVICE_FAIL);
     	   		apiResponse.setMsg("该时间段已领取过了");
     	   		return apiResponse;
     	   	}
        }
        
        // 查询对应用户
        UserInfo userInfo = userInfoService.selectByUserId(userId);
        User user = userService.selectById(userId);
        if (user == null || userInfo == null || userInfo.getId() <= 0) {// 找不到用户信息
        	apiResponse.setMsg("用户信息不存在");
 	   		return apiResponse;
        }
        
        

        /****************************正常领奖****************************/
        //添加领取记录
        WelfareReceiveLog receiveLog = new WelfareReceiveLog();
		BeanUtils.copyProperties(welfare, receiveLog);
		receiveLog.setWelfareId(welfare.getId());
		receiveLog.setUserId(userId);
		receiveLog.setStatus(2);  //已完成已领取
		receiveLog.setCreateTime(new Date());
		welfareService.addWelfareLog(receiveLog);
        
		
		Integer awardNum = welfare.getAwardNum();
		//用户账户添加趣币或人民币 String sql ="update tableName set coin = coin + 1 where userId = ?";
	    if(welfare.getAwardType() == 1) {
	    	userInfo.setCoin(userInfo.getCoin() + awardNum);
	    }else if(welfare.getAwardType() == 2) { //后台配置的单位是元
	    	userInfo.setBalance(userInfo.getBalance() + awardNum * 100);  //用户账户存储单位是分
	    }
		userInfo.setUpdateTime(now);
		userInfoService.saveOrUpdate(userInfo);
        
	    //添加交易流水
		Integer accountType = 0;
	    if(welfare.getAwardType() == 1) { //加趣币
	    	accountType = 1;
	    }else if(welfare.getAwardType() == 2) {//加人民币
	    	accountType = 3;
	    }
	    if(accountType != 0) {
	    	transLogService.generateTransLog4Welfare(accountType, awardNum, 
	  	    		SystemConstant.TRANS_TYPE_WELFARE_DAILY, "每日福利", user, userInfo);
	    }
	  
	    
        
	    data.put("awardType", welfare.getAwardType());
	    data.put("awardNum", awardNum);
        apiResponse.setCode(ResponseCodeConstant.SUCCESS);
        apiResponse.setMsg("success");
        apiResponse.setResult(data);

        return apiResponse;
    }
    
    
    
    
	    

}
