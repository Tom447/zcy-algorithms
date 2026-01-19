package class13;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class code01_findLargestBSTSubtreeHead {

    public static class Node{
        public int value;
        public Node left;
        public Node right;

        public Node(int data){
            this.value = data;
        }
    }

    public static Node findLargestBSTSubtreeHead1(Node head){
        if (head == null){
            return null;
        }
        if (getBSTSize(head) != 0){
            return head;
        }
        Node leftAns = findLargestBSTSubtreeHead1(head.left);
        Node rightAns = findLargestBSTSubtreeHead1(head.right);

        return  getBSTSize(leftAns) >= getBSTSize(rightAns) ? leftAns : rightAns;
    }

    public static int getBSTSize(Node root){
        if (root == null){
            return 0;
        }
        List<Node> arr = new ArrayList<>();
        ins(root, arr);
        for (int i = 1; i < arr.size(); i++){
            if (arr.get(i-1).value >= arr.get(i).value){
                return 0;
            }
        }
        return arr.size();
    }

    public static void ins(Node root, List<Node> arr){
        if (root == null){
            return;
        }
        ins(root.left, arr);
        arr.add(root);
        ins(root.right, arr);
    }



    public static class Info{
        public int maxSubBSTSize;
        public Node maxSubBSTHead;
        public int max;
        public int min;

        public Info(int maxSubBSTSize, Node maxSubBSTHead, int max, int min) {
            this.maxSubBSTSize = maxSubBSTSize;
            this.maxSubBSTHead = maxSubBSTHead;
            this.max = max;
            this.min = min;
        }
    }

    public static Node findLargestBSTSubtreeHead2(Node head){
       if (head == null){
           return head;
       }
       return process(head).maxSubBSTHead;
    }

    public static Info process(Node X){
        if (X == null){
            return null;
        }
        Info leftInfo = process(X.left);
        Info rightInfo = process(X.right);


       int maxSubBSTSize = 0;
       Node maxSubBSTHead = null;
       int max = X.value;
       int min = X.value;
        if (leftInfo != null){
            max = Math.max(max, leftInfo.max);
            min = Math.min(min, leftInfo.min);
            maxSubBSTSize = leftInfo.maxSubBSTSize;
            maxSubBSTHead = leftInfo.maxSubBSTHead;
        }

        if (rightInfo != null){
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);
            if (rightInfo.maxSubBSTSize > maxSubBSTSize){
                maxSubBSTSize = rightInfo.maxSubBSTSize;
                maxSubBSTHead = rightInfo.maxSubBSTHead;
            }
        }

        if (
                (leftInfo == null ? true : (leftInfo.maxSubBSTHead == X.left && leftInfo.max < X.value))
                &&
                (rightInfo == null ? true : (rightInfo.maxSubBSTHead == X.right && rightInfo.min > X.value))
        ){
            maxSubBSTHead = X;
            maxSubBSTSize = (leftInfo == null ? 0 : leftInfo.maxSubBSTSize)
                    + (rightInfo == null ? 0 : rightInfo.maxSubBSTSize)
                    + 1;
        }
        return new Info(maxSubBSTSize, maxSubBSTHead, max, min);
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
        int maxValue = 10;
        int maxLevel = 5;
        for (int i = 0; i < testTimes; i++){
            Node head = generateRandomBST(1, maxLevel, maxValue);
            if (findLargestBSTSubtreeHead2(head) != findLargestBSTSubtreeHead1(head)){
                System.out.println("oops");
                break;
            }
        }
    }


}
