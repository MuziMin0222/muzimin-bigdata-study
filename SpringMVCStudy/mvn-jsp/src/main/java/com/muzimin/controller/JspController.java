package com.muzimin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: 李煌民
 * @date: 2023-08-02 14:48
 **/
@Controller
public class JspController {
    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/success")
    public String success() {
        return "success";
    }

}
