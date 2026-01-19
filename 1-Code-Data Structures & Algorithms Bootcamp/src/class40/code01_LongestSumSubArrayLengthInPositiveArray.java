package class40;

public class code01_LongestSumSubArrayLengthInPositiveArray {


    public static int getMaxLength(int[] arr, int K){
        if (arr == null || arr.length == 0 || K < 0){
            return 0;
        }
        int left = 0;
        int right = 0;
        int sum = arr[0];
        int maxLen = 0;
        while (right < arr.length){
            if (sum == K){
                maxLen = Math.max(maxLen, right - left + 1);
                sum -= arr[left++];
            }else if (sum < K){
                right++;
                if (right == arr.length){
                    break;
                }
                sum += arr[right];
            }else{
                sum -= arr[left++];
            }
        }
        return maxLen;
    }

    // for test
    public static int right(int[] arr, int K) {
        int max = 0;
        int[] sums = new int[arr.length + 1];

        for (int i = 0; i < arr.length; i++){
            sums[i + 1] = sums[i] + arr[i];
        }

        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if (valid(sums, i, j, K)) {
                    max = Math.max(max, j - i + 1);
                }
            }
        }
        return max;
    }

    // for test
    public static boolean valid(int[] arr, int L, int R, int K) {
        int sum = arr[R + 1] - arr[L];
        return sum == K;
    }

    // for test
    public static int[] generatePositiveArray(int size, int value) {
        int[] ans = new int[size];
        for (int i = 0; i != size; i++) {
            ans[i] = (int) (Math.random() * value) + 1;
        }
        return ans;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int len = 50;
        int value = 100;
        int testTime = 500000;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generatePositiveArray(len, value);
            int K = (int) (Math.random() * value) + 1;
            int ans1 = getMaxLength(arr, K);
            int ans2 = right(arr, K);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println("K : " + K);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("test end");
    }
}
