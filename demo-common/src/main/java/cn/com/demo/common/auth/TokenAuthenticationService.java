package cn.com.demo.common.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.sf.json.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * token 验证工具类
 */
public class TokenAuthenticationService {
    /**
     * 过期时间 2小时
     */
    static final long EXPIRATIONTIME = 7200000; //1000 * 60 * 60 * 2;
    /**
     * JWT 密码
     */
    static final String SECRET = "code4funsecret";
    /**
     * TOKEN前缀
     */
    static final String TOKEN_PREFIX = "Bearer ";
    /**
     * 存放Token的Header Key
     */
    static final String HEADER_STRING = "token";

    /**
     * 自定义的 playload
     */
    static final String AUTHORITIES = "authorities";

    /**
     * 将jwt token 写入header头部
     *
     * @param response
     * @param authentication
     */
    public static void addAuthenticatiotoHttpHeader(HttpServletResponse response, Authentication authentication) {

        //生成 jwt

        Claims claims = (Claims) Jwts.claims().put("aName", "aValue");

        String token = Jwts.builder()
                //生成token的时候可以把自定义数据加进去,比如用户权限
                .claim(AUTHORITIES, "ROLE_ADMIN,AUTH_WRITE")
                .setSubject(authentication.getName())
//                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        String tokenStr = TOKEN_PREFIX + token;
        //把token设置到响应头中去
        response.addHeader(HEADER_STRING, tokenStr);
        JSONObject responseJson = new JSONObject();
        responseJson.put(HEADER_STRING, tokenStr);
        try {
            //将token输出到返回体中
            response.getWriter().print(responseJson.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 从请求头中解析出 Authentication
     * @param request
     * @return
     */
    public static Authentication getAuthentication(HttpServletRequest request) {
        // 从Header中拿到token
        String token = request.getHeader(HEADER_STRING);
        if(token==null){
            return null;

        }

        //在JWT的playload中，包含了token的过期时间、权限等信息。如果token过期在parseClaimsJws方法会抛出 ExpiredJwtException

        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
        } catch (Exception e) {
            return null;
        }



        String auth = (String)claims.get(AUTHORITIES);

        // 得到 权限（角色）
        List<GrantedAuthority> authorities =  AuthorityUtils.
                commaSeparatedStringToAuthorityList((String) claims.get(AUTHORITIES));

        //得到用户名
        String username = claims.getSubject();

        //得到过期时间
        Date expiration = claims.getExpiration();

        //判断是否过期
        Date now = new Date();

        if (now.getTime() > expiration.getTime()) {

            throw new UsernameNotFoundException("该账号已过期,请重新登陆");
        }


        if (StringUtils.isEmpty(username)) {
            //return new UsernamePasswordAuthenticationToken(username, null, authorities);
            throw new UsernameNotFoundException("该账号已过期,请重新登陆");
        }

        //可以将用户的账号、权限等信息缓存到request中，当请求到了具体的controller的时候，需要这些参数的时候从request中再把它拿出来即可
        request.setAttribute("userCode", username);

        return new UsernamePasswordAuthenticationToken(username, null, authorities);


    }
}
