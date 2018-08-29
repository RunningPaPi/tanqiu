/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan-web
 * Author: author  wushyue@gmail.com
 * Create On: Jun 19, 2018 5:26:48 PM
 * Modify On: Jun 19, 2018 5:26:48 PM by wushyue@gmail.com
 */
package com.artqiyi.tanqiu.system.controller;

import com.artqiyi.tanqiu.common.constant.ResponseCodeConstant;
import com.artqiyi.tanqiu.response.ApiResponse;
import com.artqiyi.tanqiu.response.DefaultResponse;
import com.artqiyi.tanqiu.system.domain.SystemDistribution;
import com.artqiyi.tanqiu.system.service.DistributionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** 
 * 发布管理
 *
 * @author wushuang
 * @since 2018-06-19
 */
@RestController
@RequestMapping("/api/distribution")
public class DistributionController {
    private static Logger logger = LoggerFactory.getLogger(DistributionController.class);

    @Autowired
    DistributionService distributionService;

    @RequestMapping(value = "find", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse findDistribution(@RequestParam(value = "appType", required = true) Short appType,
                                        @RequestParam(value = "version", required = false) String version){
        ApiResponse apiResponse = new DefaultResponse();

        SystemDistribution distribution = distributionService.findSystemDistribution(appType,version);

        apiResponse.setCode(ResponseCodeConstant.SUCCESS);
        apiResponse.setMsg("success");
        apiResponse.setResult(distribution);

        return apiResponse;

    }

    @RequestMapping(value = "findList", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse findDistribution(@RequestParam(value = "appType", required = true) Short appType,
                                        @RequestParam(value = "version", required = false) String version,
                                        @RequestParam(value = "appStoreId", required = false) Short appStoreId){
        ApiResponse apiResponse = new DefaultResponse();

        List<SystemDistribution> distributionList = distributionService.findSystemDistribution(appType,version,appStoreId);

        apiResponse.setCode(ResponseCodeConstant.SUCCESS);
        apiResponse.setMsg("success");
        apiResponse.setResult(distributionList);

        return apiResponse;

    }


}
