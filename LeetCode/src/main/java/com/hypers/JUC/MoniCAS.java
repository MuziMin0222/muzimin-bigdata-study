package com.hypers.JUC;

import java.util.Collections;

/**
 * @author : 李煌民
 * @date : 2020-09-04 10:08
 * 模拟CAS算法
 **/
public class MoniCAS {
    public static void main(String[] args) {
        final CompareAndSwap cas = new CompareAndSwap();
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(cas.compareAndSet(cas.get(), (int) (Math.random() * 101)));
                }
            }).start();
        }

    }
}

class CompareAndSwap {
    private int value;

    //获取内存值
    public synchronized int get() {
        return value;
    }

    //比较
    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;

        if (oldValue == expectedValue) {
            this.value = newValue;
        }
        return oldValue;
    }

    public synchronized boolean compareAndSet(int expectedValue, int newValue) {
        return expectedValue == compareAndSwap(expectedValue, newValue);
    }
}
