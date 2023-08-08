package com.muzimin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: 李煌民
 * @date: 2023-08-08 11:22
 **/
@Controller
public class InterceptorController {

    @RequestMapping(value = "/testInterceptor")
    public String testInterceptor() {
        return "success";
    }

    @RequestMapping(value = "/testException")
    public String testException() {
        System.out.println(1 / 0);
        return "success";
    }
}
