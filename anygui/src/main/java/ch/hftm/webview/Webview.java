package ch.hftm.webview;

import ch.hftm.config.PluginRegistry;
import ch.hftm.config.PluginValidator;
import ch.hftm.plugin.application.ApplicationPlugin;
import ch.hftm.webserver.util.NetworkUtils;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Webview class is responsible for starting the JavaFX WebView and loading the webpage served by the Javalin webserver.
 */
public class Webview extends Application{
    private static final Logger log = LoggerFactory.getLogger(Webview.class);

    /**
     * Starts the JavaFX WebView and loads the webpage served by the Javalin webserver.
     *
     * @param primaryStage the primary stage
     */
    @Override
    public void start(Stage primaryStage) {
        // Create a WebView
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        PluginRegistry registry = PluginRegistry.getInstance();
        ApplicationPlugin applicationPlugin = registry.getApplicationPlugin();

        String ipAddress = NetworkUtils.getActiveIpAddress();
        int port = applicationPlugin.getPort();

        // Load the webpage served by the Javalin webserver
        webEngine.load("http://" + ipAddress + ":" + port);

        webEngine.setOnError(event -> {
            log.error("WebView Error: {}", event.getMessage());
        });
        webEngine.setOnAlert(event -> {
            log.error("WebView Alert: {}", event.getData());
        });

        // Set the WebView into a VBox
        VBox root = new VBox(webView);

        // Create a Scene with the VBox
        Scene scene = new Scene(root, 800, 600); //TODO: Read from anygui_config.toml

        //TODO: Set location of window according to anygui_config.toml

        // Set the Scene on the Stage
        primaryStage.setTitle(applicationPlugin.getWindowTitle() + " - " + ipAddress + ":" + port);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Launches the JavaFX application in a separate thread.
     *
     * @param args the command line arguments
     */
    public static void launchWebview(String[] args) {
        new Thread(() -> Application.launch(Webview.class, args)).start();
    }
    // Hello
}
