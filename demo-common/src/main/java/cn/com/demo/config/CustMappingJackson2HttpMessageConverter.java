package cn.com.demo.config;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: code4fun
 * @date: 2018/9/29:下午4:59
 */
public class CustMappingJackson2HttpMessageConverter  extends MappingJackson2HttpMessageConverter {
    public CustMappingJackson2HttpMessageConverter() {
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.TEXT_PLAIN);
        mediaTypes.add(MediaType.TEXT_HTML);
        mediaTypes.add(MediaType.APPLICATION_JSON);
    }
}