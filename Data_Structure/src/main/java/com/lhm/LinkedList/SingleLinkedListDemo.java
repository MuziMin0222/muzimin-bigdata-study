package com.lhm.LinkedList;

import java.util.Stack;

/*
单链表的实现
 */
public class SingleLinkedListDemo {
    public static void main(String[] args) {
        HeroNode h1 = new HeroNode(10, "亚瑟", "死亡骑士");
        HeroNode h2 = new HeroNode(20, "诸葛亮", "星航指挥官");
        HeroNode h3 = new HeroNode(30, "花木兰", "兔女郎");
        SingleLinkedList list = new SingleLinkedList();
        list.addById(h2);
        list.addById(h3);
        list.addById(h1);

        HeroNode h4 = new HeroNode(15, "韩信", "白龙吟");
        HeroNode h5 = new HeroNode(25, "李白", "千年狐");
        HeroNode h6 = new HeroNode(35, "孙悟空", "至尊宝");
        SingleLinkedList list1 = new SingleLinkedList();
        list1.addById(h6);
        list1.addById(h5);
        list1.addById(h4);

        HeroNode heroNode = mergeTwoLists(list.getHead(), list1.getHead());
        bianli(heroNode);

    }

    //遍历
    public static void bianli(HeroNode head){
        if (head == null){
            System.out.println("链表为空。。。");
            return;
        }

        HeroNode temp = head.next;
        while (temp != null){
            System.out.println(temp);
            temp = temp.next;
        }
    }

    //合并两个有序的单链表，合并之后的链表仍然有序
    /*
    1、判断是否链表为空，如果head1.next为空，那么返回head2，如果head2.next为空，那么返回head1
    2、比较head1和head2中的第一个节点，选出最小的节点，将该节点设为合并后的链表的head第一个节点，同时将head1和head2后移
    3、设置一个变量temp指向head节点，用于之后连接其他节点
    4、再比较head1和head2接地那，同样选出小的那个节点，将该节点设为合并后的第二个节点，用temp.next表示该节点，同时将该节点后移
    5、重复比较head1和head2节点，直到head1或者head2节点为空
    6、此时，必有一个链表中的所有节点都放入了新链表中，只要将另一个链表中的剩余的所有节点都接到新链表之后就可以了
     */
    public static HeroNode mergeTwoLists(HeroNode head1,HeroNode head2){
        HeroNode head = new HeroNode(0,"","");
        if (head1.next == null){
            return head2;
        }
        if (head2.next == null){
            return head1;
        }
        //如果head1节点的id值小于等于head2节点的id值，由于这两个链表是有序的，所以合并后的最小节点就是他们之间最小的节点
        if (head1.next.id <= head2.next.id){
            head.next = head1.next;
            head1.next = head1.next.next;
        }else {
            head.next = head2.next;
            head2.next = head2.next.next;
        }

        HeroNode temp = head.next;

        while (head1.next != null && head2.next != null){
            if (head1.next.id <= head2.next.id){
                temp.next = head1.next;
                head1.next = head1.next.next;
            }else {
                temp.next = head2.next;
                head2.next = head2.next.next;
            }
            temp = temp.next;
        }

        if (head1.next == null){
            temp.next = head2.next;
        }
        if (head2.next == null){
            temp.next = head1.next;
        }
        return head;
    }

    //不改变链表原有的结构进行逆序打印
    public static void ReversePrint(HeroNode head){
        if (head.next == null){
            return;//链表为空，所以直接停止
        }
        //使用栈的特点来实现链表反转打印
        Stack<HeroNode> stack = new Stack<>();
        //遍历原来的链表来进行压栈
        HeroNode temp = head.next;
        while (temp != null){
            stack.push(temp);
            temp = temp.next;
        }
        //通过栈的特点，一个一个弹出并输出
        while (stack.size() > 0){
            System.out.println(stack.pop());
        }
    }

    //链表的反转
    public static void FanZhuang(HeroNode head){
        //如果当前链表为空或者只有一个节点时，无需反转，直接返回
        if (head.next == null || head.next.next == null){
            System.out.println("该链表只有一个元素或者没有元素");
            return;
        }
        //定义一个辅助变量，帮助我们遍历原理的链表
        HeroNode temp = head.next;
        //指向当前节点的下一个节点
        HeroNode next = null;
        //创建一个反转节点
        HeroNode reverseHead = new HeroNode(0,"","");
        /*
        遍历原来的链表
        并从头到尾遍历一遍原来的链表，没遍历一个节点，就将其取出
        并放在新的reverseHead的最前端
         */
        while (temp != null){
            //先暂时保存当前节点的下一个节点，后面要用
            next = temp.next;
            //将反转头结点指向当前节点的下一个节点
            temp.next = reverseHead.next;
            //将当前节点连接到反转头结点
            reverseHead.next = temp;
            //让temp后移
            temp = next;
        }
        //将head.next指向reverseHead.next，实现单链表的反转
        head.next = reverseHead.next;
    }

    //求单链表中的倒数第K个节点
    /*
    1、编写一个方法接收head节点，同时接收index
    2、index表示倒数第index个节点
    3、先把链表从头到尾遍历，得到链表的总长度getLength
    4、得到size后，我们从链表的第一个开始遍历size-index个，就可以得到
    5、如果找到了，则返回该节点，否则返回null
     */
    public static HeroNode getLastIndexNode(HeroNode head,int index){
        if (head.next == null){
            return null;
        }
        HeroNode temp = head.next;
        int size = getLength(head);

        if (index < 0 || index > size){
            return null;
        }

        for (int i = 0; i < size -index; i++) {
            temp = temp.next;
        }
        return temp;
    }

    //求单链表中的有效节点个数
    public static int getLength(HeroNode head){
        if (head.next == null){
            return 0;
        }
        HeroNode temp = head.next;
        int length = 1;
        while (temp.next != null){
            length++;
            temp = temp.next;
        }
        return length;
    }
}

//定义一个singleLinkedList管理我们的英雄
class SingleLinkedList{
    //初始化一个头结点，该头结点不动，也不存放具体的数据
    private HeroNode head = new HeroNode(0,"","");

    //获取头结点
    public HeroNode getHead(){
        return head;
    }

    /*
    添加节点到单链表
    思路：不考虑编号顺序时
        1：找到当前链表的最后节点
        2：将最后这个节点的next指向新的节点
     */
    public void add(HeroNode heroNode){
        //头结点不可以动，我们定义一个辅助变量来存储头结点
        HeroNode temp = head;
        //遍历链表，找到最后
        while (true){
            //遍历链表，找到最后
            if (temp.next == null){
                break;
            }
            //如果没有找到最后，将temp后移
            temp = temp.next;
        }
        //当退出while循环时，temp就指向了最后
        //将最后这个节点的next指向了新的节点
        temp.next = heroNode;
    }

    /*
    第二种方式添加元素的方式：根据编号来将元素插到指定位置
        如果有这个排名，那么添加失败，并给出提示
     */
    public void addById(HeroNode heroNode){
        /*
        因为头结点不可以动，因此我们通过一个辅助变量来帮助找到添加的位置
           因为是单链表，因此我们找的temp位置是位于添加位置的前一个节点，否则插入不了
         */
        HeroNode temp = head;
        //flag标示添加的编号是否存在，默认是false
        boolean flag = false;
        while (true){
            if (temp.next == null){
                //说明temp已经在链表的最后
                break;
            }
            if (temp.next.id > heroNode.id){
                //该位置已经找到，就在temp的后面插入
                break;
            }else if (temp.next.id == heroNode.id){
                //说明希望添加的HeroNode的编号已经存在
                flag = true;
                break;
            }
            //将该节点后移
            temp = temp.next;
        }
        //判断flag的值
        if (flag){
            //不能添加。因为该节点已经存在
            System.out.println(heroNode.id + "已存在");
        }else {
            //插入到链表中，在temp的后面
            heroNode.next = temp.next;
            temp.next = heroNode;
        }
    }

    //修改节点元素里面的值
    public void Update(HeroNode newHeroNode){
        //判断链表是否为空
        if (head.next == null){
            System.out.println("该链表为空。。。。");
            return;
        }

        HeroNode temp = head.next;
        boolean flag = false;
        while (true){
            if (temp.next == null){
                //该节点不存在
                break;
            }
            if (temp.id == newHeroNode.id){
                //该节点已经找到
                flag = true;
                break;
            }
            temp = temp.next;
        }

        //根据flag，判断是否找到要修改的节点
        if (flag){
            temp.name = newHeroNode.name;
            temp.nickname = newHeroNode.nickname;
        }else {
            System.out.println("您找的节点" + newHeroNode.id + "不存在。。。");
        }
    }

    //根据链表元素的id来删除元素
    public void Delete(int id){
        HeroNode temp = head;
        //标志是否找到待删除的节点
        boolean flag = false;
        if (temp.next == null){
            System.out.println("链表为空。。。。");
            return;
        }
        while (true){
            if (temp.next == null){
                //说明已经到了链表的最后
                break;
            }
            if (temp.next.id == id){
                //找到了待删除节点的前一个元素temp
                flag = true;
                break;
            }
            //上述条件不满足时将temp后移
            temp = temp.next;
        }
        if (flag){
            temp.next = temp.next.next;
        }else {
            System.out.println("要删除的元素不存在。。。");
        }
    }

    //遍历链表
    public void list(){
        if (head.next == null){
            System.out.println("链表为空、。。。。");
            return;
        }
        //因为头结点，不能动，定义一个辅助变量存储头结点
        HeroNode temp = head;
        while (true){
            //判断链表是否为空
            if (temp.next == null){
                break;
            }
            //此时要将temp后移
            temp = temp.next;
            //输出节点信息
            System.out.println(temp);
        }
    }
}

//定义HeroNode，每个HeroNode对象就是一个节点
class HeroNode{
    //每个节点的编号
    public int id;
    //每个节点中的值
    public String name;
    //每个节点中的值的别名
    public String nickname;
    //指向下一个节点
    public HeroNode next;

    //构造器
    public HeroNode(int id,String name,String nickname){
        this.id = id;
        this.name = name;
        this.nickname = nickname;
    }

    //重写toString方法
    @Override
    public String toString() {
        return "HeroNode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
