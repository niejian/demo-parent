package cn.com.demo.user.controller;


import cn.com.demo.common.aop.log.LogAspect;
import cn.com.demo.common.aop.token.FlushTokenAspect;
import cn.com.demo.user.dao.entity.User;
import cn.com.demo.user.service.GetProductService;
import cn.com.demo.user.service.UserService;
import cn.com.demo.utils.CommonFunction;
import cn.com.demo.utils.ResponseBody;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.slf4j.Slf4j;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
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

    private static final String DATE_PARTTEN = "yyyy-MM-dd HH:mm:ss";

    @LogAspect
    @FlushTokenAspect
    @GetMapping(value = "/getUser/{userId}")
    public ResponseBody getUser(@PathVariable("userId") String userId, HttpServletResponse response){
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


    @GetMapping(value = "/getCacheUser/{userId}")
    public ResponseBody getCacheUser(@PathVariable("userId") String userId){
        CommonFunction.beforeProcess(log, userId);
        ResponseBody responseBody = new ResponseBody();
        User user = this.userService.getUserById(userId);
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

    @PostMapping("/updateUser")
    public ResponseBody ResponseBody(@RequestBody net.sf.json.JSONObject jsonObject) throws Exception{
        String createDateStr = jsonObject.has("createDate") ? jsonObject.getString("createDate") : "";
        String updateDateStr = jsonObject.has("updateDate") ? jsonObject.getString("updateDate") : "";
        String[] patterns = {DATE_PARTTEN};
        Date createDate = new Date();
        Date updateDate = new Date();
        if (!StringUtils.isEmpty(createDateStr) && !"null".equals(createDateStr)) {
            createDate = DateUtils.parseDate(createDateStr, patterns);
        }
        jsonObject.put("createDate", createDate);

        if (!StringUtils.isEmpty(updateDateStr) && !"null".equals(updateDateStr)) {
            updateDate = DateUtils.parseDate(updateDateStr, patterns);
        }
        jsonObject.put("updateDate", updateDate);

        User user = (User)net.sf.json.JSONObject.toBean(jsonObject, User.class);
        this.userService.updateUser(user);
        ResponseBody responseBody = new ResponseBody();
        responseBody.setResponseBody(null);
        responseBody.setResponseCode(0);
        responseBody.setSuccess(true);
        return responseBody;
    }

    /**
     * 获取登录的用户信息；从request中获取到登录用户的userId
     * @param request
     * @return
     */
    @LogAspect
    @PostMapping(value = "/getLoginUserInfo")
    public ResponseBody getLoginUserInfo(@RequestBody JSONObject jsonObject, HttpServletRequest request) {
        CommonFunction.beforeProcess(log, jsonObject);
        ResponseBody responseBody = new ResponseBody();
        String userId = (String)request.getAttribute("userCode");
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
}

