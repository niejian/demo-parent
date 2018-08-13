package cn.com.demo.user.web;

import cn.com.demo.user.service.CodeService;
import cn.com.demo.utils.CommonFunction;
import cn.com.demo.utils.ResponseBody;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: code4fun
 * @date: 2018/8/13:上午11:38
 */
@RestController
@RequestMapping(value = "/code")
@Slf4j
public class CodeController {

    @Autowired
    private CodeService codeService;

    @PostMapping(value = "/generateCode")
    public ResponseBody generateCode(@RequestBody JSONObject jsonObject) {
        Map<String, String> map = new HashMap<>(1);
        CommonFunction.beforeProcess(log, jsonObject.toString());
        ResponseBody responseBody = new ResponseBody();

//        boolean isSuccess = false;
//        int responseCode = -1;
//        String responseMsg = "";

        try {
            String tableName = jsonObject.has("tableName") ? jsonObject.getString("tableName") : null;
            String prefix = jsonObject.has("prefix") ? jsonObject.getString("prefix") : null;
            String middle = jsonObject.has("middle") ? jsonObject.getString("middle") : null;
            int length = jsonObject.has("length") ? jsonObject.getInt("length") : -1;

            if (StringUtils.isEmpty(tableName) || StringUtils.isEmpty(prefix)
                    || StringUtils.isEmpty(middle) || length < 0
                    ) {

                responseBody.setResponseCode(-1);
                responseBody.setResponseMsg("");
                responseBody.setSuccess(false);
                responseBody.setResponseBody(map);
                return responseBody;
            }

            String code = codeService.createSerialCode(tableName, prefix, middle, length);
            map.put("code", code);
            responseBody.setResponseCode(0);
            responseBody.setResponseMsg("success");
            responseBody.setSuccess(true);
            responseBody.setResponseBody(map);
            return responseBody;
        }catch (Exception e){
            e.printStackTrace();
            CommonFunction.genErrorMessage(log, e, jsonObject.toString());
        }


        CommonFunction.afterProcess(log, jsonObject.toString());
        return responseBody;
    }
}
