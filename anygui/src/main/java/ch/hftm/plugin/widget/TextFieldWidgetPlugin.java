package ch.hftm.plugin.widget;

import ch.hftm.config.ConfigKeysConstants;
import ch.hftm.plugin.PluginType;

import ch.hftm.plugin.application.ApplicationPlugin;
import com.moandjiezana.toml.Toml;

import java.util.HashSet;
import java.util.Set;

public class TextFieldWidgetPlugin extends WidgetPlugin {
    private Integer maxLength;
    private String placeholderText;

    public TextFieldWidgetPlugin(Toml config) {
        super(PluginType.WIDGET);
        parseConfig(config);
    }

    @Override
    public void parseConfig(Toml config) {
        this.id = config.getString(ConfigKeysConstants.WIDGET_ID);
        this.name = config.getString(ConfigKeysConstants.WIDGET_NAME, null);
        this.toolTip = config.getString(ConfigKeysConstants.WIDGET_TOOL_TIP);
        this.placeholderText = config.getString(ConfigKeysConstants.WIDGET_PLACEHOLDER);
        if (config.contains(ConfigKeysConstants.WIDGET_TEXT_FIELD_MAX_LENGTH)) {
            this.maxLength =  Math.toIntExact(config.getLong(ConfigKeysConstants.WIDGET_TEXT_FIELD_MAX_LENGTH));
        } else {
            this.maxLength = null;
        }
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public String getPlaceholderText() {
        return placeholderText;
    }

    @Override
    public String toString() {
        return "TextFieldWidgetPlugin{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", toolTip='" + toolTip + '\'' +
                ", placeholderText='" + placeholderText + '\'' +
                ", maxLength=" + maxLength +
                '}';
    }

    public static Set<String> retrieveExpectedColumns() {
        Set<String> expectedColumns = WidgetPlugin.retrieveExpectedColumns();
        expectedColumns.add(ConfigKeysConstants.WIDGET_PLACEHOLDER);
        expectedColumns.add(ConfigKeysConstants.WIDGET_TEXT_FIELD_MAX_LENGTH);
        return expectedColumns;
    }
}
