package class19;

public class code01_Knapsack {

    public static int maxValue1(int[] w, int[] v, int bag){
        if (w == null || v == null || w.length != v.length || w.length == 0) {
            return 0;
        }
        return process1(w, v, 0, bag);
    }

    public static int process1(int[] w, int[] v, int index, int rest){
        if (rest < 0){
            return -1;
        }
        if (index == w.length){
            return 0;
        }
        //index不要货
        int p1 = process1(w, v, index+1, rest);
        //index要货
        int p2 = 0;
        int next = process1(w, v, index+1, rest - w[index]);
        if (next != -1){
            p2 = v[index] + next;
        }
        return Math.max(p1, p2);
    }


    public static int maxValue2(int[] w, int[] v, int bag){
        if (w == null || v == null || w.length != v.length || w.length == 0) {
            return 0;
        }
        int N = w.length;
        //index rest为变量 index的范围0-N-1；rest的范围 负数-bag
        int[][] dp = new int[N+1][bag+1];

        for (int index=N-1; index>=0; index--){
            for (int rest=0; rest <= bag; rest++){
                //index不要货
                int p1 = dp[index+1][rest];
                //index要货
                int p2 = 0;
                int next = rest - w[index] < 0 ? -1 : dp[index+1][rest-w[index]];
                if (next != -1){
                    p2 = v[index] + next;
                }
                dp[index][rest] = Math.max(p1, p2);
            }
        }
        return dp[0][bag];
    }
    public static void main(String[] args) {
        int[] weights = { 3, 2, 4, 7, 3, 1, 7 };
        int[] values = { 5, 6, 3, 19, 12, 4, 2 };
        int bag = 15;
        System.out.println(maxValue1(weights, values, bag));
        System.out.println(maxValue2(weights, values, bag));
    }
}
