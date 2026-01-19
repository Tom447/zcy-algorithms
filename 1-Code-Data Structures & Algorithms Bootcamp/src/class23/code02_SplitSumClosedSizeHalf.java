package class23;

import java.util.Random;

public class code02_SplitSumClosedSizeHalf {

//    题目二
//    给定一个正数数组 arr，请把 arr 中所有的数分成两个集合。具体要求如下：
//            - 如果 arr 的长度为偶数，两个集合包含的数的个数要一样多。
//            - 如果 arr 的长度为奇数，两个集合包含的数的个数必须只差一个。
//            - 尽量让两个集合的累加和接近。
//    返回：最接近的情况下，较小集合的累加和。


    public static int ways1(int[] arr){
        if (arr == null || arr.length < 2){
            return 0;
        }

        int sum = 0;
        for (int i = 0; i < arr.length; i++){
            sum += arr[i];
        }


        sum /= 2;
        if ((arr.length & 1) == 0){
            return process1(arr, 0, arr.length / 2 , sum);
        }else{
            return Math.max(
                    process1(arr, 0, arr.length / 2, sum),
                    process1(arr, 0, arr.length / 2 + 1, sum)
            );
        }
    }

    public static int process1(int[] arr, int index, int picks, int rest){
        if (index == arr.length){
            return picks == 0 ? 0 : -1;
        }else{
            int p1 = process1(arr, index + 1, picks, rest);

            int p2 = -1;
            int next = -1;


            if (arr[index] <= rest) {
                next = process1(arr, index + 1, picks - 1, rest - arr[index]);
            }

            if (next != -1){
                p2 = arr[index] + next;
            }
            return Math.max(p1, p2);
        }
    }



    public static int ways2(int[] arr){
        if (arr == null || arr.length < 2){
            return 0;
        }

        int sum = 0;
        for (int i = 0; i < arr.length; i++){
            sum += arr[i];
        }
        sum /= 2;
        int N = arr.length;
        int M = (N + 1) / 2;
        //index:0~N,  picks:0~（N+1/2）, rest: 0~sum;
        int[][][] dp = new int[N + 1][M + 1][sum + 1];

        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= M; j++) {
                for (int k = 0; k <= sum; k++) {
                    dp[i][j][k] = -1;
                }
            }
        }
        for (int rest = 0; rest <= sum; rest++) {
            dp[N][0][rest] = 0;
        }

        for (int i = N - 1; i >= 0; i--) {
            for (int picks = 0; picks <= M; picks++) {
                for (int rest = 0; rest <= sum; rest++) {
                    int p1 = dp[i + 1][picks][rest];
                    // 就是要使用arr[i]这个数
                    int p2 = -1;
                    int next = -1;
                    if (picks - 1 >= 0 && arr[i] <= rest) {
                        next = dp[i + 1][picks - 1][rest - arr[i]];
                    }
                    if (next != -1) {
                        p2 = arr[i] + next;
                    }
                    dp[i][picks][rest] = Math.max(p1, p2);
                }
            }
        }

        if ((arr.length & 1) == 0) {
            return dp[0][arr.length / 2][sum];
        } else {
            return Math.max(dp[0][arr.length / 2][sum], dp[0][(arr.length / 2) + 1][sum]);
        }
    }


    public static void main(String[] args) {
        int testTimes = 1000; // 测试次数
        int maxArrayLength = 20; // 数组最大长度
        int maxValue = 100; // 数组中元素的最大值

        Random random = new Random();

        for (int i = 0; i < testTimes; i++) {
            int length = random.nextInt(maxArrayLength) + 1;
            int[] arr = new int[length];
            for (int j = 0; j < length; j++) {
                arr[j] = random.nextInt(maxValue);
            }

            int result1 = code02_SplitSumClosedSizeHalf.ways1(arr);
            int result2 = code02_SplitSumClosedSizeHalf.ways2(arr);

            if (result1 != result2) {
                System.out.println("Test failed!");
                System.out.print("Array: ");
                for (int num : arr) {
                    System.out.print(num + " ");
                }
                System.out.println();
                System.out.println("ways1 result: " + result1);
                System.out.println("ways2 result: " + result2);
                break;
            } else {
                System.out.println("Test passed for iteration " + (i + 1));
            }
        }
    }


}
