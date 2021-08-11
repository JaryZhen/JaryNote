package leetcode.src;

import java.util.HashMap;

/**
 *
 */
public class Problem_0001_TwoSum {
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        int other = 0;
        for (int i = 0; i < nums.length; i++) {
            other = target - nums[i];
            if (hashMap.containsKey(other)) {
                return new int[]{hashMap.get(other), i};
            }
            hashMap.put(nums[i], i);
        }
        return new int[]{-1,-1};

    }

    public static int[] twoSum2(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap();
        int[] re = new int[2];
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                re = new int[]{i, map.get(nums[i])};
                break;
            } else map.put(target - nums[i], i);
        }
        return re;
    }
}
