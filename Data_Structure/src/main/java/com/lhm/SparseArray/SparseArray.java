package com.lhm.SparseArray;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/*
 * 二维数组转稀疏数组以及稀疏数组转二维数组
 */
public class SparseArray {
	public static void main(String[] args) throws IOException {
		//创建一个二维数组
		int[][] arr = new int[11][10];
		//对二维数组一些特殊地方进行复制
		arr[0][3] = 22;
		arr[0][6] = 15;
		arr[1][1] = 11;
		System.out.println("这是最初的二维数组-----");
		PrintArray(arr);

		//将二维数组转为一个稀疏数组
		int[][] sparseArray = ErWeiArrayToSparseArray(arr);
		System.out.println("这是一个由二维数组转为的稀疏数组======");
		PrintArray(sparseArray);

		//稀疏数组转二维数组
		int[][] array = SparseArrayToErWeiArray(sparseArray);
		System.out.println("这是一个由稀疏数组转为二维数组++++++++++++");
		PrintArray(array);

		//将数组写入文件中
		WriterArrToFile(array);

		System.out.println("从文件中读取的二维数组。。。。。");
		int[][] arr2 = ReaderFileToArr();
		PrintArray(arr2);
	}

	/*
	 * 将数组写入文件中
	 */
	public static void WriterArrToFile(int[][] arr) throws IOException{
		StringBuffer sb = new StringBuffer();
		BufferedWriter bw= new BufferedWriter(new OutputStreamWriter(new FileOutputStream("a.txt")));
		for(int i = 0;i < arr.length;i++) {
			for(int j = 0;j < arr[i].length;j++) {
				if(j == arr[i].length-1) {
					sb.append(arr[i][j]);
				}else {
					sb.append(arr[i][j]).append(",");
				}
			}
			bw.write(sb.toString());
			bw.newLine();
			bw.flush();
			sb = new StringBuffer();
		}

		bw.close();
	}


	/*
	 * 将文件写入到数组
	 */
	@SuppressWarnings("resource")
	public static int[][] ReaderFileToArr() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("a.txt")));
		String line;
		int row = 0;
		int col = 0;
		while((line = br.readLine()) != null) {
			String[] strArr = line.split(",");
			row++;
			col = strArr.length;
		}

		int[][] arr = new int[row][col];

		BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream("a.txt")));
		String str;
		int count = 0;
		while((str = br1.readLine()) != null) {
			String[] string = str.split(",");
			for(int i = 0;i < string.length;i++) {
				arr[count][i] = Integer.parseInt(string[i]);
			}
			count++;
		}

		br.close();

		return arr;
	}



	/*
	 * 稀疏数组转二维数组
	 * 1、先读取稀疏数组中的第一行，根据第一行的数据，创建原始的二维数组
	 * 2、在读取稀疏数组后几行的数据，赋值给二维数组即可
	 */
	public static int[][] SparseArrayToErWeiArray(int[][] sparseArr) {
		//定义一个二维数组
		int[][] arr = new int[sparseArr[0][0]][sparseArr[0][1]];

		for(int i =1;i < sparseArr.length;i++) {
			arr[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
		}

		return arr;
	}

	/*
	 * 二维数组转稀疏数组
	 * 1、遍历原始的二维数组，得到有效数据的个数sum
	 * 2、根据sum就可以创建稀疏数组sparseArr int[sum+1][3]
	 * 3、将二维数组的有效数据存放到稀疏数组中
	 */
	public static int[][] ErWeiArrayToSparseArray(int[][] arr) {
		int sum = 0;
		for(int[] a : arr) {
			for(int data : a) {
				if(data != 0) {
					sum++;
				}
			}
		}

		//创建一个稀疏数组
		int[][] sparsearr = new int[sum + 1][3];

		//为稀疏数组中的每一个值进行赋值
		//1、为稀疏数组的第一行先赋值
		sparsearr[0][0] = arr.length;
		sparsearr[0][1] = arr[0].length;
		sparsearr[0][2] = sum;
		int count = 1;
		for(int i = 0;i < arr.length;i++) {
			for(int j = 0; j < arr[i].length;j++) {
				if(arr[i][j] != 0) {
					sparsearr[count][0] = i;
					sparsearr[count][1] = j;
					sparsearr[count][2] = arr[i][j];
					count++;
				}
			}
		}

		return sparsearr;
	}

	//遍历一个二维数组
	public static void PrintArray(int[][] arr) {
		for(int[] a : arr) {
			for(int data : a) {
				System.out.print(data + "\t");
			}
			System.out.println();
		}
	}
}
