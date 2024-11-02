package ch.hftm.plugin.widgets;

import ch.hftm.config.ConfigKeysConstants;
import ch.hftm.plugin.widget.TextFieldWidgetPlugin;

import com.moandjiezana.toml.Toml;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class TextFieldWidgetPluginValidationTest {

    @Test
    public void testValidConfig() {
        // Valid configuration string
        String tomlString = "[widget]\n" +
                ConfigKeysConstants.WIDGET_ID + " = \"textFieldId\"\n" +
                ConfigKeysConstants.WIDGET_NAME + " = \"TextFieldName\"\n" +
                ConfigKeysConstants.WIDGET_TOOL_TIP + " = \"A tooltip for the text field.\"\n" +
                ConfigKeysConstants.WIDGET_PLACEHOLDER + " = \"Enter text here...\"\n" +
                ConfigKeysConstants.WIDGET_TEXT_FIELD_MAX_LENGTH + " = 20\n";

        Toml config = new Toml().read(tomlString);
        TextFieldWidgetPlugin textFieldWidgetPlugin = new TextFieldWidgetPlugin(config.getTable("widget"));

        Assertions.assertEquals("textFieldId", textFieldWidgetPlugin.getId());
        Assertions.assertEquals("TextFieldName", textFieldWidgetPlugin.getName());
        Assertions.assertEquals("Enter text here...", textFieldWidgetPlugin.getPlaceholderText());
        Assertions.assertEquals(20, textFieldWidgetPlugin.getMaxLength());
    }

//    @Test
//    public void testMissingIdThrowsException() {
//        // Configuration with ID missing
//        String tomlString = "[widget]\n" +
//                ConfigKeysConstants.WIDGET_NAME + " = \"TextFieldName\"\n" +
//                ConfigKeysConstants.WIDGET_TOOL_TIP + " = \"A tooltip for the text field.\"\n" +
//                ConfigKeysConstants.WIDGET_PLACEHOLDER + " = \"Enter text here...\"\n" +
//                ConfigKeysConstants.WIDGET_TEXT_FIELD_MAX_LENGTH + " = 20\n";
//
//        Toml config = new Toml().read(tomlString);
//
//        Assertions.assertThrows(IllegalArgumentException.class, () -> new TextFieldWidgetPlugin(config.getTable("widget")),
//                "Text field Id is required and cannot be null or empty.");
//    }

//    @Test
//    public void testEmptyIdThrowsException() {
//        // Configuration with empty ID
//        String tomlString = "[widget]\n" +
//                ConfigKeysConstants.WIDGET_ID + " = \"\"\n" +
//                ConfigKeysConstants.WIDGET_NAME + " = \"TextFieldName\"\n" +
//                ConfigKeysConstants.WIDGET_TOOL_TIP + " = \"A tooltip for the text field.\"\n" +
//                ConfigKeysConstants.WIDGET_PLACEHOLDER + " = \"Enter text here...\"\n" +
//                ConfigKeysConstants.WIDGET_TEXT_FIELD_MAX_LENGTH + " = 20\n";
//
//        Toml config = new Toml().read(tomlString);
//
//        Assertions.assertThrows(IllegalArgumentException.class, () -> new TextFieldWidgetPlugin(config.getTable("widget")),
//                "Text field Id is required and cannot be null or empty.");
//    }

    /*@Test
    public void testMissingPlaceholderTextThrowsException() {
        // Configuration with placeholder text missing
        String tomlString = "[widget]\n" +
                ConfigKeysConstants.WIDGET_ID + " = \"textFieldId\"\n" +
                ConfigKeysConstants.WIDGET_NAME + " = \"TextFieldName\"\n" +
                ConfigKeysConstants.WIDGET_TOOL_TIP + " = \"A tooltip for the text field.\"\n" +
                ConfigKeysConstants.WIDGET_TEXT_FIELD_MAX_LENGTH + " = 20\n";

        Toml config = new Toml().read(tomlString);

        Assertions.assertThrows(IllegalArgumentException.class, () -> new TextFieldWidgetPlugin(config.getTable("widget")),
                "Text field Placeholder text is required and cannot be null or empty.");
    }
*/
//    @Test
//    public void testInvalidMaxLengthThrowsException() {
//        // Configuration with max length set to 0
//        String tomlString = "[widget]\n" +
//                ConfigKeysConstants.WIDGET_ID + " = \"textFieldId\"\n" +
//                ConfigKeysConstants.WIDGET_NAME + " = \"TextFieldName\"\n" +
//                ConfigKeysConstants.WIDGET_TOOL_TIP + " = \"A tooltip for the text field.\"\n" +
//                ConfigKeysConstants.WIDGET_PLACEHOLDER + " = \"Enter text here...\"\n" +
//                ConfigKeysConstants.WIDGET_TEXT_FIELD_MAX_LENGTH + " = 0\n";
//
//        Toml config = new Toml().read(tomlString);
//
//        Assertions.assertThrows(IllegalArgumentException.class, () -> new TextFieldWidgetPlugin(config.getTable("widget")),
//                "Text field Max length is required and cannot be empty if provided.");
//    }

    @Test
    public void testToString() {
        // Configuration for testing toString
        String tomlString = "[widget]\n" +
                ConfigKeysConstants.WIDGET_ID + " = \"textFieldId\"\n" +
                ConfigKeysConstants.WIDGET_NAME + " = \"TextFieldName\"\n" +
                ConfigKeysConstants.WIDGET_TOOL_TIP + " = \"A tooltip for the text field.\"\n" +
                ConfigKeysConstants.WIDGET_PLACEHOLDER + " = \"Enter text here...\"\n" +
                ConfigKeysConstants.WIDGET_TEXT_FIELD_MAX_LENGTH + " = 20\n";

        Toml config = new Toml().read(tomlString);
        TextFieldWidgetPlugin textFieldWidgetPlugin = new TextFieldWidgetPlugin(config.getTable("widget"));

        String expectedString = "TextFieldWidgetPlugin{id='textFieldId', name='TextFieldName', toolTip='A tooltip for the text field.', placeholderText='Enter text here...', maxLength=20}";
        Assertions.assertEquals(expectedString, textFieldWidgetPlugin.toString());
    }
}
