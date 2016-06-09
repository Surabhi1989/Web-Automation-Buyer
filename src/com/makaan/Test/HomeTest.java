package com.makaan.Test;

import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.makaan.Middleware.HomeMiddleware;
import com.makaan.Middleware.SearchMiddleware;

public class HomeTest {

	HomeMiddleware hm = new HomeMiddleware();

	@BeforeClass
	public void InitiateDriver() throws NoSuchElementException {
		try {
			System.out.println("Inside Test Initiate Driver");

			hm.OpenURL();

		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false, "not able to Initiate Driver due to exception");
		}

	}

	 @Test(priority =1)
	//@Test(enabled = false)
	public void ValidateDownloadAppButton() throws Exception {
		System.out.println("Inside Test Validate Download app button");
		if (hm.VerifyAppButton()) {
			System.out.println("Download App button is validated");
		} else {
			Assert.assertTrue(false, "not able to Validate Download App button due to exception");
		}
	}

	// @Test (priority =2)
	@Test(enabled = false)
	public void ValidateSellerLink() throws Exception {
		System.out.println("Inside Test Validate Seller Link");
		if (hm.VerifySeller()) {
			System.out.println("Seller Link is validated");
		} else {
			Assert.assertTrue(false, "not able to Validate Seller Linkn due to exception");
		}
	}

	@Test(priority = 3)
	//@Test (enabled = false)
	public void ValidateBuyerJourney() throws Exception {
		System.out.println("Inside Test Validate Buyer Journey");
		if (hm.VerifyBuyerJourney()) {
			System.out.println("Buyer Journey Link is validated");
		} else {
			Assert.assertTrue(false, "not able to Validate Buyer Journey due to exception");
		}
	}
	
	@Test(priority = 4)
	// @Test (enabled = false)
	public void MenuDrawer() throws Exception {
		System.out.println("Inside Test Validate Menu Drawer");
		if (hm.VerifyMenuDrawer()) {
			System.out.println("Menu Drawer is validated");
		} else {
			Assert.assertTrue(false, "not able to Validate Menu Drawer due to exception");
		}
	}
		
		@Test(priority = 5)
	//	 @Test (enabled = false)
		public void VerifyAppSection() throws Exception {
			System.out.println("Inside Test Verify App Section");
			if (hm.DownloadAppSection()) {
				System.out.println("App Section is validated");
			} else {
				Assert.assertTrue(false, "not able to Validate App Section due to exception");
			}
		}

	@AfterClass
	public void Close() {
		try {
			hm.CloseAll();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
