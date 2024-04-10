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
 * @date: 2023-08-02 11:21
 **/
@Controller
public class TestScope {
    @RequestMapping(value = "/testServletApi")
    public String testServletApi(HttpServletRequest request) {
        request.setAttribute("testScope", "hello:-->servlet");
        return "success";
    }

    @RequestMapping(value = "/testModelAndView")
    public ModelAndView testModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        //向请求域共享数据
        modelAndView.addObject("testScope", "hello:--> modelAndView");
        //设置试图，实现页面跳转
        modelAndView.setViewName("success");

        return modelAndView;
    }

    @RequestMapping(value = "/testModel")
    public String testModel(Model model) {
        model.addAttribute("testScope", "hello:--> model");
        return "success";
    }

    @RequestMapping(value = "/testMap")
    public String testMap(Map<String, Object> map) {
        map.put("testScope", "hello:--> map");
        return "success";
    }

    @RequestMapping(value = "/testModelMap")
    public String testModelMap(ModelMap modelMap) {
        modelMap.addAttribute("testScope", "hello:--> ModelMap");
        return "success";
    }

    @RequestMapping(value = "/testSession")
    public String testSession(HttpSession session) {
        session.setAttribute("testSession","hello:--> Session");
        return "success";
    }

    @RequestMapping(value = "/testApplication")
    public String testApplication(HttpSession session) {
        ServletContext context = session.getServletContext();
        context.setAttribute("testApplication","hello: --> application");

        return "success";
    }
}
