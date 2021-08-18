package leetcode.src;

import java.util.Stack;

/**
 * @author cuilihuan
 * @data 2020/11/25 10:39
 */
public class Problem_0042_接雨水 {
    public int trap(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int sum  = 0;
        int left = 0;
        int leftMax = height[0];
        int right = height.length - 1;
        int rightMax = height[height.length - 1];
        while (left <= right) {
            if (leftMax < rightMax) {
                leftMax = Math.max(leftMax, height[left]);
                sum += (leftMax > height[left] ? leftMax - height[left] : 0);
                left++;
            }else{
                rightMax = Math.max(rightMax, height[right]);
                sum += (rightMax > height[right] ? rightMax - height[right] : 0);
                right--;
            }
        }
        return sum;
    }

    public int trap2(int[] height) {
        if (height.length < 2)
            return 0;
        Stack<Integer> stack = new Stack<>();
        Stack<Integer> index = new Stack<>();
        int are = 0;
        for (int i = 0; i < height.length; i++) {
            int curr = height[i];
            if (stack.isEmpty() || stack.size() < 2 || stack.peek() >= curr) {// 栈少于2个 或者空 或者 上一个元素大于当前元素（构成不了凹槽）
                stack.push(height[i]);
                index.push(i);
            } else {
                while (stack.peek() < curr && stack.size() >= 2) {
                    int mid = stack.pop();
                    int mid_index = index.pop();
                    int first = stack.peek();
                    int first_index = index.peek();
                    if (mid > first) {
                        stack.push(mid);
                        index.push(mid_index);
                        break;
                    } else {
                        int craer = (Math.min(curr, first) - mid) * Math.abs(i - first_index - 1);
                        are = are + craer;
                    }
                }
                stack.push(curr);
                index.push(i);
            }

        }
        return are;
    }
}
