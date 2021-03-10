package com.zestmoney.web.library;
/***********************************************************************
* @author 				:		Gargi Verma
* @description			: 		Implemented Application Precondition and Postconditions
* @Variables			: 	  	Declared and Initialised AndroidDriver and WebDriver, Instance for GlobalVariables Page
* @BeforeSuiteMethod	: 		DB connection for xyz
* @BeforeTest			: 		Desired Capabilities for launching app and launching portal		
*/


import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.zestmoney.web.init.GlobalVariables;
import com.zestmoney.web.init.InitializePages;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;

public class BaseLib {

	public static GlobalVariables gv = new GlobalVariables();


	@BeforeSuite
	public void before_suite() throws Exception {

	}

	/**
	 * Description : This Function launch the app based on capabilities provided by
	 * testng.xml file
	 * 
	 * @param port
	 * @param UDID
	 * @param version
	 * @param deviceName
	 * @throws Exception
	 */
	@BeforeMethod
	public void _LaunchApp() throws Exception {

		if (gv.tripAdviser_Browser.equalsIgnoreCase("Firefox")) {
			gv.wDriver = new FirefoxDriver();
			gv.wDriver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		} else if (gv.tripAdviser_Browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", GlobalVariables.userDirectory+"\\src\\main\\resources\\Driver\\chromedriver.exe");
			gv.wDriver = new ChromeDriver();
			gv.wDriver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
			gv.wDriver.get(gv.tripAdviser_Url);
			gv.wDriver.manage().window().maximize();
			
		} else {
			gv.wDriver = new InternetExplorerDriver();
			gv.wDriver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
			
		}
		gv.init = new InitializePages(gv.wDriver);
		System.out.println("----------------Browser Launched-------------------");
			
	}
	


	@AfterMethod()
	public void after_suite() throws Exception {

		gv.wDriver.close();
		
	}

	@AfterSuite
	public void OracleCloseConnection() throws Exception {

		gv.wDriver.quit();

	}
	

}



