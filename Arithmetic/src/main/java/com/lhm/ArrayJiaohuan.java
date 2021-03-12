package com.lhm;

import java.lang.management.MonitorInfo;

/*
输入数组，最大的与第一个元素交换，最小的与最后一个元素交换，输出数组
 */
public class ArrayJiaohuan {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5};
        int Max_Index = 0;
        int max = arr[Max_Index];
        for (int i = 0; i < arr.length; i++) {
            if (max < arr[i]){
                max = arr[i];
                Max_Index = i;
            }
        }
        System.out.println("最大值：" + max + "索引是：" + Max_Index);
        int temp = arr[0];
        arr[0] = arr[Max_Index];
        arr[Max_Index] = temp;
        for (int i : arr) {
            System.out.print(i + "\t");
        }
        System.out.println();

        int Min_Index = 0;
        int min = arr[Min_Index];
        for (int i = 0; i < arr.length; i++) {
            if(min > arr[i]){
                min = arr[i];
                Min_Index = i;
            }
        }
        System.out.println("最小值：" + min + "索引是：" + Min_Index);
        int temp1 = arr[arr.length -1];
        arr[arr.length -1] = arr[Min_Index];
        arr[Min_Index] = temp1;

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "\t");
        }
    }
}
