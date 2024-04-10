package com.lhm.search;

/**
 * @author : 李煌民
 * @date : 2019-10-26 11:53
 * <h3>Data_Structure</h3>
 * <p>插值查找的算法</p>
 **/
public class InsertSearch {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,8,9};
        int index = insertSearch(arr, 0, arr.length - 1, 2);
        System.out.println(index);
    }

    //插值查找的算法，要求数组是一个有序的
    public static int insertSearch(int[] arr,int left,int right,int findval){
        if (left > right ||findval < arr[0] || findval > arr[arr.length - 1]){
            return -1;
        }

        //求出mid
        int mid = left + (right + left) * (findval - arr[left])/(arr[right] -arr[left]);
        int midVal = arr[mid];

        if (findval < midVal){
            //左递归
            return insertSearch(arr,left,mid -1,findval);
        }else if (findval > midVal){
            //右递归
            return insertSearch(arr,mid + 1,right,findval);
        }else {
            return mid;
        }
    }
}
