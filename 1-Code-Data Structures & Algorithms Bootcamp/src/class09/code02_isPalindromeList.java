package class09;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Stack;

public class code02_isPalindromeList {


    public static final LinkedList<Object> OBJECTS = new LinkedList<>();

    public static class Node{
        private int value;
        private Node next;

        public Node(int data){
            this.value = data;
        }
    }


    public static boolean isPalindromeLinkList(Node head){
        //无节点或只有一个节点
        if (head == null || (head != null && head.next == null)){
            return true;
        }
        //只有两个节点
        if (head != null && head.next != null && head.next.next == null){
            if (Objects.equals(head.value, head.next.value)){
                return true;
            }else{
                return false;
            }
        }
        //有3个或3个以上节点
        Node cur = head;
        Node doubleCur = head;
        while(doubleCur.next != null && doubleCur.next.next != null){
            doubleCur = doubleCur.next.next;
            cur = cur.next;
        }

        //如果是奇数链表cur是中间点，如果是偶数链表cur是前节点
        //将链表部分逆转
        Node end = reverseLinklist(cur);

        Node start = head;

        while(start != null && end != null){
            if (!Objects.equals(start.value, end.value)){
                return false;
            }
            start = start.next;
            end = end.next;
        }
        return true;
    }


    public static Node reverseLinklist(Node head){
        Node pre = null;
        Node next = null;
        Node cur = head;
        while (cur != null){
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }

        return pre;
    }

    public static boolean isPalindromeLinkListByStack(Node head){
        if (head == null){
            return true;
        }
        Stack<Node> stack = new Stack<>();
        Node cur = head;
        while(cur != null){
            stack.push(cur);
            cur = cur.next;
        }
        cur = head;
        while (cur != null){
            if (!Objects.equals(cur.value, stack.pop().value)){
                return false;
            }
            cur = cur.next;
        }
        return true;
    }


    public static void main(String[] args) {
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);


        Node s = new Node(1);
        s.next = new Node(2);
        s.next.next = new Node(3);


        System.out.println(isPalindromeLinkList(head));
        System.out.println(isPalindromeLinkListByStack(s));
    }
}
