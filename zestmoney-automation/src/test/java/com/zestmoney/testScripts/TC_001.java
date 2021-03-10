package com.zestmoney.testScripts;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.zestmoney.web.init.InitializePages;
import com.zestmoney.web.library.BaseLib;
import com.zestmoney.web.page.HomePage;

public class TC_001 extends BaseLib {
	WebDriver driver;
	@Test(description = "Search")
	public void addReviewComments() throws Exception {
		
		gv.init.o_HomePage.performActionOnSearchBar("TC_001");
		gv.init.o_HomePage.clickOnFirstSearchOption();
		gv.init.o_FirstSearchPage.clickOnWriteReview();
		gv.init.o_FirstSearchPage.performActionOnStar();
		gv.init.o_FirstSearchPage.enterReview("TC_001");
		gv.init.o_FirstSearchPage.clickSortOfTrip();
		gv.init.o_FirstSearchPage.selectWhenToTravel();
		gv.init.o_FirstSearchPage.checkForHotelRating();
		gv.init.o_FirstSearchPage.clickOnNoFraudCheckBx();
		gv.init.o_FirstSearchPage.clickOnSubmitBtn();
		
		
	}

}
