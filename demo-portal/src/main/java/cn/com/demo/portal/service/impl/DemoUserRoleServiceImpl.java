package cn.com.demo.portal.service.impl;

import cn.com.demo.portal.dao.role.entity.DemoUserRole;
import cn.com.demo.portal.dao.role.mapper.DemoUserRoleMapper;
import cn.com.demo.portal.service.DemoUserRoleService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户角色信息表 服务实现类
 * </p>
 *
 * @author code4fun
 * @since 2018-09-28
 */
@Service
@Slf4j
public class DemoUserRoleServiceImpl extends ServiceImpl<DemoUserRoleMapper, DemoUserRole> implements DemoUserRoleService {

    /**
     * 通过用户Id获取所有权限信息
     *
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public List<DemoUserRole> getUserRoleList(String userId) throws Exception {
        EntityWrapper ew = new EntityWrapper();
        ew.setEntity(new DemoUserRole());
        Date date = new Date();
        ew.eq("user_id", userId)
                .eq("valid", 1)
                .and()
                .where("start_date < {0}", date)
                .where("end_date > {0}", date);
        String sqlSegment = ew.getSqlSegment();
        log.info("查询用户角色输出语句：{}", sqlSegment);
        List<DemoUserRole> roleList = selectList(ew);
        if (CollectionUtils.isEmpty(roleList)) {
            roleList = new ArrayList<>();
        }
        return roleList;
    }
}
