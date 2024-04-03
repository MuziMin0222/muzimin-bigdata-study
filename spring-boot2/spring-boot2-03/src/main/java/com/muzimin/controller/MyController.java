package com.muzimin.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @author: 李煌民
 * @date: 2024-04-03 09:51
 **/
@RestController
public class MyController {

    //@RequestMapping(value = "/user", method = RequestMethod.GET)
    @GetMapping("/user")
    String getUser() {
        return "Get User";
    }

    //@RequestMapping(value = "/user", method = RequestMethod.POST)
    @PostMapping("/user")
    String postUser() {
        return "Post User";
    }

    //@RequestMapping(value = "/user", method = RequestMethod.PUT)
    @PutMapping("/user")
    String putUser() {
        return "Put User";
    }

    //@RequestMapping(value = "/user", method = RequestMethod.DELETE)
    @DeleteMapping("/user")
    String deleteUser() {
        return "Delete User";
    }
}
