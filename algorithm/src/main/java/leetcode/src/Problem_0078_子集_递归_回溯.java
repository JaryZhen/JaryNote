package leetcode.src;

import java.util.*;

/**
 * @author cuilihuan
 * @data 2020/12/5 14:32
 */
public class Problem_0078_子集_递归_回溯 {
    // 递归
    List<Integer> t = new ArrayList<Integer>();
    List<List<Integer>> ans = new ArrayList<List<Integer>>();
    public List<List<Integer>> subsets2(int[] nums) {
        dfs(0, nums);
        return ans;
    }

    public void dfs(int cur, int[] nums) {
        System.out.println(t+ " ");
        if (cur == nums.length) {
            ans.add(new ArrayList<Integer>(t));
            return;
        }
        t.add(nums[cur]);
        dfs(cur + 1, nums);
        t.remove(t.size() - 1);
        dfs(cur + 1, nums);
    }

    // 回溯
    private Deque<Integer> curAns = new ArrayDeque<>();
    private int len = 0;
    private int[] nums;

    // 取所有的节点 甚至包括根节点 所以一开头ans.add最好了
    private void backTracking(int startIndex){
        ans.add(new ArrayList(curAns));

        for(int i = startIndex ; i < len ; ++i){
            curAns.offerLast(nums[i]);
            backTracking(i + 1);
            curAns.pollLast();
        }
    }
    public List<List<Integer>> subsets3(int[] nums) {
        this.len = nums.length;
        this.nums = nums;

        backTracking(0);
        return ans;
    }

}
