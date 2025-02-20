package com.baseClass;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

public class BaseClass {

	// Declare WebDriver instance that will be shared by all test classes
	protected WebDriver driver;

	// Setup method to initialize the WebDriver before each test
	@BeforeMethod
	public void setUp() {
		// Fetch browser type from System properties (default to Chrome)
		String browser = System.getProperty("browser", "chrome");

		// Initialize WebDriver based on the browser choice
		switch (browser.toLowerCase()) {
		case "chrome":
			WebDriverManager.chromedriver().setup(); // Setup Chrome driver using WebDriverManager
			driver = new ChromeDriver();
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup(); // Setup Firefox driver using WebDriverManager
			driver = new FirefoxDriver();
			break;
		case "edge":
			WebDriverManager.edgedriver().setup(); // Setup Edge driver using WebDriverManager
			driver = new EdgeDriver();
			break;
		default:
			throw new IllegalArgumentException("Browser not supported: " + browser);
		}

		// Maximize the browser window for consistency in tests
		driver.manage().window().maximize();
	}

	// Teardown method to close the WebDriver after each test
	@AfterMethod
	public void tearDown() {
		// Close the browser after the test execution
		if (driver != null) {
			driver.quit();
		}
	}
}
