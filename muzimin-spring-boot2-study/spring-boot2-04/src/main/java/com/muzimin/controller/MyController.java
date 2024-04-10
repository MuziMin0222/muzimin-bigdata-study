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

    //get http://localhost:8080/car/1/onwer/lihuangmin?aaa=11111&bbbb=121&bbbb=121212
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
            @CookieValue("username-localhost-8888") Cookie cookie
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

    @PostMapping("/save")
    public Map<String, Object> testRequestBody(@RequestBody String body) {
        Map<String, Object> map = new HashMap<>();

        map.put("body", body);

        return map;
    }


    /**
     * 矩阵变量的请求路径：/boss/1;age=102;/emp/2;age=1111
     * @return
     */
    @GetMapping("/boss/{bossId}/{empId}")
    Map<String, Object> testMatrixVariable(@MatrixVariable(value = "age", pathVar = "bossId") Integer bAge,
                                           @MatrixVariable(value = "age", pathVar = "empId") Integer eAge,
                                           @PathVariable("bossId") String bossId,
                                           @PathVariable("empId") String empId) {
        HashMap<String, Object> map = new HashMap<>();

        map.put("boss_msg", bossId + ":::" + bAge);
        map.put("emp_msg", empId + ":::" + eAge);

        /**
         * {
         *     "emp_msg": "2:::40",
         *     "boss_msg": "1:::20"
         * }
         */
        return map;
    }

}
