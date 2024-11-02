package ch.hftm.config;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import ch.hftm.plugin.Plugin;
import ch.hftm.plugin.application.ApplicationPlugin;
import ch.hftm.plugin.format.FormatPlugin;
import ch.hftm.plugin.io.IOPlugin;
import ch.hftm.plugin.widget.WidgetPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PluginRegistry is a Singleton class that serves as a global storage for all registered plugin instances.
 * It provides methods to register plugins and retrieve different types of plugins (e.g., Widget, IO, Format, Application).
 * This registry ensures that the same instance is shared across the entire application, providing easy access to plugins.
 *
 * The class is used to register plugins after they are created (e.g., using {@link PluginParser}) and to
 * retrieve them for different purposes throughout the application.
 *
 * <p><b>Example Usage:</b></p>
 *
 * <pre>
 * {@code
 * // Get the PluginRegistry instance
 * PluginRegistry registry = PluginRegistry.getInstance();
 *
 * // Register a plugin (e.g., WidgetPlugin)
 * WidgetPlugin widgetPlugin = new TextFieldWidgetPlugin();
 * registry.registerPlugin(widgetPlugin);
 *
 * // Retrieve all registered widget plugins
 * List<WidgetPlugin> widgetPlugins = registry.getWidgetPlugins();
 *
 * // Get the single ApplicationPlugin
 * ApplicationPlugin appPlugin = registry.getApplicationPlugin();
 * }
 * </pre>
 */
public class PluginRegistry {
    private static PluginRegistry instance;
    private final List<Plugin> plugins;
    private static final Logger log = LoggerFactory.getLogger(PluginRegistry.class);

    /**
     * Private constructor for the Singleton pattern. Initializes the plugin storage list.
     *
     * This ensures that only one instance of PluginRegistry can be created.
     */
    private PluginRegistry() {
        plugins = new ArrayList<>();
    }

    /**
     * Returns the singleton instance of PluginRegistry.
     *
     * If the instance does not already exist, it is created.
     *
     * @return The singleton instance of PluginRegistry.
     */
    public static PluginRegistry getInstance() {
        if (instance == null) {
            instance = new PluginRegistry();
        }
        return instance;
    }

    /**
     * Registers a plugin into the registry. The plugin will be stored in the internal list
     * and can be retrieved later using the corresponding retrieval methods.
     *
     * @param plugin The plugin to register.
     */
    public void registerPlugin(Plugin plugin) {
        LinkedHashMap<Class<? extends Plugin>, Integer> compatibilityMap = plugin.getIncompatibilityMap();
        compatibilityMap.forEach((key, value) -> {
            long count = plugins.stream().filter(p -> key.isAssignableFrom(p.getClass())).count();

            if (count > value) {

                String errorMessage = "Plugin registration failed: " + plugin.getType() +
                        " is incompatible with " + key.getSimpleName() +
                        ". Maximum allowed instances of " + key.getSimpleName() + " is " + value +
                        ", but found " + count + ".";

                log.warn(errorMessage);
                throw new IllegalArgumentException(errorMessage);
            }

        });

        plugins.add(plugin);
    }

    /**
     * Returns a list of all registered {@link WidgetPlugin} instances.
     * <p>
     * This method filters the plugins list and returns only those that are instances of {@link WidgetPlugin}.
     *
     * @return A list of registered WidgetPlugin instances.
     */
    public List<WidgetPlugin> getWidgetPlugins() {
        List<WidgetPlugin> widgetPlugins = new ArrayList<>();
        for (Plugin plugin : plugins) {
            if (plugin instanceof WidgetPlugin) {
                widgetPlugins.add((WidgetPlugin) plugin);
            }
        }
        return widgetPlugins;
    }

    /**
     * Returns a list of all registered {@link FormatPlugin} instances.
     * <p>
     * This method filters the plugins list and returns only those that are instances of {@link FormatPlugin}.
     *
     * @return A list of registered FormatPlugin instances.
     */
    public List<FormatPlugin> getFormatPlugins() {
        List<FormatPlugin> formatPlugins = new ArrayList<>();
        for (Plugin plugin : plugins) {
            if (plugin instanceof FormatPlugin) {
                formatPlugins.add((FormatPlugin) plugin);
            }
        }
        return formatPlugins;
    }

    /**
     * Returns a list of all registered {@link IOPlugin} instances.
     * <p>
     * This method filters the plugins list and returns only those that are instances of {@link IOPlugin}.
     *
     * @return A list of registered IOPlugin instances.
     */
    public List<IOPlugin> getIOPlugins() {
        List<IOPlugin> ioPlugins = new ArrayList<>();
        for (Plugin plugin : plugins) {
            if (plugin instanceof IOPlugin) {
                ioPlugins.add((IOPlugin) plugin);
            }
        }
        return ioPlugins;
    }

    /**
     * Returns the registered {@link ApplicationPlugin} instance.
     * <p>
     * There should be only one ApplicationPlugin instance, and this method retrieves it. If no
     * ApplicationPlugin is registered, it returns null.
     *
     * @return The registered ApplicationPlugin instance, or null if not found.
     */
    public ApplicationPlugin getApplicationPlugin() {
        for (Plugin plugin : plugins) {
            if (plugin instanceof ApplicationPlugin) {
                return (ApplicationPlugin) plugin;
            }
        }
        return null;
    }

    /**
     * Returns a list of all registered plugins.
     * <p>
     * This method returns the entire list of plugins, regardless of their specific types.
     *
     * @return A list of all registered Plugin instances.
     */
    public List<Plugin> getAllPlugins() {
        return plugins;
    }
}