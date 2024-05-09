package com.automate.pages;

import com.automate.driver.enums.WaitStrategy;
import com.automate.pages.screen.ScreenActions;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public final class WebViewPage extends ScreenActions
{
  @AndroidFindBy(className = "android.widget.EditText")
  private static MobileElement EnterUrlTextBox;

  @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-GO TO SITE\"]")
  private static MobileElement GoToSiteBtn;

  @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView")
  private static MobileElement errorMessage;

  public WebViewPage gotoSite(String url)
  {
    enter(EnterUrlTextBox, url, "Url TextBox");
    click(GoToSiteBtn, "GoToSiteBtn");
    return this;
  }

  public String getErrorText() {
    return getText(errorMessage, WaitStrategy.VISIBLE);
  }

}
