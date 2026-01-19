package class37;

import java.util.HashSet;

public class code01_CountofRangeSum {

   public static class countOfRangeSum1{

       public  int countRangeSum(int[] nums, int lower, int upper) {
           if (nums == null || nums.length == 0) {
               return 0;
           }
           long[] sum = new long[nums.length];
           sum[0] = nums[0];
           for (int i = 1; i < nums.length; i++) {
               sum[i] = sum[i - 1] + nums[i];
           }
           return process(sum, 0, sum.length - 1, lower, upper);
       }

       public int process(long[] sum, int L, int R, int lower, int upper) {
           if (L == R) {
               return sum[L] >= lower && sum[L] <= upper ? 1 : 0;
           }
           int M = L + ((R - L) >> 1);
           return process(sum, L, M, lower, upper) + process(sum, M + 1, R, lower, upper)
                   + merge(sum, L, M, R, lower, upper);
       }

       public int merge(long[] arr, int L, int M, int R, int lower, int upper) {
           int ans = 0;
           int windowL = L;
           int windowR = L;
           // [windowL, windowR)
           for (int i = M + 1; i <= R; i++) {
               long min = arr[i] - upper;
               long max = arr[i] - lower;
               while (windowR <= M && arr[windowR] <= max) {
                   windowR++;
               }
               while (windowL <= M && arr[windowL] < min) {
                   windowL++;
               }
               ans += windowR - windowL;
           }
           long[] help = new long[R - L + 1];
           int i = 0;
           int p1 = L;
           int p2 = M + 1;
           while (p1 <= M && p2 <= R) {
               help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
           }
           while (p1 <= M) {
               help[i++] = arr[p1++];
           }
           while (p2 <= R) {
               help[i++] = arr[p2++];
           }
           for (i = 0; i < help.length; i++) {
               arr[L + i] = help[i];
           }
           return ans;
       }
   }

    public static class SBTNode{
       public long key;
       public SBTNode l;
       public SBTNode r;
       public long size; //不同key的size（不重复的）
       public long all; //不同key的size（可重复的）

       public SBTNode(long k){
           key = k;
           size = 1;
           all = 1;
       }
    }


    public static class SizeBalancedTreeSet{
       private SBTNode root;
       private HashSet<Long> set = new HashSet<>();

       private SBTNode rightRotate(SBTNode cur){
           long same = cur.all - (cur.l != null ? cur.l.all : 0) - (cur.r != null ? cur.r.all : 0);
           SBTNode leftNode = cur.l;
           cur.l = leftNode.r;
           leftNode.r = cur;
           leftNode.size = cur.size;
           cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
           leftNode.all = cur.all;
           cur.all = (cur.l != null ? cur.l.all : 0) + (cur.r != null ? cur.r.all : 0) + same;

           return leftNode;
       }

        private SBTNode leftRotate(SBTNode cur) {
            long same = cur.all - (cur.l != null ? cur.l.all : 0) - (cur.r != null ? cur.r.all : 0);
            SBTNode rightNode = cur.r;
            cur.r = rightNode.l;
            rightNode.l = cur;
            rightNode.size = cur.size;
            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
            // all modify
            rightNode.all = cur.all;
            cur.all = (cur.l != null ? cur.l.all : 0) + (cur.r != null ? cur.r.all : 0) + same;
            return rightNode;
        }

        private SBTNode maintain(SBTNode cur){
           if (cur == null){
               return null;
           }

            long leftSize = cur.l != null ? cur.l.size : 0;
            long leftLeftSize = cur.l != null && cur.l.l != null ? cur.l.l.size : 0;
            long leftRightSize = cur.l != null && cur.l.r != null ? cur.l.r.size : 0;
            long rightSize = cur.r != null ? cur.r.size : 0;
            long rightLeftSize = cur.r != null && cur.r.l != null ? cur.r.l.size : 0;
            long rightRightSize = cur.r != null && cur.r.r != null ? cur.r.r.size : 0;

            if (leftLeftSize > rightSize) {
                cur = rightRotate(cur);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            } else if (leftRightSize > rightSize) {
                cur.l = leftRotate(cur.l);
                cur = rightRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            } else if (rightRightSize > leftSize) {
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur = maintain(cur);
            } else if (rightLeftSize > leftSize) {
                cur.r = rightRotate(cur.r);
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            }
            return cur;
        }

        private SBTNode add(SBTNode cur, long key, boolean contains){
           if (cur == null){
               return new SBTNode(key);
           }else {
               cur.all++;
               if (key == cur.key){
                   return cur;
               }else {
                   if (!contains){
                       cur.size++;
                   }
                   if (key < cur.key){
                       cur.l = add(cur.l, key, contains);
                   }else{
                       cur.r = add(cur.r, key, contains);
                   }
                   return maintain(cur);
               }
           }
        }

        public void add(long sum){
           boolean contains = set.contains(sum);
           root = add(root, sum, contains);
           set.add(sum);
        }

        public long lessKeySize(long key){
           SBTNode cur = root;
           long ans = 0;

           while (cur != null){
               if (key == cur.key){
                   return ans += (cur.l != null ? cur.l.all : 0);
               }else if (key < cur.key){
                   cur = cur.l;
               }else if (key > cur.key){
                   ans += cur.all - (cur.r != null ? cur.r.all : 0);
                   cur = cur.r;
               }
           }
           return ans;
        }
        // > 7 8...
        // <8 ...<=7
//        lessKeySize(key + 1) 实际上等于：小于等于 key 的元素数量
        public long moreKeySize(long key) {
            return root != null ? (root.all - lessKeySize(key + 1)) : 0;
        }
    }


    public static int countRangeSum2(int[] nums, int lower, int upper) {
        SizeBalancedTreeSet treeSet = new SizeBalancedTreeSet();
        long sum = 0;
        int ans = 0;
        treeSet.add(0);// 一个数都没有的时候，就已经有一个前缀和累加和为0，
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            // sum    i结尾的时候[lower, upper]
            // 之前所有前缀累加和中，有多少累加和落在[sum - upper, sum - lower]
            // 查 ？ < sum - lower + 1   a
            // 查 ?  < sum - upper    b
            // a - b
            //a 是小于等于sum - lower的数量
            long a = treeSet.lessKeySize(sum - lower + 1);
            //b是小于等于sum - upper - 1的数量,  那么要求的[l, r]的数量原本是r - l + 1
            //变成[l-1, r], 所以就是r - (l-1)也就说 a - b
            long b = treeSet.lessKeySize(sum - upper);
            ans += a - b;
            treeSet.add(sum);
        }
        return ans;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static int[] generateArray(int len, int varible) {
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * varible);
        }
        return arr;
    }

    public static void main(String[] args) {
        int len = 10;
        int varible = 50;
        for (int i = 0; i < 10000; i++) {
            int[] test = generateArray(len, varible);
            int lower = (int) (Math.random() * varible) - (int) (Math.random() * varible);
            int upper = lower + (int) (Math.random() * varible);
            countOfRangeSum1 countOfRangeSum1 = new countOfRangeSum1();
            int ans1 = countOfRangeSum1.countRangeSum(test, lower, upper);
            int ans2 = countRangeSum2(test, lower, upper);
            if (ans1 != ans2) {
                printArray(test);
                System.out.println("[" + lower + ", " + upper + "]");
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }

    }

}
