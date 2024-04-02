package com.muzimin.controller;

import com.muzimin.pojo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 李煌民
 * @date: 2024-04-02 16:04
 **/
@RestController
public class PersonController {

    @Autowired
    Person person;

    @RequestMapping("/person")
    public Person testPerson(){
        System.out.println(person.getUserName());
        return person;
    }
}
