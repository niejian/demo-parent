package cn.com.demo.auth;

import cn.com.demo.CommonBootstrap;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author: code4fun
 * @date: 2018/9/29:下午4:15
 */
@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackageClasses = {
        AuthBootstrap.class,
        CommonBootstrap.class
})
public class AuthBootstrap {
}
