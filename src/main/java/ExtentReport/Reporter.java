package ExtentReport;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.google.common.io.Files;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class Reporter {

    public static ExtentReports report;
    public static ExtentTest test;

    public static void startTest(String testName, String testDescription) {
        report = new ExtentReports("src/test/java/reports/TestReport.html", false);

        report.addSystemInfo("WebDriver", "Chrome v112")
                .addSystemInfo("Selenium", "v3.141.59")
                .addSystemInfo("OS", "Windows 11 pro")
                .addSystemInfo("Environment", "Test");

        test = report.startTest(testName, testDescription).assignAuthor("Suprise Ngoveni");
        test.setStartedTime(new java.util.Date());

    }

    public static String capture(WebDriver driver) {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File Dest = new File("src\\test\\java\\reports\\images" + System.currentTimeMillis() + ".png");
        String errflpath = Dest.getAbsolutePath();
        try {
            Files.copy(scrFile, Dest);
        } catch (IOException e) {
            System.out.println("Screenshot was not captured");
        }
        return errflpath;
    }
    public static void passed(String caseName){
        Reporter.test.log(LogStatus.PASS, caseName + " - Test passed");
    }

    public static void failed(String caseName, WebDriver driver){
        String output = caseName + " - Test failed"+ Reporter.test.addScreenCapture(Reporter.capture(driver));
        Reporter.test.log(LogStatus.FAIL, output);
    }
    public static void failed(String caseName){
        WebDriver driver = CurrentDriver.getDriver();
        String output = caseName + " - Test failed\n"+ Reporter.test.addScreenCapture(Reporter.capture(driver));
        Reporter.test.log(LogStatus.FAIL, output);
    }

    public static void endTest() {
        test.setEndedTime(new java.util.Date());
        report.endTest(test);
        report.flush();
    }
}
