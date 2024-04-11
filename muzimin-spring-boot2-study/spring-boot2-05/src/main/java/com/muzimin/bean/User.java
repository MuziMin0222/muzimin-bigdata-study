package com.muzimin.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {


    //以下是数据库字段
    private String userName;
    private String password;


}
