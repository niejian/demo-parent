package cn.com.demo.common.aop.token;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动刷新token注解
 * 写在每个请求当方法上面，当token还有30秒过期的时候，刷新token并将token返回到返回参数列表中来
 * @author: code4fun
 * @date: 2018/9/1:下午5:17
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FlushTokenAspect {
    String value() default "";
}
