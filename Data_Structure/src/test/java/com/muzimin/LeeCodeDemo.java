package com.muzimin;

import java.util.Arrays;

/**
 * @author : 李煌民
 * @date : 2021-09-08 22:09
 **/
public class LeeCodeDemo {
    public static void main(String[] args) {
        int[] a = {2, 3, 4};
        System.out.println(Arrays.toString(twoSum(a, 6)));
    }

    public static int[] twoSum(int[] nums, int target) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    int[] a = {i, j};

                    long endTime = System.currentTimeMillis();
                    System.out.println(endTime - start);
                    return a;
                }
            }
        }

        return null;
    }
}
