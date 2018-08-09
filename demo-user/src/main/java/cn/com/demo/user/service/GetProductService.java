package cn.com.demo.user.service;

import cn.com.demo.utils.ResponseBody;
import net.sf.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: code4fun
 * @date: 2018/8/9:下午4:06
 */
@FeignClient(value = "demo-product-service")
public interface GetProductService {

    @PostMapping("/getProductInfo")
    public ResponseBody getProductInfo(@RequestBody JSONObject jsonObject) throws Exception;
}
