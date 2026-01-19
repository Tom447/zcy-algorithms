package class15;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class code02_NumberOfIslans {


    public static int numsIsIslands1(char[][] boards){
        int islands = 0;
        for (int i = 0; i < boards.length; i++){
            for (int j = 0; j < boards[0].length; j++){
                if (boards[i][j] == '1'){
                    islands++;
                    infect(boards, i, j);
                }
            }
        }
        return islands;
    }

    //把所有连成一片的字符变为0
    public static void infect(char[][] boards, int i, int j){
        if (i < 0 || i == boards.length || j < 0 || j == boards[0].length || boards[i][j] != '1'){
            return;
        }
        boards[i][j] = 0;
        infect(boards, i - 1, j);
        infect(boards, i + 1, j);
        infect(boards, i, j - 1);
        infect(boards, i, j + 1);
    }


    public static int numsIsIslands2(char[][] boards){
        int row = boards.length;
        int col = boards[0].length;
        Dot[][] dots = new Dot[row][col];
        List<Dot> doList = new ArrayList<>();
        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                if (boards[i][j] == '1'){
                    dots[i][j] = new Dot();
                    doList.add(dots[i][j]);
                }
            }
        }
        UnionFind1<Dot> union = new UnionFind1<Dot>(doList);

        for (int j = 1; j < col; j++){
            if (boards[0][j-1] == '1' && boards[0][j] == '1'){
                union.union(dots[0][j-1], dots[0][j]);
            }
        }

        for (int i = 1; i < row; i++){
            if (boards[i-1][0] == '1' && boards[i][0] == '1'){
                union.union(dots[i-1][0], dots[i][0]);
            }
        }

        for (int i = 1; i < row; i++){
            for (int j = 1; j < col; j++){
                if (boards[i-1][j] == '1' && boards[i][j] == '1'){
                    union.union(dots[i-1][j], dots[i][j]);
                }
                if (boards[i][j-1] == '1' && boards[i][j] == '1'){
                    union.union(dots[i][j-1], dots[i][j]);
                }
            }
        }
        return union.setSize();
    }


    public static class Dot{

    }


    public static class Node<V>{
        V value;

        public Node(V data){
            this.value = data;
        }
    }
    public static class UnionFind1<V>{
        public HashMap<V,  Node<V>> nodes;
        public HashMap< Node<V>,  Node<V>> parentMaps;
        //sizeMap表示的是根节点的数量
        public HashMap< Node<V>, Integer> sizeMaps;

        public UnionFind1(List<V> list){
            nodes = new HashMap<>();
            parentMaps = new HashMap<>();
            sizeMaps = new HashMap<>();
            for (V cur : list){
                 Node<V> node = new  Node<>(cur);
                nodes.put(cur, node);
                parentMaps.put(node, node);
                sizeMaps.put(node, 1);
            }
        }

        //        给一个节点，一直往上找，找到不能找为止，把代表返回
        public  Node<V> findFather( Node<V> cur){
            Stack< Node> stack = new Stack<>();
            while(cur != parentMaps.get(cur)){
                stack.push(cur);
                cur = parentMaps.get(cur);
            }
            while(!stack.isEmpty()){
                parentMaps.put(stack.pop(), cur);
            }

            return cur;
        }

        public boolean isSameSet(V a, V b){
            return findFather(nodes.get(a)) == findFather(nodes.get(b));
        }

        public void union(V a, V b){
             Node<V> aHead = findFather(nodes.get(a));
             Node<V> bHead = findFather(nodes.get(b));
            if (aHead != bHead){
                int aSize = sizeMaps.get(aHead);
                int bSize = sizeMaps.get(bHead);
                 Node<V> bigNode = aSize > bSize ? aHead : bHead;
                 Node<V> smallNode = bigNode == aHead ? bHead : aHead;
                parentMaps.put(smallNode, bigNode);
                sizeMaps.put(bigNode, aSize + bSize);
                //保证根节点大小的唯一性
                sizeMaps.remove(smallNode);
            }
        }

        public int setSize(){
            return sizeMaps.size();
        }
    }



   public static class UnionFind2{
        private int[] parent;
        private int[] size;
        private int[] help;
        private int col;
        private int sets;

       public UnionFind2(char[][] boards){
           col = boards[0].length;
           int row = boards.length;
           int len = col * row;

           parent = new int[len];
           size = new int[len];
           help = new int[len];

           // 初始全部设为无效
           //如果不做这一步会导致把无效点也进行合并  所有合并必须是有效点  比如0,0处 parent[0] = 0,   那其他的i,j处也为0的话 两个parent都为0，就
           //会被视为同一个集合
           for (int i = 0; i < len; i++) {
               parent[i] = -1;
               size[i] = 0;
           }

           sets = 0;
           for (int i = 0; i < row; i++) {
               for (int j = 0; j < col; j++) {
                   if (boards[i][j] == '1') {
                       int idx = index(i, j);
                       parent[idx] = idx;
                       size[idx] = 1;
                       sets++;
                   }
               }
           }
       }


       public int find(int i){
           if (parent[i] == -1) {
               return -1;  // 无效点不能参与查找
           }
           int cur = 0;
           while(i != parent[i]){
               help[cur++] = i;
               i = parent[i];
           }
           for (cur-- ; cur >= 0; cur--){
               parent[help[cur]] = i;
           }
           return i;
       }

       public void union(int r1, int c1, int r2, int c2){
           int index1 = index(r1, c1);
           int index2 = index(r2, c2);

           if (parent[index1] == -1 || parent[index2] == -1) {
               return;
           }

           int f1 = find(index1);
           int f2 = find(index2);

           if (f1 != f2 && f1 != -1 && f2 != -1) {
               if (size[f1] >= size[f2]) {
                   size[f1] += size[f2];
                   parent[f2] = f1;
               } else {
                   size[f2] += size[f1];
                   parent[f1] = f2;
               }
               sets--;
           }
       }

        public int sets(){
            return sets;
        }

        public int index(int r, int c){
            return r * col + c;
        }
   }

    public static int numsIsIslands3(char[][] boards){
        int row = boards.length;
        int col = boards[0].length;

        UnionFind2 union = new UnionFind2(boards);

        for (int j = 1; j < col; j++){
            if (boards[0][j-1] == '1' && boards[0][j] == '1'){
                union.union(0, j-1, 0, j);
            }
        }

        for (int i = 1; i < row; i++){
            if (boards[i-1][0] == '1' && boards[i][0] == '1'){
                union.union(i-1, 0, i, 0);
            }
        }

        for (int i = 1; i < row; i++){
            for (int j = 1; j < col; j++){
                 if (boards[i][j] == '1'){
                     if (boards[i][j-1] == '1'){
                         union.union(i, j - 1, i, j);
                     }
                     if (boards[i-1][j] == '1'){
                         union.union(i-1, j, i, j);
                     }
                 }
            }
        }

        return union.sets;
    }

    // 为了测试
    public static char[][] generateRandomMatrix(int row, int col) {
        char[][] board = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board[i][j] = Math.random() < 0.5 ? '1' : '0';
            }
        }
        return board;
    }

    // 为了测试
    public static char[][] copy(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        char[][] ans = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                ans[i][j] = board[i][j];
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int row = 0;
        int col = 0;
        char[][] board1 = null;
        char[][] board2 = null;
        char[][] board3 = null;
        long start = 0;
        long end = 0;

        row = 1000;
        col = 1000;
        board1 = generateRandomMatrix(row, col);
        board2 = copy(board1);
        board3 = copy(board1);

        System.out.println("感染方法、并查集(map实现)、并查集(数组实现)的运行结果和运行时间");
        System.out.println("随机生成的二维矩阵规模 : " + row + " * " + col);

        start = System.currentTimeMillis();
        System.out.println("感染方法的运行结果: " + numsIsIslands1(board1));
        end = System.currentTimeMillis();
        System.out.println("感染方法的运行时间: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println("并查集(map实现)的运行结果: " + numsIsIslands2(board2));
        end = System.currentTimeMillis();
        System.out.println("并查集(map实现)的运行时间: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行结果: " + numsIsIslands3(board3));
        end = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行时间: " + (end - start) + " ms");

        System.out.println();


    }
}
