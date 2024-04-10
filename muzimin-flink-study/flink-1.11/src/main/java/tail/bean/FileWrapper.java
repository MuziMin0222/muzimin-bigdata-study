package tail.bean;

import java.io.File;

/**
 * @author : 李煌民
 * @date : 2020-10-23 15:29
 * 文件包装类
 **/
public class FileWrapper {
    //文件读取的行数
    private int currentLine;

    //监听的文件
    private final File file;

    public FileWrapper(File file) {
        this.file = file;
    }

    public void setCurrentLine(int currentLine) {
        this.currentLine = currentLine;
    }

    public int getCurrentLine() {
        return currentLine;
    }

    public File getFile() {
        return file;
    }
}
