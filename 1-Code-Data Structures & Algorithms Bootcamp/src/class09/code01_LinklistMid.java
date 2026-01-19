package class09;

public class code01_LinklistMid {
//    1 输入链表头节点，奇数长度返回中点，偶数长度返回上中点
//    2 输入链表头节点，奇数长度返回中点，偶数长度返回下中点
//    3 输入链表头节点，奇数长度返回中点前一个，偶数长度返回上中点前一个
//    4 输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个


    public static class Node{
        public int value;
        public Node next;


        public Node(int v){
            this.value = v;
            this.next = null;
        }
    }


//    奇数长度返回中点，偶数长度返回前中点
    public static Node findMidOrUpperMid(Node head){
        if (head == null){
            return null;
        }
        Node cur = head;
        Node doubleCur = head;
        //只有1个节点的时候
        if (cur != null && cur.next == null){
            return head;
        }
        //只有2个节点的时候
        if (cur != null && cur.next != null && cur.next.next == null){
            return head;
        }
        //至少有3个节点
        while(doubleCur.next != null && doubleCur.next.next != null){
            doubleCur = doubleCur.next.next;
            cur = cur.next;
        }

        return cur;
    }

//    奇数长度返回中点，偶数长度返回后中点
    public static Node findMidOrLowerMid(Node head){
        if (head == null){
            return null;
        }
        Node cur = head;
        Node doubleCur = head;
        int count = 0;
        //只有1个节点的时候
        if (cur != null && cur.next == null){
            return head;
        }
        //只有2个节点的时候
        if (cur != null && cur.next != null && cur.next.next == null){
            return cur.next;
        }
        //至少有3个节点

        Node c = head;
        while(c != null){
            count++;
            c = c.next;
        }
        while(doubleCur.next != null && doubleCur.next.next != null){
            doubleCur = doubleCur.next.next;
            cur = cur.next;
        }

        return count % 2 == 1 ? cur : cur.next;
    }
//    奇数长度返回中点前一个，偶数长度返回前中点前一个
    public static Node findPrevMidOrPrevUpperMid(Node head){
        if (head == null){
            return null;
        }

        Node cur = head;
        Node doubleCur = head;
        Node pre = null;
        int count = 0;
        //只有1个节点
        if (cur != null && cur.next == null){
            return null;
        }
        //只有2个节点
        if (cur != null && cur.next != null && cur.next.next == null){
            return null;
        }
        //至少有3个节点
//        Node c = head;
//        while(c != null){
//            count++;
//            c = c.next;
//        }
        while(doubleCur.next != null && doubleCur.next.next != null){
            doubleCur = doubleCur.next.next;
            pre = cur;
            cur = cur.next;
        }

        return pre;
    }
//    奇数长度返回中点前一个，偶数长度返回后中点前一个
    public static Node findPrevMidOrPrevLowerMid(Node head){
        if (head == null){
            return null;
        }

        Node cur = head;
        Node doubleCur = head;
        Node pre = null;
        int count = 0;
        //只有1个节点
        if (cur != null && cur.next == null){
            return null;
        }
        //只有2个节点
        if (cur != null && cur.next != null && cur.next.next == null){
            return cur;
        }
        //至少有3个节点
        Node c = head;
        while(c != null){
            count++;
            c = c.next;
        }

        while(doubleCur.next != null && doubleCur.next.next != null){
            doubleCur = doubleCur.next.next;
            pre = cur;
            cur = cur.next;
        }

        return count % 2 == 1 ? pre : cur;
    }


    public static void main(String[] args) {


        Node test = null;
        test = new Node(0);
        test.next = new Node(1);
        test.next.next = new Node(2);
        test.next.next.next = new Node(3);
        test.next.next.next.next = new Node(4);
        test.next.next.next.next.next = new Node(5);
        test.next.next.next.next.next.next = new Node(6);
        test.next.next.next.next.next.next.next = new Node(7);
//        test.next.next.next.next.next.next.next.next = new Node(8);
        //3 4 2 2
        System.out.println(findMidOrUpperMid(test).value);
        System.out.println(findMidOrLowerMid(test).value);
        System.out.println(findPrevMidOrPrevUpperMid(test).value);
        System.out.println(findPrevMidOrPrevLowerMid(test).value);

    }

}
