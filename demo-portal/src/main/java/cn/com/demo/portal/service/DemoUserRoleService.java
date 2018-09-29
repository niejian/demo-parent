package cn.com.demo.portal.service;

import cn.com.demo.portal.dao.role.entity.DemoUserRole;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 用户角色信息表 服务类
 * </p>
 *
 * @author code4fun
 * @since 2018-09-28
 */
public interface DemoUserRoleService extends IService<DemoUserRole> {

    /**
     * 通过用户Id获取所有权限信息
     * @param userId
     * @return
     * @throws Exception
     */
    List<DemoUserRole> getUserRoleList(String userId) throws Exception;

}
