package class25;

import java.util.Stack;

public class code04_CountSubmatricesWithAllOnes {

    public static int numSubmat(int[][] mat){
        if (mat == null || mat.length == 0 || mat[0].length == 0){
            return 0;
        }
        int nums = 0;
        int[] height = new int[mat[0].length];

        for (int i = 0; i < mat.length; i++){
            for (int j = 0; j < mat[0].length; j++){
                height[j] = mat[i][j] == 0 ? 0 : height[j] + 1;
            }
            nums +=  countFromBottom(height);
        }

        return nums;
    }

    public static int countFromBottom(int[] arr){
        if (arr == null || arr.length == 0){
            return 0;
        }
        int N = arr.length;
        int nums = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < N; i++){
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]){
                int cur = stack.pop();
                if (arr[cur] > arr[i]){
                    int left = stack.isEmpty() ? -1 : stack.peek();
                    int n = i - left - 1;
                    int down = Math.max(left == -1 ? 0 : arr[left], arr[i]);
                    nums += (arr[cur] - down) * count(n);
                }
            }
            stack.push(i);
        }

        while (!stack.isEmpty()){
            int cur = stack.pop();
            int left = stack.isEmpty() ? -1 : stack.peek();
            int n = N - left -1;
            int down = left == -1 ? 0 : arr[left];
            nums += (arr[cur] - down) * count(n);
        }
        return nums;
    }

    public static int count(int n){
        return (n*(n + 1)) >> 1;
    }
}
