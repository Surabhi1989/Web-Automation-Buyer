package com.makaan.Test;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.makaan.Middleware.SERPMiddleware;

public class SERPTest {

	
	

	SERPMiddleware smw = new SERPMiddleware();

	@BeforeClass
	public void InitiateDriver() throws Exception {
		System.out.println("Inside Test Initiate Driver");
		try{
			smw.OpenURL();
		}catch(Exception e){
		
		Assert.assertTrue(false, "not able to Initiate Driver due to exception");
		}
	}
	@Test(priority =1)
	//@Test(enabled = false)
	public void BuyFilters() throws Exception {
		System.out.println("Inside Test ValidateBuy Filters");
		if(smw.ValiadateBuyFilters()){
			System.out.println("Buy Filters are validated");
		}else{
			System.out.println("Buy filters are not validated");
		Assert.assertTrue(false, "not able to Validate Buy Filters due to exception");
		}
	}
	
	//@Test(priority =2)
	@Test(enabled = false)
	public void RentFilters() throws Exception {
		System.out.println("Inside Test Validate Rent Filters");
		if(smw.ValiadateRentFilters()){
			System.out.println("Rent Filters are validated");
		}else{
			System.out.println("Rent filters are not validated");
		Assert.assertTrue(false, "not able to Validate Filters due to exception");
		}
	}
	
//@Test (priority = 3)
	@Test(enabled = false)
	public void ValidateCard() throws Exception {
		System.out.println("Inside Test Validate card");
		if(smw.VerifyPropertyCard()){
			System.out.println("Verify Card is validated");
		}else{
			System.out.println("Property card is not validated");
		Assert.assertTrue(false, "not able to Validate Property Card due to exception");
		}
	}

//@Test (priority = 4)
@Test(enabled = false)
public void ValidateProperty() throws Exception {
	System.out.println("Inside Test Validate Property Link");
	if(smw.VerifyPropertyLink()){
		System.out.println("Verify Property link is validated");
	}else{
	Assert.assertTrue(false, "not able to Validate Property Link due to exception");
	}
}

//@Test (priority = 5)
@Test(enabled = false)
public void ValidateReadMore() throws Exception {
	System.out.println("Inside Test Validate Read More Link");
	if(smw.VerifyReadMore()){
		System.out.println("Verify Read More Link is validated");
	}else{
	Assert.assertTrue(false, "not able to Validate Read More due to exception");
	}
}

//@Test (priority = 6)
@Test(enabled = false)
public void ValidateSeller() throws Exception {
	System.out.println("Inside Test Validate Seller Type");
	if(smw.ValidateSellerType()){
		System.out.println("Verify Seller Type link is validated");
	}else{
	Assert.assertTrue(false, "not able to Validate Seler Type link due to exception");
	}
}

//@Test (priority = 7)
@Test(enabled = false)
public void ValidateLocality() throws Exception {
	System.out.println("Inside Test Validate Locality");
	if(smw.VerifyLocality()){
		System.out.println("Verify Locality Link is validated");
	}else{
	Assert.assertTrue(false, "not able to Validate Locality due to exception");
	}
}
	
//@Test (priority = 8)
@Test(enabled = false)
public void ValidateProject() throws Exception {
	System.out.println("Inside Test Validate Project");
	if(smw.VerifyProject()){
		System.out.println("Verify Project Link is validated");
	}else{
	Assert.assertTrue(false, "not able to Validate Project Link due to exception");
	}
}

	//@Test (priority = 9)
	@Test(enabled = false)
	public void ValidateShortlist() throws Exception {
		System.out.println("Inside Test Validate Shortlist");
		if(smw.VerifyShortList()){
			System.out.println("Shortlist property is validated");
		}else{
		Assert.assertTrue(false, "not able to Validate Property Card due to exception");
		}
	}
	
	@Test(enabled = false)
	//@Test (priority = 12)
	public void ValidateSetAlert() throws Exception {
		System.out.println("Inside Test Validate SetAlert");
		if(smw.SetAlert()){
			System.out.println("Set Alert is validated");
		}else{
		Assert.assertTrue(false, "not able to Validate Set Alert due to exception");
		}
	}
	
	@Test(enabled = false)
	//@Test (priority = 10)
		public void Validatepagination() throws Exception {
			System.out.println("Inside Test Validate Pagination");
			if(smw.Pagination()){
				System.out.println("Pagination is Validated");
			}else{
			Assert.assertTrue(false, "not able to Validate Paginatiion due to exception");
			}
		}
	
	@Test(enabled = false)
	//@Test (priority = 11)
	public void ValidateSidecards() throws Exception {
		System.out.println("Inside Test Validate Sidecards");
		if(smw.SideCards()){
			System.out.println("Sidecards are Validated");
		}else{
		Assert.assertTrue(false, "not able to Validate Sidecards due to exception");
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
