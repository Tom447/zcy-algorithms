package class07;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

public class code01_maxCover {

    public static void main(String[] args) {
        int testTimes = 5000;
        int maxLen = 100;
        int L = 10;
        int R = 10;
        for (int i = 0; i < testTimes; i++){
            int[][] arr = generateRandomArray(maxLen, L, R);

            if (arr == null || arr.length == 0){
                continue;
            }

            int o1 = maxCover1(arr);
            int o2 = maxCover2(arr);

            if (o1 != o2){
                System.out.println("oops");
                break;
            }
        }
    }



    public static  class LComparator implements Comparator<Line> {

        @Override
        public int compare(Line o1, Line o2) {
            return o1.L - o2.L;
        }
    }


    public static class Line{
        private int L;
        private int R;

        public Line(int l, int r){
            this.L = l;
            this.R = r;
        }
    }


    private static int maxCover2(int[][] arr) {
        Line[] line = new Line[arr.length];

        for (int i = 0; i < arr.length; i++){
            line[i] = new Line(arr[i][0], arr[i][1]);
        }

        Arrays.sort(line, new LComparator());

        PriorityQueue<Integer> heap = new PriorityQueue<>();

        int max = 0;

        for (int i = 0; i < line.length; i++){
            while (!heap.isEmpty() && heap.peek() <= line[i].L){
                heap.poll();
            }
            heap.add(line[i].R);
            max = Math.max(max, heap.size());
        }

        return max;
    }

    private static int maxCover1(int[][] arr) {
        int len = arr.length;
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < len; i++){
            min = Math.min(arr[i][0], min);
            max = Math.max(arr[i][1], max);
        }

        int coverNum = 0;
        for (double p = min + 0.5; p < max; p++){
            int cur = 0;
            for (int i = 0; i < len; i++){
                if (arr[i][0] < p && arr[i][1] > p){
                    cur++;
                }
            }
            coverNum = Math.max(coverNum, cur);
        }
        
        return coverNum;
    }

    private static int[][] generateRandomArray(int maxLen, int L, int R) {
        Random random = new Random();

        int len = random.nextInt(maxLen + 1);

        int[][] arr = new int[len][2];

        for (int i = 0; i < len; i++){
            int a = L + random.nextInt(R - L + 1);
            int b = L + random.nextInt(R - L + 1);
            if (a == b){
                b = a + 1;
            }
            arr[i][0] = Math.min(a, b);
            arr[i][1] = Math.max(a, b);
        }
        return arr;
    }
}
