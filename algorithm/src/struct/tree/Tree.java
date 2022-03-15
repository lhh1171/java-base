package struct.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//数组变的树
public class Tree<T> {
    TreeNode<T> root;
    public List<TreeNode> list = new ArrayList<TreeNode>();
    //用一个集合来存放每一个Node
    public  Tree(T[] array) {
        root=new TreeNode<>(array[0]);
        list.add(root);
        for (int i = 1; i < array.length; i++) {
            TreeNode<T> node = new TreeNode<T>(array[i]);    //创建结点，每一个结点的左结点和右结点为null
            list.add(node); // list中存着每一个结点
        }

        // 构建二叉树
        if (list.size() > 0) {
            for (int i = 0; i < array.length / 2 - 1; i++) {       // i表示的是根节点的索引，从0开始
                if (list.get(2 * i + 1) != null) {
                    // 左结点
                    list.get(i).left = list.get(2 * i + 1);
                }
                if (list.get(2 * i + 2) != null) {
                    // 右结点
                    list.get(i).right = list.get(2 * i + 2);
                }
            }

            // 判断最后一个根结点：因为最后一个根结点可能没有右结点，所以单独拿出来处理
            int lastIndex = array.length / 2 - 1;
            // 左结点
            list.get(lastIndex).left = list.get(lastIndex * 2 + 1);
            // 右结点，如果数组的长度为奇数才有右结点
            if (array.length % 2 == 1) {
                list.get(lastIndex).right = list.get(lastIndex * 2 + 2);
            }
        }
    }

    public static void printAll(TreeNode root){
        ArrayList<TreeNode> temp1=new ArrayList<>();
        ArrayList<TreeNode> temp2=new ArrayList<>();
        temp1.add(root);
        Iterator<TreeNode> iterator1;
        Iterator<TreeNode> iterator2;
        int count=0;
        while (true){
            count++;
            System.out.print("第"+count+"层 ");
            iterator1=temp1.iterator();
            while (iterator1.hasNext()){
                TreeNode tt=iterator1.next();
                if (tt!=null){
                    TreeNode temp=tt;
                    //访问
                    System.out.print(tt.val+" ");
                    iterator1.remove();
                    temp2.add(tt);
                }
            }
            System.out.println("");
            iterator2=temp2.iterator();
            while (iterator2.hasNext()){
                TreeNode tt=iterator2.next();
                if (tt.left!=null){
                    temp1.add(tt.left);
                }
                if (tt.right!=null){
                    temp1.add(tt.right);
                }
                iterator2.remove();
            }
            System.out.println("");
            if (temp1.isEmpty()){
                break;
            }
        }
    }

}
//class demo{
//    public static void main(String[] args) {
//        Integer[] in={0,1,2,3,4,5,6,7,8,9,10};
//        Tree<Integer> tree=new Tree<>(in);
//        tree.printAll(tree.root);
//        System.out.println();
//    }
//}