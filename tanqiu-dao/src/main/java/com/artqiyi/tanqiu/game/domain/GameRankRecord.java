package com.artqiyi.tanqiu.game.domain;

import java.util.Date;

public class GameRankRecord {
    private Long id;

    private Long gameModelId;

    private String gameModelKey;

    private Long userId;

    private String userNickname;

    private Integer score;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
        this.gameModelKey = gameModelKey == null ? null : gameModelKey.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname == null ? null : userNickname.trim();
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}