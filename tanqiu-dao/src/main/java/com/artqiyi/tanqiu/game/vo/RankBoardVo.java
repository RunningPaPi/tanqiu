package com.artqiyi.tanqiu.game.vo;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/04
 * Modify On: 2018/07/04 19:15 by wufuchang
 */

import java.util.List;

/**
 *
 */
public class RankBoardVo {
    private List<GameRankVo> gameRankVoList;//好友排行榜
    private GameRankVo myGameRankVo;//我的最佳记录

    public List<GameRankVo> getGameRankVoList() {
        return gameRankVoList;
    }

    public void setGameRankVoList(List<GameRankVo> gameRankVoList) {
        this.gameRankVoList = gameRankVoList;
    }

    public GameRankVo getMyGameRankVo() {
        return myGameRankVo;
    }

    public void setMyGameRankVo(GameRankVo myGameRankVo) {
        this.myGameRankVo = myGameRankVo;
    }
}
