package class01;

import java.util.Arrays;
import java.util.Random;

public class BSExist {

    public static boolean BSExist(int[] arr, int num){
        if (arr == null || arr.length == 0){
            return false;
        }
        int l = 0;
        int r = arr.length - 1;
        while (l < r){
            int mid = l + (r - l)/2;
            if (arr[mid] == num){
                return true;
            }else if (arr[mid] > num){
                r = mid - 1;
            }else if (arr[mid] < num){
                l = mid + 1;
            }
        }
        return arr[l] == num;
    }

    public static void main(String[] args) {
        int testTime = 5000;
        int maxSize = 20;
        int maxValue = 20;
        for (int i = 0 ; i < testTime; i++){
            int[] arr = generateRandomArray(maxValue, maxSize);
            Arrays.sort(arr);
            Random random = new Random();
            int value = (int)random.nextInt(maxValue * 2 + 1) - maxValue;
            if (BSExist(arr, value) != test(arr, value)){
                System.out.println("no");
                break;
            }
        }
    }

    private static boolean test(int[] arr, int value) {
        if (arr == null || arr.length == 0){
            return false;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value){
                return true;
            }
        }
        return false;
    }

    private static int[] generateRandomArray(int maxValue, int maxSize) {
        Random random = new Random();
        int size = random.nextInt(maxSize + 1);
        int [] arr = new int[size];
        for (int i = 0; i < size; i++){
            arr[i] = (int)random.nextInt(maxValue * 2 + 1) - maxValue;
        }
        return arr;
    }
}
