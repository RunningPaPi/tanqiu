package com.artqiyi.tanqiu.game.vo;

/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: tanqiu
 * Author: chencunjun  <1078027943@qq.com>
 * Create On: 2018/5/28
 * Modify On: 2018/5/28 by chencunjun
 */

/**
 * 闯关pk结果数据vo
 */
public class PkResultVo {
    private boolean isPass;//是否通关
    private boolean isWin; //是否赢得当前pk
    private boolean isCalledGame;//游戏是否提前结束
    private Integer currentRound;//当前关卡
    private Integer score;//分数
    private Integer againstScore;//对手分数

    public boolean isPass() {
        return isPass;
    }

    public void setPass(boolean pass) {
        isPass = pass;
    }

    public boolean isWin() {
        return isWin;
    }

    public void setWin(boolean win) {
        isWin = win;
    }

    public Integer getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(Integer currentRound) {
        this.currentRound = currentRound;
    }

    public boolean isCalledGame() {
        return isCalledGame;
    }

    public void setCalledGame(boolean calledGame) {
        isCalledGame = calledGame;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getAgainstScore() {
        return againstScore;
    }

    public void setAgainstScore(Integer againstScore) {
        this.againstScore = againstScore;
    }
}
