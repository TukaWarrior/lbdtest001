
# Button Component

This component wraps the `ButtonPrimitive.Root` from the `bits-ui` library to create a customizable button with multiple variants and sizes. The component provides flexibility to modify the button's appearance and behavior using props and event handlers.

## Props

- **variant**: Determines the visual style of the button. Possible values are defined in the `buttonVariants` function. Default is `"default"`.

- **size**: Specifies the button's size. The size options are also managed by the `buttonVariants` function. Default is `"default"`.

- **builders**: An array of builders that allow extending or modifying the button's behavior. Default is an empty array `[]`.

- **className**: Additional custom CSS classes that can be applied to the button. This can be useful for further styling the button beyond the provided variants and sizes.

## Events

- **on:click**: A click event handler that can be used to trigger actions when the button is clicked.

- **on:keydown**: A keydown event handler that can be used to capture keyboard interactions.

## Usage

```svelte
<script>
  import Button from './Button.svelte';
</script>

<Button 
  variant="primary" 
  size="large" 
  on:click={() => console.log('Button clicked!')}
>
  Click Me
</Button>
```

## Example

```svelte
<Button 
  variant="secondary" 
  size="small" 
  class="custom-button-class"
>
  Submit
</Button>
```

In the example above, the `variant` and `size` props define the button's appearance, and custom styles are added using the `class` prop.

## Notes

- The button styling and behavior are primarily controlled through the `buttonVariants` utility function, which takes the `variant`, `size`, and `className` parameters.
- The `$$restProps` is spread into the button, meaning any additional attributes (e.g., `id`, `data-*` attributes) will be passed to the underlying `ButtonPrimitive.Root`.
