package cn.com.demo.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class GatewayBootstrap {
    public static void main(String[] args) {
        try {
            SpringApplication.run(GatewayBootstrap.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
