package ch.hftm.plugin.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.moandjiezana.toml.Toml;

import ch.hftm.config.ConfigKeysConstants;

public class ApplicationPluginTest {

    @Test
    public void testValidConfig() {
        // Valid configuration string
        String tomlString = "[application]\n" +
                ConfigKeysConstants.APPLICATION_TITLE + " = \"Device Control Panel\"\n" +
                ConfigKeysConstants.APPLICATION_HEADER_TEXT + " = \"Control Panel for XYZ Device\"\n" +
                ConfigKeysConstants.APPLICATION_FOOTER_TEXT + " = \"XYZ Corporation - All rights reserved\"\n" +
                ConfigKeysConstants.APPLICATION_ENABLE_REMOTE + " = true\n" +
                ConfigKeysConstants.APPLICATION_JAVAFX_ENABLED + " = true\n" +
                ConfigKeysConstants.APPLICATION_PORT + " = 7070\n";

        Toml config = new Toml().read(tomlString);
        ApplicationPlugin applicationPlugin = new ApplicationPlugin(config.getTable("application"));

        Assertions.assertEquals("Device Control Panel", applicationPlugin.getWindowTitle());
        Assertions.assertEquals("Control Panel for XYZ Device", applicationPlugin.getHeaderText());
        Assertions.assertEquals("XYZ Corporation - All rights reserved", applicationPlugin.getFooterText());
        Assertions.assertTrue(applicationPlugin.isEnableRemote());
        Assertions.assertTrue(applicationPlugin.isJavaFxEnabled());
        Assertions.assertEquals(7070, applicationPlugin.getPort());
    }

    @Test
    public void testToString() {
        // Configuration for testing toString
        String tomlString = "[application]\n" +
                ConfigKeysConstants.APPLICATION_TITLE + " = \"Device Control Panel\"\n" +
                ConfigKeysConstants.APPLICATION_HEADER_TEXT + " = \"Control Panel for XYZ Device\"\n" +
                ConfigKeysConstants.APPLICATION_FOOTER_TEXT + " = \"XYZ Corporation - All rights reserved\"\n" +
                ConfigKeysConstants.APPLICATION_ENABLE_REMOTE + " = false\n" +
                ConfigKeysConstants.APPLICATION_JAVAFX_ENABLED + " = true\n" +
                ConfigKeysConstants.APPLICATION_PORT + " = 7070\n";

        Toml config = new Toml().read(tomlString);
        ApplicationPlugin applicationPlugin = new ApplicationPlugin(config.getTable("application"));

        String expectedString = "ApplicationPlugin{windowTitle='Device Control Panel', headerText='Control Panel for XYZ Device', footerText='XYZ Corporation - All rights reserved', enableRemote=true, javaFxEnabled=true, port=7070}";
        Assertions.assertEquals(expectedString, applicationPlugin.toString());
    }
}
