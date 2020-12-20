package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class NavigationUI extends MainPageObject{

    public NavigationUI(AppiumDriver driver) {
        super(driver);
    }

    private static final String
    MY_LISTS_LINK = "//*[@content-desc='My lists']";

    public void clickMyList() {
        this.waitForElementAndClick(
                By.xpath(MY_LISTS_LINK),
                "Cannot find 'My lists' button",
                5
        );
    }
}
