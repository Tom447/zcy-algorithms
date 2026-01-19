package class22;

import java.util.Random;

public class code03_SplitNumber {

//    题目三
//    给定一个正整数 n，要求将它拆分成若干个 不小于1的正整数之和。
//    并且这些整数满足：非降序排列（即后面的数不能比前面的数小）。
//    问：总共有多少种不同的拆分方式？


    public static int ways1(int n){
        if (n < 0){
            return 0;
        }
        if (n == 1){
            return 1;
        }
        return process1(1, n);
    }

    public static int process1(int pre, int rest){
        if (rest == 0){
            return 1;
        }
        if (pre > rest){
            return 0;
        }

        int ways = 0;
        for (int first = pre; first <= rest; first++){
            ways += process1(first, rest - first);
        }
        return ways;
    }

    public static int ways2(int n){
        if (n < 0){
            return 0;
        }
        if (n == 1){
            return 1;
        }


        int N = n;
        int[][] dp = new int[N+1][N+1];
        //dp[0][..]无用

//        dp[pre][rest]
        for (int pre = 1; pre <= n; pre++) {
            dp[pre][0] = 1;
            dp[pre][pre] = 1;
        }

        for (int pre = N-1; pre >= 1; pre--){
            for (int rest = pre + 1; rest <= N; rest++){
                int ways = 0;
                for (int first = pre; first <= rest; first++){
                    ways += dp[first][rest - first];
                }
                dp[pre][rest] = ways;
            }
        }

        return dp[1][N];
    }


    public static int ways3(int n){
        if (n < 0){
            return 0;
        }
        if (n == 1){
            return 1;
        }


        int N = n;
        int[][] dp = new int[N+1][N+1];
        //dp[0][..]无用

//        dp[pre][rest]
        for (int pre = 1; pre <= n; pre++) {
            dp[pre][0] = 1;
            dp[pre][pre] = 1;
        }

        for (int pre = N-1; pre >= 1; pre--){
            for (int rest = pre + 1; rest <= N; rest++){
                dp[pre][rest] = dp[pre + 1][rest];
                dp[pre][rest] += dp[pre][rest - pre];
            }
        }

        return dp[1][N];
    }

    public static void main(String[] args) {
        int testTimes = 100;     // 测试次数
        int maxN = 50;           // 最大拆分整数 n

        Random rand = new Random();
        System.out.println("开始进行对拍测试...");

        for (int i = 0; i < testTimes; i++) {
            int n = rand.nextInt(maxN) + 1; // [1, maxN]

            int ans1 = code03_SplitNumber.ways1(n);
            int ans2 = code03_SplitNumber.ways2(n);
            int ans3 = code03_SplitNumber.ways3(n);

            if (ans1 != ans2 || ans2 != ans3) {
                System.err.println("❌ 发现不一致!");
                System.err.printf("n = %d\n", n);
                System.err.printf("ways1 = %d, ways2 = %d, ways3 = %d\n", ans1, ans2, ans3);
                return;
            }
        }

        System.out.println("✅ 所有测试通过！");
    }
}
