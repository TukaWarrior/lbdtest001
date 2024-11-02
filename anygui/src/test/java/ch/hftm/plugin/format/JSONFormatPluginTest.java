package ch.hftm.plugin.format;

import ch.hftm.config.ConfigKeysConstants;
import com.moandjiezana.toml.Toml;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class JSONFormatPluginTest {

    private JSONFormatPlugin jsonFormatPlugin;
    @Mock
    private Toml mockConfig;

    @BeforeEach
    void setUp() {
        // Mocking the Toml configuration
        when(mockConfig.getString(ConfigKeysConstants.FORMAT_JSON_SCHEMA_FILE)).thenReturn("test-schema.json");
        jsonFormatPlugin = new JSONFormatPlugin(mockConfig);
    }

    @Test
    void testValidateSchemaWithValidJson() {
        // Sample JSON content that conforms to the schema
        String validJsonContent = "{\"devicename\": \"test\", \"value\": \"test-value\"}";

        assertTrue(jsonFormatPlugin.validateSchema(validJsonContent), "Expected JSON to be valid");
    }

    @Test
    void testValidateSchemaWithInvalidJson() {
        // Sample JSON content that does not conform to the schema
        String invalidJsonContent = "{\"devicename\": 123}"; // missing "value" and wrong type

        assertFalse(jsonFormatPlugin.validateSchema(invalidJsonContent), "Expected JSON to be invalid");
    }

    @Test
    void testSendToCoreWithValidJson() {
        String validJsonContent = "{\"devicename\": \"test\", \"value\": \"test-value\"}";

        // Should return content if valid
        assertEquals(validJsonContent, jsonFormatPlugin.sendToCore(validJsonContent));
    }

    @Test
    void testSendToCoreWithInvalidJson() {
        String invalidJsonContent = "{\"devicename\": 123}"; // invalid JSON

        // Should throw IllegalArgumentException if invalid
        assertThrows(IllegalArgumentException.class, () -> jsonFormatPlugin.sendToCore(invalidJsonContent));
    }

    @Test
    void testReadFromCoreWithValidJson() {
        String validJsonContent = "{\"devicename\": \"test\", \"value\": \"test-value\"}";

        // Should return JSON if valid
        assertEquals(validJsonContent, jsonFormatPlugin.readFromCore(validJsonContent));
    }

    @Test
    void testReadFromCoreWithInvalidJson() {
        String invalidJsonContent = "{\"devicename\": 123}"; // invalid JSON

        // Should throw IllegalArgumentException if invalid
        assertThrows(IllegalArgumentException.class, () -> jsonFormatPlugin.readFromCore(invalidJsonContent));
    }
}