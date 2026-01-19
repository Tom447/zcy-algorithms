package class02;

public class EvenTimesOddTimes {

    public static void printOddTimesNum1(int[] arr){
        int eor = 0;
        for (int i = 0;i < arr.length; i++){
            eor ^= arr[i];
        }
        System.out.println(eor);
    }


    public static void printOddTimesNum2(int[] arr){
        int eor = 0;

        for(int i = 0; i < arr.length; i++){
            eor ^= arr[i];
        }
        int rightOne = eor & (-eor);
        int num1 = 0;
        for(int i = 0; i < arr.length; i++){
            if ((arr[i] & rightOne) != 0){
                num1 ^= arr[i];
            }
        }
        System.out.println(num1 + " " + (eor ^ num1));
    }

    //统计一个数中1的位数
    public static int bitCount(int num){
        int count = 0;
        while(num != 0){
            int rightOne = num & (-num);
            count++;
            num -= rightOne;
        }
        return count;
    }

    public static void main(String[] args) {

        int[] arr1 = { 3, 3, 2, 3, 1, 1, 1, 3, 1, 1, 1 };
        printOddTimesNum1(arr1);

        int[] arr2 = { 4, 3, 4, 2, 2, 2, 4, 1, 1, 1, 3, 3, 1, 1, 1, 4, 2, 2 };
        printOddTimesNum2(arr2);

    }

}
