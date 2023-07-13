package com.muzimin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: 李煌民
 * @date: 2023-07-13 18:23
 **/
@Controller
public class TestParamsController {

    @RequestMapping(value = "/testServletApi")
    public String testServletApi(HttpServletRequest httpServletRequest) {
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
}
