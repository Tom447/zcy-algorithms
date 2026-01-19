package class13;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class code03_maxHappy {

    public static class Node{
        public int happy;
        public List<Node> nexts;

        public Node(int h){
            happy = h;
            nexts = new ArrayList<>();
        }
    }



    public static int maxHappy1(Node head){
        if (head == null){
            return 0;
        }
        return Math.max(process1(head,false), process1(head,true));
    }

    public static int process1(Node head, boolean come){
       if (head == null){
           return 0;
       }

       if (come){
           int ans = 0;
           for (Node n : head.nexts){
               ans += process1(n, false);
           }
           return head.happy + ans;
       }else {
           int ans = 0;
           for (Node n : head.nexts){
               ans += Math.max(process1(n, true), process1(n, false));
           }
           return ans;
       }
    }

    public static class Info{
        public int yes;
        public int no;

        public Info(int yes, int no) {
            this.yes = yes;
            this.no = no;

        }
    }
    public static int maxHappy(Node head){
        if (head == null){
            return 0;
        }
        Info info = process(head);
        return Math.max(info.yes, info.no);
    }

    public static Info process(Node x){
        if (x == null){
            return new Info(0 ,0);
        }
        int no = 0;
        int yes = x.happy;
        for (Node next : x.nexts){
            Info nextInfo = process(next);
            yes += nextInfo.no;
            no += Math.max(nextInfo.no, nextInfo.yes);
        }
        return new Info(yes, no);
    }

    public static Node generateBoss(int level, int maxLevel, int maxNexts,int maxHappy){
        Random random = new Random();
        if (random.nextDouble() < 0.22){
            return null;
        }
        int happy = random.nextInt(maxHappy + 1);
        Node boss = new Node(happy);
        genarateNexts(boss, level, maxLevel, maxNexts, maxHappy);

        return boss;
    }
    
    
    public static void genarateNexts( Node e, int level, int maxLevel, int maxNexts, int maxHappy) {
        if (level > maxLevel) {
            return;
        }
        int nextsSize = (int) (Math.random() * (maxNexts + 1));
        for (int i = 0; i < nextsSize; i++) {
             Node next = new  Node((int) (Math.random() * (maxHappy + 1)));
            e.nexts.add(next);
            genarateNexts(next, level + 1, maxLevel, maxNexts, maxHappy);
        }
    }


    /**
     * 打印整棵多叉树结构
     */
    public static void printTree(Node root) {
        if (root == null) {
            System.out.println("空树");
            return;
        }
        System.out.println("树结构：");
        printTree(root, "", true);
    }

    /**
     * 递归打印树结构
     *
     * @param node      当前节点
     * @param prefix    当前缩进字符串
     * @param isTail    是否是当前层最后一个子节点
     */
    private static void printTree(Node node, String prefix, boolean isTail) {
        if (node != null) {
            System.out.println(prefix + (isTail ? "└── " : "├── ") + "Happy: " + node.happy);

            for (int i = 0; i < node.nexts.size(); i++) {
                Node child = node.nexts.get(i);
                boolean isLast = i == node.nexts.size() - 1;

                String newPrefix = prefix + (isTail ? "    " : "│   ");
                printTree(child, newPrefix, isLast);
            }
        }
    }
    public static void main(String[] args) {
        int testTimes = 5000;
        int maxValue = 10;
        int maxLevel = 5;
        int maxNexts = 3;
        for (int i = 0; i < testTimes; i++){
                Node head = generateBoss(1, maxLevel, maxNexts, maxValue);
                if (maxHappy(head) != maxHappy1(head)) {
//                    System.out.println(maxHappy(head) + " " + maxHappy1(head));
//                    printTree(head);
                    System.out.println("oops");
                    break;
                }
        }
    }
}
