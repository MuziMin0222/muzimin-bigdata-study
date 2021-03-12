package com.lhm.Collection;

import java.util.Iterator;

public class MyArrayListDemo {
    public static void main(String[] args) {
        MyArrayList<String> list = new MyArrayList<>();

        list.add("lhm");
        list.add("love");
        list.add("lz");

        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            System.out.print(iterator.next() + "\t");
        }
        System.out.println();

        System.out.println(list.size());

        System.out.println(list.get(2));

        list.set(2,"luzhenxiaobaobei");
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + "\t");
        }
        System.out.println();

        list.remove(0);
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + "\t");
        }
        System.out.println();
    }
}
