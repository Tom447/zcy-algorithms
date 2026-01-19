package class04;

import java.util.Arrays;
import java.util.Random;

public class mergeSort {

    public static void mergeSort1(int[] arr){
        if (arr == null || arr.length < 2){
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    private static void process(int[] arr, int l, int r) {
        if (l == r){
            return;
        }
        int mid = l + (r - l) / 2;
        process(arr, l, mid);
        process(arr, mid + 1, r);
        merge(arr, l, mid, r);
    }

    private static void merge(int[] arr, int l, int mid, int r) {
        int[] help = new int[r - l + 1];
        int index = 0;
        int p1 = l;
        int p2 = mid + 1;
        while (p1 <= mid && p2 <= r){
            help[index++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }

        while(p1 <= mid){
            help[index++] = arr[p1++];
        }

        while (p2 <= r){
            help[index++] = arr[p2++];
        }

        for (int i = 0; i < help.length; i++){
            arr[l + i] = help[i];
        }
    }

    public static void mergeSort2(int[] arr){
        if (arr == null || arr.length < 2){
            return;
        }
        int mergeSize = 1;
        int N = arr.length;
        while(mergeSize < N){
            int l = 0;
            while(l < N){
                if (mergeSize >= N - l){
                    break;
                }
                int m = l + mergeSize - 1;
                int r = m + Math.min(mergeSize, N-1-m);
                merge(arr, l, m, r);
                l = r + 1;
            }
            mergeSize *= 2;
        }
    }

    public static void testSort(int[] arr){
        Arrays.sort(arr);
    }


    public static int[] copyArray(int[] arr){
        if (arr == null){
            return null;
        }
        int[] newArray = new int[arr.length];
        for (int i = 0; i < arr.length; i++){
            newArray[i] = arr[i];
        }
        return newArray;
    }

    public static int[] generateArray(int maxLen, int maxValue){
        Random random = new Random();
        int len = random.nextInt(maxLen + 1);
        if (len == 0){
            return null;
        }
        int[] arr = new int[len];
        for (int i = 0; i < len; i++){
            int num = random.nextInt(2 * maxValue + 1) - maxValue;
            arr[i] = num;
        }
        return arr;
    }
    public static void main(String[] args) {
        int testTimes = 500000;
        int maxLen = 10;
        int maxValue = 10;
        for (int i = 0; i < testTimes; i++){
            int[] arr1 = generateArray(maxLen, maxValue);
            int[] arr2 = copyArray(arr1);
            int[] arr3 = copyArray(arr1);

            if (arr1 == null){
                continue;
            }

            mergeSort1(arr1);
            mergeSort2(arr2);
            testSort(arr3);

            if (!isEquals(arr1, arr3)){
                System.out.println("oops1");
            }
            if (!isEquals(arr2, arr3)){
                System.out.println("oops2");
            }

        }
    }

    private static boolean isEquals(int[] arr1, int[] arr2) {
        if (arr1 == null && arr2 == null){
            return true;
        }
        if (arr1 != null && arr2 != null){
            for (int i = 0; i < arr1.length; i++){
                if (arr1[i] != arr2[i]){
                    return false;
                }
            }
            return true;
        }
        return false;
    }


}
