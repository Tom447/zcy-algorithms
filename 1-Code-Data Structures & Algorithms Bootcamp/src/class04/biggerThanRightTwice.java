package class04;


import java.util.Random;

public class biggerThanRightTwice {

    public static int reversePairNum(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return process(arr, 0, arr.length - 1);
    }

    private static int process(int[] arr, int l, int r) {
        if (l == r) {
            return 0;
        }
        int mid = l + (r - l) / 2;
        return process(arr, l, mid) + process(arr, mid + 1, r) + merge(arr, l, mid, r);
    }

    private static int merge(int[] arr, int l, int mid, int r) {
        int ans = 0;
        int p1 = l;
        int p2 = mid + 1;

//        // 统计逆序对数量  双指针法
//        while (p1 <= mid && p2 <= r) {
//            if (arr[p1] > (arr[p2] << 1)) {
//                ans += mid - p1 + 1; // arr[p1] 到 arr[mid] 都大于 arr[p2]
//                p2++;
//            } else {
//                p1++;
//            }
//        }
        //滑动窗口法
        int window = mid + 1;
        for (int i = l; i <= mid; i++){
            //[mid + 1, window - 1] 满足   window这个位置并不满足条件，否则也不会停在这里
            if (arr[i] > (2 * arr[window]) && window <= r){
                window++;
            }
            //window - 1 - (mid + 1) + 1 = window - mid - 1
            ans += window - mid - 1;
        }

        // 归并排序部分
        p1 = l;
        p2 = mid + 1;
        int[] help = new int[r - l + 1];
        int index = 0;
        while (p1 <= mid && p2 <= r) {
            help[index++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= mid) {
            help[index++] = arr[p1++];
        }
        while (p2 <= r) {
            help[index++] = arr[p2++];
        }
        for (int i = 0; i < help.length; i++) {
            arr[l + i] = help[i];
        }

        return ans;
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

    public static void main(String[] args) {
        int testTimes = 5000;
        int maxValue = 10;
        int maxLen = 10;

        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateRandomArray(maxLen, maxValue);
            int[] copy = copyArray(arr);

            if (arr == null) {
                continue;
            }

            int o1 = reversePairNum(arr);
            int o2 = test(copy);
            if (o1 != o2) {
                System.out.println("o1 = " + o1 + " o2 = " + o2);
                System.out.println("oops");
                printArray(arr);
                printArray(copy);
                break;
            }
        }
        System.out.println("All tests passed!");
    }

    private static int test(int[] arr) {
        int res = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > (arr[j] << 1)) {
                    res++;
                }
            }
        }
        return res;
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

    private static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}



