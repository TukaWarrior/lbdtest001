package ch.hftm.config;

import ch.hftm.plugin.PluginType;
import ch.hftm.plugin.application.ApplicationPlugin;
import ch.hftm.plugin.format.FormatPlugin;
import ch.hftm.plugin.format.PropertyFormatPlugin;
import ch.hftm.plugin.io.FileIOPlugin;
import ch.hftm.plugin.io.IOPlugin;
import ch.hftm.plugin.widget.*;
import com.moandjiezana.toml.Toml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The {@code PluginValidator} class is responsible for validating plugin configurations
 * defined in a TOML file. This class checks that the correct types and values are provided
 * for various plugin types, including application, IO, format, and widget plugins.
 *
 * <p>
 * The class ensures that all required fields are present, verifies that values fall
 * within acceptable ranges, and checks that no unexpected fields are present in the
 * configuration. The validation process will throw {@link IllegalArgumentException}s
 * for any discrepancies found.
 * </p>
 *
 * <p>
 * It utilizes the {@link Toml} library for parsing and retrieving configuration data.
 * </p>
 *
 * @see Toml
 * @see PluginType
 * @see ApplicationPlugin
 * @see IOPlugin
 * @see FormatPlugin
 * @see WidgetPlugin
 */
public class PluginValidator {
    private static final Logger log = LoggerFactory.getLogger(PluginValidator.class);
    private final Toml config;

    public PluginValidator(Toml config) {
        this.config = config;
        // validatePluginCounts();
    }

    /**
     * Validates the counts of various plugins defined in the TOML configuration.
     * Ensures that the application plugin exists exactly once,
     * the IO plugin exists at most once, and the format plugin exists at most once.
     *
     * @throws IllegalArgumentException if the counts of plugins do not meet the specified criteria
     */
    private void validatePluginCounts() {
        log.info("Validating TOML file plugin counts!");

        // Validate Application Plugin - Must exist exactly once
        if (config.getTable(ConfigKeysConstants.PLUGIN_TYPE_APPLICATION) == null) {
            throw new IllegalArgumentException("No application plugins found. There should be one application plugin.");
        } else if (config.getTables(ConfigKeysConstants.PLUGIN_TYPE_APPLICATION) != null &&
                config.getTables(ConfigKeysConstants.PLUGIN_TYPE_APPLICATION).size() > 1) {
            throw new IllegalArgumentException("There should be only one Application plugin defined.");
        }

        log.info("Validating plugin counts finished and looks good! Continuing with the parser!");
    }

    /**
     * Validates the specified plugin type by delegating to the appropriate validation method.
     *
     * @param type the type of the plugin to validate
     * @throws IllegalArgumentException if the plugin type is unknown
     */
    public void validate(PluginType type) {
        switch (type) {
            case APPLICATION:
                validateApplicationPlugin();
                break;
            case IO:
                validateIOPlugin();
                break;
            case FORMAT:
                validateFormatPlugin();
                break;
            case WIDGET:
                validateWidgetPlugin();
                break;
            default:
                throw new IllegalArgumentException("Unknown plugin type: " + type);
        }
    }

    /**
     * Validates the widget plugins defined in the TOML configuration.
     *
     * <p>
     * This method checks that all required fields are present for each widget plugin,
     * validates the types of the fields, and ensures no unexpected fields are present.
     * </p>
     *
     * @throws IllegalArgumentException if any widget plugin is invalid
     */
    private void validateWidgetPlugin() {
        List<Toml> widgetPlugins = config.getTables(ConfigKeysConstants.PLUGIN_TYPE_WIDGETS);
        for (Toml widgetPlugin : widgetPlugins) {
            String type = widgetPlugin.getString(ConfigKeysConstants.PLUGIN_TYPE);
            validateString(type, ConfigKeysConstants.PLUGIN_TYPE);

            Set<String> allowedValues = WidgetPlugin.allowedPluginType();
            if (!allowedValues.contains(type)) {
                throw new IllegalArgumentException("Invalid value for 'type': " + type + ". Allowed values are: " + allowedValues);
            }

            validateString(widgetPlugin.getString(ConfigKeysConstants.WIDGET_ID), ConfigKeysConstants.WIDGET_ID);
            validateString(widgetPlugin.getString(ConfigKeysConstants.WIDGET_NAME), ConfigKeysConstants.WIDGET_NAME);
            validateString(widgetPlugin.getString(ConfigKeysConstants.WIDGET_TOOL_TIP), ConfigKeysConstants.WIDGET_TOOL_TIP);

            // Type-specific validation
            switch (type) {
                case ConfigKeysConstants.WIDGET_TEXT_FIELD:
                    // Additional fields for textfield
                    validateString(widgetPlugin.getString(ConfigKeysConstants.WIDGET_PLACEHOLDER), ConfigKeysConstants.WIDGET_PLACEHOLDER);
                    Long maxLengthValue = widgetPlugin.getLong(ConfigKeysConstants.WIDGET_TEXT_FIELD_MAX_LENGTH);
                    if (maxLengthValue != null && (maxLengthValue <= 0)) {
                        throw new IllegalArgumentException("'maxlength' must be a positive integer for textfield.");
                    }
                    break;

                case ConfigKeysConstants.WIDGET_BOOLEAN:
                    // Additional fields for boolean
                    if(!widgetPlugin.contains(ConfigKeysConstants.WIDGET_BOOLEAN_CHECKED)) {
                        throw new IllegalArgumentException("Boolean field Checked is required and cannot be null if provided.");
                    }
                    checkBooleanValue(widgetPlugin, ConfigKeysConstants.WIDGET_BOOLEAN_CHECKED);
                    break;

                case ConfigKeysConstants.WIDGET_INTEGER:
                    validateIntegerFields(widgetPlugin);
                    break;

                case ConfigKeysConstants.WIDGET_FLOAT:
                    validateFloatFields(widgetPlugin);
                    break;

                default:
                    throw new IllegalArgumentException("Unknown widget type: " + type);
            }

            Set<String> expectedKeys = retrieveExpectedColumnsForWidgetPlugin(type);
            logUnexpectedColumns(widgetPlugin, expectedKeys);
        }
    }

    /**
     * Validates the format plugin defined in the TOML configuration.
     *
     * <p>
     * This method checks that all required fields are present, validates the types,
     * and ensures no unexpected fields are present for format plugins.
     * </p>
     *
     * @throws IllegalArgumentException if any format plugin is invalid
     */
    private void validateFormatPlugin() {
        Toml formatPlugin = config.getTable(ConfigKeysConstants.PLUGIN_TYPE_FORMAT);
        String type = formatPlugin.getString(ConfigKeysConstants.PLUGIN_TYPE);
        validateString(type, ConfigKeysConstants.PLUGIN_TYPE);

        Set<String> allowedValues = FormatPlugin.allowedPluginType();
        if (!allowedValues.contains(type)) {
            throw new IllegalArgumentException("Invalid value for 'type': " + type + ". Allowed values are: " + allowedValues);
        }

        // Additional validation for type "property"
        if (type.equals(ConfigKeysConstants.FORMAT_PROPERTY)) {
            // Validate 'charset' field
            String charset = formatPlugin.getString(ConfigKeysConstants.FORMAT_CHARSET);
            validateString(charset, ConfigKeysConstants.FORMAT_CHARSET);
            if (!Charset.isSupported(charset)) {
                throw new IllegalArgumentException("Invalid value for 'charset': " + charset + ". Allowed values are: supported charset: " + Charset.defaultCharset().name() );
            }

            // Validate 'allowedProperties' field
            List<String> allowedProperties = formatPlugin.getList(ConfigKeysConstants.FORMAT_ALLOWED_PROPERTIES);
            if (allowedProperties == null || allowedProperties.isEmpty()) {
                throw new IllegalArgumentException("'allowedProperties' must be a non-empty list of strings.");
            }

            for (String property : allowedProperties) {
                if (property == null || property.isEmpty()) {
                    throw new IllegalArgumentException("Each property in 'allowedProperties' must be a non-empty string.");
                }
            }

            // Validate 'handleOfNotConfiguredKeys' field
            String handleOfNotConfiguredKeys = formatPlugin.getString(ConfigKeysConstants.FORMAT_HANDLE_OF_NOT_CONFIGURED_KEYS);
            validateString(handleOfNotConfiguredKeys, ConfigKeysConstants.FORMAT_HANDLE_OF_NOT_CONFIGURED_KEYS);
            Set<String> validHandleOfNotConfiguredKeys = Set.of("delete", "keepAndWarn"); // TODO --> change to the enum list. Enum is already created, but its private
            if (!validHandleOfNotConfiguredKeys.contains(handleOfNotConfiguredKeys)) {
                throw new IllegalArgumentException("Invalid value for 'handleOfNotConfiguredKeys': " + handleOfNotConfiguredKeys + ". Allowed values are: " + validHandleOfNotConfiguredKeys);
            }

            // Validate 'handleOfMissingKeys' field
            String handleOfMissingKeys = formatPlugin.getString(ConfigKeysConstants.FORMAT_HANDLE_OF_MISSING_KEYS);
            validateString(handleOfMissingKeys, ConfigKeysConstants.FORMAT_HANDLE_OF_MISSING_KEYS);
            Set<String> validHandleOfMissingKeys = Set.of("warn", "exception"); // TODO --> change to the enum list. Enum is already created, but its private
            if (!validHandleOfMissingKeys.contains(handleOfMissingKeys)) {
                throw new IllegalArgumentException("Invalid value for 'handleOfMissingKeys': " + handleOfMissingKeys + ". Allowed values are: " + validHandleOfMissingKeys);
            }
        }

        Set<String> expectedKeys = retrieveExpectedColumnsForFormatPlugin(type);
        logUnexpectedColumns(formatPlugin, expectedKeys);
    }

    /**
     * Validates the IO plugin defined in the TOML configuration.
     *
     * <p>
     * This method checks that all required fields are present and valid for the IO plugin.
     * </p>
     *
     * @throws IllegalArgumentException if any IO plugin is invalid
     */
    private void validateIOPlugin() {
        Toml ioPlugin = config.getTable(ConfigKeysConstants.PLUGIN_TYPE_IO);
        String type = ioPlugin.getString(ConfigKeysConstants.PLUGIN_TYPE);
        validateString(type, ConfigKeysConstants.PLUGIN_TYPE);

        Set<String> allowedValues = IOPlugin.allowedPluginType();
        if (!allowedValues.contains(type)) {
            throw new IllegalArgumentException("Invalid value for 'type': " + type + ". Allowed values are: " + allowedValues);
        }

        if (type.equals(ConfigKeysConstants.IO_FILE)) {
            String readFilePath = ioPlugin.getString(ConfigKeysConstants.IO_FILE_READ_PATH);
            String writeFilePath = ioPlugin.getString(ConfigKeysConstants.IO_FILE_WRITE_PATH);

            validateString(readFilePath, ConfigKeysConstants.IO_FILE_READ_PATH);
            validateString(writeFilePath, ConfigKeysConstants.IO_FILE_WRITE_PATH);
        }

        Set<String> expectedKeys = retrieveExpectedColumnsForIOPlugin(type);
        logUnexpectedColumns(ioPlugin, expectedKeys);
    }

    /**
     * Validates the application plugin defined in the TOML configuration.
     *
     * <p>
     * This method checks that all required fields are present, validates their types,
     * and ensures no unexpected fields are present.
     * </p>
     *
     * @throws IllegalArgumentException if the application plugin is invalid
     */
    private void validateApplicationPlugin() {
        Toml applicationConfig = config.getTable(ConfigKeysConstants.PLUGIN_TYPE_APPLICATION);
        if (!applicationConfig.contains(ConfigKeysConstants.APPLICATION_PORT)) {
            throw new IllegalArgumentException("Application plugin does not contain application port. It is required.");
        }

        Long portValue = applicationConfig.getLong(ConfigKeysConstants.APPLICATION_PORT);
        if (portValue < 1 || portValue > 65535) {
            throw new IllegalArgumentException("The 'port' must be a valid port number (1-65535).");
        }

        checkBooleanValue(applicationConfig, ConfigKeysConstants.APPLICATION_ENABLE_REMOTE);
        checkBooleanValue(applicationConfig, ConfigKeysConstants.APPLICATION_JAVAFX_ENABLED);

        Set<String> expectedKeys = ApplicationPlugin.retrieveExpectedColumns();
        logUnexpectedColumns(applicationConfig, expectedKeys);
    }

    private Set<String> retrieveExpectedColumnsForIOPlugin(String pluginName) {
        if (pluginName.equals(ConfigKeysConstants.IO_FILE)) {
            return FileIOPlugin.retrieveExpectedColumns();
        } else {
            return IOPlugin.retrieveExpectedColumns();
        }
    }

    private Set<String> retrieveExpectedColumnsForFormatPlugin(String pluginName) {
        if (pluginName.equals(ConfigKeysConstants.FORMAT_PROPERTY)) {
            return PropertyFormatPlugin.retrieveExpectedColumns();
        } else {
            return FormatPlugin.retrieveExpectedColumns();
        }
    }

    private Set<String> retrieveExpectedColumnsForWidgetPlugin(String pluginName) {
        return switch (pluginName) {
            case ConfigKeysConstants.WIDGET_TEXT_FIELD -> TextFieldWidgetPlugin.retrieveExpectedColumns();
            case ConfigKeysConstants.WIDGET_BOOLEAN -> BooleanWidgetPlugin.retrieveExpectedColumns();
            case ConfigKeysConstants.WIDGET_INTEGER -> IntegerWidgetPlugin.retrieveExpectedColumns();
            case ConfigKeysConstants.WIDGET_FLOAT -> FloatWidgetPlugin.retrieveExpectedColumns();
            default -> WidgetPlugin.retrieveExpectedColumns();
        };
    }

    private void validateIntegerFields(Toml widgetPlugin) {
        Long min = widgetPlugin.getLong(ConfigKeysConstants.WIDGET_MIN);
        Long max = widgetPlugin.getLong(ConfigKeysConstants.WIDGET_MAX);
        Long value = widgetPlugin.getLong(ConfigKeysConstants.WIDGET_VALUE);
        Long step = widgetPlugin.getLong(ConfigKeysConstants.WIDGET_STEP);

        if (min == null || max == null || value == null || step == null) {
            throw new IllegalArgumentException("Fields 'min', 'max', 'value', and 'step' are required and must be present for integer widget.");
        }

        // Additional checks can be added here, e.g., min < max, etc.
    }

    private void validateFloatFields(Toml widgetPlugin) {
        Double min = widgetPlugin.getDouble(ConfigKeysConstants.WIDGET_MIN);
        Double max = widgetPlugin.getDouble(ConfigKeysConstants.WIDGET_MAX);
        Double value = widgetPlugin.getDouble(ConfigKeysConstants.WIDGET_VALUE);
        Double step = widgetPlugin.getDouble(ConfigKeysConstants.WIDGET_STEP);

        if (min == null || max == null || value == null || step == null) {
            throw new IllegalArgumentException("Fields 'min', 'max', 'value', and 'step' are required and must be present for float widget.");
        }
    }

    private void checkBooleanValue(Toml config, String key) {
        Map<String, Object> values = config.toMap();

        if (values.containsKey(key)) {
            Object value = values.get(key);

            if (!(value instanceof Boolean)) {
                throw new IllegalArgumentException("'" + key + "' must be a boolean value.");
            }
        }
    }

    private void validateString(String value, String fieldName) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("'" + fieldName + "' is required and cannot be empty.");
        }
    }

    private static void logUnexpectedColumns(Toml config, Set<String> expectedKeys) {
        Set<String> actualKeys = new HashSet<>(config.toMap().keySet());

        // Remove expected keys from the actual keys set
        actualKeys.removeAll(expectedKeys);

        // Log any unexpected keys
        if (!actualKeys.isEmpty()) {
            for (String unexpectedKey : actualKeys) {
                log.warn("Unexpected key found for plugins: {}", unexpectedKey);
            }
        }
    }
}
