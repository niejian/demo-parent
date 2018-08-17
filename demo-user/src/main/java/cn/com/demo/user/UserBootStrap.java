package cn.com.demo.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
//开启服务发现
@EnableDiscoveryClient
@EnableFeignClients
@EnableCaching
@EnableAsync
public class UserBootStrap {

    public static void main(String[] args) {
        try {
            SpringApplication.run(UserBootStrap.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
