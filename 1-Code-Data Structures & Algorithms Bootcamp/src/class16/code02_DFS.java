package class16;

import java.util.HashSet;
import java.util.Stack;

public class code02_DFS {

    public static void DFS(Node start){
        if (start == null){
            return;
        }
        Stack<Node> stack = new Stack<>();
        HashSet<Node> set = new HashSet<>();
        stack.push(start);
        set.add(start);
        System.out.println(start.value);
        while (!stack.isEmpty()){
            Node cur = stack.pop();
            for (Node next : cur.nexts){
                if (!set.contains(next)){
                    stack.push(cur);
                    stack.push(next);
                    System.out.println(next.value);
                    break;
                }
            }
        }
    }
}
