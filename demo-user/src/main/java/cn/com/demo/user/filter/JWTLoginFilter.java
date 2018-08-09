package cn.com.demo.user.filter;

import cn.com.demo.common.auth.TokenAuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * token信息是存放在request的ThreadLocal里面的。当当前的request销毁，它会存放到session；所以在集群环境中，需要设置session同步；
 *
 *
 *
 * 拦截用户额login操作。继承 UsernamePasswordAuthenticationFilter
 * 默认会执行方法：attemptAuthentication。
 * authenticationManager，在SercurityConfig中自定义的方法。auth.authenticationProvider(new CustomAuthenticationProvider(userDetailsService, bCryptPasswordEncoder()));
 * 表示login这个请求的权限验证会走 CustomAuthenticationProvider.authenticate这个方法
 *      在此方法的逻辑是 setDetails(request, authenticationToken); 调用用户注入的UserDetailsService
 *          1.UserDetailsService的作用调用loadByusername的通过唯一标识获取到用户的权限及密码信息。
 *          2.再调用 authenticationManager.authenticate(authenticationToken)-->实际调用的是CustomAuthenticationProvider.authenticate 来判断参数中的密码与数据库中存储的密码是否相同
 */
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {


    private AuthenticationManager authenticationManager;

    /**
     *
     * @param url 拦截的登陆URL地址
     * @param authenticationManager
     */
    public JWTLoginFilter(String url, AuthenticationManager authenticationManager) {

        super();
        //new AntPathRequestMatcher("/login", "POST"))
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {


        //得到用户登陆信息,并封装到 Authentication 中,供自定义用户组件使用.
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        username = username.trim();

//        GrantedAuthorityImpl
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password, authorities);
// Allow subclasses to set the "details" property
        setDetails(request, authenticationToken);
        //实际调用CustomAuthenticationProvider.authenicate()方法
        return authenticationManager.authenticate(authenticationToken);
    }


    /**
     * 登陆成功后,此方法会被调用,因此我们可以在次方法中生成token,并返回给客户端
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) {

        TokenAuthenticationService.addAuthenticatiotoHttpHeader(response,authResult);

    }


}
