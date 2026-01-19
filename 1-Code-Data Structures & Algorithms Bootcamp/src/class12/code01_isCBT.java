package class12;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class code01_isCBT {


    public static class Node{
        public int value;
        public Node left;
        public Node right;

        public Node(int data){
            this.value = data;
        }
    }

    public static boolean isCBT(Node cur){
        if (cur == null){
            return true;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(cur);
        boolean fondNonFullNode = false;
        while(!queue.isEmpty()){
            cur = queue.poll();
            if (cur.left != null){
                if (fondNonFullNode){
                    return false;
                }
                queue.add(cur.left);
            }else{
                fondNonFullNode = true;
            }
            if (cur.right != null){
                if (fondNonFullNode){
                    return false;
                }
                queue.add(cur.right);
            }else {
                fondNonFullNode = true;
            }
        }
        return true;
    }

    public static class Info{
        public boolean isFull;
        public boolean isCBT;
        public int height;

        public Info(boolean isFull, boolean isCBT, int height){
           this.isFull = isFull;
           this.isCBT = isCBT;
           this.height = height;
        }
    }


    public static boolean isCBT2(Node head){
        if (head == null){
            return true;
        }
        return process(head).isCBT;
    }

    public static Info process(Node head){
        if (head == null){
            return new Info(true, true, 0);
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);

        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;

        boolean isCBT = false;
        if (isFull){
            isCBT = true;
        }else{
           if (leftInfo.isCBT && rightInfo.isCBT){
               if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height + 1){
                   isCBT = true;
               }
               //左满 又不满
               if (leftInfo.isFull && rightInfo.isCBT && leftInfo.height == rightInfo.height){
                   isCBT = true;
               }
               //左不满，右满
               if (leftInfo.isCBT && rightInfo.isFull && leftInfo.height == rightInfo.height + 1){
                   isCBT = true;
               }
           }
        }
        return new Info(isFull,isCBT,height);
    }

    public static Node generateRandomBST(int level, int maxLevel, int maxValue){
        if (level > maxLevel || new Random().nextDouble() < 0.5){
            return null;
        }
        Node head = new Node(new Random().nextInt(maxValue));
        head.left = generateRandomBST(level + 1, maxLevel, maxValue);
        head.right = generateRandomBST(level + 1, maxLevel, maxValue);
        return head;
    }
    public static void printTree(Node root) {
        System.out.println("Binary Tree:");
        if (root == null) {
            System.out.println("  <empty>");
            return;
        }
        printNode(root, "", true);
    }

    private static void printNode(Node node, String prefix, boolean isTail) {
        if (node != null) {
            System.out.println(prefix + (isTail ? "└── " : "├── ") + node.value);

            String newPrefix = prefix + (isTail ? "    " : "│   ");

            // 先右后左，保证打印顺序看起来更自然
            if (node.right != null) {
                printNode(node.right, newPrefix, node.left == null);
            }
            if (node.left != null) {
                printNode(node.left, newPrefix, node.right == null);
            }
        }
    }
    public static void main(String[] args) {
        int testTimes = 5000;
        int maxLevel = 5;
        int maxValue = 10;
        for (int i = 0 ; i < testTimes; i ++){
            Node head = generateRandomBST(1, maxLevel, maxValue);
            if (isCBT(head) != isCBT2(head)){
                System.out.println("oops");
                System.out.println(isCBT(head) + " " + isCBT2(head));
                printTree(head);
                break;
            }
        }
    }
}
