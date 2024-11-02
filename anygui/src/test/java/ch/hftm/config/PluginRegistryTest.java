package ch.hftm.config;

import ch.hftm.plugin.Plugin;
import ch.hftm.plugin.application.ApplicationPlugin;
import ch.hftm.plugin.format.FormatPlugin;
import ch.hftm.plugin.format.JSONFormatPlugin;
import ch.hftm.plugin.io.FileIOPlugin;
import ch.hftm.plugin.io.IOPlugin;
import ch.hftm.plugin.widget.WidgetPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for PluginRegistry.
 */

public class PluginRegistryTest {

    private PluginRegistry registry;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        try {
            Field instance = PluginRegistry.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            fail("Failed to reset PluginRegistry instance: " + e.getMessage());
        }
        registry = PluginRegistry.getInstance();
    }

    /**
     * Tests that getInstance() returns the same instance.
     */
    @Test
    void getInstance_returnsSameInstance() {
        PluginRegistry instance1 = PluginRegistry.getInstance();
        PluginRegistry instance2 = PluginRegistry.getInstance();
        assertSame(instance1, instance2);
    }

    /**
     * Tests that registerPlugin() adds a plugin to the registry.
     */
    @Test
    void registerPlugin_addsPluginToRegistry() {
        Plugin plugin = mock(Plugin.class);
        when(plugin.getIncompatibilityMap()).thenReturn(new LinkedHashMap<>());

        registry.registerPlugin(plugin);

        assertTrue(registry.getAllPlugins().contains(plugin));
    }

    /**
     * Tests that registerPlugin() throws an exception for an incompatible plugin with him self.
     */
    @Test
    void registerPlugin_throwsExceptionForIncompatiblePluginWithHimSelf() {
        Plugin plugin = mock(ApplicationPlugin.class);
        LinkedHashMap<Class<? extends Plugin>, Integer> incompatibilityMap = new LinkedHashMap<>();
        incompatibilityMap.put(ApplicationPlugin.class, 0);
        when(plugin.getIncompatibilityMap()).thenReturn(incompatibilityMap);
        registry.registerPlugin(plugin);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            registry.registerPlugin(plugin);
        });

        assertTrue(exception.getMessage().contains("is incompatible with"));
    }

    /**
     * Tests that registerPlugin() throws an exception for an incompatible plugin with a other Plugin.
     */
    @Test
    void registerPlugin_throwsExceptionForIncompatibleAbstractPluginWithAnOtherPlugin() {
        Plugin plugin = mock(FileIOPlugin.class);
        LinkedHashMap<Class<? extends Plugin>, Integer> incompatibilityMap = new LinkedHashMap<>();
        incompatibilityMap.put(FormatPlugin.class, 0);
        when(plugin.getIncompatibilityMap()).thenReturn(incompatibilityMap);

        registry.registerPlugin(plugin);

        Plugin plugin2 = mock(JSONFormatPlugin.class);
        LinkedHashMap<Class<? extends Plugin>, Integer> incompatibilityMap2 = new LinkedHashMap<>();
        incompatibilityMap2.put(IOPlugin.class, 0);
        when(plugin2.getIncompatibilityMap()).thenReturn(incompatibilityMap2);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            registry.registerPlugin(plugin2);
        });

        assertTrue(exception.getMessage().contains("is incompatible with"));
    }

    /**
     * Tests that registerPlugin() throws an exception for an incompatible plugin with a other Plugin.
     */
    @Test
    void registerPlugin_throwsExceptionForIncompatiblePluginWithAnOtherPlugin() {
        Plugin plugin = mock(FileIOPlugin.class);
        LinkedHashMap<Class<? extends Plugin>, Integer> incompatibilityMap = new LinkedHashMap<>();
        incompatibilityMap.put(JSONFormatPlugin.class, 0);
        when(plugin.getIncompatibilityMap()).thenReturn(incompatibilityMap);

        registry.registerPlugin(plugin);

        Plugin plugin2 = mock(JSONFormatPlugin.class);
        LinkedHashMap<Class<? extends Plugin>, Integer> incompatibilityMap2 = new LinkedHashMap<>();
        incompatibilityMap2.put(FileIOPlugin.class, 0);
        when(plugin2.getIncompatibilityMap()).thenReturn(incompatibilityMap2);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            registry.registerPlugin(plugin2);
        });

        assertTrue(exception.getMessage().contains("is incompatible with"));
    }

    /**
     * Tests that registerPlugin() throws an exception for an incompatible plugin with a other Plugin.
     */
    @Test
    void registerPlugin_throwsExceptionForIncompatiblePluginWithAnOtherMultiPlugin() {
        Plugin plugin = mock(FileIOPlugin.class);
        LinkedHashMap<Class<? extends Plugin>, Integer> incompatibilityMap = new LinkedHashMap<>();
        incompatibilityMap.put(JSONFormatPlugin.class, 2);
        when(plugin.getIncompatibilityMap()).thenReturn(incompatibilityMap);

        registry.registerPlugin(plugin);
        registry.registerPlugin(plugin);
        registry.registerPlugin(plugin);

        Plugin plugin2 = mock(JSONFormatPlugin.class);
        LinkedHashMap<Class<? extends Plugin>, Integer> incompatibilityMap2 = new LinkedHashMap<>();
        incompatibilityMap2.put(FileIOPlugin.class, 2);
        when(plugin2.getIncompatibilityMap()).thenReturn(incompatibilityMap2);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            registry.registerPlugin(plugin2);
        });

        assertTrue(exception.getMessage().contains("is incompatible with"));
    }

    /**
     * Tests that getWidgetPlugins() returns only widget plugins.
     */
    @Test
    void getWidgetPlugins_returnsOnlyWidgetPlugins() {
        WidgetPlugin widgetPlugin = mock(WidgetPlugin.class);
        when(widgetPlugin.getIncompatibilityMap()).thenReturn(new LinkedHashMap<>());
        registry.registerPlugin(widgetPlugin);

        List<WidgetPlugin> widgetPlugins = registry.getWidgetPlugins();

        assertEquals(1, widgetPlugins.size());
        assertTrue(widgetPlugins.contains(widgetPlugin));
    }

    /**
     * Tests that getFormatPlugins() returns only format plugins.
     */
    @Test
    void getFormatPlugins_returnsOnlyFormatPlugins() {
        FormatPlugin formatPlugin = mock(FormatPlugin.class);
        when(formatPlugin.getIncompatibilityMap()).thenReturn(new LinkedHashMap<>());
        registry.registerPlugin(formatPlugin);

        List<FormatPlugin> formatPlugins = registry.getFormatPlugins();

        assertEquals(1, formatPlugins.size());
        assertTrue(formatPlugins.contains(formatPlugin));
    }

    /**
     * Tests that getIOPlugins() returns only IO plugins.
     */
    @Test
    void getIOPlugins_returnsOnlyIOPlugins() {
        FileIOPlugin fileIOPlugin = mock(FileIOPlugin.class);
        when(fileIOPlugin.getIncompatibilityMap()).thenReturn(new LinkedHashMap<>());
        registry.registerPlugin(fileIOPlugin);

        List<IOPlugin> ioPlugins = registry.getIOPlugins();

        assertEquals(1, ioPlugins.size());
        assertTrue(ioPlugins.contains(fileIOPlugin));
    }


    /**
     * Tests that getApplicationPlugin() returns the application plugin.
     */
    @Test
    void getApplicationPlugin_returnsApplicationPlugin() {
        ApplicationPlugin appPlugin = mock(ApplicationPlugin.class);
        when(appPlugin.getIncompatibilityMap()).thenReturn(new LinkedHashMap<>());
        registry.registerPlugin(appPlugin);

        ApplicationPlugin retrievedAppPlugin = registry.getApplicationPlugin();

        assertSame(appPlugin, retrievedAppPlugin);
    }

    /**
     * Tests that getApplicationPlugin() returns null when no application plugin is registered.
     */
    @Test
    void getApplicationPlugin_returnsNullWhenNoApplicationPlugin() {
        ApplicationPlugin retrievedAppPlugin = registry.getApplicationPlugin();

        assertNull(retrievedAppPlugin);
    }

    /**
     * Tests that getAllPlugins() returns all registered plugins.
     */
    @Test
    void getAllPlugins_returnsAllRegisteredPlugins() {
        Plugin plugin1 = mock(Plugin.class);
        Plugin plugin2 = mock(Plugin.class);
        when(plugin1.getIncompatibilityMap()).thenReturn(new LinkedHashMap<>());
        when(plugin2.getIncompatibilityMap()).thenReturn(new LinkedHashMap<>());

        registry.registerPlugin(plugin1);
        registry.registerPlugin(plugin2);

        List<Plugin> allPlugins = registry.getAllPlugins();

        assertEquals(2, allPlugins.size());
        assertTrue(allPlugins.contains(plugin1));
        assertTrue(allPlugins.contains(plugin2));
    }
}