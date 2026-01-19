package class11;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class code02_SerializeAndReconstructTree {

    public static class Node{
        public int value;
        public Node left;
        public Node right;

        public Node(int value){
            this.value = value;
        }
    }

    public static Queue<String> preSerialize(Node head){
        Queue<String> ans = new LinkedList<>();
        pres(head, ans);
        return ans;
    }

    public static void pres(Node head, Queue<String> ans){
        if (head == null){
            ans.add(null);
            return;
        }
        ans.add(String.valueOf(head.value));
        pres(head.left, ans);
        pres(head.right, ans);
    }


    public static Node buildTreeByPreQueue(Queue<String> preList){
        if (preList == null || preList.size() == 0){
            return null;
        }
        return preb(preList);
    }

    public static Node preb(Queue<String> preList){
        //中 左 右
        String value = preList.poll();
        if (value == null){
            return null;
        }
        Node head = new Node(Integer.valueOf(value));
        head.left = preb(preList);
        head.right = preb(preList);

        return head;
    }

    public static Queue<String> posSerialize(Node head){
        Queue<String> ans = new LinkedList<>();
        pose(head, ans);
        return ans;
    }

    public static void pose(Node head, Queue<String> ans){
        if (head == null){
            ans.add(null);
            return;
        }
        pose(head.left, ans);
        pose(head.right, ans);
        ans.add(String.valueOf(head.value));
    }

    public static Node buildTreeByPoseQueue(Queue<String> posList){
        if (posList == null || posList.size() == 0){
            return null;
        }
        //左右中 逆转 中右左
        Stack<String> stack = new Stack<>();
        while(!posList.isEmpty()){
            stack.push(posList.poll());
        }
        return poseb(stack);
    }

    public static Node poseb(Stack<String> stack){
        //左右中
        String value = stack.pop();
        if (value == null){
            return null;
        }
        Node head = new Node(Integer.valueOf(value));
        head.right = poseb(stack);
        head.left = poseb(stack);

        return head;
    }

    public static Queue<String> levelSerialize(Node head){
        Queue<String> ans = new LinkedList<>();
        if (head == null){
            ans.add(null);
        }else {
            Queue<Node> queue = new LinkedList<>();
            queue.add(head);
            ans.add(String.valueOf(head.value));
            while(!queue.isEmpty()){
                head = queue.poll();
                if (head.left != null){
                    queue.add(head.left);
                    ans.add(String.valueOf(head.left.value));
                }else{
                    ans.add(null);
                }

                if (head.right != null){
                    queue.add(head.right);
                    ans.add(String.valueOf(head.right.value));
                }else{
                    ans.add(null);
                }
            }
        }
        return ans;
    }

    public static Node buildTreeByLevelQueue(Queue<String> levelList){
       if (levelList == null || levelList.size() == 0){
           return null;
       }
       Node head = generateNode(levelList.poll());
       Queue<Node> queue = new LinkedList<>();
       if (head != null){
           queue.add(head);
       }
       Node node = null;
       while (!queue.isEmpty()){
           node = queue.poll();
           node.left = generateNode(levelList.poll());
           node.right = generateNode(levelList.poll());
           if (node.left != null){
               queue.add(node.left);
           }
           if (node.right != null){
               queue.add(node.right);
           }
       }
       return head;
    }



    private static Node generateNode(String value) {
        if (value == null){
            return null;
        }
        return new Node(Integer.valueOf(value));
    }

    public static Node generateRandomBST(int level, int maxLevel, int maxValue){
        if (level > maxLevel || new Random().nextDouble() < 0.5){
            return null;
        }
        Node head = new Node(new Random().nextInt(maxValue));
        head.left = generateRandomBST(level + 1, maxLevel, maxValue);
        head.right = generateRandomBST(level + 1, maxLevel, maxValue);
        return head;
    }

    //for test
    public static boolean isSameValueStructure(Node head1, Node head2){
        if (head1 == null && head2 != null){
            return false;
        }
        if (head1 != null && head2 == null){
            return false;
        }
        if (head1 == null && head2 == null){
            return true;
        }
        if (head1.value != head2.value){
            return false;
        }
        return isSameValueStructure(head1.left, head2.left)
                && isSameValueStructure(head1.right, head2.right);
    }

    public static void main(String[] args) {
        int testTimes = 1000;
        int maxLevel = 5;
        int maxValue = 10;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(1, maxLevel, maxValue);
            Queue<String> pre = preSerialize(head);
            Queue<String> pos = posSerialize(head);
            Queue<String> level = levelSerialize(head);
            Node preHead = buildTreeByPreQueue(pre);
            Node poseHead = buildTreeByPoseQueue(pos);
            Node levelHead = buildTreeByLevelQueue(level);

            if (!isSameValueStructure(preHead, poseHead) && !isSameValueStructure(poseHead, levelHead)) {
                System.out.println("oops");
                break;
            }

        }
        System.out.println("finish");
    }


}
