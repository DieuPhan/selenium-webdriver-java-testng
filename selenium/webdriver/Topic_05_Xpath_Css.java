package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Topic_05_Xpath_Css {
    WebDriver driver;
    String URL = "http://live.demoguru99.com/";
    String firstName = "Dieu";
    String middleName = "Hoang";
    String lastName = "Phan";
    String fullName = firstName+" "+middleName+" "+lastName;
    String email = "dieuphan"+ new Random().nextInt(100000) +"@gmail.com";
    String password = "123456";

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_Login_With_Empty_Email_And_Password() {
        String expectedErrorMessage = "This is a required field.";

        // Navigate to Login Page
        driver.get(URL);
        driver.findElement(By.cssSelector("div.footer a[title='My Account']")).click();
        driver.findElement(By.cssSelector("button[title='Login']")).click();

        // Verify error message of Mail field
        String actualMailMessage = driver.findElement(By.id("advice-required-entry-email")).getText();
        Assert.assertEquals(expectedErrorMessage,actualMailMessage);

        // Verify error message of Password field
        String actualPasswordMessage = driver.findElement(By.id("advice-required-entry-pass")).getText();
        Assert.assertEquals(expectedErrorMessage,actualPasswordMessage);
    }

    @Test
    public void TC_02_Login_With_Invalid_Email() {
        String invalidEmail = "123434234@12312.123123";
        String password = "123456";
        String expectedErrorMessage = "Please enter a valid email address. For example johndoe@domain.com.";

        // Navigate to Login page
        driver.get(URL);
        driver.findElement(By.cssSelector("div.footer a[title='My Account']")).click();

        // Input invalid Email and valid Password
        driver.findElement(By.id("email")).sendKeys(invalidEmail);
        driver.findElement(By.id("pass")).sendKeys(password);
        driver.findElement(By.cssSelector("button[title='Login']")).click();

        // Verify error message of Email
        String actualErrorMessage = driver.findElement(By.id("advice-validate-email-email")).getText();
        Assert.assertEquals(actualErrorMessage,expectedErrorMessage);
    }

    @Test
    public void TC_03_Login_With_Password_Less_Than_Six_Characters() {
        String email = "automation@gmail.com";
        String invalidPassword = "123";
        String expectedErrorMessage = "Please enter 6 or more characters without leading or trailing spaces.";

        // Navigate to Login page
        driver.get(URL);
        driver.findElement(By.cssSelector("div.footer a[title='My Account']")).click();

        // Input valid Email and invalid Password
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("pass")).sendKeys(invalidPassword);
        driver.findElement(By.cssSelector("button[title='Login']")).click();

        // Verify error message of Email
        String actualErrorMessage = driver.findElement(By.id("advice-validate-password-pass")).getText();
        Assert.assertEquals(actualErrorMessage,expectedErrorMessage);
    }

    @Test
    public void TC_04_Login_With_Incorrect_Email_Or_Password() {
        String email = "automation@gmail.com";
        String incorrectPassword = "123123123";
        String expectedErrorMessage = "Invalid login or password.";

        // Navigate to Login page
        driver.get(URL);
        driver.findElement(By.cssSelector("div.footer a[title='My Account']")).click();

        // Input valid Email and invalid Password
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("pass")).sendKeys(incorrectPassword);
        driver.findElement(By.cssSelector("button[title='Login']")).click();

        // Verify error message of Email
        String actualErrorMessage = driver.findElement(By.cssSelector("li.error-msg span")).getText();
        Assert.assertEquals(actualErrorMessage,expectedErrorMessage);
    }

    @Test
    public void TC_05_Create_A_New_Account() {
        String expectedSuccessMessage = "Thank you for registering with Main Website Store.";

        // Navigate to Signup page
        driver.get(URL);
        driver.findElement(By.cssSelector("div.footer a[title='My Account']")).click();
        driver.findElement(By.cssSelector("a[title='Create an Account']")).click();

        // Create new account
        driver.findElement(By.id("firstname")).sendKeys(firstName);
        driver.findElement(By.id("middlename")).sendKeys(middleName);
        driver.findElement(By.id("lastname")).sendKeys(lastName);
        driver.findElement(By.id("email_address")).sendKeys(email);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("confirmation")).sendKeys(password);
        driver.findElement(By.cssSelector("button[title='Register']")).click();

        // Verify success message
        String actualSuccessMessage = driver.findElement(By.cssSelector("li.success-msg span")).getText();
        Assert.assertEquals(actualSuccessMessage,expectedSuccessMessage);

        // Logout
        driver.findElement(By.cssSelector("a[data-target-element='#header-account']")).click();
        driver.findElement(By.cssSelector("a[title='Log Out']")).click();

    }

    @Test
    public void TC_06_Login_With_Valid_Email_And_Password() {
        String expectedTitle = "MY DASHBOARD";
        String expectedMessage = "Hello, "+fullName+"!";

        // Navigate to Login Page
        driver.get(URL);
        driver.findElement(By.cssSelector("div.footer a[title='My Account']")).click();

        // Login with created account
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("pass")).sendKeys(password);
        driver.findElement(By.cssSelector("button[title='Login']")).click();

        // Verify title
        String actualTitle = driver.findElement(By.cssSelector("div.page-title h1")).getText();
        Assert.assertEquals(actualTitle,expectedTitle);

        // Verify message
        String actualMessage = driver.findElement(By.cssSelector("div.welcome-msg p.hello")).getText();
        Assert.assertEquals(actualMessage,expectedMessage);

        // Verify fullname
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='box-account box-info']//p[contains(text(),'"+fullName+"')]")).isDisplayed());

        // Verify email
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='box-account box-info']//p[contains(.,'"+email+"')]")).isDisplayed());
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
