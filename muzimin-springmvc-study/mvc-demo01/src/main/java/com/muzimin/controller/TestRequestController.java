package com.muzimin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author: 李煌民
 * @date: 2023-07-13 15:10
 **/
@Controller
@RequestMapping(value = "test")
public class TestRequestController {
    @RequestMapping(
            value = {"/success", "/win"},
            method = {RequestMethod.POST, RequestMethod.GET}
    )
    public String successName() {
        return "success";
    }

    @PostMapping(value = "/aaa")
    public String getTestName() {
        return "success";
    }

    @RequestMapping(value = "/testParams", params = {"params=aaa", "password=123456"})
    public String testParams() {
        return "success";
    }

    @RequestMapping(value = "/testHeader", headers = {"Host=localhost:8080"})
    public String testHeader() {
        return "success";
    }

    @RequestMapping(value = "/testA?t")
    public String testAntSingle() {
        return "success";
    }

    @RequestMapping(value = "/testA*t")
    public String testAntMore() {
        return "success";
    }

    @RequestMapping(value = "/**/testAnt")
    public String testAntMoreTier() {
        return "success";
    }

    @RequestMapping(value = "/testPlaceholder/{id}/{userName}")
    public String testPlaceholder(@PathVariable Integer id, @PathVariable String userName) {
        System.out.println("id:" + id + "  userName:" + userName);

        return "success";
    }
}
