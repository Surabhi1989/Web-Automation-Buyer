package com.makaan.Middleware;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;


import com.makaan.Dictionary.Home;
import com.makaan.Utilities.ConnectDB;
import com.makaan.Utilities.Xls_Reader;
import com.makaan.Webhelper.Webhelper;

public class HomeMiddleware {

	public static Webhelper wb = null;
	public static Home dict = null;
	 static WebDriver driver = null;
	
	public static ConnectDB db = null;
	public static Xls_Reader xls = new Xls_Reader("Files/Marketplace.xls");

	public HomeMiddleware() {
		wb = new Webhelper();

		db = new ConnectDB();

		System.out.println("inside Middleware Constructor");

	}

	public void OpenURL() throws Exception {

		String URL = ReadSheet("Login", "URL", 2);
		wb.InitiateDriver();
		System.out.println("Opening URL through Middleware");
		wb.GetURL(URL);
		wb.WaitUntill(dict.MakaanLogo);

	}

	public static String ReadSheet(String Sheet, String Col_Name, int row_id)
			throws IOException, NoSuchElementException, TimeoutException {

		String data = xls.getCellData(Sheet, Col_Name, row_id);
		System.out.println("Data from sheet " + data);

		return (data);
	}

	public boolean VerifyAppButton() throws Exception {
		System.out.println("Inside test Validate Download button");
		if (wb.IsElementPresent(dict.DownloadApp)) {
			System.out.println("download app button is present");
			String URL = SwitchWindow(dict.DownloadApp);
			Thread.sleep(1000);
			if (URL.contains("apps")) {
				System.out.println("App button is verified");
			}

		} else {
			System.out.println("Download app button was not present");
			return false;
		}
		return true;

	}

	public boolean VerifySeller() throws Exception {
		System.out.println("Inside test Verify Seller Link");
		if (wb.IsElementPresent(dict.SellerLink)) {
			System.out.println("seller link is present");
			String URL = SwitchWindow(dict.SellerLink);
			Thread.sleep(2000);
			if (URL.contains("seller")) {
				System.out.println("Seller link is verified");
			}

		} else {
			System.out.println("Seller link was not present");
			return false;
		}
		return true;

	}

	public boolean VerifyBuyerJourney() throws Exception {
		System.out.println("Inside test Verify Buyer Journey Link");
		if (wb.IsElementPresent(dict.Buyerjourney)) {
			System.out.println("Buyer Journey link is present");
			String URL = NewTab(dict.Buyerjourney);
			if (URL.contains("journey")) {
				System.out.println("Buyer Journey link is verified");
			}

		} else {
			System.out.println("Buyer Journey link was not present");
			return false;
		}
		return true;

	}

	public boolean VerifyMenuDrawer() throws Exception {
		System.out.println("Inside test Verify Menu Drawer Link");
		String URL = null;
		if (wb.IsElementPresent(dict.MenuDrawer)) {
			System.out.println("Menu Drawer is present");
			wb.ClickbyXpath(dict.MenuDrawer);
			Thread.sleep(2000);
			if (wb.IsElementPresent(dict.MenuDrawerJourney)) {
				System.out.println("Buyer Journey in drawer is present");
				URL = NewTab(dict.MenuDrawerJourney);
				if (URL.contains("journey")) {
					System.out.println("Buyer Journey link is verified");
				} else {
					System.out.println("Buyer Journey link is not verified");
					return false;
				}

			}
			URL = ValidateLink(dict.TopCities, dict.MummbaiCity);
			if (URL.contains("mumbai")) {
				System.out.println("Top city link is verified");
			} else {
				System.out.println("Top Cities link is not verified");
				return false;
			}
			URL = ValidateLink(dict.TopBuilder, dict.DLF);
			if (URL.contains("dlf")) {
				System.out.println("Top builder is verified");
			} else {
				System.out.println("Top Builder link is not verified");
				return false;
			}

			URL = ValidateLink(dict.TopBrokers, dict.AbbeyHomes);
			if (URL.contains("all")) {
				System.out.println("Top broker is verified");
			} else {
				System.out.println("Top Broker link is not verified");
				return false;
			}
			wb.ClickbyXpath(dict.MenuDrawer);
			Thread.sleep(2000);
			if (wb.IsElementPresent(dict.MakaanIQ)) {
				System.out.println("MakaanIQ in drawer is present");
				URL = SwitchWindow(dict.MakaanIQ);
				if (URL.contains("makaaniq")) {
					System.out.println("makaan IQ link is verified");
				} else {
					System.out.println("makaan Iq link is not verified");
					return false;
				}

			}
			Thread.sleep(2000);
			if (wb.IsElementPresent(dict.MenuDrawerApp)) {
				System.out.println("Download App in drawer is present");
				URL = SwitchWindow(dict.MenuDrawerApp);
				if (URL.contains("apps")) {
					System.out.println("App link in drawer is verified");
				} else {
					System.out.println("App link in drawer is not verified");
					return false;
				}

			}
		}
		return true;

	}

	public String ValidateLink(String Type, String Subtype) throws Exception {
		String URL = null;
		wb.ClickbyXpath(dict.MenuDrawer);
		Thread.sleep(2000);
		if (wb.IsElementPresent(Type)) {
			System.out.println("Top Builder in drawer is present");
			wb.ClickbyXpath(Type);
			Thread.sleep(2000);
			URL = NewTab(Subtype);
		}
		return URL;
	}

	public static void CloseAll() {
		db.Close();
		wb.CloseBrowser();
	}

	public String NewTab(String Path) throws Exception {
		wb.ClickbyXpath(Path);
		Thread.sleep(2000);
		String URL = wb.CurrentURL();
		Thread.sleep(2000);
		wb.Back();
		return URL;
	}

	public String SwitchWindow(String Path) throws Exception {
		driver = wb.getDriver();
		String parentHandle = driver.getWindowHandle();
		driver.findElement(By.xpath(Path)).click();
		Thread.sleep(2000);
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
		String URL = driver.getCurrentUrl();
		System.out.println("URL of opened window is " + URL);
		driver.close();
		driver.switchTo().window(parentHandle);
		return URL;
	}
	
	public boolean DownloadAppSection() throws Exception {
		wb.PageRefresh();
		Thread.sleep(2000);
		wb.scrolldown(dict.DownloadAppSection);
		Thread.sleep(2000);
		wb.enterTextByxpath(dict.Inputnumber,"9816987656");
		Thread.sleep(2000);
		wb.ClickbyXpath(dict.GetApp);
		Thread.sleep(2000);
		if(wb.getText(dict.ErrorApp).contains("successfull")){
			System.out.println("App link send successfully sent");
			
		}else{
			System.out.println("app link was not successfully sent");
			return false;
		}
		return true;	
	}

}
