package leetcode.src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author cuilihuan
 * @data 2020/11/28 21:03
 */
public class Problem_0046_全排列_递归_回溯 {
    public static void main(String[] args) {

    }


    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        if(nums == null && nums.length == 0)
            return list;
        process(list, nums, 0);
        return list;
    }

    private void process(List<List<Integer>> list, int[] nums, int index) {
        if(index == nums.length){
            List<Integer> cur = new ArrayList<>();
            for (int i = 0; i < nums.length; i++) {
                cur.add(nums[i]);
            }
            list.add(cur);
            return;
        }

        for (int i = index; i < nums.length; i++) {
            swap(nums, index, i);
            process(list,nums,index+1);
            swap(nums, i, index);
        }
    }

    private void swap(int[] nums, int i, int index) {
        int temp = nums[i];
        nums[i] = nums[index];
        nums[index] = temp;
    }
    // 回溯
    public List<List<Integer>> permute2(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        int[] visited = new int[nums.length];
        backtrack(res, nums, new ArrayList<Integer>(), visited);
        return res;
    }

    private void backtrack(List<List<Integer>> res, int[] nums, ArrayList<Integer> tmp, int[] visited) {
        if (tmp.size() == nums.length) {
            res.add(new ArrayList<>(tmp));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (visited[i] == 1) continue;
            visited[i] = 1;
            tmp.add(nums[i]);
            backtrack(res, nums, tmp, visited);
            visited[i] = 0;
            tmp.remove(tmp.size() - 1);
        }
    }
}
