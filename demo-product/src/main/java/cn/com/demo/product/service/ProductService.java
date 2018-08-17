package cn.com.demo.product.service;

import cn.com.demo.product.dao.entity.Product;
import com.baomidou.mybatisplus.service.IService;
import net.sf.json.JSONObject;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author code4fun
 * @since 2018-08-10
 */
public interface ProductService extends IService<Product> {

    /**
     * 创建或更新
     * @param product
     * @type 'create/update'
     * @return
     */
    Product createOrUpdateProduct(Product product, String update);

}
