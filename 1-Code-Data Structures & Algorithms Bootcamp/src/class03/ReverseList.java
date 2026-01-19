package class03;

import javax.management.relation.RelationNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReverseList {

    public static class Node{
        public int value;
        public Node next;

        public Node(int data){
            this.value = value;
        }
    }

    public static class DoubleNode{
        public int value;
        public DoubleNode last;
        public DoubleNode next;

        public DoubleNode(int data){
            this.value = data;
        }
    }

    public static void main(String[] args) {
        int testTime = 5000;
        int maxValue = 100;
        int maxLen = 10;
        for (int i = 0; i < testTime; i++){
            Node list1 = generateRandomLinklist(maxLen, maxValue);
            List<Integer> origin_list1 = getLinklistOriginData(list1);
            Node reverse_list1 = reverseLinkList(list1);
            if (!checkLinkListReverse(origin_list1, reverse_list1)){
                System.out.println("oops1");
            }

            Node list2 = generateRandomLinklist(maxLen, maxValue);
            List<Integer> origin_list2 = getLinklistOriginData(list2);
            Node reverse_list2 = testReverseLinkList(list2);
            if (!checkLinkListReverse(origin_list2, reverse_list2)){
                System.out.println("oops2");
            }

            DoubleNode list3 = generateRandomDoubleList(maxLen, maxValue);
            List<Integer> origin_list3 = getDoubleListOriginData(list3);
            DoubleNode reverse_list3 = reverseDoubleList(list3);
            if (!checkDoubleListReverse(origin_list3, reverse_list3)){
                System.out.println("oops3");
            }

            DoubleNode list4 = generateRandomDoubleList(maxLen, maxValue);
            List<Integer> origin_list4 = getDoubleListOriginData(list4);
            DoubleNode reverse_list4 = testReverseDoubleList(list4);
            if (!checkDoubleListReverse(origin_list4, reverse_list4)){
                System.out.println("oops4");
            }
            break;
        }
    }

    private static DoubleNode testReverseDoubleList(DoubleNode head) {
        if (head == null){
            return null;
        }

        List<DoubleNode> arr = new ArrayList<>();
        while(head != null){
            arr.add(head);
            head = head.next;
        }

        arr.get(0).next = null;
        for (int i = 1; i < arr.size(); i++){
            arr.get(i).next = arr.get(i-1);
            arr.get(i-1).last = arr.get(i);
        }

        return arr.get(arr.size() - 1);
    }

    private static boolean checkDoubleListReverse(List<Integer> origin_list, DoubleNode head) {
        DoubleNode end = null;
        for (int i = origin_list.size() - 1; i >= 0; i--){
            if (!origin_list.get(i).equals(head.value)){
                return false;
            }
            end = head;
            head = head.next;
        }

        for (int i = 0; i < origin_list.size(); i++){
            if (!origin_list.get(i).equals(end.value)){
                return false;
            }
            end = end.last;
        }
        return true;
    }

    private static DoubleNode reverseDoubleList(DoubleNode head) {
        if (head == null){
            return null;
        }

        DoubleNode pre = null;
        DoubleNode next = null;
        while (head != null){
            next = head.next;
            head.next = pre;
            head.last = next;
            pre = head;
            head = next;
        }
        return pre;
    }

    private static List<Integer> getDoubleListOriginData(DoubleNode head) {
        List<Integer> ans = new ArrayList<>();
        while (head != null){
            ans.add(head.value);
            head = head.next;
        }
        return ans;
    }

    private static DoubleNode generateRandomDoubleList(int maxLen, int maxValue) {
        Random random = new Random();
        int size = random.nextInt(maxLen + 1);
        if (size == 0){
            return null;
        }

        DoubleNode head = new DoubleNode(random.nextInt(maxValue + 1));
        DoubleNode pre = head;
        size--;

        for (int i = 0; i < size; i++){
            DoubleNode cur = new DoubleNode(random.nextInt(maxValue + 1));
            pre.next = cur;
            cur.last = pre;
            pre = cur;
        }
        return head;
    }

    private static Node testReverseLinkList(Node head) {
        if (head == null){
            return null;
        }

        List<Node> arr = new ArrayList<>();
        while (head != null){
            arr.add(head);
            head = head.next;
        }

        for (int i = 1; i < arr.size(); i++){
            arr.get(i).next = arr.get(i-1);
        }
        return arr.get(arr.size() - 1);
    }

    private static boolean checkLinkListReverse(List<Integer> origin_list, Node head) {
        for (int i = origin_list.size() - 1; i >= 0; i--){
            if (!origin_list.get(i).equals(head.value)){
                return false;
            }
            head = head.next;
        }
        return true;
    }

    private static Node reverseLinkList(Node head) {
        if (head == null){
            return null;
        }
        Node next = null;
        Node pre = null;
        while(head != null){
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    private static List<Integer> getLinklistOriginData(Node head) {
        List<Integer> ans = new ArrayList<>();
        while (head != null){
            ans.add(head.value);
            head = head.next;
        }
        return ans;
    }

    private static Node generateRandomLinklist(int maxLen, int maxValue) {
        Random random = new Random();
        int size = random.nextInt(maxLen + 1);
        if (size == 0){
            return null;
        }
        Node head = new Node(random.nextInt(maxLen + 1));
        size--;
        Node pre = head;
        while (size != 0){
            Node cur = new Node(random.nextInt(maxLen + 1));
            pre.next = cur;
            pre = cur;
            size--;
        }
        return head;
    }


}
