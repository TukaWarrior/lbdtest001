package ch.hftm.plugin.io;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import ch.hftm.config.ConfigKeysConstants;
import ch.hftm.plugin.Plugin;
import ch.hftm.plugin.PluginType;
import ch.hftm.plugin.format.FormatPlugin;
import ch.hftm.plugin.widget.WidgetPlugin;

public abstract class IOPlugin extends Plugin {

    // Constructor that sets the plugin type to IO
        public IOPlugin() {
            super(PluginType.IO);
            incompatibilityMap.put(IOPlugin.class, 0);
    }
    // Method to read raw data from the source
    public abstract String readRawData() throws IOException;

    // Method to write raw data to the source
    public abstract void writeRawData(String data) throws IOException;

    public static Set<String> allowedPluginType() {
        Set<String> allowedTypes = new HashSet<>();
        allowedTypes.add(ConfigKeysConstants.IO_FILE);
        allowedTypes.add(ConfigKeysConstants.IO_HTTPS);
        allowedTypes.add(ConfigKeysConstants.IO_WEBSOCKET);
        return allowedTypes;
    }

    public static Set<String> retrieveExpectedColumns() {
        Set<String> expectedColumns = new HashSet<>();
        expectedColumns.add(ConfigKeysConstants.PLUGIN_TYPE);
        return expectedColumns;
    }
}
