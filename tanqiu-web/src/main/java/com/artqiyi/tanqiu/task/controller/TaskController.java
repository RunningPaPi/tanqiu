/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan-web
 * Author: author  wushyue@gmail.com
 * Create On: Apr 27, 2018 10:36:24 AM
 * Modify On: Apr 27, 2018 10:36:24 AM by wushyue@gmail.com
 */
package com.artqiyi.tanqiu.task.controller;

import java.util.List;

import com.artqiyi.tanqiu.aspect.AuthPassport;
import com.artqiyi.tanqiu.common.exception.TaskException;
import com.artqiyi.tanqiu.common.exception.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.artqiyi.tanqiu.common.constant.ResponseCodeConstant;
import com.artqiyi.tanqiu.response.ApiResponse;
import com.artqiyi.tanqiu.response.DefaultResponse;
import com.artqiyi.tanqiu.user.service.IUserInfoService;
import com.artqiyi.tanqiu.task.domain.ext.MyTask;
import com.artqiyi.tanqiu.task.service.TaskService;

/**
 * 做任务的模块控制器
 *
 * @author wushuang
 * @since 2018-04-27
 */
@Controller
@RequestMapping("/api/task")
public class TaskController {
    private static Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;
    
    @Autowired
    private IUserInfoService userInfoService;
    
    /**
     * 每日福利--日常任务  or 邀请有礼--新手任务
     * @param userId 用户ID
     * @return ApiResponse
     */
    @RequestMapping(value = "myTaskByTypeAttr",method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse listTaskByTypeAttr(@RequestParam(value = "userId",required = true)  Long userId,
    		 @RequestParam(value = "taskTypeAttr",required = true)  Short taskTypeAttr){

        ApiResponse apiResponse = new DefaultResponse();

        if(null==userId||0L>userId){
            apiResponse.setCode(ResponseCodeConstant.FAIL);
            apiResponse.setMsg("用户ID不正确");
            return apiResponse;
        }
        
        if(taskTypeAttr == null){
            apiResponse.setCode(ResponseCodeConstant.FAIL);
            apiResponse.setMsg("任务类型属性不正确");
            return apiResponse;
        }

        List<MyTask> list=null;
        try{
            list= taskService.findTask(userId, taskTypeAttr);
        }catch (UserException ue){
            apiResponse.setCode(ResponseCodeConstant.FAIL);
            apiResponse.setMsg(ue.getMessage());
            return apiResponse;
        }

        apiResponse.setCode(ResponseCodeConstant.SUCCESS);
        apiResponse.setMsg("success");
        apiResponse.setResult(list);

        return apiResponse;
    }
    
    
    /**
     * 每日领奖任务
     * @param userId 用户ID
     * @return ApiResponse
     */
    @AuthPassport(checkSign = true)
    @RequestMapping(value = "getDailyAward",method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse getReceivePrizeTask(@RequestParam(value = "userId",required = true)  Long userId){

        ApiResponse apiResponse = new DefaultResponse();

        if(null==userId||0L>userId){
            apiResponse.setCode(ResponseCodeConstant.FAIL);
            apiResponse.setMsg("用户ID不正确");

            return apiResponse;
        }

        MyTask myTask = null;
        try{
        	myTask = taskService.findReceivePrizeTask(userId);
        }catch (UserException ue){
            apiResponse.setCode(ResponseCodeConstant.FAIL);
            apiResponse.setMsg(ue.getMessage());
            return apiResponse;
        }

        apiResponse.setCode(ResponseCodeConstant.SUCCESS);
        apiResponse.setMsg("success");
        apiResponse.setResult(myTask);

        return apiResponse;
    }

    @AuthPassport(checkSign = true)
    @RequestMapping(value = "draw",method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse draw(@RequestParam(value = "userId", required = true) Long userId,@RequestParam(value = "taskId", required = true) Integer taskId ){
    	ApiResponse response = new DefaultResponse();

    	//领取奖励
    	Boolean result = false;
    	try{
            result = taskService.drawReward(userId,taskId);
        }catch (TaskException te){
            response.setCode(ResponseCodeConstant.FAIL);
            response.setMsg(te.getMessage());
            response.setResult(result);
            return response;
        }

        response.setCode(ResponseCodeConstant.SUCCESS);
        response.setMsg("领取成功");
        response.setResult(result);
    
        return response;
    }

    @AuthPassport(checkSign = true)
    @RequestMapping(value = "drawDailyAward",method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse drawPrize(@RequestParam(value = "userId", required = true) Long userId,@RequestParam(value = "taskId", required = true) Integer taskId ){
    	ApiResponse response = new DefaultResponse();

    	//每日领奖
    	Boolean result = false;
    	try{
            result = taskService.drawReward4EveryDayPrize(userId,taskId);
        }catch (TaskException te){
            response.setCode(ResponseCodeConstant.FAIL);
            response.setMsg(te.getMessage());
            response.setResult(result);
            return response;
        }

        response.setCode(ResponseCodeConstant.SUCCESS);
        response.setMsg("领取成功");
        response.setResult(result);
    
        return response;
    }
   
}
