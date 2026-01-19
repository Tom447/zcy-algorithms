package class40;

public class code04_AvgLessEqualValueLongestSubarray {
    //对数器

    public static int ways1(int[] arr, int v){

        int[] sums = new int[arr.length + 1];
        for (int i = 0; i < arr.length; i++){
            sums[i + 1] = arr[i] + sums[i];
        }

        int ans = 0;
        for (int L = 0; L < arr.length; L++){
            for (int R = L; R < arr.length; R++){
                int sum = 0;
                int k = R - L + 1;
                sum = sums[R + 1] - sums[L];
                double avg = (double) sum / (double) k;
                if (avg <= v) {
                    ans = Math.max(ans, k);
                }
            }
        }
        return ans;
    }




    public static int ways3(int[] arr, int v){
        if (arr == null || arr.length == 0){
            return 0;
        }

        for (int i = 0; i < arr.length; i++){
            arr[i] -= v;
        }

        return maxLength(arr, 0);
    }

    public static int maxLength(int[] arr, int k){
        if (arr == null || arr.length == 0 || k < 0){
            return 0;
        }
        int[] minSums = new int[arr.length];
        int[] minSumEnds = new int[arr.length];

        minSums[arr.length - 1] = arr[arr.length - 1];
        minSumEnds[arr.length - 1] = arr.length - 1;

        for (int i = arr.length - 2; i >= 0; i--){
            if (minSums[i + 1] < 0){
                minSums[i] = arr[i] + minSums[i + 1];
                minSumEnds[i] = minSumEnds[i + 1];
            }else{
                minSums[i] = arr[i];
                minSumEnds[i] = i;
            }
        }

        int end = 0;
        int len = 0;
        int sum = 0;

        for (int i = 0; i < arr.length; i++){

            while (end < arr.length && sum + minSums[end] <= k){
                sum += minSums[end];
                end = minSumEnds[end] + 1;
            }
            len = Math.max(len, end - i);

            if (end > i){
                sum -= arr[i];
            }else{
                end = i+1;
            }
        }
        return len;
    }

    // 用于测试
    public static int[] randomArray(int maxLen, int maxValue) {
        int len = (int) (Math.random() * maxLen) + 1;
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * maxValue);
        }
        return ans;
    }

    // 用于测试
    public static int[] copyArray(int[] arr) {
        int[] ans = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ans[i] = arr[i];
        }
        return ans;
    }

    // 用于测试
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // 用于测试
    public static void main(String[] args) {
        System.out.println("测试开始");
        int maxLen = 20;
        int maxValue = 100;
        int testTime = 500000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int value = (int) (Math.random() * maxValue);
            int[] arr1 = copyArray(arr);
            int[] arr2 = copyArray(arr);
            int[] arr3 = copyArray(arr);
            int ans1 = ways1(arr1, value);
            int ans3 = ways3(arr3, value);
            if (ans1 != ans3) {
                System.out.println("测试出错！");
                System.out.print("测试数组：");
                printArray(arr);
                System.out.println("子数组平均值不小于 ：" + value);
                System.out.println("方法1得到的最大长度：" + ans1);
                System.out.println("方法3得到的最大长度：" + ans3);
                System.out.println("=========================");
                break;
            }
        }
        System.out.println("测试结束");
    }
}
