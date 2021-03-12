package com.lhm.search;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 李煌民
 * @date : 2019-10-26 10:05
 * <h3>Data_Structure</h3>
 * <p>二分查找</p>
 **/
public class BinarySearch {
    public static void main(String[] args) {
        int[] arr = {1,2,3,6,7,9,9,9,9,9};
        int index = binarysearch(arr, 0, arr.length - 1, 9);
        List<Integer> list = binarysearch2(arr, 0, arr.length - 1, 9);
        System.out.println("集合:" + list);
        System.out.println("索引为：" + index);
    }

    //在使用二分查找前，数组必须是有序的
    public static int binarysearch(int[] arr,int left,int right,int findVal){
        //当left > right时，递归结束
        if (left > right){
            return -1;
        }

        int mid = (left + right) /2;
        int midVal = arr[mid];

        if (findVal > midVal){
            //向右递归
            return binarysearch(arr,mid + 1,right,findVal);
        }else if (findVal < midVal){
            //向左递归
            return binarysearch(arr,left,mid-1,findVal);
        }else {
            return mid;
        }
    }

    //使用二分查找，将一个数组中符合要查找的数字全部找出并添加到集合中
    /*
    思路分析：
        1、在找到mid的索引值，不要马上返回
        2、在mid索引值的左边扫描，将所有满足要查找的数的元素的下标，都加入到集合中
        3、在mid索引值的右边扫描，将所有满足要查找的数的元素的下标，都加入到集合中
        4、返回集合
     */
    public static List<Integer> binarysearch2(int[] arr, int left, int right, int findVal){
        //当left > right时，递归结束
        if (left > right){
            return new ArrayList<>();
        }

        int mid = (left + right) /2;
        int midVal = arr[mid];

        if (findVal > midVal){
            //向右递归
            return binarysearch2(arr,mid + 1,right,findVal);
        }else if (findVal < midVal){
            //向左递归
            return binarysearch2(arr,left,mid-1,findVal);
        }else {
            List<Integer> list = new ArrayList<>();
            //向左扫描
            int temp = mid -1;
            while (true){
                if (temp < 0 || arr[temp] != findVal){
                    //退出循环
                    break;
                }
                //否则，将temp放入到list集合中
                list.add(temp);
                //temp左移
                temp -= 1;
            }
            list.add(mid);

            //向右扫描
            temp = mid +1;
            while (true){
                if (temp > arr.length -1 || arr[temp] !=findVal){
                    //退出
                    break;
                }
                list.add(temp);
                //右移
                temp += 1;
            }
            return list;
        }
    }
}
