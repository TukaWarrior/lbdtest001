package ch.hftm.plugin.widgets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.moandjiezana.toml.Toml;

import ch.hftm.config.ConfigKeysConstants;
import ch.hftm.plugin.widget.FloatWidgetPlugin;

public class FloatWidgetPluginTest {

    @Test
    public void testValidConfig() {
        // Valid configuration string
        String tomlString = "[widget]\n" +
                ConfigKeysConstants.WIDGET_ID + " = \"FloatWidgetId\"\n" +
                ConfigKeysConstants.WIDGET_NAME + " = \"FloatWidgetName\"\n" +
                ConfigKeysConstants.WIDGET_TOOL_TIP + " = \"A tooltip for the Float widget.\"\n" +
                ConfigKeysConstants.WIDGET_MIN + " = 1.0\n" +
                ConfigKeysConstants.WIDGET_MAX + " = 100.0\n" +
                ConfigKeysConstants.WIDGET_STEP + " = 0.5\n" +
                ConfigKeysConstants.WIDGET_VALUE + " = 50.0\n";

        Toml config = new Toml().read(tomlString);
        FloatWidgetPlugin floatWidgetPlugin = new FloatWidgetPlugin(config.getTable("widget"));

        Assertions.assertEquals("FloatWidgetId", floatWidgetPlugin.getId());
        Assertions.assertEquals("FloatWidgetName", floatWidgetPlugin.getName());
        Assertions.assertEquals("A tooltip for the Float widget.", floatWidgetPlugin.getToolTip());
        Assertions.assertEquals(1.0, (double) floatWidgetPlugin.getMin());
        Assertions.assertEquals(100.0, (double) floatWidgetPlugin.getMax());
        Assertions.assertEquals(0.5, (double) floatWidgetPlugin.getStep());
        Assertions.assertEquals(50.0, (double) floatWidgetPlugin.getValue());
    }

    @Test
    public void testToString() {
        // Configuration for testing toString
        String tomlString = "[widget]\n" +
                ConfigKeysConstants.WIDGET_ID + " = \"FloatWidgetId\"\n" +
                ConfigKeysConstants.WIDGET_NAME + " = \"FloatWidgetName\"\n" +
                ConfigKeysConstants.WIDGET_TOOL_TIP + " = \"A tooltip for the Float widget.\"\n" +
                ConfigKeysConstants.WIDGET_MIN + " = 1.0\n" +
                ConfigKeysConstants.WIDGET_MAX + " = 100.0\n" +
                ConfigKeysConstants.WIDGET_STEP + " = 0.5\n" +
                ConfigKeysConstants.WIDGET_VALUE + " = 50.0\n";

        Toml config = new Toml().read(tomlString);
        FloatWidgetPlugin floatWidgetPlugin = new FloatWidgetPlugin(config.getTable("widget"));

        String expectedString = "FloatWidgetPlugin{" +

                "min=1.0, max=100.0, step=0.5, value=50.0, " +
                "id='FloatWidgetId', name='FloatWidgetName', toolTip='A tooltip for the Float widget.'}";

        Assertions.assertEquals(expectedString, floatWidgetPlugin.toString());
    }
}
