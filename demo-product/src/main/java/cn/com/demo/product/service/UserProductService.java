package cn.com.demo.product.service;

import cn.com.demo.product.config.FeginInterceptor;
import cn.com.demo.utils.ResponseBody;
import net.sf.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(value = "demo-user-service", configuration = FeginInterceptor.class)
@FeignClient(value = "demo-user-service")
public interface UserProductService {

    @GetMapping(value = "/user/getUser/{id}")
    ResponseBody getUserProductInfo(@PathVariable("id") String userId);

    @PostMapping(value = "/user/getAllUsers")
    ResponseBody getAllUser(@RequestBody JSONObject jsonObject);
}
