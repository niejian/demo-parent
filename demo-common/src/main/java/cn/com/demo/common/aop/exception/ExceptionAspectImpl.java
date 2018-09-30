package cn.com.demo.common.aop.exception;

import cn.com.demo.common.aop.token.FlushTokenImpl;
import cn.com.demo.utils.ResponseBody;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

/**
 * @author: code4fun
 * @date: 2018/9/30:上午10:11
 */
@Component
@Aspect
public class ExceptionAspectImpl {

    @Pointcut("@annotation(cn.com.demo.common.aop.exception.ExceptionAspect)")
    public void point(){

    }

    /**
     * 处理异常
     */
    @AfterThrowing(throwing = "ex", value = "point()")
    public void doAfterThrowing(Throwable ex) throws Exception{
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        if (ex instanceof UsernameNotFoundException) {
            ResponseBody responseBody = new ResponseBody();
            responseBody.setResponseCode(400);
            responseBody.setToken("");
            responseBody.setSuccess(false);
            responseBody.setResponseBody(400);
            responseBody.setResponseMsg(ex.getMessage());
            response.getWriter().print(JSONObject.fromObject(responseBody).toString());
        }
    }
}
