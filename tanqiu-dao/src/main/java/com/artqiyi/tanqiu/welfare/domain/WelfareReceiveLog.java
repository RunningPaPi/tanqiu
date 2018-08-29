package com.artqiyi.tanqiu.welfare.domain;

import java.util.Date;

public class WelfareReceiveLog {
    private Long id;

    private Long userId;

    private Long welfareId;

    private String welfareName;

    private String welfareDesc;

    private Integer welfareType;

    private Integer awardType;

    private Integer awardNum;

    private Integer status;

    private Long friendUserId;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getWelfareId() {
        return welfareId;
    }

    public void setWelfareId(Long welfareId) {
        this.welfareId = welfareId;
    }

    public String getWelfareName() {
        return welfareName;
    }

    public void setWelfareName(String welfareName) {
        this.welfareName = welfareName;
    }

    public String getWelfareDesc() {
        return welfareDesc;
    }

    public void setWelfareDesc(String welfareDesc) {
        this.welfareDesc = welfareDesc;
    }

    public Integer getWelfareType() {
        return welfareType;
    }

    public void setWelfareType(Integer welfareType) {
        this.welfareType = welfareType;
    }

    public Integer getAwardType() {
        return awardType;
    }

    public void setAwardType(Integer awardType) {
        this.awardType = awardType;
    }

    public Integer getAwardNum() {
        return awardNum;
    }

    public void setAwardNum(Integer awardNum) {
        this.awardNum = awardNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getFriendUserId() {
        return friendUserId;
    }

    public void setFriendUserId(Long friendUserId) {
        this.friendUserId = friendUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}