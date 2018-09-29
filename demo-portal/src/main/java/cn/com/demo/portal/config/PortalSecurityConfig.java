package cn.com.demo.portal.config;

import cn.com.demo.portal.service.DemoUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @author: code4fun
 * @date: 2018/9/28:下午2:26
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class PortalSecurityConfig {





}
