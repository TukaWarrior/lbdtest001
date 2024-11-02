package ch.hftm.config;

public class ConfigKeysConstants {
    // Constants general
    public static final String PLUGIN_TYPE = "type";
    public static final String PLUGIN_TYPE_APPLICATION = "application";
    public static final String PLUGIN_TYPE_WIDGETS = "widget_plugins";
    public static final String PLUGIN_TYPE_FORMAT = "format_plugin";
    public static final String PLUGIN_TYPE_IO = "io_plugin";

    // Application
    public static final String APPLICATION_TITLE = "window_title";
    public static final String APPLICATION_HEADER_TEXT = "header_text";
    public static final String APPLICATION_FOOTER_TEXT = "footer_text";
    public static final String APPLICATION_ENABLE_REMOTE = "enable_remote_access";
    public static final String APPLICATION_JAVAFX_ENABLED = "javafx_enabled";
    public static final String APPLICATION_PORT = "port";

    // Constants widget
    public static final String WIDGET_ID = "id";
    public static final String WIDGET_NAME = "name";
    public static final String WIDGET_TOOL_TIP = "toolTip";

    public static final String WIDGET_TEXT_FIELD = "textfield";
    public static final String WIDGET_PLACEHOLDER = "placeholder";
    public static final String WIDGET_TEXT_FIELD_MAX_LENGTH = "maxlength";

    public static final String WIDGET_BOOLEAN = "boolean";
    public static final String WIDGET_BOOLEAN_CHECKED = "checked";

    public static final String WIDGET_INTEGER = "integer";
    public static final String WIDGET_FLOAT = "float";
    public static final String WIDGET_MIN = "min";
    public static final String WIDGET_MAX = "max";
    public static final String WIDGET_STEP = "step";
    public static final String WIDGET_VALUE = "value";

    // Constants IO
    public static final String IO_FILE = "file";
    public static final String IO_FILE_READ_PATH = "readFilePath";
    public static final String IO_FILE_WRITE_PATH = "writeFilePath";

    public static final String IO_HTTPS = "https";

    public static final String IO_WEBSOCKET = "websocket";

    // Constants Format
    public static final String FORMAT_PROPERTY = "property";
    public static final String FORMAT_CHARSET = "charset";
    public static final String FORMAT_ALLOWED_PROPERTIES = "allowedProperties";
    public static final String FORMAT_HANDLE_OF_NOT_CONFIGURED_KEYS = "handleOfNotConfiguredKeys";
    public static final String FORMAT_HANDLE_OF_MISSING_KEYS = "handleOfMissingKeys";
    public static final String FORMAT_JSON_SCHEMA_FILE = "jsonSchemaFile";

    public static final String FORMAT_JSON = "json";

    public static final String FORMAT_CSV = "csv";

    public static final String FORMAT_TOML = "toml";

}
