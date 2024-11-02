package ch.hftm.plugin.widgets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.moandjiezana.toml.Toml;

import ch.hftm.config.ConfigKeysConstants;
import ch.hftm.plugin.widget.IntegerWidgetPlugin;

public class IntegerWidgetPluginTest {

    @Test
    public void testValidConfig() {
        // Valid configuration string
        String tomlString = "[widget]\n" +
                ConfigKeysConstants.WIDGET_ID + " = \"IntegerWidgetId\"\n" +
                ConfigKeysConstants.WIDGET_NAME + " = \"IntegerWidgetName\"\n" +
                ConfigKeysConstants.WIDGET_TOOL_TIP + " = \"A tooltip for the Integer widget.\"\n" +
                ConfigKeysConstants.WIDGET_MIN + " = 1\n" +
                ConfigKeysConstants.WIDGET_MAX + " = 100\n" +
                ConfigKeysConstants.WIDGET_STEP + " = 5\n" +
                ConfigKeysConstants.WIDGET_VALUE + " = 50\n";

        Toml config = new Toml().read(tomlString);
        IntegerWidgetPlugin integerWidgetPlugin = new IntegerWidgetPlugin(config.getTable("widget"));

        Assertions.assertEquals("IntegerWidgetId", integerWidgetPlugin.getId());
        Assertions.assertEquals("IntegerWidgetName", integerWidgetPlugin.getName());
        Assertions.assertEquals("A tooltip for the Integer widget.", integerWidgetPlugin.getToolTip());

        // Convert values to long and assert
        Assertions.assertEquals(1L, (long) integerWidgetPlugin.getMin());
        Assertions.assertEquals(100L, (long) integerWidgetPlugin.getMax());
        Assertions.assertEquals(5L, (long) integerWidgetPlugin.getStep());
        Assertions.assertEquals(50L, (long) integerWidgetPlugin.getValue());
    }

    @Test
    public void testToString() {

        // Configuration for testing toString
        String tomlString = "[widget]\n" +
                ConfigKeysConstants.WIDGET_ID + " = \"IntegerWidgetId\"\n" +
                ConfigKeysConstants.WIDGET_NAME + " = \"IntegerWidgetName\"\n" +
                ConfigKeysConstants.WIDGET_TOOL_TIP + " = \"A tooltip for the Integer widget.\"\n" +
                ConfigKeysConstants.WIDGET_MIN + " = 1\n" +
                ConfigKeysConstants.WIDGET_MAX + " = 100\n" +
                ConfigKeysConstants.WIDGET_STEP + " = 5\n" +
                ConfigKeysConstants.WIDGET_VALUE + " = 50\n";

        Toml config = new Toml().read(tomlString);
        IntegerWidgetPlugin integerWidgetPlugin = new IntegerWidgetPlugin(config.getTable("widget"));

        String expectedString = "IntegerWidgetPlugin{" +

                "min=1, max=100, step=5, value=50, " +
                "id='IntegerWidgetId', name='IntegerWidgetName', toolTip='A tooltip for the Integer widget.'}";

        Assertions.assertEquals(expectedString, integerWidgetPlugin.toString());
    }
}