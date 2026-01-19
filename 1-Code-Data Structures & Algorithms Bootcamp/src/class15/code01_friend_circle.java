package class15;

public class code01_friend_circle {


    public static int findFriendCircle(int[][] M){
        int N = M.length;
        UnionFind unionFind = new UnionFind(N);
        for (int i = 0; i < N; i ++){
            for (int j = i + 1; j < N; j++){
                unionFind.union(i, j);
            }
        }
        return unionFind.setSize();
    }


    public static class UnionFind {
        private int[] parent;  //i为parent[i] = k i的代表节点为k
        private int[] sizes; //i必须为代表节点，size[i]为代表节点所代表集合的数量
        private int[] help; //路径压缩所需要的栈
        private int sets;


        public UnionFind(int N){
            parent = new int[N];
            sizes = new int[N];
            help = new int[N];
            sets = N;
            for (int i = 0; i < N; i++){
                parent[i] = i;
                sizes[i] = 1;
            }
        }

        //从index一直往上找，过程中需要路径压缩
        public int find(int index){
            int cur = 0;
            while (index != parent[index]){
                help[cur++] = index;
                index = parent[index];
            }
            //cur-- 是因为上面index = parent[index]的时候， cur多加了一次  index此时为代表节点
            //路径压缩完毕后 需要将所有路径上的节点的代表节点都改为index
            for(cur--; cur >= 0; cur--){
                parent[help[cur]] = index;
            }

            return index;
        }


        public void union(int i ,int j){
            int iHead = find(i);
            int jHead = find(j);
            if (iHead != jHead){
                if (sizes[i] >= sizes[j]){
                    sizes[iHead] += sizes[jHead];
                    parent[jHead] = iHead;
                }else{
                    sizes[jHead] += sizes[iHead];
                    parent[iHead] = jHead;
                }
                sets--;
            }
        }

        public int setSize(){
            return sets;
        }
    }
}
