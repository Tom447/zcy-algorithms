package class03;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class DeleteLinkListValue {

    public static class Node{
        public int value;
        public Node next;

        public Node(int data){
            this.value = data;
        }
    }


    public static void main(String[] args) {
        int testTime = 5000;
        int maxLen = 10;
        int maxValue = 5;
        Random random = new Random();
        for (int i = 0 ; i < testTime; i++){
            int deleteNum = random.nextInt(2 * maxValue + 1)  - maxValue;
            Node list = generateRandomLinklist(maxLen, maxValue, deleteNum);
            List<Integer> origin = getOriginList(list);
            Node delete = DeleteLinklistNum(list, deleteNum);
            if (!check(delete, origin, deleteNum)){
                System.out.println("oops1");
            }
            break;
        }
    }

    private static boolean check(Node head, List<Integer> origin, int deleteNum) {
        if (head == null){
            return true;
        }
        List<Integer> arr = new ArrayList<>();
        for (int i = 0; i < origin.size(); i++){
            if (origin.get(i) != deleteNum){
                arr.add(origin.get(i));
            }
        }
        for (int i = 0; i < arr.size(); i++){
            if (arr.get(i) != head.value){
                return false;
            }
            head = head.next;
        }
        return true;

    }


    private static Node DeleteLinklistNum(Node head, int num) {
        while (head != null){
            if (head.value != num){
                break;
            }
            head = head.next;
        }

        if (head == null){
            return null;
        }

        //head不为空且为等于num
        Node pre = head;
        Node cur = head.next;
        while(cur != null){
            if (cur.value == num){
                pre.next = cur.next;
            }else {
                pre = cur;
            }
            cur = cur.next;
        }
        return head;
    }

    private static List<Integer> getOriginList(Node head) {
        List<Integer> ans = new ArrayList<>();
        while(head != null){
            ans.add(head.value);
            head = head.next;
        }
        return ans;
    }

    private static Node generateRandomLinklist(int maxLen, int maxValue, int Num) {
        //先生成一个数组，把数填满，然后随机交换，然后建立链表
        Random random = new Random();
        int size = random.nextInt(maxLen);
        if (size == 0){
            return null;
        }
        int times = random.nextInt(size + 1);
        //数组中可能存在num也可能不存在
        int deleteNum = random.nextDouble() < 0.5 ? Num : random.nextInt(2 * maxValue + 1)  - maxValue;

        HashSet<Integer> set = new HashSet<>();
        List<Integer> list = new ArrayList<>();
        int num = 0;
        //数组中存在num
        if (deleteNum == Num){
            size -= times;
            while(times != 0){
                list.add(deleteNum);
                times--;
            }
            set.add(deleteNum);
        }else{
            //数组中不存在Num
            set.add(deleteNum);
            list.add(deleteNum);
            size--;
        }

        while(size != 0){
            do{
                num = random.nextInt(2 * maxValue + 1)  - maxValue;
            }while (set.contains(num));
            set.add(num);
            list.add(num);
            size--;
        }

        for(int i = 0; i < list.size();i ++){
            int j = random.nextInt(list.size());
            int temp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, temp);
        }

        Node head = new Node(list.get(0));
        Node pre = head;
        for (int i = 1; i < list.size(); i++){
            Node cur = new Node(list.get(i));
            pre.next = cur;
            pre = cur;
        }

        return head;
    }
}
