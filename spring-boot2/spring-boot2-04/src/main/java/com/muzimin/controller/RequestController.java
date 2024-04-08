package com.muzimin.controller;

import com.muzimin.bean.Person;
import com.muzimin.bean.Pet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 李煌民
 * @date: 2024-04-03 15:06
 **/
@Controller
public class RequestController {
    @GetMapping("/goto")
    public String gotoSuccess(HttpServletRequest request) {
        request.setAttribute("msg", "this page is success");
        request.setAttribute("code", 200);

        return "forward:/success";
    }

    @ResponseBody
    @GetMapping("/success")
    Map<String, Object> success(HttpServletRequest request,
                                @RequestAttribute("msg") String msg,
                                @RequestAttribute("code") Integer code) {
        Object msg1 = request.getAttribute("msg");
        Object code1 = request.getAttribute("code");

        Map<String, Object> map = new HashMap<>();
        map.put("msg", msg);
        map.put("code", code);
        map.put("request_data", msg1 + "====" + code1);

        return map;
    }


    @ResponseBody
    @GetMapping("/save/person")
    Person testConverter() {
        Person person = new Person();
        person.setUserName("person");
        person.setPet(new Pet("aaa", "123"));
        return person;
    }
}
