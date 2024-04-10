package com.lhm.Spark;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.*;

/**
 * @author : 李煌民
 * @date : 2019-11-26 08:47
 * 云中数据拿到本地
 **/
public class GetData {
    public static void main(String[] args) throws IOException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","hdfs://172.16.0.4:9000");

        FileSystem fs = FileSystem.get(conf);

        Path path1 = new Path("/data/grouplens/ml-1m/README");
        Path path2 = new Path("/data/grouplens/ml-1m/movies.dat");
        Path path3 = new Path("/data/grouplens/ml-1m/ratings.dat");
        Path path4 = new Path("/data/grouplens/ml-1m/users.dat");
        Path[] arr_path = {path1,path2,path3,path4};

//        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\data")));

        for (Path path : arr_path) {
            FSDataInputStream fis = fs.open(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            System.out.println(path.getName());

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\" + path.getName())));
            String line;
            while ((line = br.readLine()) != null){
                bw.write(line);
                bw.newLine();
                bw.flush();
            }
        }



//        FileStatus[] fss = fs.listStatus(arr_path);
//        for (FileStatus fileStatus : fss) {
//            System.out.println(fileStatus);
//        }


    }
}
