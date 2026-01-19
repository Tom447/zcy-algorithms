package class01;

import java.util.Arrays;
import java.util.Random;

public class code01_Sort {
     public static void selectSort(int[] arr){
         if (arr == null || arr.length < 2){
             return;
         }
         for (int i = 0; i < arr.length - 1; i++){
             int minIndex = i;
             for (int j = i + 1; j < arr.length; j++){
                 minIndex = arr[j] < arr[minIndex] ? j : minIndex;
             }
             swap(arr, i, minIndex);
         }

     }

     public static void BubbleSort(int[] arr){
         if (arr == null || arr.length < 2){
             return;
         }

         for(int e = arr.length - 1; e > 0; e--){
             for (int i = 0; i < e; i--){
                 if (arr[i] > arr[i + 1]){
                     swap(arr, i, i+1);
                 }
             }
         }
     }

     public static void insertSort(int[] arr){
         if (arr == null || arr.length < 2){
             return;
         }
         for (int i = 1; i < arr.length; i ++){
             for (int j = i - 1; j >= 0 && arr[j] > arr[j+1]; j--){
                 swap(arr, j , j+1);
             }
         }
     }

     public static void  swap(int[] arr, int i, int temp){
         int num = arr[i];
         arr[i] = arr[temp];
         arr[temp] = num;
     }


    public static void main(String[] args) {
        int testTime = 5000;
        int maxSize = 20;
        int maxValue = 10;
        for (int i = 0; i < testTime; i++){
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            insertSort(arr1);
            test(arr2);
            if(!isEqual(arr1, arr2)){
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        return;
    }



    private static void printArray(int[] arr) {
         if (arr == null){
             return;
         }
         for (int i = 0; i < arr.length; i++){
             System.out.print(arr[i] + " ");
         }
    }

    private static boolean isEqual(int[] arr1, int[] arr2) {
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


    private static void test(int[] arr) {
         if (arr == null){
             return;
         }
         Arrays.sort(arr);
    }

    private static int[] copyArray(int[] arr) {
         if (arr == null){
             return null;
         }

         int[] newArray = new int[arr.length];
         for (int i = 0; i < arr.length; i++){
             newArray[i] = arr[i];
         }
         return newArray;
    }

    private static int[] generateRandomArray(int maxSize, int maxValue) {
        Random random = new Random();
        int size = random.nextInt(maxSize + 1);
        int[] arr = new int[size];
        for (int i = 0; i < size; i++){
            arr[i] = (int)random.nextInt(2*maxValue + 1) - maxValue;
        }
        return arr;
    }


}
