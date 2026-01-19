package class03;

import java.util.Stack;

public class twoStackImplementQueue {

    public static class myQueue{
        private Stack<Integer> pushStack;
        private Stack<Integer> pollStack;

        public myQueue(){
            pushStack = new Stack<Integer>();
            pollStack = new Stack<Integer>();
        }

        public void push(int data){
           pushStack.push(data);
        }

        public int poll(){
            if (pushStack.isEmpty() && pollStack.isEmpty()){
                throw new RuntimeException("queue is empty");
            }
            if (pollStack.isEmpty()){
                while (!pushStack.isEmpty()){
                    pollStack.push(pushStack.pop());
                }
            }

            return pollStack.pop();
        }

        public int peek(){
            if (pollStack.isEmpty() && pushStack.isEmpty()){
                throw new RuntimeException("queue is empty");
            }
            if (pollStack.isEmpty()){
                while (!pushStack.isEmpty()){
                    pollStack.push(pushStack.pop());
                }
            }
            return pollStack.peek();
        }

        public boolean isEmpty(){
            return pushStack.isEmpty() && pollStack.isEmpty();
        }
    }

    public static void main(String[] args) {
        myQueue myQueue = new myQueue();
        myQueue.push(1);
        myQueue.push(2);
        myQueue.push(3);
        myQueue.push(4);
        while (!myQueue.isEmpty()){
            System.out.print(myQueue.poll() + " ");
        }
        System.out.println();

    }
}
