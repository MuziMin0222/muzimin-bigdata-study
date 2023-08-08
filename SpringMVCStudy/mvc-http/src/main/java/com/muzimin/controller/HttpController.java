package com.muzimin.controller;

import com.muzimin.bean.User;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: 李煌民
 * @date: 2023-08-04 16:19
 **/
@Controller
public class HttpController {

    @RequestMapping(value = "/testRequestBody")
    public String testRequestBody(@RequestBody String requestBody) {
        System.out.println("requestBody: " + requestBody);
        return "success";
    }

    @RequestMapping(value = "/testRequestEntity")
    public String testRequestEntity(RequestEntity<String> requestEntity) {
        System.out.println("请求头：" + requestEntity.getHeaders());
        System.out.println("请求体：" + requestEntity.getBody());
        return "success";
    }

    @RequestMapping(value = "/testServletApi")
    public void testServletResponseBody(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.getWriter().println("hello servlet response");
    }

    @RequestMapping(value = "/testResponseBody")
    @ResponseBody
    public String testResponseBody() {
        return "test response body";
    }

    @RequestMapping(value = "/testResponseUser")
    @ResponseBody
    public User testResponseUser() {
        return new User("muzimin", "123456@qq.com", 12, 1);
    }

    @RequestMapping(value = "/testAxios")
    @ResponseBody
    public String testAxios(String userName, String password) {
        System.out.println(userName + "=====>" + password);
        return "hello axios";
    }
}
