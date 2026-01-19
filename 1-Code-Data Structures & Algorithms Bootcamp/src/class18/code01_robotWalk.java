package class18;

public class code01_robotWalk {



//    假设有排成一行的N个位置，记为1~N，N一定大于或等于2。
//    开始时机器人在其中的M位置上（M一定是1~N中的一个）。
//    如果机器人来到1位置，那么下一步只能往右来到2位置；
//    如果机器人来到N位置，那么下一步只能往左来到N-1位置；
//    如果机器人来到中间位置，那么下一步可以往左走或者往右走；
//    规定机器人必须走K步，最终能来到P位置（P也是1~N中的一个）的方法有多少种？
//    给定四个参数N、M、K、P，返回方法数。

    //常规递归方法

    public static int  ways1(int N, int start, int aim, int K){
        if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 1){
            return -1;
        }
        return process1(start, K, aim, N);
    }

    //cur是当前走到哪里了，rest是剩余的步数，aim是目标，N要走多少步
    public static int process1(int cur, int rest, int aim, int N){
        if (rest == 0){
            return cur == aim ? 1 : 0;
        }
        if (cur == 1){ //1 -> 2
            return process1(2, rest - 1, aim, N);
        }
        if (cur == N){//N -> N-1
            return process1(N-1, rest-1, aim, N);
        }
        return process1(cur-1, rest-1, aim, N) + process1(cur+1, rest-1, aim, N);
    }


    //增加一个缓存表
    public static int  ways2(int N, int start, int aim, int K){
        if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 1){
            return -1;
        }
        //行为cur 列为rest (cur != 0 rest可以为0 所以第一行无效)
        int[][] dp = new int[N+1][K+1];
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= K; j++) {
                dp[i][j] = -1;
            }
        }
        return process2(start, K, aim, N, dp);
    }

    public static int process2(int cur, int rest, int aim, int N, int[][] dp){
        if (dp[cur][rest] != -1){
            return dp[cur][rest];
        }
        int ans = 0;
        if (rest == 0){
           ans = cur == aim ? 1 : 0;
        }else if (cur == 1){ //1 -> 2
           ans = process2(2, rest - 1, aim, N, dp);
        }else if (cur == N){//N -> N-1
            ans = process2(N-1, rest-1, aim, N,dp);
        }else {
            ans = process2(cur-1, rest-1, aim, N, dp) + process2(cur+1, rest-1, aim, N, dp);
        }
        dp[cur][rest] = ans;
        return ans;
    }

    public static int ways3(int N, int start, int aim, int K){
        if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 1){
            return -1;
        }
        int[][] dp = new int[N+1][K+1];
        dp[aim][0] = 1;

        for (int rest = 1; rest <= K; rest++){
            dp[1][rest] = dp[2][rest-1];
            for (int cur=2; cur <= N-1; cur++){
                dp[cur][rest] = dp[cur-1][rest-1] + dp[cur+1][rest-1];
            }
            dp[N][rest] = dp[N-1][rest-1];
        }

        return dp[start][K];
    }


    public static void main(String[] args) {
        System.out.println(ways1(5, 2, 4, 6));
        System.out.println(ways2(5, 2, 4, 6));
        System.out.println(ways3(5, 2, 4, 6));
    }

}
