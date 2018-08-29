package com.artqiyi.tanqiu.task.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import com.artqiyi.tanqiu.task.domain.TaskRewardRule;
import com.artqiyi.tanqiu.task.domain.TaskRewardRuleExample;

public interface TaskRewardRuleMapper {
    @SelectProvider(type=TaskRewardRuleSqlProvider.class, method="countByExample")
    int countByExample(TaskRewardRuleExample example);

    @DeleteProvider(type=TaskRewardRuleSqlProvider.class, method="deleteByExample")
    int deleteByExample(TaskRewardRuleExample example);

    @Delete({
        "delete from task_reward_rule",
        "where reward_rule_id = #{rewardRuleId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer rewardRuleId);

    @Insert({
        "insert into task_reward_rule (reward_rule_name, award_type, ",
        "award_name, award_num, ",
        "update_time, create_time)",
        "values (#{rewardRuleName,jdbcType=VARCHAR}, #{awardType,jdbcType=SMALLINT}, ",
        "#{awardName,jdbcType=VARCHAR}, #{awardNum,jdbcType=INTEGER}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{createTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys=true,keyProperty="rewardRuleId")
    int insert(TaskRewardRule record);

    @InsertProvider(type=TaskRewardRuleSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="rewardRuleId")
    int insertSelective(TaskRewardRule record);

    @SelectProvider(type=TaskRewardRuleSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="reward_rule_id", property="rewardRuleId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="reward_rule_name", property="rewardRuleName", jdbcType=JdbcType.VARCHAR),
        @Result(column="award_type", property="awardType", jdbcType=JdbcType.SMALLINT),
        @Result(column="award_name", property="awardName", jdbcType=JdbcType.VARCHAR),
        @Result(column="award_num", property="awardNum", jdbcType=JdbcType.INTEGER),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<TaskRewardRule> selectByExample(TaskRewardRuleExample example);

    @Select({
        "select",
        "reward_rule_id, reward_rule_name, award_type, award_name, award_num, update_time, ",
        "create_time",
        "from task_reward_rule",
        "where reward_rule_id = #{rewardRuleId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="reward_rule_id", property="rewardRuleId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="reward_rule_name", property="rewardRuleName", jdbcType=JdbcType.VARCHAR),
        @Result(column="award_type", property="awardType", jdbcType=JdbcType.SMALLINT),
        @Result(column="award_name", property="awardName", jdbcType=JdbcType.VARCHAR),
        @Result(column="award_num", property="awardNum", jdbcType=JdbcType.INTEGER),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    TaskRewardRule selectByPrimaryKey(Integer rewardRuleId);

    @UpdateProvider(type=TaskRewardRuleSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") TaskRewardRule record, @Param("example") TaskRewardRuleExample example);

    @UpdateProvider(type=TaskRewardRuleSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") TaskRewardRule record, @Param("example") TaskRewardRuleExample example);

    @UpdateProvider(type=TaskRewardRuleSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(TaskRewardRule record);

    @Update({
        "update task_reward_rule",
        "set reward_rule_name = #{rewardRuleName,jdbcType=VARCHAR},",
          "award_type = #{awardType,jdbcType=SMALLINT},",
          "award_name = #{awardName,jdbcType=VARCHAR},",
          "award_num = #{awardNum,jdbcType=INTEGER},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "create_time = #{createTime,jdbcType=TIMESTAMP}",
        "where reward_rule_id = #{rewardRuleId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TaskRewardRule record);
}