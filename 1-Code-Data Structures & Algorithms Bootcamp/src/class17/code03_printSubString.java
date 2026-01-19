package class17;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class code03_printSubString {

    public static List<String> getSubString(String str){
        char[] arr = str.toCharArray();
        List<String> ans = new ArrayList<>();
        String path = "";
        process1(arr, ans, 0, path);
        return ans;
    }


    public static void process1(char[] arr, List<String> ans, int index, String path){
        if (index == arr.length){
            ans.add(path);
            return;
        }
        String no = path;
        process1(arr, ans, index + 1, no);
        String yes = path + String.valueOf(arr[index]);
        process1(arr, ans, index + 1, yes);
    }

    public static HashSet<String> getSubStringNoRepeat(String str){
        char[] arr = str.toCharArray();
        HashSet<String> ans = new HashSet<>();
        String path = "";
        process2(arr, ans, 0, path);
        return ans;
    }


    public static void process2(char[] arr, HashSet<String> ans, int index, String path){
        if (index == arr.length){
            ans.add(path);
            return;
        }
        String no = path;
        process2(arr, ans, index+1, no);
        String yes = path + String.valueOf(arr[index]);
        process2(arr, ans, index + 1, yes);
    }


    public static void main(String[] args) {
        String str = "abcd";
        List<String> subString = getSubString(str);
        HashSet<String> subStringNoRepeat = getSubStringNoRepeat(str);


        for (String s : subString){
            System.out.print(s + " ");
        }
        System.out.println();
        for (String s: subStringNoRepeat){
            System.out.print(s + " ");
        }
    }


}
