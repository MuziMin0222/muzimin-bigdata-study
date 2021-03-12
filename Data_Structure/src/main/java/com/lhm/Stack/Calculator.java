package com.lhm.Stack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @program:Data_Structure
 * @package:com.lhm.Stack
 * @filename:Calculator.java
 * @create:2019.10.15.19:23:43
 * @auther:李煌民
 * @description:.用数组模拟栈写一个计算器的表达式
 **/
public class Calculator {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("请输入要计算的表达式：");
        //使用键盘录入的方式来获取字符串
        String line = br.readLine();

        //创建两个栈，一个符号栈，一个数字栈
        ArrStack numStatck = new ArrStack(10);
        ArrStack operStatck = new ArrStack(10);

        //定义需要的相关变量
        int index = 0;//用于扫描字符串
        int num1 = 0;
        int num2 = 0;
        int oper = 0;
        int res = 0;
        char ch = ' ';//将每次扫描得到的字符保存到ch中
        String keepnum = "";//用于多位数的拼接

        //循环扫描表达式
        while (true){
            //依次得到表达式中的每一个字符
            ch = line.substring(index,index+1).charAt(0);
            //判断ch是什么，然后做相应的处理
            //如果该ch是运算符
            if (operStatck.isOper(ch)){
                //判断当前的符号栈是否为空
                if (!operStatck.isEmpty()){
                    //如果符号栈中有操作符，就进行比较，如果当前的操作符的优先级小于或者等于栈中的操作符
                    //就需要从数栈中pop两个数字，从符号栈中pop一个符号，进行运算得到结果再放入到数栈中，然后将当前的操作符入符号栈
                    if (operStatck.prioroty(ch) <= operStatck.prioroty(operStatck.peek())){
                        num1 = numStatck.pop();
                        num2 = numStatck.pop();
                        oper= operStatck.pop();
                        res = numStatck.cal(num1,num2,oper);
                        //把运算结果入数栈
                        numStatck.push(res);
                        //把该符号入符号栈
                        operStatck.push(ch);
                    }else {
                        //如果当前的操作符的优先级大于栈中的操作符，就直接入符号栈
                        operStatck.push(ch);
                    }
                }else {
                    //如果符号栈为空，直接入栈
                    operStatck.push(ch);
                }
            }else {
                //如果是数字
                keepnum += ch;

                //如果ch是表达式中的最后一位，就直接入栈
                if (index == line.length() -1){
                    numStatck.push(Integer.parseInt(keepnum));
                }else {
                    //判断下一个字符是不是数字，如果不是数字，就继续扫描，如果是运算符，则入栈
                    if(operStatck.isOper(line.substring(index + 1,index + 2).charAt(0))){
                        //如果后一位是运算符，则入栈
                        numStatck.push(Integer.parseInt(keepnum));
                        //把keepnum进行清空
                        keepnum = "";
                    }
                }
            }
            //让index++，并判断是否扫描到了表达式的最后
            index++;
            if (index >= line.length()){
                break;
            }
        }

        //当表达式扫描完毕，就顺序的从数栈和符号栈中pop相应的数和符号，并运行
        while (true){
            //如果符号栈为空，则计算到最后的结果，数栈中只有一个数字，就是结果
            if (operStatck.isEmpty()){
                break;
            }
            num1 = numStatck.pop();
            num2 = numStatck.pop();
            oper = operStatck.pop();
            res = numStatck.cal(num1,num2,oper);
            numStatck.push(res);
        }
        int result = numStatck.pop();
        System.out.println(line +  "=" + result);
    }
}


//使用数组模拟栈
class ArrStack{
    private int  Max_size;
    private int[] stack;
    private int top;

    public ArrStack(int max_size) {
        this.Max_size = max_size;
        this.stack = new int[this.Max_size];
        this.top = -1;
    }

    public boolean isEmpty(){
        return top == -1;
    }
    public boolean isFull(){
        return top == Max_size-1;
    }

    //出栈
    public int pop(){
        //先判断该栈是否为空
        if (isEmpty()){
            throw new RuntimeException("该栈为空");
        }

        int value = stack[top];
        top--;
        return value;
    }

    //入栈
    public void push(int num){
        //先判断该栈是否已满
        if (isFull()){
            throw new RuntimeException("该栈已满。。。");
        }

        top++;
        stack[top] = num;
    }

    //返回运算符的优先级，优先级是由程序员定义，用数字表示，数字越大，则优先级越高
    public int prioroty(int oper){
        if (oper == '*' || oper == '/'){
            return 1;
        }else if (oper == '+' || oper == '-'){
            return 0;
        }else {
            return -1; //假设目前的表达式只有+-*/
        }
    }

    //判断是不是一个运算符
    public boolean isOper(char val){
        return val == '+' || val == '-' ||val == '*' || val == '/';
    }

    //计算方法
    public int cal(int num1,int num2,int oper){
        int res = 0;//用于存放运算结果
        switch (oper){
            case '+' :
                res = num1 + num2;
                break;
            case '-' :
                res = num2 - num1;
                break;
            case '*' :
                res = num1 * num2;
                break;
            case '/' :
                res = num2 / num1;
                break;
            default:
                break;
        }
        return res;
    }

    //返回当前栈顶的值，但是不出栈
    public int peek(){
        return stack[top];
    }
}
