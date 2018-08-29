package com.artqiyi.tanqiu.welfare.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artqiyi.tanqiu.task.domain.TaskLogExample;
import com.artqiyi.tanqiu.welfare.domain.Welfare;
import com.artqiyi.tanqiu.welfare.domain.WelfareExample;
import com.artqiyi.tanqiu.welfare.domain.WelfareExample.Criteria;
import com.artqiyi.tanqiu.welfare.domain.WelfareReceiveLog;
import com.artqiyi.tanqiu.welfare.domain.WelfareReceiveLogExample;
import com.artqiyi.tanqiu.welfare.mapper.WelfareMapper;
import com.artqiyi.tanqiu.welfare.mapper.WelfareReceiveLogMapper;


@Service("welfareService")
public class WelfareService {
	private static Logger logger = LoggerFactory.getLogger(WelfareService.class);
    @Autowired
    private WelfareMapper welfareMapper;
    @Autowired
    private WelfareReceiveLogMapper welfareReceiveLogMapper;
    
    
    public Welfare getWelfare(Long welfareId) {
    	return welfareMapper.selectByPrimaryKey(welfareId);
    }
    
    public int addWelfareLog(WelfareReceiveLog welfareReceiveLog) {
    	return welfareReceiveLogMapper.insert(welfareReceiveLog);
    }
    
    public int updateWelfareLog(WelfareReceiveLog welfareReceiveLog) {
    	return welfareReceiveLogMapper.updateByPrimaryKey(welfareReceiveLog);
    }
    
    
    public List<Welfare> getRedpackWelfareList(){
    	WelfareExample example = new WelfareExample();
    	example.setOrderByClause(" welfare_type asc, id asc ");
    	Criteria criteria = example.createCriteria();
    	criteria.andWelfareTypeEqualTo(1);   //拆红包
    	example.or().andWelfareTypeEqualTo(2);
    	
    	return welfareMapper.selectByExample(example);
    }
    
    public Welfare getDailyWelfare(){
    	WelfareExample example = new WelfareExample();
    	Criteria criteria = example.createCriteria();
    	criteria.andWelfareTypeEqualTo(3);   //每日福利
    	List<Welfare> list = welfareMapper.selectByExample(example);
    	if(list != null && list.size() > 0) {
    		return list.get(0);
    	}
    	return null;
    }
    
    
    public List<WelfareReceiveLog> getReceiveLogList(Long userId, Long welfareId,  Date startDate, Date endDate) {
    	WelfareReceiveLogExample example = new WelfareReceiveLogExample();
    	com.artqiyi.tanqiu.welfare.domain.WelfareReceiveLogExample.Criteria criteria =example.createCriteria();
    	if(userId != null && userId >= 0) {
    		criteria.andUserIdEqualTo(userId);
    	}
    	if(welfareId != null && welfareId >= 0) {
    		criteria.andWelfareIdEqualTo(welfareId);
    	}
    	if(startDate != null) {
    		criteria.andCreateTimeGreaterThanOrEqualTo(startDate);
    	}
    	if(endDate != null) {
    		criteria.andCreateTimeLessThanOrEqualTo(endDate);
    	}
    	
    	return welfareReceiveLogMapper.selectByExample(example);
    }
    
    public List<WelfareReceiveLog> getRedpackReceiveLogList(Long userId, Long welfareId,  Date startDate, Date endDate) {
    	WelfareReceiveLogExample example = new WelfareReceiveLogExample();
    	com.artqiyi.tanqiu.welfare.domain.WelfareReceiveLogExample.Criteria criteria =example.createCriteria();
    	com.artqiyi.tanqiu.welfare.domain.WelfareReceiveLogExample.Criteria criteria2 =example.createCriteria();
    	
    	if(userId != null && userId >= 0) {
    		criteria.andUserIdEqualTo(userId);
    		criteria2.andUserIdEqualTo(userId);
    	}
    	if(welfareId != null && welfareId >= 0) {
    		criteria.andWelfareIdEqualTo(welfareId);
    		criteria2.andWelfareIdEqualTo(welfareId);
    	}
    	if(startDate != null) {
    		criteria.andCreateTimeGreaterThanOrEqualTo(startDate);
    		criteria2.andCreateTimeGreaterThanOrEqualTo(startDate);
    	}
    	if(endDate != null) {
    		criteria.andCreateTimeLessThanOrEqualTo(endDate);
    		criteria2.andCreateTimeLessThanOrEqualTo(endDate);
    	}
    	
    	criteria.andWelfareTypeEqualTo(1);
    	criteria2.andWelfareTypeEqualTo(2);
    	example.or(criteria2);
    	
    	return welfareReceiveLogMapper.selectByExample(example);
    }
    
    
    
    /**
     * 按条件计算记录次数
     * @param userId
     * @param taskId
     * @param startDate
     * @param endDate
     * @return
     */
    public int countWelfareReceiveLog(Long userId, Long welfareId, Date startDate, Date endDate) {
    	WelfareReceiveLogExample example = new WelfareReceiveLogExample();
        com.artqiyi.tanqiu.welfare.domain.WelfareReceiveLogExample.Criteria criteria = example.createCriteria();
        if(userId != null && userId > 0) {
        	criteria.andUserIdEqualTo(userId);
        }
        if(welfareId != null && welfareId > 0) {
        	criteria.andWelfareIdEqualTo(welfareId);
        }
        if (startDate != null) {
            criteria.andCreateTimeGreaterThanOrEqualTo(startDate);
        }
        if (endDate != null) {
            criteria.andCreateTimeLessThanOrEqualTo(endDate);
        }
      
        return welfareReceiveLogMapper.countByExample(example);
    }
	    

}
