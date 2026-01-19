package class12;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class code02_isBST {

    public static class Node{
        public int value;
        public Node left;
        public Node right;

        public Node(int data){
            this.value = data;
        }
    }


    public static class Info{
        public boolean isBST;
        public int max;
        public int min;

        public Info(boolean isBST, int max, int min){
            this.isBST = isBST;
            this.max = max;
            this.min = min;
        }
    }


    public static boolean isBST1(Node head){
        if (head == null){
            return true;
        }
        return process1(head).isBST;
    }

    public static Info process1(Node head){
        if (head == null){
            return null;
        }

        Info leftInfo = process1(head.left);
        Info rightInfo = process1(head.right);

        int max = head.value;
        int min = head.value;
        if (leftInfo != null){
            max = Math.max(max, leftInfo.max);
            min = Math.min(min, leftInfo.min);
        }
        if (rightInfo != null){
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);
        }

        boolean isBST = true;

        // 先判断子树是否是 BST
        if ((leftInfo != null && !leftInfo.isBST) ||
                (rightInfo != null && !rightInfo.isBST)) {
            isBST = false;
        }

        // 再判断当前节点是否符合 BST 性质
        if ((leftInfo != null && leftInfo.max >= head.value) ||
                (rightInfo != null && rightInfo.min <= head.value)) {
            isBST = false;
        }

        return new Info(isBST, max, min);
    }



    public static boolean isBST2(Node head){
        if (head == null){
            return true;
        }
        List<Node> list = getInsArray(head);
        for (int i = 1; i < list.size(); i++){
            if (list.get(i).value <= list.get(i-1).value){
                return false;
            }
        }
        return true;
    }

    public static List<Node> getInsArray(Node cur){
        List<Node> ans = new ArrayList<>();
        if (cur != null){
            Stack<Node> stack = new Stack<>();
            while(!stack.isEmpty() || cur != null){
                if (cur != null){
                    stack.push(cur);
                    cur = cur.left;
                }else{
                    cur = stack.pop();
                    ans.add(cur);
                    cur = cur.right;
                }
            }
        }
        return ans;
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
        printNode(root, "", true);
    }

    private static void printNode(Node node, String prefix, boolean isLeft) {
        if (node != null) {
            System.out.print(prefix);
            System.out.print(isLeft ? "├── " : "└── ");
            System.out.println(node.value);

            // 对右子节点使用不同的前缀
            String newPrefix = prefix + (isLeft ? "│   " : "    ");
            printNode(node.right, newPrefix, false);
            printNode(node.left, newPrefix, true);
        }
    }

    public static void main(String[] args) {
        int testTime = 5000;
        int maxLevel = 5;
        int maxValue = 10;
        for (int i = 0; i < testTime; i++){
            Node head = generateRandomBST(1, maxLevel, maxValue);
            if (isBST1(head) != isBST2(head)){
                System.out.println("oops!");
                printTree(head);
                return;
            }
        }
        System.out.println("finish!");
    }
}
