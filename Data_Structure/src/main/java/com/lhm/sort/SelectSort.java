package com.lhm.sort;

import java.util.Arrays;

/**
 * @program:Data_Structure
 * @package:com.lhm.sort
 * @filename:SelectSort.java
 * @create:2019.10.23.20:18:10
 * @auther:李煌民
 * @description:.选择排序的算法
 **/
public class SelectSort {
    public static void main(String[] args) {
        //定义一个数组
        int[] arr = {3,2,4,5,1};

        for (int i = 0; i < arr.length -1; i++) {
            int minIndex = i;
            int min = arr[i];
            for (int j = i + 1; j < arr.length; j++) {
                if (min > arr[j]){//说明假定的最小值不是最小值
                    min = arr[j];//重置min
                    minIndex = j;//重置minIndex
                }
            }

            //将最小值，放在前面
            if (minIndex != i){
                arr[minIndex] = arr[i];
                arr[i] = min;
            }
        }

        System.out.println(Arrays.toString(arr));
    }
}
