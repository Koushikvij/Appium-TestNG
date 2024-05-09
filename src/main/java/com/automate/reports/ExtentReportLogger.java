package com.automate.reports;

import com.automate.driver.enums.ConfigProperties;
import com.automate.utils.configloader.PropertyUtils;
import com.automate.utils.screenshot.ScreenshotService;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExtentReportLogger {

  public static void logPass(String message)
  {
    if (PropertyUtils.getPropertyValue(ConfigProperties.PASSED_STEP_SCREENSHOTS).equalsIgnoreCase("yes")) {
      ExtentReportManager.getExtentTest().pass(message,
                   MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotService.getScreenshotAsBase64()).build());
    }
    else {
      ExtentReportManager.getExtentTest().pass(MarkupHelper.createLabel(message, ExtentColor.GREEN));
    }
  }

  public static void logFail(String message, Throwable t)
  {
    if (PropertyUtils.getPropertyValue(ConfigProperties.FAILED_STEP_SCREENSHOTS).equalsIgnoreCase("yes")) {
      ExtentReportManager.getExtentTest().fail(MarkupHelper.createLabel(message, ExtentColor.RED))
        .fail(MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotService.getScreenshotAsBase64()).build()).fail(t);
    }
    else {
      ExtentReportManager.getExtentTest().fail(message).fail(t);
    }
  }

  public static void logSkip(String message) {
    if (PropertyUtils.getPropertyValue(ConfigProperties.SKIPPED_STEP_SCREENSHOTS).equalsIgnoreCase("yes")) {
      ExtentReportManager.getExtentTest().skip(message,
                MediaEntityBuilder.createScreenCaptureFromBase64String( ScreenshotService.getScreenshotAsBase64()).build());
    }
    else {
      ExtentReportManager.getExtentTest().log(Status.SKIP, message);
    }
  }

  public static void logInfo(String message)
  {
    ExtentReportManager.getExtentTest().log(Status.INFO, message);
  }

  public static void logInfoWithScreenshot(String message)
  {
    ExtentReportManager.getExtentTest().log(Status.INFO, message);
    ExtentReportManager.getExtentTest().info(message,
                    MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotService.getScreenshotAsBase64()).build());
  }

  public static void warning(String message) {
    ExtentReportManager.getExtentTest().log(Status.WARNING, message);
  }

  public static void warningWithScreenshot(String message)
  {
    ExtentReportManager.getExtentTest().log(Status.WARNING, message);
    ExtentReportManager.getExtentTest().warning(message,
                    MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotService.getScreenshotAsBase64()).build());
  }
}
