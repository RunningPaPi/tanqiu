package com.artqiyi.tanqiu.game.impl;

import com.artqiyi.tanqiu.game.IGameRedpacketRewardRecordService;
import com.artqiyi.tanqiu.game.domain.GameRedpacketRewardRecord;
import com.artqiyi.tanqiu.game.domain.GameRedpacketRewardRecordExample;
import com.artqiyi.tanqiu.game.mapper.GameRedpacketRewardRecordMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameRedpacketRewardRecordImpl implements IGameRedpacketRewardRecordService {
    @Autowired
    private GameRedpacketRewardRecordMapper mapper;
    @Override
    public PageInfo<GameRedpacketRewardRecord> page(int page, int pageSize, GameRedpacketRewardRecordExample example) {
        PageHelper.startPage(page,pageSize);
        List<GameRedpacketRewardRecord> gameBreakRecords = mapper.selectByExample(example);
        return new PageInfo<>(gameBreakRecords);
    }

    @Override
    public long saveOrUpdate(GameRedpacketRewardRecord gameBreakUserRecords) {
        if(null==gameBreakUserRecords.getId() || gameBreakUserRecords.getId()==0){
            mapper.insertSelective(gameBreakUserRecords);
        }else{
            mapper.updateByPrimaryKeySelective(gameBreakUserRecords);
        }
        return gameBreakUserRecords.getId();
    }

    @Override
    public int deleteById(long id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteByExample(GameRedpacketRewardRecordExample example) {
        return mapper.deleteByExample(example);
    }

    @Override
    public int updateByExample(GameRedpacketRewardRecord records, GameRedpacketRewardRecordExample example) {
        return mapper.updateByExample(records,example);
    }

    @Override
    public List<GameRedpacketRewardRecord> selectByExample(GameRedpacketRewardRecordExample example) {
        return mapper.selectByExample(example);
    }

    @Override
    public GameRedpacketRewardRecord selectById(long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public long countByExample(GameRedpacketRewardRecordExample example) {
        return mapper.countByExample(example);
    }

    /**
     * 获取用户上一次是否获得奖励
     * @param userId
     * @return
     */
    @Override
    public Map repacketRewardNotice(Long userId) {
        Map<String, Object> result = new HashMap<>();
        result.put("hasNotice", false);
        result.put("recordVo", null);
        GameRedpacketRewardRecordExample example = new GameRedpacketRewardRecordExample();
        example.setOrderByClause("create_time DESC");
        example.or().andCreateTimeLessThanOrEqualTo(new Date()).andUserIdEqualTo(userId).andIsReadNotEqualTo(true);
        List<GameRedpacketRewardRecord> rewardRecords = mapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(rewardRecords)) {
            result.put("hasNotice", true);
            result.put("recordVo", rewardRecords.get(0));
        }
        return result;
    }

    /**
     * 用户已阅读消息
     * @param recordId
     */
    @Override
    public void repacketRewardNoticeRead(Long recordId) {
        GameRedpacketRewardRecord rewardRecord = mapper.selectByPrimaryKey(recordId);
        if (rewardRecord != null) {
            rewardRecord.setIsRead(true);
            mapper.updateByPrimaryKeySelective(rewardRecord);
        }else{
            throw new RuntimeException("【保存消息异常】没有该条记录");
        }
    }
}
