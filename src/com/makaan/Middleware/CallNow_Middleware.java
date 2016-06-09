package com.makaan.Middleware;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeoutException;
import java.sql.*;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import com.makaan.Utilities.ConnectDB;
import com.makaan.Utilities.ExcelOperation;
import com.makaan.Utilities.Xls_Reader;
import com.makaan.Webhelper.Webhelper;
import com.makaan.Dictionary.Call_Now;

public class CallNow_Middleware {

	

	public static Webhelper wb = null;
	public static Call_Now cn = new Call_Now();
	public static WebDriver driver;
	public static ExcelOperation ex = null;
	public static ConnectDB db = null;
	public static Xls_Reader xls = new Xls_Reader("Files/Marketplace.xls");

	/*
	 * Constructor Calling
	 */
	public CallNow_Middleware() {
		wb = new Webhelper();
		ex = new ExcelOperation();
		db = new ConnectDB();
		
		
		
		System.out.println("inside Middleware Constructor");

	}

	/*
	 * This Function is responsible for Open Url by calling Webhelper Class
	 * object
	 */
	
	public String ReadSheet(String Sheet, String Col_Name, int row_id) throws IOException, NoSuchElementException, TimeoutException{
	
		String data = xls.getCellData(Sheet,Col_Name,row_id);
		System.out.println("Data from sheet " + data);
		
		return(data);
	}
	
	/*
	 * This Function is responsible for Open Url by calling Webhelper Class
	 * object
	 */
	
	public void OpenURL() throws Exception {
		Boolean result = false;

		String URL=	ReadSheet("CallNow","URL",2);
		wb.InitiateDriver();
		System.out.println("Opening URL through Middleware");
		wb.GetURL(URL);
		wb.WaitUntill(cn.makaan_logo);
		
	}
	
	public void DataBaseValidate(){
		ResultSet rs = null;
		HashMap<String,String>  hm = new HashMap();
		String Query = "select * from ENQUIRY limit 3 ;";
		String Database = "use proptiger";
		try{
		db.Connect();
		rs = db.Execute(Query, Database);
		  while(rs.next()){
		         String phone  = rs.getString("phone");
		         String email = rs.getString("email");
		         String name = rs.getString("name");

		         //Display values
		        System.out.println("Phone: " + phone);
		         System.out.println("email: " + email);
		         System.out.println("name: " + name);
		         hm.put("phone",phone);
				 hm.put("email",email);
				 hm.put("name",name);
		      }
		  VerifyData(hm);
		  
		}catch(SQLException se){
			se.printStackTrace();
		}
		
	}	
	
	public void VerifyData(HashMap<String,String> hm){
		
		String data = xls.getCellData("CallNow","Phone Number", 2);
		System.out.println("cell value is " + data);
		for (String key : hm.keySet()) {
		    String value = hm.get(key);
		    if(value.equals(data)){
		    	 System.out.println("Key = " + key + ", Value = " + value);
		    }
		   
		}
	}

	public boolean FormValidate() throws InterruptedException,IOException, TimeoutException{
		try{
			boolean result = false;
		
		wb.ClickbyLink("call now");
		Thread.sleep(3000);
		result= wb.IsElementPresent(cn.CallNowForm);
		if(result!=false){
			System.out.println("CAll now form is present");
			if(wb.IsElementPresent(cn.SellerImage)){
				System.out.println("Seller Image is present on Lead form");
			}
			
		}
		return false;
	}catch(Exception e){
		e.printStackTrace();
		return false;
	}
 }
	public boolean PostEnquiry()throws InterruptedException,IOException,TimeoutException{
		boolean result = false;
		if(result!=false){
			System.out.println("CAll now form is present");
			String Phonenumber = ReadSheet("CallNow","Phone Number",2);
			wb.enterTextByxpath(cn.PhoneText, Phonenumber);
			wb.ClickbyXpath(cn.CallNowButton);
			return true;
		}
		return false;
		
	}

	public void Close(){
		db.Close();
		wb.ClickbyXpath(cn.CloseForm);
		wb.CloseBrowser();
		
	}
	
}

