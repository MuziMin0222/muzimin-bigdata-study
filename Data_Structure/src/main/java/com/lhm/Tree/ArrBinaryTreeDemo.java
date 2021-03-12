package com.lhm.Tree;

/**
 * @author : 李煌民
 * @date : 2019-10-29 11:14
 * <h3>Data_Structure</h3>
 * <p>顺序二叉树的遍历</p>
 * 顺序存储二叉树的特点：
 * 1、顺序二叉树只考虑完全二叉树
 * 2、第n个元素的左子节点为2*n+1
 * 3、第n个元素的右子节点为2*n+2
 * 4、第n个元素的父节点为(n-1)/2
 * 5、n表示二叉树的第几个元素(从0开始)
 **/
public class ArrBinaryTreeDemo {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7};
        ArrBinaryTree abt = new ArrBinaryTree(arr);
        abt.preOrder();
        System.out.println("====================");
        abt.infixOrder(0);
        System.out.println("--------------------");
        abt.postOrder(0);
    }
}

//创建一个顺序二叉树
class ArrBinaryTree{
    private int[] arr;//存储数据节点的数组

    public ArrBinaryTree(int[] arr){
        this.arr = arr;
    }

    //重载前序遍历方法
    public void preOrder(){
        preOrder(0);
    }
    //前序遍历的方法,index是数组的下标
    public void preOrder(int index){
        //如果数组为空，或者数组里面没有数据
        if (arr == null || arr.length ==0){
            System.out.println("数组为空，或者数组没有节点数据");
        }
        //输出当前元素
        System.out.print(arr[index] + "\t");
        //向左递归遍历
        if ((index * 2 + 1) < arr.length){
            preOrder(2 * index +1);
        }
        //向右递归遍历
        if ((index * 2 + 2) < arr.length){
            preOrder(2 * index + 2);
        }
    }

    //中序遍历的方法
    public void infixOrder(int index){
        //如果数组为空，或者数组里面没有数据
        if (arr == null || arr.length ==0){
            System.out.println("数组为空，或者数组没有节点数据");
        }
        //向左递归遍历
        if ((index * 2 + 1) < arr.length){
            infixOrder(2 * index + 1);
        }
        //输出当前元素
        System.out.print(arr[index] + "\t");
        //向右递归遍历
        if ((index * 2 + 2) < arr.length ) {
            infixOrder(2 * index + 2);
        }
    }

    //后序遍历的方法
    public void postOrder(int index){
        //如果数组为空，或者数组里面没有数据
        if (arr == null || arr.length ==0){
            System.out.println("数组为空，或者数组没有节点数据");
        }
        //向左递归遍历
        if ((index * 2 + 1) < arr.length){
            postOrder(2 * index + 1);
        }
        //向右递归遍历
        if ((index * 2 + 2) < arr.length ) {
            postOrder(2 * index + 2);
        }
        //输出当前元素
        System.out.print(arr[index] + "\t");
    }
}
