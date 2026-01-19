package class29;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class code01_FindMinKth {



    public static class maxHeapComparator implements Comparator<Integer>{


        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    }

    //利用大根堆
    public static int minKth1(int[] arr, int k){

        //维护一个最小的K个数
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(new maxHeapComparator());

        for (int i = 0; i < k; i++){
            maxHeap.add(arr[i]);
        }

        for (int i = k; i < arr.length; i++){
            if (arr[i] < maxHeap.peek()){
                maxHeap.poll();
                maxHeap.add(arr[i]);
            }
        }
        return maxHeap.peek();
    }

    //利用快速选择

    public static int minKth2(int[] arr, int k){
        int[] copy_arr = copyArray(arr);
        return process2(copy_arr, 0, arr.length - 1, k - 1);
    }

    public static int[] copyArray(int[] arr){
        int[] res = new int[arr.length];

        for (int i = 0; i < arr.length; i++){
            res[i] = arr[i];
        }
        return res;
    }

    public static int process2(int[] arr, int L, int R, int index){
        if (L == R){ // L == R == index
            return arr[L];
        }
        int pivot = arr[L + (int)(Math.random() * (R - L + 1))];
        int[] range = partition(arr, L, R, pivot);
        if (index >= range[0] && index <= range[1]){
            return arr[index];
        }else if (index < range[0]){
            return process2(arr, L, range[0] - 1, index);
        }else{
            return process2(arr, range[1] + 1, R, index);
        }
    }

    public static int[] partition(int[] arr, int L, int R, int p){
        int less = L - 1;
        int more = R + 1;
        int cur = L;
        while (cur < more){
            if (arr[cur] < p){
                swap(arr, ++less, cur++);
            }else if (arr[cur] > p){
                swap(arr, cur, --more);
            }else{
                cur++;
            }
        }
        return new int[] {less + 1, more - 1};
    }

    public static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


    //快速选择非递归方法
    public static int minKth3(int[] arr, int k) {
        // ✅ 先复制一份数组，避免修改原数组
        int index = k - 1;
        int[] copy = copyArray(arr);


        int L = 0;
        int R = copy.length - 1;

        while (L < R) {
            int randomIndex = L + (int)(Math.random() * (R - L + 1));
            int pivot = copy[randomIndex];

            int[] range = partition(copy, L, R, pivot);

            if (index >= range[0] && index <= range[1]) {
                return copy[index];
            } else if (index < range[0]) {
                R = range[0] - 1;
            } else {
                L = range[1] + 1;
            }
        }

        return copy[L];
    }

    //bfprt算法
    public static int minKth4(int[] arr, int k){
       int[] copu_arr = copyArray(arr);
       return bfprt(arr, 0, arr.length - 1, k - 1);
    }

    public static int bfprt(int[] arr, int L, int R, int index){
        if (L == R){
            return arr[L];
        }

        int pivot = medianOfMedians(arr, L, R);
        int[] range = partition(arr, L, R, pivot);
        if (index >= range[0] && index <= range[1]){
            return arr[index];
        }else if (index < range[0]){
            return process2(arr, L, range[0]-1, index);
        }else{
            return process2(arr, range[1] + 1, R, index);
        }
    }

    public static int medianOfMedians(int[] arr, int L, int R){
        int size = R - L + 1;
        int offset = size % 5 == 0 ? 0 : 1;
        int[] mArr = new int[size / 5 + offset];
        for (int team = 0; team < mArr.length; team++){
            int teamOfL = L + team * 5;
            mArr[team] = getMedian(arr, teamOfL, Math.min(R, teamOfL + 4));
        }

        return bfprt(mArr, 0, mArr.length - 1, mArr.length / 2);
    }

    public static int getMedian(int[] arr, int L, int R){
        Arrays.sort(arr, L, R);
        return arr[ (L + R) / 2];
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) (Math.random() * maxSize) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static void printArray(int[] arr){
        for (int i = 0; i < arr.length ; i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
    public static void main(String[] args) {
        int testTime = 1000000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            if (arr == null || arr.length == 0){
                continue;
            }
            int k = (int) (Math.random() * arr.length) + 1;
            int ans1 = minKth1(arr, k);
            int ans2 = minKth2(arr, k);
            int ans3 = minKth3(arr, k);
            int ans4 = minKth4(arr, k);
            if (ans2 != ans4) {
                System.out.println("Oops!");
                Arrays.sort(arr);
                printArray(arr);
                System.out.println("k = " + k);
                System.out.println("res = " + arr[k-1]);
                System.out.println("ans2 = " + ans2);
                System.out.println("ans3 = " + ans4);
                break;
            }
        }
        System.out.println("test finish");
    }
}
