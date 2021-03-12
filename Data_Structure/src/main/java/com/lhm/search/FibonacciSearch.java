package com.lhm.search;

import java.util.Arrays;

/**
 * @author : 李煌民
 * @date : 2019-10-26 13:46
 * <h3>Data_Structure</h3>
 * <p>斐波那契查找</p>
 **/
public class FibonacciSearch {
    public static void main(String[] args) {
        int[] arr = {1,8,10,89,1000,1234};
        System.out.println(fibonacciSearch(arr,10));
    }

    //因为我们后面用到mid = low + F(k-1)-1，需要使用到斐波那契数列，因此我们需要先获取一个斐波那契数列
    //非递归方法得到一个斐波那契数列
    public static int[] fib(int maxSize){
        int[] f = new int[maxSize];
        f[0] = 1;
        f[1] = 1;
        for (int i = 2;i < maxSize;i++){
            f[i] = f[i-1] + f[i - 2];
        }
        return f;
    }
    //编写斐波那契查找算法，使用非递归的方式写
    public static int fibonacciSearch(int[] arr,int findkey){
        int low = 0;
        int high = arr.length - 1;
        //表示斐波那契数列数值的下标
        int k = 0;
        //存放mid值
        int mid = 0;
        //获取斐波那契数列
        int[] f = fib(20);
        //获取到斐波那契数列的下标
        while (high > f[k] -1){
            k++;
        }
        //因为f[k]的值可能大于a的长度，因此我们需要使用Arrays类，构成一个新的数组，并指向temp[]
        //不足的部分会使用0填充
        int[] temp = Arrays.copyOf(arr,f[k]);

        //使用arr中最后一个数来填充temp
        for (int i = high + 1; i < temp.length; i++) {
            temp[i] = arr[high];
        }

        //使用whil来循环处理，找到我们的数key
        while (low <= high){
            mid = low + f[k-1] -1;
            if (findkey < temp[mid]){
                //继续在数组的前面（左边）查找
                high = mid -1;
                /*
                为什么是k--，因为前面有f[k-1]个元素，所以可以继续拆分f[k-1] = f[k-2] + f[k-3]
                即在f[k-1]的前面继续查找k--
                即下次循环mid= f[k-1-1]-1
                 */
                k--;
            }else if (findkey > temp[mid]){
                //继续在数组的后面（右边）查找
                low = mid + 1;
                /*
                为什么是k -= 2,因为后面我们有f[k - 2]个元素，所以可以继续拆分f[k-2] = f[k-3] + f[k-4]
                即在f[k-2]的前面进行查找k-=2
                即下次循环mid = f[k-1-2]-1
                 */
                k -= 2;
            }else {
                //找到，需要确定，返回的是哪个坐标
                if (mid <= high){
                    return mid;
                }else {
                    return high;
                }
            }
        }
        return -1;
    }
}
