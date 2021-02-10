package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Topic_06_WebBrowser {
    WebDriver driver;
    String URL = "http://live.demoguru99.com/";
    String expectedUrlLog = "http://live.demoguru99.com/index.php/customer/account/login/";
    String expectedUrlReg = "http://live.demoguru99.com/index.php/customer/account/create/";
    String expectedTitleLog = "Customer Login";
    String expectedTitleReg = "Create New Customer Account";
    String expectedTextLog = "Login or Create an Account";
    String expectedTextReg = "Create an Account";


    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_Verify_Url(){
        // Navigate to Login Page
        driver.get(URL);
        driver.findElement(By.cssSelector("div.footer a[title='My Account']")).click();

        // Verify Url of Login Page
        String actualUrlLog = driver.getCurrentUrl();
        Assert.assertEquals(actualUrlLog,expectedUrlLog);

        //Navigate to Register Page
        driver.findElement(By.cssSelector("a[title='Create an Account']")).click();

        // Verify Url of Register Page
        String actualUrlReg = driver.getCurrentUrl();
        Assert.assertEquals(actualUrlReg,expectedUrlReg);
    }

    @Test
    public void TC_02_Verify_Title(){
        //Navigate to Login Page
        driver.get(URL);
        driver.findElement(By.cssSelector("div.footer a[title='My Account']")).click();

        // Verify title of Login Page
        String actualTitleLog = driver.getTitle();
        Assert.assertEquals(actualTitleLog,expectedTitleLog);

        //Navigate to Register Page
        driver.findElement(By.cssSelector("a[title='Create an Account']")).click();

        // Verify title of Register Page
        String actualTitleReg = driver.getTitle();
        Assert.assertEquals(actualTitleReg,expectedTitleReg);
    }

    @Test
    public void TC_03_Navigate_Function(){
        // Navigate to Register Page
        driver.get(URL);
        driver.findElement(By.cssSelector("div.footer a[title='My Account']")).click();
        driver.findElement(By.cssSelector("a[title='Create an Account']")).click();

        // Verify Url of Register Page
        String actualUrlReg = driver.getCurrentUrl();
        Assert.assertEquals(actualUrlReg,expectedUrlReg);

        // Back to Login Page
        driver.navigate().back();

        // Verify Url of Login Page
        String actualUrlLog = driver.getCurrentUrl();
        Assert.assertEquals(actualUrlLog,expectedUrlLog);

        // Forward to Register Page
        driver.navigate().forward();

        // Verify title of Register Page
        String actualTitleReg = driver.getTitle();
        Assert.assertEquals(actualTitleReg,expectedTitleReg);
    }

    @Test
    public void TC_04_Get_Page_Source_Code() {
        // Navigate to Register Page
        driver.get(URL);
        driver.findElement(By.cssSelector("div.footer a[title='My Account']")).click();

        // Verify Login Page contains expected Text
        String pageSourceLog = driver.getPageSource();
        Assert.assertTrue(pageSourceLog.contains(expectedTextLog));


        // Navigate to Register Page
        driver.findElement(By.cssSelector("a[title='Create an Account']")).click();

        // Verify Register Page contains expected Text
        String pageSourceReg = driver.getPageSource();
        Assert.assertTrue(pageSourceReg.contains(expectedTextReg));
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
