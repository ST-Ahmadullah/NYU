package com.base;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class BaseClass {

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	public static WebDriver initializeWebDriver(String browser) {
		WebDriver webDriver = null;
		try {
			switch (browser.toLowerCase()) {
			case "chrome":
				WebDriverManager.chromedriver().setup();
				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.addArguments("disable-infobars", "--disable-popup-blocking", "incognito");
				webDriver = new ChromeDriver(chromeOptions);
				break;
			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				FirefoxOptions firefoxOptions = new FirefoxOptions();
				firefoxOptions.addArguments("--disable-popup-blocking");
				webDriver = new FirefoxDriver(firefoxOptions);
				break;
			case "edge":
				WebDriverManager.edgedriver().setup();
				EdgeOptions edgeOptions = new EdgeOptions();
				edgeOptions.setCapability("ms:edgeOptions",
						Collections.singletonMap("args", Collections.singletonList("disable-popup-blocking")));
				webDriver = new EdgeDriver(edgeOptions);
				break;
			default:
				throw new IllegalArgumentException("Browser not supported: " + browser);
			}
			if (webDriver != null) {
				webDriver.manage().window().maximize();
				driver.set(webDriver);
			}
		} catch (Exception e) {
			handleException("WebDriver initialization error", e);
		}
		return driver.get();
	}

	public static void quitWebDriver() {
		WebDriver webDriver = driver.get();
		if (webDriver != null) {
			webDriver.quit();
			driver.remove();
		}
	}

	public static void navigate(String action, String url) {
		try {
			WebDriver webDriver = driver.get();
			if (webDriver == null)
				throw new IllegalStateException("WebDriver not initialized.");

			switch (action.toLowerCase()) {
			case "to":
				webDriver.navigate().to(url);
				break;
			case "forward":
				webDriver.navigate().forward();
				break;
			case "back":
				webDriver.navigate().back();
				break;
			case "refresh":
				webDriver.navigate().refresh();
				break;
			default:
				System.err.println("Invalid navigation action: " + action);
			}
		} catch (Exception e) {
			handleException("Navigation error", e);
		}
	}

	public static void performMouseAction(String action, WebElement element) {
		try {
			Actions actions = new Actions(driver.get());
			switch (action.toLowerCase()) {
			case "click":
				actions.click(element).perform();
				break;
			case "rightclick":
				actions.contextClick(element).perform();
				break;
			case "doubleclick":
				actions.doubleClick(element).perform();
				break;
			case "mousehover":
				actions.moveToElement(element).perform();
				break;
			default:
				System.err.println("Invalid mouse action: " + action);
			}
		} catch (Exception e) {
			handleException("Mouse action error", e);
		}
	}

	public static void handleAlert(String action, String input) {
		try {
			WebDriver webDriver = driver.get();
			if (webDriver == null)
				throw new IllegalStateException("WebDriver not initialized.");

			Alert alert = webDriver.switchTo().alert();
			switch (action.toLowerCase()) {
			case "accept":
				alert.accept();
				break;
			case "dismiss":
				alert.dismiss();
				break;
			case "sendkeys":
				alert.sendKeys(input);
				alert.accept();
				break;
			default:
				System.err.println("Invalid alert action: " + action);
			}
		} catch (NoAlertPresentException e) {
			System.err.println("No alert is present: " + e.getMessage());
		} catch (Exception e) {
			handleException("Alert handling error", e);
		}
	}

	public static void switchToFrame(String action, String value, WebElement element) {
		try {
			WebDriver webDriver = driver.get();
			if (webDriver == null)
				throw new IllegalStateException("WebDriver not initialized.");

			switch (action.toLowerCase()) {
			case "id":
				webDriver.switchTo().frame(value);
				break;
			case "index":
				webDriver.switchTo().frame(Integer.parseInt(value));
				break;
			case "element":
				webDriver.switchTo().frame(element);
				break;
			case "parent":
				webDriver.switchTo().parentFrame();
				break;
			case "main":
				webDriver.switchTo().defaultContent();
				break;
			default:
				System.err.println("Invalid frame action: " + action);
			}
		} catch (NoSuchFrameException e) {
			handleException("Frame not found", e);
		} catch (NumberFormatException e) {
			handleException("Invalid frame index", e);
		} catch (Exception e) {
			handleException("Frame switching error", e);
		}
	}

	public static void interactWithDropdown(String action, WebElement element, String valueOrText, int index) {
		try {
			Select select = new Select(element);
			switch (action.toLowerCase()) {
			case "value":
				select.selectByValue(valueOrText);
				break;
			case "index":
				select.selectByIndex(index);
				break;
			case "text":
				select.selectByVisibleText(valueOrText);
				break;
			case "ismultiple":
				System.out.println(select.isMultiple());
				break;
			default:
				System.err.println("Invalid dropdown action: " + action);
			}
		} catch (NoSuchElementException e) {
			handleException("Dropdown option not found", e);
		} catch (Exception e) {
			handleException("Dropdown interaction error", e);
		}
	}

	public static void waitForElement(String waitType, long timeout, WebElement element) {
		try {
			WebDriver webDriver = driver.get();
			if (webDriver == null)
				throw new IllegalStateException("WebDriver not initialized.");

			switch (waitType.toLowerCase()) {
			case "static":
				Thread.sleep(timeout);
				break;
			case "implicit":
				webDriver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
				break;
			case "explicit":
				WebDriverWait wait = new WebDriverWait(webDriver, timeout);
				wait.until(ExpectedConditions.visibilityOf(element));
				break;
			default:
				System.err.println("Invalid wait type: " + waitType);
			}
		} catch (InterruptedException e) {
			handleException("Static wait interrupted", e);
		} catch (NoSuchElementException e) {
			handleException("Element not found during explicit wait", e);
		} catch (Exception e) {
			handleException("Wait error", e);
		} finally {
			driver.get().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		}
	}

	public static void performAction(String action, WebElement element, int pixelValue) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver.get();
			switch (action.toLowerCase()) {
			case "scrollintoview":
				js.executeScript("arguments[0].scrollIntoView();", element);
				break;
			case "click":
				js.executeScript("arguments[0].click();", element);
				break;
			case "scrollby":
				js.executeScript("window.scrollBy(0, arguments[0]);", pixelValue);
				break;
			default:
				System.err.println("Invalid action: " + action);
			}
		} catch (Exception e) {
			handleException("JavaScript action error", e);
		}
	}

	public static void takeScreenshot(String location, String method) {
		try {
			TakesScreenshot ts = (TakesScreenshot) driver.get();
			File source = ts.getScreenshotAs(OutputType.FILE);
			File destination = new File(location);

			if (method.equalsIgnoreCase("FileUtils")) {
				FileUtils.copyFile(source, destination);
				System.out.println("Screenshot captured and saved at: " + location);
			} else {
				System.err.println("Invalid screenshot method: " + method);
			}
		} catch (IOException e) {
			handleException("Screenshot capture error", e);
		}
	}

	public static boolean closeAllWindows() {
		WebDriver webDriver = driver.get();
		if (webDriver == null)
			return false;

		String parentWindowHandle = webDriver.getWindowHandle();
		Set<String> allWindowHandles = webDriver.getWindowHandles();

		for (String windowHandle : allWindowHandles) {
			if (!windowHandle.equals(parentWindowHandle)) {
				try {
					webDriver.switchTo().window(windowHandle);
					webDriver.close();
				} catch (Exception e) {
					System.err.println("Error closing window: " + e.getMessage());
				}
			}
		}
		webDriver.switchTo().window(parentWindowHandle);
		return webDriver.getWindowHandles().size() == 1;
	}

	public static void clickOnElement(WebElement element) {
		try {
			if (element != null && element.isDisplayed() && element.isEnabled()) {
				element.click();
			} else {
				System.out.println("Element is not visible or clickable.");
			}
		} catch (Exception e) {
			handleException("Element click error", e);
		}
	}

	public static boolean isElementConditionMet(WebElement element, String condition) {
		boolean result = false;
		try {
			switch (condition.toLowerCase()) {
			case "enabled":
				result = element.isEnabled();
				break;
			case "selected":
				result = element.isSelected();
				break;
			case "displayed":
				result = element.isDisplayed();
				break;
			default:
				System.err.println("Invalid condition: " + condition);
			}
		} catch (Exception e) {
			handleException("Element condition check error", e);
		}
		return result;
	}

	public static void handleDropdown(WebElement element) {
		try {
			Select select = new Select(element);
			List<WebElement> options = select.getOptions();
			List<WebElement> allSelectedOptions = select.getAllSelectedOptions();
			WebElement firstSelectedOption = select.getFirstSelectedOption();

			System.out.println("Options in the dropdown:");
			if (!options.isEmpty()) {
				options.forEach(option -> System.out.println(option.getText()));
			} else {
				System.out.println("No options found in the dropdown.");
			}

			System.out.println("Selected options:");
			if (!allSelectedOptions.isEmpty()) {
				allSelectedOptions.forEach(option -> System.out.println(option.getText()));
			} else {
				System.out.println("No selected options found in the dropdown.");
			}

			System.out.println("First selected option:");
			if (firstSelectedOption != null) {
				System.out.println(firstSelectedOption.getText());
			} else {
				System.out.println("No first selected option found in the dropdown.");
			}
		} catch (Exception e) {
			handleException("Dropdown handling error", e);
		}
	}

	public static void simulateKeyEvent(int keyEvent) {
		try {
			Robot robot = new Robot();
			switch (keyEvent) {
			case KeyEvent.VK_UP:
				robot.keyPress(KeyEvent.VK_UP);
				robot.keyRelease(KeyEvent.VK_UP);
				System.out.println("Simulated VK_UP");
				break;
			case KeyEvent.VK_DOWN:
				robot.keyPress(KeyEvent.VK_DOWN);
				robot.keyRelease(KeyEvent.VK_DOWN);
				System.out.println("Simulated VK_DOWN");
				break;
			case KeyEvent.VK_ENTER:
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				System.out.println("Simulated VK_ENTER");
				break;
			default:
				System.out.println("Invalid key event");
			}
		} catch (AWTException e) {
			handleException("Key event simulation error", e);
		}
	}

	public static void getCurrentUrl() {
		WebDriver webDriver = driver.get();
		if (webDriver != null) {
			System.out.println(webDriver.getCurrentUrl());
		} else {
			System.out.println("WebDriver is not initialized.");
		}
	}

	public static void getTitle() {
		WebDriver webDriver = driver.get();
		if (webDriver != null) {
			System.out.println(webDriver.getTitle());
		} else {
			System.out.println("WebDriver is not initialized.");
		}
	}

	public static void getText(WebElement element) {
		if (element != null) {
			System.out.println(element.getText());
		} else {
			System.out.println("WebElement is null.");
		}
	}

	public static void getAttribute(WebElement element, String attributeName) {
		if (element != null) {
			System.out.println(element.getAttribute(attributeName));
		} else {
			System.out.println("WebElement is null.");
		}
	}

	public static void getUrl(String url) {
		WebDriver webDriver = driver.get();
		if (webDriver != null) {
			webDriver.get(url);
		} else {
			System.out.println("WebDriver is not initialized.");
		}
	}

	public static void sendKeys(WebElement element, String input) {
		if (element != null) {
			element.sendKeys(input);
		} else {
			System.out.println("WebElement is null.");
		}
	}

	public static void clear(WebElement element) {
		if (element != null) {
			element.clear();
		} else {
			System.out.println("WebElement is null.");
		}
	}

	public static void close() {
		WebDriver webDriver = driver.get();
		if (webDriver != null) {
			webDriver.close();
		} else {
			System.out.println("WebDriver is not initialized.");
		}
	}

	public static void quit() {
		WebDriver webDriver = driver.get();
		if (webDriver != null) {
			webDriver.quit();
			driver.remove();
		} else {
			System.out.println("WebDriver is not initialized.");
		}
	}

	private static void handleException(String message, Exception e) {
		System.err.println(message + ": " + e.getMessage());
		e.printStackTrace();
	}
}
