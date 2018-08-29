package com.artqiyi.tanqiu.game.mapper;


import com.artqiyi.tanqiu.game.domain.GameBreakUserQualify;
import com.artqiyi.tanqiu.game.domain.GameBreakUserQualifyExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GameBreakUserQualifyMapper {
    long countByExample(GameBreakUserQualifyExample example);

    int deleteByExample(GameBreakUserQualifyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(GameBreakUserQualify record);

    int insertSelective(GameBreakUserQualify record);

    List<GameBreakUserQualify> selectByExample(GameBreakUserQualifyExample example);

    GameBreakUserQualify selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") GameBreakUserQualify record, @Param("example") GameBreakUserQualifyExample example);

    int updateByExample(@Param("record") GameBreakUserQualify record, @Param("example") GameBreakUserQualifyExample example);

    int updateByPrimaryKeySelective(GameBreakUserQualify record);

    int updateByPrimaryKey(GameBreakUserQualify record);
}