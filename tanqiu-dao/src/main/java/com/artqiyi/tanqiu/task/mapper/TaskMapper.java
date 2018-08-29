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

import com.artqiyi.tanqiu.task.domain.Task;
import com.artqiyi.tanqiu.task.domain.TaskExample;

public interface TaskMapper {
    @SelectProvider(type=TaskSqlProvider.class, method="countByExample")
    int countByExample(TaskExample example);

    @DeleteProvider(type=TaskSqlProvider.class, method="deleteByExample")
    int deleteByExample(TaskExample example);

    @Delete({
        "delete from task",
        "where task_id = #{taskId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer taskId);

    @Insert({
        "insert into task (task_name, task_desc, ",
        "task_repet_num, task_type_attr, ",
        "task_type_catalog, task_type, ",
        "task_display, successor_task_id, ",
        "update_time, create_time)",
        "values (#{taskName,jdbcType=VARCHAR}, #{taskDesc,jdbcType=VARCHAR}, ",
        "#{taskRepetNum,jdbcType=INTEGER}, #{taskTypeAttr,jdbcType=SMALLINT}, ",
        "#{taskTypeCatalog,jdbcType=SMALLINT}, #{taskType,jdbcType=VARCHAR}, ",
        "#{taskDisplay,jdbcType=SMALLINT}, #{successorTaskId,jdbcType=INTEGER}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{createTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys=true,keyProperty="taskId")
    int insert(Task record);

    @InsertProvider(type=TaskSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="taskId")
    int insertSelective(Task record);

    @SelectProvider(type=TaskSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="task_id", property="taskId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="task_name", property="taskName", jdbcType=JdbcType.VARCHAR),
        @Result(column="task_desc", property="taskDesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="task_repet_num", property="taskRepetNum", jdbcType=JdbcType.INTEGER),
        @Result(column="task_type_attr", property="taskTypeAttr", jdbcType=JdbcType.SMALLINT),
        @Result(column="task_type_catalog", property="taskTypeCatalog", jdbcType=JdbcType.SMALLINT),
        @Result(column="task_type", property="taskType", jdbcType=JdbcType.VARCHAR),
        @Result(column="task_display", property="taskDisplay", jdbcType=JdbcType.SMALLINT),
        @Result(column="successor_task_id", property="successorTaskId", jdbcType=JdbcType.INTEGER),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Task> selectByExample(TaskExample example);

    @Select({
        "select",
        "task_id, task_name, task_desc, task_repet_num, task_type_attr, task_type_catalog, ",
        "task_type, task_display, successor_task_id, update_time, create_time",
        "from task",
        "where task_id = #{taskId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="task_id", property="taskId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="task_name", property="taskName", jdbcType=JdbcType.VARCHAR),
        @Result(column="task_desc", property="taskDesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="task_repet_num", property="taskRepetNum", jdbcType=JdbcType.INTEGER),
        @Result(column="task_type_attr", property="taskTypeAttr", jdbcType=JdbcType.SMALLINT),
        @Result(column="task_type_catalog", property="taskTypeCatalog", jdbcType=JdbcType.SMALLINT),
        @Result(column="task_type", property="taskType", jdbcType=JdbcType.VARCHAR),
        @Result(column="task_display", property="taskDisplay", jdbcType=JdbcType.SMALLINT),
        @Result(column="successor_task_id", property="successorTaskId", jdbcType=JdbcType.INTEGER),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    Task selectByPrimaryKey(Integer taskId);

    @UpdateProvider(type=TaskSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Task record, @Param("example") TaskExample example);

    @UpdateProvider(type=TaskSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Task record, @Param("example") TaskExample example);

    @UpdateProvider(type=TaskSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Task record);

    @Update({
        "update task",
        "set task_name = #{taskName,jdbcType=VARCHAR},",
          "task_desc = #{taskDesc,jdbcType=VARCHAR},",
          "task_repet_num = #{taskRepetNum,jdbcType=INTEGER},",
          "task_type_attr = #{taskTypeAttr,jdbcType=SMALLINT},",
          "task_type_catalog = #{taskTypeCatalog,jdbcType=SMALLINT},",
          "task_type = #{taskType,jdbcType=VARCHAR},",
          "task_display = #{taskDisplay,jdbcType=SMALLINT},",
          "successor_task_id = #{successorTaskId,jdbcType=INTEGER},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "create_time = #{createTime,jdbcType=TIMESTAMP}",
        "where task_id = #{taskId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Task record);
}