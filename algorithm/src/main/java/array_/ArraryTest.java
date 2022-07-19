package array_;

/**
 * @Author: Jary
 * @Date: 2021/7/20 3:02 下午
 */
public class ArraryTest {
    //最小和
    public static int smallSum(int[] arr) {
        if (arr == null || arr.length < 2)
            return 0;
        return process(arr, 0, arr.length - 1);
    }

    private static int process(int[] arr, int l, int r) {
        if (l == r)
            return 0;
        int mid = l + (r - l) >> 1;
        return process(arr, l, mid) +
                process(arr, mid, r) +
                merge(arr, l, mid, r);
    }

    private static int merge(int[] arr, int l, int mid, int r) {

        return 0;
    }
}
