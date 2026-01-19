package class38;

public class code04_MoneyProblem {


    //目前的能力值是ability, 来到index位置的怪兽, 如果要通过后续怪兽
    //请返回最少花的钱数
    public static long process1(int[] d, int[] p, int index, int ability){
        if (index == d.length){
            return 0;
        }
        //能力值小于当前的怪兽，只能贿赂
        if (ability < d[index]){
            return p[index] + process1(d, p, index+1, ability + d[index]);
        }else{
            //能力值大于等于当前的怪兽选择贿赂与不贿赂两种
            //贿赂花费
            long p1 = p[index] + process1(d, p, index+1, ability + d[index]);
            long p2 = process1(d, p, index+1, ability);
            return Math.min(p1, p2);
        }
    }

    public static long func1(int[] d, int[] p){
        return process1(d, p, 0, 0);
    }


    //func1的dp版本
    //dp[cur][hp]从第 cur 个怪兽开始，当前武力值为 hp，
    // 打完所有怪兽所需要的最小花费。
    public static long func2(int[] d, int[] p){
        int sum = 0;
        for (int num : d){
            sum += num;
        }

        long[][] dp = new long[d.length + 1][sum + 1];
        for (int i = 0; i <= sum; i++){
            dp[0][i] = 0;
        }
        //初始化dp[d.length][?] = 0;
        for (int cur = d.length - 1; cur >= 0; cur--){
            for (int hp = 0; hp <= sum; hp++){
                if (hp + d[cur] > sum){
                    continue;
                }

                if (hp < d[cur]){
                    dp[cur][hp] = p[cur] + dp[cur + 1][hp + d[cur]];
                }else{
                    dp[cur][hp] = Math.min(p[cur] + dp[cur + 1][hp + d[cur]], dp[cur + 1][hp]);
                }
            }
        }
        return dp[0][0];
    }

    //第一种情况是 p[?] >> d[?]
    //考虑第二种情况，就是d[?] >> p[?]

    public static long func3(int[] d, int[] p){
        int allMoney = 0;
        for (int i = 0; i < p.length; i++){
            allMoney += p[i];
        }
        int N = d.length;
        for (int money = 0; money < allMoney; money++){
            if (process3(d, p, N-1, money) != -1){
                return money;
            }
        }
        return allMoney;
    }


    //从0..index号怪兽，花的钱，必须严格==money
    //如果通过不了，返回-1
    //如果可以通过, 返回能通过情况下的最大能力值

    public static long process3(int[] d, int[] p, int index, int money){
        //一个怪兽都没遇到
        if (index == -1){
           return money == 0 ? 0 : -1;
        }
        // index >= 0
        //1)不贿赂怪兽
        long preMaxAbility1 = process3(d, p, index - 1, money);
        long p1 = -1;
        if (preMaxAbility1 != -1 && preMaxAbility1 >= d[index]){
            p1 = preMaxAbility1;
        }

        //2)贿赂当前怪兽
        long preMaxAbility2 = process3(d, p, index - 1, money - p[index]);
        long p2 = -1;
        if (preMaxAbility2 != -1){
            p2 = preMaxAbility2 + d[index];
        }

        return Math.max(p1, p2);
    }


    //第二种情况的dp算法
// dp[i][j]含义：
    // 能经过0～i的怪兽，且花钱为j（花钱的严格等于j）时的武力值最大是多少？
    // 如果dp[i][j]==-1，表示经过0～i的怪兽，花钱为j是无法通过的，
    // 或者之前的钱怎么组合也得不到正好为j的钱数
    public static long  func4(int[] d,int[] p){
        int sum = 0;
        for (int num : p){
            sum += num;
        }

        int[][] dp = new int[d.length][sum+1];
        for (int i = 0; i < dp.length; i++){
            for (int j = 0; j <= sum; j++){
                dp[i][j] = -1;
            }
        }

        dp[0][p[0]] = d[0];
        for (int i = 1; i <d.length; i++){
            for (int j = 0; j <= sum; j++){
                // 可能性一，为当前怪兽花钱
                // 存在条件：
                // j - p[i]要不越界，并且在钱数为j - p[i]时，
                // 要能通过0～i-1的怪兽，并且钱数组合是有效的。
                if(j - p[i] >= 0 && dp[i - 1][j - p[i]] != -1){
                    dp[i][j] = dp[i - 1][j - p[i]] + d[i];
                }
                // 可能性二，不为当前怪兽花钱
                // 存在条件：
                // 0~i-1怪兽在花钱为j的情况下，能保证通过当前i位置的怪兽
                if(dp[i - 1][j] >= d[i]){
                    // 两种可能性中，选武力值最大的
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j]);
                }
            }
        }
        int ans = 0;
        for (int j = 0; j <=sum; j++){
            if(dp[d.length - 1][j] != -1){
                ans = j;
                break;
            }
        }
        return ans;
    }

    public static int[][] generateTwoRandomArray(int len, int value) {
        int size = (int) (Math.random() * len) + 1;
        int[][] arrs = new int[2][size];
        for (int i = 0; i < size; i++) {
            arrs[0][i] = (int) (Math.random() * value) + 1;
            arrs[1][i] = (int) (Math.random() * value) + 1;
        }
        return arrs;
    }

    public static void main(String[] args) {
        int len = 10;
        int value = 20;
        int testTimes = 10000;
        for (int i = 0; i < testTimes; i++) {
            int[][] arrs = generateTwoRandomArray(len, value);
            int[] d = arrs[0];
            int[] p = arrs[1];
            long ans1 = func1(d, p);
            long ans2 = func2(d, p);
            long ans3 = func3(d, p);
            long ans4 = func4(d,p);
            if (ans1 != ans4){
                System.out.println("oops!");
                break;
            }
        }

    }


}
