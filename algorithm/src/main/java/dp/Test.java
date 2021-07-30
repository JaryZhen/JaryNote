package dp;

import bit.BitMap;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author: Jary
 * @Date: 2021/7/6 4:35 下午
 */
public class Test {


    public int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }
        int i1 = 1;
        int i2 = 2;
        for (int i = 3; i <= n; i++) {
            int temp = i1 + i2;
            i1 = i2;
            i2 = temp;
        }
        return i2;
    }

    /*
    给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
    子序列是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
    输入：nums = [10,9,2,5,3,7,101,18]
    输出：4
    解释：最长递增子序列是 [2,3,7,101]，因此长度为 4 。
     */
    public int lengthOfLIS(int[] nums) {
        int len = nums.length;
        if (len < 2) {
            return len;
        }
        int[] dp = new int[len];
        Arrays.fill(dp, 1);

        int res = dp[0];
        for (int i = 1; i < len; i++) {
            // 看以前的，比它小的，说明可以接在后面形成一个更长的子序列
            // int curMax = Integer.MIN_VALUE; 不能这样写，万一前面没有比自己小的，
            // 这个值就得不到更新
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[j] + 1, dp[i]);
                }
            }

            // 在遍历的时候同时找 dp 数组的最大值
            res = Math.max(res, dp[i]);
        }
        return res;
    }
    /*
    322. 零钱兑换
    给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1
    你可以认为每种硬币的数量是无限的。
    示例 1：

    输入：coins = [1, 2, 5], amount = 11
    输出：3
    解释：11 = 5 + 5 + 1
    示例 2：

    输入：coins = [2], amount = 3
    输出：-1
    示例 3：

    输入：coins = [1], amount = 0
    输出：0
    示例 4：

    输入：coins = [1], amount = 1
    输出：1
    示例 5：

    输入：coins = [1], amount = 2
    输出：2
     */

    public int coinChange(int[] coins, int amount) {
        int max = amount + 1;
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, max);
        dp[0] = 0;
        for (int target = 1; target <= amount; target++) {
            for (int j = 0; j < coins.length; j++) {
                if (coins[j] <= target) {
                    dp[target] = Math.min(dp[target], dp[target - coins[j]] + 1);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

    ;

    public int minDistance(String word1, String word2) {
        char[] w1 = word1.toCharArray();
        char[] w2 = word2.toCharArray();
        int[][] dp = new int[w1.length + 1][w2.length + 1];
        for (int i = 1; i < dp.length; i++) {
            dp[i][0] = i;
        }
        for (int i = 1; i < dp[0].length; i++) {
            dp[0][i] = i;
        }
        for (int i = 1; i < w1.length + 1; i++) {
            for (int j = 1; j < w2.length + 1; j++) {
                if (w1[i - 1] == w2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else
                    dp[i][j] = Math.min(Math.min(dp[i][j - 1], dp[i - 1][j]), dp[i - 1][j - 1]) + 1;//insert,delete update
            }
        }
        return dp[w1.length][w2.length];
    }

    /*
    200 岛屿问题
     */

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int nr = grid.length;
        int nc = grid[0].length;
        int num_islands = 0;
        int l = 0;
        for (int r = 0; r < nr; ++r) {
            for (int c = 0; c < nc; ++c) {
                if (grid[r][c] == '1') {
                    ++num_islands;
                    dfsMumLands(grid, r, c, l);
                }
            }
        }
        return num_islands;
    }

    public void dfsMumLands(char[][] grid, int i, int j, int l) {
        if (!isLand(grid, i, j))
            return;
        if (grid[i][j] != '1') return;
        grid[i][j] = '2';

        dfsMumLands(grid, i - 1, j, l);
        dfsMumLands(grid, i + 1, j, l);
        dfsMumLands(grid, i, j - 1, l);
        dfsMumLands(grid, i, j + 1, l);
    }
    public boolean isLand(char[][] grid, int i, int j) {
        return i >= 0 && i < grid.length && j >= 0 && j < grid[0].length;
    }

    public int numIslands2(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int nr = grid.length;
        int nc = grid[0].length;
        int num_islands = 0;

        for (int r = 0; r < nr; ++r) {
            for (int c = 0; c < nc; ++c) {
                if (grid[r][c] == '1') {
                    ++num_islands;
                    grid[r][c] = '0';
                    Queue<Integer> neighbors = new LinkedList<>();
                    neighbors.add(r * nc + c);
                    while (!neighbors.isEmpty()) {
                        int id = neighbors.remove();
                        int row = id / nc;
                        int col = id % nc;
                        if (row - 1 >= 0 && grid[row-1][col] == '1') {
                            neighbors.add((row-1) * nc + col);
                            grid[row-1][col] = '0';
                        }
                        if (row + 1 < nr && grid[row+1][col] == '1') {
                            neighbors.add((row+1) * nc + col);
                            grid[row+1][col] = '0';
                        }
                        if (col - 1 >= 0 && grid[row][col-1] == '1') {
                            neighbors.add(row * nc + col-1);
                            grid[row][col-1] = '0';
                        }
                        if (col + 1 < nc && grid[row][col+1] == '1') {
                            neighbors.add(row * nc + col+1);
                            grid[row][col+1] = '0';
                        }
                    }
                }
            }
        }

        return num_islands;
    }

    public static void main(String[] args) {
        Test dp = new Test();
        //System.out.println(dp.climbStairs(3));
        //int[] nums = new int[]{10, 9, 2, 5, 3, 7, 101, 18};
        //System.out.println(dp.lengthOfLIS(nums));
        /*
        int[] nums_c = new int[]{1,3, 5};'
        System.out.println(dp.coinChange(nums_c, 5));
        */
        /*
        String w1 = "zoologicoarchaeologist";
        String w2 = "zoogeologist";
        //String w1 ="horse";
        //String w2 = "ros";
        System.out.println(dp.minDistance(w1, w2));*/
/*

        char[][] grid = new char[][]{
                {'1', '1', '1'},
                {'0', '1', '0'},
                {'1', '1', '1'}};
        char[][] grid1 = new char[][]{{'1'}};

        char[][] grid2 = new char[][]{
                {'1', '1', '0', '0', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'}};

        char[][] grid3 = new char[][]{{'1', '0', '1', '1', '0', '1', '1'}};
        System.out.println("1 "+dp.numIslands(grid));
        System.out.println("1 "+dp.numIslands(grid1));
        System.out.println("3 "+dp.numIslands(grid2));
        System.out.println("3 "+dp.numIslands(grid3));
*/
        BitMap m = new BitMap(100000000);

    }
}