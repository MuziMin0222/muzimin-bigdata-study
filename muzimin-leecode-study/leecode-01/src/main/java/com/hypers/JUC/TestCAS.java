package com.hypers.JUC;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : 李煌民
 * @date : 2020-09-03 10:54
 * CAS案例
 * 1、i++的原子性问题：i++的操作实际上分为三个步骤“读-改-写”
 *                  int i = 0;
 *                  i = i++; //此时i的值是10
 *                  在底层分为三步
 *                    int temp = i;
 *                    i = i + 1;
 *                    i = temp;
 * 2、原子变量：JDK1.5之后java.util.concurrent.atomic包下提供了常用的原子变量
 *                 1、volatile只保证了内存可见性
 *                 2、CAS算法保证数据的原子性
 *                     CAS算法是硬件对于并发操作共享数据的支持
 *                     CAS包含了三个操作数；
 *                        内存值V
 *                        预估值A
 *                        更新值B
 *                        当且仅当  V==A时，V=B，否则，将不做任何操作
 **/
public class TestCAS {
    public static void main(String[] args) {
        AtomicDemo ad = new AtomicDemo();
        for (int i = 0; i < 10; i++) {
            new Thread(ad).start();
        }
    }
}

class AtomicDemo implements Runnable{

    private AtomicInteger sn = new AtomicInteger();
//    private int sn = 0;

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(getSn());
    }

    public int getSn() {
        return sn.getAndIncrement();
//        return sn++;
    }
}
