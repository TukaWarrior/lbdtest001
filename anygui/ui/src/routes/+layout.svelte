<script lang="ts">
	import '../app.css';
	import { Toaster } from 'svelte-sonner';
	import type { LayoutData } from './$types';

	export let data: LayoutData;
</script>

<Toaster position="top-right" richColors />
<header class="sticky top-0 z-10 flex h-[57px] items-center gap-1 border-b bg-background px-4">
	<img alt="AnyGUI Logo" src={data.page.header.logoUrl} class="max-h-8" />
	<h1 class="ml-2 text-xl font-semibold">{data.page.header.title}</h1>
</header>

<slot></slot>

<footer class="absolute bottom-0 flex h-12 w-full items-center border-t px-4">
	{data.page.footer.text}
</footer>
