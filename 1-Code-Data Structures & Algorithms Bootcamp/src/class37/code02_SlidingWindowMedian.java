package class37;

import class07.HeapGreater;

import java.util.Arrays;

public class code02_SlidingWindowMedian {



    public static double[] medianSlidingWindow1(int[] nums, int k) {
        double[] result = new double[nums.length - k + 1];
        HeapGreater<Integer> maxHeap = new HeapGreater<>((a, b) -> b - a);
        HeapGreater<Integer> minHeap = new HeapGreater<>((a, b) -> a - b);

        // 初始化窗口
        for (int i = 0; i < k; i++) {
            addNum(nums[i], maxHeap, minHeap);
        }
        balanceHeaps(maxHeap, minHeap);
        result[0] = getMedian(maxHeap, minHeap, k);

        // 滑动窗口
        for (int i = k; i < nums.length; i++) {
            removeNum(nums[i - k], maxHeap, minHeap);
            addNum(nums[i], maxHeap, minHeap);
            balanceHeaps(maxHeap, minHeap);
            result[i - k + 1] = getMedian(maxHeap, minHeap, k);
        }

        return result;
    }

    private static void addNum(int num, HeapGreater<Integer> maxHeap, HeapGreater<Integer> minHeap) {
        if (maxHeap.isEmpty() || num <= maxHeap.peek()) {
            maxHeap.push(num);
        } else {
            minHeap.push(num);
        }
    }

    private static  void balanceHeaps(HeapGreater<Integer> maxHeap, HeapGreater<Integer> minHeap) {
        while (maxHeap.size() > minHeap.size() + 1) {
            minHeap.push(maxHeap.pop());
        }
        while (minHeap.size() > maxHeap.size()) {
            maxHeap.push(minHeap.pop());
        }
    }

    private static  void removeNum(int num, HeapGreater<Integer> maxHeap, HeapGreater<Integer> minHeap) {
        if (maxHeap.contain(num)) {
            maxHeap.remove(num);
        } else {
            minHeap.remove(num);
        }
    }

    private static  double getMedian(HeapGreater<Integer> maxHeap, HeapGreater<Integer> minHeap, int k) {
        if (k % 2 == 1) {
            return (double) maxHeap.peek();
        } else {
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        }
    }

    public static class SBTNode<K extends Comparable<K>>{
        public K key;
        public SBTNode<K> l;
        public SBTNode<K> r;
        public int size;

        public SBTNode(K k){
            key = k;
            size = 1;
        }
    }

    public static class SizeBalancedTreeMap<K extends Comparable<K>>{
        private SBTNode<K> root;

        private SBTNode<K> rightRotate(SBTNode<K> cur){
            SBTNode<K> leftNode = cur.l;
            cur.l = leftNode.r;
            leftNode.r = cur;
            leftNode.size = cur.size;
            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;

            return leftNode;
        }

        private SBTNode<K> leftRotate(SBTNode<K> cur){
            SBTNode<K> rightNode = cur.r;
            cur.r = rightNode.l;
            rightNode.l = cur;
            rightNode.size = cur.size;
            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;

            return rightNode;
        }

        private SBTNode<K> maintain(SBTNode<K> cur){
            if (cur == null){
                return null;
            }
            int leftSize = cur.l != null ? cur.l.size : 0;
            int leftLeftSize = cur.l != null && cur.l.l != null ? cur.l.l.size : 0;
            int leftRightSize = cur.l != null && cur.l.r != null ? cur.l.r.size : 0;
            int rightSize = cur.r != null ? cur.r.size : 0;
            int rightLeftSize = cur.r != null && cur.r.l != null ? cur.r.l.size : 0;
            int rightRightSize = cur.r != null && cur.r.r != null ? cur.r.r.size : 0;

            if (leftLeftSize > rightSize){
                cur = rightRotate(cur);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            }else if (leftRightSize > rightSize){
                cur.l = leftRotate(cur.l);
                cur = rightRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            }else if (leftSize < rightRightSize){
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur = maintain(cur);
            }else if (rightLeftSize > leftSize){
                cur.r = rightRotate(cur.r);
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            }
            return cur;
        }
        //在sbt中查找与cur相等或最接近的节点，用于后续删除和插入操作
        private SBTNode<K> findLastIndex(K key){
            SBTNode<K> pre = root;
            SBTNode<K> cur = root;
            while (cur != null){
                pre = cur;
                if (key.compareTo(cur.key) == 0){
                    break;
                }else if (key.compareTo(cur.key) < 0){
                    cur = cur.l;
                }else{
                    cur = cur.r;
                }
            }
            return pre;
        }

        private SBTNode<K> add(SBTNode<K> cur, K key){
            if (cur == null){
                return new SBTNode<K>(key);
            }else {
                cur.size++;
                if (key.compareTo(cur.key) < 0){
                    cur.l = add(cur.l, key);
                }else {
                    cur.r = add(cur.r, key);
                }
                return maintain(cur);
            }
        }

        private SBTNode<K> delete(SBTNode<K> cur, K key){
            cur.size--;
            if (key.compareTo(cur.key) > 0){
                cur.r = delete(cur.r, key);
            }else if (key.compareTo(cur.key) < 0){
                cur.l = delete(cur.l, key);
            }else {
                if (cur.l == null && cur.r == null) {
                    // free cur memory -> C++
                    cur = null;
                } else if (cur.l == null && cur.r != null) {
                    // free cur memory -> C++
                    cur = cur.r;
                } else if (cur.l != null && cur.r == null) {
                    // free cur memory -> C++
                    cur = cur.l;
                } else {

//                    pre不为空的时候的情况
//                            cur
//                               \
//                                nodeA
//                                 \
//                                 nodeB
//                                /
//                              des ← 最左节点
//                    pre为空的时候
//                            des = cur.r
                    SBTNode<K> pre = null;
                    SBTNode<K> des = cur.r;
                    des.size--;
                    while (des.l != null){
                        pre = des;
                        des = des.l;
                        des.size--;
                    }
                    if (pre != null){
                        pre.l = des.r;
                        des.r = cur.r;
                    }
                    des.l = cur.l;
                    des.size = des.l.size + (des.r != null ? des.r.size : 0) + 1;

                    cur = des;
                }
            }
            return cur;
        }

        //从1开始的第k小的数
        private SBTNode<K> getIndex(SBTNode<K> cur, int kth){
            if (kth == (cur.l != null ? cur.l.size : 0) + 1){
                return cur;
            }else if (kth <= (cur.l != null ? cur.l.size : 0)){
                return getIndex(cur.l, kth);
            }else{
                return getIndex(cur.r, kth - (cur.l != null ? cur.l.size : 0) -1);
            }
        }

        public int size(){
            return root == null ? 0 : root.size;
        }

        public boolean containKey(K key){
            if (key == null){
                throw new RuntimeException("invalid parameter.");
            }
            SBTNode<K> lastNode = findLastIndex(key);
            return lastNode != null && key.compareTo(lastNode.key) == 0 ? true : false;
         }

         public void add(K key){
            if (key == null){
                throw new RuntimeException("invalid parameter.");
            }
            SBTNode<K> lastNode = findLastIndex(key);
            if (lastNode == null || key.compareTo(lastNode.key) != 0){
                root = add(root, key);
            }
         }

         public void remove(K key){
            if (key == null){
                throw new RuntimeException("invalid parameter.");
            }
            if (containKey(key)){
                root = delete(root, key);
            }
         }
        //index 从0开始
         public K getIndexKey(int index){
             if (index < 0 || index >= this.size()) {
                 throw new RuntimeException("invalid parameter.");
             }
             return getIndex(root, index + 1).key;
         }
    }

    public static class Node implements Comparable<Node> {
        public int index;
        public int value;

        public Node(int i, int n) {
            index = i;
            value = n;
        }

        @Override
        public int compareTo(Node o) {
            return  value != o.value?
                        Integer.valueOf(value).compareTo(o.value)
                    :   Integer.valueOf(index).compareTo(o.index);
        }
    }

    public static double[] medianSlidingWindow2(int[] nums, int k){
        SizeBalancedTreeMap<Node> map = new SizeBalancedTreeMap<>();
        for (int i = 0; i < k - 1; i++){
            map.add(new Node(i, nums[i]));
        }
        double[] ans = new double[nums.length - k + 1];
        int index = 0;

        for (int i = k - 1; i < nums.length; i++){
            map.add(new Node(i, nums[i]));
            if (map.size() % 2 == 0){
                Node upmid = map.getIndexKey(map.size() / 2 - 1);
                Node downmid = map.getIndexKey(map.size() / 2);
                ans[index++] = ((double) upmid.value + (double) downmid.value) / 2;
            }else{
                Node mid = map.getIndexKey(map.size() / 2);
                ans[index++] = (double) mid.value;
            }
            map.remove(new Node(i - k + 1, nums[i - k + 1]));
        }
        return ans;
    }


    public static void main(String[] args) {
        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3;
        double[]  medians1 = medianSlidingWindow1(nums, k);
        double[] medians2 = medianSlidingWindow2(nums, k);
        System.out.println(Arrays.toString(medians1));
        System.out.println(Arrays.toString(medians2));
    }


}