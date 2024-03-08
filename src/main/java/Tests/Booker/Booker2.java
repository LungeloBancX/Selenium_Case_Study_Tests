package Tests.Booker;

import CommonMethods.Steps;
import ExtentReport.ExtentManager;
import Utilities.ExcelDataReader;
import Utilities.Global_Vars;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

//import static CommonMethods.Steps.rightClickAndDragWithRobot;

public class Booker2 {

    WebDriver driver;
    private static ExtentReports extent;
    protected ExtentTest test;
    Steps step;

    @BeforeTest
    public void setUpExtentReports() {
//        start reporters
        extent = ExtentManager.createInstance("extent.html");
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extent_report");
//        create ExtentReports and attach report
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeClass
    public void startTest() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", Global_Vars.ROOT_PATH + "/src/main/java/drivers/chromedriver.exe");
        driver=new ChromeDriver();
        driver.get("https://automationintesting.online/");
        Thread.sleep(2000);
        // Maximize the browser window to make it full screen
        driver.manage().window().maximize();
    }

    @Test(dataProvider = "testData")
    public void yourTest(String FirstName, String subjectBooker, String EmailAddress ,String PhoneNumber, String commentBooker, String expectedOutput) throws InterruptedException, IOException {
//        creates a toggle for the given test, adds all log events under it
        test = extent.createTest("MyFirstTest", "Sample description");

        WebElement enterName=driver.findElement(By.xpath("//div//input[@id='name']"));
        step.scrollElementIntoView(driver,enterName);
        step.enterText(enterName, FirstName);
        test.log(Status.INFO, "Adding First Name");
        WebElement enterEmailAddress=driver.findElement(By.xpath("//div//input[@placeHolder='Email']"));
        step.enterText(enterEmailAddress, EmailAddress);
        test.log(Status.INFO, "Adding Email Address");
        WebElement enterSubject=driver.findElement(By.xpath("//div//input[@placeHolder='Subject']"));
        step.enterText(enterSubject, subjectBooker);

        WebElement enterPhoneNumber=driver.findElement(By.xpath("//div//input[@placeHolder='Phone']"));
        step.enterText(enterPhoneNumber, PhoneNumber);

        WebElement enterComment=driver.findElement(By.xpath("//div//textarea[@id='description']"));
        step.enterText(enterComment, commentBooker);

        Thread.sleep(2000);
        WebElement clickSubmitButton=driver.findElement(By.xpath("//button[contains(text(),'Submit')]"));
        step.clickElement(clickSubmitButton);
        Thread.sleep(6000);
        test.log(Status.INFO, "Validating whether booking happened");
        WebElement output=driver.findElement(By.xpath("(//div//h2)[2]"));
        String actualOutput=output.getText();

        Assert.assertEquals(expectedOutput, actualOutput);
        test.addScreenCaptureFromPath("screenshot.png");
//        actualOutputcaptureScreenshot("Validate Booking");
    }

    @AfterClass
    public void tearDown() {

        // calling flush writes everything to the log file
        extent.flush();
        driver.quit();
    }

    @DataProvider(name = "testData")
    public Object[][] testData() {
        String filePath = Global_Vars.ROOT_PATH + "/TestData/TestData.xlsx";
        String sheetName = "Comment";
        return ExcelDataReader.readExcelData(filePath, sheetName);
    }
}
