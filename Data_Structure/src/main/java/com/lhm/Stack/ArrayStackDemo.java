package com.lhm.Stack;

/**
 * @program:Data_Structure
 * @package:com.lhm.Stack
 * @filename:ArrayStackDemo.java
 * @create:2019.09.23.08:55:48
 * @auther:李煌民
 * @description:.用数组模拟的栈
 **/
public class ArrayStackDemo {
    public static void main(String[] args) {
        ArrayStack stack = new ArrayStack(3);

        stack.push(123);
        stack.push(321);
        stack.push(231);

        stack.list();

        stack.pop();
        stack.list();
    }
}

//数组模拟栈的类
class ArrayStack{
    //定义该栈的大小
    private int Max_size;
    //定义数组模拟栈
    private int[] stack;
    //定义一个栈顶
    private int top;

    //构造器
    public ArrayStack(int max_size){
        this.Max_size = max_size;
        stack = new int[this.Max_size];
        top = -1;
    }

    //判断栈是否为空
    public boolean isEmpty(){
        return top == -1;
    }
    //判断栈是否满
    public boolean isFull(){
        return top == this.Max_size - 1;
    }

    //出栈操作
    public int pop(){
        //判断栈是否为空
        if (isEmpty()){
            throw  new RuntimeException("该栈为空。。。。");
        }

        int value = stack[top];
        top--;
        return value;
    }

    //入栈操作
    public void push(int value){
        //判断栈是否满
        if (isFull()){
            System.out.println("该栈已满。。。");
            return;
        }

        top++;
        stack[top] = value;
    }

    //遍历栈
    public void list(){
        //判断栈是否为空
        if (isEmpty()){
            System.out.println("栈为空、、、");
            return;
        }

        for (int i = top;i >= 0;i--){
            System.out.print(stack[i] + "\t");
        }
    }
}
