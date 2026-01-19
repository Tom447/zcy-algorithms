package class30;

public class code02_MinHeight {
    public static class Node{
        public int value;
        public Node left;
        public Node right;

        public Node(int v){
            value = v;
        }
    }
    public static int MinHeight1(Node head){
        if (head == null){
            return 0;
        }
        return p1(head);
    }

    public static int p1(Node x){
        if (x.left == null && x.right == null){
            return 1;
        }

        int leftH = Integer.MAX_VALUE;
        if (x.left != null){
            leftH = p1(x.left);
        }

        int rightH = Integer.MAX_VALUE;
        if (x.right != null){
            rightH = p1(x.right);
        }

        return 1 + Math.min(leftH, rightH);
    }


    //morris遍历实现

//    morris遍历需要解决两个问题
//    1)cur变化, level跟随变化
//    2)发现叶节点
    public static int MinHeight2(Node head){
        if (head == null){
            return 0;
        }

        Node mostRight = null;
        Node cur = head;
        int curLevel = 0;
        int minHeight = Integer.MAX_VALUE;
        while (cur != null){
            mostRight = cur.left;
            if (mostRight != null){
                int rightBoardSize = 1;
                while (mostRight.right != null && mostRight.right != cur){
                    rightBoardSize++;
                    mostRight = mostRight.right;
                }

                if (mostRight.right == null){//第一次到cur
                    curLevel++;
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                }else{//第二次到cur
                    if (mostRight.left == null){
                        minHeight = Math.min(minHeight, curLevel);
                    }
                    curLevel -= rightBoardSize;
                    mostRight.right = null;
                }
            }else{
                curLevel++;
            }
            cur = cur.right;
        }
        int finalHeight = 1;
        cur = head;
        while(cur.right != null){
            finalHeight++;
            cur = cur.right;
        }
        if (cur.left == null && cur.right == null){
            minHeight = Math.min(minHeight, finalHeight);
        }

        return minHeight;
    }

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int treeLevel = 7;
        int nodeMaxValue = 5;
        int testTimes = 100000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(treeLevel, nodeMaxValue);
            int ans1 = MinHeight1(head);
            int ans2 = MinHeight2(head);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish!");

    }

}
