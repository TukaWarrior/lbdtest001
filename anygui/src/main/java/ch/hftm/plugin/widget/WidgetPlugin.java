package ch.hftm.plugin.widget;

import ch.hftm.config.ConfigKeysConstants;
import ch.hftm.plugin.Plugin;
import ch.hftm.plugin.PluginType;

import java.util.HashSet;
import java.util.Set;

public abstract class WidgetPlugin extends Plugin {
    protected String id;
    protected String name;
    protected String toolTip;

    // Constructor that initializes the plugin with its specific type
    public WidgetPlugin(PluginType type) {
        super(type);
    }

    public String getName() {
        return name;
    }

    public String getToolTip() {
        return toolTip;
    }

    public String getId() {
        return id;
    }

    public static Set<String> allowedPluginType() {
        Set<String> allowedTypes = new HashSet<>();
        allowedTypes.add(ConfigKeysConstants.WIDGET_TEXT_FIELD);
        allowedTypes.add(ConfigKeysConstants.WIDGET_BOOLEAN);
        allowedTypes.add(ConfigKeysConstants.WIDGET_INTEGER);
        allowedTypes.add(ConfigKeysConstants.WIDGET_FLOAT);
        return allowedTypes;
    }

    public static Set<String> retrieveExpectedColumns() {
        Set<String> expectedColumns = new HashSet<>();
        expectedColumns.add(ConfigKeysConstants.PLUGIN_TYPE);
        expectedColumns.add(ConfigKeysConstants.WIDGET_ID);
        expectedColumns.add(ConfigKeysConstants.WIDGET_NAME);
        expectedColumns.add(ConfigKeysConstants.WIDGET_TOOL_TIP);
        return expectedColumns;
    }
}
