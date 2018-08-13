package cn.com.demo.user.test;

import cn.com.demo.user.service.CodeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: code4fun
 * @date: 2018/8/13:上午11:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CustTest {

    @Autowired
    private CodeService codeService;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");

    @Test
    public void createCode() {
        String code = codeService.createSerialCode("aa", "a", sdf.format(new Date()), 20);
        log.info("----->{}", code);
    }
}
