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
    //static final long EXPIRATIONTIME = 120000;
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
     * 距离token过期的时间
     */
    private static final Long TOKEN_FLUSH_SEC = 30000L;

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
            response.getOutputStream().print(responseJson.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * token快过期了，就刷新token
     * @param claims 用户的权限信息
     * @throws Exception
     */
    private static String flushToken(Claims claims) throws Exception {
        String auth = (String)claims.get(AUTHORITIES);

        // 得到 权限（角色）
        List<GrantedAuthority> authorities =  AuthorityUtils.
                commaSeparatedStringToAuthorityList((String) claims.get(AUTHORITIES));

        //得到用户名
        String username = claims.getSubject();

        //得到过期时间
        //Date expirationTime = claims.getExpiration().getTime();
        //重新生成token
        String token = Jwts.builder()
                            //生成token的时候可以把自定义数据加进去,比如用户权限
                            .claim(AUTHORITIES, "ROLE_ADMIN,AUTH_WRITE")
                            .setSubject(claims.getSubject())
            //                .setClaims(claims)
                            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                            .signWith(SignatureAlgorithm.HS512, SECRET)
                            .compact();

        return token;


    }

    /**
     * 从请求头中解析出 Authentication
     * 添加token刷新机制，当token还有30s过期的时候主动刷新token并存放到response的header中
     *      * 为了安全起见，这里的token应该使用非对称加密，返回到客户端，当客户端下次再请求的时候拿着解密后的token来请求
     * @param request
     * @return
     */
    public static Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {
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
        long expirationTime = expiration.getTime();

        //判断是否过期
//        Date now = new Date();

//        if (now.getTime() > expiration.getTime()) {
//
//            throw new UsernameNotFoundException("该账号已过期,请重新登陆");
//        }
        //自动刷新token机制，如果token的有效时间还剩下30s，自动刷新token并将token返回出去并写到request中
        long currentTimeMillis = System.currentTimeMillis();
        if (expirationTime - currentTimeMillis <= TOKEN_FLUSH_SEC) {
            try {
                token = flushToken(claims);
                //把token设置到响应头中去
                response.addHeader(HEADER_STRING, token);
            } catch (Exception e) {
                e.printStackTrace();
            }

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
