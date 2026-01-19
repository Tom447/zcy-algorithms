package class20;

import java.util.Random;

public class code01_PalindromeSubsequence {

    public static int lpsl1(String str){
        if (str == null || str.length() == 0){
            return 0;
        }
        return process1(str.toCharArray(), 0, str.length()-1);
    }


    public static int process1(char[] str, int L, int R){
        if (L == R){
            return 1;
        }
        if (L == R-1){
            return str[L] == str[R] ? 2 : 1;
        }
        //l, r都不取
        int p1 = process1(str, L+1, R-1);
        //l或r任取其一
        int p2 = process1(str, L, R-1);
        int p3 = process1(str, L+1, R);
        //l或r都取
        int p4 = str[L] != str[R] ? 0 : (2 + process1(str, L+1, R-1));
        return Math.max(Math.max(p1, p2), Math.max(p3, p4));

    }

    public static int lpsl2(String s){
        if (s == null || s.length() == 0){
            return 0;
        }
        char[] str = s.toCharArray();
        int N = s.length();
        int[][] dp = new int[N][N];
        dp[N-1][N-1] = 1;

        for (int i = 0; i < N-1; i++){
            dp[i][i] = 1;
            dp[i][i+1] = str[i] == str[i+1] ? 2 : 1;
        }

        for (int L=N-3; L>=0; L--){
            for (int R=L+2; R < N; R++){
                dp[L][R] = Math.max(dp[L+1][R], dp[L][R-1]);
                if (str[L] == str[R]){
                    dp[L][R] = Math.max(dp[L][R], 2+dp[L+1][R-1]);
                }
            }
        }
        return dp[0][N-1];
    }


    public static int lpsl3(String s){
        if (s == null || s.length() == 0){
            return 0;
        }
        char[] str1 = s.toCharArray();
        char[] str2 = reverse(s).toCharArray();


        int N = str1.length;
        int[][] dp = new int[N][N];
        dp[0][0] = str1[0] == str2[0] ? 1 : 0;
        for (int i=1; i < N; i++){
            dp[0][i] = str1[0] == str2[i] ? 1 : dp[0][i-1];
        }

        for (int i=1; i < N; i++){
            dp[i][0] = str1[i] == str2[0] ? 1: dp[i-1][0];
        }

        for (int i = 1; i < N; i++){
            for (int j = 1; j < N; j++){
                int p1 = dp[i - 1][j];
                int p2 = dp[i][j - 1];
                int p3 = str1[i] == str2[j] ? (1 + dp[i - 1][j - 1]) : 0;
                dp[i][j] = Math.max(p1, Math.max(p2, p3));
            }
        }

        return dp[N-1][N-1];
    }

    public static String reverse(String s){
        char[] str = s.toCharArray();
        StringBuilder reverseStr = new StringBuilder();
        int N = str.length;
        for (int i=N-1; i>=0; i--){
            reverseStr.append(str[i]);
        }
        return reverseStr.toString();
    }

    public static void main(String[] args) {
        int testTime = 1000; // 测试次数
        int maxLength = 20; // 字符串最大长度
        boolean success = true;

        System.out.println("开始对拍测试...");
        Random rand = new Random();

        for (int i = 0; i < testTime; i++) {
            // 随机生成字符串
            String s = generateRandomString(rand.nextInt(maxLength) + 1);

            // 调用三个方法
            int ans1 = lpsl1(s);
            int ans2 = lpsl2(s);
            int ans3 = lpsl3(s);

            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println("发现不一致的结果！");
                System.out.println("输入字符串: " + s);
                System.out.println("lpsl1: " + ans1);
                System.out.println("lpsl2: " + ans2);
                System.out.println("lpsl3: " + ans3);
                success = false;
                break;
            }
        }

        if (success) {
            System.out.println("所有测试通过！");
        } else {
            System.out.println("存在不一致，测试失败！");
        }
    }

    // 生成随机字符串（仅包含小写字母）
    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            char c = (char) ('a' + rand.nextInt(26));
            sb.append(c);
        }
        return sb.toString();
    }


}
