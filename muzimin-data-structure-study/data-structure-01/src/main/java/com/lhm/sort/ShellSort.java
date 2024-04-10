package com.lhm.sort;

import java.util.Arrays;

/**
 * @program:Data_Structure
 * @package:com.lhm.sort
 * @filename:ShellSort.java
 * @create:2019.10.24.09:47:50
 * @auther:李煌民
 * @description:.希尔排序
 **/
public class ShellSort {
    public static void main(String[] args) {
        int[] arr = {9,7,5,8,4,3,6,2,1,0};
//        shellSortUseChange(arr);
        shellSortUseMove(arr);
        System.out.println(Arrays.toString(arr));


    }

    //使用元素交换的形式来进行希尔排序
    public static void shellSortUseChange(int[] arr){
        int temp = 0;
        for (int gap = arr.length/2;gap > 0;gap /= 2){
            for (int i = gap;i < arr.length;i++){
                //遍历各组中所有的元素，共有gap组，每组两个元素，步长gap
                for (int j = i - gap;j >= 0;j -= gap){
                    //如果当前元素大于加上步长后的那个元素，则交换
                    if (arr[j] > arr[j + gap]){
                        temp = arr[j];
                        arr[j] = arr[j + gap];
                        arr[j + gap] = temp;
                    }
                }
            }
        }
    }

    //使用元素移位的方式进行希尔排序，即希尔排序中使用插入排序
    public static void shellSortUseMove(int[] arr){
        //增量gap，并逐步的缩小增量
        for (int gap = arr.length / 2; gap > 0 ; gap /= 2) {
            //从第gap个元素，逐个对其所在组进行直接插入排序
            for (int i = gap; i < arr.length; i++) {
                int j = i;
                int temp = arr[j];
                if (arr[j] < arr[j - gap]){
                    while (j - gap >= 0 &&temp < arr[j - gap]){
                        //移动
                        arr[j] = arr[j - gap];
                        j -= gap;
                    }
                    //当退出while循环之后，就给temp找到了插入的位置
                    arr[j] = temp;
                }
            }
        }
    }
}
