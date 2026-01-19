package class19;

public class code02_ConvertToLetterString {
//    规定1和A对应、2和B对应、3和C对应...
//    那么一个数字字符串比如"111"就可以转化为:"AAA"、"KA"和"AK"
//    给定一个只有数字字符组成的字符串str，返回有多少种转化结果

    public static int ways1(String str){
        if (str == null || str.length() == 0){
            return 0;
        }
        return process1(str.toCharArray(), 0);
    }

    public static int process1(char[] str, int index){
        //说明到最后了，算一种方法
        if (index == str.length){
            return 1;
        }
        //index独自面对'0',说明之前的判断有问题
        if (str[index] == '0'){
            return 0;
        }

        //可能性一 str[i] 转换为字符 str[i] != '0'
        int ways = process1(str, index+1);



        if (index + 1 < str.length){
            int twoDigit = (str[index] - '0') * 10 + str[index+1] - '0';
            if (twoDigit < 27 && twoDigit > 9){
                ways += process1(str, index+2);
            }
        }
        return ways;
    }


    public static int ways2(String str){
        if (str == null || str.length() == 0){
            return 0;
        }
        char[] arr = str.toCharArray();
        int N = arr.length;
        //index的范围0~N
        int[] dp = new int[N+1];
        dp[N] = 1;
        for (int i = N-1; i>=0; i--){
            //可能性一 str[i] 转换为字符 str[i] != '0'
            if (arr[i] == '0'){
                dp[i] = 0;
                continue;
            }

            int ways = dp[i+1];

            if (i + 1 < arr.length){
                int twoDigit = (arr[i] - '0') * 10 + arr[i+1] - '0';
                if (twoDigit < 27 && twoDigit > 9){
                    ways += dp[i+2];
                }
            }
            dp[i] = ways;
        }
        return dp[0];
    }
    public static void main(String[] args) {
        System.out.println(ways1("7210231231232031203123"));
        System.out.println(ways2("7210231231232031203123"));
    }
}
