package class06;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

public class code03_sortArrayDistanceLessK {


    public static void sortArrayLessK(int[] arr, int k){
        if (arr == null || arr.length < 2){
            return;
        }

        if (k == 0){
            return;
        }
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int index = 0;
        for(; index <= Math.min(arr.length - 1, k - 1); index++){
            heap.add(arr[index]);
        }
        int i = 0;
        for (; index < arr.length; index++){
            heap.add(arr[index]);
            arr[i++] = heap.poll();
        }

        while(!heap.isEmpty()){
            arr[i++] = heap.poll();
        }
    }

    public static int[] generateRandomArray(int maxLen, int maxValue, int k){
        Random random = new Random();
        int len = random.nextInt(maxLen + 1);

        int[] arr = new int[len];
        for (int i = 0 ; i < len; i++){
            arr[i] =  random.nextInt(2 * maxValue + 1) - maxValue;
        }

        Arrays.sort(arr);

        boolean[] isSwap = new boolean[len];
        for (int i = 0; i < arr.length; i++){
            int j = Math.min(i + random.nextInt(k + 1), arr.length - 1);
            if (!isSwap[i] && !isSwap[j]){
                isSwap[i] = true;
                isSwap[j] = true;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
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
        for (int i = 0; i < testTimes; i++){
            Random random = new Random();
            int k = random.nextInt(maxLen + 1);
            int[] arr = generateRandomArray(maxLen, maxValue, k);
            int[] copy = copyArray(arr);

            if (arr == null){
                continue;
            }

            sortArrayLessK(arr, k);
            Arrays.sort(copy);

            if (!test(arr, copy)){
                System.out.println("oops");
            }
        }
    }
}
