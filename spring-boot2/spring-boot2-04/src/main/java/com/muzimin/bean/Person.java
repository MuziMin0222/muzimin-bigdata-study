package com.muzimin.bean;

import lombok.Data;
import lombok.ToString;

/**
 * @author: 李煌民
 * @date: 2024-04-07 13:42
 **/
@Data
@ToString
public class Person {
    String userName;
    Pet pet;
}
