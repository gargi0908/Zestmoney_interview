package com.zestmoney.web.page;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zestmoney.web.init.GlobalVariables;
import com.zestmoney.web.library.GenericLib;
import com.zestmoney.web.utility.WebActionUtil;



public class HomePage extends GlobalVariables {
	
	
	public HomePage(WebDriver driver) {
		this.wDriver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//input[@placeholder='Where to?']")
	private WebElement eleSearchTextBox;
	
	@FindBy(xpath = "(//div[@class='result-title'])[1]/span")
	private WebElement eleFirstSearchOption;
	

	public WebElement getSearchTextBox() {
		return eleSearchTextBox;
	}
	
	public WebElement getFirstSearchOption() {
		return eleFirstSearchOption;
	}
	
	
	public void performActionOnSearchBar(String sTestCaseID) throws Exception
	{
		String[] sData = GenericLib.toReadExcelData(GenericLib.sTestDataPath, GenericLib.sTestDataSheetName, sTestCaseID);
		WebActionUtil.clickElement(getSearchTextBox(), wDriver, "Search Box");
		WebActionUtil.type(getSearchTextBox(), sData[GlobalVariables.SEARCHOPTION], "Search Box", wDriver);
		WebActionUtil.enter(wDriver);
	}
	
	public void clickOnFirstSearchOption() throws IOException
	{
		WebActionUtil.clickElement(getFirstSearchOption(), wDriver, "eleFirstSearchOption");
	}



}
