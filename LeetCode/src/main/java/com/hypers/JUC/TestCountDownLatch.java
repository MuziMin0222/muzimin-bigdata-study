package com.hypers.JUC;

import java.util.concurrent.CountDownLatch;

/**
 * @author : 李煌民
 * @date : 2020-09-04 10:50
 * 测试闭锁机制
 **/
public class TestCountDownLatch {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        //定义要完成的线程为5个
        final CountDownLatch latch = new CountDownLatch(5);
        LatchDemo ld = new LatchDemo(latch);
        for (int i = 0; i < 5; i++) {
            new Thread(ld).start();
        }
        //等待其他线程执行完，当latch等于0的时候，唤醒线程
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(latch.getCount());
        long endTime = System.currentTimeMillis();
        System.out.println("程序结束时间：" + (endTime - startTime));
    }
}

class LatchDemo implements Runnable {
    private CountDownLatch latch;

    public LatchDemo(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                System.out.println(Thread.currentThread().getName() + "正在执行");
            } finally {
                //线程失败成功与否，这里必须要执行，进行线程数-1处理
                System.out.println("执行前：" + latch.getCount());
                latch.countDown();
                System.out.println("执行后：" + latch.getCount());
            }
        }
    }
}
