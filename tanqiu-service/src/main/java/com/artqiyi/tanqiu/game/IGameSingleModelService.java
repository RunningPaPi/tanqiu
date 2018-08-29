package com.artqiyi.tanqiu.game;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: tanqiu
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/03
 * Modify On: 2018/07/03 18:30 by wufuchang
 */

import com.artqiyi.tanqiu.game.vo.AssistanceResulltVo;
import com.artqiyi.tanqiu.game.vo.GameBeginVo;
import com.artqiyi.tanqiu.game.vo.GameEndVo;
import com.artqiyi.tanqiu.game.vo.RankBoardVo;

/**
 * 单机模式业务类
 */
public interface IGameSingleModelService {

    GameBeginVo gameBegin(Long userId);

    AssistanceResulltVo assistanceByFriend(Long userId);

    GameEndVo gameEnd(Long userId, String userNickName, String gameModelKey, Long gameModelId, Integer score);

}
