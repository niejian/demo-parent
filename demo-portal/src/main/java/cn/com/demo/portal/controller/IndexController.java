package cn.com.demo.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: code4fun
 * @date: 2018/9/16:下午12:07
 */
@Controller
public class IndexController {
    @GetMapping(value = "/")
    public String index() {
        return "login";
    }
}
