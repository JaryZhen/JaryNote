package stack_queue;

/**
 * @Author: Jary
 * @Date: 2020/5/26 9:30 AM
 */
public class SlidingWindow {
    public static void main(String[] args) {

        int[] a = {1, 3, -1, -3, 5, 3, 6, 7};
        int[] a2 = {1};
        int k = 1;
        int[] b = maxSlidingWindow(a2, k);

        for (int i = 0; i < b.length; i++) {
            System.out.println(b[i]);
        }
    }

    public static int[] maxSlidingWindow(int[] nums, int k) {

        int[] a = new int[nums.length - k + 1];
        if (nums.length == 0 || k > nums.length) {
            return a;
        }

        if (k == nums.length){
            a[0] = nums[0];
            return a;
        }
        for (int i = 0; i < nums.length; i++) {
            int w = i;
            int[] x = new int[k];
            for (int j = 0; j < k; j++) {
                if (w + j < nums.length) {
                    x[j] = nums[w+j];
                }
            }

        }
        return a;
    }

    public static int max(int a, int b, int c) {
        return Math.max(c, Math.max(a, b));
    }
}
