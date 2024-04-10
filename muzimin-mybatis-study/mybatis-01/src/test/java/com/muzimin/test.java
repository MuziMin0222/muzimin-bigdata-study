package com.muzimin;

/**
 * @author: 李煌民
 * @date: 2024-01-10 17:14
 **/
public class test {
    public static void main(String[] args) {
        String fileName = "155174_PLAIN_50_103_CODE.zip";
        String inputColumn = "GC NCP";
        System.out.println(String.join("_",
                fileName.split("\\.")[0],
                inputColumn.toLowerCase().replaceAll("_", "").replaceAll(" ", "")
        ));
    }
}
