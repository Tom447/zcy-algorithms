package class45;

public class code01_InsertS2MakeMostAlphabeticalOrder {
    // 暴力方法
    public static String right(String s1, String s2) {
        if (s1 == null || s1.length() == 0) {
            return s2;
        }
        if (s2 == null || s2.length() == 0) {
            return s1;
        }
        String p1 = s1 + s2;
        String p2 = s2 + s1;
        String ans = p1.compareTo(p2) > 0 ? p1 : p2;
        for (int end = 1; end < s1.length(); end++) {
            String cur = s1.substring(0, end) + s2 + s1.substring(end);
            if (cur.compareTo(ans) > 0) {
                ans = cur;
            }
        }
        return ans;
    }

    // 正式方法 O(N+M) + O(M^2)
    // N : s1长度
    // M : s2长度
    public static String maxCombine(String s1, String s2) {
        if (s1 == null || s1.length() == 0) {
            return s2;
        }
        if (s2 == null || s2.length() == 0) {
            return s1;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int N = str1.length;
        int M = str2.length;
        int min = str1[0];
        int max = str1[0];
        for (int i = 1; i < N; i++) {
            min = Math.min(min, str1[i]);
            max = Math.max(max, str1[i]);
        }
        for (int i = 0; i < M; i++) {
            min = Math.min(min, str2[i]);
            max = Math.max(max, str2[i]);
        }
        int[] all = new int[N + M + 1];
        int index = 0;
        for (int i = 0; i < N; i++) {
            all[index++] = str1[i] - min + 2;
        }
        all[index++] = 1;//加隔断
        for (int i = 0; i < M; i++) {
            all[index++] = str2[i] - min + 2;
        }
        DC3 dc3 = new DC3(all, max - min + 2);
        int[] rank = dc3.rank;
        int comp = N + 1;
        for (int i = 0; i < N; i++) {
            if (rank[i] < rank[comp]) {
                int best = bestSplit(s1, s2, i);
                return s1.substring(0, best) + s2 + s1.substring(best);
            }
        }
        return s1 + s2;
    }
    //先确定范围[first, end]
//    从 s1[first] 和 s2[0] 开始逐字符比较。
//    如果发现 s1[i] < s2[j]，说明从 i 开始，s1 的后缀已经 小于 s2。
//    那么我们最多只需要考虑插入到 i 之前的位置，因为再往后插入不会带来更大字典序。
//    所以设置 end = i，作为后续搜索的右边界。
//    举例：
//
//    s1 = "abcde", s2 = "bcd", first = 1
//    比较 s1[1]='b' vs s2[0]='b' → 相等
//    s1[2]='c' vs s2[1]='c' → 相等
//    s1[3]='d' vs s2[2]='d' → 相等
//    结束，end = N = 5
//    但如果 s2 = "bce"，则在 i=3 时：
//
//    s1[3]='d' < s2[2]='e' → end = 3

//    前缀越多，得到的str字典序可能越大
//    我们尝试在 first+1 到 end 的每个位置插入 s2。
//    对于每个插入位置 i，我们构造一个“前缀”字符串：
//    s1[first ... i-1]：从 first 到 i-1 的部分
//+ s2[0 ... j-1]：s2 的前 j 个字符（j 随 i 增加而减少）
//    这个“前缀”代表了从 first 开始到插入点附近的一段内容，用于比较哪个插入方式能让整体字符串更大。
//            🎯 为什么比较这个“前缀”？
//    因为插入后，字符串从 first 开始的部分会变成：
//    ... + s1[first:i] + s2 + ...
//    s1 = "abcde"
//    s2 = "bcd"
//    first = 1
//i	j=M-(i-first)	s1[first:i]	s2[0:j]	curPrefix = s1[first:i]+s2[0:j]
//            2	4-1=3	"b"	"bcd"	"bbcd"
//            3	2	"bc"	"bc"	"bcbc"
//            4	1	"bcd"	"b"	"bcdb"
//            5	0	"bcde"	""	"bcde"
//    初始bestPrefix = "bcd"
//    比较：
//            "bbcd" vs "bcd" → 小于 ❌
//            "bcbc" vs "bcd" → 小于 ❌
//            "bcdb" vs "bcd" → 大于 ✅ → 更新
//"bcde" vs "bcdb" → 大于 ✅ → 更新
//→ 最终 bestSplit = 5
//
//    意味着：在 s1 的位置 5（即末尾）插入 s2，得到 "abcde" + "bcd" 是最优的。

    public static int bestSplit(String s1, String s2, int first) {
        int N = s1.length();
        int M = s2.length();
        int end = N;
        for (int i = first, j = 0; i < N && j < M; i++, j++) {
            if (s1.charAt(i) < s2.charAt(j)) {
                end = i;
                break;
            }
        }
        String bestPrefix = s2;
        int bestSplit = first;
        for (int i = first + 1, j = M - 1; i <= end; i++, j--) {
            String curPrefix = s1.substring(first, i) + s2.substring(0, j);
            if (curPrefix.compareTo(bestPrefix) >= 0) {
                bestPrefix = curPrefix;
                bestSplit = i;
            }
        }
        return bestSplit;
    }

    public static class DC3 {

        public int[] sa;

        public int[] rank;

        public DC3(int[] nums, int max) {
            sa = sa(nums, max);
            rank = rank();
        }

        private int[] sa(int[] nums, int max) {
            int n = nums.length;
            int[] arr = new int[n + 3];
            for (int i = 0; i < n; i++) {
                arr[i] = nums[i];
            }
            return skew(arr, n, max);
        }

        private int[] skew(int[] nums, int n, int K) {
            int n0 = (n + 2) / 3, n1 = (n + 1) / 3, n2 = n / 3, n02 = n0 + n2;
            int[] s12 = new int[n02 + 3], sa12 = new int[n02 + 3];
            for (int i = 0, j = 0; i < n + (n0 - n1); ++i) {
                if (0 != i % 3) {
                    s12[j++] = i;
                }
            }
            radixPass(nums, s12, sa12, 2, n02, K);
            radixPass(nums, sa12, s12, 1, n02, K);
            radixPass(nums, s12, sa12, 0, n02, K);
            int name = 0, c0 = -1, c1 = -1, c2 = -1;
            for (int i = 0; i < n02; ++i) {
                if (c0 != nums[sa12[i]] || c1 != nums[sa12[i] + 1] || c2 != nums[sa12[i] + 2]) {
                    name++;
                    c0 = nums[sa12[i]];
                    c1 = nums[sa12[i] + 1];
                    c2 = nums[sa12[i] + 2];
                }
                if (1 == sa12[i] % 3) {
                    s12[sa12[i] / 3] = name;
                } else {
                    s12[sa12[i] / 3 + n0] = name;
                }
            }
            if (name < n02) {
                sa12 = skew(s12, n02, name);
                for (int i = 0; i < n02; i++) {
                    s12[sa12[i]] = i + 1;
                }
            } else {
                for (int i = 0; i < n02; i++) {
                    sa12[s12[i] - 1] = i;
                }
            }
            int[] s0 = new int[n0], sa0 = new int[n0];
            for (int i = 0, j = 0; i < n02; i++) {
                if (sa12[i] < n0) {
                    s0[j++] = 3 * sa12[i];
                }
            }
            radixPass(nums, s0, sa0, 0, n0, K);
            int[] sa = new int[n];
            for (int p = 0, t = n0 - n1, k = 0; k < n; k++) {
                int i = sa12[t] < n0 ? sa12[t] * 3 + 1 : (sa12[t] - n0) * 3 + 2;
                int j = sa0[p];
                if (sa12[t] < n0 ? leq(nums[i], s12[sa12[t] + n0], nums[j], s12[j / 3])
                        : leq(nums[i], nums[i + 1], s12[sa12[t] - n0 + 1], nums[j], nums[j + 1], s12[j / 3 + n0])) {
                    sa[k] = i;
                    t++;
                    if (t == n02) {
                        for (k++; p < n0; p++, k++) {
                            sa[k] = sa0[p];
                        }
                    }
                } else {
                    sa[k] = j;
                    p++;
                    if (p == n0) {
                        for (k++; t < n02; t++, k++) {
                            sa[k] = sa12[t] < n0 ? sa12[t] * 3 + 1 : (sa12[t] - n0) * 3 + 2;
                        }
                    }
                }
            }
            return sa;
        }

        private void radixPass(int[] nums, int[] input, int[] output, int offset, int n, int k) {
            int[] cnt = new int[k + 1];
            for (int i = 0; i < n; ++i) {
                cnt[nums[input[i] + offset]]++;
            }
            for (int i = 0, sum = 0; i < cnt.length; ++i) {
                int t = cnt[i];
                cnt[i] = sum;
                sum += t;
            }
            for (int i = 0; i < n; ++i) {
                output[cnt[nums[input[i] + offset]]++] = input[i];
            }
        }

        private boolean leq(int a1, int a2, int b1, int b2) {
            return a1 < b1 || (a1 == b1 && a2 <= b2);
        }

        private boolean leq(int a1, int a2, int a3, int b1, int b2, int b3) {
            return a1 < b1 || (a1 == b1 && leq(a2, a3, b2, b3));
        }

        private int[] rank() {
            int n = sa.length;
            int[] ans = new int[n];
            for (int i = 0; i < n; i++) {
                ans[sa[i]] = i;
            }
            return ans;
        }

    }

    // for test
    public static String randomNumberString(int len, int range) {
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = (char) ((int) (Math.random() * range) + '0');
        }
        return String.valueOf(str);
    }

    // for test
    public static void main(String[] args) {
        int range = 10;
        int len = 50;
        int testTime = 100000;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTime; i++) {
            int s1Len = (int) (Math.random() * len);
            int s2Len = (int) (Math.random() * len);
            String s1 = randomNumberString(s1Len, range);
            String s2 = randomNumberString(s2Len, range);
            String ans1 = right(s1, s2);
            String ans2 = maxCombine(s1, s2);
            if (!ans1.equals(ans2)) {
                System.out.println("Oops!");
                System.out.println(s1);
                System.out.println(s2);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("功能测试结束");

        System.out.println("==========");

        System.out.println("性能测试开始");
        int s1Len = 1000000;
        int s2Len = 500;
        String s1 = randomNumberString(s1Len, range);
        String s2 = randomNumberString(s2Len, range);
        long start = System.currentTimeMillis();
        maxCombine(s1, s2);
        long end = System.currentTimeMillis();
        System.out.println("运行时间 : " + (end - start) + " ms");
        System.out.println("性能测试结束");
    }
}
