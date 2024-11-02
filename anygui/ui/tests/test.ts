import { expect, test } from '@playwright/test';

// Test to check if the homepage renders the h1 element correctly
test('home page has expected h1', async ({ page }) => {
	await page.goto('/');
	await expect(page.locator('h1')).toBeVisible();
});
