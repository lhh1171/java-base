package struct.RBtree;


public class RBTree<K extends Comparable<K>, V> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;

    private RBNode root;

    public RBNode getRoot() {
        return root;
    }

    public void setRoot(RBNode root) {
        this.root = root;
    }

    /**
     * 围绕p左旋
     *       pf                    pf
     *      /                     /
     *     p                     pr(r)
     *    / \          ==>      / \
     *  pl  pr(r)              p   rr
     *     / \                / \
     *    rl  rr             pl  rl
     *
     * @param p
     */
    private void leftRotate(RBNode p) {
        if (p != null) {
            RBNode r = p.right;
            p.right = r.left;
            if (r.left != null) {
                r.left.parent = p;
            }
            r.parent = p.parent;
            if (p.parent == null) {
                root = r;
            } else if (p.parent.left == p) {
                p.parent.left = r;
            } else {
                p.parent.right = r;
            }
            r.left = p;
            p.parent = r;
        }
    }

    /**
     * 右旋
     *    pf                pf
     *     \                 \
     *      p             (l)pl
     *     / \      =>      /  \
     *(l)pl  pr            ll   p
     *   / \                   / \
     *  ll lr                 lr  pr
     *
     * @param p
     */
    private void rightRotate(RBNode p) {
        if (p != null) {
            RBNode l = p.left;
            p.left = l.right;
            if (l.right != null) {
                l.right.parent = p;
            }
            l.parent = p.parent;
            if (p.parent == null) {
                root = l;
            } else if (p.parent.right == p) {
                p.parent.right = l;
            } else {
                p.parent.left = l;
            }
            l.right = p;
            p.parent = l;
        }
    }

    /**
     * 找到指定节点的前驱节点，即找小于node节点的最大值
     * @param node
     */
    private RBNode predecessor(RBNode node){
        if(node==null){
            return null;
        }
        else if(node.left!=null){
            RBNode p=node.left;
            while(p.right!=null){
                p=p.right;
            }
            return p;
        }else{
            RBNode p = node.parent;
            RBNode ch=node;
            while(p!=null&&ch==p.left){
                ch=p;
                p=p.parent;
            }
            return p;
        }
    }

    /**
     * 找后继节点，即大于节点的最小值
     * @param node
     * @return
     */
    private RBNode successor(RBNode node){
        if(node==null){
            return null;
        }
        else if(node.right!=null){
            RBNode p=node.right;
            while(p.left!=null){
                p=p.left;
            }
            return p;
        }else{
            RBNode p = node.parent;
            RBNode ch=node;
            while(p!=null&&ch==p.right){
                ch=p;
                p=p.parent;
            }
            return p;
        }
    }

    private boolean colorOf(RBNode node) {
        return node == null ? BLACK : node.color;
    }

    private RBNode parentOf(RBNode node) {
        return node != null ? node.parent : null;
    }

    private RBNode leftOf(RBNode node) {
        return node != null ? node.left : null;
    }

    private RBNode rightOf(RBNode node) {
        return node != null ? node.right : null;
    }

    private void setColor(RBNode node, boolean color){
        if(node!=null){
            node.color=color;
        }
    }

    public void put(K key, V value) {
        RBNode t = this.root;
        //如果是根节点
        if (t == null) {
            root = new RBNode<>(key, value == null ? key : value, null);
            return;
        }
        int cmp;
        //寻找插入位置
        //定义一个双亲指针
        RBNode parent;
        if (key == null) {
            throw new NullPointerException();
        }
        //沿着跟节点寻找插入位置
        do {
            parent = t;
            cmp = key.compareTo((K) t.key);
            if (cmp < 0) {
                t = t.left;
            } else if (cmp > 0) {
                t = t.right;
            } else {
                t.setValue(value == null ? key : value);
                return;
            }
        } while (t != null);

        RBNode<K, Object> e = new RBNode<>(key, value == null ? key : value, parent);
        //如果比较最终落在左子树，则直接将父节点左指针指向e
        if (cmp < 0) {
            parent.left = e;
        }
        //如果比较最终落在右子树，则直接将父节点右指针指向e
        else {
            parent.right = e;
        }
        //调整
        fixAfterPut(e);
    }

    /**
     * 1、2-3-4树：新增元素+2节点合并（节点中只有1个元素）=3节点（节点中有2个元素）
     *    红黑树：新增一个红色节点+黑色父亲节点=上黑下红（2节点）--------------------不要调整
     *
     * 2、2-3-4树：新增元素+3节点合并（节点中有2个元素）=4节点（节点中有3个元素）
     *    这里有4种小情况（左3，右3，还有2个左中右不需要调整）------左3，右3需要调整，其余2个不需要调整
     *    红黑树：新增红色节点+上黑下红=排序后中间节点是黑色，两边节点都是红色（3节点）
     *
     * 3、2-3-4树：新增一个元素+4节点合并=原来的4节点分裂，中间元素升级为父节点，新增元素与剩下的其中一个合并
     *    红黑树：新增红色节点+爷爷节点黑，父节点和叔叔节点都是红色=爷爷节点变红，父亲和叔叔变黑，如果爷爷是根节点，则再变黑
     *
     *
     * @param x
     */
    private void fixAfterPut(RBNode x) {
        x.color = RED;
        //本质上就是父节点是黑色就不需要调整，对应情况就是2,3
        while (x != null && x != root && x.parent.color == RED) {
            //1、x的父节点是爷爷的左孩子（左3）
            if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
                //叔叔节点
                RBNode y = rightOf(parentOf(parentOf(x)));
                //第3种情况
                if (colorOf(y) == RED) {
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    //爷爷节点递归
                    x=parentOf(parentOf(x));
                }
                //第2种情况
                else {
                    if (x == rightOf(parentOf(x))) {
                        x = parentOf(x);
                        leftRotate(x);
                    }
                    //父亲变黑
                    setColor(parentOf(x), BLACK);
                    //爷爷变红
                    setColor(parentOf(parentOf(x)), RED);
                    //根据爷爷节点右旋转
                    rightRotate(parentOf(parentOf(x)));
                }
            }
            //2、跟第一种情况相反操作
            else {
                //右3
                //叔叔节点
                RBNode y = leftOf(parentOf(parentOf(x)));
                //第3种情况
                if (colorOf(y) == RED) {
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    //爷爷节点递归
                    x=parentOf(parentOf(x));
                }
                //第2种情况
                else {
                    if (x == leftOf(parentOf(x))) {
                        x = parentOf(x);
                        rightRotate(x);
                    }
                    //父亲变黑
                    setColor(parentOf(x), BLACK);
                    //爷爷变红
                    setColor(parentOf(parentOf(x)), RED);
                    //根据爷爷节点右旋转
                    leftRotate(parentOf(parentOf(x)));
                }
            }
        }
        root.color=BLACK;
    }

    public V remove(K key){
      RBNode node=getNode(key);
      if(node==null){
          return null;
      }
      V oldValue = (V) node.value;
      deleteNode(node);
      return oldValue;
    }

    /**
     * 删除操作：
     * 1、删除叶子节点，直接删除
     * 2、删除的节点有一个子节点，那么用子节点来替代
     * 3、如果删除的节点有2个子节点，此时需要找到前驱节点或者后继节点来替代
     *
     * @param node
     */
    private void deleteNode(RBNode node){
        //3、node节点有2个孩子
        if(node.left!=null&&node.right!=null){
            /**
             *  这里要注意，如果使用下面这个网站演示的话，此网站用的是前驱节点替代
             *  下面代码里我使用的是后继节点替代，删除节点后显示可能会和该网站不一致，
             *  但是这两种方法红黑树删除都是合法的
             *  （可以自行把前驱节点替代方案屏蔽放开，后继节点替代方案注释掉测试下）
             *
             *  https://www.cs.usfca.edu/~galles/visualization/RedBlack.html
             */

            //后继节点替代
            RBNode rep = successor(node);
            //前驱节点替代
//            RBNode rep= predecessor(node);
            node.key = rep.key;
            node.value=rep.value;
            node=rep;
        }

        RBNode replacement=node.left!=null?node.left:node.right;
        //2、替代节点不为空
        if(replacement!=null){
            //替代者的父指针指向的原来node的父亲
            replacement.parent=node.parent;
            //node是根节点
            if(node.parent==null){
                root=replacement;
            }
            //node是左孩子，所以替代者依然是左孩子
            else if(node==node.parent.left){
                node.parent.left=replacement;
            }
            //node是右孩子，所以替代者依然是右孩子
            else{
                node.parent.right=replacement;
            }
            //将node的左右孩子指针和父指针都指向null（此时node处于游离状态，等待垃圾回收）
            node.left=node.right=node.parent=null;

            //替换完之后需要调整平衡
            if(node.color==BLACK){
                //需要调整,这种情况一定是红色（替代节点一定是红色，此时只要变色）
                fixAfterRemove(replacement);
            }
        }
        //删除节点就是根节点
        else if(node.parent==null){
            root=null;
        }
        //1、node节点是叶子节点，replacement为null
        else{
            //先调整
            if(node.color==BLACK) {
                fixAfterRemove(node);
            }
            //再删除
            if(node.parent!=null){
                if(node==node.parent.left){
                    node.parent.left=null;
                }
                else if(node==node.parent.right){
                    node.parent.right=null;
                }
                node.parent=null;
            }
        }
    }

    /**
     * 删除后调整
     * @param x
     */
    private void fixAfterRemove(RBNode x){
        while(x!=root&&colorOf(x)==BLACK){
            //x是左孩子的情况
            if(x==leftOf(parentOf(x))) {
                //兄弟节点
                RBNode rnode = rightOf(parentOf(x));

                //判断此时兄弟节点是否是真正的兄弟节点
                if(colorOf(rnode)==RED){
                    setColor(rnode,BLACK);
                    setColor(parentOf(x),RED);
                    leftRotate(parentOf(x));
                    //找到真正的兄弟节点
                    rnode=rightOf(parentOf(x));
                }
                //情况三，找兄弟借，兄弟没得借
                 if(colorOf(leftOf(rnode))==BLACK&&colorOf(rightOf(rnode))==BLACK){
                     //情况复杂，暂时不写
                     setColor(rnode,RED);
                     x=parentOf(x);
                 }
                //情况二，找兄弟借，兄弟有的借
                else{
                    //分2种小情况：兄弟节点本来是3节点或者是4节点的情况
                     if(colorOf(rightOf(rnode))==BLACK){
                        setColor(leftOf(rnode),BLACK);
                        setColor(rnode,RED);
                        rightRotate(rnode);
                        rnode=rightOf(parentOf(x));
                     }
                     setColor(rnode,colorOf(parentOf(x)));
                     setColor(parentOf(x),BLACK);
                     setColor(rightOf(rnode),BLACK);
                     leftRotate(parentOf(x));
                     x=root;
                 }
            }
            //x是右孩子的情况
            else{
                //兄弟节点
                RBNode rnode = leftOf(parentOf(x));
                //判断此时兄弟节点是否是真正的兄弟节点
                if(colorOf(rnode)==RED){
                    setColor(rnode,BLACK);
                    setColor(parentOf(x),RED);
                    rightRotate(parentOf(x));
                    //找到真正的兄弟节点
                    rnode=leftOf(parentOf(x));
                }
                //情况三，找兄弟借，兄弟没得借
                if(colorOf(rightOf(rnode))==BLACK&&colorOf(leftOf(rnode))==BLACK){
                    //情况复杂，暂时不写
                    setColor(rnode,RED);
                    x=parentOf(x);
                }
                //情况二，找兄弟借，兄弟有的借
                else{
                    //分2种小情况：兄弟节点本来是3节点或者是4节点的情况
                    if(colorOf(leftOf(rnode))==BLACK){
                        setColor(rightOf(rnode),BLACK);
                        setColor(rnode,RED);
                        leftRotate(rnode);
                        rnode=leftOf(parentOf(x));
                    }
                    setColor(rnode,colorOf(parentOf(x)));
                    setColor(parentOf(x),BLACK);
                    setColor(leftOf(rnode),BLACK);
                    rightRotate(parentOf(x));
                    x=root;
                }
            }
        }
        //情况一、替代节点是红色，则直接染红，补偿删除的黑色节点，这样红黑树依然保持平衡
        setColor(x,BLACK);
    }

    private RBNode getNode(K key){
        RBNode node=this.root;
        while(node!=null){
            int cmp = key.compareTo((K) node.key);
            if(cmp<0){
                node=node.left;
            }
            else if(cmp>0){
                node=node.right;
            }
            else
                return node;
        }
        return null;
    }

    static class RBNode<K extends Comparable<K>, V> {
        private RBNode parent;
        private RBNode left;
        private RBNode right;
        private boolean color;
        private K key;
        private V value;

        public RBNode() {
        }

        public RBNode(K key, V value, RBNode parent) {
            this.parent = parent;
            this.color = BLACK;
            this.key = key;
            this.value = value;
        }

        public RBNode(RBNode parent, RBNode left, RBNode right, boolean color, K key, V value) {
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.color = color;
            this.key = key;
            this.value = value;
        }

        public RBNode getParent() {
            return parent;
        }

        public void setParent(RBNode parent) {
            this.parent = parent;
        }

        public RBNode getLeft() {
            return left;
        }

        public void setLeft(RBNode left) {
            this.left = left;
        }

        public RBNode getRight() {
            return right;
        }

        public void setRight(RBNode right) {
            this.right = right;
        }

        public boolean isColor() {
            return color;
        }

        public void setColor(boolean color) {
            this.color = color;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }
}
