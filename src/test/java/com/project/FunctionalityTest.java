package com.project;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.base.BaseClass;
import com.helper.FileReaderManager;
import com.helper.PageObjectManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class FunctionalityTest extends BaseClass {

	private WebDriver driver;
	private PageObjectManager pom;
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(FunctionalityTest.class);

	@BeforeClass
	public void setUp() {
		// Initialize WebDriver and PageObjectManager
		driver = BaseClass.initializeWebDriver("chrome");
		pom = new PageObjectManager(driver);

		// Configure Log4j properties file path
		PropertyConfigurator
				.configure("E:\\Eclipse Projects Workspace\\NewYorkUniversity\\src\\main\\resources\\log4j.properties");
	}

	@Test
	public void testLoginLogoutFunctionality() throws Throwable {

		String baseUrl = FileReaderManager.getInstance().getConfigurationReader().getUrl();

		driver.get(baseUrl + "/admin/login");

		String currentUrl = driver.getCurrentUrl();
		Assert.assertTrue(currentUrl.contains(baseUrl + "/admin/login"), "Expected URL is not opened");

		String email = FileReaderManager.getInstance().getConfigurationReader().getEmail();
		sendKeys(pom.getLoginInstance().getUsernameInput(), email);
		String password = FileReaderManager.getInstance().getConfigurationReader().getPassword();
		sendKeys(pom.getLoginInstance().getPasswordInput(), password);
		Thread.sleep(3000);
		performMouseAction("click", pom.getLoginInstance().getSignInButton());

		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//span[normalize-space()='Create New Classes']")).click();
		
		WebElement inputField = driver.findElement(By.id("title"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", inputField);
		inputField.sendKeys("AS");

//		performMouseAction("click", pom.getLogoutInstance().getMenuButton());
//		Thread.sleep(3000);
//		performAction("click", pom.getLogoutInstance().getLogoutButton(), 0);
//
//		log.info("Login and logout test executed successfully.");
	}

//	@AfterClass
//	public void tearDown() {
//
//		if (driver != null) {
//			driver.quit();
//		}
//	}
}