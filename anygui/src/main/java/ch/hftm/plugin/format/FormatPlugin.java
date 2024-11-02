package ch.hftm.plugin.format;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ch.hftm.config.ConfigKeysConstants;
import ch.hftm.plugin.Plugin;
import ch.hftm.plugin.PluginType;
import ch.hftm.plugin.application.ApplicationPlugin;

public abstract class FormatPlugin extends Plugin {

    // Constructor that sets the plugin type to FORMAT
        public FormatPlugin() {
        super(PluginType.FORMAT);
            incompatibilityMap.put(FormatPlugin.class, 0);
    }
    abstract public String sendToCore(String content);

    abstract public String readFromCore(String json);

    public static Set<String> allowedPluginType() {
        Set<String> allowedTypes = new HashSet<>();
        allowedTypes.add(ConfigKeysConstants.FORMAT_PROPERTY);
        allowedTypes.add(ConfigKeysConstants.FORMAT_JSON);
        allowedTypes.add(ConfigKeysConstants.FORMAT_CSV);
        allowedTypes.add(ConfigKeysConstants.FORMAT_TOML);
        return allowedTypes;
    }

    public static Set<String> retrieveExpectedColumns() {
        Set<String> expectedColumns = new HashSet<>();
        expectedColumns.add(ConfigKeysConstants.PLUGIN_TYPE);
        return expectedColumns;
    }
}
