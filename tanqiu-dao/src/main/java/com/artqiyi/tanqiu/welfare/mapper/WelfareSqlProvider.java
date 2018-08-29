package com.artqiyi.tanqiu.welfare.mapper;

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

import com.artqiyi.tanqiu.welfare.domain.Welfare;
import com.artqiyi.tanqiu.welfare.domain.WelfareExample.Criteria;
import com.artqiyi.tanqiu.welfare.domain.WelfareExample.Criterion;
import com.artqiyi.tanqiu.welfare.domain.WelfareExample;
import java.util.List;
import java.util.Map;

public class WelfareSqlProvider {

    public String countByExample(WelfareExample example) {
        BEGIN();
        SELECT("count(*)");
        FROM("welfare");
        applyWhere(example, false);
        return SQL();
    }

    public String deleteByExample(WelfareExample example) {
        BEGIN();
        DELETE_FROM("welfare");
        applyWhere(example, false);
        return SQL();
    }

    public String insertSelective(Welfare record) {
        BEGIN();
        INSERT_INTO("welfare");
        
        if (record.getWelfareName() != null) {
            VALUES("welfare_name", "#{welfareName,jdbcType=VARCHAR}");
        }
        
        if (record.getWelfareDesc() != null) {
            VALUES("welfare_desc", "#{welfareDesc,jdbcType=VARCHAR}");
        }
        
        if (record.getWelfareType() != null) {
            VALUES("welfare_type", "#{welfareType,jdbcType=INTEGER}");
        }
        
        if (record.getAwardType() != null) {
            VALUES("award_type", "#{awardType,jdbcType=INTEGER}");
        }
        
        if (record.getAwardNum() != null) {
            VALUES("award_num", "#{awardNum,jdbcType=INTEGER}");
        }
        
        if (record.getCreateTime() != null) {
            VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        return SQL();
    }

    public String selectByExample(WelfareExample example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("id");
        } else {
            SELECT("id");
        }
        SELECT("welfare_name");
        SELECT("welfare_desc");
        SELECT("welfare_type");
        SELECT("award_type");
        SELECT("award_num");
        SELECT("create_time");
        SELECT("update_time");
        FROM("welfare");
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
        Welfare record = (Welfare) parameter.get("record");
        WelfareExample example = (WelfareExample) parameter.get("example");
        
        BEGIN();
        UPDATE("welfare");
        
        if (record.getId() != null) {
            SET("id = #{record.id,jdbcType=BIGINT}");
        }
        
        if (record.getWelfareName() != null) {
            SET("welfare_name = #{record.welfareName,jdbcType=VARCHAR}");
        }
        
        if (record.getWelfareDesc() != null) {
            SET("welfare_desc = #{record.welfareDesc,jdbcType=VARCHAR}");
        }
        
        if (record.getWelfareType() != null) {
            SET("welfare_type = #{record.welfareType,jdbcType=INTEGER}");
        }
        
        if (record.getAwardType() != null) {
            SET("award_type = #{record.awardType,jdbcType=INTEGER}");
        }
        
        if (record.getAwardNum() != null) {
            SET("award_num = #{record.awardNum,jdbcType=INTEGER}");
        }
        
        if (record.getCreateTime() != null) {
            SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        }
        
        applyWhere(example, true);
        return SQL();
    }

    public String updateByExample(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("welfare");
        
        SET("id = #{record.id,jdbcType=BIGINT}");
        SET("welfare_name = #{record.welfareName,jdbcType=VARCHAR}");
        SET("welfare_desc = #{record.welfareDesc,jdbcType=VARCHAR}");
        SET("welfare_type = #{record.welfareType,jdbcType=INTEGER}");
        SET("award_type = #{record.awardType,jdbcType=INTEGER}");
        SET("award_num = #{record.awardNum,jdbcType=INTEGER}");
        SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        
        WelfareExample example = (WelfareExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    public String updateByPrimaryKeySelective(Welfare record) {
        BEGIN();
        UPDATE("welfare");
        
        if (record.getWelfareName() != null) {
            SET("welfare_name = #{welfareName,jdbcType=VARCHAR}");
        }
        
        if (record.getWelfareDesc() != null) {
            SET("welfare_desc = #{welfareDesc,jdbcType=VARCHAR}");
        }
        
        if (record.getWelfareType() != null) {
            SET("welfare_type = #{welfareType,jdbcType=INTEGER}");
        }
        
        if (record.getAwardType() != null) {
            SET("award_type = #{awardType,jdbcType=INTEGER}");
        }
        
        if (record.getAwardNum() != null) {
            SET("award_num = #{awardNum,jdbcType=INTEGER}");
        }
        
        if (record.getCreateTime() != null) {
            SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            SET("update_time = #{updateTime,jdbcType=TIMESTAMP}");
        }
        
        WHERE("id = #{id,jdbcType=BIGINT}");
        
        return SQL();
    }

    protected void applyWhere(WelfareExample example, boolean includeExamplePhrase) {
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
