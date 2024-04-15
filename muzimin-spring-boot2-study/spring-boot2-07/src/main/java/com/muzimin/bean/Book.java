package com.muzimin.bean;

import lombok.Data;
import lombok.ToString;

/**
 * @author: 李煌民
 * @date: 2024-04-15 13:51
 **/
@Data
@ToString
public class Book {
    private int userId;
    private String userName;
    private String userStatus;
}
