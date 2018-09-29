package sort;

import java.util.Arrays;

/**
 * Created by Jary on 2018/3/8 0008.
 */
public class Sort {

    public static void main(String[] args) {
        int[] array = {6, 5, 2, 7, 4, 8, 9, 1};
        System.out.println(Arrays.toString(array));
        //bubbleSort(array);
        quickSort(array);
        //selectionSort(array);
        //insertionSort(array);
    }

    public static int[] bubbleSort(int[] arr) {
        for (int j = arr.length - 1; j > 0; j--) {
            for (int i = 0; i < j; i++) {
                int temp = arr[i];
                if (temp > arr[i + 1]) {
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                }
            }
        }
        for (int a : arr) {
            System.out.print(a + " ");
        }
        return arr;
    }

    public static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int begin, int end) {
        if (begin < end) { //从左到右

            int i = begin, j = end;
            int vot = arr[i];//选序列的第一个元素为基准值

            while (i != j) {//当不重合时 一直循环
                while (i < j && vot <= arr[j]) //下标左边比右边小，且基准值小于等于最右边的数（从后面选最大的）
                    j--;//下标后移
                if (i < j)//基准值大于右边（从后面找到了第一个较小值）
                {
                    arr[i++] = arr[j];//元素交换，且让左边的下标前移
                    System.out.println(Arrays.toString(arr)+" 基准值"+vot+"大于右边");
                }
                while (i < j && vot >= arr[i])//下标左边比右边小, 且基准值大于最左边的数（从前面选最小值）
                    i++;
                if (i < j) {
                    arr[j--] = arr[i];
                }
            }
            arr[i] = vot;
            System.out.println(Arrays.toString(arr));
            quickSort(arr, begin, i- 1);
            quickSort(arr, j + 1, end);
        }
    }

    public static void selectionSort(int[] arr){
        for(int i = 0; i <= arr.length-1; i++){
            int min = i;
            for(int j = i+1; j < arr.length; j++){    //选出之后待排序中值最小的位置
                if(arr[j] < arr[min]){
                    min = j;
                }
            }
            if(min != i){
                int temp = arr[min];      //交换操作
                arr[min] = arr[i];
                arr[i] = temp;
                System.out.println("Sorting:  " + Arrays.toString(arr));
            }
        }
    }
    public static void insertionSort(int[] arr){
        for( int i=0; i<arr.length-1; i++ ) {
            for( int j=i+1; j>0; j-- ) {
                if( arr[j-1] <= arr[j] )
                    break;
                int temp = arr[j];      //交换操作
                arr[j] = arr[j-1];
                arr[j-1] = temp;
                System.out.println("Sorting:  " + Arrays.toString(arr));
            }
        }
    }
}

