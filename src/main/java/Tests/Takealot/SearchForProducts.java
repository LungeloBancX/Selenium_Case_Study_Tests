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

public class SearchForProducts {

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
    public void searchItem(String TestCaseID, String Scenario,String EmailAddress, String Password, String expectedOutput, String Search, String ItemName,String ItemColor, String ItemSize) throws InterruptedException {
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
        WebElement searchForItem=driver.findElement(By.xpath("//div//input[@placeholder='Search for products, brands...']"));
        Steps.enterText(searchForItem,Search);

        Thread.sleep(2000);
        WebElement clickSearchButton=driver.findElement(By.xpath("//div//button[@title='search']"));
        step.clickElement(clickSearchButton);

        Thread.sleep(2000);
        WebElement validateSearch=driver.findElement(By.xpath("//div//div[contains(text(),'5000+ results for \""+Search+"')]"));
        String actualOutput=validateSearch.getText();
        Assert.assertEquals(expectedOutput, actualOutput);

        WebElement selectItem=driver.findElement(By.xpath("(//div//a[@title='"+ItemName+"'])[1]"));
        step.clickElement(selectItem);

        //Set Item Color
        Thread.sleep(2000);
        driver.findElement(By.xpath("//span[contains(text(),'Select a colour')]")).click();
        WebElement selectColor=driver.findElement(By.xpath("(//div[@class='image-box  ']/..//*[contains(text(),'"+ItemColor+"')])[1]"));
        step.clickElement(selectColor);

        //Set Item Size
        WebElement clickSize=driver.findElement(By.xpath("//span[contains(text(),'Select a size')]/../../..//div[@class='swiper-slide']//button[contains(text(),'"+ItemSize+"')]"));
        step.clickElement(clickSize);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @DataProvider(name = "testData")
    public Object[][] testData() {
        String filePath = Global_Vars.ROOT_PATH + "/TestData/TestData.xlsx";
        String sheetName = "SearchAndSelectItems";
        return ExcelDataReader.readExcelData(filePath, sheetName);
    }
}
