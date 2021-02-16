package webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Topic_09_Alert {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;
    Alert alert;
    By result = By.cssSelector("#result");


    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver,10);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_Accept_Alert() {
        By jsAlertButton = By.xpath("//button[text()='Click for JS Alert']");

        driver.get("https://automationfc.github.io/basic-form/index.html");

        // Navigate to Login tab
        clickElement(jsAlertButton);

        // Verify Alert message
        alert = driver.switchTo().alert();
        Assert.assertEquals(alert.getText(),"I am a JS Alert");

        // Verify result
        alert.accept();
        Assert.assertEquals(getText(result),"You clicked an alert successfully");
    }

    @Test
    public void TC_02_Confirm_Alert() {
        By jsConfirmButton = By.xpath("//button[text()='Click for JS Confirm']");

        driver.navigate().refresh();

        // Navigate to Login tab
        clickElement(jsConfirmButton);

        // Verify Alert message
        alert = driver.switchTo().alert();
        Assert.assertEquals(alert.getText(),"I am a JS Confirm");

        // Verify result
        alert.dismiss();
        Assert.assertEquals(getText(result),"You clicked: Cancel");
    }

    @Test
    public void TC_03_Prompt_Alert() {
        By jsPromptButton = By.xpath("//button[text()='Click for JS Prompt']");
        String text = "DieuPhan";

        driver.navigate().refresh();

        // Navigate to Login tab
        clickElement(jsPromptButton);

        // Verify Alert message
        alert = driver.switchTo().alert();
        Assert.assertEquals(alert.getText(),"I am a JS prompt");

        // Verify result
        alert.sendKeys(text);
        alert.accept();
        Assert.assertEquals(getText(result),"You entered: "+text);
    }

    //@Test
    public void TC_04_Authentication_Alert() {
        String username = "admin";
        String password = "admin";

        driver.get("https://"+username+":"+password+"@the-internet.herokuapp.com/basic_auth");

        Assert.assertTrue(findElement(By.xpath("//div[@id='content']//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());
    }

    @Test
    public void TC_05_Authentication_Alert() throws IOException {
        String firefoxAuthen = System.getProperty("user.dir")+"\\authen_firefox.exe";
        System.out.println(firefoxAuthen);

        Runtime.getRuntime().exec(new String[] {firefoxAuthen,"admin","admin"});

        driver.get("http://the-internet.herokuapp.com/basic_auth");

        Assert.assertTrue(findElement(By.xpath("//div[@id='content']//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());
    }

    public WebElement findElement(By by){
        return driver.findElement(by);
    }

    public void clickElement(By by){
        findElement(by).click();
    }

    public String getText(By by){
        return findElement(by).getText();
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
