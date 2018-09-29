//package cn.com.demo.user.service.impl;
//
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.util.DigestUtils;
//
//import java.util.ArrayList;
//
///**
// * 自定义权限生成器
// * JWTLoginFilter拦截后调用 CustomAuthenticationProvider.authenticate()方法。
// *      1.调用自定义的UserDetailsService来判断用户传进来的密码与数据库中是否一致。
// *      2.登录成功默认调用 JWTLoginFilter.successfulAuthentication()来生成对应的token
// *
// *
// */
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//
//    //构造方法传进来
//    private UserDetailsService userDetailsService;
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    public CustomAuthenticationProvider(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
//        this.userDetailsService = userDetailsService;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//    }
//
//    /**
//     * 是否可以提供输入类型的认证服务
//     * <p>
//     * 如果这个AuthenticationProvider支持指定的身份验证对象，那么返回true。
//     * 返回true并不能保证身份验证提供者能够对身份验证类的实例进行身份验证。
//     * 它只是表明它可以支持对它进行更深入的评估。身份验证提供者仍然可以从身份验证(身份验证)方法返回null，
//     * 以表明应该尝试另一个身份验证提供者。在运行时管理器的运行时，可以选择具有执行身份验证的身份验证提供者。
//     *
//     * @param authentication
//     * @return
//     */
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return authentication.equals(UsernamePasswordAuthenticationToken.class);
//    }
//
//    /**
//     * 验证登录信息,若登陆成功,设置 Authentication
//     *
//     * @param authentication
//     * @return 一个完全经过身份验证的对象，包括凭证。
//     * 如果AuthenticationProvider无法支持已通过的身份验证对象的身份验证，则可能返回null。
//     * 在这种情况下，将会尝试支持下一个身份验证类的验证提供者。
//     * @throws UsernameNotFoundException
//     */
//    @Override
//    public Authentication authenticate(Authentication authentication) throws UsernameNotFoundException {
//        // 获取认证的用户名 & 密码
//        String username = authentication.getName();
//        String password = authentication.getCredentials().toString();
//
//        //通过用户名从数据库中查询该用户
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//        //判断密码(这里是md5加密方式)是否正确
//        String dbPassword = userDetails.getPassword();
//        String encoderPassword = DigestUtils.md5DigestAsHex(password.getBytes());
//
//        if (!dbPassword.equals(encoderPassword)) {
//            throw new UsernameNotFoundException("密码错误");
//        }
//
//        // 还可以从数据库中查出该用户所拥有的权限,设置到 authorities 中去,这里模拟数据库查询.
//        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
////        authorities.add(new GrantedAuthorityImpl("ADMIN"));
//
//        Authentication auth = new UsernamePasswordAuthenticationToken(username, password, authorities);
//
//        return auth;
//
//    }
//
//}
