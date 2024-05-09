package com.automate.pages;

import com.automate.driver.enums.WaitStrategy;
import com.automate.pages.screen.ScreenActions;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public final class LoginPage extends ScreenActions {

  @AndroidFindBy(accessibility = "test-Username")
  private static MobileElement txtFieldUsername;

  @AndroidFindBy(accessibility = "test-Password")
  private static MobileElement txtFieldPassword;

  @AndroidFindBy(accessibility = "test-LOGIN")
  private static MobileElement btnLogin;

  @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='test-Error message']/android.widget.TextView")
  private static MobileElement errorMessage;

  public boolean isLoginPageDisplayed() {
    return isElementDisplayed(txtFieldUsername);
  }

  public LoginPage setUsername(String username) {
    enter(txtFieldUsername, username, "Username");
    return this;
  }

  public LoginPage setPassword(String password) {
    enter(txtFieldPassword, password, "Password");
    return this;
  }

  public ProductPage tapOnLogin() {
    click(btnLogin, "Login");
    return new ProductPage();
  }

  public String getErrorText() {
    return getText(errorMessage, WaitStrategy.VISIBLE);
  }
}
