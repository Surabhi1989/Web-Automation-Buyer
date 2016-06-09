package com.makaan.Test;


import org.testng.annotations.Test;

import org.testng.annotations.AfterClass;

import org.testng.annotations.BeforeClass;

import com.makaan.Middleware.CallNow_Middleware;



import org.testng.Assert;



public class Call_Now_Test {




	CallNow_Middleware mw = new CallNow_Middleware();


	
	@BeforeClass
	public void InitiateDriver() {
		try {
			System.out.println("Inside Test Initiate Driver");
			
			mw.OpenURL();

		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false, "not able to Initiate Driver due to exception");
		}

	}

	@Test (priority =1)
	public void ValidateForm() {
		try {
			System.out.println("Inside Test Validate Form");
			
			mw.FormValidate();
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false, "not able to validate CAll Now form");
		}

	}
	
	
	@Test (priority = 2)
	public void ValidateDB(){
		
		try{
			System.out.println("Inside Test Validate DB");
			mw.DataBaseValidate();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	@AfterClass 
	public void closeBrowser() {
		try{
			System.out.println("Inside Test Validate DB");
			mw.Close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
}
