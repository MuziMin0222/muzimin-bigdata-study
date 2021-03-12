package com.lhm.Stack;

/**
 * @program:Data_Structure
 * @package:com.lhm.Stack
 * @filename:LinkedListStackDemo.java
 * @create:2019.09.25.11:12:19
 * @auther:李煌民
 * @description:.使用链表来实现栈
 **/
public class LinkedListStackDemo {
    public static void main(String[] args) {
        LinkedListStack<String> stack = new LinkedListStack<>(3);

        stack.pop("lhm");
        stack.pop("lz");
        stack.pop("xbb");

        stack.list();

        System.out.println("1:" + stack.push());
        System.out.println("2:" + stack.push());
        System.out.println("3:" + stack.push());

        stack.list();

        stack.pop("典韦");

        stack.push();

        stack.list();

    }
}

//定义一个栈
class LinkedListStack<T>{
   //该栈的最大容量
    private int max_size;
   //该栈的栈顶指针
    private int top;
    //定义一个链表来存储栈中数据
    private StackLinkedList<T> list;

    //构造器
    public LinkedListStack(int max_size){
        this.max_size = max_size;
        this.top = -1;
        this.list = new StackLinkedList<>();
    }

    //判断该栈是否为空
    public boolean isEmpty(){
        return top == -1;
    }

    //判断该栈是否已满
    public boolean isFull(){
        return top == max_size-1;
    }

    //压栈
    public void pop(T value){
        if (isFull()){
            System.out.println("该栈已满。。。。");
            return;
        }
        top++;
        Node<T> newNode = new Node<>(value);
        list.add(newNode);
    }

    //出栈
    public T push(){
        if (isEmpty()){
            System.out.println("该栈为空。。。");
            throw new RuntimeException("栈空");
        }
        Node<T> lastNode = list.getLast();
        top--;

        return lastNode.value;
    }

    //遍历栈
    public void list(){
        if (isEmpty()){
            System.out.println("栈为空。。。");
            return;
        }
        Node head = list.getHead();
        Node temp = head;

        while (true){
            if (temp.next == null){
                break;
            }
            temp = temp.next;

            System.out.println(temp.value);
        }
    }
}

//定义一个单链表
class StackLinkedList<T>{
    //定义链表的头节点
    private Node<T> head = new Node(null);

    //获取头结点的方法
    public Node getHead(){
        return head;
    }

    //该链表的添加操作，只在链表的最后的位置进行添加元素
    public void add(Node newnode){
        //定义辅助变量来接收头结点
        Node temp = head;

        //遍历链表，将temp指向链表的最后
        while (true){
            //找到了最后的位置即跳出循环
            if (temp.next == null){
                break;
            }
            //辅助变量后移
            temp = temp.next;
        }

        //这时的temp指向了链表的最后一个节点
        temp.next = newnode;
    }

    //得到该链表的第一个元素
    public Node<T> getLast(){
        Node<T> temp = head.next;

        head.next = head.next.next;

        return temp;
    }
}

//定义一个链表的节点
class Node<T>{
    //链表的值
    public T value;
    //指向下一个节点的指针
    public Node next;

    //构造器对属性进行赋值
    public Node(T value){
        this.value = value;
    }
}
