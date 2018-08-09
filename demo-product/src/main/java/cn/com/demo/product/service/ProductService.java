package cn.com.demo.product.service;

import cn.com.demo.utils.ResponseBody;
import org.json.JSONObject;

/**
 * @author: code4fun
 * @date: 2018/8/9:下午4:00
 */
public interface ProductService {
    ResponseBody getProductInfo() throws Exception;
}
