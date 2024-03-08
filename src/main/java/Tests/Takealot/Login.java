package Tests.Takealot;

import CommonMethods.Steps;
import Utilities.ExcelDataReader;
import Utilities.Global_Vars;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class Login {

    WebDriver driver;
    protected ExtentReports extentReports;
    protected ExtentTest extentTest;
    Steps step;

    @BeforeTest
    public void setUpExtentReports() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(Global_Vars.ROOT_PATH + "/Reports");
        extentReports = new ExtentReports();
        extentReports.attachReporter(htmlReporter);
        extentTest = extentReports.createTest("Takelot");
    }

    @BeforeClass
    public void startTest() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", Global_Vars.ROOT_PATH + "/src/main/java/drivers/chromedriver.exe");
        driver=new ChromeDriver();
        driver.get("https://www.takealot.com/");
        Thread.sleep(2000);
        // Maximize the browser window to make it full screen
        driver.manage().window().maximize();
        Thread.sleep(2000);
    }

    @Test(dataProvider = "testData")
    public void LoginAccount(String TestCaseID, String Scenario,String EmailAddress, String Password, String expectedOutput) throws InterruptedException {
        System.out.println("Test Case ID:\t"+TestCaseID+"\nScenario:\t"+Scenario);

        WebElement clickLogin=driver.findElement(By.xpath("//li//a[contains(text(),'Login')]"));
       Steps.clickElement(clickLogin);

       Thread.sleep(2000);
        WebElement enterEmailAddress=driver.findElement(By.xpath("//span[contains(text(),'Email Address')]/../..//input"));
        Steps.enterText(enterEmailAddress, EmailAddress);

        WebElement enterPassword=driver.findElement(By.xpath("//span[contains(text(),'Password')]/../..//input"));
        Steps.enterText(enterPassword, Password);

        Thread.sleep(2000);
        WebElement clickLoginButton=driver.findElement(By.xpath("//div//button[contains(text(),'Login')]"));
        Steps.scrollElementIntoView(driver, clickLoginButton);
        Steps.clickElement(clickLoginButton);

        Thread.sleep(2000);
        WebElement validateLogin=driver.findElement(By.xpath("(//ul//li)[3]"));
        String actualOutput=validateLogin.getText();
        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @DataProvider(name = "testData")
    public Object[][] testData() {
        String filePath = Global_Vars.ROOT_PATH + "/TestData/TestData.xlsx";
        String sheetName = "login";
        return ExcelDataReader.readExcelData(filePath, sheetName);
    }
}
