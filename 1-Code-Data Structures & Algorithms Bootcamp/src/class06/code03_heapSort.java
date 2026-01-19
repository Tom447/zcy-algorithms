package class06;

import java.util.Arrays;
import java.util.Random;

public class code03_heapSort {

    public static void heapSort(int[] arr){
        if (arr == null || arr.length < 2){
            return;
        }

//        建立大根堆
        for (int i = 0; i < arr.length; i++){
            heapIfyUp(arr, i);
        }
        int N = arr.length;
        while(N > 0){
            swap(arr, 0, N--);
            heapIfyDown(arr, 0, N);
        }
    }

    public static void heapIfyUp(int[] arr, int index){
        while(arr[index] > arr[(index - 1) / 2]){
            swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }


    public static void heapIfyDown(int[] arr, int index, int heapSize){
            int left = 2 * index + 1;
            while(left < heapSize){
                int maxChildIndex = left;

                if (left + 1 < heapSize){
                    maxChildIndex = arr[left] > arr[left + 1] ? left : left + 1;
                }

                if (arr[index] > arr[maxChildIndex]){
                    break;
                }
                swap(arr, index, maxChildIndex);
                index = maxChildIndex;
                left = 2 * index + 1;
            }
    }


    public static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


    private static int[] generateRandomArray(int maxLen, int maxValue) {
        Random random = new Random();
        int len = random.nextInt(maxLen + 1);
        if (len == 0) {
            return null;
        }
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            int num = random.nextInt(maxValue * 2 + 1) - maxValue;
            arr[i] = num;
        }
        return arr;
    }


    private static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] list = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            list[i] = arr[i];
        }
        return list;
    }


    public static boolean test(int[] arr1, int[] arr2){
        for (int i = 0 ; i < arr1.length; i++){
            if (arr1[i] != arr2[i]){
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        int testTimes = 5000;
        int maxValue = 10;
        int maxLen = 10;
        for (int i = 0 ; i < testTimes; i++){
            int[] arr = generateRandomArray(maxLen, maxValue);
            int[] copy = copyArray(arr);

            if (arr == null){
                continue;
            }

            heapSort(arr);
            Arrays.sort(copy);

            if (!test(arr, copy)){
                System.out.println("oops!");
            }

        }
    }
}
