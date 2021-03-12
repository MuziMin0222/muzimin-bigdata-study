package tail;

import tail.bean.FileChangeEvent;

import java.io.IOException;
import java.nio.file.*;

/**
 * @author : 李煌民
 * @date : 2020-10-23 15:44
 * 目录监视器，监控目录下文件的变化
 **/
public class DirectoryTargetMonitor {
    private WatchService watchService;

    private final FileChangeListener listener;

    private final Path path;

    private volatile boolean start = false;

    public DirectoryTargetMonitor(final FileChangeListener listener, final String targetPath) {
        this(listener, targetPath, "");
    }

    public DirectoryTargetMonitor(final FileChangeListener listener, final String targetPath, final String... morePaths) {
        this.listener = listener;
        this.path = Paths.get(targetPath, morePaths);
    }

    public void startMonitor() throws IOException {
        this.watchService = FileSystems.getDefault().newWatchService();
        // 注册变更事件到WatchService
        this.path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
        this.start = true;
        while (start) {
            WatchKey watchKey = null;
            try {
                // 阻塞直到有事件发生
                watchKey = watchService.take();
                watchKey.pollEvents().forEach(event -> {
                    WatchEvent.Kind<?> kind = event.kind();
                    Path path = (Path) event.context();
                    Path child = this.path.resolve(path);
                    listener.fire(new FileChangeEvent(child, kind));
                });
            } catch (Exception e) {
                this.start = false;
            } finally {
                if (watchKey != null) {
                    watchKey.reset();
                }
            }
        }
    }

    public void stopMonitor() throws IOException {
        System.out.printf("The directory [%s] monitor will be stop ...\n", path);
        Thread.currentThread().interrupt();
        this.start = false;
        this.watchService.close();
        System.out.printf("The directory [%s] monitor will be stop done.\n", path);
    }

}
