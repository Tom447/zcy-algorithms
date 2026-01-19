package class01;

import java.util.Arrays;
import java.util.Random;

public class BSNearRight {
    public static void main(String[] args) {
        int testTime = 5000;
        int maxValue = 20;
        int maxSize = 20;
        for (int i = 0; i < testTime; i++){
            int[] arr = generateRandomArray(maxValue, maxSize);
            Arrays.sort(arr);
            Random random = new Random();
            int value = (int)random.nextInt(2*maxValue + 1) - maxValue;
            if (BSNearRight(arr, value) != test(arr, value)){
                printArray(arr);
                System.out.println(value);
                System.out.println(BSNearRight(arr, value));
                System.out.println(test(arr,value));
                break;
            }
        }
    }

    private static void printArray(int[] arr) {
        if (arr == null){
            return;
        }
        for (int i = 0; i < arr.length; i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    private static int test(int[] arr, int value) {
        if (arr == null || arr.length == 0){
            return -1;
        }
        for(int i = arr.length - 1; i >= 0; i--){
            if (arr[i] <= value){
                return i;
            }
        }
        return -1;
    }

    private static int[] generateRandomArray(int maxValue, int maxSize) {
        Random random = new Random();
        int size = (int)random.nextInt(maxSize + 1);
        int[] arr = new int[size];
        for (int i = 0; i < size; i++){
            arr[i] = (int)random.nextInt(maxValue * 2 + 1) - maxValue;
        }
        return arr;
    }

    public static int BSNearRight(int[] arr, int value){
        if (arr == null || arr.length == 0){
            return -1;
        }

        int l = 0;
        int r = arr.length - 1;
        int index = -1;
        while (l <= r){
            int mid = l + (r - l) / 2;
            if (arr[mid] <= value){
                index = mid;
                l = mid + 1;
            }else{
                r = mid - 1;
            }
        }
        return index;
    }
}
