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

import com.artqiyi.tanqiu.task.domain.TaskLog;
import com.artqiyi.tanqiu.task.domain.TaskLogExample;

public interface TaskLogMapper {
    @SelectProvider(type=TaskLogSqlProvider.class, method="countByExample")
    int countByExample(TaskLogExample example);

    @DeleteProvider(type=TaskLogSqlProvider.class, method="deleteByExample")
    int deleteByExample(TaskLogExample example);

    @Delete({
        "delete from task_log",
        "where task_log_id = #{taskLogId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long taskLogId);

    @Insert({
        "insert into task_log (user_id, task_id, ",
        "task_name, task_desc, ",
        "task_type, task_display, ",
        "task_repet_num, task_type_attr, ",
        "task_type_catalog, task_state, ",
        "successor_task_id, update_time, ",
        "create_time)",
        "values (#{userId,jdbcType=BIGINT}, #{taskId,jdbcType=INTEGER}, ",
        "#{taskName,jdbcType=VARCHAR}, #{taskDesc,jdbcType=VARCHAR}, ",
        "#{taskType,jdbcType=VARCHAR}, #{taskDisplay,jdbcType=SMALLINT}, ",
        "#{taskRepetNum,jdbcType=INTEGER}, #{taskTypeAttr,jdbcType=SMALLINT}, ",
        "#{taskTypeCatalog,jdbcType=SMALLINT}, #{taskState,jdbcType=SMALLINT}, ",
        "#{successorTaskId,jdbcType=INTEGER}, #{updateTime,jdbcType=TIMESTAMP}, ",
        "#{createTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys=true,keyProperty="taskLogId")
    int insert(TaskLog record);

    @InsertProvider(type=TaskLogSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="taskLogId")
    int insertSelective(TaskLog record);

    @SelectProvider(type=TaskLogSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="task_log_id", property="taskLogId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="task_id", property="taskId", jdbcType=JdbcType.INTEGER),
        @Result(column="task_name", property="taskName", jdbcType=JdbcType.VARCHAR),
        @Result(column="task_desc", property="taskDesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="task_type", property="taskType", jdbcType=JdbcType.VARCHAR),
        @Result(column="task_display", property="taskDisplay", jdbcType=JdbcType.SMALLINT),
        @Result(column="task_repet_num", property="taskRepetNum", jdbcType=JdbcType.INTEGER),
        @Result(column="task_type_attr", property="taskTypeAttr", jdbcType=JdbcType.SMALLINT),
        @Result(column="task_type_catalog", property="taskTypeCatalog", jdbcType=JdbcType.SMALLINT),
        @Result(column="task_state", property="taskState", jdbcType=JdbcType.SMALLINT),
        @Result(column="successor_task_id", property="successorTaskId", jdbcType=JdbcType.INTEGER),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<TaskLog> selectByExample(TaskLogExample example);

    @Select({
        "select",
        "task_log_id, user_id, task_id, task_name, task_desc, task_type, task_display, ",
        "task_repet_num, task_type_attr, task_type_catalog, task_state, successor_task_id, ",
        "update_time, create_time",
        "from task_log",
        "where task_log_id = #{taskLogId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="task_log_id", property="taskLogId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="task_id", property="taskId", jdbcType=JdbcType.INTEGER),
        @Result(column="task_name", property="taskName", jdbcType=JdbcType.VARCHAR),
        @Result(column="task_desc", property="taskDesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="task_type", property="taskType", jdbcType=JdbcType.VARCHAR),
        @Result(column="task_display", property="taskDisplay", jdbcType=JdbcType.SMALLINT),
        @Result(column="task_repet_num", property="taskRepetNum", jdbcType=JdbcType.INTEGER),
        @Result(column="task_type_attr", property="taskTypeAttr", jdbcType=JdbcType.SMALLINT),
        @Result(column="task_type_catalog", property="taskTypeCatalog", jdbcType=JdbcType.SMALLINT),
        @Result(column="task_state", property="taskState", jdbcType=JdbcType.SMALLINT),
        @Result(column="successor_task_id", property="successorTaskId", jdbcType=JdbcType.INTEGER),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    TaskLog selectByPrimaryKey(Long taskLogId);

    @UpdateProvider(type=TaskLogSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") TaskLog record, @Param("example") TaskLogExample example);

    @UpdateProvider(type=TaskLogSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") TaskLog record, @Param("example") TaskLogExample example);

    @UpdateProvider(type=TaskLogSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(TaskLog record);

    @Update({
        "update task_log",
        "set user_id = #{userId,jdbcType=BIGINT},",
          "task_id = #{taskId,jdbcType=INTEGER},",
          "task_name = #{taskName,jdbcType=VARCHAR},",
          "task_desc = #{taskDesc,jdbcType=VARCHAR},",
          "task_type = #{taskType,jdbcType=VARCHAR},",
          "task_display = #{taskDisplay,jdbcType=SMALLINT},",
          "task_repet_num = #{taskRepetNum,jdbcType=INTEGER},",
          "task_type_attr = #{taskTypeAttr,jdbcType=SMALLINT},",
          "task_type_catalog = #{taskTypeCatalog,jdbcType=SMALLINT},",
          "task_state = #{taskState,jdbcType=SMALLINT},",
          "successor_task_id = #{successorTaskId,jdbcType=INTEGER},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "create_time = #{createTime,jdbcType=TIMESTAMP}",
        "where task_log_id = #{taskLogId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(TaskLog record);
}