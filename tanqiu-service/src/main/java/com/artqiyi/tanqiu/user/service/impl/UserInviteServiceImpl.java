package com.artqiyi.tanqiu.user.service.impl;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/04
 * Modify On: 2018/07/04 11:35 by wufuchang
 */

import com.artqiyi.tanqiu.common.util.DateUtil;
import com.artqiyi.tanqiu.game.domain.GameBreakAgainstRecordsExample.Criteria;
import com.artqiyi.tanqiu.user.domain.User;
import com.artqiyi.tanqiu.user.domain.UserInvite;
import com.artqiyi.tanqiu.user.domain.UserInviteExample;
import com.artqiyi.tanqiu.user.mapper.UserInviteMapper;
import com.artqiyi.tanqiu.user.service.IUserInviteService;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Date;
import java.util.List;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class UserInviteServiceImpl implements IUserInviteService {
    @Autowired
    private UserInviteMapper userInviteMapper;

    @Override
    public List<Long> getFriendIdList(Long userId) {
        UserInviteExample userInviteExample = new UserInviteExample();
        userInviteExample.or().andInvitorUserIdEqualTo(userId);//邀请者
        List<UserInvite> userInvitees = userInviteMapper.selectByExample(userInviteExample);
        userInviteExample.clear();
        userInviteExample.or().andUserIdEqualTo(userId);
        List<UserInvite> userInvitors = userInviteMapper.selectByExample(userInviteExample);
        //去重
        List<Long> inviteeIds = userInvitees.stream().map(UserInvite::getUserId).collect(Collectors.toList());
        List<Long> invitorIds = userInvitors.stream().map(UserInvite::getInvitorUserId).collect(Collectors.toList());
        inviteeIds.addAll(invitorIds);
        Set<Long> set = inviteeIds.stream().collect(Collectors.toSet());
        return new ArrayList<Long>(set);
    }


    /**
     * 添加邀请记录
     * @param userId
     * @param invitorUserId
     * @param isNew
     */
    public void addUserInvite(Long userId, Long invitorUserId, Boolean isNew) {
    	Date now = new Date();
    	UserInvite userInvite = new UserInvite();
    	userInvite.setUserId(userId);
    	userInvite.setInvitorUserId(invitorUserId);
    	userInvite.setIsNew(isNew);
    	userInvite.setCreateTime(now);
    	userInvite.setUpdateTime(now);
    	userInviteMapper.insert(userInvite);
    }


    /**
     * 查找邀请记录
     * @param userId
     * @param isNew
     * @param invitorUserId
     * @param startDate
     * @param endDate
     * @return
     */
    public UserInvite findUserInvite(Long userId, Long invitorUserId, Boolean isNew, Date startDate, Date endDate) {
    	  UserInviteExample userInviteExample = new UserInviteExample();
    	  com.artqiyi.tanqiu.user.domain.UserInviteExample.Criteria criteria = userInviteExample.createCriteria();
    	  criteria.andUserIdEqualTo(userId);
    	  criteria.andInvitorUserIdEqualTo(invitorUserId);

    	  if(isNew != null) {
    		  criteria.andIsNewEqualTo(isNew);
    	  }

    	  if(startDate != null) {
    		  criteria.andCreateTimeGreaterThanOrEqualTo(startDate);
    	  }

    	  if(endDate != null) {
    		  criteria.andCreateTimeLessThanOrEqualTo(endDate);
    	  }

    	  List<UserInvite> userInviteList = userInviteMapper.selectByExample(userInviteExample);
    	  if(userInviteList != null && userInviteList.size() > 0) {
    		  return userInviteList.get(0);
    	  }

    	  return null;

    }

	@Override
	public long countFriendNum(Long invitorUserId, Boolean isNew, Date startDate, Date endDate) {
		UserInviteExample userInviteExample = new UserInviteExample();
	   	com.artqiyi.tanqiu.user.domain.UserInviteExample.Criteria criteria = userInviteExample.createCriteria();
	   	criteria.andInvitorUserIdEqualTo(invitorUserId);
	
	   	if(isNew != null) {
	   		criteria.andIsNewEqualTo(isNew);
	   	}
	   	if(startDate != null) {
	   		criteria.andCreateTimeGreaterThanOrEqualTo(startDate);
	   	}
	   	if(endDate != null) {
	   		criteria.andCreateTimeLessThanOrEqualTo(endDate);
	   	}
	   	 
	   	return userInviteMapper.countByExample(userInviteExample);
	}
	
	
	/**
	 * 该用户当天邀请的好友ID集合
	 * @param shareUserId
	 * @return
	 */
	public List<Long> getFriendIdListInDay(Long shareUserId) {
		UserInviteExample userInviteExample = new UserInviteExample();
		userInviteExample.setOrderByClause("create_time asc");
	    userInviteExample.or().andInvitorUserIdEqualTo(shareUserId)
	     		.andCreateTimeBetween(DateUtil.getCurrentStartTimeDaily(), DateUtil.getCurrentEndTimeDaily());
	     
	    List<UserInvite> inviteLIst = userInviteMapper.selectByExample(userInviteExample);
	        
		List<Long> list = Lists.transform(inviteLIst, new Function<UserInvite, Long>() {
	           @Override
	           public Long apply(UserInvite input) {
	               return input.getUserId();
	           }
	        });
       return list;
	}
	

}
