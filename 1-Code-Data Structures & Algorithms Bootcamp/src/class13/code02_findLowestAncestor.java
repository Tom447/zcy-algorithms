package class13;

import java.util.*;

public class code02_findLowestAncestor {

    public static class Node{
        public int value;
        public Node left;
        public Node right;

        public Node(int data){
            this.value = data;
        }
    }

    public static class Info{
        public boolean findA;
        public boolean findB;
        public Node ans;

        public Info(boolean findA, boolean findB, Node ans) {
            this.findA = findA;
            this.findB = findB;
            this.ans = ans;
        }
    }

    public static Node findLowestAncestor1(Node head, Node a, Node b){
        return process(head, a, b).ans;
    }

    public static Info process(Node x, Node a, Node b){
        if (x == null){
            return new Info(false, false, null);
        }
        Info leftInfo = process(x.left, a, b);
        Info rightInfo = process(x.right, a, b);

        boolean findA = x == a || leftInfo.findA || rightInfo.findA;
        boolean findB = x == b || leftInfo.findB || rightInfo.findB;

        Node ans = null;
        if (leftInfo.ans != null){
            ans = leftInfo.ans;
        }else if (rightInfo.ans != null){
            ans = rightInfo.ans;
        }else{
            if (findA && findB){
                ans = x;
            }
        }
        return new Info(findA, findB, ans);
    }

    public static Node findLowestAncestor2(Node head, Node a, Node b){
        if (head == null){
            return null;
        }
        HashMap<Node, Node> parentMap = new HashMap<>();
        parentMap.put(head, null);
        fillParentMap(head, parentMap);
        HashSet<Node> Aset = new HashSet<>();
        Node cur = a;
        Aset.add(cur);
        while(parentMap.get(cur) != null){
             cur = parentMap.get(cur);
             Aset.add(cur);
        }
        
        cur = b;
        while(!Aset.contains(cur)){
            cur = parentMap.get(cur);
        }

        Node lowestAncestor = cur;

        return cur;
    }

    public static void fillParentMap(Node root, HashMap<Node, Node> map){
        if (root.left != null){
            map.put(root.left, root);
            fillParentMap(root.left, map);
        }
        if (root.right != null){
            map.put(root.right, root);
            fillParentMap(root.right, map);
        }
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

    public static Node getOneNode(Node head){
        if (head == null){
            return null;
        }
        List<Node> arr = new ArrayList<>();
        preList(arr, head);
        return arr.get(new Random().nextInt(arr.size()));
    }

    public static void preList(List<Node> arr, Node head){
        if (head == null){
            return;
        }
        arr.add(head);
        preList(arr, head.left);
        preList(arr, head.right);
    }

    public static void main(String[] args) {
        int testTimes = 5000;
        int maxValue = 10;
        int maxLevel = 5;
        for (int i = 0; i < testTimes; i++){
            Node head = generateRandomBST(1, maxLevel, maxValue);
            Node a = getOneNode(head);
            Node b = getOneNode(head);

            if (findLowestAncestor1(head, a, b) != findLowestAncestor2(head, a, b)){
                System.out.println("oops");
                break;
            }
        }
    }
}

