package class11;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class code04_TreeMaxWidth {


    public static class NODE {
        public int value;
        public NODE left;
        public NODE right;

        public NODE(int data){
            this.value = data;
        }
    }

    public static int getTreeMaxWidth(NODE root){
        if (root == null){
            return 0;
        }
        NODE curEnd = null;
        NODE nextEnd = null;
        int max = 0;
        Queue<NODE> queue = new LinkedList<>();
        curEnd = root;
        queue.add(root);
        NODE cur = null;
        int curLevelNodes = 0;
        while(!queue.isEmpty()){
            cur = queue.poll();
            if (cur.left != null){
                queue.add(cur.left);
                nextEnd = cur.left;
            }
            if (cur.right != null){
                queue.add(cur.right);
                nextEnd = curEnd.right;
            }
            curLevelNodes++;
            if (cur == curEnd){
                max = Math.max(curLevelNodes, max);
                curEnd = nextEnd;
                curLevelNodes = 0;
                nextEnd = null;
            }
        }
        return max;
    }

    public static int getTreeMaxWidthByMap(NODE root){
        if (root == null){
            return 0;
        }
        HashMap<NODE, Integer> map = new HashMap<>();
        map.put(root, 1);
        Queue<NODE> queue = new LinkedList<>();
        queue.add(root);
        NODE cur = null;
        int max = 0;
        int curLevelNodes = 0;
        int curLevel = 1;
        while (!queue.isEmpty()){
            cur = queue.poll();
            int curNodeLevel = map.get(cur);
            if (cur.left != null){
                queue.add(cur.left);
                map.put(cur.left, curLevel + 1);
            }
            if (cur.right != null){
                queue.add(cur.right);
                map.put(cur.right, curLevel + 1);
            }
            if (curNodeLevel == curLevel){
                curLevelNodes++;
            }else {
                max = Math.max(curLevelNodes, max);
                curLevel++;
                curLevelNodes = 1;
            }
        }
        return Math.max(max, curLevelNodes);
    }


    public static NODE generateRandomBST(int level, int maxLevel, int maxValue){
        if (level > maxLevel || new Random().nextDouble() < 0.5){
            return null;
        }
        NODE head = new NODE(new Random().nextInt(maxValue));
        head.left = generateRandomBST(level + 1, maxLevel, maxValue);
        head.right = generateRandomBST(level + 1, maxLevel, maxValue);
        return head;
    }


    public static void main(String[] args) {
        int testTimes = 5000;
        int maxLevel = 5;
        int maxValue = 10;
        for (int i = 0; i < testTimes; i++){
            NODE head = generateRandomBST(1, maxLevel, maxValue);
            if (getTreeMaxWidth(head) != getTreeMaxWidthByMap(head)){
                System.out.println("oops");
                break;
            }
        }
        System.out.println("finish!");
    }
}
