package com.lhm.Tree;

import java.util.Arrays;

/**
 * @author : 李煌民
 * @date : 2019-11-06 09:39
 * <h3>Data_Structure</h3>
 * <p>堆排序算法</p>
 **/
public class DuiSortDemo {
    public static void main(String[] args) {
        int[] arr = {4,6,8,5,9};

        DuiSort(arr);

        System.out.println(Arrays.toString(arr));

    }
    //堆排序
    public static void DuiSort(int[] arr){
        int temp = 0;

        //将一个无序序列构建成一个堆，根据升序降序需求选择大顶堆还是小顶堆
        for (int i = arr.length/2 -1; i >= 0; i--) {
            adjustDui(arr,i,arr.length);
        }

        //将堆顶元素与末尾元素交换，将最大元素沉到堆的末端
        //重新调整结构，使其满足堆定义，然后继续交换堆顶元素和当前末尾元素，反复执行调整+交换步骤，直到真个序列有序
        for (int i = arr.length-1; i > 0 ; i--) {
            //交换
            temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;
            adjustDui(arr,0,i);
        }
    }

    /**
     * //将一个完全二叉树构建成一个大顶堆
     * @param arr  要排序的数组
     * @param i     表示非叶子节点在数组中的索引
     * @param length    表示对多少个元素进行调整，即数组的长度
     */
    public static void adjustDui(int[] arr,int i,int length){
        //取出当前元素的值，保存至临时变量
        int temp = arr[i];

        //k = i * 2 + 1 指的是当前节点的左子节点
        for (int k = i * 2 + 1;k < length; k = k * 2 + 1){
            if (k + 1 < length && arr[k] < arr[k+1]){
                //说明左子节点的值小于右子节点的值
                k++;//此时的k指向右子节点
            }
            if (arr[k] > temp){//如果子节点大于父节点
                //把较大的值赋给当前节点
                arr[i] = arr[k];
                //i指向k，即i指向较大值的子节点的位置，继续循环比较
                i = k;
            }else {
                break;
            }
        }

        //当for循环结束后，我们已经将以i为父节点的树的最大值，放在了该子树位置的最顶
        //将temp放在调整后的位置
        arr[i] = temp;
    }
}
