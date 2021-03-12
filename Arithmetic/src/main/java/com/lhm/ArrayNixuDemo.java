package com.lhm;
/*
将一个数组逆序输出
 */
public class ArrayNixuDemo {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5};
        method(arr);
        for (int i : arr) {
            System.out.print(i + "\t");
        }

    }
    public static void method(int[] arr){
        for (int i = 0; i < arr.length/2; i++) {
            int temp = arr[i];
            arr[i] = arr[arr.length -1 -i];
            arr[arr.length - 1 - i] = temp;
        }
    }
}
