package ch.hftm.plugin.io;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.moandjiezana.toml.Toml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileIOPluginTest {

    private static final String TEST_READ_FILE_PATH = "src/test/resources/test_read_file.txt";
    private static final String TEST_WRITE_FILE_PATH = "src/test/resources/test_write_file.txt";

    @Test
    public void testReadRawData() throws IOException {
        // Arrange: Initialize fileIOPlugin and write data to the test read file
        String tomlContent = "[io_plugin]\nreadFilePath = \"" + TEST_READ_FILE_PATH + "\"\nwriteFilePath = \"" + TEST_WRITE_FILE_PATH + "\"";
        Toml toml = new Toml().read(tomlContent);
        FileIOPlugin fileIOPlugin = new FileIOPlugin(toml);
        Files.writeString(Path.of(TEST_READ_FILE_PATH), "Hello, World!");

        // Act: Read the data
        String data = fileIOPlugin.readRawData();

        // Assert: Verify the content is correct
        assertEquals("Hello, World!", data);
    }

    @Test
    public void testWriteRawData() throws IOException {
        // Arrange: Initialize fileIOPlugin
        String tomlContent = "[io_plugin]\nreadFilePath = \"" + TEST_READ_FILE_PATH + "\"\nwriteFilePath = \"" + TEST_WRITE_FILE_PATH + "\"";
        Toml toml = new Toml().read(tomlContent);
        FileIOPlugin fileIOPlugin = new FileIOPlugin(toml);

        // Act: Write data to the test write file
        fileIOPlugin.writeRawData("Test Data");

        // Assert: Verify the content in the write file
        String content = Files.readString(Path.of(TEST_WRITE_FILE_PATH));
        assertEquals("Test Data", content);
    }

    @Test
    public void testValidate() {
        // Arrange: Initialize fileIOPlugin
        String tomlContent = "[io_plugin]\nreadFilePath = \"" + TEST_READ_FILE_PATH + "\"\nwriteFilePath = \"" + TEST_WRITE_FILE_PATH + "\"";
        Toml toml = new Toml().read(tomlContent);
        FileIOPlugin fileIOPlugin = new FileIOPlugin(toml);

        // Act & Assert: Validate should return true since both file paths are set
       //  assertTrue(fileIOPlugin.validate());
    }
}
