import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import okhttp3.*;
import org.json.JSONObject;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.io.*;
import java.net.URL;
import java.util.Base64;

public class NativeAppFromLocal {
    public static final String userName = System.getenv("LT_USERNAME");
    public static final String accessKey = System.getenv("LT_ACCESS_KEY");
    public static final String URL = "https://" + userName + ":" + accessKey + "@mobile-hub.lambdatest.com/wd/hub";
    public static  FileOutputStream fos = null;
    public static  File file=null;

    public static void main(String[] args) throws Exception {


        String encoding = Base64.getEncoder().encodeToString((userName + ":" + accessKey).getBytes("UTF-8"));
        System.out.println("encoding:------"+encoding);


        OkHttpClient client = new OkHttpClient().newBuilder() .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("name","\"WikipediaSample\"").addFormDataPart("appFile","/D:/jar/WikipediaSample.apk",
                RequestBody.create(MediaType.parse("application/octet-stream"),
                        new File("/D:/jar/WikipediaSample.apk"))) .build();
        Request request = new Request.Builder() .url("https://manual-api.lambdatest.com/app/upload/realDevice").method("POST", body).addHeader("Authorization", "Basic "+encoding).build();
        Response response = client.newCall(request).execute();
        String Responsebody= response.body().string();
        System.out.println("response===="+response);
        System.out.print("ResponseBody:------"+Responsebody);
        JSONObject obj = new JSONObject(Responsebody);
        System.out.println("App URL:"+obj.get("app_url"));
        String app_url=obj.get("app_url").toString();
        System.out.println("App Uploaded");

        //--------------------------Declared capabilities--------------------------

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformVersion", "10");
        caps.setCapability("deviceName", "Galaxy Tab S4");
        caps.setCapability("isRealMobile", "true");
        caps.setCapability("app", app_url);
        caps.setCapability("platformName", "android");
        caps.setCapability("build", "Java Automation");
        caps.setCapability("name", "app testing");
        caps.setCapability("devicelog",true);
        caps.setCapability("network",false);

        //-----------------------------Test----------------------------------------
        AndroidDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(new URL("https://"+userName+":"+accessKey+"@mobile-hub.lambdatest.com/wd/hub"), caps);

        driver.findElement(MobileBy.AccessibilityId("Search Wikipedia")).click();
        driver.findElement(MobileBy.id("org.wikipedia.alpha:id/search_src_text")).sendKeys("Cristiano Ronaldo");
        driver.pressKey(new KeyEvent(AndroidKey.ENTER));

        System.out.println("Test Passed");
        driver.quit();
        //--------------------------Deletion of apk file from the source location-----------------------------------
        file.delete();
    }
}