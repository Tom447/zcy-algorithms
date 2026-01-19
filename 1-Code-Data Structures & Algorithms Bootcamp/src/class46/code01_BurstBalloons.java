package class46;

public class code01_BurstBalloons {

    public static int maxCoins0(int[] arr){
        int N = arr.length;
        int[] help = new int[N + 2];
        for (int i = 0; i < N; i++){
            help[i + 1] = arr[i];
        }
        help[0] = 1;
        help[N + 1] = 1;
        return func(help, 1, N);
    }

    // L-1位置，和R+1位置，永远不越界，并且，[L-1] 和 [R+1] 一定没爆呢！
    // 返回，arr[L...R]打爆所有气球，最大得分是什么
    public static int func(int[] arr, int L, int R){
        if (L == R){
            return arr[L - 1] * arr[L] * arr[R + 1];
        }

        //尝试每一种情况
        //L最后爆
        int max = func(arr, L - 1, R) + arr[L - 1] * arr[L] * arr[R + 1];
        //R最后爆
        max = Math.max(max, func(arr, L, R-1) + arr[L - 1] * arr[R] * arr[R + 1]);
        //i最后爆
        for (int i = L + 1; i < R; i++){
            Math.max(max, func(arr, L, i-1) + arr[L-1] * arr[i] * arr[R + 1] + func(arr, i+1, R));
        }

        return max;
    }

    public static int maxCoins2(int[] arr){
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0];
        }
        int N = arr.length;
        int[] help = new int[N + 2];
        help[0] = 1;
        help[N + 1] = 1;
        for (int i = 0; i < N; i++) {
            help[i + 1] = arr[i];
        }
        int[][] dp = new int[N + 2][N + 2];
        for (int i = 1; i <= N; i++) {
            dp[i][i] = help[i - 1] * help[i] * help[i + 1];
        }
        //dp[L][R]是引爆[L, R]上的所有气球后得到的最大的分数
        for (int L = N; L >= 1; L--){
            for (int R = L+1; R <= N; R++){
                int ans = help[L - 1] * help[L] * help[R + 1] + dp[L + 1][R];
                ans = Math.max(ans, help[L - 1] * help[R] * help[R + 1] + dp[L][R - 1]);
                for (int i = L + 1; i < R; i++){
                    ans = Math.max(ans, dp[L][i-1] + arr[L-1] * arr[i] * arr[R + 1] + dp[i+1][R]);
                }
                dp[L][R] = ans;
            }
        }
        //dp[1][N]的含义是将1到N的所有气球全部戳破后得到的分数
        return dp[1][N];
    }
}
