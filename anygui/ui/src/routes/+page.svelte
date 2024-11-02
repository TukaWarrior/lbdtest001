<script lang="ts">
	import FormBuilder from '@/components/FormBuilder.svelte';
	import type { PageData } from './$types';
	import { Button } from '@/components/ui/button';
	import { dev } from '$app/environment';

	export let data: PageData;
</script>

<div class="p-4">
	<FormBuilder fields={data.page.fields} />

	{#if dev}
		<!-- TODO: Only for debugging purposes -->
		<Button class="mt-4" on:click={() => console.log(data.page.fields)}>log field data</Button>
	{/if}
</div>
