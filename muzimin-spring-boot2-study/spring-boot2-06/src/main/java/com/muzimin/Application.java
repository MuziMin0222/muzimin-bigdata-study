package com.muzimin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author: 李煌民
 * @date: 2024-04-12 15:44
 **/
@ServletComponentScan(basePackages = "com.muzimin")//指定原生servlet组件都放在哪里
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
