package cn.com.demo.product.controller;

import cn.com.demo.product.service.ProductService;
import cn.com.demo.product.service.ProviderProductService;
import cn.com.demo.product.service.UserProductService;
import cn.com.demo.utils.ResponseBody;
import io.jsonwebtoken.ExpiredJwtException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserProductController {
    @Autowired
    private UserProductService userProductService;
    @Autowired
    private ProviderProductService providerProductService;

    @GetMapping("/getUserProduct/{id}")
    public ResponseBody getUserProduct(@PathVariable("id") String id) {
        return this.userProductService.getUserProductInfo(id);
    }

    @PostMapping("/getAllUserProduct")
    public ResponseBody getAllUserProduct(@RequestBody JSONObject jsonObject) {
        return this.userProductService.getAllUser(jsonObject);
    }


    @PostMapping("/getProductInfo")
    public ResponseBody getProductInfo(@RequestBody JSONObject jsonObject) throws Exception{

        ResponseBody responseBody = new ResponseBody();
        try {
            responseBody = this.providerProductService.getProductInfo();
        } catch (Exception e) {
            // token异常抛出
            if (e instanceof ExpiredJwtException) {
                responseBody.setResponseBody(null);
                responseBody.setSuccess(false);
                responseBody.setResponseMsg("登录过期，请重新登录");
                responseBody.setResponseCode(11001);
            }
        }
        return responseBody;
    }
}
