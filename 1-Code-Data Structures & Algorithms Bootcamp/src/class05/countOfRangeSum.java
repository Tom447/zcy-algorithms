package class05;

// 这道题直接在leetcode测评：
// https://leetcode.com/problems/count-of-range-sum/
public class countOfRangeSum {

    public static int countRangeSum(int[] nums, int lower, int upper){
        if (nums == null && nums.length == 0){
            return 0;
        }
        long[] sums = new long[nums.length];
        sums[0] = nums[0];
        for (int i = 1; i < sums.length; i++){
            sums[i] = sums[i - 1] + nums[i];
        }
        return process(sums, 0, sums.length - 1, lower, upper);
    }

    private static int process(long[] sums, int l, int r, int lower, int upper) {
        if (l == r){
            return sums[l] >= lower && sums[l] <= upper ? 1 : 0;
        }
        int m = l + (r - l) / 2;
        return process(sums, l, m, lower, upper) + process(sums, m+1, r, lower, upper)
                + merge(sums, l, m, r, lower, upper);

    }

    private static int merge(long[] sums, int l, int m, int r, int lower, int upper) {


        int windowL = l;
        int windowR = l;
        int ans = 0;
        for(int i = m+1; i <= r; i++){

            long min = sums[i] - lower;
            long max = sums[i] - upper;
            // windowR)
            while(windowR <= m && sums[windowR] <= max){
                windowR++;
            }
            //[windowL
            while (windowL <= m && sums[windowL] < min){
                windowL++;
            }

            ans += windowR - windowL;
        }

        long[] help = new long[sums.length];
        int p1 = l;
        int p2 = m + 1;
        int index = 0;
        while(p1 <= m && p2 <= r){
            help[index++ ] = sums[p1] <= sums[p2] ? sums[p1++] : sums[p2++];
        }

        while(p1 <= m){
            help[index++] = sums[p1++];
        }

        while (p2 <= r){
            help[index++] = sums[p2++];
        }

        for(int i = 0; i < help.length; i++){
            sums[i + l] = help[i];
        }

        return ans;
    }

}
