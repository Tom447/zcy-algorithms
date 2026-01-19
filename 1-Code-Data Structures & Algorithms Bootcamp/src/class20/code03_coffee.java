package class20;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class code03_coffee {

    // 题目
// 数组arr代表每一个咖啡机冲一杯咖啡的时间，每个咖啡机只能串行的制造咖啡。
// 现在有n个人需要喝咖啡，只能用咖啡机来制造咖啡。
// 认为每个人喝咖啡的时间非常短，冲好的时间即是喝完的时间。
// 每个人喝完之后咖啡杯可以选择洗或者自然挥发干净，只有一台洗咖啡杯的机器，只能串行的洗咖啡杯。
// 洗杯子的机器洗完一个杯子时间为a，任何一个杯子自然挥发干净的时间为b。
// 四个参数：arr, n, a, b
// 假设时间点从0开始，返回所有人喝完咖啡并洗完咖啡杯的全部过程结束后，至少来到什么时间点。
    public static int right(int[] arr, int n, int a, int b){
        int[] times = new int[arr.length];
        int[] drinks = new int[n];
        return forceMake(arr,times, 0, drinks, n, a, b);
    }

//    arr	int[]	所有咖啡机做一杯咖啡的时间（如 [2,3] 表示两台咖啡机）
//    times	int[]	每台咖啡机当前可用的时间点（初始都是0）
//    kth	int	当前正在处理第几个用户（比如 kth=0 是第一个用户）
//    drink	int[]	存储每个用户喝到咖啡的时间点
//    n	int	总共要处理的用户数量
//    a	int	使用洗杯机清洗一个杯子的时间
//    b	int	杯子自然挥发干净的时间
    public static int forceMake(int[] arr, int[] times, int kth, int[] drinks, int n, int a, int b){
        if (kth == n){
            int[] drinkSorted = Arrays.copyOf(drinks, kth);
            Arrays.sort(drinkSorted);
            return forceWash(drinkSorted, a, b, 0, 0, 0);
        }
        int N = arr.length;
        int time = Integer.MAX_VALUE;
        for (int i=0; i < N; i++){
            //取一个咖啡机的工作时间
            int work = arr[i];
            //pre为当前咖啡机可以使用的时间点
            int pre = times[i];
            //更新当前该咖啡机的下次可用时间
            times[i] = pre + work;
            //更新kth这个用户
            drinks[kth] = pre + work;
            //递归处理下一个用户
            time = Math.min(time, forceMake(arr, times, kth+1, drinks, n, a, b));
            //回溯后方便kth用户选择下一个咖啡机
            drinks[kth] = 0;
            times[i] = pre;
        }
        return time;
    }

//    drinks	int[]	每个用户喝完咖啡的时间（已排序）
//    a	int	使用洗杯机清洗一个杯子所需时间（串行）
//    b	int	杯子自然挥发干净所需时间（并行）
//    index	int	当前正在处理第几个杯子（从0开始）
//    washLine	int	洗杯机下一次可用的时间点
//    time	int	当前为止的整体最大结束时间
    public static int forceWash(int[] drinks, int a, int b, int index, int washLine, int time) {
        if (index == drinks.length){
            return time;
        }
        //index号咖啡杯选择用咖啡机洗干净
        int wash = Math.max(drinks[index], washLine) + a;
        int ans1 =  forceWash(drinks, a, b,  index + 1, wash, Math.max(wash, time));

        //index号杯子选择风干干净
        int dry = drinks[index] + b;
        int ans2 = forceWash(drinks, a, b, index+1, washLine, Math.max(dry, time));

        return Math.min(ans1, ans2);
    }



    public static class Machine{
        public int timePoint;
        public int workTime;

        public Machine(int t, int w){
            this.timePoint = t;
            this.workTime = w;
        }
    }


    public static class MachineComparator implements Comparator<Machine>{

        @Override
        public int compare(Machine o1, Machine o2) {
            return (o1.workTime + o1.timePoint) - (o2.workTime + o2.timePoint);
        }
    }

    public static int minTime1(int[] arr, int n, int a, int b){
        PriorityQueue<Machine> queue = new PriorityQueue<>(new MachineComparator());

        for (int i=0; i < arr.length ; i++){
            queue.add(new Machine(0, arr[i]));
        }

        int[] drinks = new int[n];
        for (int i=0; i < n; i++){
            Machine cur = queue.poll();
            cur.timePoint += cur.workTime;
            drinks[i] = cur.timePoint;
            queue.add(cur);
        }
        return bestTime1(drinks, a, b, 0, 0);
    }
    // drinks 所有杯子可以开始洗的时间
    // wash 单杯洗干净的时间（串行）
    // air 挥发干净的时间(并行)
    // washine 洗的机器什么时候可用
    // drinks[index.....]都变干净，最早的结束时间（返回）
    public static int bestTime1(int[] drinks, int wash, int air, int index, int washLine){
        if (index == drinks.length){
            return 0;
        }

        //index使用洗的
        int selfClean1 = Math.max(drinks[index], washLine) + wash;
        int restClean1 = bestTime1(drinks, wash, air, index+1, selfClean1);
        int p1 = Math.max(selfClean1, restClean1);
        //index不使用洗的
        int selfClean2 = drinks[index] + air;
        int restClean2 = bestTime1(drinks, wash, air, index+1, selfClean2);
        int p2 = Math.max(selfClean2, restClean2);

        return Math.min(p1, p2);
    }


    public static int minTime2(int[] arr, int n, int a, int b){
        PriorityQueue<Machine> queue = new PriorityQueue<>(new MachineComparator());

        for (int i=0; i < arr.length ; i++){
            queue.add(new Machine(0, arr[i]));
        }

        int[] drinks = new int[n];
        for (int i=0; i < n; i++){
            Machine cur = queue.poll();
            cur.timePoint += cur.workTime;
            drinks[i] = cur.timePoint;
            queue.add(cur);
        }
        return bestTimeDp(drinks, a, b);
    }

    public static int bestTimeDp(int[] drinks, int wash, int air){
        int N = drinks.length;
        int maxFree = 0;
        for (int i = 0; i < drinks.length; i++){
            maxFree = Math.max(maxFree, drinks[i]) + wash;
        }

        //index的区间是[0, N], free的范围是[0, maxFree]  其中index依赖于index+1
        int[][] dp = new int[N + 1][maxFree + 1];
        //index = N的时候，初始化dp[N][?] = 0;
        for (int index = N-1; index >= 0; index--){
            for (int washLine = 0; washLine <= maxFree; washLine++){
                //index使用洗的
                int selfClean1 = Math.max(drinks[index], washLine) + wash;
                if (selfClean1 > maxFree){
                    continue;
                }
                int restClean1 = dp[index+1][selfClean1];
                int p1 = Math.max(selfClean1, restClean1);


                //index不使用洗的
                int selfClean2 = drinks[index] + air;
                int restClean2 = dp[index+1][selfClean2];
                int p2 = Math.max(selfClean2, restClean2);

                int ans = Math.min(p1, p2);
                dp[index][washLine] = Math.min(p1, p2);
            }
        }
        return dp[0][0];
    }



    // for test
    public static int[] randomArray(int len, int max) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * max) + 1;
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        System.out.print("arr : ");
        for (int j = 0; j < arr.length; j++) {
            System.out.print(arr[j] + ", ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int len = 10;
        int max = 10;
        int testTime = 10;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(len, max);
            int n = (int) (Math.random() * 7) + 1;
            int a = (int) (Math.random() * 7) + 1;
            int b = (int) (Math.random() * 10) + 1;
            int ans1 = right(arr, n, a, b);
            int ans2 = minTime1(arr, n, a, b);
            int ans3 = minTime2(arr, n, a, b);
            if (ans1 != ans2 || ans2 != ans3) {
                printArray(arr);
                System.out.println("n : " + n);
                System.out.println("a : " + a);
                System.out.println("b : " + b);
                System.out.println(ans1 + " , " + ans2 + " , " + ans3);
                System.out.println("===============");
                break;
            }
        }
        System.out.println("测试结束");

    }


}
