package cn.com.demo.common.aop.token;

import cn.com.demo.utils.ResponseBody;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

/**
 * token刷新注解实现
 * @author: code4fun
 * @date: 2018/9/1:下午5:21
 */
@Component
@Aspect
public class FlushTokenImpl {
    private Logger logger = LoggerFactory.getLogger(FlushTokenImpl.class);

    @Pointcut("@annotation(cn.com.demo.common.aop.token.FlushTokenAspect)")
    public void point(){

    }

    @Before("point()")
    public void doBefore(JoinPoint joinPoint) {
        logger.info("---------->shu前置通知");
    }


    /**
     * 拦截请求返回
     * 如果response的含有token（刷新后的token），将token拼接到返回体中
     * 这里返回的token最好使用非对称加密的方式。客户端拿到加密后的token解密完再来请求
     * @param obj
     */
    @AfterReturning(returning = "obj", pointcut = "point()")
    public void doAfterReturning(Object obj) {
        //获取当前的请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        //如果response的header中已经包含可token，说明此次请求token已经刷新，需要将token返回到客户端
        String token = response.getHeader("token");
        if (!StringUtils.isEmpty(token)) {

            ((ResponseBody) obj).setToken(token);
        }

    }
}
