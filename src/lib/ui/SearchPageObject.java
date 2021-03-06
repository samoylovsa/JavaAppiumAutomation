package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject{

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    private static final String
    SEARCH_INIT_ELEMENT = "//*[contains(@text, 'Search Wikipedia')]",
    SEARCH_INPUT = "//*[contains(@text, 'Search…')]",
    SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@text='{SUBSTRING}']",
    SEARCH_CANCEL_BUTTON = "//*[contains(@content-desc, 'Clear query')]",
    SEARCH_RESULT_ELEMENT = "//*[@resource-id='org.wikipedia:id/page_list_item_container']";

    /* TEMPLATES METHODS */
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    /* TEMPLATES METHODS */

    public void initSearchInput() {
        this.waitForElementAndClick(
                By.xpath(SEARCH_INIT_ELEMENT),
                "Cannot find and click search init element",
                5);
        this.waitForElementPresent(
                By.xpath(SEARCH_INPUT),
                "Cannot find search input after clicking search init element");
    }

    public void typeSearchLine(String search_line) {
        this.waitForElementAndSendKeys(
                By.xpath(SEARCH_INPUT),
                search_line,
                "Cannot find and type into search input",
                5);
    }

    public void waitForSearchResult(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(
                By.xpath(search_result_xpath),
                "Cannot find search result with substring " + substring);
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(By.xpath(SEARCH_CANCEL_BUTTON),
                "Cannot find search cancel button!",
                5);
    }

    public void clickCancelSearch() {
        this.waitForElementAndClick(By.xpath(SEARCH_CANCEL_BUTTON),
                "Cannot find and click search cancel button!",
                5);
    }

    public void clickByArticleWithSubstring(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(
                By.xpath(search_result_xpath),
                "Cannot find and click search result with substring " + substring,
        10);
    }

    public int getAmountOfFoundArticles() {
        return this.getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT));
    }
}
