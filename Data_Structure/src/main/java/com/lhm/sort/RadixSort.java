package com.lhm.sort;

import java.util.Arrays;

/**
 * <h3>Data_Structure</h3>
 * <p>基数排序</p>
 *
 * @author : 李煌民
 * @date : 2019-10-25 10:24
 **/
public class RadixSort {
    public static void main(String[] args) {
        int[] arr = {4,5,3,4,4,1,6,7,4,3,2};
        radixsort(arr);
        System.out.println(Arrays.toString(arr));
    }

    //基数排序的方法
    public static void radixsort(int[] arr){
        //第一步：得到该数组中最大数的位数
        //假设第一个数字是最大数
        int max = arr[0];
        for (int i = 0;i < arr.length;i++){
            if (arr[i] > max){
                max = arr[i];
            }
        }
        //得到最大数的位数
        int max_length = (max + "").length();

        //定义一个二维数组，表示十个捅，每个桶都是一个一维数组
        /*
        1、二维数组包含十个一维数组
        2、为了防止在放入数字的时候，数据溢出，每一个二维数组中一维数组(桶)的大小定为arr.length，空间换时间
         */
        int[][] bucket = new int[10][arr.length];

        //为了记录每个桶中，实际存放了多少个数据，我们定义一个一维数组来记录各个桶的每次放入的数据个数
        //比如bucket[0]桶中的数据个数就是bucketElementCounts[0]
        int[] bucketElementCounts = new int[10];

        for (int i = 0, n = 1; i < max_length; i++,n *= 10) {
            //针对每个元素的对应位进行排序处理，第一轮处理个位，第二轮处理十位。。。。
            for (int j = 0; j < arr.length; j++) {
                //取出每个元素对应位的值
                int digitOfElement = arr[j] / n % 10;
                //放入到对应的桶中
                bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
                bucketElementCounts[digitOfElement]++;
            }
            //按照这个桶的顺序(一维数组的下标依次取出数据，放入原来的数组)
            int index = 0;
            //依次遍历每一个桶，并将桶中的数据，放入到原来的数组中
            for (int j = 0; j < bucketElementCounts.length; j++) {
                //如果桶中，有数据，我们才放到原数组
                if (bucketElementCounts[j] != 0){
                    //循环该桶，即第j个桶，放入到原数组
                    for (int k = 0; k < bucketElementCounts[j]; k++) {
                        //取出元素放到arr中
                        arr[index++] = bucket[j][k];
                    }
                }
                //第k轮处理后，将每个记录桶中数据的数组置0
                bucketElementCounts[j] = 0;
            }
        }
    }
}
