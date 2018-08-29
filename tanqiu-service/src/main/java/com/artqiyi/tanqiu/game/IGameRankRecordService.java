package com.artqiyi.tanqiu.game;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/03
 * Modify On: 2018/07/03 20:44 by wufuchang
 */

import com.artqiyi.tanqiu.game.domain.GameRankRecord;
import com.artqiyi.tanqiu.game.vo.GameRankVo;
import com.artqiyi.tanqiu.game.vo.RankBoardVo;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;

/**
 *
 */
public interface IGameRankRecordService {
    long saveOrUpdate(GameRankRecord gameRankRecord);

    void addGameRecord(Long userId, String userNickName, String gameModelKey, Long gameModelId, Integer score);

    ImmutablePair<List<GameRankVo>,Integer> getFriendRankList(Long userId, Integer page, Integer pageSize);

    RankBoardVo worldRankBoard(Long userId, Integer page, Integer pageSize);

    RankBoardVo friendRankBoard(Long userId, Integer page, Integer pageSize);

    Integer getSelfWeekHighestScore(Long userId, Long gameModelId, Integer score);
}
