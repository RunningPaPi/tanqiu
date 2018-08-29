package com.artqiyi.tanqiu.game.impl;

import com.artqiyi.tanqiu.game.IGameModelService;
import com.artqiyi.tanqiu.game.domain.GameModel;
import com.artqiyi.tanqiu.game.domain.GameModelExample;
import com.artqiyi.tanqiu.game.mapper.GameModelMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameModelServiceImpl implements IGameModelService {
    @Autowired
    private GameModelMapper mapper;
    @Override
    public PageInfo<GameModel> page(int page, int pageSize, GameModelExample example) {
        PageHelper.startPage(page,pageSize);
        List<GameModel> gameBreakRecords = mapper.selectByExample(example);
        return new PageInfo<>(gameBreakRecords);
    }

    @Override
    public long saveOrUpdate(GameModel gameBreakUserRecords) {
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
    public int deleteByExample(GameModelExample example) {
        return mapper.deleteByExample(example);
    }

    @Override
    public int updateByExample(GameModel records, GameModelExample example) {
        return mapper.updateByExample(records,example);
    }

    @Override
    public List<GameModel> selectByExample(GameModelExample example) {
        return mapper.selectByExample(example);
    }

    @Override
    public GameModel selectById(long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public long countByExample(GameModelExample example) {
        return mapper.countByExample(example);
    }

    @Override
    public List<GameModel> getAllGame() {
        GameModelExample example = new GameModelExample();
        example.or().andDeletedNotEqualTo(true);
        return mapper.selectByExample(example);
    }

    @Override
    public GameModel getByGameModelKey(String gameKey) {
        GameModelExample breakGameExample = new GameModelExample();
        breakGameExample.or().andGameModelKeyEqualTo(gameKey);
        List<GameModel> games = mapper.selectByExample(breakGameExample);
        if (games != null && !games.isEmpty()) {
            return games.get(0);
        }
        return null;
    }
}
