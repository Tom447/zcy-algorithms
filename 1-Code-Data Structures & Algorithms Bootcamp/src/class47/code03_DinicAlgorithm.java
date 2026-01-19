package class47;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class code03_DinicAlgorithm {

    public static class Edge{
        public int from;
        public int to;
        public int available;

        public Edge(int a, int b, int c){
            from = a;
            to = b;
            available = c;
        }
    }

    public static class Dinic{
        private int N;
        private ArrayList<ArrayList<Integer>> nexts;
        private ArrayList<Edge> edges;
        private int[] depth;
        private int[] cur;

        public Dinic(int nums){
            N = nums + 1;
            nexts = new ArrayList<>();
            for (int i = 0; i <= N; i++){
                nexts.add(new ArrayList<>());
            }
            edges = new ArrayList<>();
            depth = new int[N];
            cur = new int[N];
        }

        public void addEdge(int u, int v, int r){
            int m = edges.size();
            edges.add(new Edge(u, v, r));
            nexts.get(u).add(m);
            edges.add(new Edge(v, u, 0));
            nexts.get(v).add(m + 1);
        }

        private boolean bfs(int s, int t){
            LinkedList<Integer> queue = new LinkedList<>();
            queue.addFirst(s);
            boolean[] visited = new boolean[N];
            visited[s] = true;
            while (!queue.isEmpty()){
                int u = queue.pollLast();
                for (int i = 0; i < nexts.get(u).size(); i++){
                    Edge e = edges.get(nexts.get(u).get(i));
                    int v = e.to;
                    if (!visited[v] && e.available > 0){
                        visited[v] = true;
                        depth[v] = depth[u] + 1;
                        if (v == t){
                            break;
                        }
                        queue.add(v);
                    }
                }
            }
            return visited[t];
        }
        // 当前来到了s点，s可变
        // 最终目标是t，t固定参数
        // r，收到的任务
        // 收集到的流，作为结果返回，ans <= r
//        r 实际上代表的是当前节点 s 被分配的任务量，
//        也就是从 s 点出发最多可以推送多少流量到终点 t。
//        这个值是从父级调用传递下来的，表示上游节点希望 s
//            点能够向下游推送的最大可能的流。
        private int dfs(int s, int t, int r){
//            这个条件检查当前节点 s 是否已经是目标节点 t。
//            在递归过程中，当 dfs 调用达到目标节点 t 时，
//            意味着找到了一条从源点到汇点的有效路径。
//            如果 s == t 成立，说明已经到达了目的地，无需继续搜索。
//            r == 0:
//            这个条件检查剩余的任务量 r 是否为 0。
//            r 表示的是当前节点 s 可以尝试向下游推送的最大流量。
//            如果 r == 0，则表示没有更多的流量可以推送了。
//            当 r == 0 时，无论是否找到通往 t 的路径，
//            都不需要再继续尝试推送流量，因为已经达到了此次任务的最大容量。
            if (s == t || r == 0){
                return r;
            }
            int f = 0;
            int flow = 0;
            for (; cur[s] < nexts.get(s).size(); cur[s]++){
                int ei = nexts.get(s).get(cur[s]);
                Edge e = edges.get(ei);
                Edge o = edges.get(ei ^ 1);
                if (depth[e.to] == depth[s] + 1 && (f = dfs(e.to, t, Math.min(e.available, r))) != 0){
                    e.available -= f;
                    o.available += f;
                    flow += f;
                    r -= f;
                    if (r <= 0) {
                        break;
                    }
                }
            }
            return flow;
        }

        public int maxFlow(int s, int t) {
            int flow = 0;
            while (bfs(s, t)) {
                Arrays.fill(cur, 0);
                flow += dfs(s, t, Integer.MAX_VALUE);
                Arrays.fill(depth, 0);
            }
            return flow;
        }
    }

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int cases = cin.nextInt();
        for (int i = 1; i <= cases; i++) {
            int n = cin.nextInt();
            int s = cin.nextInt();
            int t = cin.nextInt();
            int m = cin.nextInt();
            Dinic dinic = new Dinic(n);
            for (int j = 0; j < m; j++) {
                int from = cin.nextInt();
                int to = cin.nextInt();
                int weight = cin.nextInt();
                dinic.addEdge(from, to, weight);
                dinic.addEdge(to, from, weight);
            }
            int ans = dinic.maxFlow(s, t);
            System.out.println("Case " + i + ": " + ans);
        }
        cin.close();
    }
}
