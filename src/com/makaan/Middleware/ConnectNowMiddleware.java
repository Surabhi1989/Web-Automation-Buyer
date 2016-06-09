package com.makaan.Middleware;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.jboss.netty.util.internal.SystemPropertyUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.makaan.Dictionary.ConnectNow;
import com.makaan.Utilities.ConnectDB;
import com.makaan.Utilities.ExcelOperation;
import com.makaan.Utilities.Xls_Reader;
import com.makaan.Webhelper.Webhelper;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class ConnectNowMiddleware {

	public static Webhelper wb = null;
	public static ConnectNow dict = null;
	public static WebDriver driver = null;;
	public static ExcelOperation ex = null;
	public static ConnectDB db = null;
	public static Xls_Reader xls = new Xls_Reader("Files/Marketplace.xls");

	public ConnectNowMiddleware() {
		wb = new Webhelper();
		ex = new ExcelOperation();
		db = new ConnectDB();

		System.out.println("inside Middleware Constructor");

	}

	public void OpenURL() throws Exception {

		String URL = ReadSheet("CallNow", "URL", 2);
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

	public boolean ConnectNow() throws NoSuchElementException, Exception {
		System.out.println("Inside Call Now Test");
		//driver = wb.getDriver();
		Thread.sleep(2000);
		wb.Jsclickbyxpath(dict.ConnectNowButton);
		Thread.sleep(4000);		
		System.out.println(" element has been clicked ");
		if (wb.IsElementPresentById("leadPopup")) {
			System.out.println("Lead popup is present");
			Thread.sleep(2000);
			wb.ClickbyXpath(dict.MobileNumberInput);
			String MobileNumber = ReadSheet("CallNow", "Phone Number", 2);

			wb.enterTextByxpath(dict.MobileNumberInput, MobileNumber);
			Thread.sleep(2000);
			wb.ClickbyXpath(dict.CallNowSubmit);
			Thread.sleep(2000);

			if (wb.getText(dict.Error).contains("error")) {
				System.out.println("some error occurred while submiting Call Now Lead");
				return false;
			}

			
			else if (wb.IsElementPresent(dict.TringCallNow)) {
				System.out.println("Lead form has successfully submitted");
				System.out.println("Now submitting Lead for Share my number");
				Thread.sleep(2000);
				wb.ClickbyXpath(dict.ShareMyNumber);
				Thread.sleep(5000);
				if (wb.IsElementPresent(dict.Thank)) {
					Thread.sleep(2000);
					wb.Jsclickbyxpath(dict.ThanksSubmit);
					//wb.ClickbyXpath(dict.ThanksSubmit);
					Thread.sleep(2000);
					return true;
				}

				else if (wb.getText(dict.Error).contains("error")) {
					System.out.println("some error occurred while submiting Share My datails Lead");
					wb.ClickbyXpath(dict.Skip);
					Thread.sleep(2000);
					wb.ClickbyXpath(dict.ThanksSubmit);
					Thread.sleep(2000);
					// return false;
				}
			}

			return false;
		} else {
			System.out.println("Lead popup did not open");
			return false;
		}

	}

	public boolean ViewPhoneNumber() throws Exception {
		System.out.println("Inside ViewPhone Test");
		wb.Jsclickbyxpath(dict.ConnectNowButton);
		//wb.ClickbyXpath(dict.ConnectNowButton);
		Thread.sleep(4000);

		if (wb.IsElementPresentById("leadPopup")) {
			System.out.println("Lead popup is present");
			// Thread.sleep(2000);
			wb.ClickbyXpath(dict.ViewPhone);
			Thread.sleep(2000);
			if (wb.IsElementPresent(dict.SellerDetails)) {
				Thread.sleep(2000);
				System.out.println("seller detail is displayed");
				System.out.println("Phone number of selller is: " + wb.getText(dict.PhoneText));
				Thread.sleep(2000);
				wb.Jsclickbyxpath(dict.PhoneSubmit);
				//wb.ClickbyXpath(dict.PhoneSubmit);
				Thread.sleep(2000);
			} else {
				System.out.println("Seller details card was not present");
				return false;
			}
		} else {
			System.out.println("Lead popup did not open");
			return false;
		}
		System.out.println("Seller phone number printed");

		return true;

	}

	public boolean VerifyEnquiry() throws Exception {
		String Database = "use ptigercrm ;";
		String Query = "select * from LEAD_TMP_UPLOAD order by id desc limit 5";
		Thread.sleep(5000);
		db.Connect();
		String Phone = ReadSheet("CallNow", "Phone Number", 2);
		ResultSet rs = db.Execute(Query, Database);
		while (rs.next()) {
			String id = rs.getString("ID");
			if (rs.getString("MOBILE").equals(Phone)) {
				System.out.println("id " + id);
				System.out.println("Mobile number " + rs.getString("MOBILE"));
				return true;
			}
		}
		System.out.println("Can not find phone number in leads");
		return false;
	}

	public void CloseBrowser() {
		db.Close();
		wb.CloseBrowser();

	}

	public boolean ContactSellers() throws Exception {
		System.out.println("Inside Contact Seller Test");
		if (wb.IsElementPresent(dict.ContactSellerCard)) {
			//wb.ClickbyXpath(dict.PreviousContactSellerCard);
			wb.ClickbyXpath(".//div[@data-module='topSellerPyr']");
			wb.scrollup(".//div[@data-module='topSellerPyr']");
			Thread.sleep(2000);
			System.out.println("Text on page is " + wb.getText(dict.TitleContactSeller));
			Thread.sleep(2000);
			wb.ClickbyXpath(dict.InputPhone);
			Thread.sleep(2000);
			String PhoneNumber = ReadSheet("CallNow", "Phone Number", 2);
			wb.enterTextByxpath(dict.InputPhone, PhoneNumber);
			Thread.sleep(2000);
			wb.ClickbyXpath(dict.SubmitContactSeller);
			Thread.sleep(2000);

			if (wb.IsElementPresent(dict.ErrorSubmit)) {
				System.out.println("Some error occurred while contact seller");

			} else if (wb.IsElementPresent(dict.ThankContactSeller)) {
				System.out.println("Thanks Window displayed, enquiry submitted on UI");
				if (VerifyEnquiry()) {
					System.out.println("Enquiry was present in table");
					return true;
				} else {
					System.out.println("Not able to find lead in Enquiry table");
					return false;
				}
			}
			return false;

		} else {
			System.out.println("Contact Seller card was not present on SERP Page");
			return false;
		}

	}
}
