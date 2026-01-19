package class14;

import java.util.PriorityQueue;
import java.util.Random;

public class code02_lessMoneySplitGold {


    public static int lessMoneySplitGold1(int[] arr){
        if (arr == null || arr.length == 0){
            return 0;
        }
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int a : arr){
            queue.add(a);
        }

        int sum = 0;
        int cur = 0;
        while (queue.size() > 1){
            cur = queue.poll() + queue.poll();
            sum += cur;
            queue.add(cur);
        }
        return sum;
    }

    //pre 是之前的代价
    public static int lessMoneySplitGold2(int[] arr){
        if (arr == null || arr.length == 0){
            return 0;
        }
        return process(arr, 0);
    }

    public static int process(int[] arr, int pre){
        if (arr.length == 1){
            return pre;
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++){
            for (int j = i + 1; j < arr.length; j++){
                min = Math.min(min,process(copyAndMergeTwo(arr, i, j), pre + arr[i] + arr[j]));
            }
        }
        return min;
    }

    public static int[] copyAndMergeTwo(int[] arr, int i, int j){
        int[] ans = new int[arr.length - 1];
        int cur = 0;
        for (int index = 0; index < arr.length; index++){
            if (index != i && index != j){
                ans[cur++] = arr[index];
            }
        }
        ans[cur] = arr[i] + arr[j];
        return ans;
    }


    public static int[] generateRandomArray(int maxSize, int maxValue){
        Random random = new Random();
        int size = random.nextInt(maxSize) + 1;
        int[] ans = new int[size];

        for (int i = 0; i < size; i++){
            ans[i] = random.nextInt(maxValue);
        }

        return ans;
    }

    public static void main(String[] args) {
        int testTimes = 5000;
        int maxSize = 5;
        int maxValue = 5;
        for (int i = 0; i < testTimes; i++){
            int[] ints = generateRandomArray(maxSize, maxValue);
            if (lessMoneySplitGold1(ints) != lessMoneySplitGold2(ints)){
                System.out.println("oops");
                break;
            }
        }
    }


}
