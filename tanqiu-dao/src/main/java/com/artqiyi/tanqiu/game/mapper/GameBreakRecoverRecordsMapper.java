package com.artqiyi.tanqiu.game.mapper;


import com.artqiyi.tanqiu.game.domain.GameBreakRecoverRecords;
import com.artqiyi.tanqiu.game.domain.GameBreakRecoverRecordsExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GameBreakRecoverRecordsMapper {
    long countByExample(GameBreakRecoverRecordsExample example);

    int deleteByExample(GameBreakRecoverRecordsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(GameBreakRecoverRecords record);

    int insertSelective(GameBreakRecoverRecords record);

    List<GameBreakRecoverRecords> selectByExample(GameBreakRecoverRecordsExample example);

    GameBreakRecoverRecords selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") GameBreakRecoverRecords record, @Param("example") GameBreakRecoverRecordsExample example);

    int updateByExample(@Param("record") GameBreakRecoverRecords record, @Param("example") GameBreakRecoverRecordsExample example);

    int updateByPrimaryKeySelective(GameBreakRecoverRecords record);

    int updateByPrimaryKey(GameBreakRecoverRecords record);
}