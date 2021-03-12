package com.lhm;
/*
数组的排序
 */
public class ArrayPaiXu {
    public static void main(String[] args) {

    }

    //冒泡排序
    /*
    简单来说，冒泡排序就是重复地走访过要排序的数列，一次比较两个元素，如果他们的顺序错误就把他们交换过来。
    走访数列的工作是重复地进行直到没有再需要交换，也就是说该数列已经排序完成。
     */
    public static void MaoPao(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int x = 0; x < arr.length - 1 - i; x++) {
                if (arr[x] > arr[x + 1]) {
                    int temp = arr[x];
                    arr[x] = arr[x + 1];
                    arr[x + 1] = temp;
                }
            }
        }
    }

    //选择排序
    /*
    先找到最小元素所在位置的索引，然后将该元素与第一位上的元素进行交换。
     */
    public static void XuanZhePaiXu(int[] arr){
        for(int i=0;i<arr.length;i++) {
            int teme=i;
            //将数组中从i开始的最小的元素所在位置的索引赋值给teme
            for(int j=i;j<arr.length;j++) {
                if(arr[j]<arr[teme]) {
                    teme=j;
                }
            }
            //上面获取了数组中从i开始的最小值的位置索引为teme，利用该索引将第i位上的元素与其进行交换
            int temp1=arr[i];
            arr[i]=arr[teme];
            arr[teme]=temp1;
        }
    }

    //反转排序：将原数组按逆序排列
    public static void FanZhuangPaiXu(int[] arr){
        //将数组第i位上的元素与第arr.length-i-1位上的元素进行交换
        for(int i=0;i<arr.length/2;i++) {
            int tp=arr[i];
            arr[i]=arr[arr.length-i-1];
            arr[arr.length-i-1]=tp;
        }
    }

    //插入排序
    //将大的放后面
    public static void ChaRuPaiXu(int[] arr){
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j - 1] > arr[j]) {//大的放后面
                    int tmp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = tmp;
                }
            }
        }
    }
}