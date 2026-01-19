package class18;

public class code02_CardsInLine {

    public static int win1(int[] arr){
        if (arr == null || arr.length == 0){
            return 0;
        }
        int first = f1(arr, 0, arr.length - 1);
        int second = g1(arr, 0, arr.length - 1);
        return Math.max(first, second);
    }
    //f1是先手
    public static int f1(int[] arr, int L, int R){
        if (L == R){
            return arr[L];
        }
        //做先手取左面。然后在[L+1, R]中做后手
        int p1 = arr[L] + g1(arr, L+1, R);
        //做先手取右面。然后在[L, R-1]中做后手
        int p2 = arr[R] + g1(arr, L, R-1);
        //取最大值
        return Math.max(p1, p2);
    }

    //g1作为后手
    public static int g1(int[] arr, int L, int R){
        //只剩一个单位的时候，g1作为后手取不到。
        if (L == R){
            return 0;
        }
        //g1取后手，先手取了L
        int p1 = f1(arr, L+1, R);
        //g1取后手，先手取了R
        int p2 = f1(arr, L, R-1);
        //由于先手最优，那么后手获得的一定是先手取完数后的较小数
        return Math.min(p1, p2);
    }

    public static int win2(int[] arr){
        if (arr == null || arr.length == 0){
            return 0;
        }
        int N = arr.length;
        int[][] fmp = new int[N][N];
        int[][] gmp = new int[N][N];
        for (int i=0; i < N; i++){
            for (int j=0; j < N; j++){
                fmp[i][j] = -1;
                gmp[i][j] = -1;
            }
        }

        int first = f2(arr, 0, arr.length - 1, fmp, gmp);
        int second = g2(arr, 0, arr.length - 1, fmp, gmp);
        return Math.max(first, second);
    }

    public static int f2(int[] arr, int L, int R, int[][] fmp, int[][] gmp){
        if (fmp[L][R] != -1){
            return fmp[L][R];
        }
        int ans = 0;
        if (L == R){
            ans = arr[L];
        }else {
            int p1 = arr[L] + g2(arr, L+1, R, fmp, gmp);
            int p2 = arr[R] + g2(arr, L, R-1, fmp, gmp);
            ans = Math.max(p1, p2);
        }
        fmp[L][R] = ans;
        return ans;
    }

    public static int g2(int[] arr, int L, int R, int[][] fmp, int[][] gmp){
        if (gmp[L][R] != -1){
            return gmp[L][R];
        }
        int ans = 0;
        if (L == R){
            ans = 0;
        }else{
            int p1 = f2(arr, L+1, R, fmp, gmp);
            int p2 = f2(arr, L, R-1, fmp, gmp);
            ans = Math.min(p1, p2);
        }
        gmp[L][R] = ans;
        return ans;
    }


    public static int win3(int[] arr){
        if (arr == null || arr.length == 0){
            return 0;
        }
        int N = arr.length;
        int[][] fmp = new int[N][N];
        int[][] gmp = new int[N][N];
        for(int startCol = 1; startCol < N; startCol++){
            int L = 0;
            int R = startCol;
            while(R < N){
                fmp[L][R] = Math.max(arr[L] + gmp[L+1][R], arr[R] + gmp[L][R-1]);
                gmp[L][R] = Math.min(fmp[L+1][R], fmp[L][R-1]);
                L++;
                R++;
            }
        }
        return Math.max(fmp[0][N-1], gmp[0][N-1]);

    }
    public static void main(String[] args) {
        int[] arr = { 5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7 };
        System.out.println(win1(arr));
        System.out.println(win2(arr));
        System.out.println(win3(arr));
    }

}
