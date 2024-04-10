package com.lhm.sort;

import java.util.Arrays;

/**
 * @program:Data_Structure
 * @package:com.lhm.sort
 * @filename:MergeSort.java
 * @create:2019.10.24.19:38:05
 * @auther:李煌民
 * @description:.归并排序
 **/
public class MergeSort {
    public static void main(String[] args) {
        int[] arr = {2,1,4,8};
        int[] temp = new int[arr.length];
        mergesort(arr,0,arr.length-1,temp);
        System.out.println(Arrays.toString(arr));
    }

    //分 + 和的方法
    public static void mergesort(int[] arr,int left,int right,int[] temp){
        if (left < right){
            //中间索引
            int mid = (left + right) / 2;
            //向左递归进行分解
            mergesort(arr,left,mid,temp);
            //向右递归分解
            mergesort(arr,mid + 1,right,temp);
            //合并
            merge(arr,temp,left,mid,right);
        }
    }

    /**
     *该方法用于做合并
     * @param arr   排序的原始数组
     * @param temp  做中转的临时数组
     * @param left  左边有序序列的初始索引
     * @param mid   中间索引
     * @param right 右边有序序列的初始索引
     */
    public static void merge(int[] arr,int[] temp,int left,int mid,int right){
        //初始化l，左边有序序列的初始索引
        int l = left;
        //初始化r，右边有序序列的初始索引
        int r = mid + 1;
        //指向temp数组的当前索引
        int t = 0;
        //第一步：先把左右两边(有序)的数据按照规则填充到temp数组，直到左右两边的有序序列，有一边处理完为止
        while (l <= mid && r <= right){
            //如果左边的有序序列的当前元素，小于等于右边有序序列的当前元素，就将左边的当前元素，拷贝到temp数组中
            if (arr[l] <= arr[r]){
                temp[t] = arr[l];
                t +=1;
                l +=1;
            }else {//反之：将右边有序序列的当前元素，填充到temp数组
                temp[t] = arr[r];
                t += 1;
                r += 1;
            }
        }
        //第二步：把有剩余数据的一边的数据依次全部填充到temp
        //左边的有序序列还有剩余的元素，就全部填充到temp中
        while (l <= mid){
            temp[t] = arr[l];
            t += 1;
            l += 1;
        }
        //右边的有序序列还有剩余的元素，就全部填充到temp中
        while (r <= right){
            temp[t] = arr[r];
            t += 1;
            r += 1;
        }

        //第三步：将temp数组的元素拷贝到arr,注意，并不是每次都是拷贝所有的
        t = 0;
        int templeft = left;
        //第一次合并templeft = 0.right = 1，第二次合并 templeft = 2，right = 3.。。。
        while (templeft <= right){
            arr[templeft] = temp[t];
            t += 1;
            templeft += 1;
        }
    }
}
