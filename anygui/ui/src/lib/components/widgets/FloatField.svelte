<script lang="ts">
	import type { FloatField } from '@/types/fields';
	import { Input } from '../ui/input';
	import { Label } from '../ui/label';
	import { updateService } from '@/services/updateService';
	import type {
		Event,
		NumberFieldEventData,
		TextFieldEventData,
		UpdateEvent
	} from '@/types/events';
	import { onMount } from 'svelte';
	import { websocketService } from '@/services/websocketService';

	export let field: FloatField;

	let valid = true;
	let warning = false;
	let errorMessage = ''; // Variable for specific error messages

	/**
	 * Validates the input based on the requirements specified in the validation object.
	 */
	function validate() {
		// Reset error message and warning
		errorMessage = '';
		warning = false;
		valid = true;

		// Check if the field is required
		if (field.value === null || field.value === undefined) {
			errorMessage = 'This field is required.';
			valid = false;
			return;
		}

		// Check if the field contains a decimal point
		if (!/^\d+(\.\d+)?$/.test(field.value.toString())) {
			errorMessage = 'The value must be a decimal number with a dot.';
			valid = false;
			return;
		}

		// Minimum and maximum range checks for validity
		if (field.min !== undefined && field.value < field.min) {
			errorMessage = `The value must be at least ${field.min}.`;
			valid = false;
			return;
		}
		if (field.max !== undefined && field.value > field.max) {
			errorMessage = `The value must be no more than ${field.max}.`;
			valid = false;
			return;
		}

		// Warning range check, independent of validity
		if (field.warningRange) {
			const [minWarning, maxWarning] = field.warningRange;
			warning = field.value >= minWarning && field.value <= maxWarning;
		}
	}

	/**
	 * Handles the onBlur event for validation and updates if the field is valid.
	 */
	function handleBlur() {
		if (field.readonly) return;

		validate();

		// Only update if the input is valid
		if (!valid) return;

		let event = {
			id: field.id,
			type: 'update',
			data: {
				value: field.value
			} as NumberFieldEventData
		} as UpdateEvent;

		updateService.update(event);
	}

	function handleServerUpdate(event: Event) {
		let updateEvent = event as UpdateEvent;
		if (updateEvent.id !== field.id) return;
		let eventData = updateEvent.data as NumberFieldEventData;

		field.value = eventData.value;
	}

	onMount(() => {
		websocketService.addEventListener('update', handleServerUpdate);
	});
</script>

<div class="flex w-full max-w-sm flex-col gap-1.5">
	<Label for={field.id}
		>{field.label}{field.min !== undefined || field.max !== undefined ? '*' : ''}</Label
	>
	<Input
		type="number"
		id={field.id}
		readonly={field.readonly}
		min={field.min}
		max={field.max}
		step={field.step}
		on:blur={handleBlur}
		on:input={validate}
		bind:value={field.value}
	/>

	{#if field.hint}
		<p class="text-sm text-muted-foreground">{field.hint}</p>
	{/if}

	{#if warning && valid}
		<p class="text-warning text-sm">Warning: Value is within caution range.</p>
	{/if}

	{#if !valid}
		<p class="text-sm text-destructive">{errorMessage}</p>
	{/if}
</div>

