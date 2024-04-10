package com.lhm.Stack.PolanNotation;

/**
 * @program:Data_Structure
 * @package:com.lhm.Stack.PolanNotation
 * @filename:Operation.java
 * @create:2019.10.18.09:38:41
 * @auther:李煌民
 * @description:.该类用于表示运算符的优先级
 **/
public class Operation {
    private static int ADD = 1;
    private static int SUB = 1;
    private static int MUL = 2;
    private static int DIV = 2;

    //写一个方法，返回对应的优先级数字
    public static int getValue(String opertion){
        int result = 0;
        switch (opertion) {
            case "+" :
                result = ADD;
                break;
            case "-" :
                result = SUB;
                break;
            case "*" :
                result = MUL;
                break;
            case "/" :
                result = DIV;
                break;
            default:
                System.out.println("输入的运算符非法。。。");
                break;
        }

        return result;
    }
}
