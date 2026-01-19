package class03;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class DoubleEndsQueueAndStack {
    public static class Node<T>{
        public T value;
        public Node<T> last;
        public Node<T> next;

        public Node(T data){
            this.value = data;
        }
    }

    public static class DoubleEndsQueue<T>{
        public Node<T> head;
        public Node<T> tail;


        public boolean isEmpty(){
            return head == null;
        }

        public void addFromHead(T value){
            Node<T> cur = new Node<T>(value);
            if (head == null){
                head = cur;
                tail = cur;
            }else{
                cur.next = head;
                head.last = cur;
                head = cur;
            }
        }

        public void addFromBottom(T value){
            Node<T> cur = new Node<T>(value);
            if (head == null){
                head = cur;
                tail = cur;
            }else{
                tail.next = cur;
                cur.last = tail;
                tail = cur;
            }
        }

        public T popFromHead(){
            if (head == null){
                return null;
            }
            T num = head.value;
            if (head.next == null){
                head = null;
                tail = null;
            }else {
                Node<T> cur = head;
                head = head.next;
                cur.next = null;
                head.last = null;
            }
            return num;
        }

        public T popFromBottom(){
            if (head == null){
                return null;
            }
            T num = tail.value;
            if (tail.next == null){
                head = null;
                tail = null;
            }else {
                Node<T> cur = tail;
                tail = tail.last;
                tail.next = null;
                cur.last = null;
            }
            return num;
        }
    }

    public static class MyStack<T>{
        private DoubleEndsQueue<T> stack;

        public MyStack(){
            stack = new DoubleEndsQueue<>();
        }

        public boolean isEmpty(){
            return stack.isEmpty();
        }

        public void push(T value){
            stack.addFromHead(value);
        }

        public T pop(){
            return stack.popFromHead();
        }
    }

    public static class MyQueue<T>{
        private DoubleEndsQueue<T> queue;

        public MyQueue(){
            queue = new DoubleEndsQueue<>();
        }

        public boolean isEmpty(){
            return queue.isEmpty();
        }

        public void push(T value){
            queue.addFromBottom(value);
        }

        public T pop(){
           return queue.popFromHead();
        }
    }

    public static void main(String[] args) {
        int testTimes = 5000;
        int maxValue = 10;
        int oneTestTimes = 10;
        for (int i = 0; i < testTimes; i++){
            MyStack<Integer> myStack = new MyStack<Integer>();
            MyQueue<Integer> myQueue = new MyQueue<>();
            Stack<Integer> stack = new Stack<Integer>();
            Queue<Integer> queue = new LinkedList<Integer>();
            for(int j = 0; j < oneTestTimes; j++){
                Random random = new Random();
                int num = random.nextInt(2 * maxValue + 1) - maxValue;
                if (stack.isEmpty()){
                    myStack.push(num);
                    stack.push(num);
                }else{
                    if (random.nextDouble() < 0.5){
                        myStack.push(num);
                        stack.push(num);
                    }else{
                        if (!myStack.pop().equals(stack.pop())){
                            System.out.println("oops1");
                        }
                    }
                }

                num = random.nextInt(2 * maxValue + 1) - maxValue;
                if (queue.isEmpty()){
                    myQueue.push(num);
                    queue.add(num);
                }else{
                    if (random.nextDouble() < 0.5){
                        myQueue.push(num);
                        queue.offer(num);
                    }else{
                        if (!myQueue.pop().equals(queue.poll())){
                            System.out.println("oops2");
                        }
                    }
                }
            }
        }
    }




}
