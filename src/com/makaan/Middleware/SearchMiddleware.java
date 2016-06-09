package com.makaan.Middleware;

import com.makaan.Utilities.ConnectDB;
import com.makaan.Utilities.ExcelOperation;
import com.makaan.Utilities.Xls_Reader;
import com.makaan.Webhelper.Webhelper;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import com.makaan.Dictionary.Search;

public class SearchMiddleware {

	public static Webhelper wb = null;
	public static Search dict = null;
	public static WebDriver driver;
	public static ExcelOperation ex = null;
	public static ConnectDB db = null;
	public static Xls_Reader xls = new Xls_Reader("Files/Marketplace.xls");

	public SearchMiddleware() {
		System.out.println("inside Search Middleware Constructor");

		wb = new Webhelper();
		ex = new ExcelOperation();
		db = new ConnectDB();

		System.out.println("inside Middleware Constructor");

	}

	public void OpenURL() throws Exception {

		String URL = ReadSheet("Search", "URL", 2);
		wb.InitiateDriver();
		System.out.println("Opening URL through Middleware");
		wb.GetURL(URL);
		wb.WaitUntill(dict.MakaanLogo);

	}

	public String ReadSheet(String Sheet, String Col_Name, int row_id)
			throws IOException, NoSuchElementException, TimeoutException {

		String data = xls.getCellData(Sheet, Col_Name, row_id);
		System.out.println("Data from sheet " + data);

		return (data);
	}

	public boolean ValidateSearch() throws Exception {

		if (wb.IsElementPresentById(dict.SearchBox)) {
			System.out.println("Search box exist");
			if (wb.IsElementPresent(dict.BuyTab) && (wb.IsElementPresent(dict.RentTab))) {
				System.out.println("buy and renttab is present on search box");
			} else {
				System.out.println("buy and renttab is not present on search box");
				return false;
			}

		} else {
			System.out.println("an not find Search box on home page");
			return false;
		}

		return true;
	}

	public boolean ValidateSuggestions() throws Exception {
		List<String> arr = new ArrayList();
		Thread.sleep(2000);
		wb.ClickbyId(dict.SearchBox);
		Thread.sleep(2000);
		arr = wb.GetElementvalues(dict.Suggestiontype);
		for (int i = 0; i < arr.size(); i++) {
			System.out.println("element on Search bar is: " + arr.get(i));
			if (arr.get(i).contains("delhi")) {
				System.out.println("Suggestions are of delhi as user located in gurgaon");
			} else {
				return false;
			}
		}
		return true;

	}

	public boolean ValidateBuyLocality() throws Exception {
		if (ValidateGeneric("Buy", "Location")) {
			System.out.println("All Localities been Tested for Buy Tab Successfuly");
			return true;
		} else {
			System.out.println("All Localities Tested for Buy Tab are not successfull");
			return false;
		}

	}

	public boolean ValidateRentLocality() throws Exception {
		if (ValidateGeneric("Rent", "Location")) {
			System.out.println("All Localities been Tested for Rent Tab Successfuly");
			return true;
		} else {
			System.out.println("All Localities Tested for Rent Tab are not successfull");
			return false;
		}

	}

	public boolean ValidateBuyProject() throws Exception {
		if (ValidateGeneric("Buy", "Project")) {
			System.out.println("All Project has been Tested for Buy Tab Successfuly");
			return true;
		} else {
			System.out.println("All Project Tested for Buy Tab are not successfull");
			return false;
		}

	}

	public boolean ValidateRentProject() throws Exception {
		if (ValidateGeneric("Rent", "Project")) {
			System.out.println("All Project has been Tested for Rent Tab Successfuly");
			return true;
		} else {
			System.out.println("All Project Tested for Rent Tab are not successfull");
			return false;
		}

	}

	public boolean ValidateBuilder() throws Exception {
		if (ValidateGeneric("Buy", "Builder")) {
			System.out.println("All Builder has been Tested for Buy Tab Successfuly");
			return true;
		} else {
			System.out.println("All Builder Tested for Buy Tab are not successfull");
			return false;
		}

	}

	public boolean ValidateLandmarksBuy() throws Exception {
		if (ValidateGeneric("Buy", "Landmark")) {
			System.out.println("All Landmarks has been Tested for Buy Tab Successfuly");
			return true;
		} else {
			System.out.println("All Landmark Tested for Buy Tab are not successfull");
			return false;
		}

	}

	public boolean ValidateLandmarksRent() throws Exception {
		if (ValidateGeneric("Rent", "Landmark")) {
			System.out.println("All Landmarks has been Tested for Rent Tab Successfuly");
			return true;
		} else {
			System.out.println("All Landmark Tested for Rent Tab are not successfull");
			return false;
		}

	}

	public boolean ValidateSuburbRent() throws Exception {
		if (ValidateGeneric("Rent", "Suburb")) {
			System.out.println("All suburb has been Tested for Rent Tab Successfuly");
			return true;
		} else {
			System.out.println("All suburb Tested for Rent Tab are not successfull");
			return false;
		}

	}

	public boolean ValidateSuburbBuy() throws Exception {
		if (ValidateGeneric("Buy", "Suburb")) {
			System.out.println("All suburb has been Tested for Buy Tab Successfuly");
			return true;
		} else {
			System.out.println("All Suburb Tested for Buy Tab are not successfull");
			return false;
		}

	}

	public boolean ValidateLandmarkBuy() throws Exception {
		if (ValidateGeneric("Buy", "Landmark")) {
			System.out.println("All Landmark has been Tested for Buy Tab Successfuly");
			return true;
		} else {
			System.out.println("All Landmark Tested for Buy Tab are not successfull");
			return false;
		}

	}

	public boolean ValidateLandmarkRent() throws Exception {
		if (ValidateGeneric("Rent", "Landmark")) {
			System.out.println("All Landmark has been Tested for rent Tab Successfuly");
			return true;
		} else {
			System.out.println("All Landmark Tested for Rent Tab are not successfull");
			return false;
		}

	}

	public boolean ValidateGeneric(String Tab, String Column) throws Exception {
		List<String> arr = new ArrayList();
		Thread.sleep(2000);
		String TabSelect = null;
		wb.ClickbyXpath(dict.SearchBoxInput);
		Thread.sleep(2000);
		List<String> arr1 = new ArrayList();
		if (Tab.equals("Buy")) {
			TabSelect = dict.BuyTab;
		} else if (Tab.equals("Rent")) {
			TabSelect = dict.RentTab;
		}
		for (int i = 2; i < 6; i++) {
			arr1.add(ReadSheet("search", Column, i));
		}
		for (int j = 0; j < arr1.size(); j++) {
			wb.ClickbyXpath(TabSelect);
			Thread.sleep(3000);
			wb.enterTextByxpath(dict.SearchBoxInput, arr1.get(j));
			System.out.println("data in array sheet is: " + arr1.get(j));
			Thread.sleep(3000);

			arr = wb.GetElementvalues(dict.Suggestiontype);
			if (Column.equals("Landmark")) {
				if (wb.getText(dict.WrongSearch).contains("sorry")) {
					System.out.println("No Search Suggestions found");
					return false;
				}
				else if ((arr.get(0).contains(arr1.get(j))) && ((wb.getText(dict.Propertytype)).equalsIgnoreCase(Column))) {
					System.out.println("Search for Landmark is matching with data provided: " + arr.get(0));
					//wb.ClickbyXpath();
				}  
				else
					return false;
			}
			
			else if (Column.equals("Builder")) {
				if (wb.getText(dict.WrongSearch).contains("sorry")) {
					System.out.println("No Search Suggestions found");
					return false;
				}
				else if ((arr.get(0).contains(arr1.get(j))) && ((wb.getText(dict.Propertytype)).equalsIgnoreCase(Column))) {
					System.out.println("Search for Builder is matching with data provided: " + arr.get(0));
				}  
				else
					return false;
			}

			else {
				if (arr.get(0).equalsIgnoreCase(arr1.get(j))) {
					System.out.println("Search for Locality is matching with data provided: " + arr.get(0));
				} else if (wb.getText(dict.WrongSearch).contains("sorry")) {
					System.out.println("No Search Suggestions found");
					return false;
				} else
					return false;
			}

			if (wb.IsElementSelected(dict.BuyTab)) {
				wb.ClickbyXpath(dict.RentTab);
				Thread.sleep(3000);
			} else if (wb.IsElementSelected(dict.RentTab)) {
				wb.ClickbyXpath(dict.BuyTab);
				Thread.sleep(3000);

			}
		}

		return true;

	}
	
	public String ValidateURL() throws Exception{
		String URL = wb.CurrentURL();
		Thread.sleep(2000);
		wb.Back();
		return URL;
	}

	public static void CloseAll() {
		db.Close();
		wb.CloseBrowser();
	}
}
