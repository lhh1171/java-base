package struct.list;

public class SList<T extends Comparable<T>> {
    Node<T> head;
    Node<T> tail;
    public void createList(T[] t){
        Node<T> temp1 = null;
        for (T tt:t){
            if (head==null){
                this.head=new Node<T>(tt);
                temp1=this.head;
            } else {
                assert temp1 != null;
                temp1.next=new Node<T>(tt);
                temp1.next.pre=temp1;
                temp1=temp1.next;
                tail=temp1;
            }
        }
    }


    public void sort(Node<T> node){
        Node<T> temp=null;
        Node<T> h=this.head;
        Node<T> t=this.tail;
        int flag=0;
        while (true){

            if (flag==0){
                if (h.val.compareTo(node.val) <= 0){
                    if (t==h){
                        break;
                    }
                    h=h.next;
                }else{
                    if (t==h){
                        break;
                    }
                    if (t.val.compareTo(node.val)<0){
                        swap(t,h);
                        temp=t;
                        t=h;
                        h=temp;
                        h=h.next;
                        t=t.pre;

                    }
                    flag=1;
                }
            } else {
                if (t.val.compareTo(node.val)>=0){
                    if (t==h){
                        break;
                    }
                    t=t.pre;
                }else{
                    if (t==h){
                        break;
                    }
                    if (h.val.compareTo(node.val)>0){
                        swap(t,h);
                        temp=t;
                        t=h;
                        h=temp;
                        t=t.pre;
                        h=h.next;
                    }
                    flag=0;
                }

            }
        }
        temp=this.head;
        this.head=tail;
        this.tail=temp;
    }
    public void swap(Node n1,Node n2) throws NullPointerException{
        Node tp1=n1.pre;
        Node tn1=n1.next;
        Node tp2=n2.pre;
        Node tn2=n2.next;

        if (tn2!=null){
            tn2.pre=n1;
        }

        if (tp2!=null){
            tp2.next=n1;
        }

        n1.pre=tp2;
        n1.next=tn2;
        if (tn1!=null){
            tn1.pre=n2;
        }
        if (tp1!=null){
            tp1.next=n2;
        }
        n2.pre=tp1;
        n2.next=tn1;
    }
}
