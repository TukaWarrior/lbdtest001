package ch.hftm.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PluginParserTest {

    private static final Logger log = LoggerFactory.getLogger(PluginParserTest.class);

    @TempDir
    Path tempDir;

    private static final String DEFAULT_CONFIG_FILE_PATH = "anygui_config.toml";

    @BeforeEach
    void setUp() {
    }

    @Test
    void testConstructorWithExternalConfigFilePresent() {
        File externalConfigFile = tempDir.resolve(DEFAULT_CONFIG_FILE_PATH).toFile();

        try (MockedStatic<File> mockedFile = Mockito.mockStatic(File.class)) {
            mockedFile.when(() -> new File(DEFAULT_CONFIG_FILE_PATH)).thenReturn(externalConfigFile);
            externalConfigFile.createNewFile();

            assertDoesNotThrow(PluginParser::new, "Constructor should load config from external file without exception");
            log.info("Test passed: External config file loaded successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
