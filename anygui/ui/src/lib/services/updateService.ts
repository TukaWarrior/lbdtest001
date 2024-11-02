import { websocketService, type WebsocketService } from "./websocketService";
import type { UpdateEvent } from "@/types/events";

/**
 * Service responsible for updating the changed values
 */
class UpdateService {
	private socketService: WebsocketService;

	constructor(socketService: WebsocketService) {
		this.socketService = socketService;
	}

	/**
	* Send update event to server
	*/
	async update(event: UpdateEvent) {
		await this.socketService.sendEvent(event);
	}
}


export const updateService = new UpdateService(websocketService);
