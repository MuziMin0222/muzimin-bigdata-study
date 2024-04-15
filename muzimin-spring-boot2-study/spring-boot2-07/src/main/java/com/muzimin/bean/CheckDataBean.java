package com.muzimin.bean;

import lombok.Data;
import lombok.ToString;

/**
 * @author: 李煌民
 * @date: 2024-04-15 13:24
 **/
@Data
@ToString
public class CheckDataBean {
    String etlDate;
    String platform;
    String typeName;
    String s3LineNum;
    String hiveLineNum;
    String subtraction;
    String isCheck;
}
