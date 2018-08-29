package com.artqiyi.tanqiu.game.domain;

import java.util.Date;

public class GameRedpacketRewardRecord {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_redpacket_reward_record.id
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_redpacket_reward_record.game_model_key
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    private String gameModelKey;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_redpacket_reward_record.game_no
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    private String gameNo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_redpacket_reward_record.user_id
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    private Long userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_redpacket_reward_record.nick_name
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    private String nickName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_redpacket_reward_record.head_url
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    private String headUrl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_redpacket_reward_record.rank
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    private Integer rank;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_redpacket_reward_record.max_score
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    private Integer maxScore;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_redpacket_reward_record.reward_type
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    private Short rewardType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_redpacket_reward_record.reward_num
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    private Integer rewardNum;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_redpacket_reward_record.ranked_time
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    private Date rankedTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_redpacket_reward_record.is_read
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    private Boolean isRead;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_redpacket_reward_record.create_time
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_redpacket_reward_record.update_time
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_redpacket_reward_record.id
     *
     * @return the value of game_redpacket_reward_record.id
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_redpacket_reward_record.id
     *
     * @param id the value for game_redpacket_reward_record.id
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_redpacket_reward_record.game_model_key
     *
     * @return the value of game_redpacket_reward_record.game_model_key
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    public String getGameModelKey() {
        return gameModelKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_redpacket_reward_record.game_model_key
     *
     * @param gameModelKey the value for game_redpacket_reward_record.game_model_key
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    public void setGameModelKey(String gameModelKey) {
        this.gameModelKey = gameModelKey == null ? null : gameModelKey.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_redpacket_reward_record.game_no
     *
     * @return the value of game_redpacket_reward_record.game_no
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    public String getGameNo() {
        return gameNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_redpacket_reward_record.game_no
     *
     * @param gameNo the value for game_redpacket_reward_record.game_no
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    public void setGameNo(String gameNo) {
        this.gameNo = gameNo == null ? null : gameNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_redpacket_reward_record.user_id
     *
     * @return the value of game_redpacket_reward_record.user_id
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_redpacket_reward_record.user_id
     *
     * @param userId the value for game_redpacket_reward_record.user_id
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_redpacket_reward_record.nick_name
     *
     * @return the value of game_redpacket_reward_record.nick_name
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_redpacket_reward_record.nick_name
     *
     * @param nickName the value for game_redpacket_reward_record.nick_name
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_redpacket_reward_record.head_url
     *
     * @return the value of game_redpacket_reward_record.head_url
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    public String getHeadUrl() {
        return headUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_redpacket_reward_record.head_url
     *
     * @param headUrl the value for game_redpacket_reward_record.head_url
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl == null ? null : headUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_redpacket_reward_record.rank
     *
     * @return the value of game_redpacket_reward_record.rank
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_redpacket_reward_record.rank
     *
     * @param rank the value for game_redpacket_reward_record.rank
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_redpacket_reward_record.max_score
     *
     * @return the value of game_redpacket_reward_record.max_score
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    public Integer getMaxScore() {
        return maxScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_redpacket_reward_record.max_score
     *
     * @param maxScore the value for game_redpacket_reward_record.max_score
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_redpacket_reward_record.reward_type
     *
     * @return the value of game_redpacket_reward_record.reward_type
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    public Short getRewardType() {
        return rewardType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_redpacket_reward_record.reward_type
     *
     * @param rewardType the value for game_redpacket_reward_record.reward_type
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    public void setRewardType(Short rewardType) {
        this.rewardType = rewardType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_redpacket_reward_record.reward_num
     *
     * @return the value of game_redpacket_reward_record.reward_num
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    public Integer getRewardNum() {
        return rewardNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_redpacket_reward_record.reward_num
     *
     * @param rewardNum the value for game_redpacket_reward_record.reward_num
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    public void setRewardNum(Integer rewardNum) {
        this.rewardNum = rewardNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_redpacket_reward_record.ranked_time
     *
     * @return the value of game_redpacket_reward_record.ranked_time
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    public Date getRankedTime() {
        return rankedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_redpacket_reward_record.ranked_time
     *
     * @param rankedTime the value for game_redpacket_reward_record.ranked_time
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    public void setRankedTime(Date rankedTime) {
        this.rankedTime = rankedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_redpacket_reward_record.is_read
     *
     * @return the value of game_redpacket_reward_record.is_read
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    public Boolean getIsRead() {
        return isRead;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_redpacket_reward_record.is_read
     *
     * @param isRead the value for game_redpacket_reward_record.is_read
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_redpacket_reward_record.create_time
     *
     * @return the value of game_redpacket_reward_record.create_time
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_redpacket_reward_record.create_time
     *
     * @param createTime the value for game_redpacket_reward_record.create_time
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_redpacket_reward_record.update_time
     *
     * @return the value of game_redpacket_reward_record.update_time
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_redpacket_reward_record.update_time
     *
     * @param updateTime the value for game_redpacket_reward_record.update_time
     *
     * @mbg.generated Sat Jul 28 18:17:07 CST 2018
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}