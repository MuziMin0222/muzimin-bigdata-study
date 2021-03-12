package com.lhm.recursion;

/**
 * @program:Data_Structure
 * @package:com.lhm.recursion
 * @filename:MiGong.java
 * @create:2019.10.22.18:53:23
 * @auther:李煌民
 * @description:. 迷宫案列
 **/
public class MiGong {
    public static void main(String[] args) {
        //先创建一个二维数组，模拟迷宫
        int[][] map = new int[8][7];

        //使用1表示墙
        for (int i = 0;i < 7;i++){
            map[0][i] = 1;
            map[7][i] = 1;
        }
        for (int i = 0; i < 8;i++){
            map[i][0] = 1;
            map[i][6] = 1;
        }
        //设置挡板
        map[3][1] = 1;
        map[3][2] = 1;

        PrintMap(map);

        //递归找路
        setWay(map,1,1);

        //输出新地图
        System.out.println("新地图");
        PrintMap(map);
    }

    //使用递归回溯给小球找路
    /*
        map:表示地图
        i,j表示从地图出发的位置
        如果小球能到map[6][5]位置，说明通过找到
        约定：当map[i][j]为0时表示该点没有走过，当为1时表示墙，2表示该通路可以走，3表示该点已经走过，但是走不通
        走迷宫时，需要确定一个策略：下-右-上-左，如果该点走不通，再回溯
     */
    public static boolean setWay(int[][] map,int i,int j){
        if (map[6][5] == 2){
            //通过已经找到
            return true;
        }else {
            //如果当前的点还没有走过
            if (map[i][j] == 0){
                //就按照策略：下右上左走
                //假设该点可以走
                map[i][j] = 2;
                if (setWay(map,i+1,j)){//向下走
                    return true;
                }else if (setWay(map,i,j+1)){//向右走
                    return true;
                }else if (setWay(map,i-1,j)){//向上走
                    return true;
                }else if (setWay(map,i,j-1)){//向左走
                    return true;
                }else {
                    //说明该点是死路
                    map[i][j] = 3;
                    return false;
                }
            }else {//如果map[i][j] != 0,可能是1,2,3
                return false;
            }
        }
    }

    //输出地图
    public static void PrintMap(int[][] map){
        for (int i = 0;i < 8;i++){
            for (int j = 0;j < 7;j++){
                System.out.print(map[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
