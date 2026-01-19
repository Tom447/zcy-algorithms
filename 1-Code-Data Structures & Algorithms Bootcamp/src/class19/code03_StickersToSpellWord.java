package class19;

import java.util.HashMap;
import java.util.Random;

public class code03_StickersToSpellWord {


    public static int minStickers1(String[] stickers, String target){
        int ans = process1(stickers, target);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public static int process1(String[] strings, String target){
        if (target.length() == 0){
            return 0;
        }
        int min = Integer.MAX_VALUE;
        for (String first : strings){
            String rest = minus(first, target);
            if (rest.length() != target.length()){
                min = Math.min(min, process1(strings, rest));
            }
        }
//        if (min == Integer.MAX_VALUE){
//            return Integer.MAX_VALUE;
//        }else{
//            min += 1;
//        }
        return  min + (min == Integer.MAX_VALUE ? 0 : 1);
    }

    public static String minus(String s1, String s2){
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int[] count = new int[26];
        for (char c : str1){
            count[c - 'a']++;
        }
        for (char c : str2){
            count[c - 'a']--;
        }
        StringBuilder rest = new StringBuilder();
        for (int i = 0; i < 26; i++){
            if (count[i] > 0){
                rest.append((char)(i + 'a'));
            }
        }

        return rest.toString();
    }

    public static int minStickers2(String[] stickers, String target){
        int N = stickers.length;
        int[][] count = new int[N][26];

        for (int i = 0; i < N; i++){
            char[] str = stickers[i].toCharArray();
            for (int j = 0; j < str.length; j++){
                count[i][str[j] - 'a']++;
            }
        }

        int ans = process2(count, target);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public static int process2(int[][] stickers, String t){
        if (t.length() == 0){
            return 0;
        }
        char[] target = t.toCharArray();
        int[] tcounts = new int[26];
        for (char cha : target){
            tcounts[cha - 'a']++;
        }

        int N = stickers.length;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++){
            int[] sticker = stickers[i];
            //t第一个位置的字符在sticker中不存在，略去
            if (sticker[target[0] - 'a'] > 0){
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < 26; j++){
                    if (tcounts[j] > 0){
                        int nums = tcounts[j] - sticker[j];
                        for (int k=0; k < nums; k++){
                            builder.append((char)(j + 'a'));
                        }
                    }
                }
                String rest = builder.toString();
                min = Math.min(min, process2(stickers, rest));
            }
        }
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }
    public static int minStickers3(String[] stickers, String target){
        int N = stickers.length;
        int[][] count = new int[N][26];
        for (int i = 0; i < N; i++){
            char[] str = stickers[i].toCharArray();
            for (int j = 0; j < str.length; j++){
                count[i][str[j] - 'a']++;
            }
        }
        HashMap<String, Integer> dp = new HashMap<>();
        dp.put("", 0);
        int ans = process3(count, target, dp);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }


    public static int process3(int[][] stickers, String t, HashMap<String, Integer> dp){
        if (dp.containsKey(t)){
            return dp.get(t);
        }
        char[] target = t.toCharArray();
        int[] tcounts = new int[26];
        for (char cha : target){
            tcounts[cha - 'a']++;
        }

        int N = stickers.length;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++){
            int[] sticker = stickers[i];
            //t第一个位置的字符在sticker中不存在，略去
            if (sticker[target[0] - 'a'] > 0){
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < 26; j++){
                    if (tcounts[j] > 0){
                        int nums = tcounts[j] - sticker[j];
                        for (int k=0; k < nums; k++){
                            builder.append((char)(j + 'a'));
                        }
                    }
                }
                String rest = builder.toString();
                min = Math.min(min, process3(stickers, rest, dp));
            }
        }

        int ans = min + ( min == Integer.MAX_VALUE ? 0 : 1);
        dp.put(t, ans);
        return ans;
    }
    public static void main(String[] args) {
        int testTime = 10000; // 测试次数
        System.out.println("开始进行 " + testTime + " 次对拍测试...");

        for (int i = 1; i <= testTime; i++) {
            System.out.println("\n--- Test Case #" + i + " ---");

            // 随机生成贴纸数组
            String[] stickers = generateRandomStickers(5, 10); // 最多5张贴纸，每张贴纸最多10个字符
            String target = generateRandomTarget(10); // 目标字符串最长10个字符

            System.out.print("Stickers: ");
            for (String s : stickers) System.out.print(s + " ");
            System.out.println("\nTarget: " + target);

            // 分别运行三个函数
//            int ans1 = minStickers1(stickers, target);
            int ans2 = minStickers2(stickers, target);
            int ans3 = minStickers3(stickers, target);


            System.out.println("ans2: " + ans2);
            System.out.println("ans3: " + ans3);

            if (ans2 != ans3) {
                System.out.println("❌ 不一致！测试失败！");
                return;
            } else {
                System.out.println("✅ 所有结果一致，测试通过！");
            }
        }

        System.out.println("\n🎉 所有测试通过！");
    }

    // 生成随机贴纸数组
    public static String[] generateRandomStickers(int maxCount, int maxLen) {
        Random rand = new Random();
        int count = rand.nextInt(maxCount) + 1;
        String[] res = new String[count];
        for (int i = 0; i < count; i++) {
            StringBuilder sb = new StringBuilder();
            int len = rand.nextInt(maxLen) + 1;
            for (int j = 0; j < len; j++) {
                char c = (char) ('a' + rand.nextInt(26));
                sb.append(c);
            }
            res[i] = sb.toString();
        }
        return res;
    }

    // 生成随机目标字符串
    public static String generateRandomTarget(int maxLen) {
        Random rand = new Random();
        int len = rand.nextInt(maxLen) + 1;
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < len; j++) {
            char c = (char) ('a' + rand.nextInt(26));
            sb.append(c);
        }
        return sb.toString();
    }


}
