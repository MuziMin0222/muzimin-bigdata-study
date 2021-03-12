package com.lhm.search;

import javax.print.DocFlavor;

/**
 * @author : 李煌民
 * @date : 2019-10-26 09:41
 * <h3>Data_Structure</h3>
 * <p>线性查找</p>
 **/
public class SeqSearch {
    public static void main(String[] args) {
        int[] arr = {5,9,47,3,6,9};
        int seqsearch = seqsearch(arr, 3);
        System.out.println("数值下标为：" + seqsearch);
    }

    //这里的线性查找是找到一个满足条件的数字返回其下标即可
    public static int seqsearch(int[] arr,int value){
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value) {
                return i;
            }
        }
        return -1;
    }
}
