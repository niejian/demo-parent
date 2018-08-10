package cn.com.demo.product.serviceImpl;

import cn.com.demo.product.dao.entity.ProductItems;
import cn.com.demo.product.dao.mapper.ProductItemsMapper;
import cn.com.demo.product.service.ProductItemsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author code4fun
 * @since 2018-08-10
 */
@Service
public class ProductItemsServiceImpl extends ServiceImpl<ProductItemsMapper, ProductItems> implements ProductItemsService {

}
