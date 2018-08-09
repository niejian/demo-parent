package cn.com.demo.product.controller;

import cn.com.demo.product.service.ProductService;
import cn.com.demo.product.service.UserProductService;
import cn.com.demo.utils.ResponseBody;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserProductController {
    @Autowired
    private UserProductService userProductService;
    @Autowired
    private ProductService productService;

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
        return this.productService.getProductInfo();
    }
}
