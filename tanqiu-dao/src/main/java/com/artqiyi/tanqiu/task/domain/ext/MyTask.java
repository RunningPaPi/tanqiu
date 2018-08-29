/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: qudianwan-dao
 * Author: author  wushyue@gmail.com
 * Create On: Apr 27, 2018 11:06:50 AM
 * Modify On: Apr 27, 2018 11:06:50 AM by wushyue@gmail.com
 */
package com.artqiyi.tanqiu.task.domain.ext;

import java.util.List;

import com.artqiyi.tanqiu.task.domain.TaskRewardRule;

/** 
 * 点击任务按钮后, 能看到的任务, 包括待做的任务和当天进行中的任务
 *
 * @author wushuang
 * @since 2018-04-27
 */
public class MyTask {
    private Integer taskId;

    private String taskName;

	private String taskDesc;

	private Integer taskRepetNum;

	private Short taskTypeAttr;

	//private Short taskTypeCatalog;

    private String taskType;

	//private Short taskDisplay;

	//private Integer successorTaskId;

    private Short taskState;

	private Integer taskFinishedNum;
    
    private List<TaskRewardRule> rewardRuleList;

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}


	public Short getTaskState() {
		return taskState;
	}

	public void setTaskState(Short taskState) {
		this.taskState = taskState;
	}

	public List<TaskRewardRule> getRewardRuleList() {
		return rewardRuleList;
	}

	public void setRewardRuleList(List<TaskRewardRule> rewardRuleList) {
		this.rewardRuleList = rewardRuleList;
	}
	
	

	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}

	public Integer getTaskRepetNum() {
		return taskRepetNum;
	}

	public void setTaskRepetNum(Integer taskRepetNum) {
		this.taskRepetNum = taskRepetNum;
	}

	public Short getTaskTypeAttr() {
		return taskTypeAttr;
	}

	public void setTaskTypeAttr(Short taskTypeAttr) {
		this.taskTypeAttr = taskTypeAttr;
	}

	/*public Short getTaskTypeCatalog() {
		return taskTypeCatalog;
	}

	public void setTaskTypeCatalog(Short taskTypeCatalog) {
		this.taskTypeCatalog = taskTypeCatalog;
	}

	public Short getTaskDisplay() {
		return taskDisplay;
	}

	public void setTaskDisplay(Short taskDisplay) {
		this.taskDisplay = taskDisplay;
	}

	public Integer getSuccessorTaskId() {
		return successorTaskId;
	}

	public void setSuccessorTaskId(Integer successorTaskId) {
		this.successorTaskId = successorTaskId;
	}*/

	public Integer getTaskFinishedNum() {
		return taskFinishedNum;
	}

	public void setTaskFinishedNum(Integer taskFinishedNum) {
		this.taskFinishedNum = taskFinishedNum;
	}

	@Override
	public String toString() {
		return "MyTask [taskId=" + taskId + ", taskName=" + taskName + ", taskType=" + taskType +  ", taskState=" + taskState + ", rewardRuleList=" + rewardRuleList + "]";
	}

}
