package com.artqiyi.tanqiu.game.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class GameBreakRecoverCost {
    @JsonIgnore
    private Long id;
    @JsonProperty("gameId")
    private Long gameModelId;
    @JsonProperty("gameKey")
    private String gameModelKey;

    private Integer gameRound;

    private Short costType;

    private Integer costNum;
    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Long createId;

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

    public Integer getGameRound() {
        return gameRound;
    }

    public void setGameRound(Integer gameRound) {
        this.gameRound = gameRound;
    }

    public Short getCostType() {
        return costType;
    }

    public void setCostType(Short costType) {
        this.costType = costType;
    }

    public Integer getCostNum() {
        return costNum;
    }

    public void setCostNum(Integer costNum) {
        this.costNum = costNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }
}