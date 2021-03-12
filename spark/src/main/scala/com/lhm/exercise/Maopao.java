package com.lhm.exercise;

/**
 * @author : 李煌民
 * @date : 2019-11-26 12:15
 * 冒泡排序
 **/
public class Maopao {
    public static void main(String[] args) {
        float a[] = {6,5,1,9,7};
        float t;
        int step,i;
        for (step = 4;step > 0;step--){
            for (i = 0;i < step;i++){
                if (a[i] > a[i + 1]){
                    t = a[i];
                    a[i] = a[i+1];
                    a[i+1] = t;
                }
            }
        }

        for (int i1 = 0; i1 < a.length; i1++) {
            System.out.println(a[i1]);
        }
    }
}
