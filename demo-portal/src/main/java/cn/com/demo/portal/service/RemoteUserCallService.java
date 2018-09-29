package cn.com.demo.portal.service;

import cn.com.demo.utils.ResponseBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: code4fun
 * @date: 2018/9/29:下午3:50
 */
@FeignClient(serviceId = "demo-user-service")
public interface RemoteUserCallService {
    @GetMapping(value = "/user/getUser/{id}")
    ResponseBody getUser(@PathVariable("id") String id) throws UsernameNotFoundException;
}
