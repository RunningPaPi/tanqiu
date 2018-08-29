package com.artqiyi.tanqiu.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.artqiyi.tanqiu.aspect.AuthPassport;
import com.artqiyi.tanqiu.common.constant.*;
import com.artqiyi.tanqiu.common.util.*;
import com.artqiyi.tanqiu.redis.RedisClient;
import com.artqiyi.tanqiu.response.UserResponse;
import com.artqiyi.tanqiu.system.domain.SystemConfig;
import com.artqiyi.tanqiu.system.service.SystemConfigService;
import com.artqiyi.tanqiu.user.domain.*;
import com.artqiyi.tanqiu.user.service.*;

import com.artqiyi.tanqiu.user.service.vo.UserVo;
import org.apache.commons.collections.MapUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 平台用户接口
 */
@Controller
@RequestMapping("/api/user")
public class UserController {

    private static Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IUserInviteService userInviteService;
    @Autowired
    private WXAppletUserInfoService wxAppletUserInfoService;
    @Autowired
    private IUserShareService userShareService;
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private SystemConfigService systemConfigService;

    @AuthPassport(checkSign = true,checkLogin = false)
    @GetMapping("/{unionId}")
    @ResponseBody
    public UserResponse getByUnionId(@PathVariable("unionId") String unionId){
        UserResponse rsp = new UserResponse();
        UserVo userVo = userService.getByUnionId(unionId);
        rsp.setMsg("用户信息");
        rsp.setCode(ResponseCodeConstant.SUCCESS);
        rsp.setResult(userVo);
        return rsp;
    }
    /**
     * 微信小程序获取用户信息
     * @param encryptedData
     * @param sessionKey
     * @param iv
     * @return
     */
    @AuthPassport(checkSign = true,checkLogin = false)
    @PostMapping("/wxapplet/userInfo")
    @ResponseBody
    public UserResponse wxAppletUserInfo(@RequestParam("encryptedData") String encryptedData,
                                         @RequestParam("sessionKey") String sessionKey,
                                         @RequestParam("iv") String iv){
        UserResponse rsp = new UserResponse();
        try {
            String realSessionKey = redisClient.get(RedisFiledConstant.USER_SESSION_KEY+sessionKey, String.class);

            log.info("realSessionKey={},encryptedData={},iv={}", realSessionKey,encryptedData,iv);
            redisClient.del(RedisFiledConstant.USER_SESSION_KEY+sessionKey);
            rsp.setResult(wxAppletUserInfoService.getUserInfo(encryptedData, realSessionKey, iv));
        } catch (Exception e){
            log.info("【微信小程序】 解密用户信息出错");
            rsp.setCode(ResponseCodeConstant.SERVICE_FAIL);
            rsp.setResult(null);
            return rsp;
        }
        rsp.setCode(ResponseCodeConstant.SUCCESS);
        return rsp;
    }

    /**
     * 微信小程序登录
     * @param code
     * @return
     */
    @AuthPassport(checkSign = true,checkLogin = false)
    @GetMapping("/wxapplet/login")
    @ResponseBody
    public UserResponse wxAppletLogin(@RequestParam("code") String code){
        UserResponse rsp = new UserResponse();
        try {
            String fakeSessionKey = UUID.randomUUID().toString();
            JSONObject jsonObject = wxAppletUserInfoService.getSessionKeyOrOpenid(code);
            String sessionKey = MapUtils.getString(jsonObject,"session_key");
            log.info("realSessionKey={}", sessionKey);
            redisClient.setWithExpire(RedisFiledConstant.USER_SESSION_KEY+ fakeSessionKey, sessionKey,5*60);
            jsonObject.put("session_key",fakeSessionKey);
            rsp.setResult(jsonObject);
        } catch (Exception e){
            log.info("【微信小程序】 登录出错");
            rsp.setCode(ResponseCodeConstant.SERVICE_FAIL);
            rsp.setResult(null);
            return rsp;
        }
        rsp.setCode(ResponseCodeConstant.SUCCESS);
        return rsp;
    }



    /**
     * 绑定支付宝帐号
     *
     * @param userId  用户id
     * @param alipayAccount  支付宝帐号
     * @param alipayRealname 支付宝认证实名
     * @return
     */
    @AuthPassport(checkSign = true,checkLogin = true)
    @PostMapping("/bindAlipay")
    @ResponseBody
    public UserResponse bindAlipayAccount(@RequestParam("userId") Long userId,
                                  @RequestParam("alipayAccount") String alipayAccount,
                                  @RequestParam("alipayRealname") String alipayRealname) {
        UserResponse userResponse = new UserResponse();
        Map<String, Object> result = new HashMap<>();
        userResponse.setResult(result);
        result.put("isOprSuccess", false);
        result.put("alipayAccount", alipayAccount);
        result.put("alipayRealname", alipayRealname);
        if(null==userId||0L>userId){
            userResponse.setCode(ResponseCodeConstant.PARAM_FAIL);
            userResponse.setMsg("用户ID不正确");
            return userResponse;
        }
        if (StringUtils.isEmpty(alipayAccount)||StringUtils.isEmpty(alipayRealname)) {
            userResponse.setCode(ResponseCodeConstant.PARAM_FAIL);
            userResponse.setMsg(MsgConstant.BIND_ALIPAY_FAIL_PARAM);
            return userResponse;
        }
        try {
            userInfoService.bindAlipayAccount(userId,alipayAccount,alipayRealname);
            userResponse.setCode(ResponseCodeConstant.SUCCESS);
            userResponse.setMsg(MsgConstant.BIND_ALIPAY_SUCCESS);
            result.put("isOprSuccess", true);
        } catch (Exception e) {
            e.printStackTrace();
            userResponse.setCode(ResponseCodeConstant.SERVICE_FAIL);
            userResponse.setMsg(e.getMessage());
        }
        return userResponse;
    }

    /**
     * 微信快捷登录
     *
     * @param nickName 微信昵称
     * @param unionId  微信唯一标志
     * @return
     */
    @AuthPassport(checkSign = true,checkLogin = false)
    @RequestMapping(method = RequestMethod.GET, value = "/loginByWechat")
    @ResponseBody
    public UserResponse loginByWechat(@RequestParam("nickName") String nickName, @RequestParam("unionId") String unionId,
                                      @RequestParam("headUrl") String headUrl, @RequestParam("gender") short gender) {
        UserResponse rsp = new UserResponse();
        Map<String, Object> data = new HashMap<String, Object>();//返回数据
        UserExample example = new UserExample();

        //处理头像问题
        if (StringUtils.isEmpty(headUrl)) {
            SystemConfig systemConfig = systemConfigService.getByParamKey(SystemConstant.DEFAULT_HEAD_URL);
            headUrl = systemConfig.getParamValue();
        }

        example.or().andUnionIdEqualTo(unionId);
        List<User> users = userService.selectByExample(example);
        String token = UUID.randomUUID().toString();
        if (null != users && users.size() > 0) { //非初次微信授权登录
            User user = users.get(0);
            String oldToken = user.getToken();
            user.setToken(token);
            user.setNickName(nickName);
            userService.saveOrUpdate(user); //更新用户token
            UserInfo userInfo = userInfoService.selectByUserId(user.getId());
            if (null != userInfo) {
                userInfo.setLastLoginTime(new Date());
                userInfo.setHeadPicUrl(headUrl);
                userInfoService.saveOrUpdate(userInfo); //更新用户最后登录时间
            }
            user.setPassword("");
            data.put("user", user);
            data.put("userInfo", userInfo);
            //返回是否首次注册登录
            data.put("isFirstTimeLogin", false);
            redisClient.hDel(RedisFiledConstant.FILED_USER, oldToken); //移除上一次用户缓存信息
            redisClient.hSet(RedisFiledConstant.FILED_USER, token, data);//保存用户信息至redis
        } else { //初次授权登录相当于新用户注册
            User user = new User();
            user.setToken(token);
            user.setNickName(nickName);
            user.setUnionId(unionId);
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            user.setStatus(SystemConstant.VALID);
            user.setRegisteType(SystemConstant.REGISTE_WEIXIN);
            user.setIsRobot(false);
            long userId = userService.saveOrUpdate(user); //保存用户信息
            user.setId(userId);
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(userId);
            //生成随机编码
            int userRandomNO = RandomCodeUtil.generateUserNo(SystemConstant.USER_RANDOM_NO);
            while (userInfoService.isUserRandomNoExist(userRandomNO)){
                userRandomNO = RandomCodeUtil.generateUserNo(SystemConstant.USER_RANDOM_NO);
            }
            userInfo.setUserRandomNo(userRandomNO);
            userInfo.setLastLoginTime(new Date());
            userInfo.setCreateTime(new Date());
            userInfo.setUpdateTime(new Date());
            //生成邀请码
            String inviteCode = RandomCodeUtil.generateInviteCode(SystemConstant.INVITE_CODE_LENGTH);
            while (userInfoService.isInviteCodeExist(inviteCode)){
                inviteCode = RandomCodeUtil.generateInviteCode(SystemConstant.INVITE_CODE_LENGTH);
            }
            userInfo.setInvaiteCode(inviteCode);
            userInfo.setHeadPicUrl(headUrl);
            userInfo.setGender(gender);
            userInfo.setLevel("0");
            userInfo.setCoin(0);
            userInfo.setPoint(0);
            userInfo.setBalance(0L);//首次登录，赠送红包
            userInfo.setBalanceWithdrawable(0L);
            userInfo.setBalanceFreezed(0L);
            userInfo.setAlipayAccountValidated(false);
            userInfo.setPhoneValidated(false);
            long userInfoId = userInfoService.saveOrUpdate(userInfo); //保存用户基本信息
            userInfo.setId(userInfoId);
            user.setPassword("");
            data.put("user", user);
            data.put("userInfo", userInfo);
            //返回是否首次注册登录
            data.put("isFirstTimeLogin", true);
            redisClient.hSet(RedisFiledConstant.FILED_USER, token, data);//保存用户信息至redis
        }

        rsp.setResult(data);
        rsp.setCode(ResponseCodeConstant.SUCCESS);
        rsp.setMsg(MsgConstant.SUCCESS_LOGIN);
        return rsp;
    }





    /**
     * 注销登录
     *
     * @param token
     * @return
     */
    @AuthPassport(checkLogin = true,checkSign = true)
    @RequestMapping(method = RequestMethod.GET, value = "/loginOut")
    @ResponseBody
    public UserResponse logout(@RequestParam("token") String token) {
        UserResponse rsp = new UserResponse();
        redisClient.hDel(RedisFiledConstant.FILED_USER, token); //移除用户缓存信息
        //清空用户token
        UserExample example = new UserExample();
        example.or().andTokenEqualTo(token);
        List<User> users = userService.selectByExample(example);
        if (null != users && users.size() > 0) {
            User user = users.get(0);
            user.setToken("");
            user.setUpdateTime(new Date());
            userService.saveOrUpdate(user);
        }
        rsp.setResult(null);
        rsp.setCode(ResponseCodeConstant.SUCCESS);
        rsp.setMsg(MsgConstant.SUCCESS_LOGINOUT);
        return rsp;
    }

    /**
     * 修改头像
     *
     * @param headPic
     * @param userId
     * @return
     */
    @AuthPassport(checkLogin = true,checkSign = true)
    @RequestMapping(method = RequestMethod.GET, value = "/modifyHeadPic")
    @ResponseBody
    public UserResponse modifyHeadPic(@RequestParam("headPic") String headPic, @RequestParam("userId") long userId) {
        UserResponse rsp = new UserResponse();
        UserInfo userInfo = userInfoService.selectByUserId(userId);
        if (userInfo != null) {
            String oldHeadPicUrl = userInfo.getHeadPicUrl();//用于记录更换历史
            userInfo.setHeadPicUrl(headPic);
            userInfo.setUpdateTime(new Date());
            userInfoService.saveOrUpdate(userInfo);
            rsp.setMsg(MsgConstant.SUCCESS_SAVE);
        } else {
            rsp.setMsg(MsgConstant.FAIL_SAVE);
        }
        rsp.setCode(ResponseCodeConstant.SUCCESS);
        rsp.setResult(null);
        return rsp;
    }

    /**
     * 修改昵称
     * @param nickName
     * @param userId
     * @return
     */
    @AuthPassport(checkLogin = true,checkSign = true)
    @RequestMapping(method = RequestMethod.POST, value = "/modifyNickName")
    @ResponseBody
    public UserResponse modifyNickName(@RequestParam("nickName") String nickName, @RequestParam("userId") long userId) {
        UserResponse rsp = new UserResponse();
        User user = userService.selectById(userId);
        if (user != null) {
            String oldNickName = user.getNickName();//用于记录更换历史
            user.setNickName(nickName);
            user.setUpdateTime(new Date());
            userService.saveOrUpdate(user);
            rsp.setMsg(MsgConstant.SUCCESS_SAVE);
        } else {
            rsp.setMsg(MsgConstant.FAIL_SAVE);
        }
        rsp.setCode(ResponseCodeConstant.SUCCESS);
        rsp.setResult(null);
        return rsp;
    }

    /**
     * 获取个人中心信息接口
     */
    @AuthPassport(checkSign = true,checkLogin = true)
    @RequestMapping(method = RequestMethod.GET, value = "/getUserCenterInfo")
    @ResponseBody
    public UserResponse getUserCenterInfo(@RequestParam(value = "userId", required = true) Long userId) {
        UserResponse rsp = new UserResponse();

        Map<String, Object> data = new HashMap<>();
        User user = userService.selectById(userId);
        UserInfo userInfo = userInfoService.selectByUserId(userId);
        data.put("user", MapConventUtil.obj2Map(user));
        data.put("userInfo", MapConventUtil.obj2Map(userInfo));
        //获取服务器的自身的周一的时间
        data.put("systemSunDayEndTime",DateUtil.getThisWeekMonday(new DateTime().withMillisOfDay(0).toDate()));
        rsp.setResult(data);
        rsp.setCode(ResponseCodeConstant.SUCCESS);
        rsp.setMsg(MsgConstant.SUCCESS_SEARCH);


        return rsp;
    }

    /**
     * 获取个人可提现金额，根据unionid
     */
    @AuthPassport(checkSign = true,checkLogin = false)
    @RequestMapping(method = RequestMethod.GET, value = "/getUserBalanceByUnionId")
    @ResponseBody
    public UserResponse getUserBalanceByUnionId(@RequestParam(value = "unionid", required = true) String unionid) {
        UserResponse rsp = new UserResponse();

        Map<String, Object> data = new HashMap<>();
        UserExample example = new UserExample();
        example.or().andUnionIdEqualTo(unionid);
        List<User> users = userService.selectByExample(example);
        if (users.size()==0) {
            rsp.setMsg(MsgConstant.FAIL_SEARCH);
            rsp.setCode(ResponseCodeConstant.SUCCESS);
            return rsp;
        }
        User user = users.get(0);
        UserInfo userInfo = userInfoService.selectByUserId(user.getId());
        data.put("balance", userInfo!=null?userInfo.getBalance():0);
        rsp.setResult(data);
        rsp.setCode(ResponseCodeConstant.SUCCESS);
        rsp.setMsg(MsgConstant.SUCCESS_SEARCH);
        return rsp;
    }



    /**
     *  测试系统测试签名
     * @return
     */
    @AuthPassport(checkSign = true,checkLogin = true)
    @RequestMapping(method = RequestMethod.GET, value = "test")
    @ResponseBody
    public UserResponse test() {
        UserResponse response = new UserResponse();
        response.setCode(ResponseCodeConstant.SUCCESS);
        response.setResult(null);
        response.setMsg("恭喜你测试通过了！！");
        return response;
    }
    
    /**
     * 邀请好友
     * @param userId  好友ID
     * @param invitorUserId  邀请人ID即分享者
     * @param isNew   1-新用户   0-老用户
     * @return
     */
    @AuthPassport(checkSign = true,checkLogin = true)
    @RequestMapping(method = RequestMethod.POST, value = "/inviteFriend")
    @ResponseBody
    public UserResponse inviteFriend(@RequestParam(value = "friendUserId", required = true) Long userId,
                                          @RequestParam(value = "shareUserId", required = true) Long invitorUserId,
                                          @RequestParam(value = "isNew", required = true) Boolean isNew) {
    	
        UserResponse resp = new UserResponse();
        Map<String, Object> data = new HashMap<>();
        
        User friendUser  = userService.selectById(userId);
        if(friendUser == null || friendUser.getId() <= 0) {
        	resp.setCode(ResponseCodeConstant.PARAM_ID_FAIL);
 	        resp.setMsg("好友信息不存在");
 	        return resp;
        }
        
        User shareUser  = userService.selectById(invitorUserId);
        if(shareUser == null || shareUser.getId() <= 0) {
        	resp.setCode(ResponseCodeConstant.PARAM_ID_FAIL);
 	        resp.setMsg("分享人信息不存在");
 	        return resp;
        }
        
        if(userId.equals(invitorUserId)) {
        	resp.setCode(ResponseCodeConstant.SERVICE_FAIL);
 	        resp.setMsg("不能邀请自己");
 	        return resp;
        }
        
        
        try {
        	
        	UserInvite userInvite = userInviteService.findUserInvite(userId, invitorUserId, null, 
         		DateUtil.getCurrentStartTimeDaily(), DateUtil.getCurrentEndTimeDaily());
         
	        if(userInvite == null) { //当天不存在邀请记录才添加
	        	userInviteService.addUserInvite(userId, invitorUserId, isNew);
	        }
	         
	        resp.setCode(ResponseCodeConstant.SUCCESS);
	        resp.setMsg("邀请成功");
	        
        }catch(Exception ex) {
        	ex.printStackTrace();
        	resp.setCode(ResponseCodeConstant.FAIL);
        	resp.setMsg("邀请异常");
        }
       
        return resp;
    }

    @AuthPassport(checkSign = true,checkLogin = true)
    @PostMapping("/addUserShare")
    @ResponseBody
    public UserResponse addUserShare(@RequestParam(value = "userId", required = true) Long userId,
                                     @RequestParam(value = "shareTitle", required = true) String shareTitle,
                                     @RequestParam(value = "shareDesc", required = true) String shareDesc,
                                     @RequestParam(value = "shareLink", required = true) String shareLink,
                                     @RequestParam(value = "shareImgUrl", required = true) String shareImgUrl,
                                     @RequestParam(value = "shareType", required = true) String shareType,
                                     @RequestParam(value = "dataUrl", required = true) String dataUrl,
                                     @RequestParam(value = "contentSubType", required = true) Short contentSubType,
                                     @RequestParam(value = "contentType", required = true) Short contentType) {
        UserResponse rsp = new UserResponse();
        rsp.setResult(false);
        try {
            userShareService.addUserShare(userId,shareTitle,shareDesc,shareLink,shareImgUrl,shareType,dataUrl,contentType,contentSubType);
            rsp.setCode(ResponseCodeConstant.SUCCESS);
            rsp.setMsg(MsgConstant.SUCCESS_SAVE);
            rsp.setResult(true);
            return rsp;
        } catch (Exception e) {
            e.printStackTrace();
            rsp.setCode(ResponseCodeConstant.FAIL);
            rsp.setMsg(MsgConstant.FAIL_SAVE);
            return rsp;
        }
    }

}
