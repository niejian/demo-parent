package cn.com.demo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author: code4fun
 * @date: 2018/9/29:下午5:01
 */
@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackageClasses = CommonBootstrap.class)
public class CommonBootstrap {
}
