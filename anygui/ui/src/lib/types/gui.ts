import type { Field } from "./fields"

/**
 * Type that describes the whole AnyGUI gui
 */
export type Gui = {
	header: Header,
	fields: Field[],
	footer: Footer
}


/**
 * Header type for the gui
 */
export type Header = {
	logoUrl: string,
	title: string
}

/**
 * Footer type for the gui
 */
export type Footer = {
	text: string
}
