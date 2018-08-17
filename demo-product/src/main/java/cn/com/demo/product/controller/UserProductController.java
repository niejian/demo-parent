package cn.com.demo.product.controller;

import cn.com.demo.product.dao.entity.Product;
import cn.com.demo.product.service.ProductService;
import cn.com.demo.product.service.ProviderProductService;
import cn.com.demo.product.service.UserProductService;
import cn.com.demo.utils.CommonFunction;
import cn.com.demo.utils.ResponseBody;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
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

    @PostMapping("/createProduct")
    public ResponseBody createProduct(@RequestBody JSONObject jsonObject) {
        ResponseBody responseBody = new ResponseBody();
        CommonFunction.beforeProcess(log, jsonObject);
        try {
            String productCode = null;
            //生成编码
            JSONObject generateCodeJSONParam = new JSONObject();
            ResponseBody codeResponse = this.userProductService.generateCode(generateCodeJSONParam);
            if (codeResponse.getResponseCode() == 0 && !StringUtils.isEmpty((String) codeResponse.getResponseBody())) {
                productCode = (String) codeResponse.getResponseBody();
            }

            if (!StringUtils.isEmpty(productCode)) {
                jsonObject.put("productCode", productCode);
            }
            Product product = (Product)JSONObject.toBean(jsonObject, Product.class);

        } catch (Exception e) {
            e.printStackTrace();
            CommonFunction.genErrorMessage(log, e);
        }

        CommonFunction.afterProcess(log, jsonObject);

        return  responseBody;
    }
}
