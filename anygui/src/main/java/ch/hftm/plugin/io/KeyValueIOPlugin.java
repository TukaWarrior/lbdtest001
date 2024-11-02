package ch.hftm.plugin.io;

import java.nio.file.Files;
import java.nio.file.Path;

import com.moandjiezana.toml.Toml;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyValueIOPlugin extends IOPlugin {
    private static final Logger log = LoggerFactory.getLogger(KeyValueIOPlugin.class);
    private String filePath;

    // Constructor to take the file path from the TOML configuration
    public KeyValueIOPlugin(Toml config) {
        this.filePath = config.getString("io_plugin.filePath", "default/path/to/file.properties");
    }

    // Method to read data from the file
    @Override
    public String readRawData() {
        try {
            return Files.readString(Path.of(filePath));
        } catch (IOException e) {
            log.error("Error reading the file: {}" , filePath, e);
            return null;
        }
    }

    // Method to write data to the file
    @Override
    public void writeRawData(String data) {
        try {
            Files.writeString(Path.of(filePath), data);
        } catch (IOException e) {
            log.error("Error writing to the file: {}", filePath, e);
        }
    }

    // TODO remove here and add it in plugin parser
    public boolean validate() {
        return filePath != null && !filePath.isEmpty();
    }

    // Empty implementation because configuration parsing is done in the constructor
    @Override
    public void parseConfig(Toml config) {
        // No parsing needed here
    }
}
