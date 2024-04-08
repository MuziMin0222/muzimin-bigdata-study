package com.muzimin.controller;

import com.muzimin.bean.Person;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 李煌民
 * @date: 2024-04-07 13:44
 **/
@RestController
public class TestPojoController {
    @PostMapping("/saveUser")
    public String testPojo(Person person) {
        return person.getUserName() + "======" + person.getPet().getName();
    }
}
