package class04;

import java.util.Random;

public class smallSum {


   public static int smallSum(int[] arr){
       if (arr == null || arr.length < 2){
           return 0;
       }
       return process(arr, 0, arr.length - 1);
   }

    private static int process(int[] arr, int l, int r) {
       if (l == r){
            return 0;
       }
       int mid = l + (r - l) / 2;
       return process(arr, l, mid) + process(arr, mid + 1, r) + merge(arr, l, mid, r);
    }

    private static int merge(int[] arr, int l, int mid, int r) {
       int[] help = new int[r - l + 1];
       int p1 = l;
       int p2 = mid + 1;
       int res = 0;
       int index = 0;
       while(p1 <= mid && p2 <= r){
           res += arr[p1] < arr[p2] ? arr[p1] * (r - p2 + 1) : 0;
           help[index++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
       }

       while(p1 <= mid){
           help[index++] = arr[p1++];
       }

       while(p2 <= r){
           help[index++] = arr[p2++];
       }

       for (int i = 0 ; i < help.length; i++){
           arr[l + i] = help[i];
       }

       return res;
    }


    public static void main(String[] args) {
        int testTimes = 5000;
        int maxLen = 10;
        int maxValue = 10;

        for (int i = 0; i < testTimes; i++){
            int[] arr = generateRandomArray(maxLen, maxValue);
            int[] copyArr = copyArray(arr);

            if (arr == null){
                continue;
            }
            if (smallSum(arr) != test(copyArr)){
                System.out.println("oops");
                break;
            }
        }

        return;
    }

    private static int test(int[] arr) {
       int res = 0;
       for (int i = 1; i < arr.length; i++){
           for (int j = 0; j < i; j++){
               if (arr[j] < arr[i]){
                   res += arr[j];
               }
           }
       }
       return res;
    }

    private static int[] copyArray(int[] arr) {
       if (arr == null){
           return null;
       }
       int[] list = new int[arr.length];
       for (int i = 0; i < arr.length; i++){
           list[i] = arr[i];
       }
       return list;
    }

    private static int[] generateRandomArray(int maxLen, int maxValue) {
        Random random = new Random();
        int len = random.nextInt(maxLen + 1);
        if (len == 0){
            return null;
        }
        int[] arr = new int[len];
        for (int i=0; i < len; i++){
            int num = random.nextInt(maxValue * 2 + 1) - maxValue;
            arr[i] = num;
        }
        return arr;
    }


}
