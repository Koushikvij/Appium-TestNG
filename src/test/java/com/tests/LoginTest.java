package com.tests;

import base.BaseTest;
import com.automate.constants.StringConstants;
import com.automate.customannotations.FrameworkAnnotation;
import com.automate.driver.enums.CategoryType;
import com.automate.pages.LoginPage;
import com.automate.utils.XmlHandler;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LoginTest extends BaseTest
{
  LoginPage loginPage;

  @BeforeMethod
  public void initialize()
  {
    loginPage = new LoginPage();
  }


  @FrameworkAnnotation(author = "User-1", category = {CategoryType.REGRESSION, CategoryType.SMOKE})
  @Test(description = "Incorrect Username and Password combination")
  public void invalidLogin()
  {
    String username = XmlHandler.readTestXMLData("InvalidLogin","Username");
    String password = XmlHandler.readTestXMLData("InvalidLogin","Password");

    loginPage.setUsername(username)
      .setPassword(password)
      .tapOnLogin();
    String invalidLoginErrorMessage = loginPage.getErrorText();

    Assert.assertEquals(invalidLoginErrorMessage, StringConstants.INVALID_LOGIN_ERROR_MESSAGE, "Assertion for Invalid login error");
  }

  @FrameworkAnnotation(author = "User-2", category = {CategoryType.REGRESSION, CategoryType.SANITY})
  @Test(description = "Correct Username and Password combination")
  public void validLogin()
  {
    String username = XmlHandler.readTestXMLData("ValidLogin","Username");
    String password = XmlHandler.readTestXMLData("ValidLogin","Password");

    String productPageTitle = loginPage.setUsername(username)
      .setPassword(password)
      .tapOnLogin()
      .getProductPageTitle();

    Assert.assertEquals(productPageTitle, StringConstants.PRODUCT_PAGE_TITLE, "Assertion for valid login");
  }
}
