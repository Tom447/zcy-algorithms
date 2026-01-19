package class21;

import java.util.Random;

public class code03_CoinsWayNoLimit {

//    题目三
//    给定一个正整数数组 arr（代表不同面值的货币）和一个正整数 aim（
//    目标金额），程序计算出使用这些货币组合成 aim 的总方法数。
//    每种货币可以使用任意张数。
//    例如：
//    当 arr = {1, 2} 和 aim = 3 时，可能的组合方式有：
//            - 1 + 1 + 1
//            - 1 + 2所以返回结果是 2。

    public static int ways1(int[] arr, int aim){
        return process1(arr, 0, aim);
    }

    public static int process1(int[] arr, int index, int rest){
        if (rest < 0){
            return 0;
        }
        if (index == arr.length){
            return rest == 0 ? 1 : 0;
        }

        int ways = 0;
        for (int zhang = 0; zhang * arr[index] <= rest; zhang++){
            ways += process1(arr, index+1, rest - (zhang * arr[index]));
        }
        return ways;
    }

    public static int ways2(int[] arr, int aim){
        int N = arr.length;
        int[][] dp = new int[N+1][aim+1];

        dp[N][0] = 1;

        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int ways = 0;
                for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
                    ways += dp[index + 1][rest - (zhang * arr[index])];
                }
                dp[index][rest] = ways;
            }
        }
        return dp[0][aim];
    }

    public static int ways3(int[] arr, int aim){
        int N = arr.length;
        int[][] dp = new int[N+1][aim+1];

        dp[N][0] = 1;

        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
               dp[index][rest] = dp[index+1][rest];
               if (rest - arr[index] >= 0){
                   dp[index][rest] += dp[index][rest - arr[index]];
               }
            }
        }
        return dp[0][aim];
    }
    // 随机生成数组
    public static int[] generateRandomArray(int maxLength, int maxValue) {
        Random random = new Random();
        int length = random.nextInt(maxLength) + 1; // 至少长度为1
        int[] arr = new int[length];

        for (int i = 0; i < length; i++) {
            arr[i] = random.nextInt(maxValue) + 1; // 确保货币面值大于0
        }

        return arr;
    }

    // 打印数组
    public static void printArray(int[] arr) {
        for (int value : arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

    // 测试主函数
    public static void main(String[] args) {
        int testTime = 1000; // 测试次数
        int maxLength = 10;  // 数组最大长度
        int maxValue = 20;   // 货币最大面值
        int maxAim = 50;     // 目标金额最大值（避免过大导致超时）
        boolean succeed = true;

        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxLength, maxValue);
            int aim = new Random().nextInt(maxAim);

            int res1 = ways1(arr, aim);
            int res2 = ways2(arr, aim);
            int res3 = ways3(arr, aim);

            if (res1 != res2 || res1 != res3) {
                succeed = false;
                System.out.println("Test failed at case " + i);
                System.out.println("Array:");
                printArray(arr);
                System.out.println("Aim: " + aim);
                System.out.println("ways1 result: " + res1);
                System.out.println("ways2 result: " + res2);
                System.out.println("ways3 result: " + res3);
                break;
            }
        }

        System.out.println(succeed ? "All tests passed!" : "Some tests failed.");
    }
}
