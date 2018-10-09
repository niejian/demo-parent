package cn.com.demo.portal;

import cn.com.demo.CommonBootstrap;
import cn.com.demo.auth.AuthBootstrap;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: code4fun
 * @date: 2018/9/16:下午12:00
 */
@Slf4j
@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackageClasses = {
        PortalBootstrap.class,
        CommonBootstrap.class,
        AuthBootstrap.class
})
public class PortalBootstrap {

    public static void main(String[] args) {
        log.info("portal启动");
        try {
            SpringApplication.run(PortalBootstrap.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
