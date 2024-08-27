package testCases;


import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass{
	
	@Test(groups={"Snaity","Master"})
	public void verify_login()
	{
		logger.info("***** Starting TC_002_LoginTest *****");
		
		try
		{
		
		//HomePage
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		//LoginPage
		LoginPage lp=new LoginPage(driver);
		lp.setEmail(p.getProperty("email"));
		lp.setPassword(p.getProperty("password"));
		lp.clickLogin();
		
		//MyAccountPage
		MyAccountPage macc=new MyAccountPage(driver);
		boolean targetpage=macc.isMyAccountPageExists();
		
		//Assert.assertEquals(targetpage, true,"Login Failed");
		//or
		Assert.assertTrue(targetpage);
		}
		catch (Exception e) 
		{
			Assert.fail();
		}
		
		logger.info("***** Finished TC_002_LoginTest *****");
		
	}

}
