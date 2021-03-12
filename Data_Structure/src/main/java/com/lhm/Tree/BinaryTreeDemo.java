package com.lhm.Tree;

/**
 * @author : 李煌民
 * @date : 2019-10-28 18:33
 * <h3>Data_Structure</h3>
 * <p>二叉树结构</p>
 **/
public class BinaryTreeDemo {
    public static void main(String[] args) {
        //创建一个二叉树对象
        BinaryTree bt = new BinaryTree();
        //创建节点
        Node root = new Node(1, "lhm");
        Node node1 = new Node(2, "lz");
        Node node2 = new Node(3, "lyx");
        Node node3 = new Node(4, "lws");

        //手动将节点添加到二叉树中
        bt.setRoot(root);
        root.left = node1;
        root.right = node2;
        node1.left = node3;

        bt.preOrder();
        System.out.println("==========================");
        bt.infixOrder();
        System.out.println("--------------------------");
        bt.postOrder();
        System.out.println("++++++++++++++++++++++++++");

        System.out.println(bt.preOrderSearch(2));
        System.out.println(bt.infixOrderSearch(2));
        System.out.println(bt.postOrderSearch(2));
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
}

//定义一颗二叉树
class BinaryTree{
    //定义一个根节点
    private Node root;

    //给根节点进行赋值操作
    public void setRoot(Node node){
        this.root = node;
    }

    //前序遍历
    public void preOrder(){
        if (this.root != null){
            this.root.preOrder();
        }else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    //前序遍历查找
    public Node preOrderSearch(int num){
        if (this.root != null){
            return this.root.preOrderSearch(num);
        }else {
            return null;
        }
    }

    //中序遍历
    public void infixOrder(){
        if (this.root != null){
            this.root.infixOrder();
        }else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    //中序遍历查找
    public Node infixOrderSearch(int num){
        if (this.root != null){
            return this.root.infixOrderSearch(num);
        }else {
            return null;
        }
    }

    //后序遍历
    public void postOrder(){
        if (this.root != null){
            this.root.postOrder();
        }else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    //后续查找遍历
    public Node postOrderSearch(int num){
        if (this.root != null){
            return this.root.postOrderSearch(num);
        }else {
            return null;
        }
    }

    //删除节点的操作
    public void DelNode(int no){
        if (root != null){
            //如果只有一个root结点,这里立即判断root是不是就是要删除结点
            if (root.num == no){
                root = null;
            }else {
                //递归删除
                root.DelNode(no);
            }
        }else {
            System.out.println("空树，不能删除");
        }
    }
}

//定义树的节点
class Node{
    public int num;
    public String name;
    public Node left;//默认为空
    public Node right;//默认为空

    //构造器
    public Node(int num, String name) {
        this.num = num;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Node{" +
                "num=" + num +
                ", name='" + name + '\'' +
                '}';
    }

    //编写前序遍历的方法
    public void preOrder(){
        System.out.println(this);//先输出父节点
        //递归向左子树前序遍历
        if (this.left != null){
            this.left.preOrder();
        }
        //递归向右子树前序遍历
        if (this.right != null){
            this.right.preOrder();
        }
    }

    //前序遍历查找
    public Node preOrderSearch(int num){
        //比较当前节点是不是
        if (this.num == num){
            return this;
        }
        //1.则判断当前结点的左子节点是否为空，如果不为空，则递归前序查找
        // 2.如果左递归前序查找，找到结点，则返回
        Node result = null;
        if (this.left != null){
            result = this.left.preOrderSearch(num);
        }
        //说明找到了该节点
        if (result != null){
            return result;
        }
        //1.左递归前序查找，找到结点，则返回，否继续判断，
        // 2.当前的结点的右子节点是否为空，如果不空，则继续向右递归前序查找
        if (this.right != null){
            result = this.right.preOrderSearch(num);
        }
        return result;
    }

    //中序遍历的方法
    public void infixOrder(){
        //递归向左子树中序遍历
        if (this.left != null){
            this.left.infixOrder();
        }
        //输出当前节点
        System.out.println(this);
        //递归向右子树中序遍历
        if (this.right != null){
            this.right.infixOrder();
        }
    }

    //中序遍历查找
    public Node infixOrderSearch(int num){
        //先判断当前节点的子节点是否为空
        Node result = null;
        if (this.left != null){
            result = this.left.infixOrderSearch(num);
        }
        if (result != null){
            return result;
        }
        //和当前节点进行比较
        if (this.num == num){
            return this;
        }
        //向右子节点进行查询
        if (this.right != null){
            result = this.right.infixOrderSearch(num);
        }
        return result;
    }

    //后序遍历
    public void postOrder(){
        //递归向左子树后序遍历
        if (this.left != null){
            this.left.postOrder();
        }
        //递归向右子树后序遍历
        if (this.right != null){
            this.right.postOrder();
        }
        //输出当前节点
        System.out.println(this);
    }

    //后续遍历查找
    public Node postOrderSearch(int num){
        Node result = null;
        //左递归查找
        if (this.left != null){
            result = this.left.postOrderSearch(num);
        }
        if (result != null){
            return result;
        }
        //右递归查找
        if (this.right != null){
            result = this.right.postOrderSearch(num);
        }
        //和当前节点进行判断
        if (this.num == num){
            return this;
        }
        return result;
    }

    //删除节点的方法
    //规定：如果删除的节点是叶子节点，则删除该节点，如果删除的节点是非叶子节点，则删除该子树
    public void DelNode(int num){
        //如果当前结点的左子结点不为空，并且左子结点就是要删除结点，就将this.left=null;并且就返回(结束递归删除)
        if (this.left != null && this.left.num == num){
            this.left = null;
            return;
        }
        //如果当前结点的右子结点不为空，并且右子结点就是要删除结点，就将this.right=null;并且就返回(结束递归删除)
        if (this.right != null && this.right.num == num){
            this.right = null;
            return;
        }
        //我们就需要向左子树进行递归删除
        if (this.left != null){
            this.left.DelNode(num);
        }
        //则应当向右子树进行递归删除
        if (this.right != null){
            this.right.DelNode(num);
        }
    }
}