package TestSuites.Takealot;

import CommonMethods.Steps;
import Utilities.ExcelDataReader;
import Utilities.Global_Vars;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

public class Register {

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
    public void CreateAccount(String TestCaseID, String Scenario,String FirstName, String LastName, String EmailAddress ,String PhoneNumber, String Password, String expectedOutput) throws InterruptedException {

        System.out.println("Test Case ID:\t"+TestCaseID+"\nFirstname:\t"+FirstName+"\nLastname:\t"+LastName+"\nScenario:\t"+Scenario+"\nExpected Output:\t"+expectedOutput);
       WebElement clickRegister=driver.findElement(By.xpath("//li//a[contains(text(),'Register')]"));
       Steps.clickElement(clickRegister);

       Thread.sleep(2000);
       WebElement enterFirstName=driver.findElement(By.xpath("//span[contains(text(),'First Name')]/../..//input"));
       Steps.scrollElementIntoView(driver, enterFirstName);
       Steps.enterText(enterFirstName, FirstName);

        WebElement enterLastName=driver.findElement(By.xpath("//span[contains(text(),'Last Name')]/../..//input"));
        Steps.enterText(enterLastName, LastName);

        WebElement enterEmailAddress=driver.findElement(By.xpath("//span[contains(text(),'Email Address')]/../..//input"));
        Steps.enterText(enterEmailAddress, EmailAddress);

        WebElement enterPassword=driver.findElement(By.xpath("//span[contains(text(),'Password')]/../..//input"));
        Steps.enterText(enterPassword, Password);

        WebElement enterCellphoneNumber=driver.findElement(By.xpath("//span[contains(text(),'Mobile Number')]/../..//input"));
        Steps.enterText(enterCellphoneNumber, PhoneNumber);
        Thread.sleep(2000);
        WebElement clickContinue=driver.findElement(By.xpath("//div//button[contains(text(),'Continue')]"));
        Steps.scrollElementIntoView(driver, clickContinue);
        Steps.clickElement(clickContinue);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @DataProvider(name = "testData")
    public Object[][] testData() {
        String filePath = Global_Vars.ROOT_PATH + "/TestData/TestData.xlsx";
        String sheetName = "register";
        return ExcelDataReader.readExcelData(filePath, sheetName);
    }
}
