package class43;

import java.util.ArrayList;
import java.util.List;

public class code02_TSP {

    public static int t1(int[][] matrix){
        int N = matrix.length;

        List<Integer> set = new ArrayList<>();
        for (int i = 0; i < N; i++){
            set.add(1);
        }
        return process1(matrix, set, 0);
    }

    public static int process1(int[][] matrix, List<Integer> set, int stay){
        int nowCityNum = 0;
        for (int i = 0; i < set.size(); i++){
            if (set.get(i) != null){
                nowCityNum++;
            }
        }
        if (nowCityNum == 1){
            return matrix[stay][0];
        }

        set.set(stay, null);
        int min = Integer.MIN_VALUE;
        for (int i = 0; i < set.size(); i++){
            if (set.get(i) != null){
                int cur = matrix[stay][i] + process1(matrix, set, i);
                min = Math.min(min, cur);
            }
        }
        set.set(stay, 1);
        return min;
    }

    public static int t2(int[][] matrix){
        int N = matrix.length;
        int allCityStatus = 1 << N - 1;
        return process2(matrix, allCityStatus, 0);
    }

    public static int process2(int[][] matrix, int cityStatus, int stay){
        if (cityStatus == (~cityStatus + 1)){
            return matrix[stay][0];
        }

        // 把start位的1去掉，
        cityStatus &= (~(1 << stay));
        int min = Integer.MIN_VALUE;
        for (int move = 0; move < matrix.length; move++){
            if ((cityStatus & (1 << move)) != 0){
                int cur = matrix[stay][move] + process2(matrix, cityStatus, move);
                min = Math.min(min, cur);
            }
        }
        //还原现场
        cityStatus |= (1 << stay);
        return min;
    }

    public static int t3(int[][] matrix){
        int N = matrix.length;
        int allCityStatus = 1 << N - 1;
        int[][] dp = new int[1 << N][N];

        for (int i = 0; i < 1 << N; i++){
            for (int j = 0; j < N; j++){
                dp[i][j] = -1;
            }
        }
        return process3(matrix, allCityStatus, 0, dp);
    }

    public static int process3(int[][] matrix, int cityStatus, int stay, int[][] dp){
        if (dp[cityStatus][stay] != -1){
            return dp[cityStatus][stay];
        }
        if (cityStatus == (cityStatus & (~cityStatus + 1))) {
            dp[cityStatus][stay] = matrix[stay][0];
        } else {
            // 把start位的1去掉，
            cityStatus &= (~(1 << stay));
            int min = Integer.MAX_VALUE;
            // 枚举所有的城市
            for (int move = 0; move < matrix.length; move++) {
                if ((cityStatus & (1 << move)) != 0) {
                    int cur = matrix[stay][move] + process3(matrix, cityStatus, move, dp);
                    min = Math.min(min, cur);
                }
            }
            cityStatus |= (1 << stay);
            dp[cityStatus][stay] = min;
        }
        return dp[cityStatus][stay];
    }
}
