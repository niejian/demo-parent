package cn.com.demo.message;

import cn.com.demo.utils.CommonFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * @author: code4fun
 * @date: 2018/8/22:上午11:55
 */
@SpringBootApplication
@Slf4j
@EnableKafka
public class MessageBootstrap {




    public static void main(String[] args) {
        try{
            log.info("消息组件开始运行");
            SpringApplication.run(MessageBootstrap.class, args);
        }catch (Exception e){
            e.printStackTrace();
            CommonFunction.genErrorMessage(log, e);
        }

    }
}
