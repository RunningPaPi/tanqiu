package com.artqiyi.tanqiu.game.vo;

/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: tanqiu
 * Author: chencunjun  <1078027943@qq.com>
 * Create On: 2018/7/4
 * Modify On: 2018/7/4 by chencunjun
 */

import java.util.Date;

/**
 * 好友对战信息
 */
public class GameFightUserRecordsVo {
    private String roomNo; //房间编码

    private long userId; //用户id

    private String nickName;

    private String headUrl;

    private Integer score;

    private int gameTime;

    private Date createTime;

    private Date updateTime;

    private Object data;//弹球初始渲染数据

    private long againstId;//对手id

    private Object agaistData;//对手弹球初始渲染数据

    private String agaistNickName;//对手昵称

    private String agaistHeadUrl;//对手头像

    private int gender;//性别

    private int agaistGender;//对手性别

    private boolean isComplete;//是否完成比赛

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public int getGameTime() {
        return gameTime;
    }

    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public long getAgainstId() {
        return againstId;
    }

    public void setAgainstId(long againstId) {
        this.againstId = againstId;
    }

    public Object getAgaistData() {
        return agaistData;
    }

    public void setAgaistData(Object agaistData) {
        this.agaistData = agaistData;
    }

    public String getAgaistNickName() {
        return agaistNickName;
    }

    public void setAgaistNickName(String agaistNickName) {
        this.agaistNickName = agaistNickName;
    }

    public String getAgaistHeadUrl() {
        return agaistHeadUrl;
    }

    public void setAgaistHeadUrl(String agaistHeadUrl) {
        this.agaistHeadUrl = agaistHeadUrl;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAgaistGender() {
        return agaistGender;
    }

    public void setAgaistGender(int agaistGender) {
        this.agaistGender = agaistGender;
    }
}
