package com.makaan.Test;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.makaan.Middleware.LoginMiddleware;

public class LoginTest {

	LoginMiddleware mw = new LoginMiddleware();

	@BeforeClass
	public void InitiateDriver() throws Exception {
		System.out.println("Inside Test Initiate Driver");
		try{
			mw.OpenURL();
		}catch(Exception e){
		
		Assert.assertTrue(false, "not able to Initiate Driver due to exception");
		}
	}

	@Test(priority = 1)
	public void ValidateLoginButton() throws Exception {
		System.out.println("Inside Test Validate Login Button");
		if (mw.LoginForm()) {
			System.out.println("Login Button is present");
		} else {
			Assert.assertTrue(false, "not able to validate Login button");
		}
	}

	@Test(priority = 2)
	//@Test (enabled = false)
	public void ValidateSocialLogin() throws Exception {
		System.out.println("Inside Test Validate Social Login");
		if (mw.SocialLogin()) {
			System.out.println("social login present");
		} else {

			Assert.assertTrue(false, "not able to validate Social login form");
		}

	}

	@Test(priority = 3)
//	@Test (enabled = false)
	public void ValidateForgotPassword() throws Exception {
		System.out.println("Inside Test Forgot password");

		if (mw.ForgetPassword()) {

			System.out.println("Verified Forgot password");
		} else {
			Assert.assertTrue(false, "not able to Verify Forgot password  form");
		}

	}
	
	@Test(priority = 4)
	//@Test (enabled = false)
	public void ValidateResetPassword() throws Exception {
		System.out.println("Inside Test Reset password");

		if (mw.ResetPassword()) {

			System.out.println("Verified Reset password");
		} else {
			Assert.assertTrue(false, "not able to Verify Reset password  form");
		}

	}
	
	

	@Test(priority = 5)
	// @Test (enabled = false)
	public void ValidateMakaanLogin() throws Exception {
		System.out.println("Inside Test Validate Makaan Login");

		if (mw.MakaanLogin()) {
			System.out.println("Verified Makaan Login");
		} else {
			Assert.assertTrue(false, "not able to validate MakaanLogin form");
		}

	}
	
	@Test(priority = 6)
	// @Test (enabled = false)
		public void ValidateMakaanLogout() throws Exception {
			System.out.println("Inside Test Validate Makaan Logout");

			if (mw.MakaanLogout()) {
				System.out.println("Verified Makaan Logout");
			} else {
				Assert.assertTrue(false, "not able to validate MakaanLogout form");
			}

		}

	@Test(priority = 7)
	// @Test (enabled = false)
	public void Signup()throws Exception{
		System.out.println("Inside Test Validate Makaan Signup");	
		if(mw.MakaanSignup()){
			System.out.println("Verified Signup Functionality");
			
		}else{
			Assert.assertTrue(false, "not able to validate MakaanSignup form");
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
