<script lang="ts">
	import type { CheckboxField } from '@/types/fields';
	import { Checkbox } from '../ui/checkbox';
	import { Label } from '../ui/label';
	import { updateService } from '@/services/updateService';
	import type { CheckboxFieldEventData, Event, UpdateEvent } from '@/types/events';
	import { onMount } from 'svelte';
	import { websocketService } from '@/services/websocketService';

	export let field: CheckboxField;

	/**
	 * Handle the change when the checkbox gets checked/unchecked
	 */
	function handleChange(value: boolean) {
		let event = {
			id: field.id,
			type: 'update',
			data: {
				value: field.checked
			} as CheckboxFieldEventData
		} as UpdateEvent;

		updateService.update(event);
	}

	function handleServerUpdate(event: Event) {
		let updateEvent = event as UpdateEvent;
		if (updateEvent.id !== field.id) return;
		let eventData = updateEvent.data as BooleanFieldEventData;

		field.checked = eventData.value;
	}

	onMount(() => {
		websocketService.addEventListener('update', handleServerUpdate);
	});
</script>

<div class="items-top flex space-x-2">
	<Checkbox
		id={field.id}
		bind:checked={field.checked}
		onCheckedChange={(value) => handleChange(value ? true : false)}
	/>
	<div class="grid gap-1.5 leading-none">
		<Label
			for={field.id}
			class="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
		>
			{field.label}
		</Label>
		{#if field.hint}
			<p class="text-sm text-muted-foreground">
				{field.hint}
			</p>
		{/if}
	</div>
</div>
