package com.artqiyi.tanqiu.game.mapper;


import com.artqiyi.tanqiu.game.domain.GameBreakRecoverCost;
import com.artqiyi.tanqiu.game.domain.GameBreakRecoverCostExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GameBreakRecoverCostMapper {
    long countByExample(GameBreakRecoverCostExample example);

    int deleteByExample(GameBreakRecoverCostExample example);

    int deleteByPrimaryKey(Long id);

    int insert(GameBreakRecoverCost record);

    int insertSelective(GameBreakRecoverCost record);

    List<GameBreakRecoverCost> selectByExample(GameBreakRecoverCostExample example);

    GameBreakRecoverCost selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") GameBreakRecoverCost record, @Param("example") GameBreakRecoverCostExample example);

    int updateByExample(@Param("record") GameBreakRecoverCost record, @Param("example") GameBreakRecoverCostExample example);

    int updateByPrimaryKeySelective(GameBreakRecoverCost record);

    int updateByPrimaryKey(GameBreakRecoverCost record);
}