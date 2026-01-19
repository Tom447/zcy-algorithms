package class10;

import java.util.Stack;

public class code03_UnrecursiveTraversalBT {

    public static class Node{
        public int value;
        public Node left;
        public Node right;


        public Node(int v){
            this.value = v;
        }
    }


    public static void pre(Node head){
        System.out.println("pre-order: ");
        if (head != null){
            Stack<Node> stack = new Stack<Node>();
            stack.add(head);
            //头 左 右
            while(!stack.isEmpty()){
                head = stack.pop();
                System.out.print(head.value + " ");
                if (head.right != null){
                    stack.push(head.right);
                }
                if (head.left != null){
                    stack.push(head.left);
                }
            }
            System.out.println();
        }
    }

    public static void ins(Node cur){
        System.out.println("in-order: ");
        if (cur != null){
            Stack<Node> stack = new Stack<>();
            while (!stack.isEmpty() || cur != null){
                if (cur != null){
                    stack.push(cur);
                    cur = cur.left;
                }else{
                    cur = stack.pop();
                    System.out.print(cur.value + " ");
                    cur = cur.right;
                }
            }
        }
        System.out.println();
    }

    public static void pos(Node head){
        System.out.println("pos-order: ");
        if (head != null){
            Stack<Node> stack1 = new Stack<>();
            Stack<Node> stack2 = new Stack<>();
            stack1.add(head);
            //头 右 左 逆序就是 左 右 头
            while(!stack1.isEmpty()){
                head = stack1.pop();
                stack2.push(head);
                if (head.left != null){
                    stack1.push(head.left);
                }
                if (head.right != null){
                    stack1.push(head.right);
                }
            }
            while(!stack2.isEmpty()){
                System.out.print(stack2.pop().value + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        pre(head);
        System.out.println("========");
        ins(head);
        System.out.println("========");
        pos(head);
        System.out.println("========");

    }


}
