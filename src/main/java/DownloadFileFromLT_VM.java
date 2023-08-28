
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

public class DownloadFileFromLT_VM {
    public static RemoteWebDriver driver;
    public static String uname = System.getenv("LT_USERNAME");
    public static String akey = System.getenv("LT_ACCESS_KEY");
    public static final String URL = "http://" + uname + ":" + akey + "@hub.lambdatest.com/wd/hub";
    public static void main(String[] args) throws Exception {

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("build", "Download File from LambdaTest VMs");
        caps.setCapability("name", "Test 1");
        caps.setCapability("platform", "MacOS Monterey");
        caps.setCapability("browserName", "Chrome");
        caps.setCapability("version", "80.0");
        caps.setCapability("resolution", "1920x1080");
        caps.setCapability("idleTimeout","600");
        caps.setCapability("acceptSslCerts", true);
        caps.setCapability("acceptInsecureCerts", true);
        driver = new RemoteWebDriver(new URL(URL), caps);

        driver.manage().window().maximize();
        Thread.sleep(4000);

        driver.get("https://the-internet.herokuapp.com/download");
        Thread.sleep(4000);
        driver.findElementByXPath("/html/body/div[2]/div/div/a[22]").click();

        Thread.sleep(4000);
        driver.get("chrome://downloads/");
        Thread.sleep(5000);
        System.out.println("Before Lambda file content done");
        String base64EncodedFile = ((JavascriptExecutor) driver).executeScript("lambda-file-content=001.pdf").toString(); // file content download
        System.out.println("Lambda file content done");
        byte[] byteArray = Base64.decodeBase64(base64EncodedFile.getBytes());
        FileOutputStream fos;
        File file=new File("D:\\Code Stuff\\Test1\\trepp\\002.pdf");
        fos = new FileOutputStream(file);
        System.out.println("Before fos.write");
        fos.write(byteArray);
        System.out.println("After fos.write");
        fos.close();
        System.out.println("FOS"+fos);
        Thread.sleep(2000);
        driver.quit();
    }
}