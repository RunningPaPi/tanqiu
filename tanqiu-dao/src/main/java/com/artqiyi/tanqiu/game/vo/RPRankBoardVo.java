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
public class RPRankBoardVo {
    private List<RPGameRankVo> gameRankVoList;//好友排行榜
    private RPGameRankVo myGameRankVo;//我的最佳记录

    public List<RPGameRankVo> getGameRankVoList() {
        return gameRankVoList;
    }

    public void setGameRankVoList(List<RPGameRankVo> gameRankVoList) {
        this.gameRankVoList = gameRankVoList;
    }

    public RPGameRankVo getMyGameRankVo() {
        return myGameRankVo;
    }

    public void setMyGameRankVo(RPGameRankVo myGameRankVo) {
        this.myGameRankVo = myGameRankVo;
    }
}
