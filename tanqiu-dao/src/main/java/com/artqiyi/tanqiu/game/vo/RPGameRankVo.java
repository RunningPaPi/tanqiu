package com.artqiyi.tanqiu.game.vo;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/04
 * Modify On: 2018/07/04 11:07 by wufuchang
 */

/**
 * 红包赛排行榜vo
 */
public class RPGameRankVo {
    private Long userId;//用户id
    private String userNickName;//用户昵称
    private String userHeadPicUrl;//用户头像
    private Integer worldRank;//世界总排行
    private Integer score;//用户最高分
    private Integer prizeNum;//奖励

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserHeadPicUrl() {
        return userHeadPicUrl;
    }

    public void setUserHeadPicUrl(String userHeadPicUrl) {
        this.userHeadPicUrl = userHeadPicUrl;
    }

    public Integer getWorldRank() {
        return worldRank;
    }

    public void setWorldRank(Integer worldRank) {
        this.worldRank = worldRank;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getPrizeNum() {
        return prizeNum;
    }

    public void setPrizeNum(Integer prizeNum) {
        this.prizeNum = prizeNum;
    }
}
