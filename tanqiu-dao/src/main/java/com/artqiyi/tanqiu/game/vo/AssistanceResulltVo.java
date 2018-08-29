package com.artqiyi.tanqiu.game.vo;
/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan
 * Author: wufuchang <17302023193@163.com>
 * Create On: 2018/07/05
 * Modify On: 2018/07/05 9:55 by wufuchang
 */

/**
 * 好友助力结果vo
 */
public class AssistanceResulltVo {

    public Integer getCurrentBallNum() {
        return currentBallNum;
    }

    public void setCurrentBallNum(Integer currentBallNum) {
        this.currentBallNum = currentBallNum;
    }

    public Boolean getAssistantOutCome() {
        return assistantOutCome;
    }

    public void setAssistantOutCome(Boolean assistantOutCome) {
        this.assistantOutCome = assistantOutCome;
    }

    private Boolean assistantOutCome;
    private Integer currentBallNum;
}
