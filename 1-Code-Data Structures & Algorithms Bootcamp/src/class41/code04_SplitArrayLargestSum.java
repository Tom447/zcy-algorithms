package class41;

public class code04_SplitArrayLargestSum {

    //计算arr[l]~arr[r]的累加和
    public static int w(int[] sum, int L, int R){
        return sum[R + 1] - sum[L];
    }

    //不使用枚举的动态规划方法,O(N^2 * K){
    public static int splitArray1(int[] arr, int K){
        int N = arr.length;
        int[] sum  = new int[N + 1];
        for (int i = 0; i < N; i++){
            sum[i + 1] = sum[i] + arr[i];
        }
        int[][] dp = new int[N][K + 1];
        for (int j = 1; j <= K; j++){
            dp[0][j] = arr[0];
        }

        for (int i = 1; i < N; i++){
            dp[i][1] = w(sum, 0, i);
        }


        for (int k = 2; k <= K; k++){
            for (int i = 1; i < N; i++){
                //枚举所有j ∈ [0, i-1]的可能性，j为划分点
                int minCost = Integer.MAX_VALUE;

                for (int j = 0; j <= i-1; j++){
                    int leftCost = dp[j][k-1];
                    int rightCost = w(sum, j+1, i);
                    int curCost = Math.max(leftCost, rightCost);
                    minCost = Math.min(minCost, curCost);
                }
                dp[i][k] = minCost;
            }
        }
        return dp[N-1][K];
    }
    //best实现的逻辑是将上文中的j记录一下，复用以降低时间复杂度。

    public static int splitArray2(int[] arr, int K){
        int N = arr.length;
        int[] sum = new int[N + 1];
        for (int i = 0; i < N; i++){
            sum[i + 1] = sum[i] + arr[i];
        }

        int[][] dp = new int[N][K + 1];
        int [][] best = new int[N][K + 1];

        for (int j = 1; j <= K; j++){
            dp[0][j] = arr[0];
            best[0][j] = -1;
        }

        for (int i = 1; i < N; i++){
            dp[i][1] = w(sum, 0, i);
            best[i][1] = -1;
        }
        for (int k = 2; k <= K; k++){
            for (int i = N - 1; i >= 1; i--){
                int down = best[i][k-1];
                int up = i == N - 1 ? N - 1 : best[i+1][k];

                int ans = Integer.MAX_VALUE;
                int bestChoose = -1;
                for (int leftEnd = down; leftEnd <= up; leftEnd++){
                    int leftCost = leftEnd == -1 ? 0 : dp[leftEnd][k-1];
                    int rightCost = leftEnd == i ? 0 : w(sum, leftEnd + 1, i);
                    int cur = Math.max(leftCost, rightCost);
//                    在使用决策单调性优化时，只有在当前方案严格优于已有方案时，才更新划分点。
                    if (cur < ans){
                        ans = cur;
                        bestChoose = leftEnd;
                    }
                    dp[i][k] = ans;
                    best[i][k] = bestChoose;
                }
            }
        }
        return dp[N-1][K];
    }
    public static int[] randomArray(int len, int maxValue) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * maxValue);
        }
        return arr;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static int splitArray3(int[] arr, int K){
        int N = arr.length;
        long sum = 0;
        for (int i = 0; i < N; i++) {
            sum += arr[i];
        }

        long l = 0;
        long r = sum;
        long ans = 0;
        while (l <= r){
            long mid = (l + r) / 2;
            long cur = getNeedParts(arr, mid);
            if (cur <= K){
                ans = mid;
                r = mid - 1;
            }else{
                l = mid + 1;
            }
        }
        return (int)ans;
    }

    public static int getNeedParts(int[] arr, long aim) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > aim) {
                return Integer.MAX_VALUE;
            }
        }
        int parts = 1;
        int all = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (all + arr[i] > aim) {
                parts++;
                all = arr[i];
            } else {
                all += arr[i];
            }
        }
        return parts;
    }


    // 测试用例
    public static void main(String[] args) {


            int N = 10;
            int maxValue = 100;
            int testTime = 10000;
            System.out.println("测试开始");
            for (int i = 0; i < testTime; i++) {
                int len = (int) (Math.random() * N) + 1;
                int M = (int) (Math.random() * N) + 1;
                int[] arr = randomArray(len, maxValue);
                int ans1 = splitArray1(arr, M);
                int ans2 = splitArray2(arr, M);
                int ans3 = splitArray3(arr, M);

                if (ans1 != ans2 || ans1 != ans3) {
                    System.out.print("arr : ");
                    printArray(arr);
                    System.out.println("M : " + M);
                    System.out.println("ans1 : " + ans1);
                    System.out.println("ans2 : " + ans2);
                    System.out.println("ans2 : " + ans3);
                    System.out.println("Oops!");
                    break;
                }
            }
            System.out.println("测试结束");
        }
}
