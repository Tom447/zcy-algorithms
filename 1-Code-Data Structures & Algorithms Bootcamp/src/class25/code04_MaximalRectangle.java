package class25;

import java.util.Stack;

public class code04_MaximalRectangle {

    public static int maximalRectangle(char[][] matrix){
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int maxArea = 0;
        int[] height = new int[matrix[0].length];

        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int i = 0; i < rows; i++){

            for (int j = 0; j < cols; j++){
                height[j] = matrix[i][j] == '0' ? 0 : height[j] + 1;
            }
            maxArea = Math.max(maxArea, maxRecFromBottom(height));
        }

        return maxArea;

    }


    public static int maxRecFromBottom(int[] arr){
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
