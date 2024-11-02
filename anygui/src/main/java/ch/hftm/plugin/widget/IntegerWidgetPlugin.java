package ch.hftm.plugin.widget;

import ch.hftm.config.ConfigKeysConstants;
import ch.hftm.plugin.PluginType;
import com.moandjiezana.toml.Toml;

import java.util.Set;

public class IntegerWidgetPlugin extends WidgetPlugin {
    private Integer min;
    private Integer max;
    private Integer step;
    private int value;

    public IntegerWidgetPlugin(Toml config) {
        super(PluginType.WIDGET);
        parseConfig(config);
    }

    public Integer getMin() {
        return min;
    }

    public Integer getMax() {
        return max;
    }

    public Integer getStep() {
        return step;
    }

    public int getValue() {
        return value;
    }

    @Override
    public void parseConfig(Toml config) {
        this.id = config.getString(ConfigKeysConstants.WIDGET_ID);
        this.name = config.getString(ConfigKeysConstants.WIDGET_NAME, null);
        this.toolTip = config.getString(ConfigKeysConstants.WIDGET_TOOL_TIP);
        this.min = Math.toIntExact(config.getLong(ConfigKeysConstants.WIDGET_MIN, 0L));
        this.max = Math.toIntExact(config.getLong(ConfigKeysConstants.WIDGET_MAX, 0L));
        this.step = Math.toIntExact(config.getLong(ConfigKeysConstants.WIDGET_STEP, 1L));
        this.value = Math.toIntExact(config.getLong(ConfigKeysConstants.WIDGET_VALUE, 10L));
    }

    @Override
    public String toString() {
        return "IntegerWidgetPlugin{" +
                "min=" + min +
                ", max=" + max +
                ", step=" + step +
                ", value=" + value +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", toolTip='" + toolTip + '\'' +
                '}';
    }

    public static Set<String> retrieveExpectedColumns() {
        Set<String> expectedColumns = WidgetPlugin.retrieveExpectedColumns();
        expectedColumns.add(ConfigKeysConstants.WIDGET_MIN);
        expectedColumns.add(ConfigKeysConstants.WIDGET_MAX);
        expectedColumns.add(ConfigKeysConstants.WIDGET_STEP);
        expectedColumns.add(ConfigKeysConstants.WIDGET_VALUE);
        return expectedColumns;
    }
}
