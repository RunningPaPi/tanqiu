package com.artqiyi.tanqiu.game;

import com.artqiyi.tanqiu.base.service.IBaseService;
import com.artqiyi.tanqiu.game.domain.GameBreakUserQualify;
import com.artqiyi.tanqiu.game.domain.GameBreakUserQualifyExample;

/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: tanqiu
 * Author: chencunjun  <1078027943@qq.com>
 * Create On: 2018/7/3
 * Modify On: 2018/7/3 by chencunjun
 */
public interface IGameBreakUserQualifyService extends IBaseService<GameBreakUserQualify,GameBreakUserQualifyExample> {
    GameBreakUserQualify getLatestUserPrize(Long userId, Long gameId, String gameNo);
}
