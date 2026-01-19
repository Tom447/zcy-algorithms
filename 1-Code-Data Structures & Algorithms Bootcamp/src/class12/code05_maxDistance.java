package class12;

import java.util.*;

public class code05_maxDistance {

    public static class Node{
        public int value;
        public Node left;
        public Node right;

        public Node(int data){
            this.value = data;
        }
    }

    public static class Info{
        public int maxDistance;
        public int height;

        public Info(int m, int h){
            maxDistance = m;
            height = h;
        }
    }

    public static int maxDistance1(Node head){
        return process(head).maxDistance;
    }

    public static Info process(Node root){
        if (root == null){
            return new Info(0, 0);
        }
        Info leftInfo = process(root.left);
        Info rightInfo = process(root.right);

        int height = Math.max(leftInfo.height, rightInfo.height) + 1;

        int p1 = leftInfo.maxDistance;
        int p2 = rightInfo.maxDistance;
        int p3 = leftInfo.height + rightInfo.height + 1;
        int maxDistance = Math.max(p1,Math.max(p2, p3));
        return new Info(maxDistance, height);
    }

    public static int maxDistance2(Node root){
        if (root == null){
            return 0;
        }
        List<Node> list = preList(root);
        HashMap<Node, Node> parentMap = getParentMap(root);
        int max = 0;
        for (int i = 0; i < list.size(); i++){
            for (int j = i; j < list.size(); j++){
                max = Math.max(max, distance(parentMap, list.get(i), list.get(j)));
            }
        }
        return max;
    }

    public static List<Node> preList(Node head){
        List<Node> arr = new ArrayList<>();
        fillPreList(head, arr);
        return arr;
    }

    public static void fillPreList(Node head, List<Node> arr){
        if (head == null){
            return;
        }
        arr.add(head);
        fillPreList(head.left, arr);
        fillPreList(head.right, arr);
    }


    public static HashMap<Node, Node> getParentMap(Node root) {
        HashMap<Node, Node> parentMap = new HashMap<>();
        parentMap.put(root, null);
        fillParentMap(root, parentMap);
        return parentMap;
    }

    public static void fillParentMap(Node root, HashMap<Node, Node> parentMap) {
        if (root == null){
            return;
        }

        if (root.left != null){
            parentMap.put(root.left, root);
            fillParentMap(root.left, parentMap);
        }

        if (root.right != null){
            parentMap.put(root.right, root);
            fillParentMap(root.right, parentMap);
        }
    }

    public static int distance(HashMap<Node, Node> parentMap, Node o1, Node o2) {
        Node cur = o1;
        HashSet<Node> o1Set = new HashSet<>();
        while (cur != null){
            o1Set.add(cur);
            cur = parentMap.get(cur);
        }
        cur = o2;
        while (!o1Set.contains(cur) && cur != null){
            cur = parentMap.get(cur);
        }

        if (cur == null) return 0;

        Node lowestNode = cur;

        int distance1 = 1;
        cur = o1;
        while(cur != lowestNode){
            cur = parentMap.get(cur);
            distance1++;
        }

        int distance2 = 1;
        cur = o2;
        while (cur != lowestNode){
            cur = parentMap.get(cur);
            distance2++;
        }

        return distance1 + distance2 - 1;
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
            if (maxDistance1(head) != maxDistance2(head)){
                System.out.println("oops!");
                break;
            }
        }
    }

}
