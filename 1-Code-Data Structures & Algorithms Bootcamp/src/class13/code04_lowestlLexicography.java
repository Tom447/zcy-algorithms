package class13;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

public class code04_lowestlLexicography {





    public static class myComparator implements Comparator<String>{

        @Override
        public int compare(String a, String b) {
            return (a + b).compareTo(b + a);
        }
    }


    public static String getowestlLexicography1(String[] strs){
        if (strs == null || strs.length == 0){
            return "";
        }
        Arrays.sort(strs, new myComparator());
        String res = "";
        for (int i = 0; i < strs.length; i++){
            res += strs[i];
        }
        return res;
    }


    public static String getowestlLexicography2(String[] strs){
        if (strs == null || strs.length == 0){
            return "";
        }
        TreeSet<String> ans = process(strs);
        return ans.size() == 0 ? "" : ans.first();
    }

    public static TreeSet<String> process(String[] strs){
        TreeSet<String> ans = new TreeSet<>();
        if (strs.length == 0){
            ans.add("");
            return ans;
        }
        for (int i = 0; i < strs.length; i++){
            String first = strs[i];
            String[] nexts = removeIndexStrs(strs, i);
            TreeSet<String> next = process(nexts);
            for (String cur : next){
                ans.add(first + cur);
            }
        }
        return ans;
    }

    public static String[] removeIndexStrs(String[] strs, int index){
        String[] ans = new String[strs.length - 1];
        int cur = 0;
        for(int i = 0; i < strs.length; i++){
            if (i == index){
                continue;
            }else{
                ans[cur++] = strs[i];
            }
        }
        return ans;
    }
    // for test
    public static String generateRandomString(int strLen) {
        char[] ans = new char[(int) (Math.random() * strLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 5);
            ans[i] = (Math.random() <= 0.5) ? (char) (65 + value) : (char) (97 + value);
        }
        return String.valueOf(ans);
    }


    // for test
    public static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int) (Math.random() * arrLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }

    // for test
    public static String[] copyStringArray(String[] arr) {
        String[] ans = new String[arr.length];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = String.valueOf(arr[i]);
        }
        return ans;
    }


    public static void main(String[] args) {
        int arrLen = 6;
        int strLen = 5;
        int testTimes = 10000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String[] arr1 = generateRandomStringArray(arrLen, strLen);
            String[] arr2 = copyStringArray(arr1);
            if (!getowestlLexicography1(arr1).equals(getowestlLexicography2(arr2))) {
                for (String str : arr1) {
                    System.out.print(str + ",");
                }
                System.out.println();
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("finish!");
    }
}
