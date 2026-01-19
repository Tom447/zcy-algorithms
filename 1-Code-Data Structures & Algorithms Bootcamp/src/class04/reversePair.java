package class04;

import java.util.Random;

public class reversePair {


    public static int reversePairNum(int[] arr){
        if (arr == null || arr.length < 2){
            return 0;
        }

        return process(arr, 0, arr.length - 1);
    }



    private static int process(int[] arr, int l, int r){
        if (l == r){
            return 0;
        }
        int mid = l + (r - l) / 2;
        return process(arr, l, mid) + process(arr, mid+1, r) + merge(arr, l, mid, r);
    }


    private static int merge(int[] arr, int l, int mid,int r) {

        int[] help = new int[r - l + 1];
        int p1 = mid;
        int p2 = r;
        int ans = 0;
        int index = help.length - 1;
        while(p1 >= l && p2 > mid){
            ans += arr[p1] > arr[p2] ? (p2 - mid): 0;
            help[index--] = arr[p2] >= arr[p1] ? arr[p2--] : arr[p1--];
        }

        while(p1 >= l){
            help[index--] = arr[p1--];
        }

        while(p2 > mid){
            help[index--] = arr[p2--];
        }

        for(int i = 0; i < help.length; i++){
            arr[l + i] = help[i];
        }

        return ans;
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

    public static void main(String[] args) {
        int testTimes = 5000;
        int maxValue = 10;
        int maxLen = 10;

        for (int i = 0; i < testTimes; i++){
            int[] arr = generateRandomArray(maxLen, maxValue);
            int[] copy = copyArray(arr);

            if (arr == null){
                continue;
            }

            int o1 = reversePairNum(arr);
            int o2 = test(copy);
            if (o1 != o2){
                System.out.println("o1 = " + o1 + " o2 = " + o2);
                System.out.println("oops");
                break;
            }
        }
    }

    private static int test(int[] arr) {
        int res = 0;
        for (int i = 0; i < arr.length; i++){
            for (int j = i+1; j < arr.length; j++){
                if (arr[i] > arr[j]){
                    res++;
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
}
