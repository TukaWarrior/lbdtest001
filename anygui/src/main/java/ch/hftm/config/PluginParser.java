package ch.hftm.config;

import com.moandjiezana.toml.Toml;

import ch.hftm.plugin.Plugin;
import ch.hftm.plugin.PluginFactory;
import ch.hftm.plugin.PluginType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * PluginParser is responsible for reading and parsing the TOML configuration file to
 * instantiate plugins using the {@link PluginFactory} and store them in the {@link PluginRegistry}.
 *
 * The configuration file defines different plugin types such as Application, IO, Format, and Widget plugins.
 * This class reads the configuration, creates plugin instances accordingly, and registers them
 * within the {@link PluginRegistry} for global access.
 *
 * The plugin creation is handled by the {@link PluginFactory}, which selects and creates the
 * appropriate plugin type based on the configuration provided in the TOML file.
 *
 * This class supports parsing plugins from both an external TOML file or a default configuration
 * file embedded in the application resources.
 */
public class PluginParser {
    private static final Logger log = LoggerFactory.getLogger(PluginParser.class);
    private static final String DEFAULT_CONFIG_FILE_PATH = "anygui_config.toml";
    private Toml toml;

    /**
     * Constructs a PluginParser instance, initializing it with the given configuration file path.
     */
    public PluginParser() {
        // Check for external "anygui_config.toml" file in the current directory
        File externalConfigFile = new File(DEFAULT_CONFIG_FILE_PATH);

        if (externalConfigFile.exists() && externalConfigFile.isFile()) {
            log.info("External configuration file found at {}. Loading plugins from file.", externalConfigFile.getAbsolutePath());
            this.toml = new Toml().read(externalConfigFile);
        } else {
            log.warn("External TOML config file not found. Attempting to load from resources.");

            // Load configuration file from application resources
            try (InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(DEFAULT_CONFIG_FILE_PATH)) {
                if (resourceStream == null) {
                    throw new IllegalArgumentException("No configuration file found in resources.");
                }
                this.toml = new Toml().read(resourceStream);
                log.info("Configuration loaded from application resources.");
            } catch (Exception e) {
                log.error("Failed to load configuration from resources.", e);
                throw new RuntimeException("Error loading configuration file.");
            }
        }
    }

    /**
     * Parses the plugins from the TOML configuration file and registers them in the {@link PluginRegistry}.
     *
     * This method reads the plugin definitions from the configuration file, creates the necessary plugin
     * instances using the {@link PluginFactory}, and registers them in the {@link PluginRegistry}. It supports
     * parsing of various plugin types such as Application, IO, Format, and Widget plugins.
     *
     * @return The {@link PluginRegistry} containing all registered plugins.
     */
    public PluginRegistry parsePlugins() {
        log.info("*************************  Plugin Parsing and Validation started ***********************");
        PluginRegistry pluginRegistry = PluginRegistry.getInstance();
        toml = getToml();
        PluginValidator validator = new PluginValidator(toml);

        // Parse and create Application plugin
        try {
            log.info("Starting application plugin validation and parsing...");
            Map<String, Object> applicationConfig = toml.getTable(ConfigKeysConstants.PLUGIN_TYPE_APPLICATION).toMap();
            validator.validate(PluginType.APPLICATION);
            Plugin applicationPlugin = PluginFactory.createPlugin(PluginType.APPLICATION, null, toml.getTable(ConfigKeysConstants.PLUGIN_TYPE_APPLICATION));
            pluginRegistry.registerPlugin(applicationPlugin);
            log.info("Application plugin registered.");
        } catch (Exception e) {
            log.error("Failed to parse Application plugin configuration.", e);
        }

        // Parse and create IO plugin
        try {
            log.info("Starting io plugin validation and parsing...");
            Map<String, Object> ioConfig = toml.getTable(ConfigKeysConstants.PLUGIN_TYPE_IO).toMap();
            validator.validate(PluginType.IO);
            String ioType = (String) ioConfig.get(ConfigKeysConstants.PLUGIN_TYPE);
            log.info("{} plugin type found.", ioType);
            Plugin ioPlugin = PluginFactory.createPlugin(PluginType.IO, ioType, toml.getTable(ConfigKeysConstants.PLUGIN_TYPE_IO));
            pluginRegistry.registerPlugin(ioPlugin);
            log.info("IO plugin registered.");
        } catch (Exception e) {
            log.error("Failed to parse IO plugin configuration.", e);
        }

        // Parse and create Format plugin
        try {
            log.info("Starting format plugin validation and parsing...");
            Map<String, Object> formatConfig = toml.getTable(ConfigKeysConstants.PLUGIN_TYPE_FORMAT).toMap();
            validator.validate(PluginType.FORMAT);
            String formatType = (String) formatConfig.get(ConfigKeysConstants.PLUGIN_TYPE);
            log.info("{} plugin type found.", formatType);
            Plugin formatPlugin = PluginFactory.createPlugin(PluginType.FORMAT, formatType, toml.getTable(ConfigKeysConstants.PLUGIN_TYPE_FORMAT));
            pluginRegistry.registerPlugin(formatPlugin);
            log.info("Format plugin registered.");
        } catch (Exception e) {
            log.error("Failed to parse Format plugin configuration.", e);
        }

        // Parse and create Widget plugins
        List<Toml> widgetConfigs = toml.getTables(ConfigKeysConstants.PLUGIN_TYPE_WIDGETS);
        log.info("Found {} widget plugins.", widgetConfigs.size());
        validator.validate(PluginType.WIDGET);
        for (Toml widgetConfig : widgetConfigs) {
            try {
                String widgetType = widgetConfig.getString(ConfigKeysConstants.PLUGIN_TYPE);
                log.info("{} widget type found.", widgetType);
                Plugin widgetPlugin = PluginFactory.createPlugin(PluginType.WIDGET, widgetType, widgetConfig);
                pluginRegistry.registerPlugin(widgetPlugin);
            } catch (Exception e) {
                log.error("Failed to parse Widget plugin configuration for a widget.", e);
            }
        }
        log.info("Widget plugin registered.");
        log.info("*************************  Plugin Parsing and Validation completed ***********************");
        return pluginRegistry;
    }

    /**
     * Loads and returns the TOML configuration file.
     *
     * This method attempts to load the TOML configuration file from an external file if available,
     * or falls back to a default configuration file from the application's resources.
     *
     * @return The parsed {@link Toml} object containing the configuration data.
     */
    Toml getToml() {
        return toml;
    }
}
