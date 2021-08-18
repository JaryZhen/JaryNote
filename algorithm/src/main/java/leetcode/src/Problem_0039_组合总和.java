package leetcode.src;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author cuilihuan
 * @data 2020/11/25 16:07
 */
public class Problem_0039_组合总和 {
    public static void main(String[] args) {
        System.out.println(new Problem_0039_组合总和().combinationSum(new int[]{2, 6}, 6));
    }
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        if (candidates == null || candidates.length == 0)
            return null;
        List<List<Integer>> list = new LinkedList<>();
        List<Integer> cur = new LinkedList<>();
        process(list, candidates, target, cur, 0, 0);
        return list;

    }

    public void process(List<List<Integer>> list, int[] candidates, int target, List<Integer> cur, int index, int sum) {
        if (sum == target) {
            list.add(cur);
            return;
        }
        if (index == candidates.length || sum > target)
            return;
        List<Integer> now = new LinkedList<>(cur);
        for (int i = 0; candidates[index] * i + sum <= target; i++) {
            if (i == 0) {
                process(list, candidates, target, now, index + 1, sum);
                continue;
            }
            now.add(candidates[index]);
            process(list, candidates, target, now, index + 1, sum+candidates[index]*i);
        }
    }


    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        processCS(candidates, 0, target, res, new ArrayList<>(), 0);
        return res;
    }

    private void processCS(int[] candidates, int index, int target, List<List<Integer>> lists, List<Integer> list, int sum) {
        if (index >= candidates.length)
            return;
        if (sum == target) {
            lists.add(new ArrayList<>(list));
            return;
        }
        if (sum > target) return;

        list.add(candidates[index]);
        sum = sum + candidates[index];
        processCS(candidates, index, target, lists, list, sum);
        list.remove(list.size() - 1);
        sum = sum - candidates[index];
        processCS(candidates, index + 1, target, lists, list, sum);
    }
}
