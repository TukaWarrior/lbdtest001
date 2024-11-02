import { dev } from "$app/environment";
import type { Event, EventHandler, EventType } from "@/types/events";
import { toast } from "svelte-sonner";

/**
 * Websocket service. Handles the websocket connection to the server.
 */
export class WebsocketService {
	private socket: WebSocket;
	private listeners: Map<EventType, EventHandler[]> = new Map();
	private keepAliveInterval?: NodeJS.Timeout;
	private keepAliveIntervalMs = 2000;

	constructor(url: string) {
		this.socket = new WebSocket(url);

		this.socket.addEventListener("open", () => {
			console.log("Websocket connection opened");
			this.startKeepAlive(); // Start sending keepalive messages
		});

		this.socket.addEventListener("close", () => {
			console.log("Websocket connection closed");
			this.stopKeepAlive(); // Stop sending keepalive messages
		});

		this.addMessageEventListener();
	}

	/**
	 * Start the keepalive messages
	 */
	private startKeepAlive() {
		this.keepAliveInterval = setInterval(() => {
			if (this.socket.readyState === WebSocket.OPEN) {
				this.socket.send("ping");
				console.log("sent ping")
			} else {
				console.warn("Websocket connection is not ready");
			}
		}, this.keepAliveIntervalMs);
	}

	/**
	 * Stop the keepalive messages
	 */
	private stopKeepAlive() {
		if (this.keepAliveInterval) {
			clearInterval(this.keepAliveInterval);
			this.keepAliveInterval = undefined;
		}
	}

	/**
	 * Send an event to the server
	 *
	 * @param event The event to send
	 */
	public async sendEvent(event: Event) {
		if (dev) {
			toast.info("Send update to server", { description: JSON.stringify(event) });
			return;
		}

		if (this.socket.readyState !== WebSocket.OPEN) {
			console.warn("Websocket is not in an open state. State:", this.socket.readyState);
			toast.warning("Cannot send update to server. See console for more information.");
			return;
		}

		this.socket.send(JSON.stringify(event));
	}

	/**
	 * Add an event listener
	 *
	 * @param type The type of the event
	 * @param eventHandler The event handler
	 */
	public async addEventListener(type: EventType, eventHandler: EventHandler) {
		if (!this.listeners.has(type)) {
			this.listeners.set(type, []);
		}

		this.listeners.get(type)!.push(eventHandler);
	}

	/**
	 * Add socket event listener
	 */
	private addMessageEventListener() {
		this.socket.addEventListener("message", event => {
			const e = JSON.parse(event.data) as Event;

			if (this.listeners.has(e.type)) {
				this.listeners.get(e.type)!.forEach(listener => {
					listener(e);
				});
			} else {
				console.warn("No listener for event type:", e.type);
			}
		});
	}
}

export const websocketService = new WebsocketService("/ws");
