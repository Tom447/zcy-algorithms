package class41;

public class code02_BestSplitForEveryPosition {

    public static int[] bestSplit1(int[] arr){
        if (arr == null || arr.length == 0){
            return new int[0];
        }
        int[] ans = new int[arr.length];
        ans[0] = 0;

       int N = arr.length;

       for (int range = 1; range < N; range++){
           for (int s = 0; s < range; s++){
               int sumL = 0;
               for (int L = 0; L <= s; L++){
                   sumL += arr[L];
               }
               int sumR = 0;
               for (int R = s+1; R <= range; R++){
                   sumR += arr[R];
               }

               ans[range] = Math.max(ans[range], Math.min(sumL, sumR));
           }
       }
       return ans;
    }


    public static int[] bestSplit2(int[] arr){
        if (arr == null || arr.length == 0){
            return new int[0];
        }
        int[] ans = new int[arr.length];
        ans[0] = 0;

        int N = arr.length;
        int[] sums = new int[N + 1];


        sums[0] = 0;
        for (int i = 0; i < N; i++){
            sums[i + 1] = sums[i] + arr[i];
        }

        for (int range = 1; range < N; range++){
            for (int s = 0; s < range; s++){
                int sumL = sum(sums, 0, s);
                int sumR = sum(sums, s+1, range);
                ans[range] = Math.max(ans[range], Math.min(sumL, sumR));
            }
        }
        return ans;
    }

    public static int sum(int[] sums, int L, int R){
        return sums[R + 1] - sums[L];
    }

    public static int[] bestSplit3(int[] arr){
        if (arr == null || arr.length == 0){
            return new int[0];
        }
        int[] ans = new int[arr.length];
        ans[0] = 0;

        int N = arr.length;
        int[] sums = new int[N + 1];


        sums[0] = 0;
        for (int i = 0; i < N; i++){
            sums[i + 1] = sums[i] + arr[i];
        }

        int best = 0;
        //best的划分，左部分[0, best], 右部分[best + 1, range]
        for (int range = 1; range < N; range++){
            while (best + 1 < range){//best + 1 = range的时候无刀可试
                int before = Math.min(sum(sums, 0, best), sum(sums, best + 1, range));
                int after = Math.min(sum(sums, 0, best + 1), sum(sums, best + 2, range));

                if (after >= before){
                    best++;
                }else{
                    break;
                }
            }
            ans[range] = Math.min(sum(sums, 0, best), sum(sums, best + 1, range));
        }
        return ans;
    }

    public static int[] randomArray(int len, int max) {
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * max);
        }
        return ans;
    }

    public static boolean isSameArray(int[] arr1, int[] arr2) {
        if (arr1.length != arr2.length) {
            return false;
        }
        int N = arr1.length;
        for (int i = 0; i < N; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int N = 20;
        int max = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N);
            int[] arr = randomArray(len, max);
            int[] ans1 = bestSplit1(arr);
            int[] ans2 = bestSplit2(arr);
            int[] ans3 = bestSplit3(arr);
            if (!isSameArray(ans1, ans2) || !isSameArray(ans1, ans3)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }

}
