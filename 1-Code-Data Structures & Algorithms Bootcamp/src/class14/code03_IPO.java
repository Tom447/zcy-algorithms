package class14;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class code03_IPO {

    public static class Program{
        public int cost;
        public int profit;

        public Program(int cost, int profit){
            this.cost = cost;
            this.profit = profit;
        }
    }


    public static class MinComparator implements Comparator<Program>{

        @Override
        public int compare(Program o1, Program o2) {
            return o1.cost - o2.cost;
        }
    }

    public static class MaxComparator implements Comparator<Program>{

        @Override
        public int compare(Program o1, Program o2) {
            return o2.profit - o1.profit;
        }
    }

    public static int getIPO1(int[] cost, int[] profit, int W, int K){

        PriorityQueue<Program> minCost = new PriorityQueue<>(new MinComparator());
        PriorityQueue<Program> maxProfit = new PriorityQueue<>(new MaxComparator());

        for (int i = 0; i < cost.length; i++){
            minCost.add(new Program(cost[i], profit[i]));
        }

        for (int i = 0; i < K; i++){

            while(!minCost.isEmpty() && minCost.peek().cost <= W){
                maxProfit.add(minCost.poll());
            }

            if (maxProfit.isEmpty()){
                return W;
            }

            W += maxProfit.poll().profit;
        }
        return W;
    }

    public static int getIPO2(int[] cost, int[] profit, int W, int K){
        List<Program> arr = new ArrayList<>();
        for (int i = 0; i < cost.length; i++){
            arr.add(new Program(cost[i], profit[i]));
        }
        return Process(arr, W, K);
    }

    public static List<Program> CopyAndExcept(List<Program> arr, int index){
        List<Program> list = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++){
            if (i != index){
                list.add(arr.get(i));
            }
        }
        return list;
    }

    public static int Process(List<Program> arr, int W, int K){
        if (K == 0 || arr.isEmpty()){
            return W;
        }

        int max = W;
        for (int i = 0; i < arr.size(); i++){
            Program first = arr.get(i);
            if (first.cost <= W){
                List<Program> nexts = CopyAndExcept(arr, i);
                max = Math.max(max, Process(nexts, W- first.cost + first.profit, K - 1));
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int testTimes = 1000;     // 测试次数
        int maxProject = 5;       // 每次最多多少个项目
        int maxValue = 100;       // 成本和利润最大值
        int maxW = 200;           // 初始资金最大值
        int maxK = 3;             // 最多投资项目数

        System.out.println("测试开始");

        for (int i = 0; i < testTimes; i++) {
            // 随机项目数量
            int projectNum = (int) (Math.random() * maxProject) + 1;

            // 随机生成成本和利润数组
            int[] cost = new int[projectNum];
            int[] profit = new int[projectNum];

            for (int j = 0; j < projectNum; j++) {
                cost[j] = (int) (Math.random() * maxValue);      // 成本范围 [0, maxValue)
                profit[j] = (int) (Math.random() * maxValue);    // 收益范围 [0, maxValue)
            }

            // 随机初始资金 W 和最多投资次数 K
            int W = (int) (Math.random() * maxW);
            int K = (int) (Math.random() * maxK) + 1;

            // 拷贝一份用于测试
            int ans1 = getIPO1(cost, profit, W, K);

            // 注意：getIPO2 修改了 List 的内容，所以不能直接复用原数据
            int ans2 = getIPO2(cost, profit, W, K);

            if (ans1 != ans2) {
                System.out.println("Oops!");
                System.out.println("W = " + W + ", K = " + K);
                System.out.print("cost = ");
                printArray(cost);
                System.out.print("profit = ");
                printArray(profit);
                System.out.println("getIPO1 = " + ans1);
                System.out.println("getIPO2 = " + ans2);
                break;
            }
        }

        System.out.println("测试结束");
    }

    // 打印数组辅助方法
    private static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
