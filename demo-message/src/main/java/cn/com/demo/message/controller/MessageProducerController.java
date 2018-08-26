package cn.com.demo.message.controller;

import cn.com.demo.message.service.MessageProducerService;
import cn.com.demo.utils.CommonFunction;
import cn.com.demo.utils.ResponseBody;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: code4fun
 * @date: 2018/8/23:上午9:31
 */
@RestController
@RequestMapping(value = "/message/")
@Slf4j
public class MessageProducerController {

    @Autowired
    private MessageProducerService messageProducerService;

    @PostMapping(value = "/sendMessage")
    public ResponseBody sendMessage(@RequestBody JSONObject json, HttpServletRequest request){
        ResponseBody responseBody = new ResponseBody();
        CommonFunction.beforeProcess(log, json);
        try {
            //String userId = (String)request.getAttribute("userCode");

            String topic = json.getString("topic");
            //log.info("用户：{}", userId);
            String message = json.getString("message");
            messageProducerService.sendMessage(topic, message);

        } catch (Exception e) {
            e.printStackTrace();
            CommonFunction.genErrorMessage(log, e);
        }
        CommonFunction.afterProcess(log, json);
        return responseBody;
    }
}
