package com.muzimin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: 李煌民
 * @date: 2023-08-02 11:24
 **/
@Controller
public class TestForward {

    @RequestMapping("/testThymeleaf")
    public String testThymeleaf() {
        return "success";
    }

    @RequestMapping("/testForward")
    public String testForward() {
        return "forward:/testThymeleaf";
    }

    @RequestMapping("/testRedirect")
    public String testRedirect() {
        return "redirect:/testThymeleaf";
    }
}
