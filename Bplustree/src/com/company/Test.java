package com.company;

public class Test {
    public static void main(String[] args){

        BPlusTree<Product, Integer> b = new BPlusTree<>(4);

        long time1 = System.currentTimeMillis();

        for (int i = 0; i < 50; i++) {
            Product p = new Product(i, "test", 1.0 * i);
            b.insert(p, p.getId());
        }

        long time2 = System.currentTimeMillis();

        Product p1 = b.find(25);

        long time3 = System.currentTimeMillis();


        System.out.println("插入耗时: " + (time2 - time1));
        System.out.println("查询耗时: " + (time3 - time2));
        System.out.println(p1.getName()+" "+ p1.getId()+" "+p1.getPrice());
    }
}


