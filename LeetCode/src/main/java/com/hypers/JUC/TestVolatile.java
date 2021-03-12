package com.hypers.JUC;

/**
 * @author : 李煌民
 * @date : 2020-09-02 17:30
 * Volatile关键字的学习
 * volatile关键字;当多个线程操作共享数据时，可以保证内存中的数据可见，相较于synchronized是一种轻量级的同步策略
 * 注意：
 * 1、volatile不具备互斥性(互斥性：一个线程访问该变量，另外一个线程依旧可以访问该变量)
 * 2、volatile不能保证变量的原子性
 **/
public class TestVolatile {
    public static void main(String[] args) {
        MyThread td = new MyThread();
        new Thread(td).start();

        while (true){
            if (td.isFlag()){
                System.out.println("Main线程中flag的结果：" + td.isFlag());
                break;
            }
        }
    }
}

class MyThread implements Runnable{

    //volatile关键字将flag放入主存中，即多个线程共享的那块内存
    private volatile boolean flag = false;

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.setFlag(true);

        System.out.println("线程中的flag的结果：" + isFlag());
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}