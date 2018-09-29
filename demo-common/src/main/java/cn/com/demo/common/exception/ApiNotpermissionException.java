package cn.com.demo.common.exception;

import javax.naming.AuthenticationException;

/**
 * 接口不允许访问异常
 * @author: code4fun
 * @date: 2018/9/29:下午3:37
 */
public class ApiNotpermissionException extends RuntimeException {

    public ApiNotpermissionException(String msg) {
        super(msg);
    }
    public ApiNotpermissionException(String msg, Throwable t) {
        super(msg, t);
    }
}
