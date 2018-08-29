package com.artqiyi.tanqiu.task.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Generated;

@Generated("task_reward_rule")
public class TaskRewardRuleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public TaskRewardRuleExample() {
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

        public Criteria andRewardRuleIdIsNull() {
            addCriterion("reward_rule_id is null");
            return (Criteria) this;
        }

        public Criteria andRewardRuleIdIsNotNull() {
            addCriterion("reward_rule_id is not null");
            return (Criteria) this;
        }

        public Criteria andRewardRuleIdEqualTo(Integer value) {
            addCriterion("reward_rule_id =", value, "rewardRuleId");
            return (Criteria) this;
        }

        public Criteria andRewardRuleIdNotEqualTo(Integer value) {
            addCriterion("reward_rule_id <>", value, "rewardRuleId");
            return (Criteria) this;
        }

        public Criteria andRewardRuleIdGreaterThan(Integer value) {
            addCriterion("reward_rule_id >", value, "rewardRuleId");
            return (Criteria) this;
        }

        public Criteria andRewardRuleIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("reward_rule_id >=", value, "rewardRuleId");
            return (Criteria) this;
        }

        public Criteria andRewardRuleIdLessThan(Integer value) {
            addCriterion("reward_rule_id <", value, "rewardRuleId");
            return (Criteria) this;
        }

        public Criteria andRewardRuleIdLessThanOrEqualTo(Integer value) {
            addCriterion("reward_rule_id <=", value, "rewardRuleId");
            return (Criteria) this;
        }

        public Criteria andRewardRuleIdIn(List<Integer> values) {
            addCriterion("reward_rule_id in", values, "rewardRuleId");
            return (Criteria) this;
        }

        public Criteria andRewardRuleIdNotIn(List<Integer> values) {
            addCriterion("reward_rule_id not in", values, "rewardRuleId");
            return (Criteria) this;
        }

        public Criteria andRewardRuleIdBetween(Integer value1, Integer value2) {
            addCriterion("reward_rule_id between", value1, value2, "rewardRuleId");
            return (Criteria) this;
        }

        public Criteria andRewardRuleIdNotBetween(Integer value1, Integer value2) {
            addCriterion("reward_rule_id not between", value1, value2, "rewardRuleId");
            return (Criteria) this;
        }

        public Criteria andRewardRuleNameIsNull() {
            addCriterion("reward_rule_name is null");
            return (Criteria) this;
        }

        public Criteria andRewardRuleNameIsNotNull() {
            addCriterion("reward_rule_name is not null");
            return (Criteria) this;
        }

        public Criteria andRewardRuleNameEqualTo(String value) {
            addCriterion("reward_rule_name =", value, "rewardRuleName");
            return (Criteria) this;
        }

        public Criteria andRewardRuleNameNotEqualTo(String value) {
            addCriterion("reward_rule_name <>", value, "rewardRuleName");
            return (Criteria) this;
        }

        public Criteria andRewardRuleNameGreaterThan(String value) {
            addCriterion("reward_rule_name >", value, "rewardRuleName");
            return (Criteria) this;
        }

        public Criteria andRewardRuleNameGreaterThanOrEqualTo(String value) {
            addCriterion("reward_rule_name >=", value, "rewardRuleName");
            return (Criteria) this;
        }

        public Criteria andRewardRuleNameLessThan(String value) {
            addCriterion("reward_rule_name <", value, "rewardRuleName");
            return (Criteria) this;
        }

        public Criteria andRewardRuleNameLessThanOrEqualTo(String value) {
            addCriterion("reward_rule_name <=", value, "rewardRuleName");
            return (Criteria) this;
        }

        public Criteria andRewardRuleNameLike(String value) {
            addCriterion("reward_rule_name like", value, "rewardRuleName");
            return (Criteria) this;
        }

        public Criteria andRewardRuleNameNotLike(String value) {
            addCriterion("reward_rule_name not like", value, "rewardRuleName");
            return (Criteria) this;
        }

        public Criteria andRewardRuleNameIn(List<String> values) {
            addCriterion("reward_rule_name in", values, "rewardRuleName");
            return (Criteria) this;
        }

        public Criteria andRewardRuleNameNotIn(List<String> values) {
            addCriterion("reward_rule_name not in", values, "rewardRuleName");
            return (Criteria) this;
        }

        public Criteria andRewardRuleNameBetween(String value1, String value2) {
            addCriterion("reward_rule_name between", value1, value2, "rewardRuleName");
            return (Criteria) this;
        }

        public Criteria andRewardRuleNameNotBetween(String value1, String value2) {
            addCriterion("reward_rule_name not between", value1, value2, "rewardRuleName");
            return (Criteria) this;
        }

        public Criteria andAwardTypeIsNull() {
            addCriterion("award_type is null");
            return (Criteria) this;
        }

        public Criteria andAwardTypeIsNotNull() {
            addCriterion("award_type is not null");
            return (Criteria) this;
        }

        public Criteria andAwardTypeEqualTo(Short value) {
            addCriterion("award_type =", value, "awardType");
            return (Criteria) this;
        }

        public Criteria andAwardTypeNotEqualTo(Short value) {
            addCriterion("award_type <>", value, "awardType");
            return (Criteria) this;
        }

        public Criteria andAwardTypeGreaterThan(Short value) {
            addCriterion("award_type >", value, "awardType");
            return (Criteria) this;
        }

        public Criteria andAwardTypeGreaterThanOrEqualTo(Short value) {
            addCriterion("award_type >=", value, "awardType");
            return (Criteria) this;
        }

        public Criteria andAwardTypeLessThan(Short value) {
            addCriterion("award_type <", value, "awardType");
            return (Criteria) this;
        }

        public Criteria andAwardTypeLessThanOrEqualTo(Short value) {
            addCriterion("award_type <=", value, "awardType");
            return (Criteria) this;
        }

        public Criteria andAwardTypeIn(List<Short> values) {
            addCriterion("award_type in", values, "awardType");
            return (Criteria) this;
        }

        public Criteria andAwardTypeNotIn(List<Short> values) {
            addCriterion("award_type not in", values, "awardType");
            return (Criteria) this;
        }

        public Criteria andAwardTypeBetween(Short value1, Short value2) {
            addCriterion("award_type between", value1, value2, "awardType");
            return (Criteria) this;
        }

        public Criteria andAwardTypeNotBetween(Short value1, Short value2) {
            addCriterion("award_type not between", value1, value2, "awardType");
            return (Criteria) this;
        }

        public Criteria andAwardNameIsNull() {
            addCriterion("award_name is null");
            return (Criteria) this;
        }

        public Criteria andAwardNameIsNotNull() {
            addCriterion("award_name is not null");
            return (Criteria) this;
        }

        public Criteria andAwardNameEqualTo(String value) {
            addCriterion("award_name =", value, "awardName");
            return (Criteria) this;
        }

        public Criteria andAwardNameNotEqualTo(String value) {
            addCriterion("award_name <>", value, "awardName");
            return (Criteria) this;
        }

        public Criteria andAwardNameGreaterThan(String value) {
            addCriterion("award_name >", value, "awardName");
            return (Criteria) this;
        }

        public Criteria andAwardNameGreaterThanOrEqualTo(String value) {
            addCriterion("award_name >=", value, "awardName");
            return (Criteria) this;
        }

        public Criteria andAwardNameLessThan(String value) {
            addCriterion("award_name <", value, "awardName");
            return (Criteria) this;
        }

        public Criteria andAwardNameLessThanOrEqualTo(String value) {
            addCriterion("award_name <=", value, "awardName");
            return (Criteria) this;
        }

        public Criteria andAwardNameLike(String value) {
            addCriterion("award_name like", value, "awardName");
            return (Criteria) this;
        }

        public Criteria andAwardNameNotLike(String value) {
            addCriterion("award_name not like", value, "awardName");
            return (Criteria) this;
        }

        public Criteria andAwardNameIn(List<String> values) {
            addCriterion("award_name in", values, "awardName");
            return (Criteria) this;
        }

        public Criteria andAwardNameNotIn(List<String> values) {
            addCriterion("award_name not in", values, "awardName");
            return (Criteria) this;
        }

        public Criteria andAwardNameBetween(String value1, String value2) {
            addCriterion("award_name between", value1, value2, "awardName");
            return (Criteria) this;
        }

        public Criteria andAwardNameNotBetween(String value1, String value2) {
            addCriterion("award_name not between", value1, value2, "awardName");
            return (Criteria) this;
        }

        public Criteria andAwardNumIsNull() {
            addCriterion("award_num is null");
            return (Criteria) this;
        }

        public Criteria andAwardNumIsNotNull() {
            addCriterion("award_num is not null");
            return (Criteria) this;
        }

        public Criteria andAwardNumEqualTo(Integer value) {
            addCriterion("award_num =", value, "awardNum");
            return (Criteria) this;
        }

        public Criteria andAwardNumNotEqualTo(Integer value) {
            addCriterion("award_num <>", value, "awardNum");
            return (Criteria) this;
        }

        public Criteria andAwardNumGreaterThan(Integer value) {
            addCriterion("award_num >", value, "awardNum");
            return (Criteria) this;
        }

        public Criteria andAwardNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("award_num >=", value, "awardNum");
            return (Criteria) this;
        }

        public Criteria andAwardNumLessThan(Integer value) {
            addCriterion("award_num <", value, "awardNum");
            return (Criteria) this;
        }

        public Criteria andAwardNumLessThanOrEqualTo(Integer value) {
            addCriterion("award_num <=", value, "awardNum");
            return (Criteria) this;
        }

        public Criteria andAwardNumIn(List<Integer> values) {
            addCriterion("award_num in", values, "awardNum");
            return (Criteria) this;
        }

        public Criteria andAwardNumNotIn(List<Integer> values) {
            addCriterion("award_num not in", values, "awardNum");
            return (Criteria) this;
        }

        public Criteria andAwardNumBetween(Integer value1, Integer value2) {
            addCriterion("award_num between", value1, value2, "awardNum");
            return (Criteria) this;
        }

        public Criteria andAwardNumNotBetween(Integer value1, Integer value2) {
            addCriterion("award_num not between", value1, value2, "awardNum");
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