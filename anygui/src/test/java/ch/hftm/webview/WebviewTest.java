package ch.hftm.webview;

// ___          __              _             _ 
// | \ \        / /             (_)           | |
// | |\ \  /\  / /_ _ _ __ _ __  _ _ __   __ _| |
// | | \ \/  \/ / _` | '__| '_ \| | '_ \ / _` | |
// |_|  \  /\  / (_| | |  | | | | | | | | (_| |_|
// (_)   \/  \/ \__,_|_|  |_| |_|_|_| |_|\__, (_)
//                                        __/ |  
//                                       |___/   
// These tests do not work yet because they require the Mockito dependency that is used for creating mockup data. 
// The Mockito dependency is currently not implemented in the project. 

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;

// import org.junit.jupiter.api.BeforeEach;

// import ch.hftm.core.webserver.util.NetworkUtils;
// import javafx.scene.Scene;
// import javafx.scene.web.WebView;
// import javafx.stage.Stage;

// public class WebviewTest extends ApplicationTest {

//     @BeforeEach
//     public void setUp() {
//         // Mock NetworkUtils to return a specific IP address before each test
//         Mockito.mockStatic(NetworkUtils.class).when(NetworkUtils::getActiveIpAddress).thenReturn("127.0.0.1");
//     }

//     @Override
//     public void start(Stage stage) throws Exception {
//         // This will be automatically called when using ApplicationTest
//         // This is required by TestFX to set up the Stage
//         new Webview().start(stage);
//     }

//     @Test
//     public void testWebviewLoadsCorrectUrl() {
//         // Use TestFX methods to find the WebView and its WebEngine
//         WebView webView = lookup(".web-view").query();
//         WebEngine webEngine = webView.getEngine();

//         // Verify that the WebEngine loaded the correct URL
//         assertEquals("http://127.0.0.1:7070", webEngine.getLocation(), "The WebEngine should load the correct URL");
//     }

//     @Test
//     public void testSceneIsSetCorrectly() {
//         // Use TestFX to verify the scene is set correctly
//         Stage primaryStage = (Stage) lookup(".stage").query();

//         Scene scene = primaryStage.getScene();
//         assertNotNull(scene, "The Scene should not be null");
//         assertEquals(800, scene.getWidth(), "The Scene width should be 800");
//         assertEquals(600, scene.getHeight(), "The Scene height should be 600");
//     }

//     @Test
//     public void testPrimaryStageTitle() {
//         // Verify that the title of the primary stage is set correctly
//         Stage primaryStage = (Stage) lookup(".stage").query();
//         assertEquals("JavaFX WebView - 127.0.0.1:7070", primaryStage.getTitle(), "The Stage title should be set correctly");
//     }
// }