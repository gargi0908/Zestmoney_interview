package com.zestmoney.web.listener;

import com.aventstack.extentreports.ExtentReports;


public class ExtentManager {

	private static ExtentReports extent;

	public static ExtentReports getInstance() {
		if (extent == null)
			createInstance("./Reports/HtmlReport.html");
		return extent;
	}

	public static ExtentReports createInstance(String fileName) {

		extent = new ExtentReports();
		return extent;
	}
}