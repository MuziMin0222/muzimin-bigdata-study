package com.lhm;

import java.util.Arrays;

/*
有一个已经排好序的数组。现输入一个数，要求按原来的规律将它插入数组中
 */
public class shuzuDemo {
    public static void main(String[] args) {
        int[] arr = {7,8,1,4,3,4,6,7,9,0};
        //排序前
        for (int i : arr) {
            System.out.print(i + "\t");
        }
        System.out.println();
        //排序后
        Arrays.sort(arr);
        for (int i : arr) {
            System.out.print(i + "\t");
        }
        System.out.println();

        int[] ints = method(arr, 2);
        for (int i = 0; i < ints.length; i++) {
            System.out.print(ints[i] + "\t");
        }
    }

    public static int[] method(int[] arr,int n){
        int[] newArr = new int[arr.length+1];
        //如果数字n大于排序好的数组中的最后一个，那么将n放在新数组的最后一位
        if(n > arr[arr.length-1]){
            newArr[newArr.length - 1] = n;
            //其他数字按顺序插入新数组
            for(int i = 0;i < arr.length;i++){
                newArr[i] = arr[i];
            }
        }else {
            //找到原来数组中大于n的第一位，将n放在此位置
            for (int i = 0; i < arr.length; i++) {
                if (n < arr[i]){
                    newArr[i] = n;
                    //给之前没变的数给新数组赋值
                    for (int j = 0; j < i; j++) {
                        newArr[j] = arr[j];
                    }
                    //其他数字向后挪一位
                    for (int k = i;k < arr.length;k++){
                        newArr[k + 1] = arr[k];
                    }
                    break;
                }
            }
        }
        return newArr;
    }
}
