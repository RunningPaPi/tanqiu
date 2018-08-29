package com.artqiyi.tanqiu.game.vo;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/03
 * Modify On: 2018/07/03 20:12 by wufuchang
 */

import java.util.List;

/**
 * 游戏结束返回数据
 */
public class GameEndVo {
    private Integer score;//本次最终得分
    private boolean isSelfWeekHighest;//是否本周最佳 平记录不算
    //private GameRankVo myGameRankVo;//我的排行榜
    private Integer prizeCoin;//奖励


    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public boolean isSelfWeekHighest() {
        return isSelfWeekHighest;
    }

    public void setSelfWeekHighest(boolean selfWeekHighest) {
        isSelfWeekHighest = selfWeekHighest;
    }

    public Integer getPrizeCoin() {
        return prizeCoin;
    }

    public void setPrizeCoin(Integer prizeCoin) {
        this.prizeCoin = prizeCoin;
    }

    /*public GameRankVo getMyGameRankVo() {
        return myGameRankVo;
    }

    public void setMyGameRankVo(GameRankVo myGameRankVo) {
        this.myGameRankVo = myGameRankVo;
    }*/
}
