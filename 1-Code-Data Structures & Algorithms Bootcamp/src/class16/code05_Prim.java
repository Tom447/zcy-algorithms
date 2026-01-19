package class16;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class code05_Prim {


    public static class mstComparator implements Comparator<Edge> {

        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }

    public static Set<Edge> PrimMST(Graph graph){
        PriorityQueue<Edge> queue = new PriorityQueue<>(new mstComparator());
        HashSet<Node> set = new HashSet<>();
        HashSet<Edge> result = new HashSet<>();
        for (Node cur : graph.nodes.values()){
            if (!set.contains(cur)){
                set.add(cur);
                for (Edge edge : cur.edges){
                    queue.add(edge);
                }

                while (!queue.isEmpty()){
                    Edge edge = queue.poll();
                    Node toNode = edge.to;
                    if (!set.contains(toNode)){
                        result.add(edge);
                        set.add(toNode);
                        for (Edge toEdge : toNode.edges){
                            queue.add(toEdge);
                        }
                    }
                }
            }
        }
        return result;
    }




//    Prim 算法要求图中每条边的权重都必须是非负数。这意味着 graph[i][j] >= 0
//    必须对所有有效的 i 和 j 成立。如果两个节点之间没有直接连接，
//    则可以将它们之间的权重视为无穷大（例如，可以使用一个非常大的整数值来表示这种情况，
//    如 Integer.MAX_VALUE

    public static int Prim(int[][] graph){
        int nodeNum = graph.length;
        int[] distance = new int[nodeNum];
        boolean[] visted = new boolean[nodeNum];



        // 0 到各个点的距离
        visted[0] = true;
        for (int i = 0; i < nodeNum; i++){
            distance[i] = graph[0][i];
        }
        int sum = 0;

        //此时mst中只有节点0；
        for (int j = 1; j < nodeNum; j++){
            //当前mst中最小代价的路
            int minPath = Integer.MAX_VALUE;
            //最小代价路的节点是
            int minIndex = -1;
            for (int i = 0; i < nodeNum; i++){
                if (!visted[i] && minPath > distance[i]){
                    //找到当前mst的联通的最小的路
                    minPath = distance[i];
                    minIndex = i;
                }
            }
            //如果当前mst没有联通的点了返回sum
            if (minIndex == -1){
                return sum;
            }
            //将最小路对应的节点加入到mst中
            visted[minIndex] = true;
            sum += minPath;
            //更新新的mst对应的distance
            for (int i = 0; i < nodeNum; i++){
                if (!visted[i] && distance[i] > graph[minIndex][i]){
                    distance[i] = graph[minIndex][i];
                }
            }
        }
        return sum;
    }
}
