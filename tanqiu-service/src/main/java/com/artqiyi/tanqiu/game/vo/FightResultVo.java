package com.artqiyi.tanqiu.game.vo;

/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: tanqiu
 * Author: chencunjun  <1078027943@qq.com>
 * Create On: 2018/7/5
 * Modify On: 2018/7/5 by chencunjun
 */

/**
 * 好友对战PK结果
 */
public class FightResultVo {
    boolean isWin;
    int score;
    int againstScore;

    public boolean isWin() {
        return isWin;
    }

    public void setWin(boolean win) {
        isWin = win;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getAgainstScore() {
        return againstScore;
    }

    public void setAgainstScore(int againstScore) {
        this.againstScore = againstScore;
    }
}
