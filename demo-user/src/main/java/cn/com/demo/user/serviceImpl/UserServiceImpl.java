package cn.com.demo.user.serviceImpl;

import cn.com.demo.user.dao.entity.User;
import cn.com.demo.user.dao.mapper.UserMapper;
import cn.com.demo.user.service.UserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
