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

import com.artqiyi.tanqiu.task.domain.Task;
import com.artqiyi.tanqiu.task.domain.TaskExample;
import com.artqiyi.tanqiu.task.domain.TaskExample.Criteria;
import com.artqiyi.tanqiu.task.domain.TaskExample.Criterion;

public class TaskSqlProvider {

    public String countByExample(TaskExample example) {
        BEGIN();
        SELECT("count(*)");
        FROM("task");
        applyWhere(example, false);
        return SQL();
    }

    public String deleteByExample(TaskExample example) {
        BEGIN();
        DELETE_FROM("task");
        applyWhere(example, false);
        return SQL();
    }

    public String insertSelective(Task record) {
        BEGIN();
        INSERT_INTO("task");
        
        if (record.getTaskName() != null) {
            VALUES("task_name", "#{taskName,jdbcType=VARCHAR}");
        }
        
        if (record.getTaskDesc() != null) {
            VALUES("task_desc", "#{taskDesc,jdbcType=VARCHAR}");
        }
        
        if (record.getTaskRepetNum() != null) {
            VALUES("task_repet_num", "#{taskRepetNum,jdbcType=INTEGER}");
        }
        
        if (record.getTaskTypeAttr() != null) {
            VALUES("task_type_attr", "#{taskTypeAttr,jdbcType=SMALLINT}");
        }
        
        if (record.getTaskTypeCatalog() != null) {
            VALUES("task_type_catalog", "#{taskTypeCatalog,jdbcType=SMALLINT}");
        }
        
        if (record.getTaskType() != null) {
            VALUES("task_type", "#{taskType,jdbcType=VARCHAR}");
        }
        
        if (record.getTaskDisplay() != null) {
            VALUES("task_display", "#{taskDisplay,jdbcType=SMALLINT}");
        }
        
        if (record.getSuccessorTaskId() != null) {
            VALUES("successor_task_id", "#{successorTaskId,jdbcType=INTEGER}");
        }
        
        if (record.getUpdateTime() != null) {
            VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCreateTime() != null) {
            VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        return SQL();
    }

    public String selectByExample(TaskExample example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("task_id");
        } else {
            SELECT("task_id");
        }
        SELECT("task_name");
        SELECT("task_desc");
        SELECT("task_repet_num");
        SELECT("task_type_attr");
        SELECT("task_type_catalog");
        SELECT("task_type");
        SELECT("task_display");
        SELECT("successor_task_id");
        SELECT("update_time");
        SELECT("create_time");
        FROM("task");
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
        Task record = (Task) parameter.get("record");
        TaskExample example = (TaskExample) parameter.get("example");
        
        BEGIN();
        UPDATE("task");
        
        if (record.getTaskId() != null) {
            SET("task_id = #{record.taskId,jdbcType=INTEGER}");
        }
        
        if (record.getTaskName() != null) {
            SET("task_name = #{record.taskName,jdbcType=VARCHAR}");
        }
        
        if (record.getTaskDesc() != null) {
            SET("task_desc = #{record.taskDesc,jdbcType=VARCHAR}");
        }
        
        if (record.getTaskRepetNum() != null) {
            SET("task_repet_num = #{record.taskRepetNum,jdbcType=INTEGER}");
        }
        
        if (record.getTaskTypeAttr() != null) {
            SET("task_type_attr = #{record.taskTypeAttr,jdbcType=SMALLINT}");
        }
        
        if (record.getTaskTypeCatalog() != null) {
            SET("task_type_catalog = #{record.taskTypeCatalog,jdbcType=SMALLINT}");
        }
        
        if (record.getTaskType() != null) {
            SET("task_type = #{record.taskType,jdbcType=VARCHAR}");
        }
        
        if (record.getTaskDisplay() != null) {
            SET("task_display = #{record.taskDisplay,jdbcType=SMALLINT}");
        }
        
        if (record.getSuccessorTaskId() != null) {
            SET("successor_task_id = #{record.successorTaskId,jdbcType=INTEGER}");
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
        UPDATE("task");
        
        SET("task_id = #{record.taskId,jdbcType=INTEGER}");
        SET("task_name = #{record.taskName,jdbcType=VARCHAR}");
        SET("task_desc = #{record.taskDesc,jdbcType=VARCHAR}");
        SET("task_repet_num = #{record.taskRepetNum,jdbcType=INTEGER}");
        SET("task_type_attr = #{record.taskTypeAttr,jdbcType=SMALLINT}");
        SET("task_type_catalog = #{record.taskTypeCatalog,jdbcType=SMALLINT}");
        SET("task_type = #{record.taskType,jdbcType=VARCHAR}");
        SET("task_display = #{record.taskDisplay,jdbcType=SMALLINT}");
        SET("successor_task_id = #{record.successorTaskId,jdbcType=INTEGER}");
        SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        
        TaskExample example = (TaskExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    public String updateByPrimaryKeySelective(Task record) {
        BEGIN();
        UPDATE("task");
        
        if (record.getTaskName() != null) {
            SET("task_name = #{taskName,jdbcType=VARCHAR}");
        }
        
        if (record.getTaskDesc() != null) {
            SET("task_desc = #{taskDesc,jdbcType=VARCHAR}");
        }
        
        if (record.getTaskRepetNum() != null) {
            SET("task_repet_num = #{taskRepetNum,jdbcType=INTEGER}");
        }
        
        if (record.getTaskTypeAttr() != null) {
            SET("task_type_attr = #{taskTypeAttr,jdbcType=SMALLINT}");
        }
        
        if (record.getTaskTypeCatalog() != null) {
            SET("task_type_catalog = #{taskTypeCatalog,jdbcType=SMALLINT}");
        }
        
        if (record.getTaskType() != null) {
            SET("task_type = #{taskType,jdbcType=VARCHAR}");
        }
        
        if (record.getTaskDisplay() != null) {
            SET("task_display = #{taskDisplay,jdbcType=SMALLINT}");
        }
        
        if (record.getSuccessorTaskId() != null) {
            SET("successor_task_id = #{successorTaskId,jdbcType=INTEGER}");
        }
        
        if (record.getUpdateTime() != null) {
            SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCreateTime() != null) {
            SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        WHERE("task_id = #{taskId,jdbcType=INTEGER}");
        
        return SQL();
    }

    protected void applyWhere(TaskExample example, boolean includeExamplePhrase) {
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
