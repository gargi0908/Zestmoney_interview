package com.zestmoney.web.utility;
/***********************************************************************
 * @author 			:		Gargi Verma
 * @description		: 		This class contains action methods which is used for performing 
 * 							action while executing script such as Click, SendKeys 
 */

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.zestmoney.web.library.BaseLib;
import com.zestmoney.web.listener.MyExtentListners;




public class WebActionUtil {

	public final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/*
	 * @author : Gargi Verma
	 * 
	 * Description : This method has WebDriver wait implementation for element to load
	 * 
	 */
	public static void waitForElement(WebElement element, WebDriver driver, String eleName, int seconds)
			throws IOException {
		try {
			logger.info("---------Waiting for visibility of element---------" + element);

			waitTillPageLoad(driver,seconds);
			WebDriverWait wait = new WebDriverWait(driver,seconds);
			wait.until(ExpectedConditions.visibilityOf(element));
			Assert.assertTrue(wait.until(ExpectedConditions.visibilityOf(element)) != null);

			logger.info("---------Element is visible---------" + element);
		} catch (Exception e) {

			MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element));
			logger.info("---------Element is not visible---------" + element);
			throw e;
		} catch (AssertionError e) {
			MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element));
			logger.info("---------Element is not visible---------" + element);
			throw e;
		}
	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description: This method helps to verify whether given webelemnt is present
	 * page or not.
	 */
	public static void isEleDisplayed(WebElement element, WebDriver driver, String elementName, int seconds)
			throws IOException {
		try {
			logger.info("---------Verifying element is displayed or not ---------");
			waitTillPageLoad(driver,seconds);
			WebDriverWait wait = new WebDriverWait(driver,seconds);
			wait.until(ExpectedConditions.visibilityOf(element));;
			Assert.assertTrue(wait.until(ExpectedConditions.visibilityOf(element)) != null);
			System.out.println(elementName + "------ is displayed");
			MyExtentListners.test.pass("Verify " + "\'" + elementName + "\'" + " is displayed || " + "\'" + elementName
					+ "\'" + " is displayed ");
		} catch (RuntimeException e) {

			MyExtentListners.test.fail(MarkupHelper.createLabel("Verify " + "\'" + elementName + "\'"
					+ " is displayed || " + "\'" + elementName + "\'" + " is not displayed ", ExtentColor.RED));

			MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element));
			System.out.println(elementName + "------ is not displayed");
			throw e;
		}
	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description: This method helps to verify whether given webelemnt is present
	 * page or not.
	 */
	public static boolean presenceOfElement(WebElement element, int seconds, int loop)
			throws IOException, InterruptedException {
		boolean flag = false;

		int count = loop;
		while (count > 0) {
			try {
				logger.info("---------Verifying element is displayed or not ---------");
				count--;
				element.isDisplayed();
				flag = true;
				break;

			} catch (RuntimeException e) {
				Thread.sleep(seconds * 1000);
				flag = false;
			}
		}
		return flag;
	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description: This method helps to verify whether given webelemnt is present
	 * page or not without generating extent report for failure
	 */
	public static void isElementDisplayed(WebElement element, WebDriver driver, String elementName, int seconds)
			throws IOException {

		try {
			logger.info("---------Verifying element is displayed or not ---------");
			waitTillPageLoad(driver,seconds);
			WebDriverWait wait = new WebDriverWait(driver,seconds);
			wait.until(ExpectedConditions.visibilityOf(element));
			Assert.assertTrue(wait.until(ExpectedConditions.visibilityOf(element)) != null);
			System.out.println(elementName + "------ is displayed");
			MyExtentListners.test.pass("Verify " + "\'" + elementName + "\'" + " is displayed || " + "\'" + elementName
					+ "\'" + " is displayed ");
		} catch (Exception e) {

			System.out.println(elementName + "------ is not displayed");
		}
	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description: This method helps to verify whether given web element is present
	 * page or not.
	 */
	public static void eleIsNotDisplayed(WebElement element, WebDriver driver, String elementName, int seconds)
			throws IOException {
		try {
			logger.info("---------Verifying element is displayed or not ---------");
			waitTillPageLoad(driver,seconds);
			WebDriverWait wait = new WebDriverWait(driver,seconds);
			wait.until(ExpectedConditions.visibilityOf(element));
			Assert.assertTrue(wait.until(ExpectedConditions.visibilityOf(element)) == null);
			System.out.println(elementName + "------ is displayed");
			MyExtentListners.test.pass("Verify " + "\'" + elementName + "\'" + " is not displayed || " + "\'" + elementName
					+ "\'" + " is not displayed ");
		} catch (AssertionError e) {

			MyExtentListners.test.fail(MarkupHelper.createLabel("Verify " + "\'" + elementName + "\'"
					+ " should not be displayed || " + "\'" + elementName + "\'" + " is displayed ", ExtentColor.RED));

			MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element));
			System.out.println(elementName + "------ is not displayed");
			throw e;
		}
	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description: This method helps to verify whether given web element is displayed
	 * or not
	 */
	public static void verifyElementIsDisplayed(WebElement element, WebDriver driver, String elementName)
			throws IOException {
		try {
			logger.info("---------Verifying element is displayed or not ---------");
			waitTillPageLoad(driver,10);
			WebDriverWait wait = new WebDriverWait(driver,10);
			wait.until(ExpectedConditions.visibilityOf(element));
			Assert.assertTrue(wait.until(ExpectedConditions.visibilityOf(element)) != null);
			MyExtentListners.test.pass("Verify " + "\'" + elementName + "\'" + " is displayed  || " + "\'" + elementName
					+ "\'" + " is displayed ");
		} catch (Exception e) {
			MyExtentListners.test.fail(MarkupHelper.createLabel("Verify " + "\'" + elementName + "\'"
					+ " is displayed  || " + "\'" + elementName + "\'" + " is not displayed ", ExtentColor.RED));

			MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element));
			System.out.println(elementName + "------ is not displayed");
			throw e;
		} catch (AssertionError e) {
			MyExtentListners.test.fail(MarkupHelper.createLabel("Verify " + "\'" + elementName + "\'"
					+ " is displayed  || " + "\'" + elementName + "\'" + " is not displayed ", ExtentColor.RED));

			MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element));
			System.out.println(elementName + "------ is not displayed");
			throw e;
		}
	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description: this method will click on element which is provided.
	 */

	public static void clickElement(WebElement element, WebDriver driver, String elementName) throws IOException {

		try {
			logger.info("---------Verifying element is displayed or not ---------");
			waitTillPageLoad(driver, 30);
			isEleClickable(element, driver, elementName);
			element.click();
			WebActionUtil.waitTillPageLoad(driver, 30);
			MyExtentListners.test.pass("Verify user is able to click on " + "\'" + elementName + "\'"
					+ " ||  User is able to click on " + "\'" + elementName + "\'");
		} catch (AssertionError error) {
			MyExtentListners.test.fail(MarkupHelper.createLabel("Verify user is able to click on " + "\'" + elementName
					+ "\'" + "  || User is not able to click on " + "\'" + elementName + "\'", ExtentColor.RED));

			MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element));
			Assert.fail("unable to Click on " + "\'" + elementName + "\'");

			MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element));
			throw error;
		} catch (Exception e) {
			MyExtentListners.test.fail(MarkupHelper.createLabel("Verify user is able to click on " + "\'" + elementName
					+ "\'" + " || User is not able to click on " + "\'" + elementName + "\'", ExtentColor.RED));

			MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element));
			throw e;
		}

	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description: this method clear texts from text box/edit box and type the
	 * value which is provided
	 * 
	 */

	public static void clearAndType(WebElement element, String value, String elementName, WebDriver driver)
			throws Exception {
		try {
			logger.info("---------Method clear and type  ---------");
			waitTillPageLoad(driver, 30);
			element.clear();
			logger.info(elementName + " is cleared");
			element.sendKeys(value);
			logger.info(value + " is entered in " + elementName);
			logger.info(" hide keyboard");
			MyExtentListners.test.pass("Verify user is able to type " + "\'" + value + "\'" + "in " + "\'" + elementName
					+ "\'" + " || User is able to type " + "\'" + value + "\'" + "in " + "\'" + elementName + "\'");
		} catch (AssertionError error) {

			MyExtentListners.test.fail(MarkupHelper.createLabel(
					"Verify user is able to type " + "\'" + value + "\'" + "in " + "\'" + elementName + "\'"
							+ " || User is not able to type " + "\'" + value + "\'" + "in " + "\'" + elementName + "\'",
					ExtentColor.RED));

			MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element));
			Assert.fail("Unable to type on " + "\'" + elementName + "\'");
		} catch (Exception e) {
			MyExtentListners.test.fail(MarkupHelper.createLabel(
					"Verify user is able to type " + "\'" + value + "\'" + "in " + "\'" + elementName + "\'"
							+ " || User is not able to type " + "\'" + value + "\'" + "in " + "\'" + elementName + "\'",
					ExtentColor.RED));

			MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element));
			Assert.fail("Unable to type in " + "\'" + elementName + "\'");
		}

	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description: Method to type value in element
	 * 
	 */

	public static void type(WebElement element, String value, String elementName, WebDriver driver) throws Exception {
		try {
			logger.info("---------Method type  ---------");
			waitTillPageLoad(driver, 30);
			waitForElement(element, driver, elementName, 30);
			element.sendKeys(value);
			logger.info("---------hide keyboard  ---------");
			MyExtentListners.test.pass("Verify user is able to type " + "\'" + value + "\'" + "in " + "\'" + elementName
					+ "\'" + " || User is able to type " + "\'" + value + "\'" + "in " + "\'" + elementName + "\'");
		} catch (AssertionError error) {
			MyExtentListners.test.fail(MarkupHelper.createLabel(
					"Verify user is able to type " + "\'" + value + "\'" + "in " + "\'" + elementName + "\'"
							+ " || User is not able to type " + "\'" + value + "\'" + "in " + "\'" + elementName + "\'",
					ExtentColor.RED));

			MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element));
			Assert.fail("Unable to type on " + elementName);
		} catch (Exception e) {
			MyExtentListners.test.fail(MarkupHelper.createLabel(
					"Verify user is able to type " + "\'" + value + "\'" + "in " + "\'" + elementName + "\'"
							+ " || User is not able to type " + "\'" + value + "\'" + "in " + "\'" + elementName + "\'",
					ExtentColor.RED));

			MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element));
			Assert.fail("Unable to type in " + elementName);
		}

	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description: Code to wait till page is load , Page status should be ready
	 */
	public static void waitTillPageLoad(WebDriver driver, int seconds) {

		WebDriverWait wait = new WebDriverWait(driver, seconds);
		JavascriptExecutor jsExec = (JavascriptExecutor) driver;

		// Wait for Javascript to load
		ExpectedCondition<Boolean> jsLoad = wd -> ((JavascriptExecutor) driver)
				.executeScript("return document.readyState").toString().equals("complete");

		// Get JS is Ready
		boolean jsReady = (Boolean) jsExec.executeScript("return document.readyState").toString().equals("complete");

		// Wait Javascript until it is Ready!
		if (!jsReady) {
			System.out.println("JS in NOT Ready!");

			// Wait for Javascript to load
			wait.until(jsLoad);

		} else {
			System.out.println("JS is Ready!");

		}
		System.out.println(" page is in ready state ");
	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description:Explicit wait to check element is clickable
	 */
	public static void isEleClickable(WebElement element, WebDriver driver, String eleName) throws IOException {
		try {
			logger.info("---------Method is Element clickable  ---------");
			System.out.println(element);
			long timeout = 30;
			waitTillPageLoad(driver,10);
			WebDriverWait wait = new WebDriverWait(driver,10);
			wait.until(ExpectedConditions.visibilityOf(element));
			if (!(wait.until(ExpectedConditions.elementToBeClickable(element)) == null)) {
				Assert.assertTrue(wait.until(ExpectedConditions.visibilityOf(element)) != null);
				System.out.println("Eleement is Clickable");
			} else {
				System.out.println("Eleement is not Clickable");
			}

			System.out.println(" element is clickable ");
		} catch (AssertionError e) {
			MyExtentListners.test.fail(MarkupHelper.createLabel("Verify " + "\'" + eleName + "\'" + " is clickable || "
					+ "\'" + eleName + "\'" + " is not clickable", ExtentColor.RED));

			MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element));
			System.out.println(" element is not clickable ");
			throw e;
		} catch (Exception e) {
			MyExtentListners.test.fail(MarkupHelper.createLabel("Verify " + "\'" + eleName + "\'" + " is clickable || "
					+ "\'" + eleName + "\'" + " is not clickable", ExtentColor.RED));

			MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element));
			System.out.println(" element is not clickable ");
			throw e;
		}
	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description: Fetch text from element and return as string
	 */

	public static String gettext(WebElement element, WebDriver driver, String elementName) throws IOException {
		logger.info("--------- get text from element  ---------");
		String eleText = null;
		try {
			waitTillPageLoad(driver, 30);
			isEleDisplayed(element, driver, elementName, 25);
			eleText = element.getText();
			if (eleText.equals(null)) {
				MyExtentListners.test.fail(MarkupHelper
						.createLabel("Unable to fetch text from " + "\'" + elementName + "\'", ExtentColor.RED));
				MyExtentListners.test.addScreenCaptureFromPath(capture(driver)); // exception
				Assert.fail("Unable to fetch text from " + "\'" + elementName + "\'");
			}
		} catch (Exception e) {

			MyExtentListners.test.fail(MarkupHelper
					.createLabel("Unable to fetch text from " + "\'" + elementName + "\'", ExtentColor.RED));
			MyExtentListners.test.addScreenCaptureFromPath(capture(driver)); // exception
			Assert.fail("Unable to fetch text from " + "\'" + elementName + "\'");

		}
		return eleText;
	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description: Fetch text from element and return as string
	 */

	public static String getAttributeValue(WebElement element, WebDriver driver, String elementName)
			throws IOException {
		logger.info("--------- get text from element  ---------");
		String eleText = null;
		try {
			waitTillPageLoad(driver, 30);
			isEleDisplayed(element, driver, elementName, 25);
			eleText = element.getAttribute("value");
			if (eleText.equals(null)) {
				MyExtentListners.test.fail(MarkupHelper
						.createLabel("Unable to fetch text from " + "\'" + elementName + "\'", ExtentColor.RED));
				MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element)); // exception
				Assert.fail("Unable to fetch text from " + "\'" + elementName + "\'");
			}
		} catch (Exception e) {

			MyExtentListners.test.fail(MarkupHelper
					.createLabel("Unable to fetch text from " + "\'" + elementName + "\'", ExtentColor.RED));
			MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element)); // exception
			Assert.fail("Unable to fetch text from " + "\'" + elementName + "\'");

		}
		return eleText;
	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description: Fetch CSS property Value
	 */

	public static String getCssPropertyValue(WebElement element, String property, WebDriver driver, String elementName)
			throws IOException {
		logger.info("--------- get text from element  ---------");
		String eleText = null;
		try {
			waitTillPageLoad(driver, 30);
			isEleDisplayed(element, driver, elementName, 25);
			eleText = element.getCssValue(property);
			if (eleText.equals(null)) {
				MyExtentListners.test.fail(MarkupHelper.createLabel(
						"Unable to fetch property " + property + "value from " + "\'" + elementName + "\'",
						ExtentColor.RED));
				MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element)); // exception
				Assert.fail("Unable to fetch Proprety Value from " + "\'" + elementName + "\'");
			}
		} catch (Exception e) {

			MyExtentListners.test.fail(MarkupHelper.createLabel(
					"Unable to fetch property " + property + "value from " + "\'" + elementName + "\'",
					ExtentColor.RED));
			MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element)); // exception
			Assert.fail("Unable to fetch Proprety Value from " + "\'" + elementName + "\'");

		}
		return eleText;
	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description: Fetch text from element and return as string
	 */

	public static String getAttributeTitle(WebElement element, WebDriver driver, String elementName)
			throws IOException {
		logger.info("--------- get text from element  ---------");
		String eleText = null;
		try {
			waitTillPageLoad(driver, 30);
			isEleDisplayed(element, driver, elementName, 25);
			eleText = element.getAttribute("title");
			if (eleText.equals(null)) {
				MyExtentListners.test.fail(MarkupHelper
						.createLabel("Unable to fetch text from " + "\'" + elementName + "\'", ExtentColor.RED));
				MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element)); // exception
				Assert.fail("Unable to fetch text from " + "\'" + elementName + "\'");
			}
		} catch (Exception e) {

			MyExtentListners.test.fail(MarkupHelper
					.createLabel("Unable to fetch text from " + "\'" + elementName + "\'", ExtentColor.RED));
			MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element)); // exception
			Assert.fail("Unable to fetch text from " + "\'" + elementName + "\'");

		}
		return eleText;
	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description: This method verify expected result contains in actual result
	 */

	public static void verifyContainsText(String actResult, String expResult, String desc) throws Exception {
		if (actResult.toLowerCase().contains(expResult.toLowerCase())) {
			MyExtentListners.test.pass("Verify  Expected : " + "\'" + expResult + "\''" + " contains  Actual :  "
					+ actResult + "  || Expected : " + "\'" + expResult + "\''" + "contains  Actual :  " + actResult);

		} else {

			MyExtentListners.test.fail(MarkupHelper.createLabel("Verify  Expected : " + "\'" + expResult + "\''"
					+ " contains  Actual :  " + actResult + " ||  Expected : " + "\'" + expResult + "\''"
					+ " does not contains  Actual :  " + actResult, ExtentColor.RED));

			throw new Exception();

		}
	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description: This method helps to verify text contains validation
	 */
	public static void verifyContainsText(String actResult, String expResult) throws Exception {
		if (actResult.contains(expResult)) {
			MyExtentListners.test.pass("Verify  Expected : " + "\'" + expResult + "\''" + " contains  Actual :  "
					+ actResult + "  || Expected : " + "\'" + expResult + "\''" + "contains  Actual :  " + actResult);
		} else {
			MyExtentListners.test.fail(MarkupHelper.createLabel("Verify  Expected : " + "\'" + expResult + "\''"
					+ " contains  Actual :  " + actResult + " ||  Expected : " + "\'" + expResult + "\''"
					+ " does not contains  Actual :  " + actResult, ExtentColor.RED));
			throw new Exception();
		}
	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description: This method verify expected result equals in actual result
	 */

	public static void verifyEqualsText(String desc, String actResult, String expResult) throws Exception {
		if (expResult.equalsIgnoreCase(actResult)) {
			MyExtentListners.test.pass("Verify " + desc + " ||  Expected : " + "\'" + expResult + "\''"
					+ " eqauls  to Actual :  " + actResult);
		} else {
			MyExtentListners.test.fail(MarkupHelper.createLabel("Verify " + desc + "  || Expected : " + "\'" + expResult
					+ "\''" + " not eqauls to  Actual :  " + "\'" + actResult + "\'", ExtentColor.RED));
			MyExtentListners.test.addScreenCaptureFromPath(capture(BaseLib.gv.wDriver));
			throw new Exception();
		}
	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description: This method verify expected result equals in actual result
	 */

	public static void verifyEqualsTextWithNull(String desc, String actResult, String expResult) throws Exception {

		if (actResult == null || actResult.equals("") || expResult == null || expResult.equals("")) {
			MyExtentListners.test.fail(MarkupHelper.createLabel(desc + "Does not have Value", ExtentColor.RED));
		} else if (expResult.equalsIgnoreCase(actResult)) {
			MyExtentListners.test.pass("Verify " + desc + " ||  Expected : " + "\'" + expResult + "\''"
					+ " eqauls  to Actual :  " + actResult);
		} else {
			MyExtentListners.test.fail(MarkupHelper.createLabel("Verify " + desc + "  || Expected : " + "\'" + expResult
					+ "\''" + " not eqauls to  Actual :  " + "\'" + actResult + "\'", ExtentColor.RED));
			throw new Exception();
		}
	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description: This method verify expected result actual result is not equals
	 */
	public static void verifyNotEqualsText(String desc, String actResult, String expResult) throws Exception {
		if (!(expResult.equalsIgnoreCase(actResult))) {
			MyExtentListners.test.pass("Verify " + desc + " is printed on receipt or not" + " ||  Expected : " + "\'"
					+ expResult + "\''" + " not  to Actual :  " + actResult);
		} else {
			MyExtentListners.test
					.fail(MarkupHelper
							.createLabel(
									"Verify " + desc + " is printed on receipt or not" + "  || Expected : " + "\'"
											+ expResult + "\''" + "  eqauls to  Actual :  " + "\'" + actResult + "\'",
									ExtentColor.RED));
			throw new Exception();
		}
	}

	public static void verifyIsNull(String actResult, String desc) {
		if (actResult == null || actResult.equals("")) {
			MyExtentListners.test.fail(MarkupHelper.createLabel("Verify" + "\'" + desc + "\'" + " should not be NUL L"
					+ " || " + "\'" + desc + "\'" + " : " + "\'" + actResult + "\'" + " is not displayed || It is NULL",
					ExtentColor.RED));
			throw new RuntimeException();
		} else {
			MyExtentListners.test.pass("Verify" + "\'" + desc + "\'" + "  should not be NULL " + " || " + "\'" + desc
					+ "\'" + " : " + "\'" + actResult + "\'" + " is displayed");
		}
	}

	/**
	 * Mouse Hover on Element
	 *
	 */
	public static void mouseOverOnElement(WebDriver driver, WebElement element, String elementName) throws IOException {
		try {
			waitTillPageLoad(driver, 30);
			Actions act = new Actions(driver);
			act.moveToElement(element).build().perform();
			MyExtentListners.test.pass("Verify user is able to mouse hover on " + "\'" + elementName + "\'"
					+ " ||  User is able to mouse hover on " + "\'" + elementName + "\'");
		} catch (Exception e) {
			MyExtentListners.test
					.fail(MarkupHelper.createLabel(
							"Verify user is able to mouse hover on " + "\'" + elementName + "\'"
									+ " ||  User is not able to mouse hover on " + "\'" + elementName + "\'",
							ExtentColor.RED));
			MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element));
			Assert.fail("Unable to to mouse hover on " + elementName);
		}
	}

	/**
	 * Double click on element.
	 * 
	 * @throws IOException
	 */
	public static void doubleClickOnElement(WebDriver driver, WebElement element, String elementName)
			throws IOException {

		try {
			waitTillPageLoad(driver, 30);
			Actions act = new Actions(driver);
			act.doubleClick(element).perform();
			MyExtentListners.test.pass("Verify user is able to double click on " + "\'" + elementName + "\'"
					+ " ||  User is  able to double click on " + "\'" + elementName + "\'");
		} catch (Exception e) {
			MyExtentListners.test.fail(MarkupHelper.createLabel(
					"Verify user is able to double click on " + "\'" + elementName + "\'"
							+ " ||  User is not able to double click on " + "\'" + elementName + "\'",
					ExtentColor.RED));
			MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element));
			Assert.fail("Unable to to mouse hover on " + elementName);
		}

	}

	/**
	 * Right click on element.
	 * 
	 * @throws IOException
	 */
	public static void rightClickOnElement(WebDriver driver, WebElement element, String elementName)
			throws IOException {
		try {
			waitTillPageLoad(driver, 30);
			Actions act = new Actions(driver);
			act.contextClick(element).perform();
			MyExtentListners.test.pass("Verify user is able to perform right/context click on " + "\'" + elementName
					+ "\'" + " ||  User is  able to perform right/context click on " + "\'" + elementName + "\'");
		} catch (Exception e) {
			MyExtentListners.test.fail(MarkupHelper.createLabel(
					"Verify user is able to perform right/context click on " + "\'" + elementName + "\'"
							+ " || User is not able to perform right/context click onn " + "\'" + elementName + "\'",
					ExtentColor.RED));
			MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element));
			Assert.fail("Unable to to mouse hover on " + elementName);
		}
	}

	/**
	 * Drag and drop.
	 * 
	 * @throws IOException
	 *
	 * 
	 */
	public static void dragAndDrop(WebDriver driver, WebElement source, WebElement destination, String scrEleName,
			String destEleName) throws IOException {
		try {
			waitTillPageLoad(driver, 30);
			Actions act = new Actions(driver);
			act.dragAndDrop(source, destination).perform();
			MyExtentListners.test.pass("Verify user is able to perfrom drag and drop from " + "\'" + scrEleName + "\'"
					+ "to " + "\'" + destEleName + "\'" + " || User is able to perfrom drag and drop from " + "\'"
					+ scrEleName + "\'" + "to " + "\'" + destEleName + "\'");
		} catch (Exception e) {
			MyExtentListners.test.fail(MarkupHelper
					.createLabel("Verify user is able to perfrom drag and drop from " + "\'" + scrEleName + "\'" + "to "
							+ "\'" + destEleName + "\'" + " || User is not able to perfrom drag and drop from " + "\'"
							+ scrEleName + "\'" + "to " + "\'" + destEleName + "\'", ExtentColor.RED));
			MyExtentListners.test.addScreenCaptureFromPath(capture(driver, destination));
			Assert.fail("User is not able to perfrom drag and drop from " + "\'" + scrEleName + "\'" + "to " + "\'"
					+ destEleName + "\'");
		}
	}

	/**
	 * Selectby visibletext.
	 * 
	 * @throws IOException
	 *
	 */
	public static void selectbyVisibletext(WebDriver driver, WebElement element, String text) throws IOException {

		try {
			waitTillPageLoad(driver, 30);
			Select sel = new Select(element);
			sel.selectByVisibleText(text);
			MyExtentListners.test.pass("Verify user is able to select " + "\'" + text + "\'"
					+ " ||  User is able to select " + "\'" + text + "\'");
		} catch (Exception e) {
			MyExtentListners.test.fail(MarkupHelper.createLabel("Verify user is able to select " + "\'" + text + "\'"
					+ " ||  User is not able to select " + "\'" + text + "\'", ExtentColor.RED));
			MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element));
			Assert.fail("User is not able to select " + "\'" + text + "\'");
		}

	}

	/**
	 * Selectby value.
	 * 
	 * @throws IOException
	 *
	 */
	public static void selectbyValue(WebDriver driver, WebElement element, String value) throws IOException {
		try {
			waitTillPageLoad(driver, 30);
			Select sel = new Select(element);
			sel.selectByValue(value);
			MyExtentListners.test.pass("Verify user is able to select by value  " + "\'" + value + "\'"
					+ " ||  User is able to select by value " + "\'" + value + "\'");
		} catch (Exception e) {
			MyExtentListners.test.fail(MarkupHelper.createLabel("Verify user is able to select by value " + "\'" + value
					+ "\'" + " ||  User is not able to select by value " + "\'" + value + "\'", ExtentColor.RED));
			MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element));
			Assert.fail("User is not able to select " + "\'" + value + "\'");
		}
	}

	/**
	 * Selectby index.
	 * 
	 * @throws IOException
	 *
	 */
	public static void selectbyIndex(WebDriver driver, WebElement element, int index) throws IOException {
		try {
			waitTillPageLoad(driver, 30);
			Select sel = new Select(element);
			sel.selectByIndex(index);
			MyExtentListners.test.pass("Verify user is able to select by index  " + "\'" + index + "\'"
					+ " ||  User is able to select by index " + "\'" + index + "\'");
		} catch (Exception e) {
			MyExtentListners.test.fail(MarkupHelper.createLabel("Verify user is able to select by index " + "\'" + index
					+ "\'" + " ||  User is not able to select by index " + "\'" + index + "\'", ExtentColor.RED));
			MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element));
			Assert.fail("User is not able to select  by index  " + "\'" + index + "\'");
		}
	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description: This method checks for visibility of alert and waits till sec
	 * provided by and returns true if visible else false
	 */
	public static boolean isAlertPresent(WebDriver driver, int sec) {

		
		WebDriverWait wait = new WebDriverWait(driver,15);
		wait.until(ExpectedConditions.alertIsPresent());
		boolean alerPresent = wait.until(ExpectedConditions.alertIsPresent()) != null;
		if (alerPresent) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description: This method scrolls to particular element using javascript
	 * executor
	 */
	public static void scrollIntoView(WebDriver driver, WebElement ele) throws IOException {
		try {
			((JavascriptExecutor) driver).executeScript(
					"arguments[0].scrollIntoView({behavior: 'auto',block: 'center',inline: 'center'});", ele);
		} catch (Exception e) {
			MyExtentListners.test
					.fail(MarkupHelper.createLabel(" Unable to scroll to an element " + ele, ExtentColor.RED));
			MyExtentListners.test.addScreenCaptureFromPath(capture(driver));
		}
	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description: This method helps to perform save with keyboard controls
	 */
	public static void enter(WebDriver driver) {
		Actions act = new Actions(driver);
		act.sendKeys(Keys.chord(Keys.ENTER)).build().perform();
	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description: This method to scroll page downward 25% of window size
	 */
	public static void scrollDown(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,250)", "");
	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description: This method to scroll page upward 25% of window size
	 */
	public static void scrollUp(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-500)", "");
	}

	/*
	 * @author:Gargi Verma
	 * 	
	 * Description: This method to switch frame based on index
	 */
	public static void switchToFrameByIndex(int index,WebDriver driver) {
		driver.switchTo().frame(index);
	}
	
	/*
	 * @author:Gargi Verma
	 * 
	 * Description: Method to upload CSV File
	 * 
	 */

	public static void uploadFile(String filepath, WebElement element, WebDriver driver) throws Exception {

		if (driver instanceof FirefoxDriver) {
			try {
				Runtime.getRuntime().exec("./UploadFiles/firefoxtestcsvfile.exe");
			} catch (IOException e) {
				MyExtentListners.test.fail(MarkupHelper.createLabel(
						"Verify user is able to upload csv file in firefox browser || User is not able to upload csv file in firefox browser",
						ExtentColor.RED));
				MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element));
			}
		} else if (driver instanceof ChromeDriver) {
			try {
				Runtime.getRuntime().exec(filepath);
			} catch (IOException e) {
				MyExtentListners.test.fail(MarkupHelper.createLabel(
						"Verify user is able to upload csv file in chrome browser || User is not able to upload csv file in chrome browser",
						ExtentColor.RED));
				MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element));
			}
		} else {
			try {
				// Implement autoit code to upload file for ie browser
				Runtime.getRuntime().exec("");
			} catch (IOException e) {
				MyExtentListners.test.fail(MarkupHelper.createLabel(
						"Verify user is able to upload csv file in IE browser || User is not able to upload csv file in IE browser",
						ExtentColor.RED));
				MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element));
			}
		}

	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description: This method will set any parameter string to the system's
	 * clipboard.
	 * 
	 */
	public static void setClipboardData(String string) {
		// StringSelection is a class that can be used for copy and paste
		// operations.
		StringSelection stringSelection = new StringSelection(string);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description: Method to upload file thorugh robot class
	 * 
	 */
	public static void uploadFile(String fileLocation) {
		try {
			// Setting clipboard with file location
			setClipboardData(fileLocation);
			// native key strokes for CTRL, V and ENTER keys
			Robot robot = new Robot();
			robot.delay(250);
			Thread.sleep(5000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			Thread.sleep(2000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.delay(150);
			robot.keyRelease(KeyEvent.VK_ENTER);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.delay(150);
			robot.keyRelease(KeyEvent.VK_ENTER);

		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	/*
	 * @author: Gargi Verma
	 */
	public static void uploadFileUsingInput(WebElement element, String filePath) {
		element.sendKeys(filePath);
	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description: Method to validate String to Date Formate
	 * 
	 */
	public static void validateDateFormate(String stringDate) throws ParseException {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
		dateFormat.setLenient(false);
		try {
			dateFormat.parse(stringDate.trim());
		} catch (ParseException pe) {
			MyExtentListners.test
					.fail(MarkupHelper.createLabel("Date Format Exception for " + stringDate, ExtentColor.RED));
			throw pe;
		}

		/*
		 * LocalDate date = LocalDate.now(); DateTimeFormatter formatter =
		 * DateTimeFormatter.ofPattern(""); LocalDate parsedDate =
		 * LocalDate.parse(stringDate, formatter);
		 */
	}

	public static String capture(WebDriver driver, WebElement ele) {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
		String sDate = sdf.format(date);
		String destPath = MyExtentListners.screenShotPath + "/ " + sDate + ".png";
		try {
			File f = new File(destPath);
			if (!(f.exists())) {
				f.createNewFile();
			}
			FileUtils.copyFile(src, f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return destPath;
	}

	public static String capture(WebDriver driver) {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
		String sDate = sdf.format(date);
		String destPath = MyExtentListners.screenShotPath + "/ " + sDate + ".png";
		try {
			File f = new File(destPath);
			if (!(f.exists())) {
				f.createNewFile();
			}
			FileUtils.copyFile(src, f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return destPath;
	}

	/*
	 * public static String capture(AndroidDriver driver) { File src =
	 * ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE); Date date = new
	 * Date(); SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
	 * String sDate = sdf.format(date); String destPath =
	 * MyExtentListners.screenShotPath + "/ " + sDate + ".png"; try { File f = new
	 * File(destPath); if (!(f.exists())) { f.createNewFile(); }
	 * FileUtils.copyFile(src, f); } catch (IOException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } return destPath; }
	 */

	public static String captureAlert(WebDriver driver) throws HeadlessException, AWTException {
		BufferedImage src = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
		String sDate = sdf.format(date);
		String destPath = MyExtentListners.screenShotPath + "/ " + sDate + ".png";
		try {
			File f = new File(destPath);
			if (!(f.exists())) {
				f.createNewFile();
			}
			ImageIO.write(src, "png", new File(destPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return destPath;
	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description: This method is to add minutes to current date refresh
	 */
	public static String addMinutesToDateTime(int minutes) throws InterruptedException {
		SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
		TimeZone istTimeZone = TimeZone.getTimeZone("Asia/Kolkata");
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.MINUTE, minutes);
		Date finalNewDate = calendar.getTime();
		dateTimeInGMT.setTimeZone(istTimeZone);
		String finalNewDateString = dateTimeInGMT.format(finalNewDate);
		System.out.println(finalNewDateString);
		return finalNewDateString;
	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description: This method is to add days to current date refresh
	 */
	public static String addDaysToDateTime(int days) throws InterruptedException {

		SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
		TimeZone istTimeZone = TimeZone.getTimeZone("Asia/Kolkata");
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		for (int i = 0; i < days;) {
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			// here even sat and sun are added
			// but at the end it goes to the correct week day.
			// because i is only increased if it is week day
			if (calendar.get(Calendar.DAY_OF_WEEK) > 1) {
				i++;
			}

		}
		Date finalNewDate = calendar.getTime();
		dateTimeInGMT.setTimeZone(istTimeZone);
		String finalNewDateString = dateTimeInGMT.format(finalNewDate);
		System.out.println(finalNewDateString);
		return finalNewDateString;

	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description: This method is to move to element and click refresh
	 */

	public static void actionClick(WebElement element, WebDriver driver, String elementName) throws IOException {

		try {
			Actions action = new Actions(driver);
			action.moveToElement(element).click().build().perform();
			MyExtentListners.test.pass("Verify user is able to click on " + "\'" + elementName + "\'"
					+ " ||  User is able to click on " + "\'" + elementName + "\'");
		} catch (AssertionError error) {
			MyExtentListners.test.fail(MarkupHelper.createLabel("Verify user is able to click on " + "\'" + elementName
					+ "\'" + "  || User is not able to click on " + "\'" + elementName + "\'", ExtentColor.RED));

			MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element));
			Assert.fail("unable to Click on " + "\'" + elementName + "\'");

			MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element));
			throw error;
		} catch (Exception e) {
			MyExtentListners.test.fail(MarkupHelper.createLabel("Verify user is able to click on " + "\'" + elementName
					+ "\'" + " || User is not able to click on " + "\'" + elementName + "\'", ExtentColor.RED));

			MyExtentListners.test.addScreenCaptureFromPath(capture(driver, element));
			throw e;
		}

	}

	

	/**
	 * Select Value from List
	 * 
	 * @throws Exception
	 */

	public static void selectOption(List<WebElement> optionDrpDwn, String option, WebDriver driver,
			String validationType) throws Exception {

		for (int i = 0; i < optionDrpDwn.size(); i++) {

			if (validationType.equalsIgnoreCase("Equals")) {
				if (optionDrpDwn.get(i).getText().trim().equals(option)) {

					JavascriptExecutor jse = (JavascriptExecutor) driver;
					jse.executeScript("arguments[0].scrollIntoView(true);", optionDrpDwn.get(i));
					WebActionUtil.clickElement(optionDrpDwn.get(i), driver, option);
					break;
				}

			} else if (validationType.equalsIgnoreCase("Contains")) {
				if (optionDrpDwn.get(i).getText().trim().contains(option)) {
					WebActionUtil.clickElement(optionDrpDwn.get(i), driver, option);
					break;
				}
			}

		}
		
	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description: This method is to convert date and time in to this format
	 * yyyy-mm-ddThh-mm-ss
	 * 
	 */

	public static String convertDateToDiffFormat(String startDateString, String dateFormat) throws Exception {

		Date date1 = new Date(startDateString);

		SimpleDateFormat dateTimeInGMT = new SimpleDateFormat(dateFormat);
		TimeZone istTimeZone = TimeZone.getTimeZone("Asia/Kolkata");
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date1);
		Date finalNewDate = calendar.getTime();
		dateTimeInGMT.setTimeZone(istTimeZone);
		String finalNewDateString = dateTimeInGMT.format(finalNewDate);
		System.out.println(finalNewDateString);
		return finalNewDateString;

	}

	public static String convertDateToDiffFormat(String dateStr) throws ParseException {
		DateFormat srcDf = new SimpleDateFormat("dd-MM-yyyy");
		Date date = srcDf.parse(dateStr);
		DateFormat destDf = new SimpleDateFormat("dd-MMMM-yyyy");
		dateStr = destDf.format(date);
		return dateStr;
	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description: This method is to fetch the system date and time in
	 * yyyy-mm-ddThh-mm-ss
	 * 
	 */
	public static String getSystemDate() {
		SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("dd-MMMM-yyyy");
		TimeZone istTimeZone = TimeZone.getTimeZone("Asia/Kolkata");
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		Date finalNewDate = calendar.getTime();
		dateTimeInGMT.setTimeZone(istTimeZone);
		String finalNewDateString = dateTimeInGMT.format(finalNewDate);
		System.out.println(finalNewDateString);
		return finalNewDateString;

	}

	/*
	 * @author:Gargi Verma
	 * 
	 * Description: This method is to add days to a particular date
	 * 
	 */

	public static String addDaysToAParticularDate(String date, int day) throws ParseException {
		String oldDate = date;
		System.out.println(oldDate);
		// Specifying date format that matches the given date
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy");
		Calendar c = Calendar.getInstance();

		// Setting the date to the given date
		c.setTime(sdf.parse(oldDate));
		// Number of Days to add
		c.add(Calendar.DAY_OF_MONTH, day);
		// Date after adding the days to the given date
		String newDate = sdf.format(c.getTime());
		return newDate;

	}

	/**
	 * Compares the data in the two Excel files represented by the given input
	 * streams, closing them on completion
	 * 
	 * @param expected can't be <code>null</code>
	 * @param actual   can't be <code>null</code>
	 * @throws Exception
	 *//*
		 * private void compareExcelFiles(InputStream expected, InputStream actual)
		 * throws Exception { try { Assertion.assertEquals(new XlsDataSet(expected), new
		 * XlsDataSet(actual)); } finally { IOUtils.closeQuietly(expected);
		 * IOUtils.closeQuietly(actual); } }
		 */

	/**
	 * @author Gargi Verma
	 * @throws IOException
	 * @throws InterruptedException
	 * 
	 * 
	 * 
	 * 
	 */
	public static void waitForPageToBeDisplayed(WebElement elementOnAction, String action, WebElement element,
			WebDriver driver) throws IOException, InterruptedException {

		boolean pageNotDisplayed = true;
		int i = 0;
		while (pageNotDisplayed && i < 5) {
			try {
				if (elementOnAction.isDisplayed()) {
					elementOnAction.click();
					pageNotDisplayed = true;
					Thread.sleep(500);
					i++;
				}

			} catch (NoSuchElementException e) {

			}
		}
	}

	/**
	 * @author Gargi Verma
	 */

	public static void verifyIdenticalList(String desc, Collection actResult, Collection expResult, WebDriver driver)
			throws Exception {

		try {
			logger.info("---------Checking if both " + desc + " are identical-----------");
			Assert.assertEquals(actResult, expResult);
			logger.info("---------Both " + desc + " are identical-----------");
			MyExtentListners.test.pass("Verify Both " + desc + " are identical ||  Both " + desc + " are identical");

		} catch (AssertionError e) {
			MyExtentListners.test.addScreenCaptureFromPath(capture(driver));
			logger.info("---------Both " + desc + " are not identical-----------");
			MyExtentListners.test
					.fail("Verify Both " + desc + " are identical ||  Both " + desc + " are not identical");
			throw e;
		}

	}

	public static void selectDatefromPortalCalndar(String date, List<WebElement> tables) {

		String[] dateArray = date.split("-");
		String day = dateArray[0];
		String mnth = dateArray[1];
		String year = dateArray[2];
		int flag = 0;
		for (WebElement table : tables) {

			if (table.isDisplayed()) {

				List<WebElement> selectMonthYear = table.findElements(By.xpath("//tr[1]//th"));
				List<WebElement> displayedSelectMonthYear = new ArrayList<WebElement>();
				for (int i = 0; i < selectMonthYear.size(); i++) {
					if (selectMonthYear.get(i).isDisplayed()) {
						displayedSelectMonthYear.add(selectMonthYear.get(i));
					}

				}
				String displayedMonthYearString = displayedSelectMonthYear.get(1).getText();
				String[] monthYearINUI = displayedMonthYearString.split(" ");
				String MonthInUI = monthYearINUI[0];
				String YearINUI = monthYearINUI[1];

				while (!(year.equals(YearINUI))) {
					if (Integer.parseInt(year) > Integer.parseInt(YearINUI)) {
						displayedSelectMonthYear.get(2).click();
						flag = 2;
					} else if (Integer.parseInt(year) < Integer.parseInt(YearINUI)) {
						displayedSelectMonthYear.get(0).click();
						flag = 1;
					}

					selectMonthYear = table.findElements(By.xpath("//tr[1]//th"));
					displayedSelectMonthYear = new ArrayList<WebElement>();
					for (int i = 0; i < selectMonthYear.size(); i++) {
						if (selectMonthYear.get(i).isDisplayed()) {
							displayedSelectMonthYear.add(selectMonthYear.get(i));
						}

					}
					displayedMonthYearString = displayedSelectMonthYear.get(1).getText();
					monthYearINUI = displayedMonthYearString.split(" ");
					MonthInUI = monthYearINUI[0];
					YearINUI = monthYearINUI[1];
				}

				while (!(mnth.equals(MonthInUI))) {
					if (flag == 2) {
						displayedSelectMonthYear.get(2).click();
					}
					if (flag == 1) {
						displayedSelectMonthYear.get(0).click();
					}
					selectMonthYear = table.findElements(By.xpath("//tr[1]//th"));
					displayedSelectMonthYear = new ArrayList<WebElement>();
					for (int i = 0; i < selectMonthYear.size(); i++) {
						if (selectMonthYear.get(i).isDisplayed()) {
							displayedSelectMonthYear.add(selectMonthYear.get(i));
						}

					}
					displayedMonthYearString = displayedSelectMonthYear.get(1).getText();
					monthYearINUI = displayedMonthYearString.split(" ");
					MonthInUI = monthYearINUI[0];
					YearINUI = monthYearINUI[1];
				}
				List<WebElement> displayedDates = new ArrayList<WebElement>();
				List<WebElement> dates = table.findElements(By.xpath("//tr//td//span[@class='ng-binding']"));
				dates.addAll(table.findElements(By.xpath("//tr//td//span[@class='ng-binding text-info']")));
				for (int i = 0; i < dates.size(); i++) {
					if (dates.get(i).isDisplayed()) {
						displayedDates.add(dates.get(i));
					}

				}
				for (WebElement eachDate : displayedDates) {
					if (eachDate.isDisplayed()) {
						if (eachDate.getText().trim().equals(day)) {
							eachDate.click();
							break;
						}

					}
				}
				break;

			}
		}

	}

	public static String decimalRoundingOff(String data, String format) {

		String str = data;

		switch (format) {

		case ".0":
			if (!(str.contains("."))) {
				str = str + ".0";
			}
			break;

		case ".00":
			if (!(str.contains("."))) {
				str = str + ".00";
			} else if ((str.substring(str.indexOf('.'))).length() == 2) {
				str = str + "0";
			}
			break;
		}

		return str;
	}

	public static String changeDateTimeFormat(String time, String inputFormat, String outputFormat)
			throws ParseException {
		/*
		 * "hh:mm:ss a" "hh:mm a" "HH:mm"
		 */
		SimpleDateFormat displayFormat = new SimpleDateFormat(outputFormat);
		SimpleDateFormat parseFormat = new SimpleDateFormat(inputFormat);

		Date date = parseFormat.parse(time);
		String output = displayFormat.format(date);

		return output;
	}

	public static void verifyIdenticalMap(String desc, Map expResult, Map actResult, WebDriver driver)
			throws IOException {
		try {
			logger.info("---------Checking if both " + desc + " are identical-----------");
			Assert.assertEquals(actResult, expResult);
			logger.info("---------Both " + desc + " are identical-----------");
			MyExtentListners.test.pass("Verify Both " + desc + " are identical ||  Both " + desc + " are identical");

		} catch (AssertionError e) {
			MyExtentListners.test.addScreenCaptureFromPath(capture(driver));
			logger.info("---------Both " + desc + " are not identical-----------");
			MyExtentListners.test
					.fail("Verify Both " + desc + " are identical ||  Both " + desc + " are not identical");
			throw e;
		}
	}

	public static String roundingToDecimalPlace(String val, int precision) {
		Double value = Double.parseDouble(val);
		int scale = (int) Math.pow(10, precision);
		return Double.toString(((double) Math.round(value * scale) / scale));
		
	
	}
	
	
	/*
	 * Description: this method is used to convert string value to int
	 * 
	 * or fetch integer value
	 * 
	 * @author : Gargi Verma
	 */

	public static int getIntegerValue(String givenString) {
		StringBuilder str = new StringBuilder();
		str.setLength(0);
		for (int j = 0; j <= givenString.length() - 1; j++) {
			if (Character.isDigit(givenString.charAt(j))) {
				str.append(givenString.charAt(j));
			}
		}
		int convertedPrice = Integer.parseInt(str.toString());
		return convertedPrice;

	}



}