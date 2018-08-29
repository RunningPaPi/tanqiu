package com.artqiyi.tanqiu.game.vo;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/23
 * Modify On: 2018/07/23 18:23 by wufuchang
 */

import java.util.List;

/**
 *
 */
public class RedpacketModelInfoVo {
    private Long gameModelId;
    private String gameModelKey;
    private Integer userFreePlayTimes;//用户免费试玩次数
    private Integer defaultFreePlayTimes;//用户免费试玩次数
    private Integer coinCost;//参赛费用，趣币类型
    private Integer level;//游戏关卡数
    private List<String> castTimes;//游戏结算时间
    private List<String> gameRules;//游戏规则

    public Long getGameModelId() {
        return gameModelId;
    }

    public void setGameModelId(Long gameModelId) {
        this.gameModelId = gameModelId;
    }

    public String getGameModelKey() {
        return gameModelKey;
    }

    public void setGameModelKey(String gameModelKey) {
        this.gameModelKey = gameModelKey;
    }

    public Integer getUserFreePlayTimes() {
        return userFreePlayTimes;
    }

    public void setUserFreePlayTimes(Integer userFreePlayTimes) {
        this.userFreePlayTimes = userFreePlayTimes;
    }

    public Integer getDefaultFreePlayTimes() {
        return defaultFreePlayTimes;
    }

    public void setDefaultFreePlayTimes(Integer defaultFreePlayTimes) {
        this.defaultFreePlayTimes = defaultFreePlayTimes;
    }

    public Integer getCoinCost() {
        return coinCost;
    }

    public void setCoinCost(Integer coinCost) {
        this.coinCost = coinCost;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<String> getCastTimes() {
        return castTimes;
    }

    public void setCastTimes(List<String> castTimes) {
        this.castTimes = castTimes;
    }

    public List<String> getGameRules() {
        return gameRules;
    }

    public void setGameRules(List<String> gameRules) {
        this.gameRules = gameRules;
    }
}
