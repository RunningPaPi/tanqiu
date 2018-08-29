package com.artqiyi.tanqiu.payment.service;

import com.artqiyi.tanqiu.common.util.PaginationUtil;
import com.artqiyi.tanqiu.common.constant.SystemConstant;
import com.artqiyi.tanqiu.common.util.DateUtil;
import com.artqiyi.tanqiu.payment.domain.CoinTranslog;
import com.artqiyi.tanqiu.payment.domain.CoinTranslogExample;
import com.artqiyi.tanqiu.payment.domain.CoinTranslogExample.Criteria;
import com.artqiyi.tanqiu.payment.mapper.CoinTranslogMapper;
import com.artqiyi.tanqiu.payment.mapper.ext.CustomCoinTranslogMapper;
import com.artqiyi.tanqiu.user.domain.User;
import com.artqiyi.tanqiu.user.domain.UserInfo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.ToIntFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class TransLogService {
    private static Logger logger = LoggerFactory.getLogger(TransLogService.class);
    @Autowired
    private CoinTranslogMapper coinTranslogMapper;
    
    @Autowired
    private CustomCoinTranslogMapper customCoinTranslogMapper;

    public int addTranslog(CoinTranslog translog){
        return coinTranslogMapper.insertSelective(translog);
    }

    public List<CoinTranslog> selectByExample(CoinTranslogExample coinTranslogExample) {
        return coinTranslogMapper.selectByExample(coinTranslogExample);
    }

    /**
     * 写交易流水
     * @param translog
     */

    @Transactional(readOnly = false,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public void addTransLog(CoinTranslog translog){
        coinTranslogMapper.insertSelective(translog);
    }

    
    /**
     * 获取用户总奖励--奖金
     * @param userId 用户ID
     * @return
     */
    public Integer getSumRewardByUserId(Long userId) {
    	return customCoinTranslogMapper.getSumRewardByUserId(userId);
    }

    public static void main(String[] args) {
        Integer a = 9995;
        System.out.println(BigDecimal.valueOf(a).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
        long divide =  BigDecimal.valueOf(a).multiply(new BigDecimal(10)).divide(new BigDecimal(100), 0, BigDecimal.ROUND_DOWN).longValue();
        System.out.println(divide);

        System.out.println(BigDecimal.ZERO);
    }

    /**
     * 生成提现交易流水
     * @param amount
     * @param user
     * @param userInfo
     */
    public void generateTransLogForWithdraw(Long amount, User user, UserInfo userInfo) {
        CoinTranslog coinTranslog = new CoinTranslog();
        coinTranslog.setUserId(userInfo.getUserId());
        coinTranslog.setUserName(user.getNickName());
        coinTranslog.setAccountType(SystemConstant.ACC_TYPE_BALANCE);
        coinTranslog.setTransType(SystemConstant.TRANS_TYPE_WITHDRAW);
        coinTranslog.setTransFlag(SystemConstant.TRANS_DIRECT_OUTCOME);
        coinTranslog.setTransAmount(Integer.valueOf(amount+""));
        coinTranslog.setBalance(Integer.valueOf(userInfo.getBalance()+""));
        coinTranslog.setRemark("余额提现");
        coinTranslog.setTransTime(new Date());
        addTransLog(coinTranslog);
    }

    /**
     * 企业转账失败退款流水
     * @param withdrawAmount
     * @param user
     * @param userInfo
     */
    public void generateTransLogForTransferFailed(Long withdrawAmount, User user, UserInfo userInfo) {
        CoinTranslog coinTranslog = new CoinTranslog();
        coinTranslog.setUserId(userInfo.getUserId());
        coinTranslog.setUserName(user.getNickName());
        coinTranslog.setAccountType(SystemConstant.ACC_TYPE_BALANCE);
        coinTranslog.setTransType(SystemConstant.TRANS_TYPE_WITHDRAW_TRANSFER_FAILED);
        coinTranslog.setTransFlag(SystemConstant.TRANS_DIRECT_INCOM);
        coinTranslog.setTransAmount(Integer.valueOf(withdrawAmount+""));
        coinTranslog.setBalance(Integer.valueOf(userInfo.getBalance()+""));
        coinTranslog.setRemark("提现失败");
        coinTranslog.setTransTime(new Date());
        addTransLog(coinTranslog);
    }



    /**
     * 分页查询交易记录
     * @param userId
     * @param accountType
     * @param transType
     * @param transFlag
     * @return
     */
    public List<CoinTranslog> findTransLogList(Integer pageNum, Integer pageSize, Long userId, Integer accountType, String transType, Integer transFlag) {
    	if (pageNum == null) {
			pageNum = 1;
		}

    	if (pageSize == null ) {
			pageSize = 20;
		}

		Integer startNum = PaginationUtil.getStartNum(pageNum, pageSize, 20);

    	CoinTranslogExample example = new CoinTranslogExample();
    	example.setOrderByClause("trans_time desc");
		example.setOffset(startNum);
		example.setLimit(pageSize);

        Criteria criteria = example.createCriteria();

        if(userId != null && userId > 0) {
        	criteria.andUserIdEqualTo(userId);
        }
        if(accountType != null && accountType > 0) {
        	criteria.andAccountTypeEqualTo(accountType);
        }
        if(transType != null && !"".equals(transType)) {
        	criteria.andTransTypeEqualTo(transType);
        }
        if(transFlag != null && transFlag > 0) {
        	criteria.andTransFlagEqualTo(transFlag);
        }

        return coinTranslogMapper.selectByExample(example);
    }




    /**
     * 单人模式比赛结算趣币奖励流水
     * @param prizeCoin
     * @param userNickName
     * @param userInfo
     */
    public void generateTransLogForGameSingleEnd(Integer prizeCoin, String userNickName, UserInfo userInfo) {
        CoinTranslog coinTranslog = new CoinTranslog();
        coinTranslog.setUserId(userInfo.getUserId());
        coinTranslog.setUserName(userNickName);
        coinTranslog.setAccountType(SystemConstant.ACC_TYPE_GOLDCOIN);
        coinTranslog.setTransType(SystemConstant.TRANS_TYPE_GAME_SINGLE_END);
        coinTranslog.setTransFlag(SystemConstant.TRANS_DIRECT_INCOM);
        coinTranslog.setTransAmount(prizeCoin);
        coinTranslog.setBalance(Integer.valueOf(userInfo.getCoin()+""));
        coinTranslog.setRemark("疯狂模式奖励");
        coinTranslog.setTransTime(new Date());
        addTransLog(coinTranslog);
    }
    
    /**
     * 生成福利奖励流水
     * @param amount
     * @param user
     * @param userInfo
     */
    public void generateTransLog4Welfare(Integer accountType, Integer amount, String transType, String remark, User user, UserInfo userInfo) {
        CoinTranslog coinTranslog = new CoinTranslog();
        coinTranslog.setUserId(userInfo.getUserId());
        coinTranslog.setUserName(user.getNickName());
        coinTranslog.setAccountType(accountType);
        coinTranslog.setTransType(transType);
        coinTranslog.setTransFlag(SystemConstant.TRANS_DIRECT_INCOM); //获得
        coinTranslog.setTransAmount(amount);
        if(accountType == 1) {
        	 coinTranslog.setBalance(Integer.valueOf(userInfo.getCoin()+""));
        }else if(accountType == 3) {
        	 coinTranslog.setBalance(Integer.valueOf(userInfo.getBalance()+""));
        }
       
        coinTranslog.setRemark(remark);
        coinTranslog.setTransTime(new Date());
        addTransLog(coinTranslog);
    }

    public void generateTransLog4RedpacketModelBegin(User user, UserInfo userInfo, Integer cost) {
        CoinTranslog coinTranslog = new CoinTranslog();
        coinTranslog.setUserId(userInfo.getUserId());
        coinTranslog.setUserName(user.getNickName());
        coinTranslog.setAccountType(SystemConstant.ACC_TYPE_GOLDCOIN);
        coinTranslog.setTransType(SystemConstant.TRANS_TYPE_GAME_REDPACKET_BEGIN);
        coinTranslog.setTransFlag(SystemConstant.TRANS_DIRECT_OUTCOME);
        coinTranslog.setTransAmount(cost);
        coinTranslog.setBalance(userInfo.getCoin());
        coinTranslog.setRemark("参加红包赛");
        coinTranslog.setTransTime(new Date());
        addTransLog(coinTranslog);

    }

    public void generateTransLog4RedpacketModelEnd(UserInfo userInfo, User user, Integer prizeNum,Integer rank) {
        CoinTranslog coinTranslog = new CoinTranslog();
        coinTranslog.setUserId(userInfo.getUserId());
        coinTranslog.setUserName(user.getNickName());
        coinTranslog.setAccountType(SystemConstant.ACC_TYPE_BALANCE);
        coinTranslog.setTransType(SystemConstant.TRANS_TYPE_GAME_REDPACKET_END_REWARD);
        coinTranslog.setTransFlag(SystemConstant.TRANS_DIRECT_INCOM);
        coinTranslog.setTransAmount(prizeNum);
        coinTranslog.setBalance(Integer.valueOf(userInfo.getBalance()+""));
        coinTranslog.setRemark("红包赛获得第"+rank+"名");
        coinTranslog.setTransTime(new Date());
        addTransLog(coinTranslog);
    }
}
