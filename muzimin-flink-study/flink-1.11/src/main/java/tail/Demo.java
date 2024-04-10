package tail;

import java.io.IOException;

/**
 * @author : 李煌民
 * @date : 2020-10-23 15:45
 * 测试
 **/
public class Demo {
    public static void main(String[] args) throws IOException {
        DirectoryTargetMonitor monitor = new DirectoryTargetMonitor(new FileChangeListener(), "D:\\monitor");
        monitor.startMonitor();
    }
}
