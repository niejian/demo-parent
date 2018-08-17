package cn.com.demo.product.controller;

import cn.com.demo.product.dao.entity.Product;
import cn.com.demo.product.service.ProductService;
import cn.com.demo.product.service.UserProductService;
import cn.com.demo.utils.CommonFunction;
import cn.com.demo.utils.CustDateUtils;
import cn.com.demo.utils.IdWorker;
import cn.com.demo.utils.ResponseBody;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Date;


/**
 * @author: code4fun
 * @date: 2018/8/17:下午2:33
 */
@RestController
@RequestMapping(value = "/product")
@Slf4j
public class ProductController {

    @Autowired
    private UserProductService userProductService;
    @Autowired
    private ProductService productService;

    private IdWorker idWorker = null;

    @PostConstruct
    public void getIdWorker(){
        idWorker = IdWorker.getIdWorkInstance(1, 1);
    }

    /**
     *
     * @param json 包含 createBy，
     * @return
     */
    @PostMapping(value = "/createOrUpdate")
    public ResponseBody createOrUpdateProduct(@RequestBody JSONObject json) {
        ResponseBody responseBody = new ResponseBody();
        CommonFunction.beforeProcess(log, json);
        Date date = new Date();
        try {

            Product product = null;
            String type = "create";
            if (!json.has("productId")) {
                String middle = CustDateUtils.getFormat("yyMMdd", date);
                long id = idWorker.nextId();
                JSONObject codeParam = new JSONObject();
                codeParam.put("tableName", "demo_product");
                codeParam.put("prefix", middle);
                codeParam.put("length", 15);
                String code = "";

                ResponseBody generateCodeResponse = this.userProductService.generateCode(codeParam);
                if (generateCodeResponse.isSuccess() && generateCodeResponse.getResponseCode() == 0) {
                    JSONObject codeJSON = (JSONObject) generateCodeResponse.getResponseBody();
                    code = codeJSON.getString("code");
                }

                json.put("productId", id);
                json.put("productCode", code);
                json.put("lastUpdateBy", "");
                json.put("lastUpdateDate", date);
                json.put("createDate", date);

            } else if (json.has("product") && !StringUtils.isEmpty("productId")) {
                json.put("lastUpdateDate", date);
                type = "update";
            }

            product = (Product)JSONObject.toBean(json, Product.class);
            this.productService.createOrUpdateProduct(product, type);

            responseBody.setSuccess(true);
            responseBody.setResponseMsg("请求成功");
            responseBody.setResponseCode(0);
        } catch (Exception e) {
            responseBody.setSuccess(false);
            e.printStackTrace();
            CommonFunction.genErrorMessage(log, e);
        }

        CommonFunction.afterProcess(log);

        return responseBody;
    }
}
