package com.lhm.Spark;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.*;

/**
 * @author : 李煌民
 * @date : 2019-11-27 09:40
 * 拿数据
 **/
public class GetDataTopic {
    public static void main(String[] args) throws IOException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","hdfs://172.16.0.4:9000");

        FileSystem fs = FileSystem.get(conf);

//        Path path = new Path("/data/spark/topic");
        Path path = new Path("/data/spark/pageRank");

        FileStatus[] fss = fs.listStatus(path);
        for (FileStatus fileStatus : fss) {
            System.out.println(fileStatus.getPath().getName());
            FSDataInputStream fis = fs.open(fileStatus.getPath());
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\" + fileStatus.getPath().getName())));
            String line;
            while ((line = br.readLine()) != null){
                bw.write(line);
                bw.newLine();
                bw.flush();
            }
            bw.close();
            br.close();
        }
    }
}
