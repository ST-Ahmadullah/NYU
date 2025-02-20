package com.helper;

import org.openqa.selenium.WebDriver;

import com.pom.Login;
import com.pom.Logout;

public class PageObjectManager {

	private WebDriver driver;
	private Login loginPage;
	private Logout logoutPage;

	public PageObjectManager(WebDriver driver) {
		this.driver = driver;
	}

	// Get instance of Login page
	public Login getLoginInstance() {
		if (loginPage == null) {
			loginPage = new Login(driver);
		}
		return loginPage;
	}

	// Get instance of Logout page
	public Logout getLogoutInstance() {
		if (logoutPage == null) {
			logoutPage = new Logout(driver);
		}
		return logoutPage;
	}
}