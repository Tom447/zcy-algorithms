package class42;

public class code02_ThrowChessPiecesProblem {
    public static int superEggDrop1(int kChess, int nLevel) {
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }
        return Process1(nLevel, kChess);
    }

    public static int Process1(int rest, int k){
        if (rest == 0){
            return 0;
        }
        if (k == 1){
            return rest;
        }
        int ans = Integer.MIN_VALUE;
        for (int i = 1; i <= rest; i++){// 第一次扔在了第i层
            ans = Math.min(ans, Math.max(Process1(i - 1, k - 1), Process1(rest - i, k)));
        }
        return ans + 1;
    }

    public static int superEggDrop2(int kChess, int nLevel) {
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }
        if (kChess == 1) {
            return nLevel;
        }
        int[][] dp = new int[nLevel + 1][kChess + 1];


        for (int i = 1; i != dp.length; i++) {
            dp[i][1] = i;
        }

        for (int j = 1; j <= nLevel; j++){
            dp[1][j] = 1;
        }

        //dp[l][r] 代表arr[0, l]层，棋子个数为r的最坏情况下的最小尝试次数
        for (int i = 2; i != dp.length; i++) {
            for (int j = 2; j != dp[0].length; j++) {
                int min = Integer.MAX_VALUE;
                for (int k = 1; k != i + 1; k++) {
                    min = Math.min(min, Math.max(dp[k - 1][j - 1], dp[i - k][j]));
                }
                dp[i][j] = min + 1;
            }
        }
        return dp[nLevel][kChess];
    }


    public static int superEggDrop3(int kChess, int nLevel) {
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }
        if (kChess == 1) {
            return nLevel;
        }
        int[][] dp = new int[nLevel + 1][kChess + 1];
        for (int i = 1; i != dp.length; i++) {
            dp[i][1] = i;
        }
        int[][] best = new int[nLevel + 1][kChess + 1];
        for (int i = 1; i != dp[0].length; i++) {
            dp[1][i] = 1;
            best[1][i] = 1;
        }
        for (int i = 2; i < nLevel + 1; i++) {
            for (int j = kChess; j > 1; j--) {
                int ans = Integer.MAX_VALUE;
                int bestChoose = -1;
                int down = best[i - 1][j];
                int up = j == kChess ? i : best[i][j + 1];
                for (int first = down; first <= up; first++) {
                    int cur = Math.max(dp[first - 1][j - 1], dp[i - first][j]);
                    if (cur <= ans) {
                        ans = cur;
                        bestChoose = first;
                    }
                }
                dp[i][j] = ans + 1;
                best[i][j] = bestChoose;
            }
        }
        return dp[nLevel][kChess];
    }

    //在最坏情况下，固定棋子获取最多楼层的最少尝试次数 转化成 固定棋子固定尝试次数的最多楼层。

    public static int superEggDrop4(int kChess, int nLevel) {
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }
        int res = 0;//楼层
        int[] dp = new int[kChess + 1];
        //注意dp[i]是i+1个棋子在res尝试次数下的最多楼层
        while (true){
            int previous = 0;
            res++;
            //i虽然从零开始， 但是代表的是i+1的棋子数
            for (int i = 1; i < dp.length; i++){
                int temp = dp[i];
                dp[i] = dp[i] + previous + 1;
                previous = temp;
                if (dp[i] >= nLevel){
                    return res;
                }
            }
        }
    }
    public static void main(String[] args) {
        int maxN = 500;
        int maxK = 30;
        int testTime = 1000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * maxN) + 1;
            int K = (int) (Math.random() * maxK) + 1;
//            int ans1 = superEggDrop1(K, N);
//            int ans2 = superEggDrop2(K, N);
            int ans3 = superEggDrop3(K, N);
            int ans4 = superEggDrop4(K, N);
            if (ans3 != ans4) {
                System.out.println("出错了!");
            }
        }
        System.out.println("测试结束");
    }


}
