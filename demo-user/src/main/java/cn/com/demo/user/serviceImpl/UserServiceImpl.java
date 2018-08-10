package cn.com.demo.user.serviceImpl;

import cn.com.demo.user.dao.entity.User;
import cn.com.demo.user.dao.mapper.UserMapper;
import cn.com.demo.user.service.UserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author code4fun
 * @since 2018-08-08
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    //先查缓存再执行对应的方法
    @Cacheable(cacheNames = "demo_user", key = "#p0")
    @Override
    public User getUserById(String id) {
        EntityWrapper<User> ew = new EntityWrapper<>();
        ew.eq("user_id", id);
        log.info("----->查询用户信息：{}", id);

        return selectById(id);
    }

    @CachePut(cacheNames = "demo_user", key = "#p0.userId")
    @Override
    public User updateUser(User user) {
        this.updateById(user);
        return user;
    }
}
