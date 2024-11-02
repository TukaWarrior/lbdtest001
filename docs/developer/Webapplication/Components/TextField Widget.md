#  TextField Widget

## Overview
This implementation introduces a dynamic rendering feature for TextField widgets in the `+page.svelte` file based on a JSON object. The JSON object, currently simulated, will be provided by the Core Team. This setup allows the page to dynamically create TextField input elements based on the properties of the JSON object, offering adaptability as the JSON structure evolves.

## Components and Implementation

### Main Page (`+page.svelte`)
The page dynamically renders `TextField` components by iterating over each key-value pair in the `formData` object.

- **Simulated JSON Object**: The `formData` object represents a sample JSON structure.


## TextField Component (`TextField.svelte`)

The `TextField` component is reusable and accepts the following props:

- **label**: The input’s identifier (used as the label text and ID).
- **value**: The bound value for the input field.
