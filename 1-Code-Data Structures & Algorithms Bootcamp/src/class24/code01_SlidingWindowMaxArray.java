package class24;

import java.util.LinkedList;

public class code01_SlidingWindowMaxArray {

    public static int[] right(int[] arr, int w){
        if (arr == null || w < 1 || arr.length < w) {
            return null;
        }
        int N = arr.length;
        int[] res = new int[N - w + 1];
        int index = 0;
        int windowsL = 0;
        int windowsR = w - 1;

        while (windowsR < N){
            int max = arr[windowsL];
            for (int i = windowsL + 1; i <= windowsR; i++){
                max = Math.max(max, arr[i]);
            }

            res[index++] = max;
            windowsL++;
            windowsR++;
        }

        return res;
    }

    public static int[] getMaxWindow(int[] arr, int w){
        if (arr == null || w < 1 || arr.length < w) {
            return null;
        }

        int N = arr.length;
        int index = 0;
        int[] res = new int[N - w + 1];


        //qmax内的数据必须严格从大到小
        final LinkedList<Integer> qmax = new LinkedList<>();

        for (int R = 0; R < N; R++) {
//            双端队列 qmax 的作用
//            qmax 存储的是数组 arr 中元素的索引，这些索引对应着当前窗口内的元素，
//            并且它们在 arr 中对应的值是从大到小排列的。这意味着：
//
//            qmax 队首的元素始终是当前窗口的最大值对应的索引。
//            当向窗口添加新元素时（即遍历到新的数组位置 R），需要移除所有比新元素
//        arr[R] 小的旧元素索引，以保持 qmax 的单调递减性质。
            while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[R]) {
                qmax.pollLast();
            }
            //维持递减规则后，就可以加入arr[R]的索引了
            qmax.addLast(R);

            //判断过期了没
            if (qmax.peekFirst() == R - w) {
                qmax.pollFirst();
            }


            //队列填满再加入
            if (R >= w - 1) {
                res[index++] = arr[qmax.peekFirst()];
            }
        }
        return res;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int w = (int) (Math.random() * (arr.length + 1));
            int[] ans1 = getMaxWindow(arr, w);
            int[] ans2 = right(arr, w);
            if (!isEqual(ans1, ans2)) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("test finish");
    }
}
