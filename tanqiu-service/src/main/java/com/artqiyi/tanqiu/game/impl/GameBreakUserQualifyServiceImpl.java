package com.artqiyi.tanqiu.game.impl;

import com.artqiyi.tanqiu.game.IGameBreakUserQualifyService;
import com.artqiyi.tanqiu.game.domain.GameBreakUserQualify;
import com.artqiyi.tanqiu.game.domain.GameBreakUserQualifyExample;
import com.artqiyi.tanqiu.game.mapper.GameBreakUserQualifyMapper;
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
public class GameBreakUserQualifyServiceImpl implements IGameBreakUserQualifyService {
    @Autowired
    private GameBreakUserQualifyMapper mapper;

    @Override
    public PageInfo<GameBreakUserQualify> page(int page, int pageSize, GameBreakUserQualifyExample example) {
        return null;
    }

    @Override
    public long saveOrUpdate(GameBreakUserQualify gameBreakUserQualify) {
        if(null==gameBreakUserQualify.getId() || gameBreakUserQualify.getId()==0){
            mapper.insertSelective(gameBreakUserQualify);
        }else{
            mapper.updateByPrimaryKeySelective(gameBreakUserQualify);
        }
        return gameBreakUserQualify.getId();
    }

    @Override
    public int deleteById(long id) {
        return 0;
    }

    @Override
    public int deleteByExample(GameBreakUserQualifyExample gameBreakUserQualifyExample) {
        return 0;
    }

    @Override
    public int updateByExample(GameBreakUserQualify gameBreakUserQualify, GameBreakUserQualifyExample gameBreakUserQualifyExample) {
        return 0;
    }

    @Override
    public List<GameBreakUserQualify> selectByExample(GameBreakUserQualifyExample gameBreakUserQualifyExample) {
        return mapper.selectByExample(gameBreakUserQualifyExample);
    }

    @Override
    public GameBreakUserQualify selectById(long id) {
        return null;
    }

    @Override
    public long countByExample(GameBreakUserQualifyExample gameBreakUserQualifyExample) {
        return 0;
    }

    @Override
    public GameBreakUserQualify getLatestUserPrize(Long userId, Long gameId, String gameNo) {
        GameBreakUserQualifyExample example = new GameBreakUserQualifyExample();
        example.or().andUserIdEqualTo(userId).andGameModelIdEqualTo(gameId).andGameNoEqualTo(gameNo);
        List<GameBreakUserQualify> breakGameUserQualifies = mapper.selectByExample(example);
        if (breakGameUserQualifies != null && !breakGameUserQualifies.isEmpty()) {
            return breakGameUserQualifies.get(0);
        }
        return null;
    }
}
