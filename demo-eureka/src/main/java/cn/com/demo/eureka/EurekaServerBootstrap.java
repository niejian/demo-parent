package cn.com.demo.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author: code4fun
 * @date: 2018/9/28:上午10:23
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerBootstrap {
    public static void main(String[] args) {
        try {
            SpringApplication.run(EurekaServerBootstrap.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
