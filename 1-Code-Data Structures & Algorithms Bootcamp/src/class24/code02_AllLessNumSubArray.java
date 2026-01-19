package class24;

import java.util.LinkedList;
import java.util.Random;

public class code02_AllLessNumSubArray {

    public static int right(int[] arr, int sum){
        if (arr == null || arr.length == 0 || sum < 0){
            return 0;
        }
        int N = arr.length;
        int count = 0;
        for (int L = 0; L < N; L++){
            for (int R = L; R < N; R++){
                int max = arr[L];
                int min = arr[L];
                for (int i = L+1; i <= R; i++){
                    max = Math.max(max, arr[i]);
                    min = Math.min(min, arr[i]);
                }

                if (max - min <= sum){
                    count++;
                }
            }
        }
        return count;
    }

    public static int getAllLessNumSubArray(int[] arr, int sum){
        if (arr == null || arr.length == 0 || sum < 0){
            return 0;
        }

        int N = arr.length;
        int count = 0;
        int R = 0;
        LinkedList<Integer> qmax = new LinkedList<>();
        LinkedList<Integer> qmin = new LinkedList<>();
        for (int L = 0; L < N; L++){
            while (R < N){
                if (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[R]){
                    qmax.pollLast();
                }
                qmax.add(R);
                if (!qmin.isEmpty() && arr[qmin.peekLast()] >= arr[R]){
                    qmin.pollLast();
                }
                qmin.add(R);

                if (arr[qmax.peekFirst()] - arr[qmin.peekFirst()] > sum){
                    break;
                }else {
                    R++;
                }

                count += R - L;
                if (qmax.peekFirst() == L){
                    qmax.pollFirst();
                }
                if (qmin.peekFirst() == L){
                    qmin.pollFirst();
                }
            }
        }
        return count;
    }

    // 生成随机数组
    public static int[] generateRandomArray(int maxLength, int maxValue) {
        int len = (int) (Math.random() * (maxLength + 1));
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            // 可能正负都有
            arr[i] = (int) Math.random() * (maxValue + 1);
        }
        return arr;
    }

    // 打印数组（调试用）
    public static void printArray(int[] arr) {
        if (arr == null) {
            System.out.println("null");
            return;
        }
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    // 测试主函数
    public static void main(String[] args) {
        int testTime = 10000;     // 测试次数
        int maxLength = 100;      // 数组最大长度
        int maxValue = 50;        // 元素最大值
        boolean succeed = true;

        System.out.println("开始测试...");

        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxLength, maxValue);
            int sum = new Random().nextInt(maxValue + 1);

            int ans1 = right(arr, sum);
            int ans2 = getAllLessNumSubArray(arr, sum);

            if (ans1 != ans2) {
                succeed = false;
                System.out.println("出错啦！");
                printArray(arr);
                System.out.println("sum = " + sum);
                System.out.println("暴力解法结果：" + ans1);
                System.out.println("高效解法结果：" + ans2);
                break;
            }
        }

        System.out.println(succeed ? "测试通过！" : "测试失败！");
    }
}
