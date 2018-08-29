package com.artqiyi.tanqiu.welfare.mapper;

import com.artqiyi.tanqiu.welfare.domain.Welfare;
import com.artqiyi.tanqiu.welfare.domain.WelfareExample;
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

public interface WelfareMapper {
    @SelectProvider(type=WelfareSqlProvider.class, method="countByExample")
    int countByExample(WelfareExample example);

    @DeleteProvider(type=WelfareSqlProvider.class, method="deleteByExample")
    int deleteByExample(WelfareExample example);

    @Delete({
        "delete from welfare",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into welfare (welfare_name, welfare_desc, ",
        "welfare_type, award_type, ",
        "award_num, create_time, ",
        "update_time)",
        "values (#{welfareName,jdbcType=VARCHAR}, #{welfareDesc,jdbcType=VARCHAR}, ",
        "#{welfareType,jdbcType=INTEGER}, #{awardType,jdbcType=INTEGER}, ",
        "#{awardNum,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(Welfare record);

    @InsertProvider(type=WelfareSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(Welfare record);

    @SelectProvider(type=WelfareSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="welfare_name", property="welfareName", jdbcType=JdbcType.VARCHAR),
        @Result(column="welfare_desc", property="welfareDesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="welfare_type", property="welfareType", jdbcType=JdbcType.INTEGER),
        @Result(column="award_type", property="awardType", jdbcType=JdbcType.INTEGER),
        @Result(column="award_num", property="awardNum", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Welfare> selectByExample(WelfareExample example);

    @Select({
        "select",
        "id, welfare_name, welfare_desc, welfare_type, award_type, award_num, create_time, ",
        "update_time",
        "from welfare",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="welfare_name", property="welfareName", jdbcType=JdbcType.VARCHAR),
        @Result(column="welfare_desc", property="welfareDesc", jdbcType=JdbcType.VARCHAR),
        @Result(column="welfare_type", property="welfareType", jdbcType=JdbcType.INTEGER),
        @Result(column="award_type", property="awardType", jdbcType=JdbcType.INTEGER),
        @Result(column="award_num", property="awardNum", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    Welfare selectByPrimaryKey(Long id);

    @UpdateProvider(type=WelfareSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Welfare record, @Param("example") WelfareExample example);

    @UpdateProvider(type=WelfareSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Welfare record, @Param("example") WelfareExample example);

    @UpdateProvider(type=WelfareSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Welfare record);

    @Update({
        "update welfare",
        "set welfare_name = #{welfareName,jdbcType=VARCHAR},",
          "welfare_desc = #{welfareDesc,jdbcType=VARCHAR},",
          "welfare_type = #{welfareType,jdbcType=INTEGER},",
          "award_type = #{awardType,jdbcType=INTEGER},",
          "award_num = #{awardNum,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Welfare record);
}