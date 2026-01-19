package class21;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class code05_CoinsWaySameValueSamePapper {

//    题目四
//    arr是货币数组，其中的值都是正数。再给定一个正数aim。
//    每个值都认为是一张货币，认为值相同的货币没有任何不同，
//    返回组成aim的方法数例如：arr = {1,2,1,1,2,1,2},
//    aim = 4方法：1+1+1+1、1+1+2、2+2一共就3种方法，
//    所以返回3



    public static class record{
        public int[] value;
        public int[] count;

        public record(int[] v, int[] c){
            value = v;
            count = c;
        }
    }
    public static record getRecord(int[] arr, int aim){
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < arr.length; i++){
            if (!map.containsKey(arr[i])){
                map.put(arr[i], 1);
            }else{
                map.put(arr[i], 1 + map.get(arr[i]));
            }
        }

        int[] value = new int[map.size()];
        int[] count = new int[map.size()];

        int index = 0;
        for (Map.Entry<Integer, Integer> cur : map.entrySet()){
            value[index] = cur.getKey();
            count[index++] = cur.getValue();
        }

        return new record(value, count);
    }


    public static int coinsWay1(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        record info = getRecord(arr, aim);
        return process1(info.value, info.count, 0, aim);
    }

    public static int process1(int[] value, int[] count, int index, int rest){
        if (rest < 0){
            return 0;
        }
        if (index == value.length){
            return rest == 0 ? 1 : 0;
        }
        int ways = 0;
        for (int zhang = 0; zhang * value[index] <= rest && zhang <= count[index]; zhang++){
            ways += process1(value, count, index+1, rest - (zhang * value[index]));
        }
        return ways;
    }

    public static int coinsWay2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        record info = getRecord(arr, aim);

        int[] value = info.value;
        int[] count = info.count;

        int N = value.length;
        int[][] dp = new int[N+1][aim+1];

        dp[N][0] = 1;

        for (int index = N-1; index >= 0; index--){
           for (int rest = 0; rest <= aim; rest++){
               int ways = 0;
               for (int zhang = 0; zhang * value[index] <= rest && zhang <= count[index]; zhang++){
                   ways += dp[index+1][rest - (zhang * value[index])];
               }
               dp[index][rest] = ways;
           }
        }
        return dp[0][aim];
    }

    public static int coinsWay3(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        record info = getRecord(arr, aim);

        int[] value = info.value;
        int[] count = info.count;

        int N = value.length;
        int[][] dp = new int[N+1][aim+1];

        dp[N][0] = 1;

        for (int index = N-1; index >= 0; index--){
            for (int rest = 0; rest <= aim; rest++){
                dp[index][rest] = dp[index+1][rest];
                if (rest - value[index] >= 0){
                    dp[index][rest] += dp[index][rest - value[index]];
                }
                if (rest - value[index] * (count[index] + 1) >= 0){
                    dp[index][rest] -= dp[index+1][rest - value[index] * (count[index] + 1)];
                }
            }
        }
        return dp[0][aim];
    }


    // 随机生成数组
    public static int[] generateRandomArray(int maxLength, int maxValue, int maxCount) {
        Random random = new Random();
        int length = random.nextInt(maxLength) + 1; // 至少包含一个元素
        int[] arr = new int[length];

        for (int i = 0; i < length; i++) {
            arr[i] = random.nextInt(maxValue) + 1; // 确保货币面值大于0
        }

        // 确保不会出现超出范围的数量
        if (length > maxCount) {
            int[] temp = new int[maxCount];
            System.arraycopy(arr, 0, temp, 0, maxCount);
            arr = temp;
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
        int maxValue = 5;    // 单个货币最大面值
        int maxAim = 20;     // 目标金额最大值
        boolean succeed = true;

        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxLength, maxValue, maxLength);
            int aim = new Random().nextInt(maxAim);

            int res1 = coinsWay1(arr, aim);
            int res2 = coinsWay2(arr, aim);
            int res3 = coinsWay3(arr, aim);

            if (res1 != res2 || res1 != res3) {
                succeed = false;
                System.out.println("Test failed at case " + i);
                System.out.print("Array: ");
                printArray(arr);
                System.out.println("Aim: " + aim);
                System.out.println("Method1 result: " + res1);
                System.out.println("Method2 result: " + res2);
                System.out.println("Method3 result: " + res3);
                break;
            }
        }

        System.out.println(succeed ? "All tests passed!" : "Some tests failed.");
    }

}
