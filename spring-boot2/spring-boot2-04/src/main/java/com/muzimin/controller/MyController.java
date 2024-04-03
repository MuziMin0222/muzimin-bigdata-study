package com.muzimin.controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 李煌民
 * @date: 2024-04-03 13:09
 **/
@RestController
public class MyController {

    @GetMapping("/car/{id}/onwer/{name}")
    public Map<String, Object> testAnnotation(
            @PathVariable("id") Integer id,
            @PathVariable("name") String name,
            @PathVariable Map<String, String> mapPath,
            @RequestHeader("User-Agent") String userAgent,
            @RequestHeader Map<String, String> header,
            @RequestParam("aaa") String aaa,
            @RequestParam("bbbb") List<String> bbbb,
            @RequestParam Map<String, Object> allRequestParam,
            @CookieValue("_ga") Cookie cookie
            ) {
        Map<String, Object> map = new HashMap<>();

        map.put("id", id);
        map.put("name", name);
        map.put("mapPath", mapPath);

        map.put("userAgent", userAgent);
        map.put("header", header);

        map.put("aaa", aaa);
        map.put("bbbb", bbbb);
        map.put("allRequestParam", allRequestParam);

        map.put("cookie", cookie.getName() + "---" + cookie.getValue());

        return map;
    }

}
