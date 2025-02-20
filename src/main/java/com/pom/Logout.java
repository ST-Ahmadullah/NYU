package com.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Logout {

	private WebDriver driver;

	// WebElement representing the menu button for logout
	@FindBy(id = "menuButton")
	private WebElement menuButton;

	// WebElement representing the logout button
	@FindBy(xpath = "//p[normalize-space()='Logout']")
	private WebElement logoutButton;

	// Constructor to initialize WebDriver and WebElements
	public Logout(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this); // Initialize elements using PageFactory
	}

	// Getter method for the menu button WebElement
	public WebElement getMenuButton() {
		return menuButton;
	}

	// Getter method for the logout button WebElement
	public WebElement getLogoutButton() {
		return logoutButton;
	}

	public WebDriver getDriver() {
		return driver;
	}
}
