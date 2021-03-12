package Dome;

import java.util.Arrays;

/**
 * @author : 李煌民
 * @date : 2019-12-01 20:32
 * 插入排序
 **/
public class InsertSort {
    public static void main(String[] args) {
        int[] arr = {78,89,45,65};
        is1(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void is1(int[] arr){
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            int min = arr[i];
            for (int j = i + 1; j < arr.length;j++){
                if (min > arr[j]){
                    min = arr[j];
                    minIndex = j;
                }
            }

            if(minIndex != i){
                arr[minIndex] = arr[i];
                arr[i] = min;
            }
        }
    }
}
