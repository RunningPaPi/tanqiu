package com.artqiyi.tanqiu.game;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/03
 * Modify On: 2018/07/03 20:44 by wufuchang
 */

import com.artqiyi.tanqiu.game.vo.RPRankBoardVo;

import java.util.Date;

/**
 *
 */
public interface IGameRPRankRecordService {
    RPRankBoardVo worldRankBoard(Long userId, Integer page, Integer pageSize);

    void setToRedisZset(Long userId, Date endTime, Integer score, String redisField);

    void setToRedisZset2(Long userId, Date endTime,  Integer score, String redisField);

    Integer getSelfHighestScore(Long userId);
}
