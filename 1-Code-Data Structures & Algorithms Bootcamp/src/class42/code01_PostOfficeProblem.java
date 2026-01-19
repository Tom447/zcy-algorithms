package class42;

import java.util.Arrays;

public class code01_PostOfficeProblem {

    public static int min2(int[] arr, int k){
        if (arr == null || k < 1 || arr.length < k) {
            return 0;
        }
        int n = arr.length;
        //dp[i][j]表示在arr[0,i]上，邮局为j的时候的最优解
        int[][] dp = new int[n][k + 1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1); // 初始化为 -1 表示未计算
        }
        return process(arr, n - 1, k, dp);

    }
    //计算arr上[l, r]只有一个邮局时的cost
    public static int cost(int[] arr, int l, int r){
        int res = 0;
        int mid = (l + r) / 2;
        for (int i = l; i <= r; i++){
            res += Math.abs(arr[i] - arr[mid]);
        }
        return res;
    }
//  表示在arr[0,i]上，有j个邮局的最小代价
    public static int process(int[] arr, int i, int j, int[][] dp){
        if (j == 0){
            return Integer.MAX_VALUE / 2;//不能建邮局非法
        }
        if (j == 1){
            return cost(arr, 0, i);
        }
        if (i < 0){
            return Integer.MAX_VALUE / 2; //越界非法
        }
        if (dp[i][j] != -1){
            return dp[i][j];
        }

        int res = Integer.MAX_VALUE;
        for (int p = 0; p < i; p++){
            int curCost = process(arr, p, j -1, dp) + cost(arr, p + 1, i);
            res = Math.min(res, curCost);
        }
        dp[i][j] = res;
        return res;
    }

    public static int min1(int[] arr, int num) {
        if (arr == null || num < 1 || arr.length < num) {
            return 0;
        }

        int N = arr.length;
        int[][] w = new int[N+1][N+1];
        //i, 有i+1个居民，那么邮局的数量最多不超过i+1， 所以w[i][i+1] = 0;邮局占据全部的居民点
        //w[L][R]的含义是在[L, R]上，一个邮局耗费的最小代价
        for (int L = 0; L < N; L++){
            for (int R = L + 1; R < N; R++){
                w[L][R] = w[L][R - 1] + arr[R] - arr[(L + R) / 2];
            }
        }

        int[][] dp = new int[N][num + 1];


        //dp第一列为无效列, 从第二列开始为有效列。
        //从第二列开始，第0行全为0
        //从第二列开始，顺序填写第一列
        for (int i = 1; i < N; i++){
            dp[i][1] = w[0][i];
        }
        //dp[L][R]依赖于 dp[k][R-1] + w(k+1, R), dp[L][R]依赖于前面的一列
        //其中k <= 行数
        //i代表居民编号，j代表邮局数量
        for (int i = 1; i < N; i++){
            for (int j = 2; j <= i+1 && j <= num; j++){
                int ans = Integer.MAX_VALUE;
                for (int k = 0; k <= i; k++){
                    ans = Math.min(ans, dp[k][j-1] + w[k+1][i]);
                }
                dp[i][j] = ans;
            }
        }
        return dp[N-1][num];
    }

    public static int min3(int[] arr, int num){
        if (arr == null || num < 1 || arr.length < num) {
            return 0;
        }

        int N = arr.length;
        int[][] w = new int[N+1][N+1];
        for (int L = 0; L < N; L++){
            for (int R = L + 1; R < N; R++){
                w[L][R] = w[L][R - 1] + arr[R] - arr[(L + R) / 2];
            }
        }

        int[][] dp = new int[N][num + 1];
        int[][] best = new int[N][num + 1];


        //dp第一列为无效列, 从第二列开始为有效列。
        //从第二列开始，第0行全为0
        //从第二列开始，顺序填写第一列
        for (int i = 1; i < N; i++){
            dp[i][1] = w[0][i];
            best[i][1] = -1;
        }

        for (int j = 2; j <= num; j++){
            for (int i = N - 1; j <= i + 1; i--){
                int up = i == N - 1 ? N - 1 : dp[i + 1][j];
                int down = best[j-1][i];
                int ans = Integer.MAX_VALUE;
                int bestChoose = -1;
                for (int leftEnd = down; leftEnd <= up; leftEnd++){
                    int leftCost = leftEnd == -1 ? 0 : dp[leftEnd][j-1];
                    int rightCost = leftEnd == i ? 0 : w[leftEnd + 1][i];
                    int cur = leftCost + rightCost;
                    if (cur < ans){
                        ans = cur;
                        bestChoose = leftEnd;
                    }
                }
                dp[i][j] = ans;
                best[i][j] = bestChoose;
            }
        }
        return dp[N-1][num];
    }

    // for test
    public static int[] randomSortedArray(int len, int range) {
        int[] arr = new int[len];
        for (int i = 0; i != len; i++) {
            arr[i] = (int) (Math.random() * range);
        }
        Arrays.sort(arr);
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int N = 30;
        int maxValue = 100;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N) + 1;
            int[] arr = randomSortedArray(len, maxValue);
            int num = (int) (Math.random() * N) + 1;
            int ans1 = min1(arr, num);
            int ans3 = min3(arr, num);
            if (ans1 != ans3) {
                printArray(arr);
                System.out.println(num);
                System.out.println(ans1);
                System.out.println(ans3);
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");

    }
}
