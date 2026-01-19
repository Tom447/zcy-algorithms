package class09;

public class code03_smallEqualBigger {

    public static class Node{
        public int value;
        public Node next;


        public Node(int data){
            this.value = data;
        }

        public Node(){
            value = 0;
            next = null;
        }
    }


    public static Node getSmallEqualBiggerLinkList(Node head, int v){
        Node sH = null;
        Node sT = null;
        Node eH = null;
        Node eT = null;
        Node bH = null;
        Node bT = null;
        Node next = null;
        while(head != null){
            next = head.next;
            head.next = null;
            if (head.value < v){
                if (sH == null){
                    sH = head;
                    sT = head;
                }else{
                    sT.next = head;
                    sT = head;
                }
            }else if (head.value == v){
                if (eH == null){
                    eH = head;
                    eT = head;
                }else{
                    eT.next = head;
                    eT = head;
                }
            }else if (head.value > v){
                if (bH == null){
                    bH = head;
                    bT = head;
                }else{
                    bT.next = head;
                    bT = head;
                }
            }
            head = next;
        }

        //如果有小于区域
        if (sT != null){
            sT.next = eH;
            eT = eT == null ? sT : eT;
        }

        //有等于区域eT接bH
        //无等于区域eT就为sT;
        if (eT != null){
            eT.next = bH;
        }

        return sH != null ? sH : (eH != null ? eH : bH);

    }


    public static Node getgetSmallEqualBiggerLinkListByArray(Node head, int v){
        if (head == null){
            return head;
        }
        int i = 0;
        Node cur = head;
        while(cur != null){
            i++;
            cur = cur.next;
        }
        Node[] arr = new Node[i];
        cur = head;
        for (i = 0; i < arr.length; i++){
            arr[i] = cur;
            cur = cur.next;
        }
        arrPartition(arr, v);
        for (i = 1; i < arr.length; i++){
            arr[i-1].next = arr[i];
        }
        arr[arr.length - 1].next = null;
        return arr[0];
    }

    public static void arrPartition(Node[] arr, int v){
        int small = -1;
        int bigger = arr.length;
        int index = 0;
        while (index < bigger){
            if (arr[index].value < v){
                swap(arr, small+1, index);
                small++;
                index++;
            }else if (arr[index].value == v){
                index++;
            }else if (arr[index].value > v){
                swap(arr, index, bigger-1);
                bigger--;
            }
        }
    }

    public static void swap(Node[] arr, int i, int j){
        Node temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


    public static void printLinkedList(Node node) {
        System.out.print("Linked List: ");
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head1 = new Node(7);
        head1.next = new Node(9);
        head1.next.next = new Node(1);
        head1.next.next.next = new Node(8);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(2);
        head1.next.next.next.next.next.next = new Node(5);

        printLinkedList(head1);
//        head1 = getSmallEqualBiggerLinkList(head1, 5);
        head1 = getgetSmallEqualBiggerLinkListByArray(head1, 5);
        printLinkedList(head1);
    }
}
