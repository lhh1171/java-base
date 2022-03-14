package struct.list;

//public class QuickSort {
//
//    public void swap(Node n1,Node n2){
//        Node tp1=n1.pre;
//        Node tn1=n1.next;
//        Node tp2=n2.next;
//        Node tn2=n2.next;
//        n1.pre=tp2;
//        n1.next=tn2;
//        n2.pre=tp1;
//        n2.next=tn1;
//    }
//}
class demo{
    public static void main(String[] args) {
        SList<Integer> sList=new SList<Integer>();
        Integer[] in={9,3,7,5,4,2,1};
        sList.createList(in);
        sList.sort(new Node<Integer>(5));
        System.out.println();
    }
}