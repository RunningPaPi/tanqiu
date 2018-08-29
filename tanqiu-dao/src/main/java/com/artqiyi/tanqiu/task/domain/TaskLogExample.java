package com.artqiyi.tanqiu.task.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Generated;

@Generated("task_log")
public class TaskLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public TaskLogExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return offset;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andTaskLogIdIsNull() {
            addCriterion("task_log_id is null");
            return (Criteria) this;
        }

        public Criteria andTaskLogIdIsNotNull() {
            addCriterion("task_log_id is not null");
            return (Criteria) this;
        }

        public Criteria andTaskLogIdEqualTo(Long value) {
            addCriterion("task_log_id =", value, "taskLogId");
            return (Criteria) this;
        }

        public Criteria andTaskLogIdNotEqualTo(Long value) {
            addCriterion("task_log_id <>", value, "taskLogId");
            return (Criteria) this;
        }

        public Criteria andTaskLogIdGreaterThan(Long value) {
            addCriterion("task_log_id >", value, "taskLogId");
            return (Criteria) this;
        }

        public Criteria andTaskLogIdGreaterThanOrEqualTo(Long value) {
            addCriterion("task_log_id >=", value, "taskLogId");
            return (Criteria) this;
        }

        public Criteria andTaskLogIdLessThan(Long value) {
            addCriterion("task_log_id <", value, "taskLogId");
            return (Criteria) this;
        }

        public Criteria andTaskLogIdLessThanOrEqualTo(Long value) {
            addCriterion("task_log_id <=", value, "taskLogId");
            return (Criteria) this;
        }

        public Criteria andTaskLogIdIn(List<Long> values) {
            addCriterion("task_log_id in", values, "taskLogId");
            return (Criteria) this;
        }

        public Criteria andTaskLogIdNotIn(List<Long> values) {
            addCriterion("task_log_id not in", values, "taskLogId");
            return (Criteria) this;
        }

        public Criteria andTaskLogIdBetween(Long value1, Long value2) {
            addCriterion("task_log_id between", value1, value2, "taskLogId");
            return (Criteria) this;
        }

        public Criteria andTaskLogIdNotBetween(Long value1, Long value2) {
            addCriterion("task_log_id not between", value1, value2, "taskLogId");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andTaskIdIsNull() {
            addCriterion("task_id is null");
            return (Criteria) this;
        }

        public Criteria andTaskIdIsNotNull() {
            addCriterion("task_id is not null");
            return (Criteria) this;
        }

        public Criteria andTaskIdEqualTo(Integer value) {
            addCriterion("task_id =", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotEqualTo(Integer value) {
            addCriterion("task_id <>", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdGreaterThan(Integer value) {
            addCriterion("task_id >", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("task_id >=", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLessThan(Integer value) {
            addCriterion("task_id <", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLessThanOrEqualTo(Integer value) {
            addCriterion("task_id <=", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdIn(List<Integer> values) {
            addCriterion("task_id in", values, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotIn(List<Integer> values) {
            addCriterion("task_id not in", values, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdBetween(Integer value1, Integer value2) {
            addCriterion("task_id between", value1, value2, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotBetween(Integer value1, Integer value2) {
            addCriterion("task_id not between", value1, value2, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskNameIsNull() {
            addCriterion("task_name is null");
            return (Criteria) this;
        }

        public Criteria andTaskNameIsNotNull() {
            addCriterion("task_name is not null");
            return (Criteria) this;
        }

        public Criteria andTaskNameEqualTo(String value) {
            addCriterion("task_name =", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameNotEqualTo(String value) {
            addCriterion("task_name <>", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameGreaterThan(String value) {
            addCriterion("task_name >", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameGreaterThanOrEqualTo(String value) {
            addCriterion("task_name >=", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameLessThan(String value) {
            addCriterion("task_name <", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameLessThanOrEqualTo(String value) {
            addCriterion("task_name <=", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameLike(String value) {
            addCriterion("task_name like", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameNotLike(String value) {
            addCriterion("task_name not like", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameIn(List<String> values) {
            addCriterion("task_name in", values, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameNotIn(List<String> values) {
            addCriterion("task_name not in", values, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameBetween(String value1, String value2) {
            addCriterion("task_name between", value1, value2, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameNotBetween(String value1, String value2) {
            addCriterion("task_name not between", value1, value2, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskDescIsNull() {
            addCriterion("task_desc is null");
            return (Criteria) this;
        }

        public Criteria andTaskDescIsNotNull() {
            addCriterion("task_desc is not null");
            return (Criteria) this;
        }

        public Criteria andTaskDescEqualTo(String value) {
            addCriterion("task_desc =", value, "taskDesc");
            return (Criteria) this;
        }

        public Criteria andTaskDescNotEqualTo(String value) {
            addCriterion("task_desc <>", value, "taskDesc");
            return (Criteria) this;
        }

        public Criteria andTaskDescGreaterThan(String value) {
            addCriterion("task_desc >", value, "taskDesc");
            return (Criteria) this;
        }

        public Criteria andTaskDescGreaterThanOrEqualTo(String value) {
            addCriterion("task_desc >=", value, "taskDesc");
            return (Criteria) this;
        }

        public Criteria andTaskDescLessThan(String value) {
            addCriterion("task_desc <", value, "taskDesc");
            return (Criteria) this;
        }

        public Criteria andTaskDescLessThanOrEqualTo(String value) {
            addCriterion("task_desc <=", value, "taskDesc");
            return (Criteria) this;
        }

        public Criteria andTaskDescLike(String value) {
            addCriterion("task_desc like", value, "taskDesc");
            return (Criteria) this;
        }

        public Criteria andTaskDescNotLike(String value) {
            addCriterion("task_desc not like", value, "taskDesc");
            return (Criteria) this;
        }

        public Criteria andTaskDescIn(List<String> values) {
            addCriterion("task_desc in", values, "taskDesc");
            return (Criteria) this;
        }

        public Criteria andTaskDescNotIn(List<String> values) {
            addCriterion("task_desc not in", values, "taskDesc");
            return (Criteria) this;
        }

        public Criteria andTaskDescBetween(String value1, String value2) {
            addCriterion("task_desc between", value1, value2, "taskDesc");
            return (Criteria) this;
        }

        public Criteria andTaskDescNotBetween(String value1, String value2) {
            addCriterion("task_desc not between", value1, value2, "taskDesc");
            return (Criteria) this;
        }

        public Criteria andTaskTypeIsNull() {
            addCriterion("task_type is null");
            return (Criteria) this;
        }

        public Criteria andTaskTypeIsNotNull() {
            addCriterion("task_type is not null");
            return (Criteria) this;
        }

        public Criteria andTaskTypeEqualTo(String value) {
            addCriterion("task_type =", value, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeNotEqualTo(String value) {
            addCriterion("task_type <>", value, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeGreaterThan(String value) {
            addCriterion("task_type >", value, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeGreaterThanOrEqualTo(String value) {
            addCriterion("task_type >=", value, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeLessThan(String value) {
            addCriterion("task_type <", value, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeLessThanOrEqualTo(String value) {
            addCriterion("task_type <=", value, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeLike(String value) {
            addCriterion("task_type like", value, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeNotLike(String value) {
            addCriterion("task_type not like", value, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeIn(List<String> values) {
            addCriterion("task_type in", values, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeNotIn(List<String> values) {
            addCriterion("task_type not in", values, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeBetween(String value1, String value2) {
            addCriterion("task_type between", value1, value2, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskTypeNotBetween(String value1, String value2) {
            addCriterion("task_type not between", value1, value2, "taskType");
            return (Criteria) this;
        }

        public Criteria andTaskDisplayIsNull() {
            addCriterion("task_display is null");
            return (Criteria) this;
        }

        public Criteria andTaskDisplayIsNotNull() {
            addCriterion("task_display is not null");
            return (Criteria) this;
        }

        public Criteria andTaskDisplayEqualTo(Short value) {
            addCriterion("task_display =", value, "taskDisplay");
            return (Criteria) this;
        }

        public Criteria andTaskDisplayNotEqualTo(Short value) {
            addCriterion("task_display <>", value, "taskDisplay");
            return (Criteria) this;
        }

        public Criteria andTaskDisplayGreaterThan(Short value) {
            addCriterion("task_display >", value, "taskDisplay");
            return (Criteria) this;
        }

        public Criteria andTaskDisplayGreaterThanOrEqualTo(Short value) {
            addCriterion("task_display >=", value, "taskDisplay");
            return (Criteria) this;
        }

        public Criteria andTaskDisplayLessThan(Short value) {
            addCriterion("task_display <", value, "taskDisplay");
            return (Criteria) this;
        }

        public Criteria andTaskDisplayLessThanOrEqualTo(Short value) {
            addCriterion("task_display <=", value, "taskDisplay");
            return (Criteria) this;
        }

        public Criteria andTaskDisplayIn(List<Short> values) {
            addCriterion("task_display in", values, "taskDisplay");
            return (Criteria) this;
        }

        public Criteria andTaskDisplayNotIn(List<Short> values) {
            addCriterion("task_display not in", values, "taskDisplay");
            return (Criteria) this;
        }

        public Criteria andTaskDisplayBetween(Short value1, Short value2) {
            addCriterion("task_display between", value1, value2, "taskDisplay");
            return (Criteria) this;
        }

        public Criteria andTaskDisplayNotBetween(Short value1, Short value2) {
            addCriterion("task_display not between", value1, value2, "taskDisplay");
            return (Criteria) this;
        }

        public Criteria andTaskRepetNumIsNull() {
            addCriterion("task_repet_num is null");
            return (Criteria) this;
        }

        public Criteria andTaskRepetNumIsNotNull() {
            addCriterion("task_repet_num is not null");
            return (Criteria) this;
        }

        public Criteria andTaskRepetNumEqualTo(Integer value) {
            addCriterion("task_repet_num =", value, "taskRepetNum");
            return (Criteria) this;
        }

        public Criteria andTaskRepetNumNotEqualTo(Integer value) {
            addCriterion("task_repet_num <>", value, "taskRepetNum");
            return (Criteria) this;
        }

        public Criteria andTaskRepetNumGreaterThan(Integer value) {
            addCriterion("task_repet_num >", value, "taskRepetNum");
            return (Criteria) this;
        }

        public Criteria andTaskRepetNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("task_repet_num >=", value, "taskRepetNum");
            return (Criteria) this;
        }

        public Criteria andTaskRepetNumLessThan(Integer value) {
            addCriterion("task_repet_num <", value, "taskRepetNum");
            return (Criteria) this;
        }

        public Criteria andTaskRepetNumLessThanOrEqualTo(Integer value) {
            addCriterion("task_repet_num <=", value, "taskRepetNum");
            return (Criteria) this;
        }

        public Criteria andTaskRepetNumIn(List<Integer> values) {
            addCriterion("task_repet_num in", values, "taskRepetNum");
            return (Criteria) this;
        }

        public Criteria andTaskRepetNumNotIn(List<Integer> values) {
            addCriterion("task_repet_num not in", values, "taskRepetNum");
            return (Criteria) this;
        }

        public Criteria andTaskRepetNumBetween(Integer value1, Integer value2) {
            addCriterion("task_repet_num between", value1, value2, "taskRepetNum");
            return (Criteria) this;
        }

        public Criteria andTaskRepetNumNotBetween(Integer value1, Integer value2) {
            addCriterion("task_repet_num not between", value1, value2, "taskRepetNum");
            return (Criteria) this;
        }

        public Criteria andTaskTypeAttrIsNull() {
            addCriterion("task_type_attr is null");
            return (Criteria) this;
        }

        public Criteria andTaskTypeAttrIsNotNull() {
            addCriterion("task_type_attr is not null");
            return (Criteria) this;
        }

        public Criteria andTaskTypeAttrEqualTo(Short value) {
            addCriterion("task_type_attr =", value, "taskTypeAttr");
            return (Criteria) this;
        }

        public Criteria andTaskTypeAttrNotEqualTo(Short value) {
            addCriterion("task_type_attr <>", value, "taskTypeAttr");
            return (Criteria) this;
        }

        public Criteria andTaskTypeAttrGreaterThan(Short value) {
            addCriterion("task_type_attr >", value, "taskTypeAttr");
            return (Criteria) this;
        }

        public Criteria andTaskTypeAttrGreaterThanOrEqualTo(Short value) {
            addCriterion("task_type_attr >=", value, "taskTypeAttr");
            return (Criteria) this;
        }

        public Criteria andTaskTypeAttrLessThan(Short value) {
            addCriterion("task_type_attr <", value, "taskTypeAttr");
            return (Criteria) this;
        }

        public Criteria andTaskTypeAttrLessThanOrEqualTo(Short value) {
            addCriterion("task_type_attr <=", value, "taskTypeAttr");
            return (Criteria) this;
        }

        public Criteria andTaskTypeAttrIn(List<Short> values) {
            addCriterion("task_type_attr in", values, "taskTypeAttr");
            return (Criteria) this;
        }

        public Criteria andTaskTypeAttrNotIn(List<Short> values) {
            addCriterion("task_type_attr not in", values, "taskTypeAttr");
            return (Criteria) this;
        }

        public Criteria andTaskTypeAttrBetween(Short value1, Short value2) {
            addCriterion("task_type_attr between", value1, value2, "taskTypeAttr");
            return (Criteria) this;
        }

        public Criteria andTaskTypeAttrNotBetween(Short value1, Short value2) {
            addCriterion("task_type_attr not between", value1, value2, "taskTypeAttr");
            return (Criteria) this;
        }

        public Criteria andTaskTypeCatalogIsNull() {
            addCriterion("task_type_catalog is null");
            return (Criteria) this;
        }

        public Criteria andTaskTypeCatalogIsNotNull() {
            addCriterion("task_type_catalog is not null");
            return (Criteria) this;
        }

        public Criteria andTaskTypeCatalogEqualTo(Short value) {
            addCriterion("task_type_catalog =", value, "taskTypeCatalog");
            return (Criteria) this;
        }

        public Criteria andTaskTypeCatalogNotEqualTo(Short value) {
            addCriterion("task_type_catalog <>", value, "taskTypeCatalog");
            return (Criteria) this;
        }

        public Criteria andTaskTypeCatalogGreaterThan(Short value) {
            addCriterion("task_type_catalog >", value, "taskTypeCatalog");
            return (Criteria) this;
        }

        public Criteria andTaskTypeCatalogGreaterThanOrEqualTo(Short value) {
            addCriterion("task_type_catalog >=", value, "taskTypeCatalog");
            return (Criteria) this;
        }

        public Criteria andTaskTypeCatalogLessThan(Short value) {
            addCriterion("task_type_catalog <", value, "taskTypeCatalog");
            return (Criteria) this;
        }

        public Criteria andTaskTypeCatalogLessThanOrEqualTo(Short value) {
            addCriterion("task_type_catalog <=", value, "taskTypeCatalog");
            return (Criteria) this;
        }

        public Criteria andTaskTypeCatalogIn(List<Short> values) {
            addCriterion("task_type_catalog in", values, "taskTypeCatalog");
            return (Criteria) this;
        }

        public Criteria andTaskTypeCatalogNotIn(List<Short> values) {
            addCriterion("task_type_catalog not in", values, "taskTypeCatalog");
            return (Criteria) this;
        }

        public Criteria andTaskTypeCatalogBetween(Short value1, Short value2) {
            addCriterion("task_type_catalog between", value1, value2, "taskTypeCatalog");
            return (Criteria) this;
        }

        public Criteria andTaskTypeCatalogNotBetween(Short value1, Short value2) {
            addCriterion("task_type_catalog not between", value1, value2, "taskTypeCatalog");
            return (Criteria) this;
        }

        public Criteria andTaskStateIsNull() {
            addCriterion("task_state is null");
            return (Criteria) this;
        }

        public Criteria andTaskStateIsNotNull() {
            addCriterion("task_state is not null");
            return (Criteria) this;
        }

        public Criteria andTaskStateEqualTo(Short value) {
            addCriterion("task_state =", value, "taskState");
            return (Criteria) this;
        }

        public Criteria andTaskStateNotEqualTo(Short value) {
            addCriterion("task_state <>", value, "taskState");
            return (Criteria) this;
        }

        public Criteria andTaskStateGreaterThan(Short value) {
            addCriterion("task_state >", value, "taskState");
            return (Criteria) this;
        }

        public Criteria andTaskStateGreaterThanOrEqualTo(Short value) {
            addCriterion("task_state >=", value, "taskState");
            return (Criteria) this;
        }

        public Criteria andTaskStateLessThan(Short value) {
            addCriterion("task_state <", value, "taskState");
            return (Criteria) this;
        }

        public Criteria andTaskStateLessThanOrEqualTo(Short value) {
            addCriterion("task_state <=", value, "taskState");
            return (Criteria) this;
        }

        public Criteria andTaskStateIn(List<Short> values) {
            addCriterion("task_state in", values, "taskState");
            return (Criteria) this;
        }

        public Criteria andTaskStateNotIn(List<Short> values) {
            addCriterion("task_state not in", values, "taskState");
            return (Criteria) this;
        }

        public Criteria andTaskStateBetween(Short value1, Short value2) {
            addCriterion("task_state between", value1, value2, "taskState");
            return (Criteria) this;
        }

        public Criteria andTaskStateNotBetween(Short value1, Short value2) {
            addCriterion("task_state not between", value1, value2, "taskState");
            return (Criteria) this;
        }

        public Criteria andSuccessorTaskIdIsNull() {
            addCriterion("successor_task_id is null");
            return (Criteria) this;
        }

        public Criteria andSuccessorTaskIdIsNotNull() {
            addCriterion("successor_task_id is not null");
            return (Criteria) this;
        }

        public Criteria andSuccessorTaskIdEqualTo(Integer value) {
            addCriterion("successor_task_id =", value, "successorTaskId");
            return (Criteria) this;
        }

        public Criteria andSuccessorTaskIdNotEqualTo(Integer value) {
            addCriterion("successor_task_id <>", value, "successorTaskId");
            return (Criteria) this;
        }

        public Criteria andSuccessorTaskIdGreaterThan(Integer value) {
            addCriterion("successor_task_id >", value, "successorTaskId");
            return (Criteria) this;
        }

        public Criteria andSuccessorTaskIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("successor_task_id >=", value, "successorTaskId");
            return (Criteria) this;
        }

        public Criteria andSuccessorTaskIdLessThan(Integer value) {
            addCriterion("successor_task_id <", value, "successorTaskId");
            return (Criteria) this;
        }

        public Criteria andSuccessorTaskIdLessThanOrEqualTo(Integer value) {
            addCriterion("successor_task_id <=", value, "successorTaskId");
            return (Criteria) this;
        }

        public Criteria andSuccessorTaskIdIn(List<Integer> values) {
            addCriterion("successor_task_id in", values, "successorTaskId");
            return (Criteria) this;
        }

        public Criteria andSuccessorTaskIdNotIn(List<Integer> values) {
            addCriterion("successor_task_id not in", values, "successorTaskId");
            return (Criteria) this;
        }

        public Criteria andSuccessorTaskIdBetween(Integer value1, Integer value2) {
            addCriterion("successor_task_id between", value1, value2, "successorTaskId");
            return (Criteria) this;
        }

        public Criteria andSuccessorTaskIdNotBetween(Integer value1, Integer value2) {
            addCriterion("successor_task_id not between", value1, value2, "successorTaskId");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}