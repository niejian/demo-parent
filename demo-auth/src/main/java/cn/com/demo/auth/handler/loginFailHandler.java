package cn.com.demo.auth.handler;

import cn.com.demo.utils.ResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: code4fun
 * @date: 2018/9/30:上午10:29
 */
@Component
@Slf4j
public class loginFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    // AuthenticationException 认证过程中产生的异常
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        log.info("MyAuthenticationSuccessHandler login failure!");
        ResponseBody responseBody = new ResponseBody();
        responseBody.setResponseCode(400);
        responseBody.setToken("");
        responseBody.setSuccess(false);
        responseBody.setResponseBody(400);
        responseBody.setResponseMsg(exception.getMessage());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json;charset=UTF-8");
//          response.getWriter().write(objectMapper.writeValueAsString(exception));
        response.getWriter().write(responseBody.toString());

    }



}
