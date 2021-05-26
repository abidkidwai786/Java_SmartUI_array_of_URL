
import com.lambdatest.tunnel.Tunnel;
import cucumber.api.java.eo.Se;
import io.appium.java_client.AppiumDriver;
import org.apache.commons.lang3.time.StopWatch;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class demoDesktop {

    public String username = System.getenv("LT_USERNAME");
    public String accesskey = System.getenv("LT_ACCESS_KEY");
    public RemoteWebDriver driver;
    public String gridURL = "hub.lambdatest.com";
    String status;
    String hub;
    SessionId sessionId;


    @org.testng.annotations.Parameters(value = {"browser", "version", "platform"})

    @BeforeTest
    public void setUp(String browser, String version, String platform) throws Exception {
        try {

            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("build", "DesktopDemo");
            caps.setCapability("name", "ToDO");
            caps.setCapability("platform", platform);
            caps.setCapability("browserName", browser);
            caps.setCapability("version", version);
            caps.setCapability("network", true);
            caps.setCapability("visual", true);
            caps.setCapability("console", true);
            caps.setCapability("headless", true);

            StopWatch driverStart = new StopWatch();
            driverStart.start();

            hub = "https://" + username + ":" + accesskey + "@" + gridURL + "/wd/hub";
            driver = new RemoteWebDriver(new URL(hub), caps);

            sessionId = driver.getSessionId();
            System.out.println(sessionId);
            driverStart.stop();
            float timeElapsed = driverStart.getTime() / 1000f;
            System.out.println("Driver initiate time" + "   " + timeElapsed);
            ArrayList<Float> TotalTimeDriverSetup = new ArrayList<Float>();
            TotalTimeDriverSetup.add(timeElapsed);
            System.out.println(TotalTimeDriverSetup);


        } catch (
                MalformedURLException e) {
            System.out.println("Invalid grid URL");
        } catch (Exception f) {
            System.out.println(f);

        }

    }

    @Test
    public void DesktopScript() {
        try {
            driver.get("https://lambdatest.github.io/sample-todo-app/");
            WebDriverWait wait = new WebDriverWait(driver, 30);
            WebElement firstItem;
            firstItem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div > div > div > ul > li:nth-child(1) > input")));
            WebElement secondItem = driver.findElement(By.cssSelector("body > div > div > div > ul > li:nth-child(2) > input"));
            WebElement thirdItem = driver.findElement(By.cssSelector("body > div > div > div > ul > li:nth-child(4) > input"));
            WebElement fifthElement = driver.findElement(By.cssSelector("body > div > div > div > ul > li:nth-child(5) > input"));
            firstItem.click();
            secondItem.click();
            thirdItem.click();
            fifthElement.click();
            driver.findElement(By.xpath("//*[@id=\"sampletodotext\"]")).sendKeys("new item added");
            driver.findElement(By.xpath("//*[@id=\"addbutton\"]")).isDisplayed();
            driver.findElement(By.xpath("//*[@id=\"addbutton\"]")).click();
            status="passed";
        } catch (Exception e) {

            System.out.println(e);
            status = "failed";
        }
    }


    @AfterTest
    public void tearDown() throws Exception {
        if (driver != null) {
            ((JavascriptExecutor) driver).executeScript("lambda-status=" + status);
            driver.quit();
        }
    }
}

