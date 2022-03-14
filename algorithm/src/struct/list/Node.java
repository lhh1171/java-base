package struct.list;

import java.util.Objects;

public class Node<Val extends Comparable<Val>> {
    Val val;
    Node<Val> next;
    Node<Val> pre;
    public Node(Val val){
        this.val=val;
    }

}
