package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject{

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    public static final String
    TITLE = "//*[@resource-id='org.wikipedia:id/view_page_title_text']",
    FOOTER_ELEMENT = "//*[@text='View page in browser']",
    OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
    OPTIONS_ADD_TO_MY_LIST_BUTTON = "//*[@text='Add to reading list']",
    ADD_TO_MY_LIST_OVERLAY = "//*[@text='GOT IT']",
    MY_LIST_NAME_INPUT = "//*[@resource-id='org.wikipedia:id/text_input']",
    MY_LIST_OK_BUTTON = "//*[@text='OK']",
    CLOSE_ARTICLE_BUTTON = "//*[@content-desc='Navigate up']";



    public WebElement waitForTitleElement() {
       return this.waitForElementPresent(
               By.xpath(TITLE),
               "Cannot find article title on page",
               10
       );
    }

    public String getArticleTitle() {
        AndroidElement androidElement = (AndroidElement) waitForTitleElement();
        return androidElement.getAttribute("text");
    }

    public void swipeToFooter() {
        this.swipeUpToFindElement(
              By.xpath(FOOTER_ELEMENT),
                "Cannot find the end of article",
                20
        );
    }

    public void addArticleToMyList(String name_of_folder) {
        this.waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),
                "Cannot find 'More options' button",
                5
        );
        this.waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "Cannot find 'Add to reading list' button",
                5
        );
        this.waitForElementAndClick(
                By.xpath(ADD_TO_MY_LIST_OVERLAY),
                "Cannot find 'GOT IT' button",
                5
        );
        this.waitForElementAndClear(
                By.xpath(MY_LIST_NAME_INPUT),
                "Cannot clear text 'My reading list'",
                5
        );
        this.waitForElementAndSendKeys(
                By.xpath(MY_LIST_NAME_INPUT),
                name_of_folder,
                "Cannot put text into article folder",
                5
        );
        this.waitForElementAndClick(
                By.xpath(MY_LIST_OK_BUTTON),
                "Cannot find 'OK' button",
                5
        );
    }

    public void closeArticle() {
        this.waitForElementAndClick(
                By.xpath(CLOSE_ARTICLE_BUTTON),
                "Cannot find X button",
                5
        );
    }
}
