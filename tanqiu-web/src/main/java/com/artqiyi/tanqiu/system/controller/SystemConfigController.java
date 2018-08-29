
package com.artqiyi.tanqiu.system.controller;

import com.artqiyi.tanqiu.common.constant.MsgConstant;
import com.artqiyi.tanqiu.common.constant.ResponseCodeConstant;
import com.artqiyi.tanqiu.response.ApiResponse;
import com.artqiyi.tanqiu.response.DefaultResponse;
import com.artqiyi.tanqiu.system.domain.SystemConfig;
import com.artqiyi.tanqiu.system.service.SystemConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 类或接口作用描述

 */
@Controller
@RequestMapping("/api/sys")
public class SystemConfigController {
	private static Logger logger = LoggerFactory.getLogger(SystemConfigController.class);

	@Autowired
	SystemConfigService systemConfigService;

	@RequestMapping(value = "getAdImg",method = RequestMethod.GET)
	@ResponseBody
	public ApiResponse getAdImg() {
		ApiResponse response = new DefaultResponse();
		try {
			SystemConfig systemConfig = systemConfigService.getByParamKey("AD_IMG"); //获取广告图链接
			String imgUrl = "";
			if(systemConfig != null) {
				imgUrl = systemConfig.getParamValue();
			}
			response.setCode(ResponseCodeConstant.SUCCESS);
			response.setMsg(MsgConstant.SUCCESS_SEARCH);
			response.setResult(imgUrl);
		} catch (Exception e) {
			logger.error("【获取广告图异常】",e);
			response.setCode(ResponseCodeConstant.SERVICE_FAIL);
			response.setMsg(MsgConstant.FAIL_SEARCH);
		}
		return response;
	}

	
}
