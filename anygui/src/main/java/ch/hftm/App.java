package ch.hftm;

import ch.hftm.config.PluginParser;
import ch.hftm.config.PluginRegistry;
import ch.hftm.plugin.Plugin;
import ch.hftm.webserver.Webserver;
import ch.hftm.webview.Webview;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class App{
    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        // initiating plugin registry by parsing the toml file
        App.retrievePluginRegistry();

        // Start the Javalin webserver in a separate thread
        new Thread(Webserver::start).start();

        // Start the JavaFX webview in a separate thread
        Webview.launchWebview(args);
    }

    private static void retrievePluginRegistry() {
        PluginParser pluginParser = new PluginParser();
        PluginRegistry registry = pluginParser.parsePlugins();

        for (Plugin plugin : registry.getAllPlugins()) {
            log.info("Plugin created: {}", plugin);
        }
    }
    // Test
    // This is a second test
    // Test
}
