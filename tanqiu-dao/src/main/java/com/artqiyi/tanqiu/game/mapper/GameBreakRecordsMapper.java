package com.artqiyi.tanqiu.game.mapper;

import com.artqiyi.tanqiu.game.domain.GameBreakRecords;
import com.artqiyi.tanqiu.game.domain.GameBreakRecordsExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GameBreakRecordsMapper {
    long countByExample(GameBreakRecordsExample example);

    int deleteByExample(GameBreakRecordsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(GameBreakRecords record);

    int insertSelective(GameBreakRecords record);

    List<GameBreakRecords> selectByExample(GameBreakRecordsExample example);

    GameBreakRecords selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") GameBreakRecords record, @Param("example") GameBreakRecordsExample example);

    int updateByExample(@Param("record") GameBreakRecords record, @Param("example") GameBreakRecordsExample example);

    int updateByPrimaryKeySelective(GameBreakRecords record);

    int updateByPrimaryKey(GameBreakRecords record);
}