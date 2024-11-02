package ch.hftm.webserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import ch.hftm.App;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.hftm.config.PluginRegistry;
import ch.hftm.plugin.widget.BooleanWidgetPlugin;
import ch.hftm.plugin.widget.FloatWidgetPlugin;
import ch.hftm.plugin.widget.IntegerWidgetPlugin;
import ch.hftm.plugin.widget.TextFieldWidgetPlugin;
import ch.hftm.plugin.widget.WidgetPlugin;
import ch.hftm.webserver.util.NetworkUtils;
import io.javalin.Javalin;


import org.eclipse.jetty.server.ServerConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.javalin.websocket.WsContext;

/**
 * Webserver class responsible for starting the Javalin server and handling
 * widget information.
 */
public class Webserver {
	private static final Logger log = LoggerFactory.getLogger(App.class);
    private static final Set<WsContext> connectedClients = new CopyOnWriteArraySet<>();

	/**
	 * Starts the Javalin server and sets up the API endpoint.
	 */
	public static void start() {
		try {
			// Get the active network IP address using the utility method
			String deviceIpAdress = NetworkUtils.getActiveIpAddress();
			String localhostAddress = NetworkUtils.getLocalhostIpAddress();

			if (deviceIpAdress != null) {
				PluginRegistry registry = PluginRegistry.getInstance();
				String widgetsJson = generatedWidgetsToJson(registry);
				int port = registry.getApplicationPlugin().getPort(); //n

				// Start the Javalin server
				Javalin app = Javalin.create(config -> {
					config.staticFiles.add("/public");
				});

				// Create multiple connectors
				createConnector(app, deviceIpAdress, port);
				createConnector(app, localhostAddress, port);

				app.start();

				app.get("/api", ctx -> {
					ctx.json(widgetsJson);
				});

				initWebsocketConnection(app, widgetsJson);

				log.info("Server is successfully running and is available at:");
            	log.info("http://{}:{}/api", deviceIpAdress, port);
            	log.info("http://{}:{}/api", localhostAddress, port);
			} else {
				log.warn("Webserver can't Start. No active network IP address found.");
			}

		} catch (Exception e) {
			log.error("Error starting the webserver: {}", e.getMessage());
			e.printStackTrace();
		}
	}

	private static void createConnector(Javalin app, String host, int port) {
		org.eclipse.jetty.server.Server server = app.jettyServer().server();
		ServerConnector connector = new ServerConnector(server);
		connector.setHost(host);
		connector.setPort(port);
		server.addConnector(connector);
	}
	
	/**
	 * Generates a JSON representation of the widgets from the PluginRegistry.
	 *
	 * @param registry the PluginRegistry instance containing widget plugins
	 * @return a JSON string representing the widgets
	 */
	public static String generatedWidgetsToJson(PluginRegistry registry) {
		try {
			// Retrieve widgets directly from the pluginRegistry
			List<WidgetPlugin> widgets = registry.getWidgetPlugins();
			// Convert the list of widgets into a Map format to clarify the type
			List<Map<String, Object>> widgetList = new ArrayList<>();
			for (WidgetPlugin widget : widgets) {
				Map<String, Object> widgetInfo = new HashMap<>();
				// Retrieve corresponding information from the WidgetFactory based on the widget
				// type
				if (widget instanceof BooleanWidgetPlugin) {
					BooleanWidgetPlugin booleanWidget = (BooleanWidgetPlugin) widget;
					widgetInfo.put("type", "checkbox");
					widgetInfo.put("id", booleanWidget.getId());
					widgetInfo.put("label", booleanWidget.getName());
					widgetInfo.put("checked", booleanWidget.isChecked());
					widgetInfo.put("hint", booleanWidget.getToolTip());
				} else if (widget instanceof TextFieldWidgetPlugin) {
					TextFieldWidgetPlugin textFieldWidget = (TextFieldWidgetPlugin) widget;
					widgetInfo.put("type", "textfield");
					widgetInfo.put("id", textFieldWidget.getId());
					widgetInfo.put("label", textFieldWidget.getName());
					widgetInfo.put("hint", textFieldWidget.getToolTip());
					widgetInfo.put("maxLength", textFieldWidget.getMaxLength());
					widgetInfo.put("placeholder", textFieldWidget.getPlaceholderText());
				} else if (widget instanceof IntegerWidgetPlugin) {
					IntegerWidgetPlugin integerWidget = (IntegerWidgetPlugin) widget;
					widgetInfo.put("type", "integer");
					widgetInfo.put("id", integerWidget.getId());
					widgetInfo.put("label", integerWidget.getName());
					widgetInfo.put("hint", integerWidget.getToolTip());
					widgetInfo.put("min", integerWidget.getMin());
					widgetInfo.put("max", integerWidget.getMax());
					widgetInfo.put("step", integerWidget.getStep());
					widgetInfo.put("value", integerWidget.getValue());
				} else if (widget instanceof FloatWidgetPlugin) {
					FloatWidgetPlugin floatWidget = (FloatWidgetPlugin) widget;
					widgetInfo.put("type", "float");
					widgetInfo.put("id", floatWidget.getId());
					widgetInfo.put("label", floatWidget.getName());
					widgetInfo.put("hint", floatWidget.getToolTip());
					widgetInfo.put("min", floatWidget.getMin());
					widgetInfo.put("max", floatWidget.getMax());
					widgetInfo.put("step", floatWidget.getStep());
					widgetInfo.put("value", floatWidget.getValue());
				} else {
					// Handle unknown widget types accordingly
					widgetInfo.put("type", "UnknownWidget");
					widgetInfo.put("id", widget.getId());
					widgetInfo.put("name", widget.getName());
				}
				// Add the widget to the list
				widgetList.add(widgetInfo);
			}
			// Generate the JSON object from the list
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonOutput = objectMapper.writeValueAsString(widgetList);

			// Print the JSON output to the console
			System.out.println(jsonOutput);

			return jsonOutput;
		} catch (Exception e) {
			log.error(e.getMessage());
			return "{\"error\": \"Error generating JSON\"}";
		}
	}

	private static void initWebsocketConnection(Javalin app, String widgetsJson) {
		// Initialize websocket connection on adress [
		// ws://{ipAdress}:{configuredPort}/ws ]
		app.ws("/ws", webSocket -> {
			// Handle when client connects (e.g. send initial data)
			webSocket.onConnect(websocketContext -> {
				connectedClients.add(websocketContext);
                log.info("Client connected: {}", websocketContext.sessionId());
				broadcastData(widgetsJson);
			});

			// Handle when client disconnects (clean up?)
			webSocket.onClose(websocketContext -> {
				connectedClients.remove(websocketContext);
                log.info("Client disconnected: {}", websocketContext.sessionId());
			});

			// Handle incoming messages from the client (e.g. UI changes)
			webSocket.onMessage(websocketContext -> {
				handleIncomingData(websocketContext.message());
			});

			// Handle when an error inside the websocket context occurs
			webSocket.onError(error -> {
                assert error.error() != null;
                log.error("Websocket error: {}", error.error().getMessage());
			});
		});
	}

	public static void broadcastData(String jsonData) {
		for (WsContext ctx : connectedClients) {
			ctx.send(jsonData);
		}
	}

	public static void handleIncomingData(String data) {
		// Handle incoming data from the client (e.g. UI changes)
        log.debug("Incoming data: {}", data);

		// => Validate incoming data
	}
}