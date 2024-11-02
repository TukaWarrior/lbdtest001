package ch.hftm.plugin.io;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.moandjiezana.toml.Toml;

import ch.hftm.plugin.io.KeyValueIOPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class KeyValueIOPluginTest {

    private static final String TEST_KEYVALUE_FILE_PATH = "src/test/resources/test_keyvalue_file.txt";

    @Test
    public void testReadRawData() throws IOException {
        // Arrange: Initialize keyValueIOPlugin and write data to the test file
        String tomlContent = "[io_plugin]\nfilePath = \"" + TEST_KEYVALUE_FILE_PATH + "\"";
        Toml toml = new Toml().read(tomlContent);
        KeyValueIOPlugin keyValueIOPlugin = new KeyValueIOPlugin(toml);
        Files.writeString(Path.of(TEST_KEYVALUE_FILE_PATH), "Key=Value");

        // Act: Read the data
        String data = keyValueIOPlugin.readRawData();

        // Assert: Verify the content is correct
        assertEquals("Key=Value", data);
    }

    @Test
    public void testWriteRawData() throws IOException {
        // Arrange: Initialize keyValueIOPlugin
        String tomlContent = "[io_plugin]\nfilePath = \"" + TEST_KEYVALUE_FILE_PATH + "\"";
        Toml toml = new Toml().read(tomlContent);
        KeyValueIOPlugin keyValueIOPlugin = new KeyValueIOPlugin(toml);

        // Act: Write data to the test file
        keyValueIOPlugin.writeRawData("NewKey=NewValue");

        // Assert: Verify the content in the file
        String content = Files.readString(Path.of(TEST_KEYVALUE_FILE_PATH));
        assertEquals("NewKey=NewValue", content);
    }

    @Test
    public void testValidate() {
        // Arrange: Initialize keyValueIOPlugin
        String tomlContent = "[io_plugin]\nfilePath = \"" + TEST_KEYVALUE_FILE_PATH + "\"";
        Toml toml = new Toml().read(tomlContent);
        KeyValueIOPlugin keyValueIOPlugin = new KeyValueIOPlugin(toml);

        // Act & Assert: Validate should return true since the file path is set
        assertTrue(keyValueIOPlugin.validate());
    }
}