package com.muzimin.controller;

import com.muzimin.pojo.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 李煌民
 * @date: 2024-03-27 11:50
 **/
@RestController
public class HelloWorldController {
    @RequestMapping("/hello")
    String helloWorld() {
        return "hello spring boot2";
    }

    @Autowired
    Car car;

    @RequestMapping("/car")
    Car helloCar() {
        return car;
    }

}
