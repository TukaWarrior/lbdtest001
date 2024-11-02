/**
 * Base form field definition
 */
export type Field = {
	id: string,
	label: string,
	type: FieldType,
	readonly: boolean,
	hint: string
}

/**
 * Base field validation definition
 */
export type FieldValidation = {
	required: boolean,
	message: string
}

/**
 * Form field type
 */
export type FieldType = 
	| "textfield" 
	| "checkbox" 
	| "integer"
	| "float"

/**
 * Text field type
 */
export type TextField = Field & {
	placeholder?: string,
	value: string,
	validation?: FieldValidation & {
		maxLenght: number
	},
}

/**
 * Checkbox field type
 */
export type CheckboxField = Field & {
	checked: boolean,
}

/**
 * Integer field type
 */
export type IntegerField = Field & {
	value: number;
	min?: number;                    // Minimum value at the top level
	max?: number;                    // Maximum value at the top level
	warningRange?: [number, number]; // Optional warning range at the top level
	step?: number;
	readonly?: boolean;
	hint?: string;
};

/**
 * Float field type
 */
export type FloatField = Field & {
	value: number;
	min?: number;                    // Minimum value at the top level
	max?: number;                    // Maximum value at the top level
	warningRange?: [number, number]; // Optional warning range at the top level
	step?: number;                   // Step size for input
	readonly?: boolean;
	hint?: string;
	validation?: FieldValidation & {
		decimalRequired?: boolean;  // Specifies if a decimal point is required
	}
};