package class11;


import java.util.ArrayList;
import java.util.List;

public class code03_encodeNaryTreeToBinaryTree {

    public static class NaryNode{
        public int value;
        public List<NaryNode> children;

        public NaryNode(){

        }

        public NaryNode(int data){
            this.value = data;
            children = new ArrayList<>();
        }

        public NaryNode(int value, List<NaryNode> children) {
            this.value = value;
            this.children = children;
        }
    }

    public static class BinaryNode{
        public int value;
        public BinaryNode left;
        public BinaryNode right;

        public BinaryNode(){

        }

        public BinaryNode(int data){
            this.value = data;
        }
    }


    public static BinaryNode NaryTreeToBinaryNode(NaryNode root){
        if (root == null){
            return null;
        }
        BinaryNode head = new BinaryNode(root.value);
        head.left = en(root.children);
        return head;
    }


    public static BinaryNode en(List<NaryNode> children){

        BinaryNode head = null;
        BinaryNode cur = null;
        for (NaryNode child : children){
            BinaryNode tNode = new BinaryNode(child.value);
            if (head == null){
                head = tNode;
            }else{
                cur.right = tNode;
            }
            cur = tNode;
            tNode.left = en(child.children);
        }
        return head;
    }

    public static NaryNode BinaryNodeToNaryNode(BinaryNode root){
        if (root == null){
            return null;
        }
        return new NaryNode(root.value, de(root));
    }

    public static List<NaryNode> de(BinaryNode root){
        List<NaryNode> children = new ArrayList<>();
        while(root != null){
            NaryNode cur = new NaryNode(root.value, de(root.left));
            children.add(cur);
            root = root.right;
        }
        return children;
    }
}
