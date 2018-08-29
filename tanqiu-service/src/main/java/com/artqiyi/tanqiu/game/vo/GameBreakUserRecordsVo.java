package com.artqiyi.tanqiu.game.vo;

/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: tanqiu
 * Author: chencunjun  <1078027943@qq.com>
 * Create On: 2018/7/3
 * Modify On: 2018/7/3 by chencunjun
 */

import java.util.Date;

/**
 * 闯关游戏比赛记录vo
 */
public class GameBreakUserRecordsVo {

    private Long id;

    private Long gameId;

    private String gameNo;

    private Long userId;

    private String nickName;

    private String headUrl;

    private Integer recoveryTimes;

    private Short passMaxLevel;

    private Boolean isPass;

    private Integer score;

    private String note;

    private int gameTime;

    private Date createTime;

    private Date updateTime;

    private Object data;//弹球初始渲染数据

    private long againstId;//对手id

    private Object agaistData;//对手弹球初始渲染数据

    private String agaistNickName;//对手昵称

    private String agaistHeadUrl;//对手头像

    private boolean isWin;//是否取胜

    private boolean islive;//是否存活

    private int gender;//性别

    private int agaistGender;//对手性别
    private boolean robot;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getGameNo() {
        return gameNo;
    }

    public void setGameNo(String gameNo) {
        this.gameNo = gameNo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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

    public Integer getRecoveryTimes() {
        return recoveryTimes;
    }

    public void setRecoveryTimes(Integer recoveryTimes) {
        this.recoveryTimes = recoveryTimes;
    }

    public Short getPassMaxLevel() {
        return passMaxLevel;
    }

    public void setPassMaxLevel(Short passMaxLevel) {
        this.passMaxLevel = passMaxLevel;
    }

    public Boolean getPass() {
        return isPass;
    }

    public void setPass(Boolean pass) {
        isPass = pass;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public boolean isWin() {
        return isWin;
    }

    public void setWin(boolean win) {
        isWin = win;
    }

    public boolean isIslive() {
        return islive;
    }

    public void setIslive(boolean islive) {
        this.islive = islive;
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

    public int getGameTime() {
        return gameTime;
    }

    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }

    public boolean isRobot() {
        return robot;
    }

    public void setRobot(boolean robot) {
        this.robot = robot;
    }
}
