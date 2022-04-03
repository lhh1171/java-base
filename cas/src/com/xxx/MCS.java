package com.xxx;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author lhh1171
 * @Date 2022/4/3 下午12:38
 * @Version 1.8
 */
/*
*
* a. 队列初始化时没有结点，tail=null

b. 线程A想要获取锁，将自己置于队尾，由于它是第一个结点，它的locked域为false

c. 线程B和C相继加入队列，a->next=b,b->next=c，B和C没有获取锁，处于等待状态，所以locked域为true，尾指针指向线程C对应的结点

d. 线程A释放锁后，顺着它的next指针找到了线程B，并把B的locked域设置为false，这一动作会触发线程B获取锁。
* */
public class MCS {
    //原子量，只能有一个node可以get他
    private final AtomicReference<Node> tail;
    //每个线程都有一个node属性
    private final ThreadLocal<Node> myNode;

    public MCS() {
        tail = new AtomicReference<>();
        //
        myNode = ThreadLocal.withInitial(() -> new Node());
    }

    public void lock() {
        /*返回此线程局部变量的当前线程副本中的值。
        如果变量没有当前线程的值，则首先将其初始化为调用initialValue方法返回的值。*/
        //拿到当前线程的node
        Node node = myNode.get();
        //抢占原子量,一直抢
        Node pred = tail.getAndSet(node);
        if (pred != null) {
            //上锁
            node.locked = true;

            pred.next = node;
            //当前节点一直阻塞，等待unlock
            while (node.locked) {
            }
        }
    }

    public void unLock() {
        Node node = myNode.get();
        if (node.next == null) {
            //把tail制空
            if (tail.compareAndSet(node, null)) {
                return;
            }
            //等待下一个
            while (node.next == null) {
            }
        }
        node.next.locked = false;
        node.next = null;
    }

    class Node {
        volatile boolean locked = false;
        Node next = null;
    }

    public static void main(String[] args) {

        MCS lock = new MCS();

        Runnable CTask = new Runnable() {
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
                System.out.println(Thread.currentThread());
                lock.unLock();
            }
        };

        new Thread(CTask).start();
        new Thread(CTask).start();
        new Thread(CTask).start();
        new Thread(CTask).start();
    }
}
