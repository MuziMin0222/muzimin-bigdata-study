package tail;

import tail.bean.FileWrapper;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : 李煌民
 * @date : 2020-10-23 15:26
 * 内容读取类
 **/
public class ContentReader {
    private final FileWrapper wrapper;

    public ContentReader(FileWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public void read() {
        try (LineNumberReader lineReader = new LineNumberReader(new FileReader(wrapper.getFile()))) {
            List<String> contents = lineReader.lines().collect(Collectors.toList());
            if (contents.size() > wrapper.getCurrentLine()) {
                for (int i = wrapper.getCurrentLine(); i < contents.size(); i++) {
                    // 这里只是简单打印出新加的内容到控制台
                    System.out.println(contents.get(i));
                }
            }
            // 保存当前读取到的行数
            wrapper.setCurrentLine(contents.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
