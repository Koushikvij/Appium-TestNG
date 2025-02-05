package com.automate.pages;

import com.automate.pages.screen.ScreenActions;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public final class SettingsPage extends ScreenActions {

  @AndroidFindBy(accessibility = "test-LOGOUT")
  private static MobileElement logOutButton;

  @AndroidFindBy(accessibility = "test-WEBVIEW")
  private static MobileElement WebView;


  public WebViewPage pressWebviewButton()
  {
    click(WebView, "WebView");
    return  new WebViewPage();
  }


  public LoginPage pressLogOutButton() {
    click(logOutButton, "Logout");
    return new LoginPage();
  }
}
