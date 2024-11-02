
# Checkbox Component

This component is a wrapper around the `CheckboxPrimitive.Root` from the `bits-ui` library, providing customizable checkbox functionality. It also includes icons from `lucide-svelte` to visually represent the checked and indeterminate states.

## Props

- **checked**: Boolean value that determines whether the checkbox is checked (`true`) or unchecked (`false`). Default is `false`.

- **className**: Additional CSS classes that can be applied to the checkbox for further styling. This can be used to modify the appearance beyond the default styles.

## Events

- **on:click**: A click event handler that can be used to trigger actions when the checkbox is clicked.

## Usage

```svelte
<script>
  import Checkbox from './Checkbox.svelte';
</script>

<Checkbox 
  checked={true}
  on:click={() => console.log('Checkbox clicked!')}
/>
```

## Example

```svelte
<Checkbox
  checked={false}
  class="custom-checkbox-class"
/>
```

In the example above, the `checked` prop controls the checkbox's state, and additional custom styles are added using the `class` prop.

## Indicator

- The `CheckboxPrimitive.Indicator` component is used to visually represent the checkbox state:
    - When the checkbox is **checked**, it shows the `Check` icon.
    - When the checkbox is **indeterminate**, it shows the `Minus` icon.
    - If neither is true, no icon is shown.

## Notes

- The checkbox's appearance and behavior are controlled using utility classes and the `cn` function to conditionally apply them.
- The `$$restProps` is spread into the root `CheckboxPrimitive.Root`, allowing for additional attributes to be passed down (e.g., `id`, `data-*` attributes).
