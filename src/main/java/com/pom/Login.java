package com.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login {

	private WebDriver driver;

	@FindBy(id = "email")
	private WebElement emailInput;

	@FindBy(id = "password")
	private WebElement passwordInput;

//	@FindBy(xpath = "//button[@type='submit']")
	@FindBy(xpath = "//span[normalize-space()='sign in']")
	private WebElement signInButton;

	// Constructor to initialize WebDriver and elements
	public Login(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this); // Initialize elements using PageFactory
	}

	public WebElement getUsernameInput() {
		return emailInput;
	}

	public WebElement getPasswordInput() {
		return passwordInput;
	}

	public WebElement getSignInButton() {
		return signInButton;
	}

	public WebDriver getDriver() {
		return driver;
	}
}
