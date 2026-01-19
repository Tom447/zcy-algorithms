package class01;

public class BSAweSome {

    public static int BSAweSome(int[] arr){
        if (arr == null || arr.length == 0){
            return -1;
        }
        if (arr.length == 0 || arr[0] < arr[1]){
            return 0;
        }
        if (arr[arr.length-2] > arr[arr.length - 1]){
            return arr.length - 1;
        }
        int l = 1;
        int r = arr.length - 1;
        while(l < r){
            int mid = l + (r - l) / 2;
            if (arr[mid] > arr[mid - 1]){
                r = mid - 1;
            }else if(arr[mid] > arr[mid + 1]){
                l = mid + 1;
            }else{
                return mid;
            }
        }
        return l;
    }
}
