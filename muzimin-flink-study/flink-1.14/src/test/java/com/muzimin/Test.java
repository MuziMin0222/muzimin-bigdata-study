package com.muzimin;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Arrays;

/**
 * @author: 李煌民
 * @date: 2024-04-18 08:02
 **/
public class Test {
    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment(configuration);

        DataStreamSource<String> source = environment.fromCollection(Arrays.asList("a", "b", "c"));

        source.print();

        environment.execute();
    }
}
