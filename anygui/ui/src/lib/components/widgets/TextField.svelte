<script lang="ts">
	import type { TextField } from '@/types/fields';
	import { Input } from '../ui/input';
	import { Label } from '../ui/label';
	import { updateService } from '@/services/updateService';
	import type { Event, TextFieldEventData, UpdateEvent } from '@/types/events';
	import { onMount } from 'svelte';
	import { websocketService } from '@/services/websocketService';

	export let field: TextField;

	let valid = true;

	/**
	 * Validates the input with the given validation.* requirements.
	 *
	 * @returns true if valid or false when invalid
	 */
	function validate(): boolean {
		if (!field.validation) return true;

		if (field.validation.required && !field.value) return false;
		if (field.validation.maxLenght < field.value.length) return false;

		return true;
	}

	/**
	 * Handle the on blur events. Does the validation and if field is valid do the update.
	 */
	function handleBlur() {
		if (field.readonly) return;

		valid = validate();

		// only update if valid
		if (!valid) return;

		let event = {
			id: field.id,
			type: 'update',
			data: {
				value: field.value
			} as TextFieldEventData
		} as UpdateEvent;

		updateService.update(event);
	}

	function handleServerUpdate(event: Event) {
		let updateEvent = event as UpdateEvent;
		if (updateEvent.id !== field.id) return;
		let eventData = updateEvent.data as TextFieldEventData;

		field.value = eventData.value;
	}

	onMount(() => {
		websocketService.addEventListener('update', handleServerUpdate);
	});
</script>

<div class="flex w-full max-w-sm flex-col gap-1.5">
	<Label for={field.id}>{field.label}{field.validation?.required ? '*' : ''}</Label>
	<Input
		type="text"
		id={field.id}
		readonly={field.readonly}
		placeholder={field.placeholder}
		on:blur={handleBlur}
		bind:value={field.value}
	/>

	{#if field.hint}
		<p class="text-sm text-muted-foreground">{field.hint}</p>
	{/if}

	{#if !valid}
		<p class="text-sm text-destructive">{field.validation?.message}</p>
	{/if}
</div>
