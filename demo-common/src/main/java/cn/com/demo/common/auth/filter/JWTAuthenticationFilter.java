package cn.com.demo.common.auth.filter;

import cn.com.demo.common.auth.TokenAuthenticationService;
import cn.com.demo.utils.ResponseBody;
import net.sf.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token校验
 */
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }


    /**
     * 在此方法中检验客户端请求头中的token,
     * 如果存在并合法,就把token中的信息封装到 Authentication 类型的对象中,
     * 最后使用  SecurityContextHolder.getContext().setAuthentication(authentication); 改变或删除当前已经验证的 pricipal
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        //要拦截的请求过来的时候先调用此方法，来判断header中的token是否合法
        Authentication authentication = TokenAuthenticationService.getAuthentication(request, response);

        //判断是否有token
        if (authentication == null) {
            //response.getOutputStream()..print("无权限访问，请先登录");
            logger.info("无权限访问，请先登录");
            //chain.doFilter(request, response);
            response.setCharacterEncoding("UTF-8");
            ResponseBody responseBody = new ResponseBody();
            responseBody.setToken("");
            responseBody.setSuccess(false);
            responseBody.setResponseBody("无权限访问，请先登录");
            responseBody.setResponseMsg("无权限访问，请先登录");
            responseBody.setResponseCode(401);
            response.getWriter().print(JSONObject.fromObject(responseBody).toString());

        } else {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //放行
            chain.doFilter(request, response);
        }




    }
}
