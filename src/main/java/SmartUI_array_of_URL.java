import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.MalformedURLException;
import java.net.URL;

public class SmartUI_array_of_URL {
    public static void main(String[] args) throws MalformedURLException, InterruptedException {

        RemoteWebDriver driver = null;
        String username = System.getenv("LT_USERNAME");
        String access_key = System.getenv("LT_ACCESS_KEY");
        int popup=0;
        int no_popup=0;

        String[] url = {
                "https://edit.medicaid.gov/?ACA=G2dnlrKwoL",
                "https://medicaid.gov/federal-policy-guidance/index.html",
                "https://medicaid.gov/resources-for-states/index.html",
                "https://medicaid.gov/resources-for-states/medicaid-innovation-accelerator-program/index.html",
                "https://medicaid.gov/resources-for-states/medicaid-state-technical-assistance/index.html",
                "https://medicaid.gov/resources-for-states/medicaid-and-chip-program-macpro-portal/index.html",
                "https://medicaid.gov/resources-for-states/medicaid-and-chip-mac-learning-collaboratives/index.html",
                "https://medicaid.gov/resources-for-states/medicaid-and-chip-program-portal/medicaid-data-collection-tool-mdct-portal/index.html",
                "https://medicaid.gov/resources-for-states/disaster-response-toolkit/index.html",
                "https://medicaid.gov/resources-for-states/coronavirus-disease-2019-covid-19/index.html",
                "https://medicaid.gov/resources-for-states/spa-and-1915-waiver-processing/index.html",
                "https://medicaid.gov/resources-for-states/medicaid-and-chip-eligibility-enrollment-webinars/index.html",
                "https://medicaid.gov/medicaid/medicaid-state-plan-amendments/index.html",
                "https://medicaid.gov/medicaid/access-care/index.html",
                "https://medicaid.gov/medicaid/program-integrity/index.html",
                "https://medicaid.gov/medicaid/cost-sharing/index.html",
                "https://medicaid.gov/medicaid/indian-health-medicaid/index.html",
                "https://medicaid.gov/medicaid/outreach-tools/index.html",
                "https://medicaid.gov/medicaid/quality-of-care/index.html",
                "https://medicaid.gov/medicaid/quality-of-care/quality-resource-library",
                "https://medicaid.gov/medicaid/enrollment-strategies/index.html",
                "https://medicaid.gov/medicaid/home-community-based-services/index.html",
                "https://medicaid.gov/medicaid/national-medicaid-chip-program-information/index.html",
                "https://medicaid.gov/medicaid/section-1115-demonstrations/index.html",
                "https://medicaid.gov/medicaid/section-1115-demo/demonstration-and-waiver-list/index.html",
                "https://medicaid.gov/medicaid/managed-care/index.html",
                "https://medicaid.gov/medicaid/eligibility/index.html",
                "https://medicaid.gov/medicaid/financial-management/index.html",
                "https://medicaid.gov/medicaid/data-systems/index.html",
                "https://medicaid.gov/medicaid/benefits/index.html",
                "https://medicaid.gov/medicaid/prescription-drugs/index.html",
                "https://medicaid.gov/medicaid/long-term-services-supports/index.html",
                "https://medicaid.gov/chip/index.html",
                "https://medicaid.gov/chip/benefits/index.html",
                "https://medicaid.gov/chip/eligibility/index.html",
                "https://medicaid.gov/chip/chip-cost-sharing/index.html",
                "https://medicaid.gov/chip/financing/index.html",
                "https://medicaid.gov/chip/chipra/index.html",
                "https://medicaid.gov/chip/state-program-information/index.html",
                "https://medicaid.gov/chip/reports-evaluations/index.html",
                "https://medicaid.gov/chip/chip-managed-care/index.html",
                "https://medicaid.gov/chip/chip-quality-performance/index.html",
                "https://medicaid.gov/basic-health-program/index.html",
                "https://medicaid.gov/state-overviews/index.html",
                "https://medicaid.gov/state-overviews/scorecard/index.html",
                "https://medicaid.gov/sitemap/index.html",
                "https://medicaid.gov/faq/index.html"
        };



        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("build", "SmartUI testing");
        capabilities.setCapability("name", "Visual Ui Testing");
        capabilities.setCapability("platform", "Windows 10");
        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("version","latest");
        capabilities.setCapability("visual",true);
        capabilities.setCapability("smartUI.project","SmartUI-5-9");

        //  capabilities.setCapability("smartUI.build","Build 1"); // smart build name e.g. "Baseline" , "Comparison" etc


        driver = new RemoteWebDriver(new URL("http://" + username + ":" + access_key + "@hub.lambdatest.com/wd/hub"), capabilities);

        for (int i=0; i< url.length; i++) {

            driver.get(url[i]); // URL to be tested

            Thread.sleep(3000);

            //************* Below try/catch block is used to handle website pop up **************//

            try
            {

                driver.findElementByXPath("/html/body/div[3]/div/div/section[3]/button[2]").click(); // You can use ID, XPath or any other selector to click on the element.
                System.out.println("Pop found for URL "+url[i]);
                Thread.sleep(2000);
                driver.executeScript("smartui.takeScreenshot=" + i);
                popup++;
            }
            catch (Exception e)
            {
                System.out.println("Pop not found for URL "+url[i]);
                driver.executeScript("smartui.takeScreenshot=" + i);
                no_popup++;
            }

        }

        System.out.println("Total sites with pop up="+popup);
        System.out.println("Total site without pop up="+no_popup);


        driver.executeScript("lambda-status=passed");
        driver.quit();

    }
}