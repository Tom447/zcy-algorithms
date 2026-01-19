package class36;

import java.util.ArrayList;

public class code02_SkipListMap {
//    跳表的大概形状是
//    层级 2: H ------> 5 --------------> 15 -----> null
//    层级 1: H --> 3 --> 5 ----> 10 --> 15 -> 20 -> null
//    层级 0: H --> 3 --> 5 --> 7 --> 10 --> 15 --> 18 --> 20 --> null

    public static class SkipListNode<K extends Comparable<K>, V>{
        public K key;
        public V val;
        public ArrayList<SkipListNode<K, V>> nextNodes;

        public SkipListNode(K k, V v){
            key = k;
            val = v;
            nextNodes = new ArrayList<>();
        }
        //判断一下key是否小于otherkey
        public boolean isLessKey(K otherKey){
            return otherKey != null && (key == null || key.compareTo(otherKey) < 0);
        }
        //判断一下key是否等于otherkey
        public boolean isKeyEqual(K otherKey){
            return (key == null && otherKey == null)
                    || (key != null && otherKey != null && key.compareTo(otherKey) == 0);
        }
    }

    public static class SkipListMap<K extends Comparable<K>, V>{
        private static final double  PROBABILITY = 0.5;
        private SkipListNode<K, V> head;
        private int size;
        private int maxLevel;


        public SkipListMap(){
            head = new SkipListNode<>(null, null);
            head.nextNodes.add(null);
            size = 0;
            maxLevel = 0;
        }

        //key在第0层小于key最右的位置
        private SkipListNode<K, V> mostRightLessNodeInTree(K key){
            if (key == null){
                return null;
            }
            int level = maxLevel;
            SkipListNode<K, V> cur = head;
            while (level >= 0){
                cur = mostRightLessNodeInLevel(key, cur, level--);
            }

            return cur;
        }

        private SkipListNode<K, V> mostRightLessNodeInLevel(
                K key,
                SkipListNode<K, V> cur,
                int level){
            SkipListNode<K, V> next = cur.nextNodes.get(level);
            while (next != null && next.isLessKey(key)){
                cur = next;
                next = next.nextNodes.get(level);
            }
            return cur;
        }


        public boolean containsKey(K key){
            if (key == null){
                return false;
            }
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null && next.isKeyEqual(key);
        }

        public void put(K key, V val){
            if (key == null){
                return;
            }

            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> find = less.nextNodes.get(0);
            if (find != null && find.isKeyEqual(key)){
                find.val = val;
            }else{
                size++;
                int newNodeLevel = 0;
                while (Math.random() < PROBABILITY){
                    newNodeLevel++;
                }

                while (newNodeLevel > maxLevel){
                    head.nextNodes.add(null);
                    maxLevel++;
                }

                SkipListNode<K, V> newNode = new SkipListNode<>(key, val);

                for (int i = 0; i <= newNodeLevel; i++){
                    newNode.nextNodes.add(null);
                }

                int level = maxLevel;
                SkipListNode<K, V> pre = head;
                while (level >= 0){
                    pre = mostRightLessNodeInLevel(key, pre, level);
                    if (level <= newNodeLevel){
                        newNode.nextNodes.set(level, pre.nextNodes.get(level));
                        pre.nextNodes.set(level, newNode);
                    }
                    level--;
                }
            }
        }


        public void remove(K key){
            if (containsKey(key)){
                size--;
                int level = maxLevel;
                SkipListNode<K, V> pre = head;

                while (level >= 0){
                    pre = mostRightLessNodeInLevel(key, pre, level);
                    SkipListNode<K, V> next = pre.nextNodes.get(level);

                    if (next != null && next.isKeyEqual(key)){
                        pre.nextNodes.set(level, next.nextNodes.get(level));
                    }
                    //如果当前层在删除next之后为空了，就需要将该level清空
                    if (level != 0 && pre == head && pre.nextNodes.get(level) == null){
                        head.nextNodes.remove(level);
                        maxLevel--;
                    }
                    level--;
                }
            }
        }

        public K firstKey(){
            return head.nextNodes.get(0) != null ? head.nextNodes.get(0).key : null;
        }



        public K lastKey(){
            int level = maxLevel;
            SkipListNode<K, V> cur = head;
            while (level >= 0){
                SkipListNode<K, V> next = cur.nextNodes.get(level);
                while (next != null){
                    cur = next;
                    next = next.nextNodes.get(level);
                }
                level--;
            }
            return cur.key;
        }
//        返回跳表中 大于等于给定 key 的最小键（即“天花板”键），如果没有这样的键，则返回 null。
        public K ceilingKey(K key){
            if (key == null){
                return null;
            }

            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null ? next.key : null;
        }

//        返回跳表中 小于等于给定 key 的最大键（即“地板”键），如果没有这样的键，则返回 null。
        public K floorKey(K key){
            if (key == null){
                return null;
            }

            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);

            return next != null && next.isKeyEqual(key) ? next.key : less.key;
        }

        public V get(K key) {
            if (key == null) {
                return null;
            }
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            if (next != null && next.isKeyEqual(key)) {
                return next.val;
            }
            return null;
        }

    }

    // for test
    public static void printAll(SkipListMap<String, String> obj) {
        for (int i = obj.maxLevel; i >= 0; i--) {
            System.out.print("Level " + i + " : ");
            SkipListNode<String, String> cur = obj.head;
            while (cur.nextNodes.get(i) != null) {
                SkipListNode<String, String> next = cur.nextNodes.get(i);
                System.out.print("(" + next.key + " , " + next.val + ") ");
                cur = next;
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        SkipListMap<String, String> test = new SkipListMap<>();
        printAll(test);
        System.out.println("======================");
        test.put("A", "10");
        printAll(test);
        System.out.println("======================");
        test.remove("A");
        printAll(test);
        System.out.println("======================");
        test.put("E", "E");
        test.put("B", "B");
        test.put("A", "A");
        test.put("F", "F");
        test.put("C", "C");
        test.put("D", "D");
        printAll(test);
        System.out.println("======================");
        System.out.println(test.containsKey("B"));
        System.out.println(test.containsKey("Z"));
        System.out.println(test.firstKey());
        System.out.println(test.lastKey());
        System.out.println(test.floorKey("D"));
        System.out.println(test.ceilingKey("D"));
        System.out.println("======================");
        test.remove("D");
        printAll(test);
        System.out.println("======================");
        System.out.println(test.floorKey("D"));
        System.out.println(test.ceilingKey("D"));


    }




}
