package class07;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class HeapGreater<T>{
    private ArrayList<T> heap;
    private int heapSize;
    private HashMap<T, Integer> indexMap;
    private Comparator<? super T> comp;

    public HeapGreater(Comparator<T> c){
        heap = new ArrayList<>();
        heapSize = 0;
        indexMap = new HashMap<>();
        comp = c;
    }

    public boolean isEmpty(){
        return heapSize == 0;
    }

    public int size(){
        return heapSize;
    }

    public boolean contain(T item){
        return indexMap.containsKey(item);
    }

    public T peek(){
        return heap.get(0);
    }

    public T pop(){
       T ans = heap.get(0);
       swap(0, heapSize-1);
       indexMap.remove(ans);
       heap.remove(heapSize-1);
       heapSize--;

       heapIfyDown(0);
       return ans;
    }

    public void push(T item){
        heap.add(item);
        indexMap.put(item, heapSize);
        heapIfyUp(heapSize++);
    }



    public void remove(T item){
        T lastItem = heap.get(heapSize - 1);
        int index = indexMap.get(item);
        heap.remove(heapSize-1);
        heapSize--;
        indexMap.remove(item);
        if (lastItem != item){
            heap.set(index, lastItem);
            indexMap.put(lastItem, index);
            resign(lastItem);
        }
    }

    public ArrayList<T> getAllElement(){
        ArrayList<T> arr = new ArrayList<>();
        for (T c : heap){
            arr.add(c);
        }
        return arr;
    }

    public void resign(T c){
        heapIfyUp(indexMap.get(c));
        heapIfyDown(indexMap.get(c));
    }
    public void heapIfyDown(int index){
        int left = 2 * index + 1;
        while(left < heapSize){
            int best = left;
            best = left + 1 < heapSize && comp.compare(heap.get(left + 1), heap.get(left)) < 0 ? left + 1 : left;
            if (comp.compare(heap.get(index), heap.get(best)) < 0){
                break;
            }
            swap(index, best);
            index = best;
            left = 2 * index + 1;
        }
    }

    public void heapIfyUp(int index){
        while (comp.compare(heap.get(index), heap.get((index - 1) / 2)) < 0){
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    private void swap(int i, int j){
        T o1 = heap.get(i);
        T o2 = heap.get(j);
        heap.set(i, o2);
        heap.set(j, o1);
        indexMap.put(o2, i);
        indexMap.put(o1, j);
    }
}
