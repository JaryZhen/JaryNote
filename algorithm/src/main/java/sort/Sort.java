package sort;

import java.util.Arrays;

/**
 * Created by Jary on 2018/3/8 0008.
 */
public class Sort {

    public static void swop(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * bubble sort
     * n^2
     *
     * @param arr
     * @return
     */
    public static int[] bubbleSort(int[] arr) {
        for (int j = arr.length - 1; j > 0; j--) {
            for (int i = 0; i < j; i++) {
                int temp = arr[i];
                if (temp > arr[i + 1]) {
                    swop(arr, i, i + 1);
                }
            }
        }
        return arr;
    }

    /**
     * select sort
     * n^2
     *
     * @param arr
     */
    public static void selectionSort(int[] arr) {
        for (int i = 0; i <= arr.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {    //选出之后待排序中值最小的位置
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }
            if (min != i) {
                swop(arr, min, i);
            }
        }
    }

    /*
    insert sort
    n^2
     */
    public static void insertionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (arr[j - 1] <= arr[j])
                    break;
                swop(arr, j, j - 1);
                System.out.println("Sorting:  " + Arrays.toString(arr));
            }
        }
    }

    /**
     * 归并排序
     *
     * @param array
     * @return
     */
    private static void mergeSort(int[] array) {
        if (array == null || array.length < 2) return;
        process(array, 0, array.length - 1);
    }

    private static void process(int[] array, int left, int right) {
        if (left == right) return;
        int middle = left + (right - left) / 2;

        process(array, left, middle);
        process(array, middle + 1, right);
        merge(array, left, middle, right);
    }

    /**
     * 归并排序——将两段排序好的数组结合成一个排序数组
     *
     * @param left
     * @param right
     * @return
     */
    private static void merge(int[] arr, int left, int mid, int right) {
        int[] help = new int[right - left + 1];
        int i = 0;
        int p1 = left;
        int p2 = mid + 1;
        while (p1 <= mid && p2 <= right) {
            //从两个数组中选取较小的数放入中间数组
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        //将剩余的部分放入中间数组
        while (p1 <= mid) {
            help[i++] = arr[p1++];
        }
        while (p2 <= right) {
            help[i++] = arr[p2++];
        }
        //将中间数组复制回原数组
        for (int j = 0; j < help.length; j++) {
            arr[left + j] = help[j];
        }
    }

    /**
     * 由荷兰国旗问题引出快排思想
     */
    public static int[] newzLand(int[] arr, int L, int R) {
        if (L > R) return new int[]{-1, -1};// 记录中间结果位置
        if (L == R) return new int[]{L, R};
        int less = L - 1; // 小于区域
        int more = R + 1;// 大于区域
        int index = L;
        int mid = L + (R - L) / 2;
        int belt = arr[mid]; // 基准值
        while (index < more) {
            if (arr[index] == belt)
                index++;
            else if (arr[index] < belt)
                swop(arr, index++, ++less);
            else
                swop(arr, index, --more);
        }
        // swop(arr, more, R)
        return new int[]{less + 1, more - 1};//more)
    }

    /**
     * quick sort 传统方式
     *
     * @param arr
     */
    public static void quickSort(int[] arr) {
        //quickSort(arr, 0, arr.length - 1);
        processQuick2(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int begin, int end) {
        if (begin < end) { //从左到右
            int i = begin, j = end;
            int vot = arr[i];//选序列的第一个元素为基准值
            while (i != j) {//当不重合时 一直循环
                while (i < j && vot <= arr[j]) //下标左边比右边小，且基准值小于等于最右边的数（从后面选最大的）
                    j--;//下标后移
                if (i < j) {//基准值大于右边（从后面找到了第一个较小值）
                    arr[i++] = arr[j];//元素交换，且让左边的下标前移
                    System.out.println(Arrays.toString(arr) + " 基准值" + vot + "大于右边");
                }
                while (i < j && vot >= arr[i])//下标左边比右边小, 且基准值大于最左边的数（从前面选最小值）
                    i++;
                if (i < j)
                    arr[j--] = arr[i];
            }
            arr[i] = vot;
            System.out.println(Arrays.toString(arr));
            quickSort(arr, begin, i - 1);
            quickSort(arr, j + 1, end);
        }
    }

    public static void processQuick2(int[] arr, int L, int R) {
        if (L >= R) return;
        int[] middle = newzLand(arr, L, R);
        processQuick2(arr, L, middle[0] - 1);
        processQuick2(arr, middle[1] + 1, R);
    }

    public static void main(String[] args) {
        int[] array = {6, 3, 1, 5, 2, 7, 4, 8, 9, 4};
        System.out.println(Arrays.toString(array));
        //bubbleSort(array);
        //quickSort(array);
        //selectionSort(array);
        //insertionSort(array);
        //mergeSort(array);
       /* System.out.println("newzland: ");
        Arrays.stream(newzLand(array, 0, array.length - 1)).forEach(System.out::println);
        System.out.println("after newzland:\n" + Arrays.toString(array));
*/
        quickSort(array);
        System.out.println(Arrays.toString(array));

    }
}

