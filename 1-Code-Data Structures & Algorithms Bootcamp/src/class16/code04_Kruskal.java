package class16;

import java.util.*;

public class code04_Kruskal {

    public static class UnionFind1{
        private HashMap<Node, Node> parents;
        private HashMap<Node, Integer> sizes;


        public UnionFind1(){
            parents = new HashMap<>();
            sizes = new HashMap<>();
        }

        public void makeSets(Collection<Node> nodes){
            parents.clear();
            sizes.clear();
            for (Node cur : nodes){
                parents.put(cur, cur);
                sizes.put(cur, 1);
            }
        }

        private Node findParent(Node cur){
            Stack<Node> path = new Stack<>();
            while(cur != parents.get(cur)){
                path.push(cur);
                cur = parents.get(cur);
            }
            while(!path.isEmpty()){
                parents.put(path.pop(), cur);
            }

            return cur;
        }


        public boolean isSameUnion(Node a, Node b){
            return findParent(a) == findParent(b);
        }

        public void union(Node a, Node b){
            if (a == null || b == null){
                return;
            }
            Node aHead = findParent(a);
            Node bHead = findParent(b);

            if (aHead != bHead){
                int aSize = sizes.get(aHead);
                int bSize = sizes.get(bHead);
                if (aSize <= bSize){
                    parents.put(aHead, bHead);
                    sizes.put(bHead, aSize + bSize);
                    sizes.remove(aHead);
                }else{
                    parents.put(bHead, aHead);
                    sizes.put(aHead, aSize + bSize);
                    sizes.remove(bHead);
                }
            }

        }
    }

    public static class EdgeComparator implements Comparator<Edge> {

        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }

    public static Set<Edge> getKruska(Graph graph){
        UnionFind1 unf = new UnionFind1();
        unf.makeSets(graph.nodes.values());

        PriorityQueue<Edge> queue = new PriorityQueue<>(new EdgeComparator());
        for (Edge e : graph.edges){
            queue.add(e);
        }

        Set<Edge> result = new HashSet<>();
        while(!queue.isEmpty()){
            Edge e = queue.poll();
            if (!unf.isSameUnion(e.from, e.to)){
                result.add(e);
                unf.union(e.from, e.to);
            }
        }
        return result;
    }
}
