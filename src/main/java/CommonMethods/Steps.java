package CommonMethods;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

public class Steps {

    protected ExtentReports extentReports;
    protected ExtentTest extentTest;
    public static void scrollElementIntoView(WebDriver driver, WebElement element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        // Scroll to the element using JavaScript
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void enterText(WebElement element, String text) {
        WebElement elementXpath = element;
        elementXpath.sendKeys(text);
    }

    public static void clickElement(WebElement element) {
        element.click();
    }

    public static void rightClickAndDrag(WebDriver driver, WebElement sourceElement, WebElement targetElement) throws InterruptedException {
        Actions actions = new Actions(driver);
        // Right-click on the source element
        actions.contextClick(sourceElement).build().perform();
        Thread.sleep(2000);
        // Move to the target element while holding down the right mouse button
        actions.moveToElement(targetElement).contextClick().build().perform();
        // Release the right mouse button
        actions.release().perform();
    }
}
