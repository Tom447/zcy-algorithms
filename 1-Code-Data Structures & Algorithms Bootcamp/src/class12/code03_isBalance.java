package class12;


import java.util.Random;

public class code03_isBalance {

    public static class Node{
        public int value;
        public Node left;
        public Node right;

        public Node(int data){
            this.value = data;
        }
    }


    public static class Info{
        public int height;
        public boolean isBalance;

        public Info(int height, boolean isBalance){
            this.height = height;
            this.isBalance = isBalance;
        }
    }

    public static boolean isBalance1(Node head){
        return process1(head).isBalance;
    }

    public static Info process1(Node head){
        if (head == null){
            return new Info(0, true);
        }
        Info leftInfo = process1(head.left);
        Info rightInfo = process1(head.right);

        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        boolean isBalance = true;
        if (!leftInfo.isBalance || !rightInfo.isBalance || Math.abs(leftInfo.height - rightInfo.height) > 1){
            isBalance = false;
        }
        return new Info(height, isBalance);
    }

    public static boolean isBalance2(Node head){
        boolean[] ans = new boolean[1];
        ans[0] = true;
        process2(head, ans);
        return ans[0];
    }

    public static int process2(Node head, boolean[] ans){
        if (!ans[0] || head == null){
            return -1;
        }
        int leftHeight = process2(head.left, ans);
        int rightHeight = process2(head.right, ans);

        if (Math.abs(leftHeight - rightHeight) > 1){
            ans[0] = false;
        }
        return Math.max(leftHeight, rightHeight) + 1;
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
            Node head = generateRandomBST(1, maxLevel, maxValue);
            if (isBalance1(head) != isBalance2(head)){
                System.out.println("oops!");
                break;
            }
        }
        System.out.println("finish!");
    }
}
