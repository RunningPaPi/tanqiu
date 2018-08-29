package com.artqiyi.tanqiu.game;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/23
 * Modify On: 2018/07/23 18:12 by wufuchang
 */

import com.artqiyi.tanqiu.game.domain.GameRedpacketPrizeConfig;

import java.util.List;

/**
 *
 */
public interface IGameRedpacketPrizeConfigService {
    List<GameRedpacketPrizeConfig> getPirzeListSortedByRank();
}
