package com.artqiyi.tanqiu.user.service;

import com.artqiyi.tanqiu.base.service.IBaseService;
import com.artqiyi.tanqiu.user.domain.UserInfo;
import com.artqiyi.tanqiu.user.domain.UserInfoExample;

/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: tanqiu
 * Author: chencunjun  <1078027943@qq.com>
 * Create On: 2018/4/25
 * Modify On: 2018/4/25 by chencunjun
 */
public interface IUserInfoService extends IBaseService<UserInfo,UserInfoExample>{
    UserInfo selectByUserId(long userId);
    //邀请码是否已存在
    boolean isInviteCodeExist(String inviteCode);

    /**
     * 绑定支付宝
     * @param userId
     * @param alipayAccount
     * @param alipayRealname
     */
    void bindAlipayAccount(Long userId, String alipayAccount, String alipayRealname);


    boolean isUserRandomNoExist(Integer userRandomNo);
}
