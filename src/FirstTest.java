import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class FirstTest {

    private AppiumDriver driver;

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String error_message) {
        return waitForElementPresent(by, error_message, 5);
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by , String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        boolean result = wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
        if (result) System.out.println("true");
        else if (!result) System.out.println("false");
        return result;
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSecond) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSecond);
        element.clear();
        return element;
    }

    protected void swipeUp(int timeOfSwipe) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);
        action.press(x, start_y).waitAction(timeOfSwipe).moveTo(x, end_y).release().perform();
    }

    protected void swipeUpQuick() {
        swipeUp(200);
    }

    protected void swipeUpToFindElement(By by, String error_message, int max_swipes) {
        int already_swipes = 0;
        while (driver.findElements(by).size() == 0) {
            if (already_swipes > max_swipes) {
                waitForElementPresent(by, "Cannot find element by swiping up. \n" + error_message, 0);
                return;
            }
            swipeUpQuick();
            ++already_swipes;
        }
    }

    protected void swipeElementToLeft(By by, String error_message) {
        WebElement element = waitForElementPresent(by, error_message, 10);
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y+lower_y) / 2;
        TouchAction action = new TouchAction(driver);
        action.press(right_x, middle_y).waitAction(150).moveTo(left_x, middle_y).release().perform();
    }

    private int getAmountOfElements(By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }

/*    private String waitElementAndGetAttribute(By by, String attribute, String error_message, long timeoutInSeconds) {
        MobileElement mobileElement = (MobileElement) waitForElementPresent(by, error_message, timeoutInSeconds);
        AndroidElement androidElement = (AndroidElement) waitForElementPresent(by, error_message, timeoutInSeconds);
        return androidElement.getAttribute(attribute);
        Убрал метод, так как выдает ошибку org.openqa.selenium.UnsupportedCommandException: Method is not implemented
    }*/

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","8.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","C:/JavaAppiumAutomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void firstTests() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find 'Search…'",
                5
        );

        waitForElementPresent(
                By.xpath("//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                5
        );
    }

    @Test
    public void testCancelSearch() {

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find 'Search…'",
                5
        );

        waitForElementAndClear(
                By.xpath("//*[contains(@text, 'Java')]"),
                "Cannot find 'Java'",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[contains(@content-desc, 'Clear query')]"),
                "Cannot find Back button",
                10
        );

        waitForElementNotPresent(
                By.xpath("//*[contains(@content-desc, 'Clear query')]"),
                "Cannot find X",
                10
        );
    }

    @Test
    public void testSwipeArticle() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find 'Search…'",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Java (programming language)')]"),
                "Cannot find 'Java (programming language)'",
                10
        );
        swipeUp(2000);
        swipeUp(2000);
        swipeUp(2000);
    }

    @Test
    public void testSwipeArticleToFindElement() {

        swipeUpToFindElement(
                By.xpath("//*[contains(@text, 'Today on Wikipedia')]"),
                "Cannot find 'Today on Wikipedia'",
                5
        );
    }

    @Test
    public void testSaveArticleToMyList() {

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find 'Search…'",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                5
        );
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find 'More options' button",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find 'Add to reading list' button",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='GOT IT']"),
                "Cannot find 'GOT IT' button",
                5
        );
        waitForElementAndClear(
                By.xpath("//*[@text='My reading list']"),
                "Cannot clear text 'My reading list'",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[@resource-id='org.wikipedia:id/text_input']"),
                "Learning programming",
                "Cannot put text into article folder",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot find 'OK' button",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Cannot find X button",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@content-desc='My lists']"),
                "Cannot find 'My lists' button",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='Learning programming']"),
                "Cannot find 'My lists' button",
                5
        );
        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot swipe element to the left"
        );
        waitForElementNotPresent(
                By.xpath("//*[@text='object-oriented programming language']"),
                "Cannot delete saved article",
                5
        );
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Linkin park discography",
                "Cannot find 'Linkin park discography'",
                5
        );
        int amount_of_search_results = getAmountOfElements(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']")
        );
        Assert.assertTrue(
                "We found too few results!",
                amount_of_search_results > 0
        );
    }

    /*@Test
    public void testChangeScreenOrientationOnSearchResults() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find 'Search…'",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                5
        );

        String title_before_rotation = waitElementAndGetAttribute(
                By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text']"),
                "text",
                "Cannot find title of article",
                10
        );

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String title_after_rotation = waitElementAndGetAttribute(
                By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text']"),
                "text",
                "Cannot find title of article",
                10
        );

        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                title_before_rotation,
                title_after_rotation
        );
    }
    Убрал тест, так как выдаёт ошибку org.openqa.selenium.UnsupportedCommandException: Method is not implemented
    */

    @Test
    public void testCheckSearchArticleInBackground() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find 'Search…'",
                5
        );
        waitForElementPresent(
                By.xpath("//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language'",
                10
        );
        driver.runAppInBackground(3);
        waitForElementPresent(
                By.xpath("//*[@text='Object-oriented programming language']"),
                "Cannot find article after returning from background",
                10
        );
    }
}