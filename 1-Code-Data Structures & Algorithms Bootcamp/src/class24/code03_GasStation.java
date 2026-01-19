package class24;

import java.util.LinkedList;
import java.util.Random;

public class code03_GasStation {

    public static void main(String[] args) {
        int testTime = 1000; // 测试次数
        int maxLen = 100;    // 加油站最大数量
        int maxValue = 100;  // 汽油和油耗最大值
        boolean succeed = true;

        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * maxLen) + 1;
            int[] gas = randomArray(N, maxValue);
            int[] cost = randomArray(N, maxValue);

            int expected = bruteForce(gas, cost);
            int actual = canCompleteCircuit(gas, cost);

            if (expected != actual) {
                succeed = false;
                System.out.println("Oops!");
                System.out.println("gas: " + toString(gas));
                System.out.println("cost: " + toString(cost));
                System.out.println("Expected: " + expected);
                System.out.println("Actual: " + actual);
                break;
            }
        }

        System.out.println(succeed ? "Nice!" : "Fucking fucked up!");
    }


    // 暴力解法 O(N^2)
    public static int bruteForce(int[] gas, int[] cost) {
        int N = gas.length;
        for (int start = 0; start < N; start++) {
            int fuel = 0;
            boolean ok = true;
            for (int i = 0; i < N; i++) {
                int idx = (start + i) % N;
                fuel += gas[idx] - cost[idx];
                if (fuel < 0) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                return start;
            }
        }
        return -1;
    }
    // 测试链接：https://leetcode.com/problems/gas-station
    // 这个方法的时间复杂度O(N)，额外空间复杂度O(N)
    public static int canCompleteCircuit(int[] gas, int[] cost) {
        boolean[] good = goodArray(gas, cost);
        for (int i = 0; i < gas.length; i++) {
            if (good[i]) {
                return i;
            }
        }
        return -1;
    }

    public static boolean[] goodArray(int[] gas, int[] cost){
        int N = gas.length;
        int M = N * 2;
        int[] arr = new int[M];

        for (int i = 0; i < N; i++){
            arr[i] = gas[i] - cost[i];
            arr[i + N] = gas[i] - cost[i];
        }

        for (int i = 1; i < M; i++){
            arr[i] += arr[i - 1];
        }

        LinkedList<Integer> qmin = new LinkedList<>();

        for (int i = 0; i < N; i++){
            while (!qmin.isEmpty() && arr[qmin.peekLast()] >= arr[i]){
                qmin.pollLast();
            }
            qmin.addLast(i);
        }

        boolean[] ans = new boolean[N];

        for (int offet = 0, i = 0, j = N; j < M; offet = arr[i++], j++){
            if (arr[qmin.peekFirst()] - offet >= 0){
                ans[i] = true;
            }

            if (qmin.peekFirst() == i){
                qmin.pollFirst();
            }

            while (!qmin.isEmpty() && arr[qmin.peekLast()] >= arr[j]){
                qmin.pollLast();
            }
            qmin.addLast(j);
        }

        return ans;
    }

    // 生成随机数组
    public static int[] randomArray(int len, int max) {
        int[] res = new int[len];
        Random rand = new Random();
        for (int i = 0; i < len; i++) {
            res[i] = rand.nextInt(max);
        }
        return res;
    }

    // 打印数组
    public static String toString(int[] arr) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i != arr.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
