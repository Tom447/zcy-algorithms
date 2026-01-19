package class12;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class code06_maxSubBSTSize {

    public static class Node{
        public int value;
        public Node left;
        public Node right;

        public Node(int data){
            this.value = data;
        }
    }

    public static int maxSubBSTSize1(Node head){
        if (head == null){
            return 0;
        }

        int h = getBSTSize(head);
        if (h != 0){
            return h;
        }
        return Math.max(maxSubBSTSize1(head.left), maxSubBSTSize1(head.right));
    }


    public static List<Node> getPreList(Node head){
        List<Node> list = new ArrayList<>();
        ins(head, list);
        return list;
    }

    public static void ins(Node head, List<Node> list){
        if (head == null){
            return;
        }
        ins(head.left, list);
        list.add(head);
        ins(head.right, list);

    }
    public static int getBSTSize(Node head) {
        if (head == null){
            return 0;
        }
        List<Node> preList = getPreList(head);
        for (int i = 1; i < preList.size(); i++){
            if (preList.get(i-1).value >= preList.get(i).value){
                return 0;
            }
        }
        return preList.size();
    }


    public static class Info{
        public int maxSubBSTSize;
        public int allSize;
        public int max;
        public int min;

        public Info(int maxSubBSTSize, int allSize, int max, int min) {
            this.maxSubBSTSize = maxSubBSTSize;
            this.allSize = allSize;
            this.max = max;
            this.min = min;
        }



    }


    public static int maxSubBSTSize2(Node head){
        if (head == null){
            return 0;
        }
        return process(head).maxSubBSTSize;
    }

    public static Info process(Node x){
        if (x == null){
            return null;
        }

        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);


        int allSize = 1;

        int max = x.value;
        int min = x.value;


        if (leftInfo != null){
            max = Math.max(max, leftInfo.max);
            min = Math.min(min, leftInfo.min);
            allSize += leftInfo.allSize;
        }
        if (rightInfo != null){
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);
            allSize += rightInfo.allSize;
        }

        int p1 = leftInfo == null ? 0 : leftInfo.maxSubBSTSize;
        int p2 = rightInfo == null ? 0 : rightInfo.maxSubBSTSize;

        int p3 = -1;
        boolean leftBST = leftInfo == null ? true : leftInfo.maxSubBSTSize == leftInfo.allSize;
        boolean rightBST = rightInfo == null ? true : rightInfo.maxSubBSTSize == rightInfo.allSize;
        if (leftBST && rightBST){
            boolean leftFlag = leftInfo == null ? true : leftInfo.max < x.value;
            boolean rightFlag = rightInfo == null ? true : rightInfo.min > x.value;
            if (leftFlag && rightFlag){
                int leftSize = leftInfo == null ? 0 : leftInfo.allSize;
                int rightSize = rightInfo == null ? 0 : rightInfo.allSize;
                p3 = leftSize + rightSize + 1;
            }
        }
        return new Info(Math.max(p1, Math.max(p2, p3)),allSize, max, min);
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


    // 打印二叉树
    public static void printTree(Node root) {
        if (root == null) {
            System.out.println("Empty tree");
            return;
        }

        List<List<String>> lines = new ArrayList<>();
        int height = getTreeHeight(root);
        int[] colWidths = new int[height];
        fillWidths(root, colWidths, 0);

        buildTreeLines(root, lines, 0, 0, colWidths);

        for (List<String> line : lines) {
            StringBuilder sb = new StringBuilder();
            for (String s : line) {
                sb.append(s);
            }
            System.out.println(sb.toString().replaceAll("\\s+$", ""));
        }
    }

    // 获取树的高度
    private static int getTreeHeight(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(getTreeHeight(node.left), getTreeHeight(node.right));
    }

    // 填充每一层的最大宽度
    private static int fillWidths(Node node, int[] widths, int depth) {
        if (node == null) return 0;
        String val = Integer.toString(node.value);
        int w = val.length();

        int leftW = fillWidths(node.left, widths, depth + 1);
        int rightW = fillWidths(node.right, widths, depth + 1);
        widths[depth] = Math.max(widths[depth], leftW + w + rightW);

        return widths[depth];
    }

    // 构建每一行的内容
    private static void buildTreeLines(Node node, List<List<String>> lines, int x, int depth, int[] colWidths) {
        if (node == null) return;

        String val = Integer.toString(node.value);
        int width = colWidths[depth];

        // 如果当前深度还没有对应的行，则新建一行
        while (lines.size() <= depth) {
            lines.add(new ArrayList<>());
        }

        List<String> line = lines.get(depth);
        // 补全前面的空格
        while (line.size() < x) {
            line.add(" ");
        }

        // 添加当前节点值
        line.add(val);

        // 左右子树连接线
        int leftPos = x - val.length() / 2;
        int rightPos = x + val.length() / 2 + 1;

        if (node.left != null) {
            drawLine(lines, depth + 1, leftPos, x, '/');
        }
        if (node.right != null) {
            drawLine(lines, depth + 1, x + val.length(), rightPos, '\\');
        }

        // 递归构建左右子树
        int leftOffset = 0;
        if (node.left != null) {
            leftOffset = colWidths[depth + 1] / 2;
        }
        buildTreeLines(node.left, lines, x - leftOffset - val.length(), depth + 1, colWidths);

        int rightOffset = 0;
        if (node.right != null) {
            rightOffset = colWidths[depth + 1] / 2;
        }
        buildTreeLines(node.right, lines, x + rightOffset + val.length(), depth + 1, colWidths);
    }

    // 绘制连接线
    private static void drawLine(List<List<String>> lines, int depth, int from, int to, char c) {
        if (depth >= lines.size()) {
            lines.add(new ArrayList<>());
        }

        List<String> line = lines.get(depth);
        while (from > to) {
            swap(from, to);
        }

        for (int i = from; i <= to; i++) {
            while (i >= line.size()) {
                line.add(" ");
            }
            if (i == from || i == to) {
                line.set(i, Character.toString(c));
            } else {
                line.set(i, "-");
            }
        }
    }

    private static void swap(int a, int b) {
        int temp = a;
        a = b;
        b = temp;
    }

    public static void main(String[] args) {
        int testTimes = 5000;
        int maxLevel = 5;
        int maxValue = 10;
        for (int i = 0; i < testTimes; i++){
            Node head = generateRandomBST(1, maxLevel, maxValue);
            if (maxSubBSTSize1(head) != maxSubBSTSize2(head)){
                System.out.println(maxSubBSTSize1(head) + " " + maxSubBSTSize2(head));
                printTree(head);
                System.out.println("oops");
                break;
            }
        }

    }

}
