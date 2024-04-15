package com.muzimin.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

/**
 * @author: 李煌民
 * @date: 2024-04-15 10:16
 **/
@RestController
public class MyDataSourceController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;

    Counter counter;

    public MyDataSourceController(MeterRegistry meterRegistry) {
        counter = meterRegistry.counter("myservice.method.running.counter");
    }

    @GetMapping("/test")
    public String testJDBC() {
        counter.increment();

        Long aLong = jdbcTemplate.queryForObject("select count(*) from check_data", Long.class);
        System.out.println(aLong);
        System.out.println(dataSource.getClass());
        return "ok";
    }
}
