package class40;

public class code03_LongestLessSumSubArrayLength {


    //    假设要找的是preFixSum中第一个大于等于target的索引。我想要找到prefixSum中大于num的第一个位置，
//    由于prefixSum可能为负数，而产生跳变也是就preFixSum只跟
//    第一次遇到数num比以往的数更大（其中以往的数都小于target）
//    而假设遇到num的时候刚好比target大，那么当前的索引index就是满足条件的索引。
    public static int maxLength1(int[] arr, int k){
        int[] h = new int[arr.length + 1];
        int sum = 0;
        h[0] = 0;
//        sum(arr[i..j]) = prefixSum[j+1] - prefixSum[i]
//        任何一个sum都可以通过前缀和相减得到结果。
        for (int i = 0; i < arr.length; i++){
            sum += arr[i];
            h[i + 1] = Math.max(sum, h[i]);
        }

        sum = 0;
        int res = 0;
        int pre = 0;
        int len = 0;

        for (int i = 0; i < arr.length; i++){
            sum += arr[i];
            pre = getLessIndex(h, sum - k);
            len = pre == -1? 0 : i - pre + 1;
            res = Math.max(res, len);
        }
        return res;
    }

    public static int getLessIndex(int[] arr, int num){
        int low = 0;
        int height = arr.length - 1;
        int mid = 0;
        int res = -1;
        while (low <= height){
            mid = (low + height) / 2;
            if (arr[mid] >= num){
                res = mid;
                height = mid - 1;
            }else{
                low = mid + 1;
            }
        }
        return res;
    }

   public static int maxLength2(int[] arr, int K){
        int[] minSums = new int[arr.length];
        int[] minSumsEnds = new int[arr.length];

        minSums[arr.length - 1] = arr[arr.length - 1];
        minSumsEnds[arr.length - 1] = arr.length - 1;

        for (int i = arr.length -2; i >= 0; i--){
            if (minSums[i + 1] < 0){
                minSums[i] = arr[i] + minSums[i + 1];
                minSumsEnds[i] = minSumsEnds[i + 1];
            }else{
                minSums[i] = arr[i];
                minSumsEnds[i] = i;
            }
        }

        int end = 0;
        int sum = 0;
        int ans = 0;

        for (int i = 0; i < arr.length; i++){

            while (end < arr.length && sum + minSums[end] <= K){
                sum += minSums[end];
                end = minSumsEnds[end] + 1;
            }
            ans = Math.max(ans, end - i);
            if (end > i){
                sum -= arr[i];
            }else{
                end = i + 1;
            }
        }
        return ans;
   }

    // for test
    public static int[] generateRandomArray(int len, int maxValue) {
        int[] res = new int[len];
        for (int i = 0; i != res.length; i++) {
            res[i] = (int) (Math.random() * maxValue) - (maxValue / 3);
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println("test begin");
        for (int i = 0; i < 10000000; i++) {
            int[] arr = generateRandomArray(10, 20);
            int k = (int) (Math.random() * 20) - 5;
            if (maxLength1(arr, k) != maxLength2(arr, k)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }
}
