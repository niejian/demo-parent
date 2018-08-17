package cn.com.demo.product.serviceImpl;

import cn.com.demo.product.dao.entity.Product;
import cn.com.demo.product.dao.mapper.ProductMapper;
import cn.com.demo.product.service.ProductService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author code4fun
 * @since 2018-08-10
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    /**
     * 创建或更新
     *
     * @param product
     * @type 'create/update'
     * @return
     */
    @Transactional
    @Override
    public Product createOrUpdateProduct(Product product, String type) {
        switch (type) {
            case "create":
                insert(product);
                break;
            case "update":
                updateById(product);
                break;
        }
        return product;
    }
}
