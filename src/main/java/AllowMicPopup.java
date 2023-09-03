import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.AppiumDriver;
import java.net.URL;

public class AllowMicPopup {
    public static final String AUTOMATE_USERNAME = System.getenv("LT_USERNAME");
    public static final String AUTOMATE_KEY = System.getenv("LT_ACCESS_KEY");
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

        //Mic Test
        driver.get("https://www.vidyard.com/mic-test/");
        Thread.sleep(5000);
        driver.findElement(By.xpath("/html/body/section[1]/div/div/div[1]/button")).click();
        Thread.sleep(5000);
        driver.context("NATIVE_APP");
        driver.findElement(By.xpath(".//android.widget.Button[@text='Allow']")).click();
        driver.findElement(By.xpath(".//android.widget.Button[@text='Allow']")).click();
        Thread.sleep(5000);
        driver.context("CHROMIUM");
        //******************** Next test steps *******************

        driver.quit();
    }
}

