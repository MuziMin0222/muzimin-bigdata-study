package com.lhm.Queue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * 环形队列的测试
 *
 * 思路：
 * 1、front变量的含义做一个调整：front就指向队列的第一个元素，也就是说arr[front]就是
  *   队列的第一个元素，front的初始化值为0
	2、rear变量的含义做一个调整：rear指向队列的最后一个元素的后一个位置，因为希望
	空出一个空间作为约定，rear的初始值为0
	3、当队列满时，条件是(rear + 1)%maxSize = front
	4、当队列为空的条件，rear == front
	5、队列中的有效数字的个数是(rear + maxSize - front)%maxSize
	6、在原来的队列上就改进成环形队列
 */
public class CircleArrayQueueDemo {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("请输入一个队列的大小：");
		int maxsize = Integer.parseInt(br.readLine());
		Queue queue = new Queue(maxsize);
		while(true) {
			System.out.println("a表示队列的添加操作\r\n"
					+ "g表示队列的获取操作\r\n"
					+ "h表示获取头数据操作\r\n"
					+ "s表示显示队列的全部元素的操作\r\n");
			System.out.println("请输入你要执行的操作:");
			char c = br.readLine().toCharArray()[0];
			switch(c) {
				case 'a':
					System.out.println("请输入你要添加的数据：");
					int n = Integer.parseInt(br.readLine());
					queue.addQueue(n);
					break;
				case 'g':
					System.out.println(queue.getQueue());
					break;
				case 'h':
					System.out.println(queue.showHead());
					break;
				case 's':
					queue.showQueue();
					break;
			}
		}
	}
}

class Queue{
	//队列头，默认值是0
	private int front;
	//队列尾，默认值是0
	private int rear;
	//定义一个数组来存储数值
	private int[] arr;
	//定义该队列的最大值
	private int maxSize;

	public Queue(int maxSize) {
		this.maxSize = maxSize;
		//初始化数组
		arr = new int[maxSize];
		rear = 0;
		front = 0;
	}

	//判断队列是否已满
	public boolean isFull() {
		//这里会预留出一个空间作为约定
		return (rear + 1)%maxSize == front;
	}

	//判断你队列是否为空
	public boolean isEmpty() {
		return rear == front;
	}

	//模拟队列添加元素
	public void addQueue(int n) {
		if(isFull()) {
			System.out.println("该队列为满。。。不可以添加元素");
			return;
		}
		//这里rear直接添加，指向的是第一个元素的位置
		arr[rear] = n;
		//将rear往后移，那么这里做到回环的话必须取模
		rear = (rear+1)%maxSize;
	}

	//模拟队列获取元素
	public int getQueue() {
		if(isEmpty()) {
			System.out.println("该队列此时为空，，，，不可以取出元素");
			return -1;
		}
		int value = arr[front];
		front = (front + 1)%maxSize;
		return value;
	}

	//显示队列的全部元素
	public void showQueue() {
		if (isEmpty()) {
			System.out.println("该队列为空。。。");
			return;
		}
		for (int s = front; s < front + getNum(); s++) {
			System.out.println("arr[" + s%maxSize + "]:" + arr[s%maxSize]);
		}
	}

	//获取该队列的元素个数
	public int getNum() {
		return (rear+ maxSize - front)%maxSize;
	}

	//显示队列头数据
	public int showHead() {
		if(isEmpty()) {
			System.out.println("队列空。。。。");
			return -1;
		}
		return arr[front];
	}
}
