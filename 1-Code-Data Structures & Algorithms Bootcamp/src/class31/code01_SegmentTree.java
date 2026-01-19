package class31;

public class code01_SegmentTree {

    public static class SegmentTree{
        private int MAXN;
        //origin为原始数组， arr是origin搬过来，但起始点是1
        private int[] arr;
        private int[] sum;
        private int[] lazy;
        private int[] change;
        private boolean[] update;

        public SegmentTree(int[] origin){
            MAXN = origin.length + 1;
            arr = new int[MAXN];

            for (int i = 1; i < MAXN; i++){
                arr[i] = origin[i - 1];
            }
            sum = new int[MAXN * 4];//当MAXN = 2^(?) + 1时， 对应sums需要4 * MAXN，这是最糟糕的情况
            lazy = new int[MAXN * 4];
            change = new int[MAXN * 4];
            update = new boolean[MAXN * 4];
        }

        //[l, r]范围对应的sums的索引为rt
        private void build(int l, int r, int rt){
            if (l == r){ //l = r的时候， sums[rt]对应arr[l]
                sum[rt] = arr[l];
                return;
            }
            int mid = (l + r) / 2;
            build(l, mid, rt * 2);
            build(mid + 1, r,rt * 2 + 1);
            pushUp(rt);
        }

        private void pushUp(int rt){
            sum[rt] = sum[rt * 2] + sum[rt * 2 + 1];
        }

//          每个节点都有一个布尔数组 update[]，用来记录它是否有一个“覆盖更新”的懒惰任务。
//          change[rt] 的作用是保存当前节点的覆盖更新值。
//              当你在 pushDown 中处理了 update 标记后，
//              意味着这个覆盖操作已经被下传给了子节点，因此对于当前节点来说，
//              它已经完成了它的任务。但是，并不需要将 change[rt] 设为 0 或者任何特定值，因为一旦
//              update[rt] 被设置为 false，表示当前节点不再持有未下传的覆盖更新任务，
//              此时 change[rt] 的具体值就变得无关紧要了，除非有新的覆盖更新操作再次修改它。

        //pushDown是懒惰标记下传函数
        //在进行区间查询和操作之前，将"未完成任务"下发给子节点，保证后面操作的数据是正确的
        private void pushDown(int rt, int ln, int rn){
            if (update[rt]){
                update[rt * 2] = true;
                update[rt * 2 + 1] = true;
                change[rt * 2] = change[rt];
                change[rt * 2 + 1] = change[rt];
                lazy[rt * 2] = 0;
                lazy[rt * 2 + 1] = 0;
                sum[rt * 2] = change[rt] * ln;
                sum[rt * 2 + 1] = change[rt] * rn;
                update[rt] = false;

            }
            if (lazy[rt] != 0){
                lazy[rt * 2] += lazy[rt];
                sum[rt * 2] += lazy[rt] * ln;
                lazy[rt * 2 + 1] += lazy[rt];
                sum[rt * 2 + 1] += lazy[rt] * rn;
                lazy[rt] = 0;
            }
        }


        //L ~ R 所有的值都变成C
        //l ~ r是arr的实际的范围，rt为sum的索引点
        public  void update(int L, int R, int C, int l, int r, int rt){
            //完全覆盖
//            完全覆盖（Current Task Can Be Lazy）：
//            如果当前节点的区间 [l, r] 完全被待更新区间 [L, R]
//            包含（即 L <= l && r <= R），则可以直接在这个节点上应用更新操作，
//            并设置相应的懒惰标记。
//            在这种情况下，不需要进一步下传更新，因为整个区间的值都被替换了
//            任何之前的懒惰标记都不再适用。
            if (L <= l && r <= R){
                update[rt] = true;
                change[rt] = C;
                sum[rt] = (r - l + 1) * C;
                lazy[rt] = 0;
                return;
            }
//            部分覆盖
//            如果当前节点的区间 [l, r] 只是部分包含在待更新区间
//                    [L, R] 中，则需要将更新操作分解并传递给子节点。
//            在这种情况下，必须调用 pushDown 函数来确保当前节点的懒惰标记被正确地下
//        传给子节点，然后再分别对左右子节点进行递归更新。
            int mid = (l + r) / 2;
            pushDown(rt, mid - l + 1, r - mid);
            if (L <= mid){
                update(L, R, C, l, mid, rt * 2);
            }

            if (R > mid){
                update(L, R, C, mid+1, r, rt * 2 + 1);
            }
            pushUp(rt);
        }

        public void add(int L, int R, int C, int l, int r, int rt){
            if (L <= l && r <= R){
                sum[rt] += C * (r - l + 1);
                lazy[rt] += C;
                return;
            }

            int mid = (l + r) / 2;
            pushDown(rt, mid - l + 1, r - mid);

            if (L <= mid) {
                add(L, R, C, l, mid, rt << 1);
            }
            if (R > mid) {
                add(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            pushUp(rt);
        }

        public long query(int L, int R, int l, int r, int rt) {
            if (L <= l && r <= R) {
                return sum[rt];
            }
            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);
            long ans = 0;
            if (L <= mid) {
                ans += query(L, R, l, mid, rt << 1);
            }
            if (R > mid) {
                ans += query(L, R, mid + 1, r, rt << 1 | 1);
            }
            return ans;
        }
    }

    public static class Right {
        public int[] arr;

        public Right(int[] origin) {
            arr = new int[origin.length + 1];
            for (int i = 0; i < origin.length; i++) {
                arr[i + 1] = origin[i];
            }
        }

        public void update(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] = C;
            }
        }

        public void add(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] += C;
            }
        }

        public long query(int L, int R) {
            long ans = 0;
            for (int i = L; i <= R; i++) {
                ans += arr[i];
            }
            return ans;
        }

    }

    public static int[] genarateRandomArray(int len, int max) {
        int size = (int) (Math.random() * len) + 1;
        int[] origin = new int[size];
        for (int i = 0; i < size; i++) {
            origin[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return origin;
    }

    public static boolean test() {
        int len = 100;
        int max = 1000;
        int testTimes = 5000;
        int addOrUpdateTimes = 1000;
        int queryTimes = 500;
        for (int i = 0; i < testTimes; i++) {
            int[] origin = genarateRandomArray(len, max);
            SegmentTree seg = new SegmentTree(origin);
            int S = 1;
            int N = origin.length;
            int root = 1;
            seg.build(S, N, root);
            Right rig = new Right(origin);
            for (int j = 0; j < addOrUpdateTimes; j++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                int C = (int) (Math.random() * max) - (int) (Math.random() * max);
                if (Math.random() < 0.5) {
                    seg.add(L, R, C, S, N, root);
                    rig.add(L, R, C);
                } else {
                    seg.update(L, R, C, S, N, root);
                    rig.update(L, R, C);
                }
            }
            for (int k = 0; k < queryTimes; k++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                long ans1 = seg.query(L, R, S, N, root);
                long ans2 = rig.query(L, R);
                if (ans1 != ans2) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("对数器测试开始...");
        System.out.println("测试结果 : " + (test() ? "通过" : "未通过"));
    }
}
