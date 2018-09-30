package cn.com.demo.user.service.impl;

import cn.com.demo.user.dao.entity.User;
import cn.com.demo.user.service.UserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 重写springSecurity的userDetailService
 * 调用客户端查询用户信息方法
 */
@Service("userDetailService")
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EntityWrapper<cn.com.demo.user.dao.entity.User> ew = new EntityWrapper();
        ew.setEntity(new User());
        ew.eq("user_code", username);
        List<User> userList = this.userService.selectList(ew);
        if (CollectionUtils.isEmpty(userList)) {
            ew = new EntityWrapper();
            ew.setEntity(new User());
            ew.eq("user_email", username);
            userList =  this.userService.selectList(ew);
        }

        //查不到用户信息
        if (CollectionUtils.isEmpty(userList)) {
            return null;
            //throw new UsernameNotFoundException("该用户不存在");
        } else {
            User user = userList.get(0);
            return new org.springframework.security.core.userdetails.User(username, user.getUserPassword(), new ArrayList<>());

        }


    }
}
