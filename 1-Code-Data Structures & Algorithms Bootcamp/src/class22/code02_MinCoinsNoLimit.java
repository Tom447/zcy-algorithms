package class22;

import java.util.HashSet;
import java.util.Random;

public class code02_MinCoinsNoLimit {

//    arr是面值数组，其中的值都是正数且没有重复。
//    再给定一个正数aim。每个值都认为是一种面值，
//    且认为张数是无限的。返回组成aim的最少货币数

    public static int ways1(int[] arr, int aim){
        if (arr == null || arr.length == 0 || aim < 0){
            return 0;
        }
        return process1(arr, 0, aim);
    }


    public static int process1(int[] arr, int index, int rest){
        if (index == arr.length){
            return rest == 0 ? 0 : Integer.MAX_VALUE;
        }else{
            int min = Integer.MAX_VALUE;
            for (int zhang = 0; zhang * arr[index] <= rest; zhang++){
                int next = process1(arr, index+1, rest - (zhang * arr[index]));
                if (next != Integer.MAX_VALUE){
                    min = Math.min(min, zhang + next);
                }
            }
            return min;
        }
    }

    public static int ways2(int[] arr, int aim){
        if (arr == null || arr.length == 0 || aim < 0){
            return 0;
        }

        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];

        dp[N][0] = 0;
        for (int j = 1; j <= aim; j++){
            dp[N][j] = Integer.MAX_VALUE;
        }

        for (int index = N-1; index >= 0; index--){
            for (int rest = 0; rest <= aim; rest++){
                int min = Integer.MAX_VALUE;

                for (int zhang = 0; zhang * arr[index] <= rest; zhang++){
                    int next = dp[index+1][rest - (zhang * arr[index])];
                    if (next != Integer.MAX_VALUE){
                        min = Math.min(min, zhang + next);
                    }
                }
                dp[index][rest] = min;

            }
        }
        return dp[0][aim];
    }


    public static int ways3(int[] arr, int aim){
        if (arr == null || arr.length == 0 || aim < 0){
            return 0;
        }

        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];

        dp[N][0] = 0;
        for (int j = 1; j <= aim; j++){
            dp[N][j] = Integer.MAX_VALUE;
        }

        for (int index = N-1; index >= 0; index--){
            for (int rest = 0; rest <= aim; rest++){
                dp[index][rest] = dp[index+1][rest];
                if (rest - arr[index] >= 0 && dp[index][rest - arr[index]] != Integer.MAX_VALUE){
                    dp[index][rest] = Math.min(dp[index][rest], dp[index][rest - arr[index]] + 1);
                }
            }
        }
        return dp[0][aim];
    }

    public static void main(String[] args) {
        int testTimes = 100;      // 测试次数
        int maxArrLength = 10;    // 数组最大长度
        int maxValue = 50;        // 面值最大值
        int maxAim = 200;         // aim 最大值
        Random rand = new Random();

        System.out.println("开始进行对拍测试...");

        for (int i = 0; i < testTimes; i++) {
            // 生成随机数组
            int arrLength = rand.nextInt(maxArrLength) + 1;
            int[] arr = new int[arrLength];
            HashSet<Integer> used = new HashSet<>();
            for (int j = 0; j < arrLength; j++) {
                int val;
                do {
                    val = rand.nextInt(maxValue) + 1;
                } while (used.contains(val));
                used.add(val);
                arr[j] = val;
            }

            int aim = rand.nextInt(maxAim);

            int ans1 = ways1(arr, aim);
            int ans2 = ways2(arr, aim);
            int ans3 = ways3(arr, aim);

            if ((ans1 != ans2 || ans2 != ans3)) {
                System.err.println("❌ 发现不一致!");
                System.err.print("arr = [");
                for (int j = 0; j < arr.length; j++) {
                    System.err.print(arr[j] + (j == arr.length - 1 ? "" : ", "));
                }
                System.err.println("]");
                System.err.printf("aim = %d\n", aim);
                System.err.printf("ways1 = %d, ways2 = %d, ways3 = %d\n", ans1, ans2, ans3);
                return;
            }
        }

        System.out.println("✅ 所有测试通过！");
    }
}
