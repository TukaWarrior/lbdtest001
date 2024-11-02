import type { Load } from "@sveltejs/kit";
import type { Field } from '@/types/fields';
import type { Gui } from "@/types/gui";
import { dev } from "$app/environment";

// SPA mode
export const ssr = false;
export const prerender = true;

export const load: Load = async ({ fetch }) => {
	let fields = await fetch(dev ? "/fields.json" : "/api")
		.then(response => response.json()) as Field[];

	const page: Gui = {
		header: {
			logoUrl: "https://www.hftm.ch/themes/custom/hftm/logo.svg",
			title: "HFTM - AnyGUI"
		},
		fields,
		footer: {
			text: "This is my cool footer."
		}
	}

	return {
		page
	};
}
// Example for the schema (keep for now)
// fields: [
// 	{
// 		id: "1",
// 		label: "My first textfield",
// 		hint: "This field is readonly - you cannot edit this",
// 		readonly: true,
// 		type: "textfield",
// 		validation: {
// 			required: false,
// 			maxLenght: 10,
// 			message: "Field is required and must be max. 10 characters"
// 		},
// 		value: "test"
// 	} as TextField,
// 	{
// 		id: "2",
// 		label: "Another one",
// 		placeholder: "With placeholder",
// 		readonly: false,
// 		type: "textfield",
// 		validation: {
// 			required: true,
// 			maxLenght: 10,
// 			message: "Field is required and must be max. 10 characters"
// 		},
// 		value: ""
// 	} as TextField,
// 	{
// 		id: "3",
// 		label: "Accept terms and conditions",
// 		hint: "You agree to our Terms of Service and Privacy Policy.",
// 		readonly: false,
// 		type: "checkbox",
// 		checked: false
// 	} as CheckboxField
// ] as Field[],
