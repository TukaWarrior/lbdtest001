package ch.hftm.plugin.widget;

import ch.hftm.config.ConfigKeysConstants;
import com.moandjiezana.toml.Toml;

import ch.hftm.plugin.PluginType;

import java.util.Set;

public class FloatWidgetPlugin extends WidgetPlugin{
    private Double min;
    private Double max;
    private Double step;
    private double value;

    public FloatWidgetPlugin(Toml config) {
        super(PluginType.WIDGET);
        parseConfig(config);
    }

    public Double getMin() {
        return min;
    }

    public Double getMax() {
        return max;
    }

    public Double getStep() {
        return step;
    }

    public double getValue() {
        return value;
    }

    @Override
    public void parseConfig(Toml config) {
        this.id = config.getString(ConfigKeysConstants.WIDGET_ID);
        this.name = config.getString(ConfigKeysConstants.WIDGET_NAME, null);
        this.toolTip = config.getString(ConfigKeysConstants.WIDGET_TOOL_TIP);
        this.min = config.getDouble(ConfigKeysConstants.WIDGET_MIN, 0.0);
        this.max = config.getDouble(ConfigKeysConstants.WIDGET_MAX, 0.0);
        this.step = config.getDouble(ConfigKeysConstants.WIDGET_STEP, 0.01);
        this.value = config.getDouble(ConfigKeysConstants.WIDGET_VALUE, 0.0);
    }

    @Override
    public String toString() {
        return "FloatWidgetPlugin{" +
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
