package sort;

import java.util.Arrays;

/**
 * Created by Jary on 2018/3/8 0008.
 */
public class Sort {

    public static void main(String[] args) {
        int[] array = {6, 5, 2, 7, 4, 8, 9, 1};

        //bubbleSort(array);
        //quickSort(array);
        //selectionSort(array);
        insertionSort(array);
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
        if (begin < end) {

            int i = begin, j = end;
            int vot = arr[i];

            while (i != j) {
                while (i < j && vot <= arr[j])
                    j--;
                if (i < j)
                    arr[i++] = arr[j];
                while (i < j && arr[i] <= vot)
                    i++;
                if (i < j)
                    arr[j--] = arr[i];
            }
            System.out.println(Arrays.toString(arr));
            arr[i] = vot;
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

