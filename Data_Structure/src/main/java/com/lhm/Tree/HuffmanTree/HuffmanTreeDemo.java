package com.lhm.Tree.HuffmanTree;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author : 李煌民
 * @date : 2019-11-09 10:53
 * <h3>Data_Structure</h3>
 * <p>哈夫曼树</p>
 **/
public class HuffmanTreeDemo {
    public static void main(String[] args) {
        int[] arr = {13,7,8,3};

        Node node = createHuffmanTree(arr);

        preOrder(node);

    }

    //前序遍历
    public static void preOrder(Node node){
        if (node != null){
            node.preOrder();
        }else{
            System.out.println("空树，无法遍历");
        }
    }

    /**
     * 创建一颗哈夫曼树的方法
     * @param arr 需要创建哈夫曼树的数组
     * @return 创建好后的哈夫曼数的root节点
     */
    public static Node createHuffmanTree(int[] arr){
        //遍历arr数组，将arr数组每个元素构成一个node，将node放入到Arraylist中
        ArrayList<Node> list = new ArrayList<>();
        for (int i : arr) {
            list.add(new Node(i));
        }

        //做到最后，集合中只有一个根节点
        while (list.size() > 1){
            //对node节点进行排序
            Collections.sort(list);

            //取出根节点中权值最小的两颗二叉树，一个叶子节点看成一颗最简单的二叉树
            Node leftNode = list.get(0);
            Node rightNode = list.get(1);

            //构建一颗新的二叉树
            Node parent = new Node(leftNode.value + rightNode.value);
            parent.left = leftNode;
            parent.right = rightNode;

            //从Arraylist中删除已经处理过的二叉树
            list.remove(leftNode);
            list.remove(rightNode);

            //将parent加到list集合中
            list.add(parent);
        }

        //返回哈夫曼树的root节点
        return list.get(0);
    }
}

//创建一个树节点类,为了让node对象可以进行排序，实现comparable接口
class Node implements Comparable<Node>{
    //节点权值
    int value;
    //指向左子节点
    Node left;
    //指向右子节点
    Node right;

    //写一个前序遍历的方法
    public void preOrder(){
        //输出当前节点
        System.out.println(this);
        if (this.left != null){
            this.left.preOrder();
        }
        if (this.right != null){
            this.right.preOrder();
        }
    }

    public Node(int value){
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    @Override
    public int compareTo(Node o) {
        //让节点从小到大排序
        return this.value - o.value;
    }
}
