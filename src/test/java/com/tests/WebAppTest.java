package com.tests;

import base.BaseTest;
import com.automate.constants.StringConstants;
import com.automate.customannotations.FrameworkAnnotation;
import com.automate.driver.manager.DriverManager;
import com.automate.driver.enums.CategoryType;
import com.automate.pages.LoginPage;
import com.automate.pages.MenuPage;
import com.automate.pages.SettingsPage;
import com.automate.pages.WebViewPage;
import com.automate.utils.XmlHandler;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WebAppTest extends BaseTest {
  LoginPage loginPage;
  MenuPage menuPage;
  SettingsPage settingsPage;
  WebViewPage webViewPage;

  @BeforeMethod
  public void initialize() {
    loginPage = new LoginPage();
    menuPage = new MenuPage();
    settingsPage = new SettingsPage();
    webViewPage = new WebViewPage();
  }


  @FrameworkAnnotation(author = "User-1", category = {CategoryType.REGRESSION, CategoryType.SANITY, CategoryType.SMOKE})
  @Test
  public void HybridApp() {

    String username = XmlHandler.readTestXMLData("ValidLogin", "Username");
    String password = XmlHandler.readTestXMLData("ValidLogin", "Password");

    loginPage.setUsername(username).setPassword(password).tapOnLogin();
    menuPage.pressMenuIcon().pressWebviewButton();

    String url = "www.qualibar.com";

    String Errormsg = webViewPage.gotoSite("").getErrorText();
    Assert.assertEquals(Errormsg, StringConstants.INCORRECT_URL_ERROR, "Assertion for Invalid Url error");

    webViewPage.gotoSite(url);

    Set<String> contextNames = DriverManager.getDriver().getContextHandles();
    String webViewContext = "";
    for (String context : contextNames) {
      if (context.contains("WEBVIEW"))
        webViewContext = context;
      System.out.println(context);
    }

    if (DriverManager.getDriver().getContext().equals("NATIVE_APP"))
    {
      DriverManager.getDriver().context(webViewContext);
      System.out.println("Switched to WebView");
    }
    else if (DriverManager.getDriver().getContext().contains("WEBVIEW"))
      System.out.println("Was Already On WebView");

    String Title = DriverManager.getDriver().getTitle();
    Assert.assertEquals(Title, "Qualibar", "Assertion for Title");
  }
}
