package class31;

import java.util.*;

public class code02_FallingSquares {


    public static HashMap<Integer,Integer> index(int[][] position){
        TreeSet<Integer> pos = new TreeSet<>();
        for (int[] arr : position){
            pos.add(arr[0]);
            pos.add(arr[0] + arr[1] - 1);
        }

        HashMap<Integer, Integer> map = new HashMap<>();
        int count = 0;
        for (Integer index : pos){
            map.put(index, ++count);
        }

        return map;
    }

    public static class Right{
       private int[] max;


        public Right(int N) {
            max = new int[N + 1];
        }

        public void update(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                max[i] = C;
            }
        }

        public int query(int L, int R) {
            int res = 0;
            for (int i = L; i <= R; i++) {
                res = Math.max(res, max[i]);
            }
            return res;
        }
    }

    public List<Integer> fallingSquares1(int[][] positions) {
        HashMap<Integer, Integer> map = index(positions);
        int N = map.size();

        Right right = new Right(N);
        int max = 0;
        List<Integer> res = new ArrayList<>();
        for (int[] arr : positions) {
            int L = map.get(arr[0]);
            int R = map.get(arr[0] + arr[1] - 1);
            int height = right.query(L, R) + arr[1];
            max = Math.max(max, height);
            res.add(max);
            right.update(L, R, height);
        }
        return res;
    }

    public static class SegmentTree{
        private int[] max;
        private int[] change;
        private boolean[] update;

        public SegmentTree(int size){
            int N = size + 1;
            max = new int[4 * N];
            change = new int[4 * N];
            update = new boolean[4 * N];
        }

        public void pushUp(int rt){
            max[rt] = Math.max(max[rt * 2],max[rt * 2 + 1]);
        }

        private void pushDown(int rt, int ln, int rn){
            if (update[rt]){
                update[rt * 2] = true;
                update[rt * 2 + 1] = true;
                change[rt * 2] = change[rt];
                change[rt * 2 + 1] = change[rt];
                max[rt * 2] = change[rt];
                max[rt * 2 + 1] = change[rt];
                update[rt] = false;
            }
        }


        public void update(int L, int R, int C, int l, int r, int rt){
            if (L <= l && r <= R){
                update[rt] = true;
                change[rt] = C;
                max[rt] = C;
                return;
            }
            int mid = (l + r) / 2;
            pushDown(rt, mid - l + 1, l - mid);
            if (L <= mid){
                update(L, R, C, l, mid, rt * 2);
            }

            if (R > mid){
                update(L, R, C, mid+1, r, rt * 2 + 1);
            }

            pushUp(rt);
        }

        public int query(int L, int R, int l, int r, int rt) {
            if (L <= l && r <= R) {
                return max[rt];
            }
            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);
            int left = 0;
            int right = 0;
            if (L <= mid) {
                left = query(L, R, l, mid, rt << 1);
            }
            if (R > mid) {
                right = query(L, R, mid + 1, r, rt << 1 | 1);
            }
            return Math.max(left, right);
        }
    }


    public List<Integer> fallingSquares2(int[][] positions) {
        HashMap<Integer, Integer> map = index(positions);
        int N = map.size();
        SegmentTree segmentTree = new SegmentTree(N);
        int max = 0;
        List<Integer> res = new ArrayList<>();
        // 每落一个正方形，收集一下，所有东西组成的图像，最高高度是什么
        for (int[] arr : positions) {
            int L = map.get(arr[0]);
            int R = map.get(arr[0] + arr[1] - 1);
            int height = segmentTree.query(L, R, 1, N, 1) + arr[1];
            max = Math.max(max, height);
            res.add(max);
            segmentTree.update(L, R, height, 1, N, 1);
        }
        return res;
    }

    public static void main(String[] args) {
        int testCases = 100; // 测试案例的数量
        int maxSize = 10; // 每个测试案例中方块的最大数量

        Random random = new Random();

        code02_FallingSquares solver = new code02_FallingSquares();

        for (int i = 0; i < testCases; i++) {
            // 生成随机测试案例
            int size = random.nextInt(maxSize) + 1;
            int[][] positions = new int[size][2];
            for (int j = 0; j < size; j++) {
                positions[j][0] = random.nextInt(10) + 1; // 随机起点位置
                positions[j][1] = random.nextInt(5) + 1; // 随机长度
            }

            // 分别调用两种方法
            List<Integer> result1 = solver.fallingSquares1(positions);
            List<Integer> result2 = solver.fallingSquares2(positions);

            // 比较结果
            if (!result1.equals(result2)) {
                System.out.println("Test case " + (i + 1) + " failed.");
                printArray(positions);
                System.out.println("Result 1: " + result1);
                System.out.println("Result 2: " + result2);
                return;
            }
        }
        System.out.println("All tests passed!");
    }

    private static void printArray(int[][] array) {
        for (int[] row : array) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
}
