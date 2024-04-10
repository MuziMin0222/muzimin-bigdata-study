package com.muzimin.pojo;

/**
 * @author: 李煌民
 * @date: 2024-03-28 16:32
 **/

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 只有在容器中的组件，才会拥有SpringBoot提供的强大功能
 */
//@Component
@ConfigurationProperties(prefix = "mycar")
@Data                 //get\set方法
@AllArgsConstructor   //所有参数的构造器
@NoArgsConstructor    //无参构造器
@ToString             //toString方法
@EqualsAndHashCode    //equals 和 HashCode方法
@Slf4j                //SLF4j的对象，对象名为log
public class Car {
    private String brand;
    private String price;

    public void testLog() {
        log.info("aaaa");
    }
}
