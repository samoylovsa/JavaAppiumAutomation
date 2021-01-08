package lib;

import io.appium.java_client.AppiumDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.RemoteWebDriver;

public class CoreTestCase extends TestCase {

    private static final String
            PLATFORM_IOS = "ios",
            PLATFORM_ANDROID = "android";

    protected RemoteWebDriver driver;
    protected Platform platform;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        this.platform = new Platform();
        driver = this.platform.getDriver();
        this.rotateScreenPortrait();
    }

    @Override
    protected void tearDown() throws Exception {
        driver.quit();
        super.tearDown();
    }

    protected void backgroundApp(int seconds) {
        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.runAppInBackground(seconds);
        }
        else {
            System.out.println("Method backgroundApp() does nothing for platform");
        }
    }

    protected void rotateScreenPortrait() {
        if (driver instanceof AppiumDriver) {
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.rotate(ScreenOrientation.PORTRAIT);
        }
        else {
            System.out.println("Method rotateScreenPortrait() does nothing for platform");
        }
    }
}
