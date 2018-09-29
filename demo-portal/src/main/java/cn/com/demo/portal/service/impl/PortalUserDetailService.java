package cn.com.demo.portal.service.impl;

import cn.com.demo.portal.dao.role.entity.DemoUserRole;
import cn.com.demo.portal.service.DemoUserRoleService;
import cn.com.demo.portal.service.RemoteUserCallService;
import cn.com.demo.utils.ResponseBody;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: code4fun
 * @date: 2018/9/28:下午2:27
 */
public class PortalUserDetailService implements UserDetailsService {
    @Autowired
    private DemoUserRoleService userRoleService;
    @Autowired
    private RemoteUserCallService remoteUserCallService;
    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //TODO 1.查询该用户是否存在
        ResponseBody responseBody = this.remoteUserCallService.getUser(username);
        JSONObject user = new JSONObject();
        if (responseBody.isSuccess() && responseBody.getResponseCode() == 0) {
            user = JSONObject.fromObject(responseBody.getResponseBody());
        }

        //2.获取该用户的所有权限信息
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        try {

            List<DemoUserRole> userRoleList = this.userRoleService.getUserRoleList(username);
            if (!CollectionUtils.isEmpty(userRoleList)) {
                userRoleList.forEach(userRole -> {

                    grantedAuthorities.add(new SimpleGrantedAuthority(userRole.getRoleCode()));
                });
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }


        return new org.springframework.security.core.userdetails.User(user.getString("userName"),
                new BCryptPasswordEncoder().encode(user.getString("userPwd")), grantedAuthorities);
    }
}
