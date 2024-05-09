package com.tests;

import base.BaseTest;
import com.automate.constants.StringConstants;
import com.automate.customannotations.FrameworkAnnotation;
import com.automate.driver.manager.DriverManager;
import com.automate.driver.enums.CategoryType;
import com.automate.pages.GoogleSearchPage;
import com.automate.utils.XmlHandler;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.testng.Assert;
import org.testng.annotations.Test;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GoogleTest extends BaseTest {

  @FrameworkAnnotation(author = "User-1", category = {CategoryType.REGRESSION, CategoryType.SANITY, CategoryType.SMOKE})
  @Test(description = "Google search")
  public void googleSearch()
  {
    String url = XmlHandler.readTestXMLData("googleSearch", "Url");
    String SearchTerm = XmlHandler.readTestXMLData("googleSearch","SearchTerm");

    DriverManager.getDriver().get(url);
    String searchResultsPageTitle = new GoogleSearchPage()
      .performSearch(SearchTerm)
      .getSearchResultsPageTitle();

    Assert.assertEquals(searchResultsPageTitle, StringConstants.SEARCH_RESULTS_PAGE_TITLE);
  }
}
