package com.artqiyi.tanqiu.task.domain;

import java.util.Date;

public class Task {
    private Integer taskId;

    private String taskName;

    private String taskDesc;

    private Integer taskRepetNum;

    private Short taskTypeAttr;

    private Short taskTypeCatalog;

    private String taskType;

    private Short taskDisplay;

    private Integer successorTaskId;

    private Date updateTime;

    private Date createTime;

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

    public Short getTaskTypeCatalog() {
        return taskTypeCatalog;
    }

    public void setTaskTypeCatalog(Short taskTypeCatalog) {
        this.taskTypeCatalog = taskTypeCatalog;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
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