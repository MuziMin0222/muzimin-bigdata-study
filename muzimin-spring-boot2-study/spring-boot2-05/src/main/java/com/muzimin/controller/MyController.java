package com.muzimin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: 李煌民
 * @date: 2024-04-11 18:56
 **/
@Controller
public class MyController {

    @GetMapping("/success")
    public String success(Model model) {

        model.addAttribute("msg","hello springboot2");
        model.addAttribute("link","www.baidu.com");
        return "success";
    }
}
