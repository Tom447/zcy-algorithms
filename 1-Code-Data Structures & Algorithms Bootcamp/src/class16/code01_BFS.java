package class16;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class code01_BFS {
    //  从start出发
    public static void bfs(Node start){
        if (start == null){
            return;
        }

        Queue<Node> queue = new LinkedList<>();
        HashSet<Node> set = new HashSet<>();
        queue.add(start);
        set.add(start);
        while(!queue.isEmpty()){
            Node cur = queue.poll();
            System.out.println(cur.value);
            for (Node n : cur.nexts){
                if (!set.contains(n)){
                    queue.add(n);
                    set.add(n);
                }
            }
        }
    }
}
