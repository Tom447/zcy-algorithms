package class16;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class code03_TopologicalOrderDFS2 {
    // 不要提交这个类
    public static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }

    // 提交下面的
    public static class Record {
        public DirectedGraphNode node;
        public long nodes;

        public Record(DirectedGraphNode node, long nodes) {
            this.node = node;
            this.nodes = nodes;
        }
    }


    public static class MyComparator implements Comparator<Record> {


        @Override
        public int compare(Record o1, Record o2) {
            return o1.nodes == o2.nodes ? 0 : o1.nodes > o2.nodes ? -1 : 1;
        }
    }
    public static ArrayList<  DirectedGraphNode> topSort(ArrayList<  DirectedGraphNode> graph){
        HashMap<  DirectedGraphNode, Record> order = new HashMap<>();
        for (  DirectedGraphNode cur : graph){
            f(cur, order);
        }
        ArrayList<Record> recordArray = new ArrayList<>();
        for (Record r : order.values()){
            recordArray.add(r);
        }
        recordArray.sort(new MyComparator());

        ArrayList<  DirectedGraphNode> ans = new ArrayList<>();
        for (Record r : recordArray){
            ans.add(r.node);
        }
        return ans;
    }


    public static Record f(DirectedGraphNode cur, HashMap<DirectedGraphNode, Record> order){
        if (order.containsKey(cur)){
            return order.get(cur);
        }
        long nodes = 0;
        for (DirectedGraphNode next : cur.neighbors){
            nodes += f(next, order).nodes;
        }
        Record ans = new Record(cur, nodes+1);
        order.put(cur, ans);
        return ans;
    }
}
