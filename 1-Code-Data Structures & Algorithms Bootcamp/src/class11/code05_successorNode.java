package class11;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class code05_successorNode {

    public static class Node{
        public int value;
        public Node left;
        public Node right;
        public Node parent;

        public Node(int data){
            this.value = data;
        }
    }


    public static Node getSuccessorNode(Node node){
        if (node == null){
            return node;
        }
        //有右子树 则得其右子树的最左节点
        if (node.right != null){
            return getMostLeft(node.right);
        }
        //无右子树，则回找
        Node parent = node.parent;
        while(parent != null && parent.right == node){
            node = parent;
            parent = node.parent;
        }
        return parent;
    }

    public static Node getMostLeft(Node node){
        if (node == null){
            return node;
        }
        while (node.left != null){
            node = node.left;
        }
        return node;
    }



    public static Node getgetSuccessorNodeByIns(Node node){
        if (node == null){
            return node;
        }
        //得到根节点
        Node cur = node;
        while(cur.parent != null){
            cur = cur.parent;
        }
        Node root = cur;
        List<Node> arr = getInsArray(root);
        Node ans = null;
        for (int i = 0; i < arr.size(); i++){
            if (arr.get(i) == node){
               ans = i == arr.size() - 1 ? null : arr.get(i+1);
            }
        }
        return ans;
    }

    public static List<Node> getInsArray(Node cur){
        List<Node> arr = new ArrayList<>();
        if (cur != null){
            Stack<Node> stack = new Stack<>();
            while (!stack.isEmpty() || cur != null){
                if (cur != null){
                    stack.push(cur);
                    cur = cur.left;
                }else{
                    cur = stack.pop();
                    arr.add(cur);
                    cur = cur.right;
                }
            }
        }
        return arr;
    }



    public static Node generateRandomBST(int level, int maxLevel, int maxValue){
        if (level > maxLevel || new Random().nextDouble() < 0.5){
            return null;
        }
        Node head = new Node(new Random().nextInt(maxValue));

        head.left = generateRandomBST(level + 1, maxLevel, maxValue);
        if (head.left != null){
            head.left.parent = head;
        }
        head.right = generateRandomBST(level + 1, maxLevel, maxValue);
        if (head.right != null){
            head.right.parent = head;
        }
        return head;
    }


    public static Node getRandomNode(Node head){
        if (head == null){
            return null;
        }
        List<Node> arr = getInsArray(head);
        int index = new Random().nextInt(arr.size());
        return arr.get(index);
    }

    public static void main(String[] args) {
        int testTimes = 5000;
        int maxLevel = 5;
        int maxValue = 10;

        for (int i = 0; i < testTimes; i++){
            Node head = generateRandomBST(1, maxLevel, maxValue);
            if (head == null){
                continue;
            }
            Node node = getRandomNode(head);
            if (getSuccessorNode(node) != getgetSuccessorNodeByIns(node)){
                System.out.println("oops");
                break;
            }
        }
        System.out.println("finish!");
    }
}
