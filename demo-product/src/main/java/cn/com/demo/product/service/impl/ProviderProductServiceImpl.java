package cn.com.demo.product.service.impl;

import cn.com.demo.product.service.ProductService;
import cn.com.demo.product.service.ProviderProductService;
import cn.com.demo.utils.ResponseBody;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: code4fun
 * @date: 2018/8/9:下午4:01
 */
@Service("providerProductService")
public class ProviderProductServiceImpl implements ProviderProductService {

    @Override
    public ResponseBody getProductInfo() throws Exception {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setResponseMsg("sds");
        responseBody.setSuccess(false);
        Map<String, String> map = new HashMap<>();
        map.put("key", System.currentTimeMillis() + "");
        responseBody.setResponseBody(net.sf.json.JSONObject.fromObject(map));
        return responseBody;
    }
}
