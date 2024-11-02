import { expect, test } from '@playwright/test';

// Test to check if the TextField displays validation message when input is invalid
test('text field shows validation message on invalid input', async ({ page }) => {
  // Arrange: Navigate to the page where the text field is located
  await page.goto('/');

  // Arrange: Locate the elements needed for the test
  const textField = page.locator('input[id="2"]'); // assuming the ID is '2' based on your mock data
  const validationMessage = page.locator('p.text-destructive');

  // Act: Focus on the text field and blur without entering any value
  await textField.focus();
  await textField.evaluate((node: HTMLInputElement) => node.blur());

  // Assert: Check that the validation message is displayed
  await expect(validationMessage).toBeVisible();
  await expect(validationMessage).toHaveText('Field is required and must be max. 10 characters');

  // Act: Fill the text field with more characters than allowed
  await textField.fill('This is more than 10 characters');
  await textField.evaluate((node: HTMLInputElement) => node.blur());

  // Assert: Check that the validation message is still displayed
  await expect(validationMessage).toBeVisible();
  await expect(validationMessage).toHaveText('Field is required and must be max. 10 characters');
});

// Test to check if the TextField updates value correctly on valid input
test('text field updates value correctly on valid input', async ({ page }) => {
  // Arrange: Navigate to the page where the text field is located
  await page.goto('/');

  // Arrange: Locate the elements needed for the test
  const textField = page.locator('input[id="2"]'); // assuming the ID is '2' based on your mock data
  const validationMessage = page.locator('p.text-destructive');

  // Act: Fill the input with a valid value and blur to trigger the update
  await textField.fill('Valid');
  await textField.evaluate((node: HTMLInputElement) => node.blur());

  // Assert: Ensure no validation message is displayed (input is valid)
  await expect(validationMessage).not.toBeVisible();
});
