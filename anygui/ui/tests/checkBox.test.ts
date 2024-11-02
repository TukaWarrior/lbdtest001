import { expect, test } from '@playwright/test';

// Test: Checkbox (button) toggles correctly when clicked
test('checkbox button toggles on click', async ({ page }) => {
  // Arrange: Navigate to the page where the checkbox button is located
  await page.goto('/');

  // Arrange: Locate the button element by its ID
  const checkboxButton = page.locator('button[id="3"]');

  // Assert: Verify that the button is initially unchecked
  await expect(checkboxButton).toHaveAttribute('aria-checked', 'false');

  // Act: Click the button to check it
  await checkboxButton.click();

  // Assert: Verify that the button is now checked
  await expect(checkboxButton).toHaveAttribute('aria-checked', 'true');

  // Act: Click the button again to uncheck it
  await checkboxButton.click();

  // Assert: Verify that the button is unchecked again
  await expect(checkboxButton).toHaveAttribute('aria-checked', 'false');
});
