package com.muzimin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author: 李煌民
 * @date: 2023-07-31 10:56
 **/
@Controller
public class IndexController {

    /*@RequestMapping(value = "/")
    public String index() {
        return "index";
    }*/

    @RequestMapping(value = "/testView")
    public String testView() {
        return "testView";
    }
}
