package com.makaan.Middleware;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import org.openqa.selenium.NoSuchElementException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.makaan.Dictionary.PYRDictionary;
import com.makaan.Utilities.ConnectDB;
import com.makaan.Utilities.ExcelOperation;
import com.makaan.Utilities.Xls_Reader;
import com.makaan.Webhelper.Webhelper;
//import com.sun.org.glassfish.external.statistics.annotations.Reset;

public class PYRMiddleware {

	public static Webhelper wb = null;
	public static PYRDictionary dict = null;
	public static WebDriver driver = null;;
	public static ConnectDB db = null;
	public static Xls_Reader xls = new Xls_Reader("Files/Marketplace.xls");

	public PYRMiddleware() {
		wb = new Webhelper();
		// ex = new ExcelOperation();
		db = new ConnectDB();

		System.out.println("inside Middleware Constructor");

	}

	public void OpenURL() throws Exception {

		String URL = ReadSheet("PYR", "URL", 2);
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

	public static boolean PYRBuy() throws Exception {
		System.out.println("Validate PYR form for BUY Tab");
		if (PYRRequirement("Buy")) {
			UserDetails();
			wb.ClickbyXpath(dict.PYRSubmit);
			Thread.sleep(2000);
			if (VerifyPYR()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean PYRRent() throws Exception {
		System.out.println("Validate PYR form for Rent Tab");
		Thread.sleep(3000);
		if (PYRRequirement("Rent")) {
			wb.ClickbyXpath(dict.PYRSubmit);
			Thread.sleep(2000);
			try{
				if (wb.IsElementPresent(dict.ErrorPage)) {
					Thread.sleep(20000);
				System.out.println("No Seller is available plz try later");
				wb.ClickbyXpath(dict.Errorclose);
				return false;
			}
			}catch(Exception e){
				
			}
			wb.ClickbyXpath(dict.ClosePopup);
			Thread.sleep(10000);
			if (VerifyEnquiry("rent")) {
				System.out.println("Enquiry is updated");
			} else {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	public static void UserDetails() throws Exception {
		System.out.println("Entering User Details");
		Thread.sleep(2000);
		String UserName = ReadSheet("PYR", "UserName", 2);
		wb.enterTextByxpath(dict.UserName, UserName);
		Thread.sleep(2000);
		String Email = ReadSheet("PYR", "Email", 2);
		wb.enterTextByxpath(dict.UserEmail, Email);
		Thread.sleep(2000);
		Random rnd = new Random();
		int n = 10000000 + rnd.nextInt(90000000);
		String value = String.valueOf(n);
		String phone = "98".concat(value);
		wb.enterTextByxpath(dict.UserPhone, phone);
		Thread.sleep(3000);

	}

	public static int GetOTP() throws Exception {
		System.out.println("Get OTP from database");
		ResultSet rs = null;
		String userID = null;
		int OTP = 0;
		String DB = "use user";
		String Query1 = "select id from users where email = '".concat(ReadSheet("PYR", "Email", 2)).concat("' ;");
		rs = db.Execute(Query1, DB);
		while (rs.next()) {
			userID = rs.getString("id");
			System.out.println("User Id is " + userID);
		}
		String Query2 = "select * from user.user_otps where user_id = '".concat(userID).concat("' ;");
		rs = db.Execute(Query2, DB);
		while (rs.next()) {
			OTP = rs.getInt("otp");
			System.out.println("OTP found in table is: " + OTP);
		}
		return OTP;
	}

	public static boolean VerifyEnquiry(String Type) throws Exception {
		System.out.println("Get Enquiry from database");
		String Database = "use proptiger ;";
		String Query = "Select * from ENQUIRY order by id desc limit 10; ";
		ResultSet rs = null;
		System.out.println("Verifying enquiry table");
		Thread.sleep(5000);
		db.Connect();
		rs = db.Execute(Query, Database);
		while (rs.next()) {
			String email = rs.getString("email");
			System.out.println(email);
			if (email.equals(ReadSheet("PYR", "Email", 2))) {
					if (rs.getString("Lead_Sale_Type").equalsIgnoreCase(Type)) {
						System.out.println("Enquiry Table is updated");
						return true;
					} else {
						System.out.println("Enquiry is not updated for " + Type);
						return false;
					}
			}
		}

		System.out.println("Enquiry Table is not updated");
		return false;
	}

	public static boolean VerifyPYR() throws Exception {
		System.out.println("Verifing PYR");
		int OTP = 0;
		try {
			if (wb.IsElementPresent(dict.OTPPage)) {
				System.out.println("PYR form submited");
				Thread.sleep(5000);
				db.Connect();
				OTP = GetOTP();
				wb.enterTextByxpath(dict.OTPBox, OTP);
				Thread.sleep(3000);
				System.out.println("Entered Otp");
				wb.ClickbyXpath(dict.ClosePopup);
				Thread.sleep(10000);
				if (VerifyEnquiry("buy")) {
					System.out.println("Enquiry is updated");
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			try {
				if (wb.IsElementPresent(dict.ErrorPage)) {
					Thread.sleep(2000);
					System.out.println("No Seller is available plz try later");
					wb.ClickbyXpath(dict.Errorclose);
					Thread.sleep(2000);
					return false;
				}
			} catch (Exception ne) {
				if (wb.IsElementPresent(".//div[@class='thanks-image']")) {
					System.out.println("Thanks image appeared case passed");
					wb.ClickbyXpath(dict.ClosePopup);
					Thread.sleep(10000);
					if (VerifyEnquiry("buy")) {
						System.out.println("Enquiry is updated");
						return true;
					}
					System.out.println("Enquiry table is not updated");
					return false;
				}
			}

		}
		return true;
	}

	public static boolean PYRRequirement(String Tab) throws Exception {
		System.out.println("Inside PYR VAlidation");
		wb.ClickbyXpath(dict.PYRLink);
		Thread.sleep(2000);

		if (wb.IsElementPresentById(dict.PYRForm)) {
			Thread.sleep(2000);
			if (Tab.equalsIgnoreCase("buy")) {
				wb.ClickbyXpath(dict.rentTab);
				Thread.sleep(2000);
				wb.ClickbyXpath(dict.buyTab);
				Thread.sleep(2000);
			} else if (Tab.equalsIgnoreCase("rent")) {
				wb.ClickbyXpath(dict.buyTab);
				Thread.sleep(2000);
				wb.ClickbyXpath(dict.rentTab);
				Thread.sleep(2000);
			}
			wb.Slider(dict.Slider);
			Thread.sleep(2000);
			System.out.println("Minimum Range is " + wb.getText(dict.MinBudget));
			System.out.println("MAximum Range is " + wb.getText(dict.MaxBudget));
			String Bedroom = ReadSheet("PYR", "Bedroom", 2);
			wb.ClickbyXpath(dict.Bedroom.concat(Bedroom).concat("']"));
			Thread.sleep(2000);
			wb.ClickbyXpath(dict.Property);
			Thread.sleep(1000);
			String PropertyType = ReadSheet("PYR", "Property Type", 2);
			wb.ClickbyXpath(dict.PropertyType.concat(PropertyType).concat("']"));

			Thread.sleep(3000);
			wb.ClickbyXpath(dict.Apply);

			Thread.sleep(2000);
			wb.ClickbyXpath(dict.Location);
			Thread.sleep(2000);
			String LocalityValue = ReadSheet("PYR", "Locality", 2);
			wb.enterTextByxpath(dict.Locality, LocalityValue);
			wb.getText(dict.GetfirstElement);
			wb.ClickbyXpath(dict.GetfirstElement);
			Thread.sleep(2000);
			wb.ClickbyXpath(dict.Apply);
			Thread.sleep(2000);

		} else {
			System.out.println("PYR form was not available");
			return false;
		}
		return true;
	}

	public static void CloseAll() {
		db.Close();
		wb.CloseBrowser();
	}
}
