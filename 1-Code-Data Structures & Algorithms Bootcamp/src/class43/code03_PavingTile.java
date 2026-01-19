package class43;

public class code03_PavingTile {
//    2 * M (1 * 2的砖块)
//        M和铺法数的递推关系式是斐波那契数列
//public static int countWays(int M) {
//    if (M == 0) return 1; // 空区域也算一种铺法
//    if (M == 1) return 1; // 2x1区域，只能竖着铺一块砖
//
//    int[] dp = new int[M + 1];
//    dp[0] = 1;
//    dp[1] = 1;
//
//    for (int i = 2; i <= M; i++) {
//        dp[i] = dp[i - 1] + dp[i - 2];
//    }
//
//    return dp[M];
//}

//    这道题处理N * M的问题，使用状态压缩方法
public static int way1(int N, int M) {
    if (N < 1 || M < 1 || ((N * M) & 1) != 0) {
        return 0;
    }
    if (N == 1 || M == 1) {
        return 1;
    }

    // 初始化上一行状态：全填满（1）
    int[] pre = new int[M];
    for (int i = 0; i < M; i++) {
        pre[i] = 1;
    }

    return process(pre, 0, N);
}

    // 处理第 level 行
    public static int process(int[] pre, int level, int N) {
        if (level == N) {
            for (int i = 0; i < pre.length; i++) {
                if (pre[i] == 0) {
                    return 0;
                }
            }
            return 1;
        }

        int[] op = getOp(pre); // 当前行可以铺砖的位置
        return dfs(op, 0, level, N);
    }

    // 根据上一行状态生成当前行的状态（0表示可以铺）
    public static int[] getOp(int[] pre) {
        int[] cur = new int[pre.length];
        for (int i = 0; i < pre.length; i++) {
            cur[i] = pre[i] ^ 1;
        }
        return cur;
    }

    // 尝试在当前行铺砖
    public static int dfs(int[] op, int col, int level, int N) {
        if (col == op.length) {
            return process(op, level + 1, N);
        }

        int ans = 0;

        // 不铺砖，直接跳到下一列
        ans += dfs(op, col + 1, level, N);

        // 尝试水平铺砖（向右铺）
        if (col + 1 < op.length && op[col] == 0 && op[col + 1] == 0) {
            op[col] = 1;
            op[col + 1] = 1;
            ans += dfs(op, col + 2, level, N);
            op[col] = 0;
            op[col + 1] = 0;
        }

        return ans;
    }

    // Min (N,M) 不超过 32
    public static int way2(int N, int M) {
        if (N < 1 || M < 1 || ((N * M) & 1) != 0) {
            return 0;
        }
        if (N == 1 || M == 1) {
            return 1;
        }
        int max = Math.max(N, M);
        int min = Math.min(N, M);
        int pre = (1 << min) - 1;
        return process2(pre, 0, max, min);
    }
    // 上一行的状态，是pre，limit是用来对齐的，固定参数不用管
    // 当前来到i行，一共N行，返回填满的方法数
    public static int process2(int pre, int i, int N, int M) {
        if (i == N) { // base case
            return pre == ((1 << M) - 1) ? 1 : 0;
        }
        int op = ((~pre) & ((1 << M) - 1));
        return dfs2(op, M - 1, i, N, M);
    }

    public static int dfs2(int op, int col, int level, int N, int M) {
        if (col == -1) {
            return process2(op, level + 1, N, M);
        }
        int ans = 0;
        ans += dfs2(op, col - 1, level, N, M);
        if ((op & (1 << col)) == 0 && col - 1 >= 0 && (op & (1 << (col - 1))) == 0) {
            ans += dfs2((op | (3 << (col - 1))), col - 2, level, N, M);
        }
        return ans;
    }

    public static void main(String[] args) {
        int N = 10;
        int M = 6;
        System.out.println(way1(N, M)); // 输出应为 13
        System.out.println(way2(N, M)); // 输出应为 13
    }
}
