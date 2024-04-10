package com.muzimin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: 李煌民
 * @date: 2023-07-13 11:15
 **/
@Controller
public class HelloController {

    // "/" --> /WEB-INF/templates/index.html
    @RequestMapping(value = "/")
    public String indexName() {
        //返回视图名称
        return "index";
    }

    @RequestMapping(value = "/target")
    public String targetName() {
        return "target";
    }


}
