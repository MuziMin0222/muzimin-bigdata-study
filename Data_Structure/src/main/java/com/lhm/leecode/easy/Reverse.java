package com.lhm.leecode.easy;


/**
 * @author : 李煌民
 * @date : 2021-09-08 22:23
 * 整数反转
 **/
public class Reverse {
    private int reverse(int x) {
        int ans = 0;
        while (x != 0) {
            int pop = x % 10;
            if (ans > Integer.MAX_VALUE / 10 || (ans == Integer.MAX_VALUE / 10 && pop > 7)) {
                return 0;
            }
            if (ans < Integer.MIN_VALUE / 10 || (ans == Integer.MIN_VALUE / 10 && pop < -8)) {
                return 0;
            }
            ans = ans * 10 + pop;
            x /= 10;
        }
        return ans;
    }

    public int reverse_1(int x) {
        int res = 0;
        while(x!=0) {
            //每次取末尾数字
            int tmp = x%10;
            //判断是否 大于 最大32位整数
            if (res>214748364 || (res==214748364 && tmp>7)) {
                return 0;
            }
            //判断是否 小于 最小32位整数
            if (res<-214748364 || (res==-214748364 && tmp<-8)) {
                return 0;
            }
            res = res*10 + tmp;
            x /= 10;
        }
        return res;
    }

    public static int reverse_2(int x) {
        int number=0;
        boolean flag=true;
        if(x<0){
            flag=false;
            x=-x;
        }
        while (x>0){
            if((number>(((1<<31)-1)/10))){
                return 0;
            }
            number=x%10+number*10;
            x=x/10;
        }
        return flag? number: -number;
    }



    public static void main(String[] args) {
        Reverse reverse = new Reverse();

        System.out.println(reverse.reverse(12345678));
        System.out.println(reverse.reverse_1(87654321));
    }

}
