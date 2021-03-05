package webdriver;

import com.google.common.base.Function;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

public class Topic_16_Selenium_WebDriver_Wait {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;
    FluentWait<WebElement> fluentWait;


    @BeforeClass
    public void beforeClass() {
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver,10);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_Static_Wait() {
        driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");

        // Click Start
        clickElement(By.xpath("//button[text()='Start']"));

        sleepInSecond(7);

        // Verify text is displayed
        Assert.assertTrue(findElement(By.xpath("//h4[text()='Hello World!']")).isDisplayed());

    }

    @Test
    public void TC_02_Implicit_Wait() {
        driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");

        // Click Start
        clickElement(By.xpath("//button[text()='Start']"));

        // Verify text is displayed
        Assert.assertTrue(findElement(By.xpath("//h4[text()='Hello World!']")).isDisplayed());

    }

    @Test
    public void TC_03_Explicit_Wait() {
        driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");

        // Click Start
        clickElement(By.xpath("//button[text()='Start']"));

        // Wait for Loading bar invisible
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#loading")));

        // Verify text is displayed
        Assert.assertTrue(findElement(By.xpath("//h4[text()='Hello World!']")).isDisplayed());

    }

    @Test
    public void TC_04_Explicit_Wait() {
        driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");

        // Click Start
        clickElement(By.xpath("//button[text()='Start']"));

        // Wait for text visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[text()='Hello World!']")));

        // Verify text is displayed
        Assert.assertTrue(findElement(By.xpath("//h4[text()='Hello World!']")).isDisplayed());

    }

    @Test
    public void TC_05_Explicit_Wait() {
        By selectedDates = By.xpath("//legend[text()='Selected Dates:']/..//span");

        driver.get("https://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");

        // Wait for Calendar visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".contentWrapper")));

        // Print out selected date
        System.out.println(getText(selectedDates));

        // Select date
        clickElement(By.xpath("//a[text()='10']"));
        clickElement(By.cssSelector(".kd-input"));

        // Wait for Ajax loading invisible
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("[id*='RadCalendar'] .raDiv")));

        // Wait for date is selected
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@class='rcSelected']/a[text()='10']")));

        // Verify selected date
        Assert.assertEquals(getText(selectedDates),"Wednesday, March 10, 2021");
    }

    @Test
    public void TC_06_Explicit_Wait() {
        String uploadFilePath = System.getProperty("user.dir")+"\\testData\\upload_01.txt";
        driver.get("https://gofile.io/uploadFiles");
        String parentWindow = driver.getWindowHandle();

        // Upload file
        uploadByRobotClass(By.cssSelector("button#dropZoneBtnSelect"),uploadFilePath);

        // Click upload button
        clickElement(By.cssSelector("button#uploadFiles-btnUpload"));

        // Click Download link
        clickElement(By.cssSelector("a#uploadFiles-uploadRowResult-downloadLink"));

        // Close popup
        switchToWindowByID(parentWindow);
        closeRandomPopup(By.cssSelector("[role='dialog']"),By.xpath("//button[text()='I have a VPN already']"));

        // Check button is displayed
        Assert.assertTrue(findElement(By.xpath("//button[text()='Download']")).isDisplayed());
    }

    @Test
    public void TC_07_Fluent_Wait() {
        driver.get("https://automationfc.github.io/fluent-wait/");

        // Wait for countdown visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#javascript_countdown_time")));

        // Wait for countdown = 00 by fluent wait
        fluentWait = new FluentWait<WebElement>(findElement(By.cssSelector("#javascript_countdown_time")));
        fluentWait.withTimeout(15,TimeUnit.SECONDS)
                .pollingEvery(1,TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class)
                .until(new Function<WebElement,Boolean>() {
                    public Boolean apply(WebElement element) {
                        System.out.println(element.getText());
                        return element.getText().endsWith("00");
                    }
                });
    }

    @Test
    public void TC_08_Fluent_Wait() {
        driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");

        // Click Start
        clickElement(By.cssSelector("div#start button"));

        // Wait for Hello World display
        fluentWait = new FluentWait<WebElement>(findElement(By.xpath("//div[@id='finish']/h4[text()='Hello World!']")));
        fluentWait.withTimeout(15,TimeUnit.SECONDS)
                .pollingEvery(1,TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class)
                .until(new Function<WebElement,Boolean>() {
                    public Boolean apply(WebElement element) {
                        return element.isDisplayed();
                    }
                });
    }

    public WebElement findElement(By by){
        return driver.findElement(by);
    }

    public void inputText(By by,String text){
        findElement(by).clear();
        findElement(by).sendKeys(text);
    }

    public void clickElement(By by){
        findElement(by).click();
    }

    public String getText(By by){
        return findElement(by).getText();
    }

    public void sleepInSecond(int second){
        try {
            Thread.sleep(second*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void switchToWindowByID(String parentWindow){
        for(String window:driver.getWindowHandles()){
            if(!window.equals(parentWindow)){
                driver.switchTo().window(window);
                break;
            }
        }
    }

    public void closeRandomPopup(By popupBy, By closeButton){
        if(wait.until(ExpectedConditions.visibilityOfElementLocated(popupBy))!=null){
            clickElement(closeButton);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(popupBy));
        }
    }

    public void uploadByRobotClass(By by, String filePath) {
        clickElement(by);
        try {
            StringSelection strSelection = new StringSelection(filePath);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(strSelection, null);
            Robot robot = new Robot();
            robot.delay(300);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);

            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.delay(200);
            robot.keyRelease(KeyEvent.VK_ENTER);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
