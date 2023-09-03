import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.AppiumDriver;
import java.net.URL;

public class AllowCameraPopup {
    public static final String AUTOMATE_USERNAME = "";
    public static final String AUTOMATE_KEY = "";
    public static final String URL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_KEY + "@hub.lambdatest.com/wd/hub";

    public static void main(String[] args) throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("build", "your build name");
        caps.setCapability("name", "your test name");
        caps.setCapability("platformName", "Android");
        caps.setCapability("deviceName", "Galaxy S20 Plus");
        caps.setCapability("browserName", "Chrome");
        caps.setCapability("platformVersion","10");
        //caps.setCapability("w3c",true);
        AppiumDriver driver = new AppiumDriver(new URL(URL), caps);

        //WebCam Test
        driver.get("https://webcamtests.com/check");
        Thread.sleep(5000);
        driver.findElement(By.id("webcam-launcher")).click();
        Thread.sleep(5000);
        // To accept/block the popup, you need to switch the context to “NATIVE_APP“ and click on the Allow/Block button.
        driver.context("NATIVE_APP");
        driver.findElement(By.xpath(".//android.widget.Button[@text='Allow']")).click();
        driver.findElement(By.xpath(".//android.widget.Button[@text='Allow']")).click();
        Thread.sleep(5000);
        driver.context("CHROMIUM");
        driver.quit();
    }
}

