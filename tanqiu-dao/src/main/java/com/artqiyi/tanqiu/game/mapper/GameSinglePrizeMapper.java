package com.artqiyi.tanqiu.game.mapper;

import com.artqiyi.tanqiu.game.domain.GameSinglePrize;
import com.artqiyi.tanqiu.game.domain.GameSinglePrizeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GameSinglePrizeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_single_prize
     *
     * @mbg.generated Thu Jul 05 18:45:18 CST 2018
     */
    long countByExample(GameSinglePrizeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_single_prize
     *
     * @mbg.generated Thu Jul 05 18:45:18 CST 2018
     */
    int deleteByExample(GameSinglePrizeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_single_prize
     *
     * @mbg.generated Thu Jul 05 18:45:18 CST 2018
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_single_prize
     *
     * @mbg.generated Thu Jul 05 18:45:18 CST 2018
     */
    int insert(GameSinglePrize entity);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_single_prize
     *
     * @mbg.generated Thu Jul 05 18:45:18 CST 2018
     */
    int insertSelective(GameSinglePrize entity);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_single_prize
     *
     * @mbg.generated Thu Jul 05 18:45:18 CST 2018
     */
    List<GameSinglePrize> selectByExample(GameSinglePrizeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_single_prize
     *
     * @mbg.generated Thu Jul 05 18:45:18 CST 2018
     */
    GameSinglePrize selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_single_prize
     *
     * @mbg.generated Thu Jul 05 18:45:18 CST 2018
     */
    int updateByExampleSelective(@Param("record") GameSinglePrize record, @Param("example") GameSinglePrizeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_single_prize
     *
     * @mbg.generated Thu Jul 05 18:45:18 CST 2018
     */
    int updateByExample(@Param("record") GameSinglePrize record, @Param("example") GameSinglePrizeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_single_prize
     *
     * @mbg.generated Thu Jul 05 18:45:18 CST 2018
     */
    int updateByPrimaryKeySelective(GameSinglePrize entity);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_single_prize
     *
     * @mbg.generated Thu Jul 05 18:45:18 CST 2018
     */
    int updateByPrimaryKey(GameSinglePrize entity);
}