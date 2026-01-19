package class43;

public class code01_CanIWin {

    public static boolean CanIWin0(int choose, int total){
        //先手第一次遇到0，赢了
        if (total == 0){
            return true;
        }
        //choose的可选范围太小，全选完也达不到
        if ((choose * (choose + 1)) / 2 < total){
            return false;
        }

        int[] arr = new int[choose + 1];
        for (int i = 1; i < arr.length; i++){
            arr[i] = i;
        }
        return process0(arr, total);
    }

    public static boolean process0(int[] arr, int rest){
        if (rest <= 0){
            return true;
        }
        //-1表示选过了，否则就是没选过
        for (int i = 1; i < arr.length; i++){
            if (arr[i] != -1){
                int cur = arr[i];
                arr[i] = -1;
                boolean next = process0(arr, rest - cur);
                arr[i] = cur;
                //这里的先手，是next中的后手，next中的先手输了，这里的先手也就赢了
                if (!next){
                    return true;
                }
            }
        }
        return false;
    }

    // 这个是暴力尝试，思路是正确的，超时而已
    public static boolean canIWin1(int choose, int total) {
        if (total == 0) {
            return true;
        }
        if ((choose * (choose + 1) >> 1) < total) {
            return false;
        }
        return process1(choose, 0, total);
    }

    public static boolean process1(int choose, int status, int rest){
        if (rest <= 0){
            return true;
        }
        //0表示没拿，1表示拿了
        for (int i = 1; i <= choose; i++){
            if (((1 << i) & status) == 0){
                if (!process1(choose, ((1 << i) | status), rest - i)){
                    return true;
                }
            }
        }
        return false;
    }
    // 这个是暴力尝试，思路是正确的，超时而已
    public static boolean canIWin2(int choose, int total) {
        if (total == 0) {
            return true;
        }
        if ((choose * (choose + 1) >> 1) < total) {
            return false;
        }
        //0位置不要了
        int[] dp = new int[1 << (choose + 1)];
        //dp中各个值的含义
        //1代表 赢了 true
        //-1代表输了 false
        //0 代表没算过 要计算一下
        return process2(choose, 0, total, dp);
    }

    public static boolean process2(int choose, int status, int rest, int[] dp){
        if (dp[status] != 0){
            return dp[status] == 1 ? true : false;
        }

        boolean ans = false;
        for (int i = 1; i <= choose; i++){
            if (((1 << i) & status) == 0){
                if (!process1(choose, ((1 << i) | status), rest - i)){
                    ans = true;
                    break;
                }
            }
        }
        dp[status] = ans ? 1 : -1;
        return ans;
    }

}
