package cn.com.demo.auth.config;

import cn.com.demo.auth.filter.JWTLoginFilter;
import cn.com.demo.auth.service.CustomAuthenticationProvider;
import cn.com.demo.common.auth.filter.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author: code4fun
 * @date: 2018/9/29:下午4:18
 */
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
        // 注入自定义的provider，在权限验证的时候后实际走的是 CustomAuthenticationProvider.authenticate
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
                //拦截登录操作，
                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                //拦截每一个请求，验证token是否有效
                .addFilterBefore(new JWTAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
    }
}
