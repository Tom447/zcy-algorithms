package class15;

import java.util.*;

public class code03_NumberOfIslandsII {


    public static List<Integer> numIsLandsII1(int m, int n, int[][] positions){
        List<Integer> ans = new ArrayList<>();
        UnionFind1 uf1 = new UnionFind1(m, n);
        for (int i = 0; i < positions.length; i++){
            ans.add(uf1.connect(positions[i][0], positions[i][1]));
        }
        return ans;

    }

    public static class UnionFind1{
        private int[] parents;
        private int[] sizes;
        private int[] help;
        private int col; //列数
        private int row; //行数
        private int sets;


        public UnionFind1(int m, int n){
            row = m;
            col = n;
            int len = m * n;
            parents = new int[len];
            sizes = new int[len];
            help = new int[len];
            sets = 0;

            for (int i = 0; i < len; i++){
                parents[i] = -1;
                sizes[i] = 0;
            }
        }


        public int index(int r, int c){
            return r * col + c;
        }

        public int find(int i){
            if (parents[i] == -1){
                return -1;
            }

            int cur = 0;
            while(i != parents[i]){
                help[cur++] = i;
                i = parents[i];
            }

            for (cur--; cur >= 0; cur--){
                parents[help[cur]] = i;
            }
            return i;
        }


        public void union(int r1, int c1, int r2, int c2){
            if (r1 < 0 || r1 >= row || r2 < 0 || r2 >= row || c1 < 0 || c1 >= col || c2 < 0 || c2 >= col) {
                return;
            }
            int i1 = index(r1, c1);
            int i2 = index(r2, c2);

            if (sizes[i1] == 0 || sizes[i2] == 0){
                return;
            }

            int f1 = find(i1);
            int f2 = find(i2);
            while(f1 != f2 && f1 != -1 && f2 != -1){
                if (sizes[f1] >= sizes[f2]) {
                    sizes[f1] += sizes[f2];
                    parents[f2] = f1;
                } else {
                    sizes[f2] += sizes[f1];
                    parents[f1] = f2;
                }
                sets--;
            }
        }

        public int connect(int r, int c){
            int index = index(r, c);
            if (sizes[index] > 0){
                return sets;
            }

            parents[index] = index;
            sizes[index] = 1;
            sets++;

            union(r - 1, c, r, c);
            union(r + 1, c, r, c);
            union(r, c - 1, r, c);
            union(r, c + 1, r, c);

            return sets;
        }
    }

    public static List<Integer> numIsLandsII2(int m, int n, int[][] positions){
        List<Integer> ans = new ArrayList<>();
        UnionFind1 uf2 = new UnionFind1(m, n);
        for (int i = 0; i < positions.length; i++){
            ans.add(uf2.connect(positions[i][0], positions[i][1]));
        }
        return ans;
    }

    public static class UnionFind2{
        private HashMap<String, String> parents;
        private HashMap<String, Integer> sizes;
        private List<String> help;
        private int  sets;

        public UnionFind2(){
            parents = new HashMap<>();
            sizes = new HashMap<>();
            help = new ArrayList<>();
            sets = 0;
        }


        public String find(String cur){
            while (cur.equals(parents.get(cur))){
                help.add(cur);
                cur = parents.get(cur);
            }

            for (String str : help){
                parents.put(str, cur);
            }
            help.clear();
            return cur;
        }

        public void union(String s1, String s2){
            if (parents.containsKey(s1) && parents.containsKey(s2)){
                String f1 = find(s1);
                String f2 = find(s2);
                int size1 = sizes.get(s1);
                int size2 = sizes.get(s2);
                String  big = size1 > size2 ? f1 : f2;
                String  small = big == f1 ? f2 : f1;
                parents.put(small, big);
                sizes.put(big, size1 + size2);
                sizes.remove(small);
                sets--;
            }
        }


        public int connect(int r, int c){
            String key = String.valueOf(r) + "_" + String.valueOf(c);
            if (!parents.containsKey(key)){
                parents.put(key, key);
                sizes.put(key, 1);
                sets++;
                String up = String.valueOf(r - 1) + "_" + String.valueOf(c);
                String down = String.valueOf(r + 1) + "_" + String.valueOf(c);
                String left = String.valueOf(r) + "_" + String.valueOf(c - 1);
                String right = String.valueOf(r) + "_" + String.valueOf(c + 1);

                union(up, key);
                union(down, key);
                union(left, key);
                union(right, key);
            }
            return sets;
        }
    }

    public static void main(String[] args) {
        System.out.println("开始测试...");

        int testTime = 1000; // 测试次数
        boolean success = true;

        Random random = new Random();

        for (int i = 0; i < testTime; i++) {
            int m = random.nextInt(50) + 1; // 地图行数
            int n = random.nextInt(50) + 1; // 地图列数
            int k = random.nextInt(100);    // 添加陆地的操作次数

            Set<String> used = new HashSet<>();
            int[][] positions = new int[k][2];

            for (int j = 0; j < k; j++) {
                int r = random.nextInt(m);
                int c = random.nextInt(n);
                String key = r + "_" + c;
                while (used.contains(key)) {
                    r = random.nextInt(m);
                    c = random.nextInt(n);
                    key = r + "_" + c;
                }
                used.add(key);
                positions[j][0] = r;
                positions[j][1] = c;
            }

            List<Integer> ans1 =  numIsLandsII1(m, n, positions);
            List<Integer> ans2 =  numIsLandsII2(m, n, positions);

            if (!ans1.equals(ans2)) {
                System.out.println("❌ 不一致 ❌");
                System.out.println("m = " + m + ", n = " + n + ", k = " + k);
                System.out.println("ans1: " + ans1);
                System.out.println("ans2: " + ans2);
                success = false;
                break;
            }
        }

        if (success) {
            System.out.println("✅ 所有测试通过！");
        }

    }

}
