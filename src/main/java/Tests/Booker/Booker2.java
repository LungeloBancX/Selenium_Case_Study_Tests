package Tests.Booker;

import CommonMethods.Steps;
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

//import static CommonMethods.Steps.rightClickAndDragWithRobot;

public class Booker2 {

    WebDriver driver;
    protected ExtentReports extentReports;
    protected ExtentTest extentTest;
    Steps step;

    @BeforeTest
    public void setUpExtentReports() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(Global_Vars.ROOT_PATH + "/Reports");
        extentReports = new ExtentReports();
        extentReports.attachReporter(htmlReporter);
        extentTest = extentReports.createTest("Booker");
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
    public void yourTest(String FirstName, String subjectBooker, String EmailAddress ,String PhoneNumber, String commentBooker, String expectedOutput) throws InterruptedException {

        WebElement enterName=driver.findElement(By.xpath("//div//input[@id='name']"));
        step.scrollElementIntoView(driver,enterName);
        step.enterText(enterName, FirstName);

        WebElement enterEmailAddress=driver.findElement(By.xpath("//div//input[@placeHolder='Email']"));
        step.enterText(enterEmailAddress, EmailAddress);

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
        WebElement output=driver.findElement(By.xpath("(//div//h2)[2]"));
        String actualOutput=output.getText();
        Assert.assertEquals(expectedOutput, actualOutput);
//        actualOutputcaptureScreenshot("Validate Booking");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @DataProvider(name = "testData")
    public Object[][] testData() {
        String filePath = Global_Vars.ROOT_PATH + "/TestData/TestData.xlsx";
        String sheetName = "Comment";
        return ExcelDataReader.readExcelData(filePath, sheetName);
    }

    public void captureScreenshot(String screenshotName) {
        try {
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File screenshotFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
            String destinationPath = "screenshots/" + screenshotName + ".png";
            File destination = new File(destinationPath);
            org.apache.commons.io.FileUtils.copyFile(screenshotFile, destination);
            extentTest.addScreenCaptureFromPath(destinationPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
