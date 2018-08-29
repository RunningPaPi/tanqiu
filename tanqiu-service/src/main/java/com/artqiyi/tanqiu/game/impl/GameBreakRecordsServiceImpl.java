package com.artqiyi.tanqiu.game.impl;

import com.artqiyi.tanqiu.game.IGameBreakRecordsService;
import com.artqiyi.tanqiu.game.domain.GameBreakRecords;
import com.artqiyi.tanqiu.game.domain.GameBreakRecordsExample;
import com.artqiyi.tanqiu.game.mapper.GameBreakRecordsMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameBreakRecordsServiceImpl implements IGameBreakRecordsService {
    @Autowired
    private GameBreakRecordsMapper mapper;
    @Override
    public PageInfo<GameBreakRecords> page(int page, int pageSize, GameBreakRecordsExample example) {
        PageHelper.startPage(page,pageSize);
        List<GameBreakRecords> gameBreakRecords = mapper.selectByExample(example);
        return new PageInfo<>(gameBreakRecords);
    }

    @Override
    public long saveOrUpdate(GameBreakRecords gameBreakRecords) {
        if(null==gameBreakRecords.getId() || gameBreakRecords.getId()==0){
            mapper.insertSelective(gameBreakRecords);
        }else{
            mapper.updateByPrimaryKeySelective(gameBreakRecords);
        }
        return gameBreakRecords.getId();
    }

    @Override
    public int deleteById(long id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteByExample(GameBreakRecordsExample gameBreakRecordsExample) {
        return mapper.deleteByExample(gameBreakRecordsExample);
    }

    @Override
    public int updateByExample(GameBreakRecords gameBreakRecords, GameBreakRecordsExample gameBreakRecordsExample) {
        return mapper.updateByExample(gameBreakRecords,gameBreakRecordsExample);
    }

    @Override
    public List<GameBreakRecords> selectByExample(GameBreakRecordsExample gameBreakRecordsExample) {
        return mapper.selectByExample(gameBreakRecordsExample);
    }

    @Override
    public GameBreakRecords selectById(long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public long countByExample(GameBreakRecordsExample gameBreakRecordsExample) {
        return mapper.countByExample(gameBreakRecordsExample);
    }
}
