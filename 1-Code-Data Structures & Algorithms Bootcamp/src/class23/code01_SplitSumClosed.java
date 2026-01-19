package class23;

import java.util.Random;

public class code01_SplitSumClosed {


//    在所有合法的子集划分中，
//    两个子集的差最小的情况下，
//    较小的那个子集的和是多少？
//    或 在不超过整个数组总和一半的前提下，
//    能选出一组数的最大累加和是多少？
    public static int ways1(int[] arr){
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        return process1(arr, 0, sum / 2);
    }


    //arr[index....] index...可以选择
    public static int process1(int[] arr, int index, int rest){
        if (index == arr.length){
            return 0;
        }else{
            int p1 = process1(arr, index+1, rest);
            int p2 = 0;
            if (arr[index] <= rest){
                p2 = arr[index] + process1(arr, index + 1, rest - arr[index]);
            }

            return Math.max(p1, p2);
        }
    }

    public static int ways2(int[] arr){
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }

        int N = arr.length;
        sum /= 2;
        int[][] dp = new int[N+1][sum + 1];

        //dp[N][...] = 0;

        for (int index = N-1; index >= 0; index--){
            for (int rest = 0; rest <= sum; rest++){
                int p1 = dp[index+1][rest];
                int p2 = 0;
                if (arr[index] <= rest){
                    p2 = arr[index] + dp[index+1][rest - arr[index]];
                }
                dp[index][rest] = Math.max(p1, p2);
            }
        }


        return dp[0][sum];
    }





    public static void main(String[] args) {
        int testTimes = 1000;     // 测试次数
        int maxLen = 20;          // 数组最大长度
        int maxValue = 50;        // 数组元素最大值

        Random rand = new Random();
        System.out.println("开始进行对拍测试...");

        for (int i = 0; i < testTimes; i++) {
            int len = rand.nextInt(maxLen) + 1; // [1, maxLen]
            int[] arr = new int[len];
            for (int j = 0; j < len; j++) {
                arr[j] = rand.nextInt(maxValue) + 1; // [1, maxValue]
            }

            int ans1 = code01_SplitSumClosed.ways1(arr);
            int ans2 = code01_SplitSumClosed.ways2(arr);

            if (ans1 != ans2) {
                System.err.println("❌ 发现不一致!");
                System.err.print("arr = [");
                for (int j = 0; j < arr.length; j++) {
                    System.err.print(arr[j] + (j == arr.length - 1 ? "" : ", "));
                }
                System.err.println("]");
                System.err.printf("ways1 = %d, ways2 = %d\n", ans1, ans2);
                return;
            }
        }

        System.out.println("✅ 所有测试通过！");
    }

}
