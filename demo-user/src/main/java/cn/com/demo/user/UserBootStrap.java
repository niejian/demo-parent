package cn.com.demo.user;

import cn.com.demo.CommonBootstrap;
import cn.com.demo.auth.AuthBootstrap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
//开启服务发现
@EnableDiscoveryClient
@EnableFeignClients
@EnableCaching
@EnableAsync
@EnableCircuitBreaker
//@EnableAspectJAutoProxy
//@ComponentScan({"cn.com.demo"})
//将想要的bean加载到容器中
@ComponentScan(basePackageClasses = {
        UserBootStrap.class,
        AuthBootstrap.class,
        CommonBootstrap.class
})
public class UserBootStrap {

    public static void main(String[] args) {
        try {
            SpringApplication.run(UserBootStrap.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
