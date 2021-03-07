package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Topic_14_Upload_File {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;


    @BeforeClass
    public void beforeClass() {
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver,10);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_Upload_By_Sendkeys() {
        String uploadFileName01 = "img_01.png";
        String uploadFileName02 = "img_02.png";
        String uploadFileName03 = "img_03.png";
        String uploadFilePath01 = System.getProperty("user.dir")+"\\testData\\img_01.png";
        String uploadFilePath02 = System.getProperty("user.dir")+"\\testData\\img_02.png";
        String uploadFilePath03 = System.getProperty("user.dir")+"\\testData\\img_03.png";

        driver.get("http://blueimp.github.io/jQuery-File-Upload/");

        // Upload files
        findElement(By.xpath("//span[text()='Add files...']/../input")).sendKeys(uploadFilePath01+"\n"+uploadFilePath02+"\n"+uploadFilePath03);

        // Verify file uploaded
        Assert.assertTrue(findElement(By.xpath("//p[text()='"+uploadFileName01+"']")).isDisplayed());
        Assert.assertTrue(findElement(By.xpath("//p[text()='"+uploadFileName02+"']")).isDisplayed());
        Assert.assertTrue(findElement(By.xpath("//p[text()='"+uploadFileName03+"']")).isDisplayed());

        for (WebElement startButton:findElements(By.cssSelector(".template-upload .btn-primary.start"))) {
            startButton.click();
        }
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".fileupload-progress .progress-bar")));

        Assert.assertTrue(findElement(By.xpath("//a[text()='"+uploadFileName01+"']")).isDisplayed());
        Assert.assertTrue(findElement(By.xpath("//a[text()='"+uploadFileName02+"']")).isDisplayed());
        Assert.assertTrue(findElement(By.xpath("//a[text()='"+uploadFileName03+"']")).isDisplayed());
    }

    @Test
    public void TC_02_Upload_By_AutoIT() throws IOException {
        String uploadFileName01 = "img_01.png";
        String uploadFileName02 = "img_02.png";
        String uploadFileName03 = "img_03.png";
        String chromeUploadExe = System.getProperty("user.dir")+"\\uploadByAutoIT\\chromeUploadMultiple.exe";
        String uploadFilePath01 = System.getProperty("user.dir")+"\\testData\\img_01.png";
        String uploadFilePath02 = System.getProperty("user.dir")+"\\testData\\img_02.png";
        String uploadFilePath03 = System.getProperty("user.dir")+"\\testData\\img_03.png";

        driver.get("http://blueimp.github.io/jQuery-File-Upload/");

        // Upload files
       clickElement(By.cssSelector(".fileinput-button"));
       Runtime.getRuntime().exec(new String[] {chromeUploadExe,uploadFilePath01,uploadFilePath02,uploadFilePath03});

        // Verify file uploaded
        Assert.assertTrue(findElement(By.xpath("//p[text()='"+uploadFileName01+"']")).isDisplayed());
        Assert.assertTrue(findElement(By.xpath("//p[text()='"+uploadFileName02+"']")).isDisplayed());
        Assert.assertTrue(findElement(By.xpath("//p[text()='"+uploadFileName03+"']")).isDisplayed());

        for (WebElement startButton:findElements(By.cssSelector(".template-upload .btn-primary.start"))) {
            startButton.click();
        }
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".fileupload-progress .progress-bar")));

        Assert.assertTrue(findElement(By.xpath("//a[text()='"+uploadFileName01+"']")).isDisplayed());
        Assert.assertTrue(findElement(By.xpath("//a[text()='"+uploadFileName02+"']")).isDisplayed());
        Assert.assertTrue(findElement(By.xpath("//a[text()='"+uploadFileName03+"']")).isDisplayed());
    }

    @Test
    public void TC_03_Upload_By_Robot_class() throws IOException {
        String uploadFileName01 = "img_01.png";
        String uploadFileName02 = "img_02.png";
        String uploadFileName03 = "img_03.png";
        String uploadFilePath01 = System.getProperty("user.dir")+"\\testData\\img_01.png";
        String uploadFilePath02 = System.getProperty("user.dir")+"\\testData\\img_02.png";
        String uploadFilePath03 = System.getProperty("user.dir")+"\\testData\\img_03.png";
        By uploadButtonBy = By.cssSelector(".fileinput-button");

        driver.get("http://blueimp.github.io/jQuery-File-Upload/");

        // Upload files
        uploadByRobotClass(uploadButtonBy,uploadFilePath01);
        uploadByRobotClass(uploadButtonBy,uploadFilePath02);
        uploadByRobotClass(uploadButtonBy,uploadFilePath03);

        // Verify file uploaded
        Assert.assertTrue(findElement(By.xpath("//p[text()='"+uploadFileName01+"']")).isDisplayed());
        Assert.assertTrue(findElement(By.xpath("//p[text()='"+uploadFileName02+"']")).isDisplayed());
        Assert.assertTrue(findElement(By.xpath("//p[text()='"+uploadFileName03+"']")).isDisplayed());

        for (WebElement startButton:findElements(By.cssSelector(".template-upload .btn-primary.start"))) {
            startButton.click();
        }
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".fileupload-progress .progress-bar")));

        Assert.assertTrue(findElement(By.xpath("//a[text()='"+uploadFileName01+"']")).isDisplayed());
        Assert.assertTrue(findElement(By.xpath("//a[text()='"+uploadFileName02+"']")).isDisplayed());
        Assert.assertTrue(findElement(By.xpath("//a[text()='"+uploadFileName03+"']")).isDisplayed());
    }

    @Test
    public void TC_04_Upload_File() {
        String uploadFileName01 = "img_01.png";
        String uploadFileName02 = "img_02.png";
        String uploadFileName03 = "img_03.png";
        String uploadFilePath01 = System.getProperty("user.dir")+"\\testData\\img_01.png";
        String uploadFilePath02 = System.getProperty("user.dir")+"\\testData\\img_02.png";
        String uploadFilePath03 = System.getProperty("user.dir")+"\\testData\\img_03.png";
        driver.get("https://gofile.io/uploadFiles");
        String parentWindow = driver.getWindowHandle();


        // Upload file
        findElement(By.cssSelector("input[type='file']")).sendKeys(uploadFilePath01+"\n"+uploadFilePath02+"\n"+uploadFilePath03);

        // Click upload button
        clickElement(By.cssSelector("button#uploadFiles-btnUpload"));

        // Click Download link
        clickElement(By.cssSelector("a#uploadFiles-uploadRowResult-downloadLink"));

        // Close popup
        switchToWindowByID(parentWindow);
        closeRandomPopup(By.cssSelector("[role='dialog']"),By.xpath("//button[text()='I have a VPN already']"));

        // Check Download button is displayed
        Assert.assertTrue(findElement(By.xpath("//td[text()='"+uploadFileName01+"']/..//button[@data-original-title='Download']")).isDisplayed());
        Assert.assertTrue(findElement(By.xpath("//td[text()='"+uploadFileName02+"']/..//button[@data-original-title='Download']")).isDisplayed());
        Assert.assertTrue(findElement(By.xpath("//td[text()='"+uploadFileName03+"']/..//button[@data-original-title='Download']")).isDisplayed());

        // Check Play button is displayed
        Assert.assertTrue(findElement(By.xpath("//td[text()='"+uploadFileName01+"']/..//button[@data-original-title='Play / Info']")).isDisplayed());
        Assert.assertTrue(findElement(By.xpath("//td[text()='"+uploadFileName02+"']/..//button[@data-original-title='Play / Info']")).isDisplayed());
        Assert.assertTrue(findElement(By.xpath("//td[text()='"+uploadFileName03+"']/..//button[@data-original-title='Play / Info']")).isDisplayed());
    }

    public WebElement findElement(By by){
        return driver.findElement(by);
    }

    public List<WebElement> findElements(By by){
        return driver.findElements(by);
    }

    public void inputText(By by,String text){
        findElement(by).clear();
        findElement(by).sendKeys(text);
    }

    public void clickElement(By by){
        findElement(by).click();
    }

    public void uploadByRobotClass(By uploadButtonBy, String filePath) {
        clickElement(uploadButtonBy);
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

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
