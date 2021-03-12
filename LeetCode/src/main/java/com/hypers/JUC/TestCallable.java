package com.hypers.JUC;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author : 李煌民
 * @date : 2020-09-04 11:54
 * 测试Callable创建线程的方式
 * 1、创建线程的方式3：实现callable接口，该方式有返回值
 * 2、执行callable方式，需要FuthurTask实现类的支持，用于接收运算结果，FutureTask是Future接口的实现类
 **/
public class TestCallable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyCallable callable = new MyCallable();

        FutureTask<Integer> res = new FutureTask<>(callable);
        new Thread(res).start();

        //FutureTask可用于闭锁
        Integer sum = res.get();
        System.out.println("Main线程");
        System.out.println("其他线程的结果：" + sum);
    }
}

class MyCallable implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i <= 100; i++) {
            sum += i;
        }
        return sum;
    }
}
