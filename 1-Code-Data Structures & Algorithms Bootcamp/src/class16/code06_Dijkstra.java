package class16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class code06_Dijkstra {


    public static class Node {
        public int value;
        public ArrayList<Edge> edges;

        public Node(int value) {
            this.value = value;
            this.edges = new ArrayList<>();  // ⚠️ 必须初始化！
        }
    }

    public static class Edge {
        public int weight;
        public Node from;
        public Node to;

        public Edge(int weight, Node from, Node to) {
            this.weight = weight;
            this.from = from;
            this.to = to;  // ⚠️ 必须包含这一行！
        }
    }

    public static HashMap<Node, Integer> dijkstra1(Node from){
        HashMap<Node, Integer> distanceMap = new HashMap<>();
        distanceMap.put(from, 0);
        HashSet<Node> selectedNode = new HashSet<>();
        Node minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNode);
        while(minNode != null){
            //minNode 是跳转点 也是刷新后的参考点
            int distance = distanceMap.get(minNode);
            for (Edge edge : minNode.edges){
                Node toNode = edge.to;
                if (!distanceMap.containsKey(toNode)){
                    distanceMap.put(toNode, distance + edge.weight);
                }else{
                    distanceMap.put(toNode, Math.min(distanceMap.get(toNode), distance + edge.weight));
                }
            }
            selectedNode.add(minNode);
            minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNode);
        }
        return distanceMap;
    }


    public static Node getMinDistanceAndUnselectedNode(HashMap<Node, Integer> selectedMap, HashSet<Node> selectedNode){
        Node minNode = null;
        int minDistance = Integer.MAX_VALUE;
        for (Map.Entry<Node, Integer> entry : selectedMap.entrySet()){
            Node node = entry.getKey();
            int distance = entry.getValue();
            if (!selectedNode.contains(node) && distance < minDistance){
                minNode = node;
                minDistance = distance;
            }
        }
        return minNode;
    }



    public static class NodeRecord{
        public Node node;
        public int distance;

        public NodeRecord(Node node, int distance){
            this.node = node;
            this.distance = distance;
        }
    }

    public static class NodeHeap{
        public Node[] nodes;
        public HashMap<Node, Integer> heapIndexMap; //节点在索引中的位置
        public HashMap<Node, Integer> distanceMap;
        public int size;

        public NodeHeap(int size){
            nodes = new Node[size];
            heapIndexMap = new HashMap<>();
            distanceMap = new HashMap<>();
            this.size = 0;
        }

        public boolean isEmpty(){
            return size == 0;
        }

       private boolean isEntered(Node node){
            return heapIndexMap.containsKey(node);
       }

//      private boolean inHeap(Node node){
//            return isEntered(node) && distanceMap.get(node) != -1;
//       }

       private boolean inHeap(Node node){
            return isEntered(node) && distanceMap.containsKey(node) && distanceMap.get(node) != -1;
        }

       private void heapIfy_up(Node node, int index){
            while(distanceMap.get(nodes[index]) < distanceMap.get(nodes[(index - 1) / 2])){
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
       }


       private void heapIfy_down(int index, int size){
            int left = index * 2 + 1;
            while(left < size){
                int smallIndex = left + 1 < size && distanceMap.get(nodes[left + 1]) < distanceMap.get(nodes[left]) ? left + 1: left;
                smallIndex = distanceMap.get(nodes[smallIndex]) < distanceMap.get(nodes[index]) ? smallIndex : index;
                if (smallIndex == index) break;
                swap(smallIndex, index);
                index = smallIndex;
                left = index * 2 + 1;
            }
       }

       private void swap(int index1, int index2){
            heapIndexMap.put(nodes[index1], index2);
            heapIndexMap.put(nodes[index2], index1);
            Node temp = nodes[index1];
            nodes[index1] = nodes[index2];
            nodes[index2] = temp;
       }

       public NodeRecord pop(){
            NodeRecord nodeRecord = new NodeRecord(nodes[0], distanceMap.get(nodes[0]));
            swap(0, size-1);
            //标记为以移除
            heapIndexMap.put(nodes[size - 1], -1);
            distanceMap.remove(nodes[size - 1]);
            nodes[size - 1] = null;
            heapIfy_down(0, size-1);
            this.size--;
            return nodeRecord;
       }

       public void addOrUpdateOrIngore(Node node, int distance){
            if (inHeap(node)){
                distanceMap.put(node, Math.min(distanceMap.get(node), distance));
                heapIfy_up(node, heapIndexMap.get(node));
            }
            if (!isEntered(node)){
                nodes[size] = node;
                heapIndexMap.put(node, size);
                distanceMap.put(node, distance);
                heapIfy_up(node, size++);
            }
       }
    }

    public static HashMap<Node, Integer> dijkstra2(Node head, int size) {

        NodeHeap nodeHeap = new NodeHeap(size);
        nodeHeap.addOrUpdateOrIngore(head, 0);
        HashMap<Node, Integer> result = new HashMap<>();
        while(!nodeHeap.isEmpty()){
            NodeRecord record = nodeHeap.pop();
            Node cur = record.node;
            int distance = record.distance;
            for (Edge edge : cur.edges) {
                System.out.println("Processing edge from " + cur.value + " to " + edge.to.value + " with weight " + edge.weight);
                if (edge.to == null) {
                    System.out.println("Error: Found an edge with a null 'to' node.");
                }
                nodeHeap.addOrUpdateOrIngore(edge.to, edge.weight + distance);
            }
            result.put(cur, distance);
        }
        return result;
    }
    // 判断两个 HashMap 是否相等
    private static boolean isEqual(HashMap<Node, Integer> map1, HashMap<Node, Integer> map2) {
        if (map1.size() != map2.size()) return false;
        for (Node node : map1.keySet()) {
            if (!map2.containsKey(node)) return false;
            if (!map1.get(node).equals(map2.get(node))) return false;
        }
        return true;
    }


    private static void printGraph(HashMap<Integer, Node> nodeMap) {
        for (Node node : nodeMap.values()) {
            System.out.println("Node " + node.value + " connects to:");
            for (Edge edge : node.edges) {
                if (edge.to != null)
                    System.out.println("  -> " + edge.to.value + " (weight: " + edge.weight + ")");
                else
                    System.out.println("  -> NULL NODE");
            }
        }
    }
    // 主测试函数
    public static void main(String[] args) {
        int testTime = 100;     // 测试次数
        int maxNodes = 20;      // 每个图最多节点数
        int maxEdges = 50;      // 每个图最多边数
        int maxValue = 100;     // 边权最大值

        System.out.println("开始对拍测试...");

        for (int i = 1; i <= testTime; i++) {
            // 随机生成图
            int nodeNum = (int)(Math.random() * maxNodes) + 1;
            HashMap<Integer, Node> nodeMap = new HashMap<>();
            for (int j = 0; j < nodeNum; j++) {
                nodeMap.put(j, new Node(j));
            }

            int edgeNum = (int)(Math.random() * maxEdges) + 1;
            for (int j = 0; j < edgeNum; j++) {
                int from = (int)(Math.random() * nodeNum);
                int to = (int)(Math.random() * nodeNum);
                int weight = (int)(Math.random() * maxValue) + 1;
                if (from == to) continue;

                Node fromNode = nodeMap.get(from);
                Node toNode = nodeMap.get(to);

                // ⚠️ 增加 null 检查，防止构造 null 边
                if (fromNode != null && toNode != null) {
                    fromNode.edges.add(new Edge(weight, fromNode, toNode));
                }
            }

            Node start = nodeMap.get(0);


            printGraph(nodeMap); // 放在 main 中生成完图之后、运行算法之前
            // 运行两个算法
            HashMap<Node, Integer> res1 = dijkstra1(start);
            HashMap<Node, Integer> res2 = dijkstra2(start, nodeNum);

            // 比较结果
            if (isEqual(res1, res2)) {
                System.out.println("第 " + i + " 次测试通过 ✅");
            } else {
                System.out.println("第 " + i + " 次测试失败 ❌");
                System.out.println("暴力版结果：" + res1);
                System.out.println("堆优化版结果：" + res2);
                break;
            }
        }
    }

}

