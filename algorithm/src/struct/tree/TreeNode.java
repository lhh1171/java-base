package struct.tree;

public class TreeNode<Val> {
    TreeNode<Val> left;
    Val val;
    TreeNode<Val> right;
    public TreeNode(Val val){
        this.val=val;
        this.left=null;
        this.right=null;
    }
}
