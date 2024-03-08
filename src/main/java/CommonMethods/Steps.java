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

//    public void captureScreenshot(WebDriver driver,String screenshotName) {
//        try {
//            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
//            File screenshotFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
//            String destinationPath = "screenshots/" + screenshotName + ".png";
//            File destination = new File(destinationPath);
//            org.apache.commons.io.FileUtils.copyFile(screenshotFile, destination);
//            extentTest.addScreenCaptureFromPath(destinationPath);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    // Method to perform a right-click on the source element and drag to the target element using AWT Robot
//    public static void rightClickAndDragWithRobot(WebDriver driver, WebElement sourceElement, WebElement targetElement) {
//        try {
//            // Create a new instance of Robot
//            Robot robot = new Robot();
//
//            // Get the coordinates of the source element
//            int sourceX = sourceElement.getLocation().getX();
//            int sourceY = sourceElement.getLocation().getY();
//
//            // Get the coordinates of the target element
//            int targetX = targetElement.getLocation().getX();
//            int targetY = targetElement.getLocation().getY();
//
//            // Move the mouse to the source element and perform a right-click
//            robot.mouseMove(sourceX, sourceY);
//            robot.mousePress(InputEvent.BUTTON3_MASK);
//            robot.mouseRelease(InputEvent.BUTTON3_MASK);
//
//            // Delay for a short period to allow the context menu to appear
//            robot.delay(500);
//
//            // Move the mouse to the target element and perform a drag
//            robot.mouseMove(targetX, targetY);
//            robot.mousePress(InputEvent.BUTTON1_MASK); // Press left mouse button
//            robot.delay(500); // Adjust the delay as needed
//            robot.mouseMove(targetX + 5, targetY + 5); // Move slightly to simulate dragging
//            robot.mouseRelease(InputEvent.BUTTON1_MASK); // Release left mouse button
//
//        } catch (AWTException e) {
//            e.printStackTrace();
//        }
//    }
}
