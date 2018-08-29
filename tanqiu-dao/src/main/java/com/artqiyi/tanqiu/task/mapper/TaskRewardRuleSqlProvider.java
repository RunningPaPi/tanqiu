package com.artqiyi.tanqiu.task.mapper;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.DELETE_FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.ORDER_BY;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT_DISTINCT;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import java.util.List;
import java.util.Map;

import com.artqiyi.tanqiu.task.domain.TaskRewardRule;
import com.artqiyi.tanqiu.task.domain.TaskRewardRuleExample;
import com.artqiyi.tanqiu.task.domain.TaskRewardRuleExample.Criteria;
import com.artqiyi.tanqiu.task.domain.TaskRewardRuleExample.Criterion;

public class TaskRewardRuleSqlProvider {

    public String countByExample(TaskRewardRuleExample example) {
        BEGIN();
        SELECT("count(*)");
        FROM("task_reward_rule");
        applyWhere(example, false);
        return SQL();
    }

    public String deleteByExample(TaskRewardRuleExample example) {
        BEGIN();
        DELETE_FROM("task_reward_rule");
        applyWhere(example, false);
        return SQL();
    }

    public String insertSelective(TaskRewardRule record) {
        BEGIN();
        INSERT_INTO("task_reward_rule");
        
        if (record.getRewardRuleName() != null) {
            VALUES("reward_rule_name", "#{rewardRuleName,jdbcType=VARCHAR}");
        }
        
        if (record.getAwardType() != null) {
            VALUES("award_type", "#{awardType,jdbcType=SMALLINT}");
        }
        
        if (record.getAwardName() != null) {
            VALUES("award_name", "#{awardName,jdbcType=VARCHAR}");
        }
        
        if (record.getAwardNum() != null) {
            VALUES("award_num", "#{awardNum,jdbcType=INTEGER}");
        }
        
        if (record.getUpdateTime() != null) {
            VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCreateTime() != null) {
            VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        return SQL();
    }

    public String selectByExample(TaskRewardRuleExample example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("reward_rule_id");
        } else {
            SELECT("reward_rule_id");
        }
        SELECT("reward_rule_name");
        SELECT("award_type");
        SELECT("award_name");
        SELECT("award_num");
        SELECT("update_time");
        SELECT("create_time");
        FROM("task_reward_rule");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        
        String sqlString = SQL();
        if (example != null && example.getLimit() != null) {
            sqlString += " limit " + example.getLimit();
        }
        if (example != null && example.getOffset() != null) {
            sqlString += " offset " + example.getOffset();
        }
        return sqlString;
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        TaskRewardRule record = (TaskRewardRule) parameter.get("record");
        TaskRewardRuleExample example = (TaskRewardRuleExample) parameter.get("example");
        
        BEGIN();
        UPDATE("task_reward_rule");
        
        if (record.getRewardRuleId() != null) {
            SET("reward_rule_id = #{record.rewardRuleId,jdbcType=INTEGER}");
        }
        
        if (record.getRewardRuleName() != null) {
            SET("reward_rule_name = #{record.rewardRuleName,jdbcType=VARCHAR}");
        }
        
        if (record.getAwardType() != null) {
            SET("award_type = #{record.awardType,jdbcType=SMALLINT}");
        }
        
        if (record.getAwardName() != null) {
            SET("award_name = #{record.awardName,jdbcType=VARCHAR}");
        }
        
        if (record.getAwardNum() != null) {
            SET("award_num = #{record.awardNum,jdbcType=INTEGER}");
        }
        
        if (record.getUpdateTime() != null) {
            SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCreateTime() != null) {
            SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        }
        
        applyWhere(example, true);
        return SQL();
    }

    public String updateByExample(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("task_reward_rule");
        
        SET("reward_rule_id = #{record.rewardRuleId,jdbcType=INTEGER}");
        SET("reward_rule_name = #{record.rewardRuleName,jdbcType=VARCHAR}");
        SET("award_type = #{record.awardType,jdbcType=SMALLINT}");
        SET("award_name = #{record.awardName,jdbcType=VARCHAR}");
        SET("award_num = #{record.awardNum,jdbcType=INTEGER}");
        SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        
        TaskRewardRuleExample example = (TaskRewardRuleExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    public String updateByPrimaryKeySelective(TaskRewardRule record) {
        BEGIN();
        UPDATE("task_reward_rule");
        
        if (record.getRewardRuleName() != null) {
            SET("reward_rule_name = #{rewardRuleName,jdbcType=VARCHAR}");
        }
        
        if (record.getAwardType() != null) {
            SET("award_type = #{awardType,jdbcType=SMALLINT}");
        }
        
        if (record.getAwardName() != null) {
            SET("award_name = #{awardName,jdbcType=VARCHAR}");
        }
        
        if (record.getAwardNum() != null) {
            SET("award_num = #{awardNum,jdbcType=INTEGER}");
        }
        
        if (record.getUpdateTime() != null) {
            SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCreateTime() != null) {
            SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        WHERE("reward_rule_id = #{rewardRuleId,jdbcType=INTEGER}");
        
        return SQL();
    }

    protected void applyWhere(TaskRewardRuleExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            WHERE(sb.toString());
        }
    }
}
