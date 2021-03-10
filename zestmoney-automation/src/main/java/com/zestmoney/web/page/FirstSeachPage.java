package com.zestmoney.web.page;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.zestmoney.web.init.GlobalVariables;
import com.zestmoney.web.library.GenericLib;
import com.zestmoney.web.utility.WebActionUtil;

public class FirstSeachPage extends GlobalVariables  {
	
	public FirstSeachPage(WebDriver driver) {
		this.wDriver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[text()='Write a review']")
	private WebElement eleWriteReview;
	
	public WebElement getWriteReview() {
		return eleWriteReview;
	}
	
	@FindBy(xpath = "//span[@id='bubble_rating']")
	private WebElement eleBubbleRating;
	
	public WebElement getBubbleRating() {
		return eleBubbleRating;
	}
	
	@FindBy(xpath = "//input[@id='ReviewTitle']")
	private WebElement eleReviewTitle;
	
	public WebElement getReviewTitle() {
		return eleReviewTitle;
	}
	
	@FindBy(xpath = "//textarea[@id='ReviewText']")
	private WebElement eleReviewText;
	
	public WebElement getReviewText() {
		return eleReviewText;
	}
	
	@FindBy(xpath = "//div[text()='Hotel Ratings']")
	private WebElement eleHotelRatings;
	
	public WebElement getHotelRatings() {
		return eleHotelRatings;
	}
	
	@FindBy(xpath = "//input[@id='noFraud']")
	private WebElement eleNoFraud;
	
	public WebElement getNoFraud() {
		return eleNoFraud;
	}
	
	@FindBy(xpath = "//div[@id='SUBMIT']")
	private WebElement eleSubmitbtn;
	
	public WebElement getSubmitbtn() {
		return eleSubmitbtn;
	}
	
	

	
	@FindBy(xpath = "//div[text()='Business']")
	private WebElement eleSortOfTrip;
	
	public WebElement getSortOfTrip() {
		return eleSortOfTrip;
	}
	
	@FindBy(xpath = "//select[@id='trip_date_month_year']")
	private WebElement eleWhenToTravel;
	
	public WebElement getWhenToTravel() {
		return eleWhenToTravel;
	}
	
	public void clickOnWriteReview() throws IOException
	
	{
		ArrayList<String> listOfWindow = new ArrayList(wDriver.getWindowHandles());
		wDriver.switchTo().window(listOfWindow.get(1));
		WebActionUtil.clickElement(getWriteReview(), wDriver, "eleFirstSearchOption");
	}
	
	public void performActionOnStar() {
		for(int i =1;i<=5;i++) {
		new Actions(wDriver).moveToElement(new WebDriverWait(wDriver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='bubble_rating']"))), (i*10), 0).click().build().perform();
	}
	}
	
	public void enterReview(String sTestCaseID) throws Exception {
		String[] sData = GenericLib.toReadExcelData(GenericLib.sTestDataPath, GenericLib.sTestDataSheetName, sTestCaseID);
		WebActionUtil.clickElement(getReviewTitle(), wDriver, "Review Title");
		WebActionUtil.type(getReviewTitle(), sData[GlobalVariables.REVIEWTITLE], "Review Title", wDriver);
		WebActionUtil.clickElement(getReviewText(), wDriver, "Review Text");
		WebActionUtil.type(getReviewText(), sData[GlobalVariables.REVIEWTEXT], "Review Text", wDriver);
		
	}
	
	public void checkForHotelRating() {
		List<WebElement> hotelRatingEle = wDriver.findElements(By.xpath("//div[text()='Hotel Ratings']"));
		Actions action = new Actions(wDriver);
		if (hotelRatingEle.size() > -1 ){
			System.out.println("Hotel Rating option is present");
			try {
				WebActionUtil.scrollIntoView(wDriver, getHotelRatings());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				action.moveToElement(new WebDriverWait(wDriver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='answersBlock']/div/span)[1]"))), 50, 0).click().build().perform();
				action.moveToElement(new WebDriverWait(wDriver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='answersBlock']/div/span)[2]"))), 50, 0).click().build().perform();
				action.moveToElement(new WebDriverWait(wDriver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='answersBlock']/div/span)[3]"))), 50, 0).click().build().perform();
			
		}
	}
	
	public void clickOnNoFraudCheckBx() throws IOException {
		WebActionUtil.clickElement(getNoFraud(), wDriver, "NoFraudele");
	}
	
	public void clickOnSubmitBtn() throws IOException {
		WebActionUtil.clickElement(getSubmitbtn(), wDriver, "submit btn ele");
	}
	
	public void clickSortOfTrip() throws IOException {
		WebActionUtil.clickElement(getSortOfTrip(), wDriver, "getSortOfTrip");
	}
	
	public void selectWhenToTravel() throws IOException {
		WebActionUtil.selectbyIndex(wDriver, getWhenToTravel(),1);
	}
}
