/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: tanqiu-common
 * Author: author  wushyue@gmail.com
 * Create On: May 21, 2018 4:26:42 PM
 * Modify On: May 21, 2018 4:26:42 PM by wushyue@gmail.com
 */
package com.artqiyi.tanqiu.common.exception;

/** 
 * 类或接口作用描述 
 *
 * @author wushuang
 * @since 2018-05-21
 */
public class TaskException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TaskException(String msg) {
        super(msg);
    }

    public TaskException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
