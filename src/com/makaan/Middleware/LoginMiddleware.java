package com.makaan.Middleware;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import org.eclipse.jdt.internal.compiler.ast.MarkerAnnotation;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.makaan.Dictionary.Login;
import com.makaan.Utilities.ConnectDB;
import com.makaan.Utilities.ExcelOperation;
import com.makaan.Utilities.Xls_Reader;
import com.makaan.Webhelper.Webhelper;

public class LoginMiddleware {

	public static Webhelper wb = null;
	public static Login dict = null;
	public static WebDriver driver = null;;
	public static ExcelOperation ex = null;
	public static ConnectDB db = null;
	public static Xls_Reader xls = new Xls_Reader("Files/Marketplace.xls");

	public LoginMiddleware() {
		wb = new Webhelper();
		ex = new ExcelOperation();
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

	public boolean LoginForm() throws Exception {
		if (wb.IsElementPresent(dict.Login)) {
			return true;

		} else {
			return false;
		}

	}

	public static boolean SocialLogin() throws Exception {
		wb.ClickbyXpath(dict.Login);
		Thread.sleep(2000);
		if (wb.IsElementPresent(dict.facebookLogin) && wb.IsElementPresent(dict.GoogleLoginWindow)) {
			System.out.println("Social login button  pesent");
			if (VerifyFBLogin() && VerifyGoogleLogin()) {
				System.out.println("Social login is verified");

			}

			else {
				wb.ClickbyXpath(dict.closeLogin);
				System.out.println("Social login can not be verified due to error");
				return false;
			}
		}
		return true;
	}

	public static boolean VerifyFBLogin() throws Exception {
		System.out.println("verifying fb login");

		driver = wb.getDriver();
		String parentHandle = driver.getWindowHandle();
		driver.findElement(By.xpath(dict.facebookLogin)).click();
		Thread.sleep(5000);

		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);

		}
		try {
			if (driver.findElement(By.id(dict.FBUserId)).isDisplayed()) {
				System.out.println("Element is present on Switch Window");
				driver.findElement(By.id(dict.FBUserId)).sendKeys(ReadSheet("Login", "UserName", 2));
				driver.findElement(By.id(dict.FBUserPass)).sendKeys(ReadSheet("Login", "Password", 2));
				driver.findElement(By.xpath(dict.FBLoginButton)).click();
				Thread.sleep(4000);

				driver.switchTo().window(parentHandle);
				/*String UserName = ReadSheet("Login", "UserName", 2);
				UserName = UserName.substring(0, 1);
				if (wb.getText(dict.VerifyMakaanLogin1).equalsIgnoreCase(UserName)) {
					System.out.println("Login successfull");
					Thread.sleep(2000);
					MakaanLogout();
					return true;
				} else {
					System.out.println("Fb Login doesnot happen");
					return false;
				}*/

			}
		} catch (NoSuchElementException ne) {
			System.out.println("Some error occurred in fb Login ");
			return false;
		}
		MakaanLogout();
		return true;
	}

	public static boolean VerifyGoogleLogin() throws Exception {

		System.out.println("verifying social login");
		Thread.sleep(2000);
		wb.ClickbyXpath(dict.Login);
		Thread.sleep(2000);
		driver = wb.getDriver();
		String parentHandle = driver.getWindowHandle();
		driver.findElement(By.xpath(dict.GoogleLoginWindow)).click();
		Thread.sleep(4000);

		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);

		}
		try {
			if (driver.findElement(By.xpath(dict.GoogleUserId)).isDisplayed()) {
				System.out.println("Element is present on Google form");
				driver.findElement(By.xpath(dict.GoogleUserId)).sendKeys(ReadSheet("Login", "UserName", 3));
				driver.findElement(By.xpath(dict.GoogleNext)).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath(dict.GooglePass)).sendKeys(ReadSheet("Login", "Password", 3));
				driver.findElement(By.xpath(dict.GoogleLogin)).click();
				Thread.sleep(5000);
				driver.switchTo().window(parentHandle);
			/*	String UserName = ReadSheet("Login", "UserName", 2);
				UserName = UserName.substring(0, 1);
				if (wb.getText(dict.VerifyMakaanLogin1).equalsIgnoreCase(UserName)) {
					System.out.println("Login successfull");
					Thread.sleep(2000);
					
					return true;
				} else {
					System.out.println("Fb Login doesnot happen");
					return false;
				}*/

			}
		} catch (NoSuchElementException ne) {
			System.out.println("can not find Google Used Id");
			return false;
		}
		MakaanLogout();
		return true;
	}

	public boolean MakaanLogin() throws Exception {
		System.out.println("inside mkaan login");
		Thread.sleep(1000);
		wb.ClickbyXpath(dict.Login);
		Thread.sleep(2000);
		if (wb.IsElementPresent(dict.BasicLogin)) {
			System.out.println("makaan login button is present on form");
			wb.ClickbyXpath(dict.BasicLogin);
			Thread.sleep(2000);
			if (wb.IsElementPresent(dict.MakaanForm)) {
				System.out.println("makaan login form is present");
				String Username = ReadSheet("Login", "UserName", 4);
				String Password = ReadSheet("Login", "Password", 4);
				wb.enterTextByID(dict.UserName, Username);
				wb.enterTextByID(dict.Password, Password);
				Thread.sleep(1000);
				wb.ClickbyXpath(dict.BasicLoginButton);
				Thread.sleep(4000);
				String Initials = Username.substring(0, 1);

				if (wb.getText(dict.VerifyMakaanLogin1).equalsIgnoreCase(Initials)) {
					System.out.println("Login successfull");
					Thread.sleep(2000);
					//MakaanLogout();
					return true;
				} else if (wb.IsElementPresent(dict.VerifyMakaanLogin)) {
					System.out.println("Some error occured while login");
					wb.ClickbyXpath(dict.closeLogin);
					return false;
				} else {
					return false;
				}

			} else {
				System.out.println("Not able to find makaan form");
				return false;
			}
		} else {
			System.out.println("Basic Login button does not found");
			return false;
		}

	}

	public static boolean ForgetPassword() throws Exception {
		System.out.println("inside Forgotpassword in middleware");
		Thread.sleep(2000);
		wb.ClickbyXpath(dict.Login);
		Thread.sleep(2000);

		wb.ClickbyXpath(dict.BasicLogin);
		Thread.sleep(2000);
		if (wb.IsElementPresent(dict.Forgotpassword)) {
			System.out.println("Forgot password button is present on form");
			wb.ClickbyXpath(dict.Forgotpassword);
			Thread.sleep(2000);
			if (wb.getText(dict.ForgetPasswordWindow).equals("reset password")) {
				System.out.println("Reset password windowis present");
				String Username = ReadSheet("Login", "UserName", 4);

				wb.enterTextByxpath(dict.ForgotpasswordInput, Username);
				Thread.sleep(1000);
				wb.ClickbyXpath(dict.ForgetSubmit);
				Thread.sleep(1000);
				String Message = wb.getText(dict.VerifyForgotPassword);
				if (Message.contains("sorry")) {
					System.out.println("Some error occured while reset password may be wrong email or network issue");
					wb.ClickbyXpath(dict.closeLogin);
					return false;
				} else if (Message.contains("a link to reset password has been sent to your email id")) {
					if (VerifyEmail(Username)) {
						System.out.println("successfully verify forgot password li nk");
						return true;
					} else
						return false;
				}
				return true;
			} else {
				System.out.println("Reset password button do not open");
				return false;
			}
		}

		else {
			System.out.println("Reset password button does not present");
			return false;
		}

	}

	public static boolean VerifyEmail(String data) throws InterruptedException {
		ResultSet rs = null;
		String id = null;
		String Query = "select * from users where email = '" + data + "' order by id desc limit 1;";
		String Database = "use user";
		Thread.sleep(20000);
		try {
			db.Connect();
			rs = db.Execute(Query, Database);
			while (rs.next()) {
				// Retrieve by column name
				id = rs.getString("id");
				System.out.println("id: " + id);
			}
			Query = "select * from notification_generated where user_id = " + id + " order by id desc limit 1;";
			Database = "use notification";
			rs = db.Execute(Query, Database);
			while (rs.next()) {
				// Retrieve by column name
				String status = rs.getString("status");
				System.out.println("status: of forgot password mail " + status);
				if (status.equals("Scheduled") || status.equals("Sent")) {
					return true;
				} else {
					System.out.println("mail was not sent");
					return false;
				}
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return true;
	}

	public static boolean ResetPassword() throws Exception {
		String URL = GetToken();
		wb.GetURL(URL);
		if (wb.IsElementPresentById(dict.Newpassword)) {
			wb.enterTextByID(dict.Newpassword, "abcd1234");
			wb.enterTextByID(dict.Confirmpassword, "abcd1234");
			wb.ClickbyXpath(dict.resetPassword);
			Thread.sleep(5000);
			if (wb.IsElementPresent(dict.Login)) {
				System.out.println("Reset password Case Passed");
			} else if (wb.IsElementPresent(dict.VerifyresetPassword)) {
				System.out.println("Some error occured while reseting password");
				return false;
			}
			return true;
		} else {
			System.out.println("URL did not open");

			return false;
		}

	}

	public static String GetToken() throws Exception {
		String data = ReadSheet("Login", "UserName", 4);
		ResultSet rs = null;
		String id = null;
		String URL = null;
		String Query = "select * from users where email = '" + data + "' order by id desc limit 1 ;";
		String Database = "use user";
		try {
			db.Connect();
			rs = db.Execute(Query, Database);
			while (rs.next()) {
				// Retrieve by column name
				id = rs.getString("id");
				System.out.println("id: " + id);
			}
			Query = "select * from forum_user_token where user_id =  " + id + ";";
			rs = db.Execute(Query, Database);
			while (rs.next()) {
				// Retrieve by column name
				String Token = rs.getString("token");
				System.out.println("token of forgot password mail " + Token);
				URL = ReadSheet("Login", "URL", 2);
				URL = URL.concat("/reset-password?token=");
				URL = URL.concat(Token);
			}

		} catch (SQLException se) {
			se.printStackTrace();
			return null;
		}
		return URL;
	}

	public static boolean MakaanLogout() throws Exception {
		System.out.println("inside MakaanLogout in middleware");

		wb.ClickbyXpath(dict.MenuDrawer);
		Thread.sleep(2000);
		if (wb.IsElementPresentById(dict.Logout)) {
			wb.ClickbyId(dict.Logout);
			Thread.sleep(4000);
			System.out.println("successfully logged out");
			return true;
		} else {
			return false;
		}

	}

	public boolean MakaanSignup() throws Exception {
		System.out.println("inside SignUp in middleware");
		Thread.sleep(2000);
		wb.ClickbyXpath(dict.Login);
		Thread.sleep(4000);
		wb.ClickbyXpath(dict.BasicLogin);
		Thread.sleep(4000);
		if (wb.IsElementPresent(dict.Signup)) {
			System.out.println("Create Acount button is present on form");
			wb.ClickbyXpath(dict.Signup);
			Thread.sleep(2000);
			if (wb.getText(dict.SignupWindow).equals("let the joy begin")) {
				System.out.println("Signup window is present");
				String Username = ReadSheet("Login", "UserName", 3);
				String Pass = ReadSheet("Login", "Password", 3);
				Username = Splitter(Username);
				wb.enterTextByID(dict.SignupName, Username);
				Thread.sleep(1000);
				wb.enterTextByID(dict.SignupEmail, Username);
				Thread.sleep(1000);
				wb.enterTextByID(dict.SignupPassword, Pass);
				Thread.sleep(1000);
				wb.ClickbyXpath(dict.SignupSubmit);
				Thread.sleep(3000);
				if (wb.getText(dict.VerifyMakaanLogin1).equalsIgnoreCase(Username.substring(0, 1))) {
					System.out.println("Login successfull");
					return true;
				}
				else if (wb.getText(dict.ErrorSignup).contains("User already registered")) {
					wb.ClickbyXpath(dict.closeLogin);
					return false;
				}

			} else {
				System.out.println("Signup button do not open");
				return false;
			}
			return true;
		} else {
			System.out.println("Create account button does not present");
			return false;
		}

	}

	public String Splitter(String Value) {
		Random r = new Random();
		int Low = 10;
		int High = 100;
		int Result = r.nextInt(High - Low) + Low;
		String front = Value.substring(0, 13);
		String back = Value.substring(13, 23);
		front = front + "+" + Result + back;
		System.out.println("String " + front);
		return front;

	}

	public static void CloseAll() {
		db.Close();
		wb.CloseBrowser();
	}

}
