package class17;

import java.util.ArrayList;
import java.util.List;

public class code04_PrintAllPermutations {

    public static List<String> permutation1(String s){
        List<String> ans = new ArrayList<>();
        char[] str = s.toCharArray();
        List<Character> rest = new ArrayList<>();
        for (char c : str){
            rest.add(c);
        }
        String path = "";
        f(rest, ans, path);
        return ans;
    }

   public static void f(List<Character> rest, List<String> ans,String path){
        if (rest.isEmpty()){
            ans.add(path);
        }else{
            int N = rest.size();
            for (int i = 0; i < N; i++){
                char cur = rest.get(i);
                rest.remove(i);
                f(rest, ans,path + cur);
                rest.add(i, cur);
            }
        }
   }


   public static List<String> permutation2(String s){
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0){
            return ans;
        }
        char[] str = s.toCharArray();
        f2(str, 0, ans);
        return ans;

   }

   public static void f2(char[] str, int index, List<String> ans){
        if (index == str.length){
            ans.add(String.valueOf(str));
            return;
        }else{
            for (int i = index; i < str.length ; i++){
                swap(str, index, i);
                f2(str, index+ 1, ans);
                swap(str, index, i);
            }
        }
   }

    public static List<String> permutation3(String s){
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0){
            return ans;
        }
        char[] str = s.toCharArray();
        f3(str, 0, ans);
        return ans;

    }

    public static void f3(char[] str, int index, List<String> ans){
        if (index == str.length){
            ans.add(String.valueOf(str));
            return;
        }else{
            boolean[] visted = new boolean[256];
            for (int i = index; i < str.length ; i++){
                if (!visted[i]){
                    visted[i] = true;
                    swap(str, index, i);
                    f2(str, index+ 1, ans);
                    swap(str, index, i);
                }
            }
        }
    }



   public static void swap(char[] str, int i, int j){
        char temp = str[i];
        str[i] = str[j];
        str[j] = temp;
   }

    public static void main(String[] args) {
        String str = "aabc";
        List<String> strings1 = permutation1(str);

        for (String s: strings1){
            System.out.print(s + " ");
        }
        System.out.println();


        List<String> strings2 = permutation2(str);

        for (String s: strings2){
            System.out.print(s + " ");
        }
        System.out.println();

        List<String> strings3 = permutation3(str);

        for (String s: strings3){
            System.out.print(s + " ");
        }
        System.out.println();
    }
}
