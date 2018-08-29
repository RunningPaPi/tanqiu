package com.artqiyi.tanqiu.game.impl;

import com.artqiyi.tanqiu.game.IGameBreakRecoverRecordsService;
import com.artqiyi.tanqiu.game.domain.GameBreakRecoverRecords;
import com.artqiyi.tanqiu.game.domain.GameBreakRecoverRecordsExample;
import com.artqiyi.tanqiu.game.mapper.GameBreakRecoverRecordsMapper;
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
public class GameBreakRecoverRecordsServiceImpl implements IGameBreakRecoverRecordsService {
    @Autowired
    private GameBreakRecoverRecordsMapper gameBreakRecoverRecordsMapper;

    @Override
    public PageInfo<GameBreakRecoverRecords> page(int page, int pageSize, GameBreakRecoverRecordsExample example) {
        return null;
    }

    @Override
    public long saveOrUpdate(GameBreakRecoverRecords gameBreakRecoverRecords) {
        if(null==gameBreakRecoverRecords.getId() || gameBreakRecoverRecords.getId()==0){
            gameBreakRecoverRecordsMapper.insertSelective(gameBreakRecoverRecords);
        }else{
            gameBreakRecoverRecordsMapper.updateByPrimaryKeySelective(gameBreakRecoverRecords);
        }
        return gameBreakRecoverRecords.getId();
    }

    @Override
    public int deleteById(long id) {
        return 0;
    }

    @Override
    public int deleteByExample(GameBreakRecoverRecordsExample gameBreakRecoverRecordsExample) {
        return 0;
    }

    @Override
    public int updateByExample(GameBreakRecoverRecords gameBreakRecoverRecords, GameBreakRecoverRecordsExample gameBreakRecoverRecordsExample) {
        return 0;
    }

    @Override
    public List<GameBreakRecoverRecords> selectByExample(GameBreakRecoverRecordsExample gameBreakRecoverRecordsExample) {
        return null;
    }

    @Override
    public GameBreakRecoverRecords selectById(long id) {
        return null;
    }

    @Override
    public long countByExample(GameBreakRecoverRecordsExample gameBreakRecoverRecordsExample) {
        return 0;
    }
}
