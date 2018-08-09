package cn.com.demo.user.config;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: code4fun
 * @date: 2018/8/9:上午11:54
 * 处理Feign调用其他系统的时候，往请求头里面加上 token这个参数
 */
@Configuration //RequestInterceptor
public class FeginInterceptor implements RequestInterceptor {

    public static String TOKEN_HEADER = "token";

    @Override
    public void apply(RequestTemplate template) {
        template.header(TOKEN_HEADER, getHeaders(getHttpServletRequest()).get(TOKEN_HEADER));
    }

    private javax.servlet.http.HttpServletRequest getHttpServletRequest() {
        try {
//            RequestContextHolder.getRequestAttributes().
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    private Map<String, String> getHeaders(javax.servlet.http.HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

}
