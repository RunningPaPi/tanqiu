package com.artqiyi.tanqiu.task.domain;

import java.util.Date;

public class TaskRewardRule {
    private Integer rewardRuleId;

    private String rewardRuleName;

    private Short awardType;

    private String awardName;

    private Integer awardNum;

    private Date updateTime;

    private Date createTime;

    public Integer getRewardRuleId() {
        return rewardRuleId;
    }

    public void setRewardRuleId(Integer rewardRuleId) {
        this.rewardRuleId = rewardRuleId;
    }

    public String getRewardRuleName() {
        return rewardRuleName;
    }

    public void setRewardRuleName(String rewardRuleName) {
        this.rewardRuleName = rewardRuleName;
    }

    public Short getAwardType() {
        return awardType;
    }

    public void setAwardType(Short awardType) {
        this.awardType = awardType;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public Integer getAwardNum() {
        return awardNum;
    }

    public void setAwardNum(Integer awardNum) {
        this.awardNum = awardNum;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}