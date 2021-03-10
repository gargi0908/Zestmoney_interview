package com.zestmoney.web.listener;

import java.io.File;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.zestmoney.web.init.GlobalVariables;


public class MyExtentListners implements ITestListener {
	public static String[] sDataGuest = null;
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static ThreadLocal parentTest = new ThreadLocal();
	int count = 1;
	public static String sExcelDate;
	public static String sExcelPath;
	public static String screenShotPath;
	public static int iPassCount = 0;
	public static int iFailCount = 0;
	public static int iSkippedCount = 0;
	public static int iTotal_Executed;
	public static String sStartTime;
	public static String sEndTime;
	public static long lTotalExecutionTime;
	public static ArrayList<String> sStatus = new ArrayList<String>();
	public static ArrayList<String> sStartMethodTime = new ArrayList<String>();
	public static ArrayList<String> sMethodEndTime = new ArrayList<String>();

	public static long startTime;
	public static long endtTime;
	String className;


	public void onTestStart(ITestResult result) {
		// test = extent.createTest(result.getName().toString());
		className = result.getTestClass().getName().toString();
		className = className.substring(className.lastIndexOf(".") + 1, className.length());
		test = extent.createTest(className);
		startTime = result.getStartMillis();
		sStartMethodTime.add(startTime + "");
		parentTest.set(test);
		test.info(className + " has started");
		htmlReporter.config().setTheme(Theme.DARK);
	}

	
	 
	public void onTestSuccess(ITestResult result) {
		endtTime = result.getEndMillis();
		long totTimeInMethod = endtTime - startTime;
		int seconds = (int) (totTimeInMethod / 1000L) % 60;
		int minutes = (int) (totTimeInMethod / 60000L % 60L);
		int hours = (int) (totTimeInMethod / 3600000L % 24L);

		sStatus.add("Passed");
		test.pass(MarkupHelper.createLabel(className + " case has PASSED", ExtentColor.GREEN));
		
		try {
			extent.flush();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	
	 
	public void onTestFailure(ITestResult result) {

		sStatus.add("Failed");
		test.fail(MarkupHelper.createLabel(className + " test script has FAILED", ExtentColor.RED));
		try {
			extent.flush();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}


	public void onTestSkipped(ITestResult result) {
		sStatus.add("Skipped");
		test.skip(MarkupHelper.createLabel(className + " test script has SKIPPED", ExtentColor.YELLOW));
		try {
			extent.flush();
		} catch (Exception e) {
		}
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}



	public void onStart(ITestContext context) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
		String sDate = sdf.format(date);
		sExcelDate = sDate;
		sStartTime = sDate;
		String testOutputDir = GlobalVariables.userDirectory + "/test-output";
		String htmlDir = GlobalVariables.userDirectory + "/reports" + "/Run-" + sDate + "/extent/";
		String excelDir = GlobalVariables.userDirectory + "/reports" + "/Run-" + sDate + "/excel/";
		sExcelPath = excelDir;
		screenShotPath = GlobalVariables.userDirectory+ "/reports" + "/Run-" + sDate + "/screenshots";
		// String pdfDir = GenericLib.sDirPath + "/reports" + "/Run-" + sDate +
		// "/pdf/";
		// String pdfDir = GenericLib.sDirPath + "/reports/pdfreports";
		System.out.println(testOutputDir);
		System.out.println(htmlDir);

		// Setting test-output folder location
		File testOutputFile = new File(testOutputDir);

		if (!testOutputFile.exists()) {
			System.out.println(testOutputFile + " does not exist");
			return;
		}
		// deletes the test output folder
		if (testOutputFile.isDirectory()) {
			try {
				testOutputFile.delete();
				System.out.println("------test output dir deleted--------");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("Deleted file/folder: " + testOutputFile.getAbsolutePath());

		// Setting Extent Report Location
		File file = new File(htmlDir);
		if (!(file.exists())) {
			file.mkdirs();
			System.out.println("--extent folder created");
		}
		String filepath = htmlDir + "extent_" + sDate + ".html";
		System.out.println(filepath);
		File file1 = new File(filepath);
		if (!(file1.exists())) {
			try {
				file1.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Setting Excel Report Location
		File file2 = new File(excelDir);
		if (!(file2.exists())) {
			file2.mkdirs();
			System.out.println("--excel folder created");
		}

		// Setting ScreenShot Report Location
		File file3 = new File(screenShotPath);
		if (!(file3.exists())) {
			file3.mkdirs();
			System.out.println("--scrrenshot folder created");
		}

		// GenericLib.setPropertyValue(GenericLib.sDirPath +
		// "/pdfngreport.properties",
		// "pdfreport.outputdir",
		// pdfDir.toString());
		htmlReporter = new ExtentHtmlReporter(file1);

		// htmlReporter.setAppendExisting(true);

		extent = ExtentManager.createInstance(file.toString());

		extent.attachReporter(htmlReporter);

		// Setting System information in the custom report
		Properties properties = System.getProperties();
		// Setting System Info for Execution Type Sequential
/*		if (ExcelLibrary
				.getExcelData("./config/config.xlsx", "config", 1,
						GenericLib.getHeaderColumnIndex("./config/config.xlsx", "config", "Execution Type"))
				.trim().equalsIgnoreCase("Sequential")) {
			extent.setSystemInfo("Application Type", "WEB");
			extent.setSystemInfo("Application Name", "VIL");
			extent.setSystemInfo("OS ", properties.getProperty("os.name"));
			extent.setSystemInfo("Test Type", context.getName());
			extent.setSystemInfo("Execution Type ",
					ExcelLibrary
							.getExcelData("./config/config.xlsx", "config", 1,
									GenericLib.getHeaderColumnIndex("./config/config.xlsx", "config", "Execution Type"))
							.trim());
			extent.setSystemInfo("No Of Browsers ", ExcelLibrary
					.getExcelData("./config/config.xlsx", "config", 1,
							GenericLib.getHeaderColumnIndex("./config/config.xlsx", "config", "Number of Browsers"))
					.trim());
			extent.setSystemInfo("Execution Environment", System.getProperty("ENV"));
			extent.setSystemInfo("Browser Name", System.getProperty("BROWSER"));

			extent.setSystemInfo("Circle Name", System.getProperty("CIRCLENAME"));

		}
		// Setting System Info for Execution Type Parallel
		else if (ExcelLibrary
				.getExcelData("./config/config.xlsx", "config", 1,
						GenericLib.getHeaderColumnIndex("./config/config.xlsx", "config", "Execution Type"))
				.trim().equalsIgnoreCase("Parallel")) {

			if (count == 1) {
				count = 2;
				extent.setSystemInfo("OS ", properties.getProperty("os.name"));
				extent.setSystemInfo("Execution Type ", ExcelLibrary
						.getExcelData("./config/config.xlsx", "config", 1,
								GenericLib.getHeaderColumnIndex("./config/config.xlsx", "config", "Execution Type"))
						.trim());
				extent.setSystemInfo("No Of Browsers ", ExcelLibrary
						.getExcelData("./config/config.xlsx", "config", 1,
								GenericLib.getHeaderColumnIndex("./config/config.xlsx", "config", "Number of Browsers"))
						.trim());
				// extent.setSystemInfo("Test Type", context.getName());

				int noOfBrowsers = Integer.parseInt(ExcelLibrary.getExcelData("./config/config.xlsx", "config", 1,
						GenericLib.getHeaderColumnIndex("./config/config.xlsx", "config", "Number of Browsers")));
				ArrayList<String> browserNames = new ArrayList<String>();
				for (int i = 1; i <= noOfBrowsers; i++) {
					browserNames.add(ExcelLibrary.getExcelData("./config/config.xlsx", "config", i,
							GenericLib.getHeaderColumnIndex("./config/config.xlsx", "config", "Browser Name")));
				}
				for (int i = 1; i <= noOfBrowsers; i++) {
					HashMap<String, String> testParameters = new HashMap<String, String>();
					testParameters.put(
							ExcelLibrary.getExcelData("./config/config.xlsx", "config", 0,
									GenericLib.getHeaderColumnIndex("./config/config.xlsx", "config", "Browser Name")),
							ExcelLibrary.getExcelData("./config/config.xlsx", "config", i,
									GenericLib.getHeaderColumnIndex("./config/config.xlsx", "config", "Browser Name")));
					for (Map.Entry entry : testParameters.entrySet()) {
						System.out.println(entry.getKey() + " : " + entry.getValue());
						extent.setSystemInfo(entry.getKey().toString(), entry.getValue().toString());
					}
				}*/
			/*	extent.setSystemInfo("Application Type", "WEB");
				extent.setSystemInfo("Application Name", "Redbus");

			}

		}*/
		// Customising the html report view
		//htmlReporter.loadXMLConfig(BaseClass.userDirectory + "/extent-config.xml");
//		htmlReporter.config().setDocumentTitle("VIL Automation");
//		htmlReporter.config().setReportName("VIL Automation Test Report");
//		htmlReporter.config().setTheme(Theme.DARK);
	}

	public void onFinish(ITestContext context) {
		// flushing all logs and details into the report.
		extent.flush();
		try {
			iPassCount = context.getPassedTests().size();
			iFailCount = context.getFailedTests().size();
			iSkippedCount = context.getSkippedTests().size();
			iTotal_Executed = iPassCount + iFailCount + iSkippedCount;
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
			String sDate = sdf.format(date);
			sEndTime = sDate;
			lTotalExecutionTime = context.getEndDate().getTime() - context.getStartDate().getTime();

			//ExcelListener.generateReport(sExcelPath + "vil_excel_" + sExcelDate + ".xlsx");
			// ExcelListener.generateReport();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
