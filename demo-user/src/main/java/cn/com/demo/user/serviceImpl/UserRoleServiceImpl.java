package cn.com.demo.user.serviceImpl;

import cn.com.demo.user.dao.entity.UserRole;
import cn.com.demo.user.dao.mapper.UserRoleMapper;
import cn.com.demo.user.service.UserRoleService;
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
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
