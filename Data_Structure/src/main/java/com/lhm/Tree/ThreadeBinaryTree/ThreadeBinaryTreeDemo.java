package com.lhm.Tree.ThreadeBinaryTree;

/**
 * @author : 李煌民
 * @date : 2019-10-31 19:24
 * <h3>Data_Structure</h3>
 * <p>线索二叉树的测试</p>
 **/
public class ThreadeBinaryTreeDemo {
    public static void main(String[] args) {
        Node root = new Node(1);
        Node node2 = new Node(3);
        Node node3 = new Node(6);
        Node node4 = new Node(8);
        Node node5 = new Node(10);
        Node node6 = new Node(14);

        root.left = node2;
        root.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;

        ThreadeBinaryTree tbt = new ThreadeBinaryTree(root);
        //添加索引
        tbt.theradeNode(root);

        //测试十号节点
        Node left = node5.left;
        Node right = node5.right;
        System.out.println("10号的前驱节点是" + left);
        System.out.println("10号的后继节点是" + right);

        tbt.thteadeList();
    }
}

//定义线索化功能的二叉树
class ThreadeBinaryTree{
    //定义根节点
    private Node root;

    public ThreadeBinaryTree(Node root) {
        this.root = root;
    }

    //为了实现线索化，需要创建给指向当前节点的前驱结点的指针，在递归进行线索化时，pre总是保留前一个节点
    private Node pre;

    //编写进行二叉树进行中序线索化的方法,node是当前节点
    public void theradeNode(Node node){
        //如果node为空，那么不能线索化
        if (node == null){
            System.out.println("当前节点为空，不能进行线索化");
            return;
        }

        //先线索化左子树
        theradeNode(node.left);

        //线索化当前节点的前驱结点
        if (node.left == null){
            //让当前节点的左指针指向前驱节点
            node.left = pre;
            //修改当前左指针的类型,该节点的左节点为空
            node.leftType = 1;
        }
        //处理后继节点
        if (pre != null && pre.right == null){
            //让前驱节点的右指针指向当前节点
            pre.right = node;
            //修改当前节点的右指针类型
            pre.rightType = 1;
        }
        //每处理一个节点之后，就让当前节点成为下一个节点的前驱结点
        pre = node;

        //线索化右子树
        theradeNode(node.right);
    }

    //遍历中序线索化二叉树的方法
    public void thteadeList(){
        //定义一个变量，存储当前遍历的节点，从root开始
        Node node = root;
        while (node != null){
            //循环找到leftType == 1的节点，第一个找到的是左子树最左的那个节点
            while (node.leftType == 0){
                node = node.left;
            }
            //打印当前节点
            System.out.println(node);
            //如果当前节点的右指针指向的是后继节点，就一直输出
            while (node.rightType == 1){
                //获得当前节点的后继节点
                node = node.right;
                System.out.println(node);
            }
            //替换这个遍历的节点
            node = node.right;
        }
    }
}

//创建节点类
class Node{
    //节点id
    int id;
    //一个节点的左节点，默认为空
    Node left;
    //一个节点的右节点，默认为空
    Node right;
    //如果leftType == 0 说明该节点的left指向左子树，如果是1，那么该节点的left指向前驱结点
    int leftType;
    //如果rightType == 0 说明该节点的right指向右子树，如果是1，那么该节点的right指向后继节点
    int rightType;

    //构造器为其赋值
    public Node(int id){
        this.id = id;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                '}';
    }
}