package class16;

import java.util.*;

public class code03_TopologySort {

    public static List<Node> sortedTopology(Graph graph) {
        HashMap<Node, Integer> inMap = new HashMap<>();
        Queue<Node> zeroInqQueue = new LinkedList<>();
        for (Node node : graph.nodes.values()){
            inMap.put(node, node.in);
            if (node.in == 0){
                zeroInqQueue.add(node);
            }
        }
        List<Node> result = new ArrayList<>();
        while(!zeroInqQueue.isEmpty()){
            Node cur = zeroInqQueue.poll();
            for (Node next : cur.nexts){
                inMap.put(next, inMap.get(next) - 1);
                if (inMap.get(next) == 0){
                    zeroInqQueue.add(next);
                }
            }
        }

        return  result;
    }
}
