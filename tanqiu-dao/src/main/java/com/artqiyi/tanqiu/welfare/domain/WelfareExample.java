package com.artqiyi.tanqiu.welfare.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Generated;

@Generated("welfare")
public class WelfareExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public WelfareExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andWelfareNameIsNull() {
            addCriterion("welfare_name is null");
            return (Criteria) this;
        }

        public Criteria andWelfareNameIsNotNull() {
            addCriterion("welfare_name is not null");
            return (Criteria) this;
        }

        public Criteria andWelfareNameEqualTo(String value) {
            addCriterion("welfare_name =", value, "welfareName");
            return (Criteria) this;
        }

        public Criteria andWelfareNameNotEqualTo(String value) {
            addCriterion("welfare_name <>", value, "welfareName");
            return (Criteria) this;
        }

        public Criteria andWelfareNameGreaterThan(String value) {
            addCriterion("welfare_name >", value, "welfareName");
            return (Criteria) this;
        }

        public Criteria andWelfareNameGreaterThanOrEqualTo(String value) {
            addCriterion("welfare_name >=", value, "welfareName");
            return (Criteria) this;
        }

        public Criteria andWelfareNameLessThan(String value) {
            addCriterion("welfare_name <", value, "welfareName");
            return (Criteria) this;
        }

        public Criteria andWelfareNameLessThanOrEqualTo(String value) {
            addCriterion("welfare_name <=", value, "welfareName");
            return (Criteria) this;
        }

        public Criteria andWelfareNameLike(String value) {
            addCriterion("welfare_name like", value, "welfareName");
            return (Criteria) this;
        }

        public Criteria andWelfareNameNotLike(String value) {
            addCriterion("welfare_name not like", value, "welfareName");
            return (Criteria) this;
        }

        public Criteria andWelfareNameIn(List<String> values) {
            addCriterion("welfare_name in", values, "welfareName");
            return (Criteria) this;
        }

        public Criteria andWelfareNameNotIn(List<String> values) {
            addCriterion("welfare_name not in", values, "welfareName");
            return (Criteria) this;
        }

        public Criteria andWelfareNameBetween(String value1, String value2) {
            addCriterion("welfare_name between", value1, value2, "welfareName");
            return (Criteria) this;
        }

        public Criteria andWelfareNameNotBetween(String value1, String value2) {
            addCriterion("welfare_name not between", value1, value2, "welfareName");
            return (Criteria) this;
        }

        public Criteria andWelfareDescIsNull() {
            addCriterion("welfare_desc is null");
            return (Criteria) this;
        }

        public Criteria andWelfareDescIsNotNull() {
            addCriterion("welfare_desc is not null");
            return (Criteria) this;
        }

        public Criteria andWelfareDescEqualTo(String value) {
            addCriterion("welfare_desc =", value, "welfareDesc");
            return (Criteria) this;
        }

        public Criteria andWelfareDescNotEqualTo(String value) {
            addCriterion("welfare_desc <>", value, "welfareDesc");
            return (Criteria) this;
        }

        public Criteria andWelfareDescGreaterThan(String value) {
            addCriterion("welfare_desc >", value, "welfareDesc");
            return (Criteria) this;
        }

        public Criteria andWelfareDescGreaterThanOrEqualTo(String value) {
            addCriterion("welfare_desc >=", value, "welfareDesc");
            return (Criteria) this;
        }

        public Criteria andWelfareDescLessThan(String value) {
            addCriterion("welfare_desc <", value, "welfareDesc");
            return (Criteria) this;
        }

        public Criteria andWelfareDescLessThanOrEqualTo(String value) {
            addCriterion("welfare_desc <=", value, "welfareDesc");
            return (Criteria) this;
        }

        public Criteria andWelfareDescLike(String value) {
            addCriterion("welfare_desc like", value, "welfareDesc");
            return (Criteria) this;
        }

        public Criteria andWelfareDescNotLike(String value) {
            addCriterion("welfare_desc not like", value, "welfareDesc");
            return (Criteria) this;
        }

        public Criteria andWelfareDescIn(List<String> values) {
            addCriterion("welfare_desc in", values, "welfareDesc");
            return (Criteria) this;
        }

        public Criteria andWelfareDescNotIn(List<String> values) {
            addCriterion("welfare_desc not in", values, "welfareDesc");
            return (Criteria) this;
        }

        public Criteria andWelfareDescBetween(String value1, String value2) {
            addCriterion("welfare_desc between", value1, value2, "welfareDesc");
            return (Criteria) this;
        }

        public Criteria andWelfareDescNotBetween(String value1, String value2) {
            addCriterion("welfare_desc not between", value1, value2, "welfareDesc");
            return (Criteria) this;
        }

        public Criteria andWelfareTypeIsNull() {
            addCriterion("welfare_type is null");
            return (Criteria) this;
        }

        public Criteria andWelfareTypeIsNotNull() {
            addCriterion("welfare_type is not null");
            return (Criteria) this;
        }

        public Criteria andWelfareTypeEqualTo(Integer value) {
            addCriterion("welfare_type =", value, "welfareType");
            return (Criteria) this;
        }

        public Criteria andWelfareTypeNotEqualTo(Integer value) {
            addCriterion("welfare_type <>", value, "welfareType");
            return (Criteria) this;
        }

        public Criteria andWelfareTypeGreaterThan(Integer value) {
            addCriterion("welfare_type >", value, "welfareType");
            return (Criteria) this;
        }

        public Criteria andWelfareTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("welfare_type >=", value, "welfareType");
            return (Criteria) this;
        }

        public Criteria andWelfareTypeLessThan(Integer value) {
            addCriterion("welfare_type <", value, "welfareType");
            return (Criteria) this;
        }

        public Criteria andWelfareTypeLessThanOrEqualTo(Integer value) {
            addCriterion("welfare_type <=", value, "welfareType");
            return (Criteria) this;
        }

        public Criteria andWelfareTypeIn(List<Integer> values) {
            addCriterion("welfare_type in", values, "welfareType");
            return (Criteria) this;
        }

        public Criteria andWelfareTypeNotIn(List<Integer> values) {
            addCriterion("welfare_type not in", values, "welfareType");
            return (Criteria) this;
        }

        public Criteria andWelfareTypeBetween(Integer value1, Integer value2) {
            addCriterion("welfare_type between", value1, value2, "welfareType");
            return (Criteria) this;
        }

        public Criteria andWelfareTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("welfare_type not between", value1, value2, "welfareType");
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

        public Criteria andAwardTypeEqualTo(Integer value) {
            addCriterion("award_type =", value, "awardType");
            return (Criteria) this;
        }

        public Criteria andAwardTypeNotEqualTo(Integer value) {
            addCriterion("award_type <>", value, "awardType");
            return (Criteria) this;
        }

        public Criteria andAwardTypeGreaterThan(Integer value) {
            addCriterion("award_type >", value, "awardType");
            return (Criteria) this;
        }

        public Criteria andAwardTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("award_type >=", value, "awardType");
            return (Criteria) this;
        }

        public Criteria andAwardTypeLessThan(Integer value) {
            addCriterion("award_type <", value, "awardType");
            return (Criteria) this;
        }

        public Criteria andAwardTypeLessThanOrEqualTo(Integer value) {
            addCriterion("award_type <=", value, "awardType");
            return (Criteria) this;
        }

        public Criteria andAwardTypeIn(List<Integer> values) {
            addCriterion("award_type in", values, "awardType");
            return (Criteria) this;
        }

        public Criteria andAwardTypeNotIn(List<Integer> values) {
            addCriterion("award_type not in", values, "awardType");
            return (Criteria) this;
        }

        public Criteria andAwardTypeBetween(Integer value1, Integer value2) {
            addCriterion("award_type between", value1, value2, "awardType");
            return (Criteria) this;
        }

        public Criteria andAwardTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("award_type not between", value1, value2, "awardType");
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