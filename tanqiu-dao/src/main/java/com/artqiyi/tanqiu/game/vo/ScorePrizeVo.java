package com.artqiyi.tanqiu.game.vo;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/06
 * Modify On: 2018/07/06 14:10 by wufuchang
 */

/**
 * 分数奖励值对象
 */
public class ScorePrizeVo {
    private Integer Score;//本次分数
    private Integer nextPrizeNeededScore;//本次到达下一级奖励需要的分数
    private Integer prizeCoin;//本次分数的奖励
    private Integer nextPrizeCoin;//下次分数的奖励

    public Integer getScore() {
        return Score;
    }

    public void setScore(Integer score) {
        Score = score;
    }

    public Integer getNextPrizeNeededScore() {
        return nextPrizeNeededScore;
    }

    public void setNextPrizeNeededScore(Integer nextPrizeNeededScore) {
        this.nextPrizeNeededScore = nextPrizeNeededScore;
    }

    public Integer getPrizeCoin() {
        return prizeCoin;
    }

    public void setPrizeCoin(Integer prizeCoin) {
        this.prizeCoin = prizeCoin;
    }

    public Integer getNextPrizeCoin() {
        return nextPrizeCoin;
    }

    public void setNextPrizeCoin(Integer nextPrizeCoin) {
        this.nextPrizeCoin = nextPrizeCoin;
    }

}
