package com.muzimin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 李煌民
 * @date: 2024-04-12 15:53
 **/
@RestController
public class IndexController {

    @GetMapping({"/", "/index"})
    String index() {
        return "index";
    }
}
