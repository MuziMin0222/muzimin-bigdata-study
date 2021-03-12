package com.lhm.sort;

import java.util.Arrays;

/**
 * @program:Data_Structure
 * @package:com.lhm.sort
 * @filename:BubbleSort.java
 * @create:2019.10.23.19:11:23
 * @auther:李煌民
 * @description:.冒泡排序的算法
 **/
public class BubbleSort {
    public static void main(String[] args) {
        //定义一个数组
        int[] arr = {78,9,6,8,3};

        //冒泡排序的时间复杂度O(n^2)
        int temp = 0;//定义临时变量
        //定义一个标示
        boolean flag = false;
        for (int i = 0;i < arr.length -1;i++){
            for (int j = 0; j < arr.length -1 -i; j++) {
                //如果后面的数大于前面的数，就换位子
                if (arr[j] > arr[j + 1]){
                    flag = true;
                    temp = arr[j];
                   arr[j] = arr[j + 1];
                   arr[j + 1] = temp;
               }
               if (!flag){
                   break;
               }
            }
        }

        System.out.println(Arrays.toString(arr));
    }
}
