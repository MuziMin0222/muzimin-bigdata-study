package com.lhm.LinkedList;

/**
 * @program:Data_Structure
 * @package:com.lhm.LinkedList
 * @filename:CirecleLinkedListDemo.java
 * @create:2019.09.19.20:25:58
 * @auther:李煌民
 * @description:.环形链表的创建
 **/
public class CirecleLinkedListDemo {
    public static void main(String[] args) {
        CircleLinkedList list = new CircleLinkedList();

        //为回环链表添加元素
        list.add(5);

        //遍历集合
        list.list();
        System.out.println("====================");

        list.ChuQuan(1,2,5);
    }
}

//定义一个回环链表
class CircleLinkedList{
    //创建一个first节点，当前节点为空
    private BoyNode first = null;

    //添加节点，使之成为回环节点
    public void add(int n){
        //数据校验
        if (n < 0){
            System.out.println("输入的节点个数要大于0");
            return;
        }
        //创建辅助变量
        BoyNode temp = null;
        //使用for循环来创建我们的回环链表
        for (int i = 1; i <= n; i++) {
            //根据编号创建节点
            BoyNode node = new BoyNode(i);
            //第一个小孩的指针是不能移动的
            if (i == 1){
                first = node;
                //构建成环状
                first.next = first;
                //让辅助变量指向第一个节点
                temp = first;
            }else {
                temp.next = node;
                node.next = first;
                temp = node;
            }
        }
    }

    //遍历当前链表
    public void list(){
        //判断链表是否为空
        if (first == null){
            System.out.println("链表为空。。");
            return;
        }
        //定义一个辅助变量帮助我们遍历链表
        BoyNode temp = first;
        while (true){
            System.out.println("当前节点编号为" + temp.id);
            if (temp.next == first){
                //说明遍历完了
                break;
            }
            //辅助变量后移
            temp = temp.next;
        }
    }

    //根据用户的输入，计算人出圈的顺序
    /**
     *
     * @param startId   表示从第几个人开始数数
     * @param countNum  表示数几下
     * @param num       表示最初有多少人在圈中
     */
    public void ChuQuan(int startId,int countNum,int num){
        //对数据进行校验
        if (first == null || startId < 1 || startId > num){
            System.out.println("输入参数有误，重新输入");
            return;
        }
        //创建辅助指针
        BoyNode temp = first;
        //需求创建一个辅助指针，该辅助指针需要指向该链表的最后一个节点
        while (true){
            if (temp.next == first){
                //说明已经到了最后
                break;
            }
            temp = temp.next;
        }
        //在报数前，先让first和temp指针移动startId-1次
        for (int j = 0;j < startId-1;j++){
            first = first.next;
            temp = temp.next;
        }
        //在报数时，让first和temp指针同时移动countNum-1次，然后出圈，直到圈中只有一个节点为止
        while (true){
            if (first == temp){
                //说明圈中只有一个节点
                break;
            }
            //让first和temp指针同时移动countNum-1次
            for (int i = 0;i < countNum-1;i++){
                first = first.next;
                temp = temp.next;
            }
            //这时first指针指向的节点就是要出圈的节点
            System.out.println("出圈的节点为：" + first.id);
            //出圈操作
            temp.next = first.next;
            first = first.next;
        }
        System.out.println("此时在圈中的人为：" + first.id);
    }
}

//定义一个环形链表的节点类
class BoyNode{
    public int id;
    public BoyNode next;

    public BoyNode(int id){
        this.id = id;
    }
}
