package com.artqiyi.tanqiu.welfare.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Generated;

@Generated("welfare_receive_log")
public class WelfareReceiveLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public WelfareReceiveLogExample() {
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

        public Criteria andWelfareIdIsNull() {
            addCriterion("welfare_id is null");
            return (Criteria) this;
        }

        public Criteria andWelfareIdIsNotNull() {
            addCriterion("welfare_id is not null");
            return (Criteria) this;
        }

        public Criteria andWelfareIdEqualTo(Long value) {
            addCriterion("welfare_id =", value, "welfareId");
            return (Criteria) this;
        }

        public Criteria andWelfareIdNotEqualTo(Long value) {
            addCriterion("welfare_id <>", value, "welfareId");
            return (Criteria) this;
        }

        public Criteria andWelfareIdGreaterThan(Long value) {
            addCriterion("welfare_id >", value, "welfareId");
            return (Criteria) this;
        }

        public Criteria andWelfareIdGreaterThanOrEqualTo(Long value) {
            addCriterion("welfare_id >=", value, "welfareId");
            return (Criteria) this;
        }

        public Criteria andWelfareIdLessThan(Long value) {
            addCriterion("welfare_id <", value, "welfareId");
            return (Criteria) this;
        }

        public Criteria andWelfareIdLessThanOrEqualTo(Long value) {
            addCriterion("welfare_id <=", value, "welfareId");
            return (Criteria) this;
        }

        public Criteria andWelfareIdIn(List<Long> values) {
            addCriterion("welfare_id in", values, "welfareId");
            return (Criteria) this;
        }

        public Criteria andWelfareIdNotIn(List<Long> values) {
            addCriterion("welfare_id not in", values, "welfareId");
            return (Criteria) this;
        }

        public Criteria andWelfareIdBetween(Long value1, Long value2) {
            addCriterion("welfare_id between", value1, value2, "welfareId");
            return (Criteria) this;
        }

        public Criteria andWelfareIdNotBetween(Long value1, Long value2) {
            addCriterion("welfare_id not between", value1, value2, "welfareId");
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

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andFriendUserIdIsNull() {
            addCriterion("friend_user_id is null");
            return (Criteria) this;
        }

        public Criteria andFriendUserIdIsNotNull() {
            addCriterion("friend_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andFriendUserIdEqualTo(Long value) {
            addCriterion("friend_user_id =", value, "friendUserId");
            return (Criteria) this;
        }

        public Criteria andFriendUserIdNotEqualTo(Long value) {
            addCriterion("friend_user_id <>", value, "friendUserId");
            return (Criteria) this;
        }

        public Criteria andFriendUserIdGreaterThan(Long value) {
            addCriterion("friend_user_id >", value, "friendUserId");
            return (Criteria) this;
        }

        public Criteria andFriendUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("friend_user_id >=", value, "friendUserId");
            return (Criteria) this;
        }

        public Criteria andFriendUserIdLessThan(Long value) {
            addCriterion("friend_user_id <", value, "friendUserId");
            return (Criteria) this;
        }

        public Criteria andFriendUserIdLessThanOrEqualTo(Long value) {
            addCriterion("friend_user_id <=", value, "friendUserId");
            return (Criteria) this;
        }

        public Criteria andFriendUserIdIn(List<Long> values) {
            addCriterion("friend_user_id in", values, "friendUserId");
            return (Criteria) this;
        }

        public Criteria andFriendUserIdNotIn(List<Long> values) {
            addCriterion("friend_user_id not in", values, "friendUserId");
            return (Criteria) this;
        }

        public Criteria andFriendUserIdBetween(Long value1, Long value2) {
            addCriterion("friend_user_id between", value1, value2, "friendUserId");
            return (Criteria) this;
        }

        public Criteria andFriendUserIdNotBetween(Long value1, Long value2) {
            addCriterion("friend_user_id not between", value1, value2, "friendUserId");
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