package com.artqiyi.tanqiu.game.impl;

import com.artqiyi.tanqiu.game.IGameBreakRecoverCostService;
import com.artqiyi.tanqiu.game.domain.GameBreakRecoverCost;
import com.artqiyi.tanqiu.game.domain.GameBreakRecoverCostExample;
import com.artqiyi.tanqiu.game.mapper.GameBreakRecoverCostMapper;
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
public class GameBreakRecoverCostServiceImpl implements IGameBreakRecoverCostService {
    @Autowired
    GameBreakRecoverCostMapper mapper;

    @Override
    public PageInfo<GameBreakRecoverCost> page(int page, int pageSize, GameBreakRecoverCostExample example) {
        return null;
    }

    @Override
    public long saveOrUpdate(GameBreakRecoverCost gameBreakRecoverCost) {
        if(null==gameBreakRecoverCost.getId() || gameBreakRecoverCost.getId() == 0){
            mapper.insertSelective(gameBreakRecoverCost);
        }else{
            mapper.updateByPrimaryKeySelective(gameBreakRecoverCost);
        }
        return gameBreakRecoverCost.getId();
    }

    @Override
    public int deleteById(long id) {
        return 0;
    }

    @Override
    public int deleteByExample(GameBreakRecoverCostExample gameBreakRecoverCostExample) {
        return 0;
    }

    @Override
    public int updateByExample(GameBreakRecoverCost gameBreakRecoverCost, GameBreakRecoverCostExample gameBreakRecoverCostExample) {
        return 0;
    }

    @Override
    public List<GameBreakRecoverCost> selectByExample(GameBreakRecoverCostExample gameBreakRecoverCostExample) {
        return mapper.selectByExample(gameBreakRecoverCostExample);
    }

    @Override
    public GameBreakRecoverCost selectById(long id) {
        return null;
    }

    @Override
    public long countByExample(GameBreakRecoverCostExample gameBreakRecoverCostExample) {
        return 0;
    }

    @Override
    public List<GameBreakRecoverCost> getByGameModelKey(String gameKey) {
        GameBreakRecoverCostExample example = new GameBreakRecoverCostExample();
        example.or().andGameModelKeyEqualTo(gameKey);
        List<GameBreakRecoverCost> breakGameRecoverCosts = mapper.selectByExample(example);
        if (breakGameRecoverCosts!=null && breakGameRecoverCosts.size()>0){
            return breakGameRecoverCosts;
        }
        return null;
    }

    @Override
    public GameBreakRecoverCost getRoundCost(long gameId, int round) {
        GameBreakRecoverCostExample example = new GameBreakRecoverCostExample();
        example.or().andGameModelIdEqualTo(gameId).andGameRoundEqualTo(round);
        List<GameBreakRecoverCost> breakGameRecoverCosts = mapper.selectByExample(example);
        if (breakGameRecoverCosts!=null && breakGameRecoverCosts.size()>0){
            return breakGameRecoverCosts.get(0);
        }
        return null;
    }
}
