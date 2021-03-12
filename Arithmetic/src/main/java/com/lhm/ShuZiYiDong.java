package com.lhm;

import java.util.Arrays;

/*
有n个整数，使其前面各数顺序向后移m个位置，最后m个数变成最前面的m个数
 */
public class ShuZiYiDong {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7,8,9,10};
        method(arr,3);
        for (int i : arr) {
            System.out.print(i + "\t");
        }
    }
    public static int[] method(int[] arr,int m){
        int[] newArr = Arrays.copyOfRange(arr, arr.length - m, arr.length);

        for (int i = arr.length - 1; i >= m; i--) {
            arr[i] = arr[i-m];
        }
        for (int i = 0; i < newArr.length; i++) {
            arr[i] = newArr[i];
        }

        return arr;
    }
}
