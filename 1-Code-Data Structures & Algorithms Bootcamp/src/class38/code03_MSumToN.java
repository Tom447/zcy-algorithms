package class38;

public class code03_MSumToN {

    public static boolean isMSum1(int num){
        for (int start = 1; start <= num; start++){
            int sum = start;
            for (int j = start + 1; j <= num; j++){
                if (sum + j > num){
                    break;
                }else if (sum + j == num){
                    return true;
                }
                sum += j;
            }
        }
        return false;
    }

    public static boolean isMSum2(int num){
//        if (num == 0 || num == 1 || num == 2){
//            return false;
//        }
        return isPowerOfTwo(num) ? false : true;
    }

    public static boolean isPowerOfTwo(int n) {

        return n > 0 && (n & (n - 1)) == 0;
    }

    public static void main(String[] args) {
        int testTimes = 100;
        int maxValue = 50;
        for (int i = 1; i < testTimes; i++){
            System.out.println(i + " " + isMSum1(i) + " " + isMSum2(i));
        }
    }
}
