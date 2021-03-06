## 1.说明
### 1.1 背景
慢慢构造一个微型的商城demo。使用的技术栈是SpringBoot+SpringCloud；
各个服务间的接口调用是有权限验证的。每个请求头包含token，通过token来
校验该请求是否合法
### 1.2 使用技术
* springBoot 基础框架
* SpringCloud
    * eureka 服务与服务的注册中心
    * Feign 负责服务间的调用
    * zuul 向外暴露的服务网关
* Spring Security 安全框架 

* ORM
    * mybatis plus 对mybatis的进一步封装
* redis 应用缓存、接口数据缓存
* zookeeper 注册中心
* rabbitMQ 消息队列
* JWT 结合Spring Security使用，实现服务之间的鉴权。Spring Security负责请求的过滤拦截以及赋权，
    JWT负责判断该token是否过期
### 1.3 项目结构
```
├── README.md
├── demo-cache 缓存模块
├── demo-common 公共模块，包括切面，token认证等一些公共方法
├── demo-eureka 注册中心
├── demo-gateway 网关
├── demo-message 消息模块（kafka）
├── demo-parent.iml
├── demo-product 产品模块
├── demo-user 用户模块
├── demo.sql 初始化sql
└── pom.xml
```

### 1.4 JWT认证
#### 1.4.1 JWT的认证流程
![token生成和校验的简易流程](https://oscimg.oschina.net/oscnet/7aad7eac02b2f055446b45968be2981df7e.jpg)
#### 1.4.2 代码实现
- SpringSecurity配置
```java

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserDetailsService userDetailService;

    public SecurityConfig() {
        super();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        // auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        // ***注入自定义的provider ***，在权限验证的时候后实际走的是 CustomAuthenticationProvider.authenticate
        auth.authenticationProvider(new CustomAuthenticationProvider(userDetailService, bCryptPasswordEncoder()));
        auth.userDetailsService(userDetailService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //禁用 csrf
        http.cors().and().csrf().disable().authorizeRequests()
                //允许以下请求
                .antMatchers("/login/**").permitAll()
                // 所有请求需要身份认证
                .anyRequest().authenticated()
                .and()
                // authenticationManager() 从IOC容器中获取。实际就是用户自定义注入的 CustomAuthenticationProvider
                //拦截登录操作，
                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                //拦截每一个请求，验证token是否有效
                .addFilterBefore(new JWTAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
    }
}
```
- 登录过滤拦截 

> 这里需要注意的是，获取登录名、密码默认是通过get方式获取的，而且键名也是一定的
如果是从json中获取还需要将request的请求参数转换为json数据

```java

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

```
- 自定义的权限解析器

主要功能看类注释

```java

/**
 * 自定义权限生成器
 * JWTLoginFilter拦截后调用 CustomAuthenticationProvider.authenticate()方法。
 *      1.调用自定义的UserDetailsService来判断用户传进来的密码与数据库中是否一致。
 *      2.登录成功默认调用 JWTLoginFilter.successfulAuthentication()来生成对应的token
 *
 *
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {

    //构造方法传进来
    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public CustomAuthenticationProvider(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * 是否可以提供输入类型的认证服务
     * <p>
     * 如果这个AuthenticationProvider支持指定的身份验证对象，那么返回true。
     * 返回true并不能保证身份验证提供者能够对身份验证类的实例进行身份验证。
     * 它只是表明它可以支持对它进行更深入的评估。身份验证提供者仍然可以从身份验证(身份验证)方法返回null，
     * 以表明应该尝试另一个身份验证提供者。在运行时管理器的运行时，可以选择具有执行身份验证的身份验证提供者。
     *
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    /**
     * 验证登录信息,若登陆成功,设置 Authentication
     *
     * @param authentication
     * @return 一个完全经过身份验证的对象，包括凭证。
     * 如果AuthenticationProvider无法支持已通过的身份验证对象的身份验证，则可能返回null。
     * 在这种情况下，将会尝试支持下一个身份验证类的验证提供者。
     * @throws UsernameNotFoundException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws UsernameNotFoundException {
        // 获取认证的用户名 & 密码
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        //通过用户名从数据库中查询该用户
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        //判断密码(这里是md5加密方式)是否正确
        String dbPassword = userDetails.getPassword();
        String encoderPassword = DigestUtils.md5DigestAsHex(password.getBytes());

        if (!dbPassword.equals(encoderPassword)) {
            throw new UsernameNotFoundException("密码错误");
        }

        // 还可以从数据库中查出该用户所拥有的权限,设置到 authorities 中去,这里模拟数据库查询.
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new GrantedAuthorityImpl("ADMIN"));

        Authentication auth = new UsernamePasswordAuthenticationToken(username, password, authorities);

        return auth;

    }

}

```
- Token生成与token的校验

```java

  /**
     * 将jwt token 写入header头部
     * 添加token刷新机制，当token还有30s过期的时候主动刷新token并存放到response的header中
     * 为了安全起见，这里的token应该使用非对称加密，返回到客户端，当客户端下次再请求的时候拿着解密后的token来请求
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

        //把token设置到响应头中去
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);

    }

        /**
         * 从请求头中解析出 Authentication
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
    

```

### 1.5 注意点
- 服务间调用如何将token设置到请求头中
> 服务间的调用时通过feign来做的，但是如何将token设置到feign的请求头里面。如下：
```java

/**
 * @author: code4fun
 * @date: 2018/8/9:上午11:54
 * 处理Feign调用其他系统的时候，往请求头里面加上 token这个参数
 */
@Configuration //RequestInterceptor
public class FeginInterceptor implements RequestInterceptor {

    public static String TOKEN_HEADER = "token";

    @Override
    public void apply(RequestTemplate template) {
        template.header(TOKEN_HEADER, getHeaders(getHttpServletRequest()).get(TOKEN_HEADER));
    }

    private javax.servlet.http.HttpServletRequest getHttpServletRequest() {
        try {
//            RequestContextHolder.getRequestAttributes().
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    private Map<String, String> getHeaders(javax.servlet.http.HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

}

```
- Feign调用超时设置
```yaml

#feign的调用超时设置
ribbon:
  ReadTimeout: 60000
  ConnectTimeout: 60000

```
- 服务的application.ame命名规则
  application.name的命名规则应当使用 - 来分割，如果使用 _ 的话，在feignclient端注入服务名的时候会爆unknow host id的异常

- Druid数据源配置

  ```properties
  spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
  spring.datasource.url=jdbc:mysql://*********************?allowMultiQueries=true&autoReconnect=true 
  spring.datasource.username=admin
  spring.datasource.password=admin
  spring.datasource.driverClassName=com.mysql.jdbc.Driver
  #dataSource Pool configuration
  spring.datasource.initialSize=5
  spring.datasource.minIdle=5
  spring.datasource.maxActive=20
  spring.datasource.maxWait=60000
  spring.datasource.timeBetweenEvictionRunsMillis=60000   
  spring.datasource.minEvictableIdleTimeMillis=300000
  spring.datasource.validationQuery=SELECT 1 FROM DUAL
  spring.datasource.testWhileIdle=true
  spring.datasource.testOnBorrow=false
  spring.datasource.exceptionSorter=true
  spring.datasource.testOnReturn=false
  spring.datasource.poolPreparedStatements=true
  spring.datasource.filters=stat,wall,log4j
  spring.datasource.maxPoolPreparedStatementPerConnectionSize=20
  spring.datasource.connectionProperties=druid.stat.mergeSql=true
  druid.stat.slowSqlMillis=500
  spring.datasource.useGlobalDataSourceStat=true
  ```

  
### 1.6 获取刷新后的token
> 这里使用的事aop（AfterReturning）的方式来拦截。具体定义如下
#### 1.6.1 生命切面注解
```java
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
```
##### 1.6.2 注解的具体实现
```java
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
```
> 因为项目分模块设计的原因，注解模块不在对应的业务层上面。所以应该要在对应的业务系统引入；
具体如下：
```java
@Import({
        cn.com.demo.common.aop.token.FlushTokenImpl.class
})
```


