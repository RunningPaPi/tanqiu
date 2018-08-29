package com.artqiyi.tanqiu.game.impl;

import com.artqiyi.tanqiu.game.IGameBreakAgainstRecordsService;
import com.artqiyi.tanqiu.game.domain.GameBreakAgainstRecords;
import com.artqiyi.tanqiu.game.domain.GameBreakAgainstRecordsExample;
import com.artqiyi.tanqiu.game.mapper.GameBreakAgainstRecordsMapper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: tanqiu
 * Author: chencunjun  <1078027943@qq.com>
 * Create On: 2018/7/3
 * Modify On: 2018/7/3 by chencunjun
 */
@Service
public class GameBreakAgainstRecordsServiceImpl implements IGameBreakAgainstRecordsService {
    @Autowired
    private GameBreakAgainstRecordsMapper gameBreakAgainstRecordsMapper;

    @Override
    public PageInfo<GameBreakAgainstRecords> page(int page, int pageSize, GameBreakAgainstRecordsExample example) {
        return null;
    }

    @Override
    public long saveOrUpdate(GameBreakAgainstRecords gameBreakAgainstRecords) {
        if(null==gameBreakAgainstRecords.getId() || gameBreakAgainstRecords.getId()==0){
            gameBreakAgainstRecordsMapper.insertSelective(gameBreakAgainstRecords);
        }else{
            gameBreakAgainstRecordsMapper.updateByPrimaryKeySelective(gameBreakAgainstRecords);
        }
        return 0;
    }

    @Override
    public int deleteById(long id) {
        return 0;
    }

    @Override
    public int deleteByExample(GameBreakAgainstRecordsExample gameBreakAgainstRecordsExample) {
        return 0;
    }

    @Override
    public int updateByExample(GameBreakAgainstRecords gameBreakAgainstRecords, GameBreakAgainstRecordsExample gameBreakAgainstRecordsExample) {
        return 0;
    }

    @Override
    public List<GameBreakAgainstRecords> selectByExample(GameBreakAgainstRecordsExample gameBreakAgainstRecordsExample) {
        return null;
    }

    @Override
    public GameBreakAgainstRecords selectById(long id) {
        return null;
    }

    @Override
    public long countByExample(GameBreakAgainstRecordsExample gameBreakAgainstRecordsExample) {
        return 0;
    }
}
