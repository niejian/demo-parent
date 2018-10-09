package cn.com.demo.portal.test;

import cn.com.demo.portal.dao.role.entity.DemoRole;
import cn.com.demo.portal.service.DemoRoleService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author: code4fun
 * @date: 2018/9/28:上午11:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//@MapperScan(basePackages = "cn.com.demo.portal.dao.*.mapper")
public class PortalTest {
    @Autowired
    private DemoRoleService demoRoleService;

//    @Ignore
    @Test
    public void test01() {
        DemoRole demoRole = new DemoRole();
        demoRole.setEndDate(new Date());
        demoRole.setRoleCode("ROLE_001");
        demoRole.setRoleId("002");
        demoRole.setRoleName("test1");
        demoRole.setStartDate(new Date());
        demoRole.setValid(1);
        this.demoRoleService.insert(demoRole);
    }
}
