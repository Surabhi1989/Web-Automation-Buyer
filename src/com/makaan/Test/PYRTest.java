package com.makaan.Test;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;


import com.makaan.Middleware.PYRMiddleware;


import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;


public class PYRTest {

	PYRMiddleware mw = new PYRMiddleware();
	
	//@Test(enabled = false)
	@BeforeClass
	public void InitiateDriver() throws NoSuchElementException {
		try {
			System.out.println("Inside Test Initiate Driver");
			
			mw.OpenURL();

		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false, "not able to Initiate Driver due to exception");
		}

	}

	
	//@Test (enabled = false)
	@Test(priority = 1)
	public void ValidatePYRBuy() throws Exception{
			System.out.println("Inside Test Validate PYR Buy");
			if(mw.PYRBuy()){
				System.out.println("passed PYR for Buy");
			}else{
				Assert.assertTrue(false, "not able to Validate Buy");
			}
			
	}
	
	
	
	//@Test (enabled = false)
		@Test (priority = 2)
		public void ValidatePYRRent() throws Exception{
				System.out.println("Inside Test Validate PYR Rent");
				if(mw.PYRRent()){
					System.out.println("passed PYR for Rent");
				}else{
					Assert.assertTrue(false, "not able to Validate Rent");
				}
				
		}
	
	@AfterClass
	public void Close() {
		try {
			mw.CloseAll();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}
