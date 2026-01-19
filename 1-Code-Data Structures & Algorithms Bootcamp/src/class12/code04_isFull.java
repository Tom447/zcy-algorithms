package class12;

import java.util.Random;

public class code04_isFull {

    public static class Node{
        public int value;
        public Node left;
        public Node right;

        public Node(int data){
            this.value = data;
        }
    }

    //判断是否是满二叉树的逻辑是 2 ^(height - 1) = nodes 否

    public static boolean isFull(Node head){
        if (head == null){
            return true;
        }
        int height = h(head);
        int nodes = n(head);

        return (int)Math.pow(2, height - 1) == nodes;
    }

    public static int h(Node head){
        if (head == null){
            return 0;
        }
        int left = h(head.left);
        int right = h(head.right);

        int height = Math.max(left, right) + 1;
        return height;
    }

    public static int n(Node head){
        if (head == null){
            return 0;
        }
        int left = n(head.left);
        int right = n(head.right);

        int nodes = left + right + 1;

        return nodes;
    }

    public static class Info{
        public int height;
        public int nodes;

        public Info(int h, int n){
            height = h;
            nodes = n;
        }
    }

    public static boolean isFull2(Node head){
        if (head == null){
            return true;
        }
        Info all = process(head);

        return (int)Math.pow(2, all.height - 1) == all.nodes;
    }

    public static Info process(Node head){
        if (head == null){
            return new Info(0, 0);
        }
        Info left = process(head.left);
        Info right = process(head.right);

        int height = Math.max(left.height, right.height) + 1;
        int nodes = left.nodes + right.nodes + 1;

        return new Info(height, nodes);
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

    public static void main(String[] args) {
        int testTimes = 5000;
        int maxLevel = 5;
        int maxValue = 10;
        for (int i = 0; i < testTimes; i++){
            Node head = generateRandomBST(1, maxLevel,maxValue);
            if (isFull(head) != isFull2(head)){
                System.out.println("oops");
                break;
            }
        }
    }
}
