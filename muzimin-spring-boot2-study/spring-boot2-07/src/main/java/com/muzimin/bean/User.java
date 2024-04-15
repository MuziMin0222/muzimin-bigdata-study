package com.muzimin.bean;

import lombok.Data;
import lombok.ToString;

/**
 * @author: 李煌民
 * @date: 2024-04-15 14:35
 **/
@Data
@ToString
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
