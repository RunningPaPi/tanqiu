/**
 * COPYRIGHT. Qiyiguo Inc. ALL RIGHTS RESERVED.
 * Project: tanqiu-common
 * Author: author  wushyue@gmail.com
 * Create On: May 12, 2018 6:22:36 PM
 * Modify On: May 12, 2018 6:22:36 PM by wushyue@gmail.com
 */
package com.artqiyi.tanqiu.common.exception;

/** 
 * 支付类异常
 *
 * @author wushuang
 * @since 2018-05-12
 */
public class PaymentException extends RuntimeException {
	
    public PaymentException(String msg) {
        super(msg);
    }

    public PaymentException(String msg, Throwable cause) {
        super(msg, cause);
    }


}
