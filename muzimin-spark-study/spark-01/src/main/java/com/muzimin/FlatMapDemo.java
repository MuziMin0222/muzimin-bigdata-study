package com.muzimin;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * @author : 李煌民
 * @date : 2021-05-06 11:40
 **/
public class FlatMapDemo {
    public static void main(String[] args) throws IOException {

        Files.lines(Paths.get("/目录名", "文件名比如date.txt"), StandardCharsets.UTF_8)
                .flatMap(line -> Stream.of(line.split(";")));
    }
}
