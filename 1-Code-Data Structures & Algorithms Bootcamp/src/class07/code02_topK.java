package class07;

import java.util.*;


public class code02_topK{


    public static class Customer{
        public int id;
        public int buy;
        public int enterTime;

        public Customer(int i, int b, int e){
            id = i;
            buy = b;
            enterTime = e;
        }

        public Customer(){

        }
    }

    public static class candsComparator implements Comparator<Customer> {

        @Override
        public int compare(Customer o1, Customer o2) {
            return o1.buy != o2.buy ? o2.buy - o1.buy : o1.enterTime - o2.enterTime;
        }
    }

    public static class awardsComparator implements  Comparator<Customer>{

        @Override
        public int compare(Customer o1, Customer o2) {
            return o1.buy != o2.buy ? o1.buy - o2.buy : o1.enterTime - o2.enterTime;
        }
    }

    public static ArrayList<ArrayList<Integer>> test(int[] arr, boolean[] ops, int k){
        //map是购买商品数不为0的客户信息
        HashMap<Integer, Customer> map = new HashMap<>();
        ArrayList<Customer> cands = new ArrayList<>();
        ArrayList<Customer> awards = new ArrayList<>();
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();

        for (int i = 0; i < arr.length; i++){

            int id = arr[i];
            boolean buy = ops[i];

            //当前的时间点的值无效
            //购买数为零并且退货
            if (!buy && !map.containsKey(id)){
                ans.add(getCurAwards(awards));
                continue;
            }
            //当前时间点有效
            //购买数为零但这次购买
            //购买数不为零但这次购买或者退货
            if (!map.containsKey(id)){
                map.put(id, new Customer(id, 0, 0));
            }

            Customer c = map.get(id);
            if (buy){
                c.buy++;
            }else{
                c.buy--;
            }

            if (c.buy == 0){
                map.remove(id);
            }

            if (!cands.contains(c) && ! awards.contains(c)){
                if (awards.size() < k){
                    c.enterTime = i;
                    awards.add(c);
                }else{
                    c.enterTime = i;
                    cands.add(c);
                }
            }

            cleanZero(cands);
            cleanZero(awards);
            cands.sort(new candsComparator());
            awards.sort(new awardsComparator());
            move(cands, awards, i, k);
            ans.add(getCurAwards(awards));
        }

        return ans;
    }

    public static ArrayList<Integer> getCurAwards(ArrayList<Customer> arr){
        ArrayList<Integer> ans = new ArrayList<>();
        for (Customer c : arr){
            ans.add(c.id);
        }
        return ans;
    }

    public static void cleanZero(ArrayList<Customer> arr){
        List<Customer> noZero = new ArrayList<Customer>();
        for (Customer c : arr) {
            if (c.buy != 0) {
                noZero.add(c);
            }
        }
        arr.clear();
        for (Customer c : noZero) {
            arr.add(c);
        }
    }
    //move的作用是整理两个区域的内容
    public static void move(ArrayList<Customer> cands, ArrayList<Customer> awards, int enterTime, int k) {
        if (cands.isEmpty()){
            return;
        }

        if (awards.size() < k){
            Customer c = cands.get(0);
            c.enterTime = enterTime;
            awards.add(c);
            cands.remove(c);
        }else{
            if (!awards.isEmpty() && cands.get(0).buy > awards.get(0).buy){
                Customer oldC = awards.get(0);
                awards.remove(0);
                Customer newC = cands.get(0);
                cands.remove(0);
                oldC.enterTime = enterTime;
                newC.enterTime = enterTime;
                awards.add(newC);
                cands.add(oldC);
            }
        }

    }


    public static class getTopK{
        private HashMap<Integer, Customer> map;
        private HeapGreater<Customer> candsHeap;
        private HeapGreater<Customer> awardsHeap;
        private final int k;


        public getTopK(int k) {
            map = new HashMap<>();
            candsHeap = new HeapGreater<>(new candsComparator());
            awardsHeap = new HeapGreater<>(new awardsComparator());
            this.k = k;
        }

        public void operate(int id, boolean buy, int time) {

            //没有购买过并且退货
            if (!map.containsKey(id) && !buy) {
                return;
            }
            //购买过退货或继续购买
            //没购买过单购买
            if (!map.containsKey(id)) {
                map.put(id, new Customer(id, 0, 0));
            }

            Customer c = map.get(id);
            if (buy) {
                c.buy++;
            } else {
                c.buy--;
            }

            if (c.buy == 0) {
                map.remove(id);
            }

            if (!candsHeap.contain(c) && !awardsHeap.contain(c)) {
                if (awardsHeap.size() < k) {
                    c.enterTime = time;
                    awardsHeap.push(c);
                } else {
                    c.enterTime = time;
                    candsHeap.push(c);
                }
            } else if (candsHeap.contain(c)) {
                if (c.buy == 0) {
                    candsHeap.remove(c);
                } else {
                    candsHeap.resign(c);
                }
            } else if (awardsHeap.contain(c)) {
                if (c.buy == 0) {
                    awardsHeap.remove(c);
                } else {
                    awardsHeap.resign(c);
                }
            }
            awardsMove(time);
        }

        private void awardsMove(int enterTime){
            if (candsHeap.isEmpty()){
                return;
            }
            if (awardsHeap.size() < k){
                Customer p = candsHeap.pop();
                p.enterTime = enterTime;
                awardsHeap.push(p);
            }else{
                if (!candsHeap.isEmpty() && !awardsHeap.isEmpty() && candsHeap.peek().buy > awardsHeap.peek().buy){
                    Customer newAward = candsHeap.pop();
                    newAward.enterTime = enterTime;
                    Customer oldAward = awardsHeap.pop();
                    oldAward.enterTime = enterTime;
                    awardsHeap.push(newAward);
                    candsHeap.push(oldAward);
                }
            }
        }

        public ArrayList<Integer> getNowAwards(){
            ArrayList<Customer> arr = awardsHeap.getAllElement();
            ArrayList<Integer> ans = new ArrayList<>();
            for (Customer c : arr){
                ans.add(c.id);
            }
            return ans;
        }
    }

    //用加强堆实现
    public static ArrayList<ArrayList<Integer>> topK(int[] arr, boolean[] ops, int k){
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        getTopK now = new getTopK(k);
        for (int i = 0; i < arr.length; i++){
            now.operate(arr[i], ops[i], i);
            ans.add(now.getNowAwards());
        }
        return ans;
    }



    public static class  Data{
        private int[] arr;
        private boolean[] ops;

        public Data(int[] arr, boolean[] ops){
            this.arr = arr;
            this.ops = ops;
        }

    }

    public static Data generateRandomData(int maxLen, int maxId){
        Random random = new Random();
        int len = random.nextInt(maxLen + 1);
        int[] arr = new int[len];
        boolean[] ops = new boolean[len];
        for (int i = 0; i < len; i++){
            arr[i] = random.nextInt(maxId + 1);
            ops[i] = random.nextDouble() < 0.5 ? true : false;
        }

        Data ans = new Data(arr, ops);

        return ans;
    }


    public static boolean same(ArrayList<ArrayList<Integer>> arr1, ArrayList<ArrayList<Integer>> arr2){
        if (arr1.size() != arr2.size()){
            return false;
        }

        for (int i = 0 ; i < arr1.size(); i++){
            ArrayList<Integer> cur1 = arr1.get(i);
            ArrayList<Integer> cur2 = arr2.get(i);
            if (cur1.size() != cur2.size()){
                return false;
            }
            cur1.sort((o1, o2) -> o1 - o2);
            cur2.sort((o1, o2) -> o1 - o2);


            for (int j = 0; j < cur1.size(); j++){
                if (!cur1.get(j).equals(cur2.get(j))){
                    return false;
                }
            }
        }
        return true;
    }


    public static void main(String[] args) {
        int testTimes = 5000;
        int maxLen = 10;
        int maxId = 10;
        int maxK = 3;
        for (int i = 0 ; i < testTimes; i++){
            Data data = generateRandomData(maxLen, maxId);
            int[] arr = data.arr;
            boolean[] ops = data.ops;
            Random random = new Random();
            int k = random.nextInt(maxK + 1);
            ArrayList<ArrayList<Integer>> ans1 = topK(arr, ops, k);
            ArrayList<ArrayList<Integer>> ans2 = test(arr, ops, k);

            if (!same(ans1, ans2)){
                for (int j = 0 ; j < ans1.size(); j++){
                    System.out.println(arr[j] + ", " + ops[j] + " ");
                }
                System.out.println("k = " + k);
                System.out.println("topK = "+ ans1);
                System.out.println("test = " + ans2);
                break;
            }
        }

    }
}
