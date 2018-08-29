package com.artqiyi.tanqiu.game;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/05
 * Modify On: 2018/07/05 18:57 by wufuchang
 */

import com.artqiyi.tanqiu.game.vo.ScorePrizeVo;

/**
 *
 */
public interface IGameSinglePrizeService {

    Integer getPrizeCoinByScore(Integer score);

    ScorePrizeVo getSingleModelPrizeByScore(Integer score);
}
