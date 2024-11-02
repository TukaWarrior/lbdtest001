package ch.hftm.plugin.format;

import com.google.gson.Gson;
import com.moandjiezana.toml.Toml;

import ch.hftm.config.ConfigKeysConstants;
import ch.hftm.config.PluginRegistry;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
@ExtendWith(MockitoExtension.class)
public class PropertyFormatPluginTest {

//    private PropertyFormatPlugin propertyFormatPlugin;
//    @Mock
//    private Toml mockToml;
//
//    @BeforeEach
//    public void setUp() {
//        Mockito.when(mockToml.getString(ConfigKeysConstants.FORMAT_CHARSET)).thenReturn("UTF-8");
//        propertyFormatPlugin = new PropertyFormatPlugin(mockToml);
//    }
//
//    @Test
//    void testSendToCore() {
//        // Create a sample properties string
//        String content = "name=John\nage=30\ncity=New York";
//
//        // Call sendToCore method
//        String jsonResult = propertyFormatPlugin.sendToCore(content);
//
//        // Expected JSON result
//        String expectedJson = new Gson().toJson(Map.of(
//                "name", "John",
//                "age", "30",
//                "city", "New York"
//        ));
//
//        // Assert that the JSON result matches the expected output
//        assertEquals(expectedJson, jsonResult);
//    }
//
//    @Test
//    void testReadFromCore() {
//        // JSON input
//        String jsonInput = "{\"name\":\"John\",\"age\":\"30\",\"city\":\"New York\"}";
//
//        // Call readFromCore method
//        String propertiesResult = propertyFormatPlugin.readFromCore(jsonInput);
//
//        // Expected properties format string
//        String expectedProperties = "name=John" + System.lineSeparator() +
//                "age=30" + System.lineSeparator() +
//                "city=New York";
//
//        // Assert that the result matches the expected output
//        assertEquals(expectedProperties, propertiesResult);
//    }
//
//    @Test
//    void testReadFromCoreWithEmptyJson() {
//        // Empty JSON input
//        String jsonInput = "{}";
//
//        // Call readFromCore method
//        String propertiesResult = propertyFormatPlugin.readFromCore(jsonInput);
//
//        // Assert that the result is an empty string
//        assertEquals("", propertiesResult);
//    }

}
