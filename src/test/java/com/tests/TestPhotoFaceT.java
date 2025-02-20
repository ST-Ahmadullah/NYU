package com.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestPhotoFaceT {

	private WebDriver driver;

	@Test
	public void setUp() throws Throwable {
		WebDriverManager.chromedriver().setup();
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("disable-infobars", "--disable-popup-blocking", "incognito");

		driver = new ChromeDriver(chromeOptions);

		driver.manage().window().maximize();

		driver.get("https://photofacet-fe.sumanas.xyz/");

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()=' Sign in ']"))).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email"))).sendKeys("admin@sumanastech.com");
		driver.findElement(By.cssSelector("input[name='password']")).sendKeys("123456789");
		driver.findElement(By.cssSelector("button[type='submit']")).click();

		Thread.sleep(2000);

		driver.findElement(By.xpath("//a[@class='MuiBox-root mui-66a9lb']")).click();

		Thread.sleep(2000);

		driver.findElement(By.xpath("//a[text()='Add']")).click();

		// driver.findElement(By.xpath("//a[contains(@class,'MuiButton-containedPrimary')]")).click();

		Thread.sleep(2000);
		driver.findElement(By.id("mui-component-select-category_id")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//li[text()='Marriage']")).click();

	}
}