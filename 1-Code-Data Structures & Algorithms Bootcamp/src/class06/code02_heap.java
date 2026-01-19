package class06;

import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Random;

public class code02_heap {

    public static class MyMaxHeap{
        private int[] heap;
        private final int limit;
        private int heapSize;


       public MyMaxHeap(int size){
           heap = new int[size];
           this.limit = size;
           heapSize = 0;
       }

       public boolean isEmpty(){
           return heapSize == 0;
       }


       public boolean isFull(){
           return heapSize == limit;
       }


       public void push(int value){
          if (heapSize == limit){
              throw new RuntimeException("heap is full");
          }
          heap[heapSize] = value;
          heapInfUp(heap, heapSize++);

       }


       public int pop(){
           if (heapSize == 0){
               throw new RuntimeException("heap is empty");
           }
           int ans = heap[0];
           swap(heap, 0, --heapSize);
           heapIfyDown(heap, 0, heapSize);

           return ans;
       }



        //新加进来的数移动到了index
       private void heapInfUp(int[] arr, int index){
            while (arr[index] > arr[(index - 1) / 2]){
                swap(arr, index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
       }

        private void swap(int[] arr, int i, int j) {
           int temp = arr[i];
           arr[i] = arr[j];
           arr[j] = temp;
        }

        private void heapIfyDown(int[] heap, int index, int heapSize) {
            int left = 2 * index + 1;
            while(left < heapSize){
                int maxChildIndex = left;

                if (left + 1 < heapSize){
                    maxChildIndex = heap[left] > heap[left + 1] ? left : left + 1;
                }

                if (heap[index] > heap[maxChildIndex]){
                    break;
                }
                swap(heap, index, maxChildIndex);
                index = maxChildIndex;
                left = 2 * index + 1;
            }
       }
    }

    public static class easyMaxHeap{
        private int[] heap;
        private final int limit;
        private int heapSize;

        public easyMaxHeap(int size){
            heap = new int[size];
            limit = size;
            heapSize = 0;
        }

        public boolean isEmpty(){
            return heapSize == 0;
        }

        public boolean isFull(){
            return heapSize == limit;
        }

        public int pop(){
            if (heapSize == 0){
                throw new RuntimeException("heap is empty");
            }
            int max = 0;
            for(int i = 1; i < heapSize; i++){
                if (heap[max] < heap[i]){
                    max = i;
                }
            }
            int ans = heap[max];
            swap(heap, max, --heapSize);
            return ans;
        }


        public void push(int value){
            if (heapSize == limit){
                throw new RuntimeException("heap is full");
            }
            heap[heapSize++] = value;
        }

        private void swap(int[] heap, int i, int j) {
            int temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }
    }


    public static void main(String[] args) {
        int testTimes = 50000;
        int maxValue = 10;
        int maxLimit = 10;
        int oneTestTimes = 10;

        for (int i = 0; i < testTimes; i++){
            Random random = new Random();
            int limit = random.nextInt(maxLimit) + 1;
            MyMaxHeap my = new MyMaxHeap(limit);
            easyMaxHeap test = new easyMaxHeap(limit);
            for (int j = 0; j < oneTestTimes; j++){
                int value = random.nextInt(maxValue * 2 + 1) - maxValue;
                if (my.isEmpty() || test.isEmpty()){
                    if (my.isEmpty() != test.isEmpty()){
                        System.out.println("oops1");
                        break;
                    }
                    my.push(value);
                    test.push(value);

                }else if (my.isFull() || test.isFull()){
                    if (my.isFull() != test.isFull()){
                        System.out.println("oops2");
                        break;
                    }
                    if (my.pop() != test.pop()){
                        System.out.println("oops3");
                        break;
                    }
                }else{
                    double n = random.nextDouble();
                    if (n < 0.5){
                        my.push(value);
                        test.push(value);
                    }else{
                        if (my.pop() != test.pop()){
                            System.out.println("oops4");
                            break;
                        }
                    }
                }
            }

        }
        return;
    }
}
