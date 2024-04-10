package com.lhm.Stack.PolanNotation;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @program:Data_Structure
 * @package:com.lhm.Stack
 * @filename:PolanNotation.java
 * @create:2019.10.16.10:27:29
 * @auther:李煌民
 * @description:.使用逆波兰表达式的求解
 **/
public class PolanNotation {
    public static void main(String[] args) {
        //先定义一个逆波兰表达式3 4 + 5 * 6 -，为了使用方便，我们用空格隔开
        String line = "3 4 + 5 * 6 -";
        /*
        1、将表达式中的每一个元素放入到一个集合中
        2、将集合中传递给一个方法，遍历集合配合栈完成计算
         */
        List<String> list = getListString(line);
        int res = calculate(list);
        System.out.println(line + "=" + res);
    }

    public static List<String> getListString(String line){
        //表达式用空格进行分割
        String[] strs = line.split("[ ]");
        ArrayList<String> list = new ArrayList<>();
        for (String str : strs) {
            list.add(str);
        }
        return list;
    }

    //完成逆波兰表达式的运算
    public static int calculate(List<String> ls){
        //创建一个栈，用于存储表达式中的数字
        Stack<String> stack = new Stack<>();

        //遍历集合
        for (String s : ls) {
            //使用正则表达式来判断该字符串是否是数字
            if (s.matches("\\d+")){
                //入栈
                stack.push(s);
            }else {
                //pop两个数，并运算，再入栈
                int num2 = Integer.parseInt(stack.pop());
                int num1 = Integer.parseInt(stack.pop());
                int res = 0;
                if (s.equals("+")){
                    res = num1 + num2;
                }else if (s.equals("-")){
                    res = num1 - num2;
                }else if (s.equals("*")){
                    res = num1 * num2;
                }else if (s.equals("/")){
                    res = num1 / num2;
                }else {
                    throw new RuntimeException("定义的符号不标准。。。");
                }
                //把结果如涵
                stack.push("" + res);
            }
        }
        return Integer.parseInt(stack.pop());
    }
}
