package com.lhm.leecode.easy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : 李煌民
 * @date : 2021-09-08 22:19
 * 两数之和
 **/
public class TwoSum {
    public int[] twoSum(int[] nums, int target) {
        //构建哈希表，元素映射到对应的索引
        Map<Integer,Integer> map = new HashMap<Integer,Integer>();
        for(int i = 0;i < nums.length;i++){
            map.put(nums[i],i);
        }
        //遍历整个数组，找到对应值other
        for(int i = 0;i < nums.length;i++){
            int other = target - nums[i];
            //如果other存在且不为nums[i]本身，则成立
            if(map.containsKey(other) && map.get(other) != i){
                return new int[]{i,map.get(other)};
            }
        }
        return new int[]{-1,-1};
    }

    public int[] MyTwoSum( int[] nums, int target) {
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
