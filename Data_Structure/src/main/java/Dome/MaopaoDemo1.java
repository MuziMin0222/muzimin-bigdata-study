package Dome;

import java.util.Arrays;

/**
 * @author : 李煌民
 * @date : 2019-12-01 19:56
 * 冒泡排序,时间复杂度是O(n)
 **/
public class MaopaoDemo1 {
    public static void main(String[] args) {
        //定义一个标示
        int[] arr = {78,9,6,8,3};
        MP1(arr);
        print_arr(arr);
        System.out.println("==============================");

        MP2(arr);
        print_arr(arr);
    }

    public static void print_arr(int[] arr){
        for (int i : arr) {
            System.out.print(i + "\t");
        }
    }

    public static void MP1(int[] arr){
        //定义一个标示
        boolean flag = false;

        //定义一个辅助变量
        int temp = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]){
                    temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                    //将标示符转为true
                    flag = true;
                }

                if (!flag) {
                    break;
                }
            }

        }
    }

    public static void MP2(int[] arr){
        int temp = 0;
        boolean flag = false;

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]){
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    flag = true;
                }

                if (!flag){
                    break;
                }
            }
        }
    }
}
