package com.muzimin.controller;

import com.muzimin.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

/**
 * @author: 李煌民
 * @date: 2023-07-13 18:23
 **/
@Controller
public class TestParamsController {

    @RequestMapping(value = "/testServletApi")
    public String testServletApi(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();

        String userName = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");

        System.out.println(userName + "--->" + password);

        return "success";
    }

    @RequestMapping(value = "/params")
    public String indexName() {
        //返回视图名称
        return "test_params";
    }

    @RequestMapping(value = "/testParams")
    public String testControllerParams(String username, String password) {
        System.out.println(username + "--->" + password);

        return "success";
    }

    @RequestMapping(value = "/testParams2")
    public String testControllerParams2(
            @RequestParam(value = "username", required = false, defaultValue = "muzimin") String username,
            String password,
            String[] hobby,
            @RequestHeader(value = "Host", required = false, defaultValue = "muzimin.com") String host,
            @CookieValue(value = "JSESSIONID") String jsessionId
    ) {
        System.out.println(username + "--->" + password + "--->" + Arrays.toString(hobby));
        System.out.println("Host:" + host);
        System.out.println("JSESSIONID:" + jsessionId);

        return "success";
    }

    @RequestMapping("/testParams3")
    public String testParams3(User user) {
        System.out.println(user);
        return "success";
    }
}
