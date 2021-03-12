package com.lhm.Collection;

/*
1. MyArrayList 将保持基础数组,数组的容量，以及存储在MyArrayList中的当前项数。
2. MyArrayList将提供- - 种机制以改变基础数组的容量。通过获得- - 个新数组，将老数组拷
贝到新数组中来改变数组的容量，允许虚拟机回收老数组。
3. MyArrayList将提供get和set的实现。
4. MyArrayList将提供基本的例程，如size、isEmpty和clear,它们是典型的单行程序;还提供remove,
以及两种不同版本的add。如果数组的大小和容量相同，那么这两个add例程将增加容量。
5. MyArrayList将提供- -个实现Iterator接口的类。这个类将存储迭代序列中的下一项的
下标，并提供next、hasNext和remove等方法的实现。MyArrayList 的迭代器方法直接返回实现Iterator接口的该类的新构造的实例。
 */
public class MyArrayList<T>{
    //默认数组大小为10
    private static final int DEFAULT_CAPACTITY = 10;
    //此数组中元素的个数
    private int theSize;
    private T[] theItem;

    public MyArrayList(){
        theSize = 0;
        ensureCapacity(DEFAULT_CAPACTITY);
    }

    //拿到集合元素的多少
    public int size(){
        return theSize;
    }
    //判断集合是否为空
    public boolean isEmpty(){
        return theSize == 0;
    }

    //获取集合中的元素
    public T get(int index){
        if (index < 0 || index >= size()){
            throw new ArrayIndexOutOfBoundsException();
        }
        return theItem[index];
    }

    //对集合进行修改元素，将该索引元素进行替换并且返回之前的元素
    public T set(int index,T newVal){
        if (index < 0 || index >= size()){
            throw new ArrayIndexOutOfBoundsException();
        }
        T old = theItem[index];
        theItem[index] = newVal;
        return old;
    }

    //集合的add方法，根据指定的索引添加指点的元素
    public void add(int index,T t){
        //如果数组元素与数组容量相等时
        if (theItem.length == size()) {
            //如果该数组已满，那么对数组进行扩容
            ensureCapacity(size()*2+1);
        }
        //将该索引的元素填加到指点的位置，其他元素在本位置上往后移一位
        for (int i = theSize; i > index; i--) {
            theItem[i] = theItem[i-1];
        }
        theItem[index] = t;
        //此时该集合元素数量加一
        theSize++;
    }
    //如果add方法没有指定添加元素的索引位置，那么将该元素添加到集合的末尾,添加元素成功便放回true
    public boolean add(T t){
        add(size(),t);
        return true;
    }

    //移除指定索引位置的元素,并返回该索引
    public T remove(int index){
        //获取该索引上的元素的值
        T removeItem = theItem[index];
        //此移除元素只是将数组的容量直接缩小一位，该位上的元素被后一位元素所替代
        for (int i = index; i < size()-1; i++) {
            theItem[i] = theItem[i+1];
        }
        theSize--;
        return removeItem;
    }

    //这是对集合进行扩容的方法
    public void ensureCapacity(int newCapacity){
        if (newCapacity < theSize){
            return;
        }
        T[] old = theItem;
        theItem = (T[])new Object[newCapacity];
        for (int i = 0; i < theSize; i++) {
            theItem[i] = old[i];
        }
    }

    public java.util.Iterator<T> iterator(){
        return new ArrayListIterator();
    }

    private class ArrayListIterator implements java.util.Iterator<T>{
        private int current = 0;

        @Override
        public boolean hasNext() {
            return current < size();
        }

        @Override
        public void remove() {
            MyArrayList.this.remove(--current);
        }

        @Override
        public T next() {
            if (!hasNext()){
                throw new java.util.NoSuchElementException();
            }
            return theItem[current++];
        }
    }
}
