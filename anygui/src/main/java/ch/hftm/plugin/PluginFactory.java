package ch.hftm.plugin;

import ch.hftm.config.ConfigKeysConstants;
import ch.hftm.config.PluginParser;
import ch.hftm.plugin.application.ApplicationPlugin;
import ch.hftm.plugin.format.JSONFormatPlugin;
import ch.hftm.plugin.format.PropertyFormatPlugin;
import ch.hftm.plugin.io.FileIOPlugin;
import ch.hftm.plugin.widget.BooleanWidgetPlugin;
import ch.hftm.plugin.widget.FloatWidgetPlugin;
import ch.hftm.plugin.widget.IntegerWidgetPlugin;
import ch.hftm.plugin.widget.TextFieldWidgetPlugin;

import com.moandjiezana.toml.Toml;

/**
 * PluginFactory is responsible for creating instances of different types of plugins
 * (Application, Widget, IO, and Format) based on the plugin type and name provided.
 *
 * This factory uses a combination of {@link PluginType} and pluginName to dynamically create
 * the appropriate plugin class instances from TOML configuration files.
 * The specific names of the plugins are defined in {@link ConfigKeysConstants}.
 *
 * This factory is used within {@link PluginParser}, where plugin information parsed
 * from the TOML configuration file is used to create appropriate Plugin instances.
 */
public class PluginFactory {

    /**
     * Creates a plugin instance based on the plugin type and plugin name.
     *
     * @param type The type of the plugin (APPLICATION, WIDGET, IO, FORMAT).
     * @param pluginName The specific name of the plugin, defined in {@link ConfigKeysConstants}.
     * @param config The TOML configuration for the plugin.
     * @return A new instance of a Plugin class.
     * @throws IllegalArgumentException If the plugin type or name is not recognized.
     */
    public static Plugin createPlugin(PluginType type, String pluginName, Toml config) {
        switch (type) {
            case APPLICATION:
                return new ApplicationPlugin(config);
            case WIDGET:
                return createWidgetPlugin(pluginName, config);
            case IO:
                return createIOPlugin(pluginName, config);
            case FORMAT:
                return createFormatPlugin(pluginName, config);
            default:
                throw new IllegalArgumentException("Unknown plugin type: " + type);
        }
    }

    /**
     * Creates a Widget plugin instance based on its specific name.
     *
     * @param pluginName The name of the widget plugin, defined in {@link ConfigKeysConstants}.
     * @param config The TOML configuration for the widget plugin.
     * @return A new instance of a WidgetPlugin class.
     * @throws IllegalArgumentException If the widget plugin name is not recognized.
     */
    private static Plugin createWidgetPlugin(String pluginName, Toml config) {
        switch (pluginName) {
            case ConfigKeysConstants.WIDGET_TEXT_FIELD:
                return new TextFieldWidgetPlugin(config);
            case ConfigKeysConstants.WIDGET_BOOLEAN:
                return new BooleanWidgetPlugin(config);
            case ConfigKeysConstants.WIDGET_INTEGER:
                return new IntegerWidgetPlugin(config);
            case ConfigKeysConstants.WIDGET_FLOAT:
                return new FloatWidgetPlugin(config);
            default:
                throw new IllegalArgumentException("Unknown widget plugin: " + pluginName);
        }
    }

    /**
     * Creates an IO plugin instance based on its specific name.
     *
     * @param pluginName The name of the IO plugin, defined in {@link ConfigKeysConstants}.
     * @param config The TOML configuration for the IO plugin.
     * @return A new instance of an IOPlugin class.
     * @throws IllegalArgumentException If the IO plugin name is not recognized.
     */
    private static Plugin createIOPlugin(String pluginName, Toml config) {
        switch (pluginName) {
            case ConfigKeysConstants.IO_FILE:
                return new FileIOPlugin(config);
            default:
                throw new IllegalArgumentException("Unknown IO plugin: " + pluginName);
        }
    }

    /**
     * Creates a Format plugin instance based on its specific name.
     *
     * @param pluginName The name of the format plugin, defined in {@link ConfigKeysConstants}.
     * @param config The TOML configuration for the format plugin.
     * @return A new instance of a FormatPlugin class.
     * @throws IllegalArgumentException If the format plugin name is not recognized.
     */
    private static Plugin createFormatPlugin(String pluginName, Toml config) {
        switch (pluginName) {
            case ConfigKeysConstants.FORMAT_PROPERTY:
                return new PropertyFormatPlugin(config);
            case ConfigKeysConstants.FORMAT_JSON:
                return new JSONFormatPlugin(config);
            default:
                throw new IllegalArgumentException("Unknown format plugin: " + pluginName);
        }
    }
}