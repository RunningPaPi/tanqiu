package com.artqiyi.tanqiu.system.mapper;

import com.artqiyi.tanqiu.system.domain.SystemConfig;
import com.artqiyi.tanqiu.system.domain.SystemConfigExample;
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

public interface SystemConfigMapper {
    @SelectProvider(type=SystemConfigSqlProvider.class, method="countByExample")
    int countByExample(SystemConfigExample example);

    @DeleteProvider(type=SystemConfigSqlProvider.class, method="deleteByExample")
    int deleteByExample(SystemConfigExample example);

    @Delete({
        "delete from system_config",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into system_config (param_name, param_key, ",
        "param_value, remark, ",
        "status, create_time, ",
        "update_time)",
        "values (#{paramName,jdbcType=VARCHAR}, #{paramKey,jdbcType=VARCHAR}, ",
        "#{paramValue,jdbcType=VARCHAR}, #{remark,jdbcType=VARCHAR}, ",
        "#{status,jdbcType=SMALLINT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(SystemConfig record);

    @InsertProvider(type=SystemConfigSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(SystemConfig record);

    @SelectProvider(type=SystemConfigSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="param_name", property="paramName", jdbcType=JdbcType.VARCHAR),
        @Result(column="param_key", property="paramKey", jdbcType=JdbcType.VARCHAR),
        @Result(column="param_value", property="paramValue", jdbcType=JdbcType.VARCHAR),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.SMALLINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<SystemConfig> selectByExample(SystemConfigExample example);

    @Select({
        "select",
        "id, param_name, param_key, param_value, remark, status, create_time, update_time",
        "from system_config",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="param_name", property="paramName", jdbcType=JdbcType.VARCHAR),
        @Result(column="param_key", property="paramKey", jdbcType=JdbcType.VARCHAR),
        @Result(column="param_value", property="paramValue", jdbcType=JdbcType.VARCHAR),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.SMALLINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    SystemConfig selectByPrimaryKey(Long id);

    @UpdateProvider(type=SystemConfigSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") SystemConfig record, @Param("example") SystemConfigExample example);

    @UpdateProvider(type=SystemConfigSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") SystemConfig record, @Param("example") SystemConfigExample example);

    @UpdateProvider(type=SystemConfigSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(SystemConfig record);

    @Update({
        "update system_config",
        "set param_name = #{paramName,jdbcType=VARCHAR},",
          "param_key = #{paramKey,jdbcType=VARCHAR},",
          "param_value = #{paramValue,jdbcType=VARCHAR},",
          "remark = #{remark,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=SMALLINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(SystemConfig record);
}