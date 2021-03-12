package com.lhm.hashTable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author : 李煌民
 * @date : 2019-10-26 16:39
 * <h3>Data_Structure</h3>
 * <p>哈希表的测试</p>
 **/
public class HashTableDemo {
    public static void main(String[] args) throws IOException {
        //创建哈希表,创建七条链表
        HashTab hashTab = new HashTab(7);
        //写一个简单的页面
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String key = "";
        while (true){
            System.out.println("add:添加雇员");
            System.out.println("list:显示雇员");
            System.out.println("find:查找雇员");
            System.out.println("exit:退出系统");
            key = br.readLine();
            switch (key){
                case "add" :
                    System.out.println("输入id");
                    int id = Integer.parseInt(br.readLine());
                    System.out.println("输入名字");
                    String name = br.readLine();
                    //创建雇员
                    Emp emp = new Emp(id, name);
                    hashTab.add(emp);
                    break;
                case "list":
                    hashTab.list();
                    break;
                case "find":
                    System.out.println("输入要查找的id：");
                    id = Integer.parseInt(br.readLine());
                    hashTab.findEmpById(id);
                    break;
                case "exit":
                    br.close();
                    System.exit(0);
                    break;
            }
        }
    }
}

//创建一个HashTable管理多条链表
class HashTab{
    private EmpLinkedList[] empLinkedListArray;
    private int size;//表示有多少条链表

    //构造器
    public HashTab(int size){
        this.size = size;
        //初始化emplinkedListArray
        this.empLinkedListArray = new EmpLinkedList[size];
        //在empLinkedListArray中的全部链表都要进行初始化
        for (int i = 0; i < size; i++) {
            empLinkedListArray[i] = new EmpLinkedList();
        }
    }

    //添加雇员
    public void add(Emp emp){
        //根据员工的id，得到该员工应当添加到哪条链表
        int empLinkedListNum = hashFun(emp.id);
        //将emp添加到对应的链表中
        empLinkedListArray[empLinkedListNum].add(emp);
    }

    //遍历所有的链表，遍历HashTable
    public void list(){
        for (int i = 0; i < size; i++) {
            empLinkedListArray[i].list(i);
        }
    }

    //编写一个散列函数，使用一个简单的取模法
    public int hashFun(int id){
        return id % size;
    }

    //根据输入的id，查找雇员
    public void findEmpById(int id){
        //使用散列函数确定到哪条链表查找
        int empLinkedListNum = hashFun(id);
        Emp emp = empLinkedListArray[empLinkedListNum].FindEmpById(id);
        if (emp != null){
            System.out.println("在第" + (empLinkedListNum + 1) + "链表中找到雇员id为" + id + "的雇员");
        }else {
            System.out.println("该哈希表中没有找到雇员");
        }
    }
}

//创建一个EmpLinkedList，表示链表
class EmpLinkedList{
    //头指针，执行第一个Emp，因此我们这个链表的head是直接指向第一个Emp
    private Emp head;//默认为null

    /**
     * 该方法用于添加雇员，当添加雇员时，假设id是自增长的，即id的分配总是从小到大
     * 因此直接添加到本链表的最后即可
     * @param emp
     */
    public void add(Emp emp){
        //如果是添加第一个雇员
        if (head == null){
            head = emp;
            return;
        }
        //如果不是第一个雇员，则使用一个辅助的指针，帮助定位到最后
        Emp curemp = head;
        while (true){
            if (curemp.next == null){//说明到了链表的最后
                break;
            }
            //后移
            curemp = curemp.next;
            //退出时直接将emp加入链表
            curemp.next = emp;
        }
    }

    //遍历链表的雇员信息
    public void list(int no){
        if (head == null){//说明链表为空
            System.out.println("第" + (no + 1) + "的链表为空");
            return;
        }
        System.out.print("第" + (no + 1) + "链表信息为：");
        Emp curemp = head;//定义辅助指针
        while (true){
            System.out.println("id:" + curemp.id + "name:" + curemp.name);
            if (curemp.next == null){
                //说明到了链表的最后
                break;
            }
            //后移，遍历
            curemp = curemp.next;
        }
        System.out.println();
    }

    //根据id查找雇员
    //如果查找到，就返回employee，如果没有找到，就返回null
    public Emp FindEmpById(int id){
        //判断链表是否为空
        if (head == null){
            System.out.println("链表为空");
            return null;
        }
        //定义一个辅助指针
        Emp curemp = head;
        while (true){
            if (curemp.id == id){
                //已经找到，退出循环
                break;//这个时候curemp就指向我们要找的雇员
            }
            //将链表遍历到最后都没有找到该雇员
            if (curemp.next == null){
                curemp = null;
                break;
            }
            //辅助指针后移
            curemp = curemp.next;
        }
        return curemp;
    }
}


//创建一个雇员类
class Emp {
    public int id;
    public String name;
    //指向下一个节点信息
    public Emp next;
    //构造器
    public Emp(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
