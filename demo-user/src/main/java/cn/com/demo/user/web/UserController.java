package cn.com.demo.user.web;


import cn.com.demo.user.dao.entity.User;
import cn.com.demo.user.service.GetProductService;
import cn.com.demo.user.service.UserService;
import cn.com.demo.utils.CommonFunction;
import cn.com.demo.utils.ResponseBody;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author code4fun
 * @since 2018-08-08
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private GetProductService getProductService;

    @GetMapping(value = "/getUser/{userId}")
    public ResponseBody getUser(@PathVariable("userId") String userId){
        CommonFunction.beforeProcess(log, userId);
        ResponseBody responseBody = new ResponseBody();
        User user = new User();
        EntityWrapper ew = new EntityWrapper();
        ew.setEntity(new User());

        ew.eq("user_id", userId);

        log.info("输出语句：{}", ew.getSqlSegment());
        user = this.userService.selectById(userId);
        responseBody.setResponseBody(user);
        responseBody.setResponseCode(0);
        responseBody.setSuccess(true);
        responseBody.setResponseMsg("success");

        return responseBody;
    }

    @PostMapping(value = "/getAllUsers")
    public ResponseBody getUser(@RequestBody JSONObject jsonObject){
        ResponseBody responseBody = new ResponseBody();
        EntityWrapper ew = new EntityWrapper();
        ew.setEntity(new User());
        List<User> list = this.userService.selectList(ew);
        responseBody.setResponseBody(list);
        responseBody.setResponseCode(0);
        responseBody.setSuccess(true);
        return responseBody;
    }

    @PostMapping("/getProductInfo")
    public ResponseBody getProductInfo(@RequestBody net.sf.json.JSONObject jsonObject) throws Exception {
        return this.getProductService.getProductInfo(jsonObject);
    }
}

