package class09;

import java.util.HashMap;

public class code04_copyListWithRandom {


    public static class Node{
        public int value;
        public Node next;
        public Node random;

        public Node(int data){
            this.value = data;
        }
    }


    public static Node getCopyListWithRandomByMap(Node head){
        if (head == null){
            return head;
        }
        HashMap<Node, Node> map = new HashMap<>();
        Node cur = head;
        while(cur != null){
            map.put(cur, new Node(cur.value));
            cur = cur.next;
        }

        cur = head;
        while(cur != null){
            map.get(cur).next = map.get(cur.next);
            map.get(cur).random = map.get(cur.random);
            cur = cur.next;
        }

        return map.get(head);
    }

    public static Node getgetCopyListWithRandomNoBy(Node head){
        if (head == null){
            return head;
        }

        Node cur = head;
        Node next = null;
        //新旧链接
        while(cur != null){
            next = cur.next;
            cur.next = new Node(cur.value);
            cur.next.next = next;
            cur = next;
        }
        //random部分对应链接
        Node copyNode;
        cur = head;
        while(cur != null){
            copyNode = cur.next;
            copyNode.random = cur.random != null ? cur.random.next : null;
            cur = cur.next.next;
        }
        //分开
        cur = head;
        copyNode = null;
        next = null;
        Node res = head.next;
        while(cur != null){
            next = cur.next.next;
            copyNode = cur.next;
            cur.next = next;
            copyNode.next = next != null ? next.next : null;
            cur = next;
        }

        return res;
    }

    public static void printRandLinkedList(Node head) {
        Node cur = head;
        System.out.print("order: ");
        while (cur != null) {
            System.out.print(cur.value + " ");
            cur = cur.next;
        }
        System.out.println();
        cur = head;
        System.out.print("rand:  ");
        while (cur != null) {
            System.out.print(cur.random == null ? "- " : cur.random.value + " ");
            cur = cur.next;
        }
        System.out.println();
    }



    public static void main(String[] args) {
        Node head = null;
        Node res1 = null;
        Node res2 = null;


        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        head.next.next.next.next.next = new Node(6);

        head.random = head.next.next.next.next.next; // 1 -> 6
        head.next.random = head.next.next.next.next.next; // 2 -> 6
        head.next.next.random = head.next.next.next.next; // 3 -> 5
        head.next.next.next.random = head.next.next; // 4 -> 3
        head.next.next.next.next.random = null; // 5 -> null
        head.next.next.next.next.next.random = head.next.next.next; // 6 -> 4

        System.out.println("原始链表");
        printRandLinkedList(head);
        System.out.println("=========================");
        res1 = getCopyListWithRandomByMap(head);
        System.out.println("方法一的拷贝链表：");
        printRandLinkedList(res1);
        System.out.println("=========================");
        res2 = getgetCopyListWithRandomNoBy(head);
        System.out.println("方法二的拷贝链表：");
        printRandLinkedList(res2);
        System.out.println("=========================");
        System.out.println("经历方法二拷贝之后的原始链表：");
        printRandLinkedList(head);
        System.out.println("=========================");
    }
}
