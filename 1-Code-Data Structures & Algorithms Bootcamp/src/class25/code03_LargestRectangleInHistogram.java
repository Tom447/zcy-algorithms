package class25;

import java.util.Stack;

public class code03_LargestRectangleInHistogram {


    public static int largestRectangleArea1(int[] arr){
        if (arr == null || arr.length == 0){
            return 0;
        }

        int N = arr.length;
        int maxArea = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < N; i++){
            while (!stack.isEmpty() && arr[i] <= arr[stack.peek()]){
                int j = stack.pop();
                int k = stack.isEmpty() ? -1 : stack.peek();
                int curArea = (i - k - 1) * arr[j];
                maxArea = Math.max(curArea, maxArea);
            }
            stack.push(i);
        }
        while (!stack.isEmpty()){
            int j = stack.pop();
            int k = stack.isEmpty() ? -1 : stack.peek();
            int curArea = (N - k - 1) * arr[j];
            maxArea = Math.max(maxArea, curArea);
        }
        return maxArea;
    }
}
