package cn.com.demo.portal.service.impl;

import cn.com.demo.portal.dao.role.entity.DemoRole;
import cn.com.demo.portal.dao.role.mapper.DemoRoleMapper;

import cn.com.demo.portal.service.DemoRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author code4fun
 * @since 2018-09-28
 */
@Service
public class DemoRoleServiceImpl extends ServiceImpl<DemoRoleMapper, DemoRole> implements DemoRoleService {

}
