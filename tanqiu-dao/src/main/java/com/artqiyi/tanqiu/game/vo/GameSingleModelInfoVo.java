package com.artqiyi.tanqiu.game.vo;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: tanqiu
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/03
 * Modify On: 2018/07/03 17:36 by wufuchang
 */

import com.artqiyi.tanqiu.game.domain.GameConfig;

import java.util.List;

/**
 * 点击某种模式进入的界面 值对象
 */
public class GameSingleModelInfoVo {
    //该模式下的游戏配置
    private Integer ballNumDefault;//开局默认球数
    private Integer ballNumAwardFromFriend;//好友助力球数
    private Integer ballNumAwardLimit;//好友助力球数上限
    private Short recoverType;//复活类型
    private Integer shareLimitWithinGame;//游戏内分享限制次数
    private List<String> singleModelRules;//单人模式规则
    private Integer recoverTimesLimit;//复活次数

    public Integer getRecoverTimesLimit() {
        return recoverTimesLimit;
    }

    public void setRecoverTimesLimit(Integer recoverTimesLimit) {
        this.recoverTimesLimit = recoverTimesLimit;
    }

    public Integer getBallNumDefault() {
        return ballNumDefault;
    }

    public void setBallNumDefault(Integer ballNumDefault) {
        this.ballNumDefault = ballNumDefault;
    }

    public Integer getBallNumAwardFromFriend() {
        return ballNumAwardFromFriend;
    }

    public void setBallNumAwardFromFriend(Integer ballNumAwardFromFriend) {
        this.ballNumAwardFromFriend = ballNumAwardFromFriend;
    }

    public Integer getBallNumAwardLimit() {
        return ballNumAwardLimit;
    }

    public void setBallNumAwardLimit(Integer ballNumAwardLimit) {
        this.ballNumAwardLimit = ballNumAwardLimit;
    }

    public Short getRecoverType() {
        return recoverType;
    }

    public void setRecoverType(Short recoverType) {
        this.recoverType = recoverType;
    }

    public Integer getShareLimitWithinGame() {
        return shareLimitWithinGame;
    }

    public void setShareLimitWithinGame(Integer shareLimitWithinGame) {
        this.shareLimitWithinGame = shareLimitWithinGame;
    }

    public List<String> getSingleModelRules() {
        return singleModelRules;
    }

    public void setSingleModelRules(List<String> singleModelRules) {
        this.singleModelRules = singleModelRules;
    }
}
