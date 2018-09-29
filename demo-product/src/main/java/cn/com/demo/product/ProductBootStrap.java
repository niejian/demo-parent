package cn.com.demo.product;

import cn.com.demo.CommonBootstrap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackageClasses = {
        ProductBootStrap.class,
        CommonBootstrap.class
})
public class ProductBootStrap {

    public static void main(String[] args) {
        try {
            SpringApplication.run(ProductBootStrap.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
