package Tests.Booker;

import CommonMethods.Steps;
import Utilities.ExcelDataReader;
import Utilities.Global_Vars;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.swing.*;
//import static CommonMethods.Steps.rightClickAndDrag;
//import static CommonMethods.Steps.rightClickAndDragWithRobot;

public class Booker{

    WebDriver driver;
    Action action;
    Steps step;
    @BeforeClass
    public void startTest(){
        System.setProperty("webdriver.chrome.driver", Global_Vars.ROOT_PATH + "/src/main/java/drivers/chromedriver.exe");
        driver=new ChromeDriver();
        driver.get("https://automationintesting.online/");
    }

    @Test(dataProvider = "testData")
    public void yourTest(String FirstName, String LastName, String EmailAddress ,String PhoneNumber) throws InterruptedException {
        System.out.println("Firstname:\t"+FirstName+"\nLastname:\t"+ LastName+"\nEmail Address:\t"+EmailAddress+"\nPhone Number:\t"+PhoneNumber);

        WebElement scrollToBookThisRoom=driver.findElement(By.xpath("//div//button[contains(text(),'Book this room')]"));
        step.scrollElementIntoView(driver, scrollToBookThisRoom);
        scrollToBookThisRoom.click();

        String startDate = "1";
        String middleDate="2";
        String endDate = "3";

//       Select Dates
         WebElement sourceElement   =driver.findElement(By.xpath("(//div[@class='rbc-month-row']//div[@class='rbc-day-bg'])["+startDate+"]"));
         WebElement releaseElement  =driver.findElement(By.xpath("(//div[@class='rbc-month-row']//div[@class='rbc-day-bg'])["+endDate+"]"));
         WebElement statDateElement= driver.findElement(By.xpath("(//div[@class='rbc-month-row']//div[@class='rbc-day-bg'])[\"+endDate+\"]"));

//        rightClickAndDrag(driver, sourceElement, releaseElement);
//        rightClickAndDragWithRobot(driver, sourceElement, releaseElement);

        WebElement enterFistName=driver.findElement(By.xpath("//div//input[@name='firstname']"));
        step.scrollElementIntoView(driver,enterFistName);
        step.enterText(enterFistName, FirstName);

        WebElement enterLastName=driver.findElement(By.xpath("//div//input[@placeHolder='Lastname']"));
        step.enterText(enterLastName, LastName);

        WebElement enterEmailAddress=driver.findElement(By.xpath("//div//input[@placeHolder='Email']"));
        step.enterText(enterEmailAddress, EmailAddress);

        WebElement enterPhoneNumber=driver.findElement(By.xpath("//div//input[@placeHolder='Phone']"));
        step.enterText(enterPhoneNumber, PhoneNumber);

        Thread.sleep(2000);
        WebElement clickBook=driver.findElement(By.xpath("//div//button[contains(text(),'Book')]"));
        step.clickElement(clickBook);

    }
    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @DataProvider(name = "testData")
    public Object[][] testData() {
        String filePath = Global_Vars.ROOT_PATH + "/TestData/TestData.xlsx";
        String sheetName = "Book";
        return ExcelDataReader.readExcelData(filePath, sheetName);
    }
}
