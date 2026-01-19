package class19;

import java.util.Random;

public class code04_LongestCommonSubsequence {


    public static int longestCommonSubsequence1(String s1, String s2){
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
            return 0;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        return process1(str1, str2, str1.length-1, str2.length-1);
    }

    public static int process1(char[] str1, char[] str2, int i, int j){
        if (i == 0 && j == 0){
            return str1[i] == str2[j] ? 1 : 0;
        }else if (i == 0){ //i = 0 j != 0
            if (str1[i] == str2[j]){
                return 1;
            }else{
                return process1(str1, str2, i, j-1);
            }
        }else if (j == 0){
            if (str1[i] == str2[j]){
                return 1;
            }else{
                return process1(str1, str2, i-1, j);
            }
        }else{
            int p1 = process1(str1, str2, i-1, j);
            int p2 = process1(str1, str2, i, j-1);
            int p3 = str1[i] == str2[j] ? (1 + process1(str1, str2, i-1, j-1)) : 0;
            return Math.max(p1, Math.max(p2, p3));
        }
    }

    public static int longestCommonSubsequence2(String s1, String s2){
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
            return 0;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int N = str1.length;
        int M = str2.length;
        int[][] dp = new int[N][M];
        dp[0][0] = str1[0] == str2[0] ? 1 : 0;
        for (int i=1; i < M; i++){
            dp[0][i] = str1[0] == str2[i] ? 1 : dp[0][i-1];
        }

        for (int i=1; i < N; i++){
            dp[i][0] = str1[i] == str2[0] ? 1: dp[i-1][0];
        }

        for (int i = 1; i < N; i++){
            for (int j = 1; j < M; j++){
                int p1 = dp[i - 1][j];
                int p2 = dp[i][j - 1];
                int p3 = str1[i] == str2[j] ? (1 + dp[i - 1][j - 1]) : 0;
                dp[i][j] = Math.max(p1, Math.max(p2, p3));
            }
        }

        return dp[N-1][M-1];
    }

    public static void main(String[] args) {
        int testTime = 100; // 测试次数
        System.out.println("开始进行 " + testTime + " 次对拍测试...");

        for (int i = 1; i <= testTime; i++) {
            System.out.println("\n--- Test Case #" + i + " ---");

            // 随机生成两个字符串
            String s1 = generateRandomString(10); // 最长10个字符
            String s2 = generateRandomString(10); // 最长10个字符

            System.out.print("s1: " + s1);
            System.out.println(", s2: " + s2);

            // 分别运行两个函数
            int ans1 = longestCommonSubsequence1(s1, s2);
            int ans2 = longestCommonSubsequence2(s1, s2);

            System.out.println("ans1: " + ans1);
            System.out.println("ans2: " + ans2);

            if (ans1 != ans2) {
                System.out.println("❌ 不一致！测试失败！");
                return;
            } else {
                System.out.println("✅ 所有结果一致，测试通过！");
            }
        }

        System.out.println("\n🎉 所有测试通过！");
    }

    // 生成随机字符串
    public static String generateRandomString(int maxLen) {
        Random rand = new Random();
        int len = rand.nextInt(maxLen) + 1;
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < len; j++) {
            char c = (char) ('a' + rand.nextInt(26));
            sb.append(c);
        }
        return sb.toString();
    }




}
