package com.automate.utils.screenshot;

import com.automate.constants.FrameworkConstants;
import com.automate.driver.manager.DriverManager;
import com.automate.utils.TestUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ScreenshotUtils {

  // This class is to handle the change in third party library
  @SneakyThrows
  public static void captureScreenshotAsFile(String testName) {
	  try {
    File source = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
    File destination = new File(FrameworkConstants.SCREENSHOT_PATH + File.separator + testName + ".png");
    FileUtils.copyFile(source, destination);
	  }catch(Exception e)
	  {
		  
	  }
  }

  public static String captureScreenshotAsBase64()
  {
    try
    {
      if (Objects.nonNull(DriverManager.getDriver()))
        return ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64);
    }
    catch (NullPointerException ex)
    {
      TestUtils.log().error("No Driver Instance present",ex);
    }

    return null;
  }
}
