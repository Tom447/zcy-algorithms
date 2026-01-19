package class28;

public class code02_AddShortestEnd {

    public static String shortestEnd(String s) {
        if (s == null || s.length() == 0){
            return null;
        }

        char[] str = manacherString(s);
        int[] pArr = new int[str.length];

        int C = -1; //最右扩的中心点
        int R = -1; //最右的扩成功位置的，再下一个位置
        int maxContainsEnd = -1;
        for (int i = 0; i < str.length; i++){
            //当前已知的回文范围是 [C - pArr[C], R)
            //R - i 表示从 i 开始，最多可以向外扩展多少步，而这个‘步数’是包括中心自己（i）在内的。
            //所以是R-i而不是R-i-1
            //在 Manacher 算法中，pArr[i] 是包括中心 i 的最大扩展步数。
            pArr[i] = i < R ? Math.min(pArr[2 * C - i], R - i) : 1;

            while (i + pArr[i] < str.length && i - pArr[i] > -1){
                if (str[i + pArr[i]] == str[i - pArr[i]]){
                    pArr[i]++;
                }else{
                    break;
                }
            }
            if (i + pArr[i] > R){
                R = i + pArr[i];
                C = i;
            }
            if (R == str.length){
                maxContainsEnd = pArr[i];
                break;
            }
        }
        char[] res = new char[s.length() - (maxContainsEnd - 1)];
        for (int i = 0; i < res.length; i++){
            res[res.length - 1 - i] = str[i * 2 + 1];
        }
        return String.valueOf(res);
    }

    public static char[] manacherString(String s){
        char[] charArr = s.toCharArray();
        char[] res = new char[s.length() * 2 + 1];

        int index = 0;
        for (int i = 0; i != res.length; i++){
            res[i] = (i & 1) == 0 ? '#' : charArr[index++];
        }
        return res;
    }
    public static void main(String[] args) {
        String str1 = "abcd123321";
        System.out.println(shortestEnd(str1));
        System.out.println(str1 + shortestEnd(str1));
    }
}
