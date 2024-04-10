package tail;

import tail.bean.FileChangeEvent;

/**
 * @author : 李煌民
 * @date : 2020-10-23 15:19
 * 发生文件变动事件时的处理逻辑
 **/
public interface Listener {
    void fire(FileChangeEvent event);
}
