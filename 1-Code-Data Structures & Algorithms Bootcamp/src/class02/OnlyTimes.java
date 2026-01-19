package class02;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class OnlyTimes {
    public static int OnlyKTimes(int[] arr, int k, int m){
        int[] vec = new int[32];

        for (int num : arr){
            for (int i = 0; i < 32; i++){
                vec[i] += (num >> i) & 1;
            }
        }
        int ans = 0;
        for (int i = 0; i < 32; i++){
            if (vec[i] % m != 0 && vec[i] % m == k){
                ans |= (1 << i);
            }
        }
        //考虑ans为0的情况
        if(ans == 0){
            int count = 0;
            for (int num : arr){
                if (num == 0){
                    count++;
                }
            }
            if (count == k){
                return 0;
            }else{
                return -1;
            }
        }
        return ans;
    }



    public static void main(String[] args) {
        int testTime = 5000;
        //随机生成k和m   需要总的种类数量maxKind 每种数的大小范围range  还有k or m的最大值的界
        int maxKinds = 10;
        int range = 20;
        int maxValue = 5;
        for (int i = 0; i < testTime; i++){
            Random random = new Random();
            int a = random.nextInt(maxValue) + 1;
            int b = random.nextInt(maxValue) + 1;
            int k = Math.min(a, b);
            int m = Math.max(a, b);
            if (k == m){
                m++;
            }
            int[] arr = generateRandomArray(maxKinds, k, m, range);
            if (OnlyKTimes(arr, k, m) != test(arr, k, m)){
                System.out.println("no=============================");
                printArray(arr);
                System.out.println("maxKinds: "+maxKinds + " " +"m = " +m + " k = " + k);
                System.out.println(OnlyKTimes(arr, maxKinds, m) + " " + test(arr, maxKinds, m));
                System.out.println("no=============================");
                break;
            }
        }
    }

    private static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    private static int[] generateRandomArray(int maxKinds, int k, int m, int range) {
        Random random = new Random();
        //某个数出现了k次
        int kTimesNum = random.nextInt(range);
        //得到数字种类的区间[2, maxkinds]
        int kindsNum = random.nextInt(maxKinds - 1) + 2;
        //k可能出现也可能不出现， 如果出现那就是k次，如果不出现那就出现[1,m-1]次
        int time = random.nextDouble() < 0.5 ? k : random.nextInt(m-1) + 1;
        //数组的长度是time + (kindsNum - 1) * m
        int[] arr = new int[time + (kindsNum - 1) * m];
        //先填满time次
        int index = 0;
        for (;index < time; index++){
            arr[index] = kTimesNum;
        }
        //某数出现k次，其中某数已经占据了一个kindnum位置
        kindsNum--;
        //防止重复
        HashSet<Integer> set = new HashSet<>();
        set.add(kTimesNum);
        //填满arr
        while(kindsNum != 0){
            int num = 0;
            do{
                num = random.nextInt(2 * range - 1) - range;
            }while (set.contains(num));
            set.add(num);
            kindsNum--;
            for (int i = 0; i < m; i++){
                arr[index ++] = num;
            }
        }
//        //arr填满后，随机交换
//        for (int i = 0; i < arr.length; i++){
//            int j = random.nextInt(arr.length);
//            int temp = arr[i];
//            arr[i] = arr[j];
//            arr[j] = temp;
//        }
        return arr;

    }


    private static int test(int[] arr, int k, int m) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : arr){
            if (map.containsKey(num)){
                map.put(num, map.get(num) + 1);
            }else{
                map.put(num, 1);
            }
        }
        for (int num : map.keySet()){
            if (map.get(num) == k){
                return num;
            }
        }
        return -1;
    }
}
