package cn.com.demo.utils;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

import java.io.Serializable;

//添加此注解，lomok自动生成get，set方法
@Data
public class ResponseBody<T> implements Serializable {

    private boolean success;
    private Integer responseCode;
    private String responseMsg;
    private T responseBody;
    private String token;
}
