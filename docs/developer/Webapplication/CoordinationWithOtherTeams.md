# Dynamic TextField Widget Rendering

## Overview
To schematics to dicuss with the other team. 

## Suggestion for the field types

#### Float Field

```
{
	id: "floatField1",
	label: "Enter a floating-point number",
	type: "float",
	value: 3.14,
	readonly: false,
	validation: {
		required: true,
		min: 0.0,
		max: 100.0,
		warningRange: [90.0, 100.0],
		message: "Value must be a float between 0.0 and 100.0",
	}
}
```

#### Select-One Field

```
{
	id: "selectOne1",
	label: "Choose a single option",
	type: "selectOne",
	options: ["Option A", "Option B", "Option C"],
	selected: "Option A",
	readonly: false,
	hint: "Please select one option from the list",
}
```

#### Select-Many Field

```
{
	id: "selectMany1",
	label: "Choose multiple options",
	type: "selectMany",
	options: ["Option 1", "Option 2", "Option 3"],
	selected: ["Option 1", "Option 3"],
	readonly: false,
	hint: "You can select multiple options",
}

```

#### Gauge Field

```
{
	id: "gauge1",
	label: "Performance level",
	type: "gauge",
	value: 75,
	min: 0,
	max: 100,
	readonly: true,
	hint: "Displays the current performance level",
}

```

#### XY-Chart Field

```
{
	id: "xyChart1",
	label: "Data trends over time",
	type: "xyChart",
	data: [
		{ x: 0, y: 1 },
		{ x: 1, y: 3 },
		{ x: 2, y: 6 },
		{ x: 3, y: 10 },
	],
	readonly: true,
	hint: "A graphical representation of data over time",
}
```

#### Textarea Field

```
{
	id: "textArea1",
	label: "Detailed Description",
	type: "textarea",
	value: "Enter a detailed description here...",
	readonly: false,
	autoscroll: true,
	hint: "Use this field to provide additional details",
}
```

#### String-Output Field

```
{
	id: "stringOutput1",
	label: "Output Value",
	type: "stringOutput",
	value: "Calculated result: 42",
	readonly: true,
	hint: "This field shows a calculated result that cannot be edited",
}

```