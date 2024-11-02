package ch.hftm.plugin.application;

import ch.hftm.config.ConfigKeysConstants;
import ch.hftm.plugin.Plugin;
import ch.hftm.plugin.PluginType;

import com.moandjiezana.toml.Toml;

import java.util.HashSet;
import java.util.Set;

public class ApplicationPlugin extends Plugin {
    private String windowTitle;
    private String headerText;
    private String footerText;
    private boolean enableRemote;
    private boolean javaFxEnabled;
    private int port;

    // Constructor that sets the plugin type and parses the config
    public ApplicationPlugin(Toml config) {
        super(PluginType.APPLICATION);
        incompatibilityMap.put(ApplicationPlugin.class, 0);
        parseConfig(config);
    }

    @Override
    public void parseConfig(Toml config) {
        this.windowTitle = config.getString(ConfigKeysConstants.APPLICATION_TITLE);
        this.headerText = config.getString(ConfigKeysConstants.APPLICATION_HEADER_TEXT);
        this.footerText = config.getString(ConfigKeysConstants.APPLICATION_FOOTER_TEXT);
        this.enableRemote = config.getBoolean(ConfigKeysConstants.APPLICATION_ENABLE_REMOTE);
        this.javaFxEnabled = config.getBoolean(ConfigKeysConstants.APPLICATION_JAVAFX_ENABLED);
        this.port = config.getLong(ConfigKeysConstants.APPLICATION_PORT).intValue();
    }

    public String getWindowTitle() {
        return windowTitle;
    }

    public String getHeaderText() {
        return headerText;
    }

    public String getFooterText() {
        return footerText;
    }

    public boolean isEnableRemote() {
        return enableRemote;
    }

    public boolean isJavaFxEnabled() {
        return javaFxEnabled;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "ApplicationPlugin{" +
                "windowTitle='" + windowTitle + '\'' +
                ", headerText='" + headerText + '\'' +
                ", footerText='" + footerText + '\'' +
                ", enableRemote=" + enableRemote +
                ", javaFxEnabled=" + javaFxEnabled +
                ", port=" + port +
                '}';
    }

    public static Set<String> retrieveExpectedColumns() {
        Set<String> expectedColumns = new HashSet<>();
        expectedColumns.add(ConfigKeysConstants.APPLICATION_PORT);
        expectedColumns.add(ConfigKeysConstants.APPLICATION_ENABLE_REMOTE);
        expectedColumns.add(ConfigKeysConstants.APPLICATION_JAVAFX_ENABLED);
        expectedColumns.add(ConfigKeysConstants.APPLICATION_TITLE);
        expectedColumns.add(ConfigKeysConstants.APPLICATION_HEADER_TEXT);
        expectedColumns.add(ConfigKeysConstants.APPLICATION_FOOTER_TEXT);
        return expectedColumns;
    }
}
