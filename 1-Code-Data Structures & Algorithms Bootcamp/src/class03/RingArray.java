package class03;

import jdk.nashorn.internal.ir.SplitReturn;

public class RingArray {


    public static class myQueue{
        private int[] arr;
        private int head;
        private int tail;
        private int size;
        private int limit;

        public myQueue(int limit){
            arr = new int[limit];
        }

        public boolean isEmpty(){
            return size == 0;
        }

        public void push(int data){
            if (size == limit){
                throw new RuntimeException("队列满了");
            }
            arr[tail] = data;
            size++;
            tail = nextIndex(tail);
        }


        public int pop(){
            if (size == 0){
                throw  new RuntimeException("队列为空");
            }
            size--;
            int ans = arr[head];
            head = nextIndex(head);
            return ans;
        }
        public int nextIndex(int index){
            return index < limit - 1 ? index++ : 0;
        }
    }

}
