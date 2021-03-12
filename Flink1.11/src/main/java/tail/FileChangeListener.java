package tail;

import tail.bean.FileChangeEvent;
import tail.bean.FileWrapper;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : 李煌民
 * @date : 2020-10-23 15:27
 * Listener接口的实现类，处理文件变更事件
 **/
public class FileChangeListener implements Listener {
    //保存路径跟文件包装类的映射
    private final Map<String, FileWrapper> map = new ConcurrentHashMap<>();

    @Override
    public void fire(FileChangeEvent event) {
        switch (event.getKind().name()) {
            case "ENTRY_MODIFY":
                //文件修改事件
                modify(event.getPath());
                break;
            default:
                throw new UnsupportedOperationException(
                        String.format("The kind [%s] is unsupport.", event.getKind().name()));
        }
    }

    private void modify(Path path) {
        //根据全路径获取包装类对象
        FileWrapper wrapper = map.get(path.toString());
        if (wrapper == null) {
            wrapper = new FileWrapper(path.toFile());
            map.put(path.toString(), wrapper);
        }
        // 读取追加的内容
        new ContentReader(wrapper).read();
    }
}
