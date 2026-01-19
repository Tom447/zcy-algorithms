package class30;

import java.util.*;

public class code01_MorrisTraversal {

    public static class Node{
        public int value;
        public Node left;
        public Node right;

        public Node(int v){
            value = v;
        }
    }

    public static void process(Node head){
        if (head == null){
            return;
        }

        //1
        process(head.left);
        //2
        process(head.right);
        //3
    }

    public static void processPre(Node head){
        if (head == null){
            return;
        }

        System.out.print(head.value + " ");
        processPre(head.left);
        //2
        processPre(head.right);
        //3
    }

    public static void processIns(Node head){
        if (head == null){
            return;
        }


        processIns(head.left);
        System.out.print(head.value + " ");
        processIns(head.right);
    }

    public static void processPro(Node head){
        if (head == null){
            return;
        }


        processPro(head.left);
        processPro(head.right);
        System.out.print(head.value + " ");
    }


    public static void morris(Node head){
        if (head == null){
            return;
        }
        Node cur = head;
        Node mostRight = null;
        while (cur != null){
            mostRight = cur.left;
            if (mostRight != null){
                while (mostRight.right != null && mostRight.right != cur){
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null){
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                }else{
                    mostRight.right = null;
                }
            }
            cur = cur.right;
        }
    }

    public static void morrisPre(Node head){
        if (head == null){
            return;
        }
        Node cur = head;
        Node mostRight = null;
        while (cur != null){
            mostRight = cur.left;
            if (mostRight != null){
                while (mostRight.right != null && mostRight.right != cur){
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null){
                    System.out.print(cur.value + " ");
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                }else{
                    mostRight.right = null;
                }
            }else{
                System.out.print(cur.value + " ");
            }
            cur = cur.right;
        }
        System.out.println();
    }

    public static void morrisIns(Node head){
        if (head == null){
            return;
        }
        Node cur = head;
        Node mostRight = null;
        while (cur != null){
            mostRight = cur.left;
            if (mostRight != null){
                while (mostRight.right != null && mostRight.right != cur){
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null){
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                }else{
                    mostRight.right = null;
                }
            }
            //1 没有左子树的情况下会打印
            //2 第二次来到cur的时候，并且准备往cur的右子树移动的时候打印
            System.out.print(cur.value + " ");
            cur = cur.right;
        }
        System.out.println();
    }

    public static void morrisPro(Node head){
        if (head == null){
            return;
        }
        Node cur = head;
        Node mostRight = null;
        while (cur != null){
            mostRight = cur.left;
            if (mostRight != null){
                while (mostRight.right != null && mostRight.right != cur){
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null){
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                }else{
                    mostRight.right = null;
                    printEdge(cur.left);

                }
            }
            cur = cur.right;
        }
        printEdge(head);
        System.out.println();
    }


    public static void printEdge(Node head){
        Node tail = reverseEdge(head);
        Node cur = tail;
        while (cur != null){
            System.out.print(cur.value + " ");
            cur = cur.right;
        }
        reverseEdge(tail);
    }
    public static Node reverseEdge(Node from){
        Node pre = null;
        Node next = null;
        while(from != null){
            next = from.right;
            from.right = pre;
            pre = from;
            from = next;
        }
        return pre;
    }


    public static boolean isBST(Node head){
        if (head == null){
            return true;
        }

        Node cur = head;
        Node mostRight = null;
        Integer pre = null;
        boolean ans = true;
        while (cur != null){
            mostRight = cur.left;
            if (mostRight != null){
                while (mostRight.right != null && mostRight.right != cur){
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null){
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                }else{
                    mostRight.right = null;
                }
            }
            System.out.print(cur.value + " ");
            if (pre != null && pre >= cur.value){
                ans = false;
            }
            pre = cur.value;
            cur = cur.right;
        }
        return ans;
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
        int maxLevel = 10;
        int maxValue = 10;

        Node head = generateRandomBST(1 , maxLevel, maxValue);
//        processPre(head);
//        System.out.println();
//        morrisPre(head);

//        processIns(head);
//        System.out.println();
//        morrisIns(head);

        processPro(head);
        System.out.println();
        morrisPro(head);

    }
}

