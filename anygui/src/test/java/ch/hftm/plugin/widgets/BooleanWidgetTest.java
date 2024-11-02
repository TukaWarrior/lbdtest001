package ch.hftm.plugin.widgets;

import ch.hftm.config.ConfigKeysConstants;
import ch.hftm.plugin.widget.BooleanWidgetPlugin;

import com.moandjiezana.toml.Toml;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BooleanWidgetTest {

    @Test
    public void testValidConfig() {
        // Valid configuration string
        String tomlString = "[widget]\n" +
                ConfigKeysConstants.WIDGET_ID + " = \"booleanWidgetId\"\n" +
                ConfigKeysConstants.WIDGET_NAME + " = \"BooleanWidgetName\"\n" +
                ConfigKeysConstants.WIDGET_TOOL_TIP + " = \"A tooltip for the boolean widget.\"\n" +
                ConfigKeysConstants.WIDGET_BOOLEAN_CHECKED + " = true\n";

        Toml config = new Toml().read(tomlString);
        BooleanWidgetPlugin booleanWidgetPlugin = new BooleanWidgetPlugin(config.getTable("widget"));

        Assertions.assertEquals("booleanWidgetId", booleanWidgetPlugin.getId());
        Assertions.assertEquals("BooleanWidgetName", booleanWidgetPlugin.getName());
        Assertions.assertTrue(booleanWidgetPlugin.isChecked());
    }

    /*@Test
    public void testMissingIdThrowsException() {
        // Configuration with ID missing
        String tomlString = "[widget]\n" +
                ConfigKeysConstants.WIDGET_NAME + " = \"BooleanWidgetName\"\n" +
                ConfigKeysConstants.WIDGET_TOOL_TIP + " = \"A tooltip for the boolean widget.\"\n" +
                ConfigKeysConstants.WIDGET_BOOLEAN_CHECKED + " = true\n";

        Toml config = new Toml().read(tomlString);

        Assertions.assertThrows(IllegalArgumentException.class, () -> new BooleanWidgetPlugin(config.getTable("widget")),
                "Boolean field Id is required and cannot be null or empty.");
    }*/

    /*@Test
    public void testEmptyIdThrowsException() {
        // Configuration with empty ID
        String tomlString = "[widget]\n" +
                ConfigKeysConstants.WIDGET_ID + " = \"\"\n" +
                ConfigKeysConstants.WIDGET_NAME + " = \"BooleanWidgetName\"\n" +
                ConfigKeysConstants.WIDGET_TOOL_TIP + " = \"A tooltip for the boolean widget.\"\n" +
                ConfigKeysConstants.WIDGET_BOOLEAN_CHECKED + " = true\n";

        Toml config = new Toml().read(tomlString);

        Assertions.assertThrows(IllegalArgumentException.class, () -> new BooleanWidgetPlugin(config.getTable("widget")),
                "Boolean field Id is required and cannot be null or empty.");
    }*/

//    @Test
//    public void testMissingCheckedThrowsException() {
//        // Configuration with checked field missing
//        String tomlString = "[widget]\n" +
//                ConfigKeysConstants.WIDGET_ID + " = \"booleanWidgetId\"\n" +
//                ConfigKeysConstants.WIDGET_NAME + " = \"BooleanWidgetName\"\n" +
//                ConfigKeysConstants.WIDGET_TOOL_TIP + " = \"A tooltip for the boolean widget.\"\n";
//
//        Toml config = new Toml().read(tomlString);
//
//        Assertions.assertThrows(IllegalArgumentException.class, () -> new BooleanWidgetPlugin(config.getTable("widget")),
//                "Boolean field Checked is required and cannot be null if provided.");
//    }

    @Test
    public void testToString() {
        // Configuration for testing toString
        String tomlString = "[widget]\n" +
                ConfigKeysConstants.WIDGET_ID + " = \"booleanWidgetId\"\n" +
                ConfigKeysConstants.WIDGET_NAME + " = \"BooleanWidgetName\"\n" +
                ConfigKeysConstants.WIDGET_TOOL_TIP + " = \"A tooltip for the boolean widget.\"\n" +
                ConfigKeysConstants.WIDGET_BOOLEAN_CHECKED + " = true\n";

        Toml config = new Toml().read(tomlString);
        BooleanWidgetPlugin booleanWidgetPlugin = new BooleanWidgetPlugin(config.getTable("widget"));

        String expectedString = "BooleanWidgetPlugin{checked=true, id='booleanWidgetId', name='BooleanWidgetName', toolTip='A tooltip for the boolean widget.'}";
        Assertions.assertEquals(expectedString, booleanWidgetPlugin.toString());
    }
}
