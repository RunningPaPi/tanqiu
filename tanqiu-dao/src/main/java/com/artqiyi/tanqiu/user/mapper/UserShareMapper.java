package com.artqiyi.tanqiu.user.mapper;

import com.artqiyi.tanqiu.user.domain.UserShare;
import com.artqiyi.tanqiu.user.domain.UserShareExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserShareMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_share
     *
     * @mbg.generated Fri Jul 06 17:03:18 CST 2018
     */
    long countByExample(UserShareExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_share
     *
     * @mbg.generated Fri Jul 06 17:03:18 CST 2018
     */
    int deleteByExample(UserShareExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_share
     *
     * @mbg.generated Fri Jul 06 17:03:18 CST 2018
     */
    int deleteByPrimaryKey(Long shareId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_share
     *
     * @mbg.generated Fri Jul 06 17:03:18 CST 2018
     */
    int insert(UserShare entity);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_share
     *
     * @mbg.generated Fri Jul 06 17:03:18 CST 2018
     */
    int insertSelective(UserShare entity);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_share
     *
     * @mbg.generated Fri Jul 06 17:03:18 CST 2018
     */
    List<UserShare> selectByExample(UserShareExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_share
     *
     * @mbg.generated Fri Jul 06 17:03:18 CST 2018
     */
    UserShare selectByPrimaryKey(Long shareId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_share
     *
     * @mbg.generated Fri Jul 06 17:03:18 CST 2018
     */
    int updateByExampleSelective(@Param("record") UserShare record, @Param("example") UserShareExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_share
     *
     * @mbg.generated Fri Jul 06 17:03:18 CST 2018
     */
    int updateByExample(@Param("record") UserShare record, @Param("example") UserShareExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_share
     *
     * @mbg.generated Fri Jul 06 17:03:18 CST 2018
     */
    int updateByPrimaryKeySelective(UserShare entity);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_share
     *
     * @mbg.generated Fri Jul 06 17:03:18 CST 2018
     */
    int updateByPrimaryKey(UserShare entity);
}