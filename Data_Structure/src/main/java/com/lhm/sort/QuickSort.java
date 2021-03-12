package com.lhm.sort;

import java.util.Arrays;

/**
 * @program:Data_Structure
 * @package:com.lhm.sort
 * @filename:QuickSort.java
 * @create:2019.10.24.18:41:42
 * @auther:李煌民
 * @description:.快速排序
 **/
public class QuickSort {
    public static void main(String[] args) {
        int[] arr = {-1,102,0,-100,8786,222};
        quicksort(arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    public static void quicksort(int[] arr,int left,int right){
        //左下标
        int l = left;
        //右下标
        int r = right;
        //中轴值
        int pivot = arr[(l + r)/2];
        //临时变量
        int temp = 0;
        //while循环的目的是让比pivot值小的放在中轴值的左边，比pivot大的放右边
        while (l < r){
            //在pivot的左边一直找，找到大于等于pivot的值，才退出
            while (arr[l] < pivot){
                l += 1;
            }
            //在pivot的右边一直找，找到小于等于pivot的值，才退出
            while (arr[r] > pivot){
                r -= 1;
            }
            //如果l >= r 说明pivot左右两边的值，左边都是小于pivot的值，右边都是大于pivot的值,退出while循环
            if (l >= r){
                break;
            }
            //将找到不符合规定的值进行交换
            temp =  arr[l];
            arr[l] = arr[r];
            arr[r] = temp;

            //如果交换完，就发现这个arr[l] == pivot,将r前移
            if (arr[l] == pivot){
                r -= 1;
            }
            //如果交换完，就发现这个arr[r] == pivot,将l后移
            if (arr[r] == pivot){
                l += 1;
            }

            //如果l == r,就必须l++，r--，否则就出现栈溢出
            if (l == r){
                l += 1;
                r -= 1;
            }
            //向左递归
            if (left < r){
                quicksort(arr,left,r);
            }
            //向右递归
            if (right > l){
                quicksort(arr,l,right);
            }
        }
    }
}
