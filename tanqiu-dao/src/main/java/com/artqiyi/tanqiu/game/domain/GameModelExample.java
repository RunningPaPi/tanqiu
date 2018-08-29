package com.artqiyi.tanqiu.game.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameModelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public GameModelExample() {
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

        public Criteria andGameModelKeyIsNull() {
            addCriterion("game_model_key is null");
            return (Criteria) this;
        }

        public Criteria andGameModelKeyIsNotNull() {
            addCriterion("game_model_key is not null");
            return (Criteria) this;
        }

        public Criteria andGameModelKeyEqualTo(String value) {
            addCriterion("game_model_key =", value, "gameModelKey");
            return (Criteria) this;
        }

        public Criteria andGameModelKeyNotEqualTo(String value) {
            addCriterion("game_model_key <>", value, "gameModelKey");
            return (Criteria) this;
        }

        public Criteria andGameModelKeyGreaterThan(String value) {
            addCriterion("game_model_key >", value, "gameModelKey");
            return (Criteria) this;
        }

        public Criteria andGameModelKeyGreaterThanOrEqualTo(String value) {
            addCriterion("game_model_key >=", value, "gameModelKey");
            return (Criteria) this;
        }

        public Criteria andGameModelKeyLessThan(String value) {
            addCriterion("game_model_key <", value, "gameModelKey");
            return (Criteria) this;
        }

        public Criteria andGameModelKeyLessThanOrEqualTo(String value) {
            addCriterion("game_model_key <=", value, "gameModelKey");
            return (Criteria) this;
        }

        public Criteria andGameModelKeyLike(String value) {
            addCriterion("game_model_key like", value, "gameModelKey");
            return (Criteria) this;
        }

        public Criteria andGameModelKeyNotLike(String value) {
            addCriterion("game_model_key not like", value, "gameModelKey");
            return (Criteria) this;
        }

        public Criteria andGameModelKeyIn(List<String> values) {
            addCriterion("game_model_key in", values, "gameModelKey");
            return (Criteria) this;
        }

        public Criteria andGameModelKeyNotIn(List<String> values) {
            addCriterion("game_model_key not in", values, "gameModelKey");
            return (Criteria) this;
        }

        public Criteria andGameModelKeyBetween(String value1, String value2) {
            addCriterion("game_model_key between", value1, value2, "gameModelKey");
            return (Criteria) this;
        }

        public Criteria andGameModelKeyNotBetween(String value1, String value2) {
            addCriterion("game_model_key not between", value1, value2, "gameModelKey");
            return (Criteria) this;
        }

        public Criteria andGameModelNameIsNull() {
            addCriterion("game_model_name is null");
            return (Criteria) this;
        }

        public Criteria andGameModelNameIsNotNull() {
            addCriterion("game_model_name is not null");
            return (Criteria) this;
        }

        public Criteria andGameModelNameEqualTo(String value) {
            addCriterion("game_model_name =", value, "gameModelName");
            return (Criteria) this;
        }

        public Criteria andGameModelNameNotEqualTo(String value) {
            addCriterion("game_model_name <>", value, "gameModelName");
            return (Criteria) this;
        }

        public Criteria andGameModelNameGreaterThan(String value) {
            addCriterion("game_model_name >", value, "gameModelName");
            return (Criteria) this;
        }

        public Criteria andGameModelNameGreaterThanOrEqualTo(String value) {
            addCriterion("game_model_name >=", value, "gameModelName");
            return (Criteria) this;
        }

        public Criteria andGameModelNameLessThan(String value) {
            addCriterion("game_model_name <", value, "gameModelName");
            return (Criteria) this;
        }

        public Criteria andGameModelNameLessThanOrEqualTo(String value) {
            addCriterion("game_model_name <=", value, "gameModelName");
            return (Criteria) this;
        }

        public Criteria andGameModelNameLike(String value) {
            addCriterion("game_model_name like", value, "gameModelName");
            return (Criteria) this;
        }

        public Criteria andGameModelNameNotLike(String value) {
            addCriterion("game_model_name not like", value, "gameModelName");
            return (Criteria) this;
        }

        public Criteria andGameModelNameIn(List<String> values) {
            addCriterion("game_model_name in", values, "gameModelName");
            return (Criteria) this;
        }

        public Criteria andGameModelNameNotIn(List<String> values) {
            addCriterion("game_model_name not in", values, "gameModelName");
            return (Criteria) this;
        }

        public Criteria andGameModelNameBetween(String value1, String value2) {
            addCriterion("game_model_name between", value1, value2, "gameModelName");
            return (Criteria) this;
        }

        public Criteria andGameModelNameNotBetween(String value1, String value2) {
            addCriterion("game_model_name not between", value1, value2, "gameModelName");
            return (Criteria) this;
        }

        public Criteria andIconUrlIsNull() {
            addCriterion("icon_url is null");
            return (Criteria) this;
        }

        public Criteria andIconUrlIsNotNull() {
            addCriterion("icon_url is not null");
            return (Criteria) this;
        }

        public Criteria andIconUrlEqualTo(String value) {
            addCriterion("icon_url =", value, "iconUrl");
            return (Criteria) this;
        }

        public Criteria andIconUrlNotEqualTo(String value) {
            addCriterion("icon_url <>", value, "iconUrl");
            return (Criteria) this;
        }

        public Criteria andIconUrlGreaterThan(String value) {
            addCriterion("icon_url >", value, "iconUrl");
            return (Criteria) this;
        }

        public Criteria andIconUrlGreaterThanOrEqualTo(String value) {
            addCriterion("icon_url >=", value, "iconUrl");
            return (Criteria) this;
        }

        public Criteria andIconUrlLessThan(String value) {
            addCriterion("icon_url <", value, "iconUrl");
            return (Criteria) this;
        }

        public Criteria andIconUrlLessThanOrEqualTo(String value) {
            addCriterion("icon_url <=", value, "iconUrl");
            return (Criteria) this;
        }

        public Criteria andIconUrlLike(String value) {
            addCriterion("icon_url like", value, "iconUrl");
            return (Criteria) this;
        }

        public Criteria andIconUrlNotLike(String value) {
            addCriterion("icon_url not like", value, "iconUrl");
            return (Criteria) this;
        }

        public Criteria andIconUrlIn(List<String> values) {
            addCriterion("icon_url in", values, "iconUrl");
            return (Criteria) this;
        }

        public Criteria andIconUrlNotIn(List<String> values) {
            addCriterion("icon_url not in", values, "iconUrl");
            return (Criteria) this;
        }

        public Criteria andIconUrlBetween(String value1, String value2) {
            addCriterion("icon_url between", value1, value2, "iconUrl");
            return (Criteria) this;
        }

        public Criteria andIconUrlNotBetween(String value1, String value2) {
            addCriterion("icon_url not between", value1, value2, "iconUrl");
            return (Criteria) this;
        }

        public Criteria andBgImgIsNull() {
            addCriterion("bg_img is null");
            return (Criteria) this;
        }

        public Criteria andBgImgIsNotNull() {
            addCriterion("bg_img is not null");
            return (Criteria) this;
        }

        public Criteria andBgImgEqualTo(String value) {
            addCriterion("bg_img =", value, "bgImg");
            return (Criteria) this;
        }

        public Criteria andBgImgNotEqualTo(String value) {
            addCriterion("bg_img <>", value, "bgImg");
            return (Criteria) this;
        }

        public Criteria andBgImgGreaterThan(String value) {
            addCriterion("bg_img >", value, "bgImg");
            return (Criteria) this;
        }

        public Criteria andBgImgGreaterThanOrEqualTo(String value) {
            addCriterion("bg_img >=", value, "bgImg");
            return (Criteria) this;
        }

        public Criteria andBgImgLessThan(String value) {
            addCriterion("bg_img <", value, "bgImg");
            return (Criteria) this;
        }

        public Criteria andBgImgLessThanOrEqualTo(String value) {
            addCriterion("bg_img <=", value, "bgImg");
            return (Criteria) this;
        }

        public Criteria andBgImgLike(String value) {
            addCriterion("bg_img like", value, "bgImg");
            return (Criteria) this;
        }

        public Criteria andBgImgNotLike(String value) {
            addCriterion("bg_img not like", value, "bgImg");
            return (Criteria) this;
        }

        public Criteria andBgImgIn(List<String> values) {
            addCriterion("bg_img in", values, "bgImg");
            return (Criteria) this;
        }

        public Criteria andBgImgNotIn(List<String> values) {
            addCriterion("bg_img not in", values, "bgImg");
            return (Criteria) this;
        }

        public Criteria andBgImgBetween(String value1, String value2) {
            addCriterion("bg_img between", value1, value2, "bgImg");
            return (Criteria) this;
        }

        public Criteria andBgImgNotBetween(String value1, String value2) {
            addCriterion("bg_img not between", value1, value2, "bgImg");
            return (Criteria) this;
        }

        public Criteria andIconUrlXIsNull() {
            addCriterion("icon_url_x is null");
            return (Criteria) this;
        }

        public Criteria andIconUrlXIsNotNull() {
            addCriterion("icon_url_x is not null");
            return (Criteria) this;
        }

        public Criteria andIconUrlXEqualTo(String value) {
            addCriterion("icon_url_x =", value, "iconUrlX");
            return (Criteria) this;
        }

        public Criteria andIconUrlXNotEqualTo(String value) {
            addCriterion("icon_url_x <>", value, "iconUrlX");
            return (Criteria) this;
        }

        public Criteria andIconUrlXGreaterThan(String value) {
            addCriterion("icon_url_x >", value, "iconUrlX");
            return (Criteria) this;
        }

        public Criteria andIconUrlXGreaterThanOrEqualTo(String value) {
            addCriterion("icon_url_x >=", value, "iconUrlX");
            return (Criteria) this;
        }

        public Criteria andIconUrlXLessThan(String value) {
            addCriterion("icon_url_x <", value, "iconUrlX");
            return (Criteria) this;
        }

        public Criteria andIconUrlXLessThanOrEqualTo(String value) {
            addCriterion("icon_url_x <=", value, "iconUrlX");
            return (Criteria) this;
        }

        public Criteria andIconUrlXLike(String value) {
            addCriterion("icon_url_x like", value, "iconUrlX");
            return (Criteria) this;
        }

        public Criteria andIconUrlXNotLike(String value) {
            addCriterion("icon_url_x not like", value, "iconUrlX");
            return (Criteria) this;
        }

        public Criteria andIconUrlXIn(List<String> values) {
            addCriterion("icon_url_x in", values, "iconUrlX");
            return (Criteria) this;
        }

        public Criteria andIconUrlXNotIn(List<String> values) {
            addCriterion("icon_url_x not in", values, "iconUrlX");
            return (Criteria) this;
        }

        public Criteria andIconUrlXBetween(String value1, String value2) {
            addCriterion("icon_url_x between", value1, value2, "iconUrlX");
            return (Criteria) this;
        }

        public Criteria andIconUrlXNotBetween(String value1, String value2) {
            addCriterion("icon_url_x not between", value1, value2, "iconUrlX");
            return (Criteria) this;
        }

        public Criteria andBgImgXIsNull() {
            addCriterion("bg_img_x is null");
            return (Criteria) this;
        }

        public Criteria andBgImgXIsNotNull() {
            addCriterion("bg_img_x is not null");
            return (Criteria) this;
        }

        public Criteria andBgImgXEqualTo(String value) {
            addCriterion("bg_img_x =", value, "bgImgX");
            return (Criteria) this;
        }

        public Criteria andBgImgXNotEqualTo(String value) {
            addCriterion("bg_img_x <>", value, "bgImgX");
            return (Criteria) this;
        }

        public Criteria andBgImgXGreaterThan(String value) {
            addCriterion("bg_img_x >", value, "bgImgX");
            return (Criteria) this;
        }

        public Criteria andBgImgXGreaterThanOrEqualTo(String value) {
            addCriterion("bg_img_x >=", value, "bgImgX");
            return (Criteria) this;
        }

        public Criteria andBgImgXLessThan(String value) {
            addCriterion("bg_img_x <", value, "bgImgX");
            return (Criteria) this;
        }

        public Criteria andBgImgXLessThanOrEqualTo(String value) {
            addCriterion("bg_img_x <=", value, "bgImgX");
            return (Criteria) this;
        }

        public Criteria andBgImgXLike(String value) {
            addCriterion("bg_img_x like", value, "bgImgX");
            return (Criteria) this;
        }

        public Criteria andBgImgXNotLike(String value) {
            addCriterion("bg_img_x not like", value, "bgImgX");
            return (Criteria) this;
        }

        public Criteria andBgImgXIn(List<String> values) {
            addCriterion("bg_img_x in", values, "bgImgX");
            return (Criteria) this;
        }

        public Criteria andBgImgXNotIn(List<String> values) {
            addCriterion("bg_img_x not in", values, "bgImgX");
            return (Criteria) this;
        }

        public Criteria andBgImgXBetween(String value1, String value2) {
            addCriterion("bg_img_x between", value1, value2, "bgImgX");
            return (Criteria) this;
        }

        public Criteria andBgImgXNotBetween(String value1, String value2) {
            addCriterion("bg_img_x not between", value1, value2, "bgImgX");
            return (Criteria) this;
        }

        public Criteria andSortIsNull() {
            addCriterion("sort is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            addCriterion("sort is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(Short value) {
            addCriterion("sort =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(Short value) {
            addCriterion("sort <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(Short value) {
            addCriterion("sort >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(Short value) {
            addCriterion("sort >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(Short value) {
            addCriterion("sort <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(Short value) {
            addCriterion("sort <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(List<Short> values) {
            addCriterion("sort in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(List<Short> values) {
            addCriterion("sort not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(Short value1, Short value2) {
            addCriterion("sort between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(Short value1, Short value2) {
            addCriterion("sort not between", value1, value2, "sort");
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

        public Criteria andStatusEqualTo(Short value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Short value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Short value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Short value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Short value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Short> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Short> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Short value1, Short value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Short value1, Short value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andDeletedIsNull() {
            addCriterion("deleted is null");
            return (Criteria) this;
        }

        public Criteria andDeletedIsNotNull() {
            addCriterion("deleted is not null");
            return (Criteria) this;
        }

        public Criteria andDeletedEqualTo(Boolean value) {
            addCriterion("deleted =", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotEqualTo(Boolean value) {
            addCriterion("deleted <>", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThan(Boolean value) {
            addCriterion("deleted >", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("deleted >=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThan(Boolean value) {
            addCriterion("deleted <", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("deleted <=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedIn(List<Boolean> values) {
            addCriterion("deleted in", values, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotIn(List<Boolean> values) {
            addCriterion("deleted not in", values, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedBetween(Boolean value1, Boolean value2) {
            addCriterion("deleted between", value1, value2, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("deleted not between", value1, value2, "deleted");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andExtentstr1IsNull() {
            addCriterion("extentStr1 is null");
            return (Criteria) this;
        }

        public Criteria andExtentstr1IsNotNull() {
            addCriterion("extentStr1 is not null");
            return (Criteria) this;
        }

        public Criteria andExtentstr1EqualTo(String value) {
            addCriterion("extentStr1 =", value, "extentstr1");
            return (Criteria) this;
        }

        public Criteria andExtentstr1NotEqualTo(String value) {
            addCriterion("extentStr1 <>", value, "extentstr1");
            return (Criteria) this;
        }

        public Criteria andExtentstr1GreaterThan(String value) {
            addCriterion("extentStr1 >", value, "extentstr1");
            return (Criteria) this;
        }

        public Criteria andExtentstr1GreaterThanOrEqualTo(String value) {
            addCriterion("extentStr1 >=", value, "extentstr1");
            return (Criteria) this;
        }

        public Criteria andExtentstr1LessThan(String value) {
            addCriterion("extentStr1 <", value, "extentstr1");
            return (Criteria) this;
        }

        public Criteria andExtentstr1LessThanOrEqualTo(String value) {
            addCriterion("extentStr1 <=", value, "extentstr1");
            return (Criteria) this;
        }

        public Criteria andExtentstr1Like(String value) {
            addCriterion("extentStr1 like", value, "extentstr1");
            return (Criteria) this;
        }

        public Criteria andExtentstr1NotLike(String value) {
            addCriterion("extentStr1 not like", value, "extentstr1");
            return (Criteria) this;
        }

        public Criteria andExtentstr1In(List<String> values) {
            addCriterion("extentStr1 in", values, "extentstr1");
            return (Criteria) this;
        }

        public Criteria andExtentstr1NotIn(List<String> values) {
            addCriterion("extentStr1 not in", values, "extentstr1");
            return (Criteria) this;
        }

        public Criteria andExtentstr1Between(String value1, String value2) {
            addCriterion("extentStr1 between", value1, value2, "extentstr1");
            return (Criteria) this;
        }

        public Criteria andExtentstr1NotBetween(String value1, String value2) {
            addCriterion("extentStr1 not between", value1, value2, "extentstr1");
            return (Criteria) this;
        }

        public Criteria andExtentstr2IsNull() {
            addCriterion("extentStr2 is null");
            return (Criteria) this;
        }

        public Criteria andExtentstr2IsNotNull() {
            addCriterion("extentStr2 is not null");
            return (Criteria) this;
        }

        public Criteria andExtentstr2EqualTo(String value) {
            addCriterion("extentStr2 =", value, "extentstr2");
            return (Criteria) this;
        }

        public Criteria andExtentstr2NotEqualTo(String value) {
            addCriterion("extentStr2 <>", value, "extentstr2");
            return (Criteria) this;
        }

        public Criteria andExtentstr2GreaterThan(String value) {
            addCriterion("extentStr2 >", value, "extentstr2");
            return (Criteria) this;
        }

        public Criteria andExtentstr2GreaterThanOrEqualTo(String value) {
            addCriterion("extentStr2 >=", value, "extentstr2");
            return (Criteria) this;
        }

        public Criteria andExtentstr2LessThan(String value) {
            addCriterion("extentStr2 <", value, "extentstr2");
            return (Criteria) this;
        }

        public Criteria andExtentstr2LessThanOrEqualTo(String value) {
            addCriterion("extentStr2 <=", value, "extentstr2");
            return (Criteria) this;
        }

        public Criteria andExtentstr2Like(String value) {
            addCriterion("extentStr2 like", value, "extentstr2");
            return (Criteria) this;
        }

        public Criteria andExtentstr2NotLike(String value) {
            addCriterion("extentStr2 not like", value, "extentstr2");
            return (Criteria) this;
        }

        public Criteria andExtentstr2In(List<String> values) {
            addCriterion("extentStr2 in", values, "extentstr2");
            return (Criteria) this;
        }

        public Criteria andExtentstr2NotIn(List<String> values) {
            addCriterion("extentStr2 not in", values, "extentstr2");
            return (Criteria) this;
        }

        public Criteria andExtentstr2Between(String value1, String value2) {
            addCriterion("extentStr2 between", value1, value2, "extentstr2");
            return (Criteria) this;
        }

        public Criteria andExtentstr2NotBetween(String value1, String value2) {
            addCriterion("extentStr2 not between", value1, value2, "extentstr2");
            return (Criteria) this;
        }

        public Criteria andCreateIdIsNull() {
            addCriterion("create_id is null");
            return (Criteria) this;
        }

        public Criteria andCreateIdIsNotNull() {
            addCriterion("create_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreateIdEqualTo(Long value) {
            addCriterion("create_id =", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdNotEqualTo(Long value) {
            addCriterion("create_id <>", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdGreaterThan(Long value) {
            addCriterion("create_id >", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdGreaterThanOrEqualTo(Long value) {
            addCriterion("create_id >=", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdLessThan(Long value) {
            addCriterion("create_id <", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdLessThanOrEqualTo(Long value) {
            addCriterion("create_id <=", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdIn(List<Long> values) {
            addCriterion("create_id in", values, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdNotIn(List<Long> values) {
            addCriterion("create_id not in", values, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdBetween(Long value1, Long value2) {
            addCriterion("create_id between", value1, value2, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdNotBetween(Long value1, Long value2) {
            addCriterion("create_id not between", value1, value2, "createId");
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

        public Criteria andUpdatorIdIsNull() {
            addCriterion("updator_id is null");
            return (Criteria) this;
        }

        public Criteria andUpdatorIdIsNotNull() {
            addCriterion("updator_id is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatorIdEqualTo(Long value) {
            addCriterion("updator_id =", value, "updatorId");
            return (Criteria) this;
        }

        public Criteria andUpdatorIdNotEqualTo(Long value) {
            addCriterion("updator_id <>", value, "updatorId");
            return (Criteria) this;
        }

        public Criteria andUpdatorIdGreaterThan(Long value) {
            addCriterion("updator_id >", value, "updatorId");
            return (Criteria) this;
        }

        public Criteria andUpdatorIdGreaterThanOrEqualTo(Long value) {
            addCriterion("updator_id >=", value, "updatorId");
            return (Criteria) this;
        }

        public Criteria andUpdatorIdLessThan(Long value) {
            addCriterion("updator_id <", value, "updatorId");
            return (Criteria) this;
        }

        public Criteria andUpdatorIdLessThanOrEqualTo(Long value) {
            addCriterion("updator_id <=", value, "updatorId");
            return (Criteria) this;
        }

        public Criteria andUpdatorIdIn(List<Long> values) {
            addCriterion("updator_id in", values, "updatorId");
            return (Criteria) this;
        }

        public Criteria andUpdatorIdNotIn(List<Long> values) {
            addCriterion("updator_id not in", values, "updatorId");
            return (Criteria) this;
        }

        public Criteria andUpdatorIdBetween(Long value1, Long value2) {
            addCriterion("updator_id between", value1, value2, "updatorId");
            return (Criteria) this;
        }

        public Criteria andUpdatorIdNotBetween(Long value1, Long value2) {
            addCriterion("updator_id not between", value1, value2, "updatorId");
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