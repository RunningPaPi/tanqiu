package com.artqiyi.tanqiu.task.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import com.artqiyi.tanqiu.task.domain.TaskRewardRuleMapExample;
import com.artqiyi.tanqiu.task.domain.TaskRewardRuleMapKey;

public interface TaskRewardRuleMapMapper {
    @SelectProvider(type=TaskRewardRuleMapSqlProvider.class, method="countByExample")
    int countByExample(TaskRewardRuleMapExample example);

    @DeleteProvider(type=TaskRewardRuleMapSqlProvider.class, method="deleteByExample")
    int deleteByExample(TaskRewardRuleMapExample example);

    @Delete({
        "delete from task_reward_rule_map",
        "where task_id = #{taskId,jdbcType=INTEGER}",
          "and reward_rule_id = #{rewardRuleId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(TaskRewardRuleMapKey key);

    @Insert({
        "insert into task_reward_rule_map (task_id, reward_rule_id)",
        "values (#{taskId,jdbcType=INTEGER}, #{rewardRuleId,jdbcType=INTEGER})"
    })
    int insert(TaskRewardRuleMapKey record);

    @InsertProvider(type=TaskRewardRuleMapSqlProvider.class, method="insertSelective")
    int insertSelective(TaskRewardRuleMapKey record);

    @SelectProvider(type=TaskRewardRuleMapSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="task_id", property="taskId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="reward_rule_id", property="rewardRuleId", jdbcType=JdbcType.INTEGER, id=true)
    })
    List<TaskRewardRuleMapKey> selectByExample(TaskRewardRuleMapExample example);

    @UpdateProvider(type=TaskRewardRuleMapSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") TaskRewardRuleMapKey record, @Param("example") TaskRewardRuleMapExample example);

    @UpdateProvider(type=TaskRewardRuleMapSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") TaskRewardRuleMapKey record, @Param("example") TaskRewardRuleMapExample example);
}