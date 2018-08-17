package cn.com.demo.user.test;

import cn.com.demo.user.service.CodeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: code4fun
 * @date: 2018/8/13:上午11:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@EnableAsync
public class CustTest {

    @Autowired
    private CodeService codeService;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

   // @Ignore
    @Test
    public void createCode() throws Exception{
        String code = codeService.createSerialCode("aa", "a", sdf.format(new Date()), 20);
        log.info("----->{}", code);
    }

    @Test
    public void asyncCreateCode() throws Exception {
        ListenableFuture<String> stringListenableFuture = codeService.asyncCreateSerialCode("aa", "a", sdf.format(new Date()), 20);
        log.info("异步请求结果---->{}", stringListenableFuture);
    }
}
