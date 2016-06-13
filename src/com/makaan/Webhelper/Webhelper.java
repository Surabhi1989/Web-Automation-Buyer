package com.makaan.Webhelper;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Webhelper {

	static WebDriver driver = null;

	public static void InitiateDriver() {

		FirefoxProfile profile = new FirefoxProfile();

		profile.setPreference("browser.startup.homepage_override.mstone", "ignore");
		profile.setPreference("startup.homepage_welcome_url", "about:blank");
		profile.setPreference("startup.homepage_welcome_url.additional", "about:blank");
		profile.setPreference("browser.startup.homepage", "about:blank");
		System.setProperty("webdriver.firefox.bin", "/usr/bin/firefox");
		driver = new FirefoxDriver(profile);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

	}

	public void GetURL(String URL) throws Exception {
		System.out.println("opening URL throgh webhelper");
		System.out.println(URL + " url in webhelper");
		driver.get(URL);
		Thread.sleep(2000);
		busyIndicationWait(driver);
		driver.navigate().refresh();

	}

	public void busyIndicationWait(WebDriver driver) throws InterruptedException {

		new WebDriverWait(driver, 50000)
				.until(ExpectedConditions.invisibilityOfElementLocated(By.className("updating")));
		Thread.sleep(3000);
	}

	public void WaitUntill(String element) {
		try {
			WebDriverWait wait1 = new WebDriverWait(driver, 20);
			wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath(element)));
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public boolean IsElementPresent(String element) {
		if (driver.findElement(By.xpath(element)).isDisplayed()) {
			System.out.println("Element is present " + element);
			return true;
		} else {
			return false;
		}

	}

	public boolean IsElementPresentById(String element) {
		if (driver.findElement(By.id(element)).isDisplayed()) {
			System.out.println("Element is present " + element);
			return true;
		} else {
			return false;
		}

	}

	public WebDriver getDriver() {
		return driver;
	}
	/*
	 * public HtmlUnitDriver getDriver() { return driver; }
	 */

	public static void ClickbyXpath(String webElement) {
		driver.findElement(By.xpath(webElement)).click();
		System.out.println("inside webhelper click element" + webElement);
	}

	public static void ClickbyLink(String webElement) {
		try {
			driver.findElement(By.linkText(webElement)).click();
			System.out.println("inside webhelper click element by link" + webElement);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public static void HoverandClick(String HoverPath, String clickPath) {

		Actions action = new Actions(driver);
		WebElement we = driver.findElement(By.xpath(HoverPath));
		action.moveToElement(we).moveToElement(driver.findElement(By.xpath(clickPath))).click().build().perform();
	}

	public void ClickbyId(String Id) {

		driver.findElement(By.id(Id)).click();

	}

	public void CloseBrowser() {
		driver.close();
		driver.quit();

	}

	public void enterTextByID(String Path, String Value) {
		WebElement element = driver.findElement(By.id(Path));
		element.sendKeys(Value);
	}

	public void enterTextByxpath(String Path, String Value) {

		WebElement element = driver.findElement(By.xpath(Path));
		element.sendKeys(Value);

	}

	public void enterTextByxpath(String Path, int Value) {

		WebElement element = driver.findElement(By.xpath(Path));

		element.sendKeys(String.valueOf(Value));

	}

	public void findByxpath(String Path) {

		WebElement element = driver.findElement(By.xpath(Path));

	}

	public void clickbycss(String Path) {

		driver.findElement(By.cssSelector(Path)).click();

	}

	public String getText(String Element) {
		String text = driver.findElement(By.xpath(Element)).getText();
		System.out.println("Text on page throgh get text is: " + text);
		return text;
	}

	public boolean IsElementPresentbyLink(String element) {
		try {
			driver.findElement(By.linkText(element)).isDisplayed();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean IsElementSelected(String element) {
		try {
			driver.findElement(By.xpath(element)).isSelected();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public List<String> GetElementvalues(String Path) {
		try {
			List<WebElement> elements = driver.findElements(By.xpath(Path));
			List<String> str = new ArrayList<String>();
			for (int i = 0; i < elements.size(); i++) {
				str.add(elements.get(i).getText());
			}
			return str;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void Slider(String Path) throws Exception {

		WebElement slider = driver.findElement(By.xpath(Path));
		Thread.sleep(3000);

		Actions moveSlider = new Actions(driver);
		Action action = moveSlider.dragAndDropBy(slider, 30, 0).build();

		action.perform();

	}

	public void Back() {
		driver.navigate().back();
	}

	public String CurrentURL() {
		String URL = driver.getCurrentUrl();
		return URL;
	}

	public void scrollup(String Path) throws InterruptedException {

		WebElement element = driver.findElement(By.xpath(Path));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
		Thread.sleep(4000);
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-300)");
		Thread.sleep(4000);

	}

	public void scrolldown() throws InterruptedException {

		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,300)");
		Thread.sleep(4000);

	}

	public void scrolldown(String Path) throws InterruptedException {

		WebElement element = driver.findElement(By.xpath(Path));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
		Thread.sleep(4000);
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,800)");
		Thread.sleep(4000);

	}
	 
	public void Jsclickbyxpath(String Path){
		JavascriptExecutor js =(JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", driver.findElement(By.xpath(Path)));
		
	}
	
	public void PageRefresh()

	{
		driver.navigate().refresh();

	}

}
