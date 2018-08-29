package com.artqiyi.tanqiu.welfare.mapper;

import com.artqiyi.tanqiu.welfare.domain.WelfareReceiveLog;
import com.artqiyi.tanqiu.welfare.domain.WelfareReceiveLogExample;
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

public interface WelfareReceiveLogMapper {
    @SelectProvider(type=WelfareReceiveLogSqlProvider.class, method="countByExample")
    int countByExample(WelfareReceiveLogExample example);

    @DeleteProvider(type=WelfareReceiveLogSqlProvider.class, method="deleteByExample")
    int deleteByExample(WelfareReceiveLogExample example);

    @Delete({
        "delete from welfare_receive_log",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into welfare_receive_log (user_id, welfare_id, ",
        "welfare_name, welfare_desc, ",
        "welfare_type, award_type, ",
        "award_num, status, ",
        "friend_user_id, create_time)",
        "values (#{userId,jdbcType=BIGINT}, #{welfareId,jdbcType=BIGINT}, ",
        "#{welfareName,jdbcType=VARCHAR}, #{welfareDesc,jdbcType=VARCHAR}, ",
        "#{welfareType,jdbcType=INTEGER}, #{awardType,jdbcType=INTEGER}, ",
        "#{awardNum,jdbcType=INTEGER}, #{status,jdbcType=INTEGER}, ",
        "#{friendUserId,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(WelfareReceiveLog record);

    @InsertProvider(type=WelfareReceiveLogSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(WelfareReceiveLog record);

    @SelectProvider(type=WelfareReceiveLogSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="welfare_id", property="welfareId", jdbcType=JdbcType.BIGINT),
        @Result(column="welfare_name", property="welfareName", jdbcType=JdbcType.VARCHAR),
        @Result(column="welfare_desc", property="welfareDesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="welfare_type", property="welfareType", jdbcType=JdbcType.INTEGER),
        @Result(column="award_type", property="awardType", jdbcType=JdbcType.INTEGER),
        @Result(column="award_num", property="awardNum", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="friend_user_id", property="friendUserId", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<WelfareReceiveLog> selectByExample(WelfareReceiveLogExample example);

    @Select({
        "select",
        "id, user_id, welfare_id, welfare_name, welfare_desc, welfare_type, award_type, ",
        "award_num, status, friend_user_id, create_time",
        "from welfare_receive_log",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="welfare_id", property="welfareId", jdbcType=JdbcType.BIGINT),
        @Result(column="welfare_name", property="welfareName", jdbcType=JdbcType.VARCHAR),
        @Result(column="welfare_desc", property="welfareDesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="welfare_type", property="welfareType", jdbcType=JdbcType.INTEGER),
        @Result(column="award_type", property="awardType", jdbcType=JdbcType.INTEGER),
        @Result(column="award_num", property="awardNum", jdbcType=JdbcType.INTEGER),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="friend_user_id", property="friendUserId", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    WelfareReceiveLog selectByPrimaryKey(Long id);

    @UpdateProvider(type=WelfareReceiveLogSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") WelfareReceiveLog record, @Param("example") WelfareReceiveLogExample example);

    @UpdateProvider(type=WelfareReceiveLogSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") WelfareReceiveLog record, @Param("example") WelfareReceiveLogExample example);

    @UpdateProvider(type=WelfareReceiveLogSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(WelfareReceiveLog record);

    @Update({
        "update welfare_receive_log",
        "set user_id = #{userId,jdbcType=BIGINT},",
          "welfare_id = #{welfareId,jdbcType=BIGINT},",
          "welfare_name = #{welfareName,jdbcType=VARCHAR},",
          "welfare_desc = #{welfareDesc,jdbcType=VARCHAR},",
          "welfare_type = #{welfareType,jdbcType=INTEGER},",
          "award_type = #{awardType,jdbcType=INTEGER},",
          "award_num = #{awardNum,jdbcType=INTEGER},",
          "status = #{status,jdbcType=INTEGER},",
          "friend_user_id = #{friendUserId,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(WelfareReceiveLog record);
}