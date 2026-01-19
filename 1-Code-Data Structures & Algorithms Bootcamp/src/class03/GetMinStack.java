package class03;

import java.util.Stack;

public class GetMinStack {

    public static class MyStack{
        private Stack<Integer> dataStack;
        private Stack<Integer> minStack;

        public MyStack(){
            dataStack = new Stack<Integer>();
            minStack = new Stack<Integer>();
        }

        public void push(int data){
           if (this.minStack.isEmpty()){
               minStack.push(data);
           }else if (data <= this.getMin()){
               minStack.push(data);
           }else if (data > this.getMin()){
               minStack.push(this.getMin());
           }
           dataStack.push(data);
        }

        public int pop(){
            if (minStack.isEmpty()){
                throw new RuntimeException("stack is null");
            }
            int ans = this.dataStack.pop();
            this.minStack.pop();
            return ans;
        }

        public int getMin(){
            if (this.minStack == null){
                throw new RuntimeException("stack is null");
            }
            return minStack.peek();
        }
    }

    public static void main(String[] args) {
        MyStack stack = new MyStack();
        stack.push(5);
        stack.push(4);
        stack.push(1);
        stack.push(3);
        stack.pop();
        System.out.println(stack.getMin());

    }
}
