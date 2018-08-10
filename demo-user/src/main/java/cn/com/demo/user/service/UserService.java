package cn.com.demo.user.service;

import cn.com.demo.user.dao.entity.User;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author code4fun
 * @since 2018-08-08
 */
public interface UserService extends IService<User> {
    User getUserById(String id);

    /**
     * 更新缓存需要返回最新的对象，缓存会将返回值缓存起来
     * @param user
     * @return
     */
    User updateUser(User user);

}
