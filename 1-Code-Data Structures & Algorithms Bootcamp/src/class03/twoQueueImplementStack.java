package class03;

import java.util.*;

public class twoQueueImplementStack {

    public static class myStack<T>{
        private Queue<T> queue;
        private Queue<T> help;

        public myStack(){
            queue = new LinkedList<>();
            help = new LinkedList<>();
        }

        public T pop(){
            if (queue.isEmpty()){
               return null;
            }
            while (queue.size() > 1){
                help.offer(queue.poll());
            }
            T ans = queue.poll();
            Queue<T> temp = queue;
            queue = help;
            help = temp;
            return ans;
        }

        public void push(T data){
            queue.offer(data);
        }

        public T peek(){
            if (queue.isEmpty()){
                return null;
            }
            while (queue.size() > 1){
                help.add(queue.poll());
            }
            T ans = queue.peek();
            while(help.size() > 0){
                queue.offer(help.poll());
            }
            return ans;

        }
    }


    public static void main(String[] args) {
        int testTimes = 50000;
        int maxValue = 10;
        int oneTestTimes = 10;

        for (int i = 0; i < testTimes; i++){
            myStack<Integer> mystack = new myStack<Integer>();
            Stack<Integer> stack = new Stack<>();
            Random random = new Random();
            int num = random.nextInt(maxValue * 2 + 1) - maxValue;
            for (int j = 0; j < oneTestTimes; j++){
                if (stack.isEmpty()){
                    mystack.push(num);
                    stack.push(num);
                }else {
                    double p = random.nextDouble();
                    if (p < 0.33){
                        mystack.push(num);
                        stack.push(num);
                    }else if (p < 0.66){
                        if (!mystack.peek().equals(stack.peek())){
                            System.out.println("oops1");
                            return;
                        }
                    } else {
                        if (!mystack.pop().equals(stack.pop())){
                            System.out.println("oops2");
                            return;
                        }
                    }
                }
            }
        }
        return;

    }
}
