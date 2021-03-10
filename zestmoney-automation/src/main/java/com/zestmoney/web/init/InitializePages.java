package com.zestmoney.web.init;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.zestmoney.web.page.FirstSeachPage;
import com.zestmoney.web.page.HomePage;

public class InitializePages {

	
	/** WEB CLASSES DECLARATION**/
	public static HomePage o_HomePage ;
	public static FirstSeachPage o_FirstSearchPage ;
	
	
	/** WEB CLASSES INITIALISATION**/
	
	public InitializePages(WebDriver driver) {
		PageFactory.initElements(driver, this);
		o_HomePage = new HomePage(driver);
		o_FirstSearchPage = new FirstSeachPage(driver);
	}
	
}
