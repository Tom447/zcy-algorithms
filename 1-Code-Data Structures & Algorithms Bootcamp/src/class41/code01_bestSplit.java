package class41;

public class code01_bestSplit {

    public static int bestSplit1(int[] arr){
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int N = arr.length;
        int ans = 0;

        for (int s = 0; s < N - 1; s++){
            int sumL = 0;
            for (int i = 0; i <= s; i++){
                sumL += arr[i];
            }

            int sumR = 0;

            for (int j = s + 1; j < N; j++){
                sumR += arr[j];
            }

            ans = Math.max(ans, Math.min(sumL, sumR));
        }

        return ans;
    }

    public static int bestSplit2(int[] arr){
        if (arr == null || arr.length == 0){
            return 0;
        }

        int N = arr.length;
        int sumAll = 0;
        for (int i = 0; i < N; i++){
            sumAll += arr[i];
        }

        int sumL = 0;
        int ans = 0;
        for (int i = 0; i < N - 1; i++){
            sumL += arr[i];
            int sumR = sumAll - sumL;
            ans = Math.max(ans, Math.min(sumL, sumR));
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

    public static void main(String[] args) {
        int N = 20;
        int max = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N);
            int[] arr = randomArray(len, max);
            int ans1 = bestSplit1(arr);
            int ans2 = bestSplit2(arr);
            if (ans1 != ans2) {
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }

}
