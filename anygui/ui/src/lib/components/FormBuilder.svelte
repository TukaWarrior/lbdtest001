<script lang="ts">
	import type { Field, FieldType } from '@/types/fields';
	import Checkbox from './widgets/Checkbox.svelte';
	import TextField from './widgets/TextField.svelte';
	import IntegerField from './widgets/IntegerField.svelte';
	import FloatField from './widgets/FloatField.svelte';

	export let fields: Field[];

	// Used to map the FieldType to a component
	const fieldMap: { [key in FieldType]: any } = {
		checkbox: Checkbox,
		textfield: TextField,
		integer: IntegerField,
		float: FloatField
	};
</script>

{#each fields as field}
	<div class="mb-3">
		<svelte:component this={fieldMap[field.type]} {field} />
	</div>
{/each}
