import lib.CoreTestCase;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

public class FirstTest extends CoreTestCase {

    private MainPageObject MainPageObject;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        MainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testSearch() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() {

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find 'Search…'",
                5
        );

        MainPageObject.waitForElementAndClear(
                By.xpath("//*[contains(@text, 'Java')]"),
                "Cannot find 'Java'",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@content-desc, 'Clear query')]"),
                "Cannot find Back button",
                10
        );

        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[contains(@content-desc, 'Clear query')]"),
                "Cannot find X",
                10
        );
    }

    @Test
    public void testSwipeArticle() {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find 'Search…'",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Java (programming language)')]"),
                "Cannot find 'Java (programming language)'",
                10
        );
        MainPageObject.swipeUp(2000);
        MainPageObject.swipeUp(2000);
        MainPageObject.swipeUp(2000);
    }

    @Test
    public void testSwipeArticleToFindElement() {

        MainPageObject.swipeUpToFindElement(
                By.xpath("//*[contains(@text, 'Today on Wikipedia')]"),
                "Cannot find 'Today on Wikipedia'",
                5
        );
    }

    @Test
    public void testSaveArticleToMyList() {

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find 'Search…'",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find 'More options' button",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find 'Add to reading list' button",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='GOT IT']"),
                "Cannot find 'GOT IT' button",
                5
        );
        MainPageObject.waitForElementAndClear(
                By.xpath("//*[@text='My reading list']"),
                "Cannot clear text 'My reading list'",
                5
        );
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[@resource-id='org.wikipedia:id/text_input']"),
                "Learning programming",
                "Cannot put text into article folder",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot find 'OK' button",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@content-desc='Navigate up']"),
                "Cannot find X button",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@content-desc='My lists']"),
                "Cannot find 'My lists' button",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Learning programming']"),
                "Cannot find 'My lists' button",
                5
        );
        MainPageObject.swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot swipe element to the left"
        );
        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[@text='object-oriented programming language']"),
                "Cannot delete saved article",
                5
        );
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Linkin park discography",
                "Cannot find 'Linkin park discography'",
                5
        );
        int amount_of_search_results = MainPageObject.getAmountOfElements(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']")
        );
        Assert.assertTrue(
                "We found too few results!",
                amount_of_search_results > 0
        );
    }

    /*@Test
    public void testChangeScreenOrientationOnSearchResults() {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find 'Search…'",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                5
        );

        String title_before_rotation = MainPageObject.waitElementAndGetAttribute(
                By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text']"),
                "text",
                "Cannot find title of article",
                10
        );

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String title_after_rotation = MainPageObject.waitElementAndGetAttribute(
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
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find 'Search…'",
                5
        );
        MainPageObject.waitForElementPresent(
                By.xpath("//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language'",
                10
        );
        driver.runAppInBackground(3);
        MainPageObject.waitForElementPresent(
                By.xpath("//*[@text='Object-oriented programming language']"),
                "Cannot find article after returning from background",
                10
        );
    }
}