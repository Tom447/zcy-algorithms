package class16;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public int value;
    public int in;
    public int out;
    public List<Node> nexts;
    public List<Edge> edges;

    public Node(int value) {
        this.value = value;
        this.in = 0;
        this.out = 0;
        //当前节点出发，后面的点
        this.nexts = new ArrayList<>();
        //当前节点出发，后面的边
        this.edges = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node other = (Node) o;
        return value == other.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}
