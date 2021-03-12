package tail.bean;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * @author : 李煌民
 * @date : 2020-10-23 15:23
 * 文件变更事件
 **/
public class FileChangeEvent {
    private final Path path;
    //事件类型
    private final WatchEvent.Kind<?> kind;

    public FileChangeEvent(Path path, WatchEvent.Kind<?> kind) {
        this.path = path;
        this.kind = kind;
    }

    public Path getPath() {
        return path;
    }

    public WatchEvent.Kind<?> getKind() {
        return kind;
    }
}
