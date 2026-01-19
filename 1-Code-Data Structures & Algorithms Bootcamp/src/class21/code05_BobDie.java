package class21;

import java.util.Random;

public class code05_BobDie {


//    给定5个参数，N, M, row, col, k。
//    表示在NM的区域上，醉汉Bob初始在(row,col)位置。
//    Bob一共要迈出k步，且每步都会等概率向上下左右四个方向走一个单位。
//    任何时候Bob只要离开N,M的区域，就直接死亡。返回k步之后，
//    Bob还在N*M的区域的概率。
    public static double livePosibility1(int row, int col, int k, int N, int M){
        return (double) process1(row, col, k, N, M) / Math.pow(4, k);
    }

    public static long process1(int row,int col, int rest, int N, int M){
        if (row < 0 || row == N || col < 0 || col == M) {
            return 0;
        }
        if (rest == 0){
            return 1;
        }
        long up = process1(row-1, col, rest-1, N, M);
        long down = process1(row + 1, col, rest-1, N, M);
        long left = process1(row, col-1, rest-1, N, M);
        long right = process1(row, col+1, rest-1, N, M);

        return up + down + left + right;
    }


    public static double livePosibility2(int row, int col, int k, int N, int M){
        long[][][] dp = new long[N+1][M+1][k+1];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                dp[i][j][0] = 1;
            }
        }

        for (int rest = 0; rest <= k; rest++){
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < M; c++) {
                    dp[r][c][rest] = pick(dp, N, M, r - 1, c, rest - 1);
                    dp[r][c][rest] += pick(dp, N, M, r + 1, c, rest - 1);
                    dp[r][c][rest] += pick(dp, N, M, r, c - 1, rest - 1);
                    dp[r][c][rest] += pick(dp, N, M, r, c + 1, rest - 1);
                }
            }
        }
        return (double) dp[row][col][k] / Math.pow(4, k);
    }

    public static long pick(long[][][] dp, int N, int M, int r, int c, int rest) {
        if (r < 0 || r == N || c < 0 || c == M) {
            return 0;
        }
        return dp[r][c][rest];
    }

    // 随机生成测试用例
    public static void runTest() {
        int testTime = 100;         // 测试次数
        int maxN = 6;               // 最大行数
        int maxM = 6;               // 最大列数
        int maxK = 10;              // 最多步数
        double epsilon = 1e-10;     // 浮点数误差范围
        boolean succeed = true;

        Random random = new Random();

        for (int i = 0; i < testTime; i++) {
            int N = random.nextInt(maxN - 1) + 2;   // 至少是2行
            int M = random.nextInt(maxM - 1) + 2;   // 至少是2列
            int row = random.nextInt(N);            // 起始行
            int col = random.nextInt(M);            // 起始列
            int k = random.nextInt(maxK);           // 步数

            double res1 = livePosibility1(row, col, k, N, M);
            double res2 = livePosibility2(row, col, k, N, M);

            if (Math.abs(res1 - res2) > epsilon) {
                succeed = false;
                System.out.println("Test failed at case " + i);
                System.out.println("Parameters: N=" + N + ", M=" + M + ", row=" + row + ", col=" + col + ", k=" + k);
                System.out.printf("Method1 result: %.10f\n", res1);
                System.out.printf("Method2 result: %.10f\n", res2);
                break;
            }
        }

        System.out.println(succeed ? "All tests passed!" : "Some tests failed.");
    }

    // 主函数入口
    public static void main(String[] args) {
        runTest();
    }

}
