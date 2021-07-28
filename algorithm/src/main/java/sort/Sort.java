package sort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

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
        for (int i = 0; i <= arr.length - 1; i++) {
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
     * 归并排序—— 二分的意思
     * 将两段排序好的数组结合成一个排序数组
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
        processQuick2(arr, 0, arr.length - 1);
    }

    public static void processQuick2(int[] arr, int L, int R) {
        if (L >= R) return;
        int[] middle = newzLand(arr, L, R);
        processQuick2(arr, L, middle[0] - 1);
        processQuick2(arr, middle[1] + 1, R);
    }

    static int heapSize = 0;

    /**
     * 堆排序
     *
     * @param
     */
    public static void heapSor(int[] arr) {
        for (int i = 0; i <= arr.length - 1; i++) {
            heapSize++;
            heapInsert(arr, i);
        }
        for (int i = 0; i <= arr.length - 1; i++) {
            pop(arr);
        }
    }

    public static int pop(int[] arr) {
        int size = arr[0];
        swop(arr, 0, --heapSize);
        heapfy(arr, 0);
        return size;
    }

    private static void heapInsert(int[] heap, int i) {
        int par = (i - 1) / 2;
        if (heap[i] > heap[par]) {
            swop(heap, i, par);
            heapInsert(heap, par);
        }
    }

    private static void heapfy(int[] heap, int i) {
        int lef = 2 * i + 1;
        int right = 2 * i + 2;
        if (lef < heapSize) {
            int max = right < heapSize ? heap[lef] > heap[right] ? lef : right : lef;//考虑只有左孩子
            if (heap[i] < heap[max]) {
                swop(heap, i, max);
                heapfy(heap, max);
            }
        }
    }

    public static void main(String[] args) {
        int[] array = {6, 3, 1, 5, 2, 7, 4, 8, 9, 4};
        //int[] array = new int[]{1, 2, 3, 4, 5, 6, 7, 0};
        System.out.println(Arrays.toString(array));
        //bubbleSort(array);
        //quickSort(array);
        //selectionSort(array);
        //insertionSort(array);
        //mergeSort(array);
        /*
        System.out.println("newzland: ");
        Arrays.stream(newzLand(array, 0, array.length - 1)).forEach(System.out::println);
        System.out.println("after newzland:\n" + Arrays.toString(array));
        */
        //quickSort(array);
        // heapSor(array);
        Arrays.sort(array);
        System.out.println(Arrays.toString(array));

    }
}

