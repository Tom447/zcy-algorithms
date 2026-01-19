package class14;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class code05_UnionFind {

    public static class Node<V>{
        V value;

        public Node(V data){
            this.value = data;
        }
    }

    public static class UnionFind1<V>{
        public HashMap<V, Node<V>> nodes;
        public HashMap<Node<V>, Node<V>> parentMaps;
        //sizeMap表示的是根节点的数量
        public HashMap<Node<V>, Integer> sizeMaps;

        public UnionFind1(List<V> list){
            nodes = new HashMap<>();
            parentMaps = new HashMap<>();
            sizeMaps = new HashMap<>();
            for (V cur : list){
                Node<V> node = new Node<>(cur);
                nodes.put(cur, node);
                parentMaps.put(node, node);
                sizeMaps.put(node, 1);
            }
        }

//        给一个节点，一直往上找，找到不能找为止，把代表返回
        public Node<V> findFather(Node<V> cur){
            Stack<Node> stack = new Stack<>();
            while(cur != parentMaps.get(cur)){
                stack.push(cur);
                cur = parentMaps.get(cur);
            }
            while(!stack.isEmpty()){
                parentMaps.put(stack.pop(), cur);
            }

            return cur;
        }

        public boolean isSameSet(V a, V b){
            return findFather(nodes.get(a)) == findFather(nodes.get(b));
        }

        public void union(V a, V b){
            Node<V> aHead = findFather(nodes.get(a));
            Node<V> bHead = findFather(nodes.get(b));
            if (aHead != bHead){
                int aSize = sizeMaps.get(aHead);
                int bSize = sizeMaps.get(bHead);
                Node<V> bigNode = aSize > bSize ? aHead : bHead;
                Node<V> smallNode = bigNode == aHead ? bHead : aHead;
                parentMaps.put(smallNode, bigNode);
                sizeMaps.put(bigNode, aSize + bSize);
                //保证根节点大小的唯一性
                sizeMaps.remove(smallNode);
            }
        }

        public int setSize(){
            return sizeMaps.size();
        }
    }


    public static class UnionFind2 {
        // parent[i] = k ： i的父亲是k
        private int[] parent;
        // size[i] = k ： 如果i是代表节点，size[i]才有意义，否则无意义
        // i所在的集合大小是多少
        private int[] size;
        // 辅助结构
        private int[] help;
        // 一共有多少个集合
        private int sets;

        public UnionFind2(int N) {
            parent = new int[N];
            size = new int[N];
            help = new int[N];
            sets = N;
            for (int i = 0; i < N; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        // 从i开始一直往上，往上到不能再往上，代表节点，返回
        // 这个过程要做路径压缩
        private int find(int i) {
            int hi = 0;
            while (i != parent[i]) {
                help[hi++] = i;
                i = parent[i];
            }
            for (hi--; hi >= 0; hi--) {
                parent[help[hi]] = i;
            }
            return i;
        }

        public void union(int i, int j) {
            int f1 = find(i);
            int f2 = find(j);
            if (f1 != f2) {
                if (size[f1] >= size[f2]) {
                    size[f1] += size[f2];
                    parent[f2] = f1;
                } else {
                    size[f2] += size[f1];
                    parent[f1] = f2;
                }
                sets--;
            }
        }

        public int sets() {
            return sets;
        }
    }
}
