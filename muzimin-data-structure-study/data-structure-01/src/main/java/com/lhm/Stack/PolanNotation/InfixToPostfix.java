package com.lhm.Stack.PolanNotation;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @program:Data_Structure
 * @package:com.lhm.Stack.PolanNotation
 * @filename:InfixToPostfix.java
 * @create:2019.10.17.09:51:51
 * @auther:李煌民
 * @description:.中缀表达式转后缀表达式
 **/
public class InfixToPostfix {
    public static void main(String[] args) {
        //将中缀表达式转为后缀表达式
        List<String> postfixString = new InfixToPostfix().InfixToSufix("1+((2+3)*4)-5");
        System.out.println(postfixString);
        int calculate = PolanNotation.calculate(postfixString);
        System.out.println(calculate);
    }

    //将中缀表达式转为后缀表达式
    public List<String> InfixToSufix(String line){
        //将表达式转为中缀表达式的list集合
        List<String> InfixList = ExpressionToList(line);
        //定义一个符号栈
        Stack<String> stack = new Stack<>();
        //存储中间结果我们用List集合来存储
        ArrayList<String> list = new ArrayList<>();

        //遍历中缀表达式的list集合
        for (String s : InfixList) {
            //如果s是一个数字，直接加入到中间结果集合中
            if (s.matches("\\d+")){
                list.add(s);
            }else if (s.equals("(")){
                //如果是一个左括号,就加入到符号栈中
                stack.push(s);
            }else if (s.equals(")")){
                //如果是一个右括号，则依次弹出符号栈栈顶的元素的运算符，并压入到中间结果集合中，
                // 直到遇到左括号为止，将这一对括号丢弃
                while (!stack.peek().equals("(")){
                    list.add(stack.pop());
                }
                //将（弹出栈，消除小括号
                stack.pop();
            }else {
                //当s的优先级小于等于stack栈顶的运算符，将s1栈顶的运算符弹出并加入到s2中，再次转到4.1中与stack中新的栈顶运算符相比较
                //目前我们缺少比较运算符优先级的方法，于是定义一个Operation类来返回运算符优先级
                while (stack.size() != 0 && Operation.getValue(stack.peek()) >= Operation.getValue(s)){
                    list.add(stack.pop());
                }
                //还需要将s压入栈中
                stack.push(s);
            }
        }

        //将stack中剩余的运算符依次弹出并加入list中
        while (stack.size() != 0){
            list.add(stack.pop());
        }
        //因为存放的位置是list，所以直接按顺序输出就是一个后缀表达式
        return list;
    }

    //将中缀表达式中的各个元素添加到集合中
    public List<String> ExpressionToList(String line){
        //创建一个集合用于存储表达式中的各个元素
        ArrayList<String> list = new ArrayList<>();
        //定义一个索引，用于遍历中缀表达式
        int index = 0;
        //对多位数进行拼接的对象
        String str;
        //每遍历到一个字符，就放到ch中
        char ch;
        while (index < line.length()){
            //如果是一个非数字，我们需要加入到list集合中
            if ((ch = line.charAt(index)) < 48 || (ch = line.charAt(index)) > 57){
                list.add("" + ch);
                index++;//index需要后移
            }else {
                //如果是一个数字，考虑多位数
                str = "";//置空字符串
                while (index < line.length() && (ch = line.charAt(index)) >=48 && (ch = line.charAt(index)) <= 57){
                    str += ch;
                    index++;
                }
                list.add(str);
            }
        }
        return list;
    }
}
