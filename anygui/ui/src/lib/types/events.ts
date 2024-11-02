export type Event = {
	type: EventType;
}

export type EventType = "update" | "alert";

export type UpdateEvent = Event & {
	type: "update",
	id: string;
	data: TextFieldEventData | CheckboxFieldEventData | NumberFieldEventData;
}

export type TextFieldEventData = {
	value: string;
};

export type NumberFieldEventData = {
	value: number;
};

export type CheckboxFieldEventData = {
	value: boolean;
};

export type EventHandler = (event: Event) => void;
