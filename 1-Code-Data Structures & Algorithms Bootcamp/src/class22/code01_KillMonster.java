package class22;

import java.util.Random;

public class code01_KillMonster {

//    描述了一个概率问题，其中涉及到怪兽的血量、
//    英雄每次攻击造成的随机伤害以及在一定次数攻击后杀死怪兽的概率。
//    我们可以使用动态规划（DP）来解决这个问题。
//
//    给定参数 NN, MM, 和 KK：
//            - NN 是怪兽的初始血量。
//            - MM 是英雄每次攻击可能造成的最大伤害（包括0）。
//            - KK 是英雄攻击的次数。
//    我们需要计算在 KK 次攻击之后，英雄把怪兽砍死的概率。



    public static double ways1(int hp, int killValue, int times){
        if (hp < 1 || killValue < 1 || times < 1){
            return 0;
        }
        return (double) process1(times, killValue, hp) / Math.pow(killValue + 1, times);
    }

    public static long process1(int times, int killValue, int restHp){
        if (times == 0){
            return restHp <= 0 ? 1 : 0;
        }
        if (restHp <= 0){
            return (long)Math.pow(killValue + 1, times);
        }
        int ways = 0;
        for (int i = 0; i <= killValue; i++){
            ways += process1(times - 1, killValue, restHp - i);
        }
        return ways;
    }


    public static double ways2(int hp, int killValue, int times){
        if (hp < 1 || killValue < 1 || times < 1){
            return 0;
        }
        //目标: dp[times][hp]
        long[][] dp = new long[times + 1][hp + 1];
        //times = 0, restHp = 0,  result = 1;
        dp[0][0] = 1;
        //restHp = 0, times > 0 -> restHp < 0,
        // times = 0. -> restHp = 0, times > 0,
        // mathod = (1+killvalue) ^ (times)

        for (int restTimes = 1; restTimes <= times; restTimes++){
            //times = restTimes, hp = 0 dp[restTimes][0] = Math.pow(killValue + 1, restTimes);
            dp[restTimes][0] = (long)Math.pow(killValue + 1, restTimes);
            for (int restHp = 1; restHp <= hp; restHp++){
                long ways = 0;
                for (int kill = 0; kill <= killValue; kill++){
                    if (restHp - kill >= 0){
                        ways += dp[restTimes-1][restHp - kill];
                    }else{
                        ways += Math.pow(killValue + 1, restTimes-1);
                    }
                }
                dp[restTimes][restHp] = ways;
            }
        }


        return (double) dp[times][hp] / Math.pow(killValue + 1, times);
    }


    public static void main(String[] args) {
        int testTimes = 100; // 测试次数
        int maxHp = 20;      // 最大HP值
        int maxKill = 5;     // 最大伤害值
        int maxTimes = 10;    // 最多攻击次数

        Random rand = new Random();
        System.out.println("开始进行对拍测试...");

        for (int i = 0; i < testTimes; i++) {
            int hp = rand.nextInt(maxHp) + 1;       // HP: [1, maxHp]
            int killValue = rand.nextInt(maxKill) + 1; // Kill Value: [1, maxKill]
            int times = rand.nextInt(maxTimes) + 1; // Times: [1, maxTimes]

            double ans1 = ways1(hp, killValue, times);
            double ans2 = ways2(hp, killValue, times);

            if (Math.abs(ans1 - ans2) > 1e-10) { // 浮点数比较要小心
                System.err.println("❌ 发现不一致!");
                System.err.printf("hp = %d, killValue = %d, times = %d\n", hp, killValue, times);
                System.err.printf("ways1 = %.10f, ways2 = %.10f\n", ans1, ans2);
                return;
            }
        }

        System.out.println("✅ 所有测试通过！");
    }



}
