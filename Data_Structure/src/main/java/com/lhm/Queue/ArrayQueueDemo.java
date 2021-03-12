package com.lhm.Queue;
/*
 * 使用数组模拟队列操作
 */
public class ArrayQueueDemo {
	public static void main(String[] args) {
		//测试用数组模拟的队列
		ArrayQueue aq = new ArrayQueue(5);
		aq.addQueue(22);
		System.out.println(aq.getQueue());

		aq.getHead();
	}
}

//使用数组模拟一个队列
class ArrayQueue{
	//队列尾
	private int rear;
	//队列头
	private int front;
	//用于存放数据，模拟队列
	private int[] arr;
	//表示数组的最大容量
	private int Max_Size;

	//创建队列构造器
	public ArrayQueue(int Max_Size){
		this.Max_Size = Max_Size;
		rear = -1;//指向队列尾，即队列中的最后一个数据
		front = -1;//指向队列头部，分析出front是指向队列头的前一个位置
		arr = new int[Max_Size];
	}

	//判断队列是否满
	public boolean isFull() {
		return rear == Max_Size - 1;
	}

	//判断队列是否为空
	public boolean isEmpty() {
		return rear == front;
	}

	//模拟队列添加元素
	public void addQueue(int n) {
		//先判断队列是否满
		if(isFull()) {
			throw new RuntimeException("该队列已满，不能添加元素......");
		}

		rear++;//让rear后移
		arr[rear] = n;
	}

	//模拟队列取出元素
	public int getQueue() {
		//先判断队列是否为空
		if(isEmpty()) {
			throw new RuntimeException("该队列为空，不可以取元素========");
		}

		front++;
		return arr[front];
	}

	//模拟队列显示全部元素
	public void showQueue() {
		if(isEmpty()) {
			throw new RuntimeException("该队列是空的，，。");
		}
		for(int a : arr) {
			System.out.print(a + "\t");
		}
	}

	//模拟获取头数据
	public int getHead() {
		if(isEmpty()) {
			throw new RuntimeException("该队列为空。。。。");
		}

		return arr[front + 1];
	}

}
