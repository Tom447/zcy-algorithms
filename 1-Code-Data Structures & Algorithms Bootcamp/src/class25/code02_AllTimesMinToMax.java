package class25;

import java.util.Stack;

public class code02_AllTimesMinToMax {

    public static int max1(int[] arr){

        int N = arr.length;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < N; i++){
            for (int j = i; j < N; j++){
                int minNum = Integer.MAX_VALUE;
                int sum = 0;
                for (int k = i; k <= j; k++){
                    sum += arr[k];
                    minNum = Math.min(minNum, arr[k]);
                }
                max = Math.max(max, sum * minNum);
            }
        }

        return max;
    }


    public static int max2(int[] arr){
        int N = arr.length;
        int[] sums = new int[N];

        sums[0] = arr[0];

        for (int i = 1; i < N; i++){
            sums[i] = sums[i - 1] + arr[i];
        }


        int max = Integer.MIN_VALUE;
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < N; i++){
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]){
                int j = stack.pop();
                max  = Math.max(max, stack.isEmpty() ? sums[i-1] * arr[j] : (sums[i-1] - sums[stack.peek()]) * arr[j]);
            }
            stack.push(i);
        }

        while (!stack.isEmpty()){
            int j = stack.pop();
            max = Math.max(max, stack.isEmpty() ? sums[N-1] * arr[j] : (sums[N-1] - sums[stack.peek()]) * arr[j]);
        }
        return max;
    }

    public static int[] gerenareRondomArray() {
        int[] arr = new int[(int) (Math.random() * 20) + 10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 101);
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTimes = 2000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = gerenareRondomArray();
            if (max1(arr) != max2(arr)) {
                System.out.println("FUCK!");
                break;
            }
        }
        System.out.println("test finish");
    }
}
