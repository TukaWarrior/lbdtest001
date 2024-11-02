package ch.hftm.webserver;

import ch.hftm.config.PluginRegistry;
import ch.hftm.plugin.application.ApplicationPlugin;
import ch.hftm.plugin.widget.BooleanWidgetPlugin;
import ch.hftm.plugin.widget.TextFieldWidgetPlugin;
import ch.hftm.plugin.widget.WidgetPlugin;
import ch.hftm.webserver.Webserver;
import ch.hftm.webserver.util.NetworkUtils;
import io.javalin.Javalin;
import io.javalin.jetty.JettyServer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class WebserverTest {

    private static final String PUBLIC_DIR = "src/main/resources/public";
    private static final String JSON_FILE_PATH = PUBLIC_DIR + "/widgets.json";

    private Javalin mockJavalin;
    private PluginRegistry mockRegistry;
    private ApplicationPlugin mockPlugin;

    @BeforeEach
    public void setUp() {
        // Set up common mocks
        mockJavalin = Mockito.mock(Javalin.class);
        mockRegistry = Mockito.mock(PluginRegistry.class);
        mockPlugin = Mockito.mock(ApplicationPlugin.class);
    }


    @AfterEach
    public void tearDown() throws Exception {
        // Clean up the JSON file after each test
        Path jsonFilePath = Paths.get(JSON_FILE_PATH);
        if (Files.exists(jsonFilePath)) {
            Files.delete(jsonFilePath);
        }
        Mockito.clearAllCaches();
    }



    @Test
    public void testServerStartsWithValidIpAddress() {
        int expectedPort = 7070;

        try (var mockedJavalin = mockStatic(Javalin.class);
             var mockedRegistry = mockStatic(PluginRegistry.class)) {

            // Mock Javalin and its components
            Javalin mockJavalin = mock(Javalin.class);
            JettyServer mockJettyServer = mock(JettyServer.class);
            Server mockServer = mock(Server.class);

            when(Javalin.create(any())).thenReturn(mockJavalin);
            when(mockJavalin.jettyServer()).thenReturn(mockJettyServer);
            when(mockJettyServer.server()).thenReturn(mockServer);

            // Mock PluginRegistry
            PluginRegistry mockRegistry = mock(PluginRegistry.class);
            ApplicationPlugin mockAppPlugin = mock(ApplicationPlugin.class);
            when(PluginRegistry.getInstance()).thenReturn(mockRegistry);
            when(mockRegistry.getApplicationPlugin()).thenReturn(mockAppPlugin);
            when(mockAppPlugin.getPort()).thenReturn(expectedPort);

            // Call the method under test
            Webserver.start();

            // Verify Javalin.create() was called
            mockedJavalin.verify(() -> Javalin.create(any()));

            // Verify jettyServer() was called once
            verify(mockJavalin, times(1)).jettyServer();

            // Verify no other interactions with mockJavalin
            verifyNoMoreInteractions(mockJavalin);
        }
    }

    @Test
    public void testServerHandlesNullIpAddress() {
        // Mock NetworkUtils to return null (no active IP address)
        try (var mockedNetworkUtils = Mockito.mockStatic(NetworkUtils.class);
             var mockedJavalin = Mockito.mockStatic(Javalin.class);
             var mockedRegistry = Mockito.mockStatic(PluginRegistry.class)) {

            // Mock NetworkUtils to return null
            mockedNetworkUtils.when(NetworkUtils::getActiveIpAddress).thenReturn(null);

            // Mock the Javalin.create method to return the mock Javalin instance
            mockedJavalin.when(() -> Javalin.create(any())).thenReturn(mockJavalin);

            // Mock the PluginRegistry to avoid any potential NPE
            mockedRegistry.when(PluginRegistry::getInstance).thenReturn(mockRegistry);
            when(mockRegistry.getApplicationPlugin()).thenReturn(mockPlugin);
            when(mockPlugin.getPort()).thenReturn(7070);

            // Call the start method
            Webserver.start();

            // Verify that Javalin was not started, as there was no valid IP address
            verify(mockJavalin, never()).start(anyString(), anyInt());
        }
    }


    @Test
    public void testServerHandlesException() {
        // Mock NetworkUtils to throw an exception
        Mockito.mockStatic(NetworkUtils.class).when(NetworkUtils::getActiveIpAddress)
                .thenThrow(new RuntimeException("Network error"));

        // Call the start method and ensure no exception propagates
        assertDoesNotThrow(() -> Webserver.start(),
                "The server should handle exceptions gracefully");
    }

    @Test
    public void testGeneratedWidgetsToJson() throws Exception {

        // Create a mock BooleanWidgetPlugin and TextFieldWidgetPlugin
        BooleanWidgetPlugin mockBooleanWidget = mock(BooleanWidgetPlugin.class);
        when(mockBooleanWidget.getId()).thenReturn("activeID");
        when(mockBooleanWidget.getName()).thenReturn("Active");
        when(mockBooleanWidget.isChecked()).thenReturn(false);
        when(mockBooleanWidget.getToolTip()).thenReturn("Check if it's active");

        TextFieldWidgetPlugin mockTextFieldWidget = mock(TextFieldWidgetPlugin.class);
        when(mockTextFieldWidget.getId()).thenReturn("titleID");
        when(mockTextFieldWidget.getName()).thenReturn("Title");
        when(mockTextFieldWidget.getPlaceholderText()).thenReturn("title");
        when(mockTextFieldWidget.getMaxLength()).thenReturn(50);
        when(mockTextFieldWidget.getToolTip()).thenReturn("Enter the title");

        // Return a list of widgets when getWidgetPlugins() is called
        List<WidgetPlugin> widgetPlugins = List.of(mockBooleanWidget, mockTextFieldWidget);
        when(mockRegistry.getWidgetPlugins()).thenReturn(widgetPlugins);

        // Call the method and assert the JSON is as expected
        String actualJson = Webserver.generatedWidgetsToJson(mockRegistry);

        // Expected JSON as a String
        String expectedJson = "[{\"type\":\"checkbox\",\"id\":\"activeID\",\"label\":\"Active\",\"checked\":false,\"hint\":\"Check if it's active\"},"
                +
                "{\"type\":\"textfield\",\"id\":\"titleID\",\"label\":\"Title\",\"hint\":\"Enter the title\",\"maxLength\":50,\"placeholder\":\"title\"}]";

        // Use Jackson to compare the JSON structure, ignoring differences in formatting
        // or order
        ObjectMapper objectMapper = new ObjectMapper();

        List<Map<String, Object>> expectedList = objectMapper.readValue(expectedJson, List.class);
        List<Map<String, Object>> actualList = objectMapper.readValue(actualJson, List.class);

        assertEquals(expectedList, actualList);
    }

}