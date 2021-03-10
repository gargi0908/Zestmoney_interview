package com.zestmoney.web.init;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;


import com.zestmoney.web.library.GenericLib;

public class GlobalVariables implements TestDataCoulmns {
	public static InitializePages init;
	public WebDriver wDriver;
	public String browser;
	public DesiredCapabilities capabilities;
	public static String userDirectory = System.getProperty("user.dir");
	public static String configPath = userDirectory+"\\src\\main\\resources\\Config\\Config.xlsx";
	
	/***
	 * Initialize all the data columns index for login sheet
	 * 
	 */
	
	//public int username=GenericLib.readColumnIndex(configPath, LOGIN, TEST_CASE_NO,USERNAME);
	


		public String tripAdviser_Url = GenericLib.getProprtyValue(GenericLib.sUserCredFile, "TRIPADVISER_URL");
		public String tripAdviser_Browser = GenericLib.getProprtyValue(GenericLib.sUserCredFile, "BROWSER_CHROME");
}
