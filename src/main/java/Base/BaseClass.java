package Base;

import Utilities.Global_Vars;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseClass {

    protected WebDriver driver;

    public BaseClass() {
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", Global_Vars.ROOT_PATH + "/src/main/java/drivers/chromedriver.exe");
        driver = new ChromeDriver();
    }

    public void setUp() {
        // Maximize the browser window
        driver.manage().window().maximize();
    }

    public void tearDown() {
        // Close the browser window
        driver.quit();
    }
}
