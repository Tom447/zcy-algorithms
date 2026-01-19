package class47;

public class code01_StrangePrinter {

    public static int strangePrinter1(String s){
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        return process1(str, 0, str.length - 1);
    }

    public static int process1(char[] str, int L, int R){
        if (L == R){
            return 1;
        }

        int ans = R - L + 1;
        for (int k = L + 1; k <= R; k++){
            ans = Math.min(ans, process1(str, L, k-1) + process1(str, k, R)
                    - (str[L] == str[k] ? 1 : 0));
        }
        return ans;
    }

    public static int strangePrinter2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int[][] dp = new int[N][N];
        return process2(str, 0, N - 1, dp);
    }

    public static int process2(char[] str, int L, int R, int[][] dp) {
        if (dp[L][R] != 0) {
            return dp[L][R];
        }
        int ans = R - L + 1;
        if (L == R) {
            ans = 1;
        } else {
            for (int k = L + 1; k <= R; k++) {
                ans = Math.min(ans, process2(str, L, k - 1, dp) + process2(str, k, R, dp) - (str[L] == str[k] ? 1 : 0));
            }
        }
        dp[L][R] = ans;
        return ans;
    }


    public static int strangePrinter3_modified(String s) {
        if (s == null || s.length() == 0) return 0;
        char[] str = s.toCharArray();
        int N = str.length;
        int[][] dp = new int[N][N];

        // 只需初始化长度为1的区间
        for (int i = 0; i < N; i++) {
            dp[i][i] = 1;
        }

        // 主循环：从长度为2开始填表
        for (int L = N - 2; L >= 0; L--) {        // L 从 N-2 开始
            for (int R = L + 1; R < N; R++) {     // R 从 L+1 开始（长度>=2）
                dp[L][R] = R - L + 1; // 最坏情况
                for (int k = L + 1; k <= R; k++) {
                    dp[L][R] = Math.min(dp[L][R],
                            dp[L][k-1] + dp[k][R] - (str[L] == str[k] ? 1 : 0)
                    );
                }
            }
        }
        return dp[0][N-1];
    }


}
