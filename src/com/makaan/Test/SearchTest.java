package com.makaan.Test;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.makaan.Middleware.SearchMiddleware;

public class SearchTest {

	SearchMiddleware smw = new SearchMiddleware();

	@BeforeClass
	public void InitiateDriver() throws Exception {
		System.out.println("Inside Test Initiate Driver");
		try {
			smw.OpenURL();
		} catch (Exception e) {

			Assert.assertTrue(false, "not able to Initiate Driver due to exception");
		}
	}

	// @Test(enabled =false)
	@Test(priority = 1)
	public void ValidateSearchBox() throws Exception {
		System.out.println("Inside Test Validate Search Box");

		if (smw.ValidateSearch()) {
			System.out.println("Search Box Validated");
		} else {
			Assert.assertTrue(false, "not able to Initiate Driver due to exception");

		}
	}

	// @Test(enabled =false)
	@Test(priority = 2)
	public void ValidatePopularSuggestions() throws Exception {
		System.out.println("Inside Test Validate Popular Suggestions");

		if (smw.ValidateSuggestions()) {
			System.out.println("Search Box Validated");
		} else {
			Assert.assertTrue(false, "not able to Validate Popular suggestion due to exception");

		}
	}

	// @Test(enabled =false)
	@Test(priority = 3)
	public void ValidateBuyLocalities() throws Exception {
		System.out.println("Inside Test Validate Locality");

		if (smw.ValidateBuyLocality()) {
			System.out.println("Search Box Validated for buy Localities");
		} else {
			Assert.assertTrue(false, "not able to Validate Buy Localities due to exception");

		}
	}

	// @Test(enabled =false)
	@Test(priority = 4)
	public void ValidateRentLocalities() throws Exception {
		System.out.println("Inside Test Validate Rent Locality");

		if (smw.ValidateRentLocality()) {
			System.out.println("Search Box Validated for rent Localities");
		} else {
			Assert.assertTrue(false, "not able to Validate Rent Localities due to exception");

		}
	}

	// @Test(enabled =false)
	@Test(priority = 5)
	public void ValidateBuyProject() throws Exception {
		System.out.println("Inside Test Validate Buy Project");

		if (smw.ValidateBuyProject()) {
			System.out.println("Search Box Validated for Buy Project");
		} else {
			Assert.assertTrue(false, "not able to Validate Buy Projects due to exception");

		}
	}

	// @Test(enabled =false)
	@Test(priority = 6)
	public void ValidateRentProject() throws Exception {
		System.out.println("Inside Test Validate Rent Project");

		if (smw.ValidateRentProject()) {
			System.out.println("Search Box Validated for Rent Project");
		} else {
			Assert.assertTrue(false, "not able to Validate Rent Projects due to exception");

		}
	}

	// @Test(enabled =false)
	@Test(priority = 7)
	public void ValidateBuilder() throws Exception {
		System.out.println("Inside Test Validate Builder");

		if (smw.ValidateBuilder()) {
			System.out.println("Search Box Validated for builder");
		} else {
			Assert.assertTrue(false, "not able to Validate Builder due to exception");

		}
	}

	 @Test(enabled =false)
	//@Test(priority = 8)
	public void ValidateBuyLandmark() throws Exception {
		System.out.println("Inside Test Validate Landmark buy");

		if (smw.ValidateLandmarksBuy()) {
			System.out.println("Search Box Validated for Landmark");
		} else {
			Assert.assertTrue(false, "not able to Validate Landmark due to exception");

		}
	}

	 @Test(enabled =false)
	//@Test(priority = 9)
	public void ValidateRentLandmark() throws Exception {
		System.out.println("Inside Test Validate Landmark Rent");

		if (smw.ValidateLandmarksRent()) {
			System.out.println("Search Box Validated for Landmark");
		} else {
			Assert.assertTrue(false, "not able to Validate Landmark due to exception");

		}
	}

	// @Test(enabled =false)
	@Test(priority = 10)
	public void ValidateSuburbBuy() throws Exception {
		System.out.println("Inside Test Validate Suburb");

		if (smw.ValidateSuburbBuy()) {
			System.out.println("Search Box Validated for Suburb Buy");
		} else {
			Assert.assertTrue(false, "not able to Validate Suburb due to exception");

		}
	}

	// @Test(enabled =false)
	@Test(priority = 11)
	public void ValidateSuburbRent() throws Exception {
		System.out.println("Inside Test Validate Suburb Rent");

		if (smw.ValidateSuburbRent()) {
			System.out.println("Search Box Validated for Suburb");
		} else {
			Assert.assertTrue(false, "not able to Validate Suburb due to exception");

		}
	}

	@AfterClass
	public void Close() {
		try {
			smw.CloseAll();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
