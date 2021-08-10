package dp;

import java.util.Arrays;

/**
 * @Author: Jary
 * @Date: 2021/8/10 10:54 上午
 */
public class Probelem_零钱兑换_方法数问题 {
    /**
     * 322. 零钱兑换
     * 给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1
     * 你可以认为每种硬币的数量是无限的。
     * 示例 1：
     * <p>
     * 输入：coins = [1, 2, 5], amount = 11
     * 输出：3
     * 解释：11 = 5 + 5 + 1
     * 示例 2：
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

    /**
     * 货币问题  arr： 货币种类； aim：目标总面值
     * 求 搞定aim的方法数
     * @return
     */
    public int coinMeoth(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        return coinMeothProcess(arr, 0, aim);
    }

    //任意
    private int coinMeothProcess(int[] arr, int index, int rest) {
        if (rest < 0) return 0;

        if (index == arr.length) //no coins can select
            return rest == 0 ? 1 : 0;

        int ways = 0;
        for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
            ways += coinMeothProcess(arr, index + 1, rest - zhang * arr[index]);
        }
        return ways;
    }

    //任意
    private int coinMeothProcessDP(int[] arr, int index, int rest) {
        if (rest < 0) return 0;

        if (index == arr.length) //no coins can select
            return rest == 0 ? 1 : 0;

        int ways = 0;
        for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
            ways += coinMeothProcess(arr, index + 1, rest - zhang * arr[index]);
        }
        return ways;
    }

    public static void main(String[] args) {
        Probelem_零钱兑换_方法数问题 dp = new Probelem_零钱兑换_方法数问题();
        int[] arr = new int[]{1, 5, 10};
        System.out.println(dp.coinMeoth(arr, 11));
    }
}
