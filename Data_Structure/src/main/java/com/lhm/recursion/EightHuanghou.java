package com.lhm.recursion;

/**
 * @program:Data_Structure
 * @package:com.lhm.recursion
 * @filename:EightHuanghou.java
 * @create:2019.10.23.08:54:07
 * @auther:李煌民
 * @description:. 八皇后问题
 **/
public class EightHuanghou  {
    //定义一个max表示共有多少个皇后
    int max = 8;
    //定义数组arr，保存皇后放置位置的结果
    int[] arr = new int[max];

    static int count = 0;

    public static void main(String[] args) {
        new EightHuanghou().check(0);
        System.out.println("共有：" + count + "种解法");
    }

    //编写一个方法，可以将皇后的摆放位置进行输出
    public void PrintArr(){
        count++;
        for (int i = 0; i < arr.length;i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    //编写一个方法，放置第n个皇后
    public void check(int n){
        if (n == max){
            //n = 8:其实就是将第八个皇后安放好
            PrintArr();
            return;
        }
        //依次放入皇后，并判断是否冲突
        for (int i = 0; i< max; i++){
            //先把当前这个皇后n，放到该行的第一列
            arr[n] = i;
            //判断当前放置第n个皇后到i列时，是否冲突
            if (judge(n)){//不冲突
                //接着放n+1个皇后，即开始递归
                check(n+1);
            }
            //如果冲突，就继续执行arr[n] = i;即将第n个皇后，放置在本行的后移的一个位置
        }
    }

    //查看我们放置第n个皇后，就去检测该皇后是否和前面已经摆放的皇后冲突
    public boolean judge(int n){
        for (int i = 0;i < n;i++){
            /*
            arr[i] == arr[n]:表示判断第n个皇后是否与前面的n-1个皇后在同一列
            Math.abs(n-i) == Math.abs(arr[n] - arr[i])：表示判断第n个皇后是否和第i个皇后在同一个斜线
            因为i在递增，所以不需要判断是否在同一行
             */
            if (arr[i] == arr[n] || Math.abs(n-i) == Math.abs(arr[n] - arr[i])){
                return false;
            }
        }
        return true;
    }
}
