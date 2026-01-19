package class20;

import java.util.Random;

public class code02_HorseJump {

    public static int jump1(int a, int b, int k){
        return process1(0,0,k, a, b);
    }

    public static int process1(int x, int y, int rest, int a, int b){
        if (x < 0 || x > 9 || y < 0 || y > 8){
            return 0;
        }
        if (rest == 0){
            return (x == a && y == b) ? 1 : 0;
        }
        int ways = process1(x + 2, y + 1, rest - 1, a, b);
        ways += process1(x + 1, y + 2, rest - 1, a, b);
        ways += process1(x - 1, y + 2, rest - 1, a, b);
        ways += process1(x - 2, y + 1, rest - 1, a, b);
        ways += process1(x - 2, y - 1, rest - 1, a, b);
        ways += process1(x - 1, y - 2, rest - 1, a, b);
        ways += process1(x + 1, y - 2, rest - 1, a, b);
        ways += process1(x + 2, y - 1, rest - 1, a, b);
        return ways;
    }

    public static int jump2(int a, int b, int k){
        int[][][] dp = new int[10][9][k+1];
        dp[a][b][0] = 1;

        for (int step = 1; step <= k; step++){
            for (int x = 0; x < 10; x++){
                for (int y = 0; y < 9; y++){
                    int ways = 0;

                    // 查看哪些点可以跳一步到 (x,y)
                    ways += pick(dp, x - 2, y - 1, step - 1); // 从 (x-2,y-1) 跳过来
                    ways += pick(dp, x - 1, y - 2, step - 1);
                    ways += pick(dp, x + 1, y - 2, step - 1);
                    ways += pick(dp, x + 2, y - 1, step - 1);
                    ways += pick(dp, x + 2, y + 1, step - 1);
                    ways += pick(dp, x + 1, y + 2, step - 1);
                    ways += pick(dp, x - 1, y + 2, step - 1);
                    ways += pick(dp, x - 2, y + 1, step - 1);

                    dp[x][y][step] = ways;
                }
            }
        }
        return dp[0][0][k];
    }

    public static int pick(int[][][] dp, int x, int y, int rest){
        if (x < 0 || x > 9 || y < 0 || y > 8){
            return 0;
        }
        return dp[x][y][rest];
    }

    public static void main(String[] args) {
        int testTime = 100; // 测试次数
        int maxStep = 6;     // 最大步数（递归太慢，不宜太大）
        Random rand = new Random();

        System.out.println("开始进行 " + testTime + " 次对拍测试...");

        for (int i = 1; i <= testTime; i++) {
            System.out.println("\n--- Test Case #" + i + " ---");

            // 随机生成目标位置和步数
            int x = rand.nextInt(9 + 1);  // x: 0 ~ 9
            int y = rand.nextInt(8 + 1);  // y: 0 ~ 8
            int k = rand.nextInt(maxStep + 1); // k: 0 ~ maxStep

            System.out.println("目标位置: (" + x + ", " + y + ")");
            System.out.println("跳步数: " + k);

            // 运行两个方法
            int ans1 = jump1(x, y, k);
            int ans2 = jump2(x, y, k);

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
}
