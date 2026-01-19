package class08;

import java.util.Arrays;
import java.util.Random;

public class code02_radixSort {


    public static void radix_sort(int[] arr){
        if (arr == null || arr.length < 2){
            return;
        }
        operate(arr, 0, arr.length - 1);
    }

    public static void operate(int[] arr, int L, int R){
        int minNum = getMinArray(arr, L, R);

        if (minNum < 0){
            for (int i = 0; i < arr.length; i++){
                arr[i] += Math.abs(minNum);
            }
        }

        //具体操作

        int[] help = new int[R - L + 1];
        int digit = getMaxDigit(arr, L, R);
        int radix = 10;
        for (int d = 1; d <= digit; d++){

            int[] count = new int[radix];
            for (int i = L; i <= R; i++){
                int index = getIndexDigit(arr[i], d);
                count[index]++;
            }

            for (int i = 1; i < radix; i++){
                count[i] = count[i] + count[i - 1];
            }

            for (int i = R; i >= L; i--){
                 int index = getIndexDigit(arr[i], d);
                 help[count[index] - 1] = arr[i];
                 count[index]--;
            }

            for (int i = L; i <= R; i++){
                arr[i] = help[i - L];
            }
        }
        //..

        if (minNum < 0){
            for (int i = 0; i < arr.length; i++){
                arr[i] -= Math.abs(minNum);
            }
        }

    }

    public static int getMinArray(int[] arr, int L, int R){
        int min = Integer.MAX_VALUE;
        for (int i = L; i <= R; i++){
            min = Math.min(arr[i], min);
        }
        return min;
    }

    public static int getMaxDigit(int[] arr, int L, int R){
        int max = Integer.MIN_VALUE;
        for (int i = L; i <= R; i++){
            max = Math.max(arr[i], max);
        }
        int count = 0;
        if (max == 0){
            return 1;
        }else{
            while(max != 0){
                max /= 10;
                count++;
            }
        }

        return count;
    }

    public static int getIndexDigit(int num, int d){
        return (num / (int)Math.pow(10, d - 1) ) % 10;
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

            radix_sort(arr);
            Arrays.sort(copy);

            if (!test(arr, copy)){
                System.out.println("oops!");
                break;
            }

        }
    }
}
