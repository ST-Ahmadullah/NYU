package com.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.baseClass.BaseClass;

public class LoginTest extends BaseClass {

	@Test
	public void testLoginPage() throws Throwable {
		// Navigate to the login page
		driver.get("https://nyu.webwizardsusa.com/admin/login");

		// Get the title of the page and print it
		String title = driver.getTitle();
		System.out.println("Page Title: " + title);

		// Assert the page title
		Assert.assertEquals(title, "NYU School of Law - Admin Login", "Page title does not match expected value.");

		// Login before logout
		driver.findElement(By.id("email")).sendKeys("noreply@locus.online");
		driver.findElement(By.id("password")).sendKeys("password");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(3000);

		// Logout functionality
		driver.findElement(By.id("menuButton")).click();
		driver.findElement(By.xpath("//p[text()='Logout']")).click();

		String currentUrl = driver.getCurrentUrl();
		Assert.assertTrue(currentUrl.contains("login"), "Logout failed, user is still logged in.");
	}
}