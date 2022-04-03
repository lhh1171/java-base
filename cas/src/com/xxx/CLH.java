package com.xxx;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author lhh1171
 * @Date 2022/4/3 下午12:38
 * @Version 1.8
 */
/*
*  1、获取当前线程的锁节点，如果为空，则进行初始化；

 2、同步方法获取链表的尾节点，并将当前节点置为尾节点，此时原来的尾节点为当前节点的前置节点。

 3、如果尾节点为空，表示当前节点是第一个节点，直接加锁成功。

 4、如果尾节点不为空，则基于前置节点的锁值（locked==true）进行自旋，直到前置节点的锁值变为false。
* */
public class CLH {
    //原子量，只能有一个node可以get他
    private final AtomicReference<Node> tail;
    //每个线程都有一个myNode属性
    private final ThreadLocal<Node> myNode;
    //每个线程都有一个myPred属性
    private final ThreadLocal<Node> myPred;

    public CLH() {
        tail = new AtomicReference<>(new Node());
        myNode = ThreadLocal.withInitial(() -> new Node());
        myPred = ThreadLocal.withInitial(() -> null);
    }


    public void lock(){
        //拿到当前线程的node
        Node node = myNode.get();
        //上锁
        node.locked = true;
        //抢占原子量,一直抢
        Node pred = tail.getAndSet(node);
        /*将此线程局部变量的当前线程副本设置为指定值。
        大多数子类不需要重写此方法，
        仅依靠initialValue方法来设置线程局部变量的值。*/
        //给之前线程tail变成该线程Node的preNode
        myPred.set(pred);
        //当前节点一直阻塞，等待unlock
        while (pred.locked){

        }
    }

    public void unLock(){
        Node node = myNode.get();
        node.locked=false;
        //给之前线程tail变成该线程Node的preNode
        myNode.set(myPred.get());
    }


    static class Node {
        volatile boolean locked = false;
    }
    public static void main(String[] args) {

        CLH lock = new CLH();

        Runnable task = new Runnable() {
            private int a;
            @Override
            public void run() {
                lock.lock();
                for (int i = 0; i < 10; i++) {
                    a++;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(a);
                lock.unLock();
            }
        };

        new Thread(task).start();
        new Thread(task).start();
        new Thread(task).start();
        new Thread(task).start();
    }

}
