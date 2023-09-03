import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.MalformedURLException;
import java.net.URL;

public class UploadChromeProfile
{
    public static RemoteWebDriver driver;
    public static String uname = System.getenv("LT_USERNAME");
    public static String akey = System.getenv("LT_ACCESS_KEY");

    public static void main(String[] args) throws InterruptedException, MalformedURLException {

        //****************** Upload Chrome profile in local Chrome driver **********************//

//        System.setProperty("webdriver.chrome.driver","D:\\Code Stuff\\chromedriver.exe");
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--user-data-dir=C:\\Users\\abidk\\AppData\\Local\\Google\\Chrome\\User Data");
//        options.addArguments("--profile-directory=Profile 5");
//        options.addArguments("--start-maximized");
//        ChromeDriver driver = new ChromeDriver(options);


        //********************* Upload Chrome profile in LambdaTest Remote driver ******************//

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("build", "Uploading Chrome Profile on LambdaTest");
        capabilities.setCapability("name", "Chrome Profile Test 1");
        capabilities.setCapability("platform", "Windows 10");
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("version","103.0");
        capabilities.setCapability("browserProfile","https://prod-magicleap-user-files-us-east-1-v1.s3.amazonaws.com/profile/chrome/orgId-32518/Profile 5.zip");
        capabilities.setCapability("network", true);

        driver = new RemoteWebDriver(new URL("https://"+ uname+":"+akey+""+"@hub.lambdatest.com/wd/hub"),capabilities);

        driver.manage().window().maximize();

        driver.get("chrome://version/");
        Thread.sleep(60000);

        driver.quit();
    }
}
