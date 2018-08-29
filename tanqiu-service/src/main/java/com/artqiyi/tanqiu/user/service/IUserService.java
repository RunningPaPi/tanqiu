package com.artqiyi.tanqiu.user.service;

import com.artqiyi.tanqiu.base.service.IBaseService;
import com.artqiyi.tanqiu.user.domain.User;
import com.artqiyi.tanqiu.user.domain.UserExample;
import com.artqiyi.tanqiu.user.service.vo.UserVo;

/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: tanqiu
 * Author: chencunjun  <1078027943@qq.com>
 * Create On: 2018/4/23
 * Modify On: 2018/4/23 by chencunjun
 */
public interface IUserService extends IBaseService<User,UserExample>{
    //是否登录
    boolean hasLogin(String token);
    //根据手机获取用户
    User getByPhone(String phone);

    UserVo getByUnionId(String unionId);
}
