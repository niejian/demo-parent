package cn.com.demo.common.aop.log;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日志切面实现
 * @author: code4fun
 * @date: 2018/9/2:下午9:17
 */
@Slf4j
@Aspect
@Component
public class LogAspectImpl {

    @Pointcut("@annotation(cn.com.demo.common.aop.log.LogAspect)")
    public void logAspect(){

    }

    /**
     * 日志前置通知
     * @param joinPoint
     */
    @Before(value = "logAspect()")
    public void logBefore(JoinPoint joinPoint) {
        //获取请求链接，并记录之
        //获取当前的请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String uri = request.getRequestURI();

        //获取请求参数，并记录之
        Object[] args = joinPoint.getArgs();
        if (null != args && args.length > 0) {
            //请求参数都是json格式
            for (Object obj : args ) {
                //只要参数不是request或者response类型。就输出到日志中
                if (!(obj instanceof HttpServletRequest)
                        && !(obj instanceof HttpServletResponse)) {
                    log.info("请求链接--->{}，参数：{}", uri, obj.toString());

//                    if (obj instanceof Integer) {
//                        log.info("请求链接--->{}，参数：{}", uri, obj);
//                    } else if (obj instanceof JSONObject) {
//                        log.info("请求链接--->{}，参数：{}", uri, );
//                    }

                }
            }
        }
    }

}
