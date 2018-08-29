/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: tanqiu-common
 * Author: author  wushyue@gmail.com
 * Create On: Apr 25, 2018 3:56:52 PM
 * Modify On: Apr 25, 2018 3:56:52 PM by wushyue@gmail.com
 */
package com.artqiyi.tanqiu.common.util;

/** 
 * 分页有关的计算工具
 */
public class PaginationUtil {


    public static Integer getStartNum(Integer pageNum, Integer pageSize, Integer defaultPageSize){
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? defaultPageSize : pageSize;

        Integer startNum = (pageNum - 1) * pageSize;

        return  startNum;
    }
	

}
