package com.artqiyi.tanqiu.welfare.domain;

import java.util.Date;

public class Welfare {
    private Long id;

    private String welfareName;

    private String welfareDesc;

    private Integer welfareType;

    private Integer awardType;

    private Integer awardNum;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}