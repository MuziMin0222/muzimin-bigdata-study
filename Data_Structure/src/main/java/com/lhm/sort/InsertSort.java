package com.lhm.sort;

import java.util.Arrays;

/**
 * @program:Data_Structure
 * @package:com.lhm.sort
 * @filename:InsertSort.java
 * @create:2019.10.24.08:53:05
 * @auther:李煌民
 * @description:.插入排序
 **/
public class InsertSort {
    public static void main(String[] args) {
        int[] arr = {3,2,4,1,5};
        for (int i = 1; i < arr.length; i++) {
            //定义待插入的数字
            int insertValue = arr[i];
            //待插入的数字的前一个数字的索引
            int insertIndex = i - 1;
            /*
            给insertValue找到插入的位置
            insertIndex >= 0 保证在个insertVal找到插入位置，不越界
            insertValue < arr[insertIndex] 表示该待插入的数据还没有找到正确的位置
             */
            while (insertIndex >= 0 && insertValue < arr[insertIndex]){
                arr[insertIndex + 1] = arr[insertIndex];
                insertIndex--;
            }
            //当退出while循环时，说明插入的位置找到，insertIndex + 1
            arr[insertIndex + 1] = insertValue;
        }

        System.out.println(Arrays.toString(arr));
    }
}
