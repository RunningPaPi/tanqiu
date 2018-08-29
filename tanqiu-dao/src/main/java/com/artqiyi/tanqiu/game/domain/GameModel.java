package com.artqiyi.tanqiu.game.domain;

import java.util.Date;

public class GameModel {
    private Long id;

    private String gameModelKey;

    private String gameModelName;

    private String iconUrl;

    private String bgImg;

    private String iconUrlX;

    private String bgImgX;

    private Short sort;

    private Short status;

    private Boolean deleted;

    private String remark;

    private String extentstr1;

    private String extentstr2;

    private Long createId;

    private Date createTime;

    private Long updatorId;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGameModelKey() {
        return gameModelKey;
    }

    public void setGameModelKey(String gameModelKey) {
        this.gameModelKey = gameModelKey == null ? null : gameModelKey.trim();
    }

    public String getGameModelName() {
        return gameModelName;
    }

    public void setGameModelName(String gameModelName) {
        this.gameModelName = gameModelName == null ? null : gameModelName.trim();
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl == null ? null : iconUrl.trim();
    }

    public String getBgImg() {
        return bgImg;
    }

    public void setBgImg(String bgImg) {
        this.bgImg = bgImg == null ? null : bgImg.trim();
    }

    public String getIconUrlX() {
        return iconUrlX;
    }

    public void setIconUrlX(String iconUrlX) {
        this.iconUrlX = iconUrlX == null ? null : iconUrlX.trim();
    }

    public String getBgImgX() {
        return bgImgX;
    }

    public void setBgImgX(String bgImgX) {
        this.bgImgX = bgImgX == null ? null : bgImgX.trim();
    }

    public Short getSort() {
        return sort;
    }

    public void setSort(Short sort) {
        this.sort = sort;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getExtentstr1() {
        return extentstr1;
    }

    public void setExtentstr1(String extentstr1) {
        this.extentstr1 = extentstr1 == null ? null : extentstr1.trim();
    }

    public String getExtentstr2() {
        return extentstr2;
    }

    public void setExtentstr2(String extentstr2) {
        this.extentstr2 = extentstr2 == null ? null : extentstr2.trim();
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(Long updatorId) {
        this.updatorId = updatorId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}