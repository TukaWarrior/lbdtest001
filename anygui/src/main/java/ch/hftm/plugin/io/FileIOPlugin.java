package ch.hftm.plugin.io;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.nio.file.WatchService;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;

import ch.hftm.plugin.widget.WidgetPlugin;
import com.moandjiezana.toml.Toml;

import ch.hftm.config.ConfigKeysConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class FileIOPlugin extends IOPlugin {
    private static final Logger log = LoggerFactory.getLogger(FileIOPlugin.class);
    private String readFilePath;
    private String writeFilePath;
    private WatchService watchService;
    private ExecutorService executorService;

    // Constructor to take the file paths from the TOML configuration
    public FileIOPlugin(Toml config) {
        // Get file paths from TOML configuration
        parseConfig(config);

        // Initialize WatchService if the directory for reading exists
        try {
            Path readDir = Path.of(readFilePath).getParent();
            if (Files.exists(readDir)) {
                watchService = FileSystems.getDefault().newWatchService();
                readDir.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
                executorService = Executors.newSingleThreadExecutor();
                startWatching();
            } else {
                log.error("Directory for file watching does not exist: {}", readDir);

            }
        } catch (IOException e) {
            log.error("Error setting up WatchService for file monitoring: {}", readFilePath, e);

        }
    }

    private void startWatching() {
        executorService.submit(() -> {
            try {
                WatchKey key;
                while ((key = watchService.take()) != null) {
                    for (WatchEvent<?> event : key.pollEvents()) {
                        if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                            Path changed = (Path) event.context();
                            if (changed.endsWith(Path.of(readFilePath).getFileName())) {
                                log.info("File {} has been modified. Reading new data.", readFilePath);

                                readRawData();
                            }
                        }
                    }
                    key.reset();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.warn("File watching interrupted.");

            }
        });
    }

    // Method to read data from the specified read file
    @Override
    public String readRawData() {
        try {
            return Files.readString(Path.of(readFilePath));
        } catch (IOException e) {
            log.error("Error reading the file: {}", readFilePath, e);

            return null;
        }
    }

    // Method to write data to the specified write file
    @Override
    public void writeRawData(String data) {
        try {
            Files.writeString(Path.of(writeFilePath), data);
        } catch (IOException e) {
            log.error("Error writing to the file: {}", writeFilePath, e);

        }
    }

    @Override
    public void parseConfig(Toml config) {
        this.readFilePath = config.getString(ConfigKeysConstants.IO_FILE_READ_PATH, "src/test/resources/test_read_file.txt");
        this.writeFilePath = config.getString(ConfigKeysConstants.IO_FILE_WRITE_PATH, "src/test/resources/test_write_file.txt");
    }

    @Override
    public String toString() {
        return "FileIOPlugin{" +
                "readFilePath='" + readFilePath + '\'' +
                ", writeFilePath='" + writeFilePath + '\'' +
                ", watchService=" + watchService +
                ", executorService=" + executorService +
                '}';
    }

    public static Set<String> retrieveExpectedColumns() {
        Set<String> expectedColumns = IOPlugin.retrieveExpectedColumns();
        expectedColumns.add(ConfigKeysConstants.IO_FILE_READ_PATH);
        expectedColumns.add(ConfigKeysConstants.IO_FILE_WRITE_PATH);
        return expectedColumns;
    }
}
