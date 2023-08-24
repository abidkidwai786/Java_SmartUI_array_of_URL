import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import okhttp3.*;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
public class fetch_build_api {
    private RemoteWebDriver driver;
    private String Status = "failed";
    private String sessionId;
    @BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {
        String username = "";
        String authkey = "";
        ;
        String hub = "@hub.lambdatest.com/wd/hub";
        DesiredCapabilities caps = new DesiredCapabilities();
        //caps.setCapability("platformName", "android");
        caps.setCapability("build", "Fetch Build Details using Session API");
        caps.setCapability("name", "Test 1");
        caps.setCapability("plugin", "git-testng");
        caps.setCapability("platform", "Windows 10");
        caps.setCapability("browserName", "Chrome");
        caps.setCapability("version","103.0");
        String[] Tags = new String[] { "Feature", "Tag", "Moderate" };
        caps.setCapability("tags", Tags);
        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), caps);
    }
    @Test
    public void basicTest() throws InterruptedException {
        sessionId = driver.getSessionId().toString(); // FETCHING SESSION ID
        System.out.println("Loading Url");
        Thread.sleep(100);
        driver.get("https://lambdatest.github.io/sample-todo-app/");
        System.out.println("Checking Box");
        driver.findElement(By.name("li1")).click();
        System.out.println("Checking Another Box");
        driver.findElement(By.name("li2")).click();
        System.out.println("Checking Box");
        driver.findElement(By.name("li3")).click();
        System.out.println("Checking Another Box");

        Status = "passed";
        Thread.sleep(800);
        System.out.println("TestFinished");

    }
    @AfterMethod
    public void tearDown() throws IOException {

        driver.executeScript("lambda-status=" + Status); //marking test status using LambdaHooks
        driver.quit();  //quitting the driver


        //******Calling sessionAPI using session ID to fetch the build ID*****//
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        String apiUrl = "https://api.lambdatest.com/automation/api/v1/sessions/"+sessionId;
        String authorization = "Basic <BASE_AUTH>"; // add your base 64 token here

        // Create an HttpClient
        Request request = new Request.Builder()
                .url(apiUrl)
                .header("accept", "application/json")
                .header("Authorization", authorization)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String ResponseBody = response.body().string();
        //System.out.println("Response Body:\n" + ResponseBody); // printing session API response
        JSONObject obj = new JSONObject(ResponseBody);
        int buildId = obj.getJSONObject("data").getInt("build_id");
        System.out.println(buildId); // printing build ID fetched from Session ID API response



        //******Calling Build API using build ID to fetch the build status*****//
        String apiUrl1 = "https://api.lambdatest.com/automation/api/v1/builds/"+buildId;
        String authorization1 = "Basic YWJpZEtpZHdha2MyYQ==";

        Request request1 = new Request.Builder()
                .url(apiUrl1)
                .header("accept", "application/json")
                .header("Authorization", authorization1)
                .get()
                .build();

        Response response1 = client.newCall(request1).execute();
        String ResponseBody1 = response1.body().string();
        //System.out.println("Response Body:\n" + ResponseBody1); // printing build API response

        JSONObject obj1 = new JSONObject(ResponseBody1);
        String buildstatus = obj1.getJSONObject("data").getString("status_ind");

        System.out.println("Build status is "+ buildstatus); //printing build status

    }
}