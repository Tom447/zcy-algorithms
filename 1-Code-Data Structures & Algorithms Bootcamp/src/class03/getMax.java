package class03;

public class getMax {

    public static int getMax(int[] arr){
        return process(arr, 0, arr.length-1);
    }

    public static int process(int[] arr, int l, int r){
        if (l == r){
            return arr[l];
        }
        int mid = l + (r - l) / 2;
        int leftMax = process(arr, l, mid);
        int rightMax = process(arr, mid + 1, r);
        return leftMax > rightMax ? leftMax : rightMax;
    }


    public static void main(String[] args) {
        int[] arr = {1 ,2, 3, 4, 5, 5};
        System.out.println(getMax(arr));
        return;
    }
}
