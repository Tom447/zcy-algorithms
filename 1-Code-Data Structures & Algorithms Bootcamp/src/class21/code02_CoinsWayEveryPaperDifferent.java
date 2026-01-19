package class21;

import java.util.Random;

public class code02_CoinsWayEveryPaperDifferent {

//    题目二
//    arr是货币数组，其中的值都是正数。再给定一个正数aim。
//    每个值都认为是一张货币，
//    即便是值相同的货币也认为每一张都是不同的，
//    返回组成aim的方法数
//    例如：arr = {1,1,1}, aim = 2
//    第0个和第1个能组成2，第1个和第2个能组成2，第0个和第2个能组成2
//    一共就3种方法，所以返回3


    public static int ways1(int[] arr, int aim){
        return process1(arr, 0, aim);
    }

    public static int process1(int[] arr, int index, int rest){
        if (rest < 0){
            return 0;
        }
        if (index == arr.length){
            return rest == 0 ? 1: 0;
        }
        return process1(arr, index + 1, rest) + process1(arr, index + 1, rest-arr[index]);
    }

    public static int ways2(int[] arr, int aim){
        if (aim == 0){
            return 1;
        }
        int N = arr.length;
        int[][] dp = new int[N+1][aim+1];

        dp[N][0] = 1;


        for (int index = N-1; index >= 0; index--){
            for (int rest = 0; rest <= aim; rest++){
                //不选当前硬币
                dp[index][rest] = dp[index+1][rest];
                //选当前硬币
                if (rest - arr[index] >= 0){
                    dp[index][rest] += dp[index+1][rest - arr[index]];
                }
            }
        }

        return dp[0][aim];
    }

    // 随机生成数组
    public static int[] generateRandomArray(int maxLength, int maxValue) {
        Random random = new Random();
        int length = random.nextInt(maxLength);
        int[] arr = new int[length];

        for (int i = 0; i < length; i++) {
            arr[i] = random.nextInt(maxValue) + 1; // 确保值大于0
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
        int maxLength = 10; // 数组最大长度
        int maxValue = 20; // 数组元素最大值
        boolean succeed = true;

        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxLength, maxValue);
            int aim = new Random().nextInt(maxValue * maxLength); // aim 最大值可以更大一些

            int res1 = ways1(arr, aim);
            int res2 = ways2(arr, aim);

            if (res1 != res2) {
                succeed = false;
                System.out.println("Test failed!");
                System.out.println("Array:");
                printArray(arr);
                System.out.println("Aim: " + aim);
                System.out.println("ways1 result: " + res1);
                System.out.println("ways2 result: " + res2);
                break;
            }
        }

        System.out.println(succeed ? "All tests passed!" : "Some tests failed.");
    }
}
