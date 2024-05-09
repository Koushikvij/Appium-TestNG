package com.automate.listeners;

import com.automate.customannotations.FrameworkAnnotation;
import com.automate.reports.ExtentReportLogger;
import com.automate.reports.ExtentReportManager;
import com.aventstack.extentreports.model.Test;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.internal.TestNGMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Listeners implements ITestListener, ISuiteListener {

  @Override
  public void onStart(ISuite suite) {
    ExtentReportManager.initExtentReport();
  }

  @Override
  public void onTestStart(ITestResult result)
  {
    ExtentReportManager.createTest(result.getMethod().getMethodName());
    ExtentReportManager.addAuthors(
      result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(FrameworkAnnotation.class).author());
    ExtentReportManager.addCategories(
      result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(FrameworkAnnotation.class).category());
    ExtentReportManager.addDevices();
    ExtentReportLogger.logInfo("Test - <b>" + result.getMethod().getMethodName() + "</b> is started");
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    ExtentReportLogger.logPass("Test - <b>" + result.getMethod().getMethodName() + "</b> is passed");
  }

  @Override
  public void onTestFailure(ITestResult result) {
    ExtentReportLogger.logFail("Test - <b>" + result.getMethod().getMethodName() + "</b> is failed", result.getThrowable());
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    ExtentReportLogger.logSkip("Test - <b>" + result.getMethod().getMethodName() + "</b> is skipped");
  }

  @Override
  public void onFinish(ISuite suite) {
    ExtentReportManager.MarkRerun();
    ExtentReportManager.getExtentReports().setSystemInfo("Total Execution time", ExtentReportManager.TotalExecutionTime());
    ExtentReportManager.flushExtentReport();
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    // No implementation
  }

  @Override
  public void onStart(ITestContext iTestContext) {
    // No implementation
  }

  @Override
  public void onFinish(ITestContext context)
  {

  }
}
