package com.lhm.LinkedList;

/**
 * @program:Data_Structure
 * @package:com.lhm.LinkedList
 * @filename:DoubleLinkedListDemo.java
 * @create:2019.09.19.16:28:34
 * @auther:李煌民
 * @description:.这是一个双向链表的测试类
 **/
public class DoubleLinkedListDemo {
    public static void main(String[] args) {
        Node n1 = new Node(1, "韩信", "白龙吟");
        Node n2 = new Node(2, "李白", "千年狐");
        Node n3 = new Node(3, "孙悟空", "至尊宝");
        Node n4 = new Node(4, "李煌民", "木子");

        DoubleLinkedList list = new DoubleLinkedList();
//        list.add(n1);
//        list.add(n2);
//        list.add(n3);
//        list.add(n4);
        list.addById(n4);
        list.addById(n2);
        list.addById(n1);
        list.addById(n3);

        //遍历操作
        System.out.println("添加元素后的链表");
        list.list();
    }
}

//定义一个双向链表来管理我们的节点
class DoubleLinkedList{
    //初始化一个头结点,该头结点不为空，id为0
    private Node head = new Node(0,"","");

    //获取头结点
    public Node getHead(){
        return head;
    }

    //不考虑编号，直接将新节点添加到链表的最后
    public void add(Node newNode){
        //定义一个辅助变量来存储第一个节点
        Node temp = head;
        //遍历链表，直到最后的位置
        while (true){
            if (temp.next == null){
                //到了链表最后跳出循环
                break;
            }
            //将节点后移
            temp = temp.next;
        }
        //此时的temp是该链表的最后一个节点
        newNode.pre = temp;
        temp.next = newNode;
    }

    //按照id顺序来添加元素，如果元素存在，那么不添加
    public void addById(Node newNode){
        Node temp = head;
        boolean flag = false;
        while (true){
            if (temp.next == null){
                //说明已经到了最后
                break;
            }
            if (temp.next.id > newNode.id){
                //该位置已经找到，就在temp的后面插入
                break;
            }else if (temp.next.id == newNode.id){
                //说明该位置已存在元素
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag){
            System.out.println("该位置元素已存在");
        }else {
            //插入到链表中
            newNode.next = temp.next;
            if (temp.next != null){
                temp.next.pre = newNode;
                temp.next = newNode;
                newNode.pre = temp;
            }else {
                newNode.pre = temp;
                temp.next = newNode;
            }
        }
    }

    //修改节点里面的值
    public void Update(Node newNode){
        //判断链表是否为空
        if (head.next == null){
            System.out.println("该链表为空。。。。");
            return;
        }

        //定义一个辅助变量来存储该链表的第一个节点
        Node temp = head.next;
        boolean flag = false;
        while (true){
            if (temp == null){
                System.out.println("该元素不存在");
                break;
            }
            if (temp.id == newNode.id){
                flag = true;
                System.out.println("该元素存在，id为" + temp.id);
                break;
            }
            temp = temp.next;
        }

        if (flag){
            temp.name = newNode.name;
            temp.nickname = newNode.nickname;
        }
    }

    //删除节点操作
    public void Delete(int id){
        if (head.next == null){
            System.out.println("链表为空、、、");
            return;
        }
        Node temp = head;
        boolean flag = false;

        while (true){
            if (temp == null){
                //没有找到该节点
                System.out.println("要删除的元素不存在");
                break;
            }
            if (temp.id == id){
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag){
            temp.pre.next = temp.next;
            if (temp.next != null){
                temp.next.pre = temp.pre;
            }
        }
    }

    //遍历链表操作
    public void list(){
        //判断链表是否为空
        if (head.next == null){
            System.out.println("链表为空。。。");
            return;
        }

        //定义一个辅助变量存储第一个结点
        Node temp = head.next;

        while (true){
            System.out.println(temp);
            if (temp.next == null){
                //遍历到了最后
                break;
            }
            temp = temp.next;
        }
    }
}

//定义一个节点类
class Node{
    //每个节点的编号
    public int id;
    //每个节点中的值
    public String name;
    //每个节点中的值的别名
    public String nickname;
    //指向该节点的上一个节点
    public Node pre;
    //指向该节点的下一个节点
    public Node next;

    //定义构造器为属性赋值
    public Node(int id,String name,String nickname){
        this.id = id;
        this.name = name;
        this.nickname = nickname;
    }

    //重写toString方法
    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
