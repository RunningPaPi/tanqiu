package com.artqiyi.tanqiu.game.mapper;

import com.artqiyi.tanqiu.game.domain.GameBreakUserRecords;
import com.artqiyi.tanqiu.game.domain.GameBreakUserRecordsExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GameBreakUserRecordsMapper {
    long countByExample(GameBreakUserRecordsExample example);

    int deleteByExample(GameBreakUserRecordsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(GameBreakUserRecords record);

    int insertSelective(GameBreakUserRecords record);

    List<GameBreakUserRecords> selectByExample(GameBreakUserRecordsExample example);

    GameBreakUserRecords selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") GameBreakUserRecords record, @Param("example") GameBreakUserRecordsExample example);

    int updateByExample(@Param("record") GameBreakUserRecords record, @Param("example") GameBreakUserRecordsExample example);

    int updateByPrimaryKeySelective(GameBreakUserRecords record);

    int updateByPrimaryKey(GameBreakUserRecords record);
}