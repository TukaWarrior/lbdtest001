package ch.hftm.plugin.widget;

import ch.hftm.config.ConfigKeysConstants;
import ch.hftm.plugin.PluginType;

import com.moandjiezana.toml.Toml;

import java.util.Set;

public class BooleanWidgetPlugin extends WidgetPlugin {
    private Boolean checked;

    public BooleanWidgetPlugin(Toml config) {
        super(PluginType.WIDGET);
        parseConfig(config);
    }

    @Override
    public void parseConfig(Toml config) {
        this.id = config.getString(ConfigKeysConstants.WIDGET_ID);
        this.name = config.getString(ConfigKeysConstants.WIDGET_NAME, null);
        this.toolTip = config.getString(ConfigKeysConstants.WIDGET_TOOL_TIP);
        if (config.contains(ConfigKeysConstants.WIDGET_BOOLEAN_CHECKED)) {
            this.checked = config.getBoolean(ConfigKeysConstants.WIDGET_BOOLEAN_CHECKED);
        } else {
            this.checked = null;
        }
    }

    @Override
    public String toString() {
        return "BooleanWidgetPlugin{" +
                "checked=" + checked +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", toolTip='" + toolTip + '\'' +
                '}';
    }

    public boolean isChecked() {
        return this.checked;
    }

    public static Set<String> retrieveExpectedColumns() {
        Set<String> expectedColumns = WidgetPlugin.retrieveExpectedColumns();
        expectedColumns.add(ConfigKeysConstants.WIDGET_BOOLEAN_CHECKED);
        return expectedColumns;
    }
}
