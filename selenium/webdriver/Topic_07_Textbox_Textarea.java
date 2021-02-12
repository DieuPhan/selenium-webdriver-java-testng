package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Topic_07_Textbox_Textarea {
    WebDriver driver;


    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://demo.guru99.com/");
    }

    @Test
    public void TC_01_Textbox_Textarea() {
        String name = "Drake";
        String gender = "male";
        String dateOfBirth = "1993-08-23";
        String address = "Botanica";
        String editAddress = "Edit Botanica";
        String city = "Ho Chi Minh";
        String editCity = "Edit Ho Chi Minh";
        String state = "Tan Binh";
        String editState = "Edit Tan Binh";
        String pin = "123456";
        String editPin = "654321";
        String tel = "123456789";
        String editTel = "987654321";
        String email = "drake"+ new Random().nextInt(100000) +"@gmail.com";
        String editEmail = "Edit" + email;
        String password = "pass123";

        // Get UserId and Password
        inputText(By.cssSelector("[name='emailid']"),"dieuph230893@gmail.com");
        clickElement(By.cssSelector("[name='btnLogin']"));
        String userID = getText(By.xpath("//td[text()='User ID :']/following-sibling::td"));
        String loginPassword = getText(By.xpath("//td[text()='Password :']/following-sibling::td"));

        // Login
        driver.get("http://demo.guru99.com/v4/");
        inputText(By.cssSelector("[name='uid']"),userID);
        inputText(By.cssSelector("[name='password']"),loginPassword);
        clickElement(By.cssSelector("[name='btnLogin']"));

        // Verify Home Page display
        Assert.assertTrue(getText(By.xpath("//td[contains(text(),'Manger Id :')]")).contains(userID));

        // Create Customer
        clickElement(By.xpath("//a[text()='New Customer']"));
        inputText(By.cssSelector("[name='name']"),name);
        inputText(By.cssSelector("#dob"),dateOfBirth);
        inputText(By.cssSelector("[name='addr']"),address);
        inputText(By.cssSelector("[name='city']"),city);
        inputText(By.cssSelector("[name='state']"),state);
        inputText(By.cssSelector("[name='pinno']"),pin);
        inputText(By.cssSelector("[name='telephoneno']"),tel);
        inputText(By.cssSelector("[name='emailid']"),email);
        inputText(By.cssSelector("[name='password']"),password);
        clickElement(By.cssSelector("[name='sub']"));

        // Get Customer ID
        String customerId = getText(By.xpath("//td[text()='Customer ID']/following-sibling::td"));

        // Verify Customer Name, Gender, Birthdate, Address, City, State, Pin, Mobile No., Email
        Assert.assertEquals(getText(By.xpath("//td[text()='Customer Name']/following-sibling::td")),name);
        Assert.assertEquals(getText(By.xpath("//td[text()='Gender']/following-sibling::td")),gender);
        Assert.assertEquals(getText(By.xpath("//td[text()='Birthdate']/following-sibling::td")),dateOfBirth);
        Assert.assertEquals(getText(By.xpath("//td[text()='Address']/following-sibling::td")),address);
        Assert.assertEquals(getText(By.xpath("//td[text()='City']/following-sibling::td")),city);
        Assert.assertEquals(getText(By.xpath("//td[text()='State']/following-sibling::td")),state);
        Assert.assertEquals(getText(By.xpath("//td[text()='Pin']/following-sibling::td")),pin);
        Assert.assertEquals(getText(By.xpath("//td[text()='Mobile No.']/following-sibling::td")),tel);
        Assert.assertEquals(getText(By.xpath("//td[text()='Email']/following-sibling::td")),email);

        // Navigate to Edit Customer Page
        clickElement(By.xpath("//a[text()='Edit Customer']"));
        inputText(By.cssSelector("[name='cusid']"),customerId);
        clickElement(By.cssSelector("[name='AccSubmit']"));

        // Verify Customer name and Address
        Assert.assertEquals(getAttribute(By.cssSelector("[name='name']"),"value"),name);
        Assert.assertEquals(getText(By.cssSelector("[name='addr']")),address);

        // Edit Customer
        inputText(By.cssSelector("[name='addr']"),editAddress);
        inputText(By.cssSelector("[name='city']"),editCity);
        inputText(By.cssSelector("[name='state']"),editState);
        inputText(By.cssSelector("[name='pinno']"),editPin);
        inputText(By.cssSelector("[name='telephoneno']"),editTel);
        inputText(By.cssSelector("[name='emailid']"),editEmail);
        clickElement(By.cssSelector("[name='sub']"));

        // Verify Address, City, State, Pin, Mobile No., Email
        Assert.assertEquals(getText(By.xpath("//td[text()='Address']/following-sibling::td")),editAddress);
        Assert.assertEquals(getText(By.xpath("//td[text()='City']/following-sibling::td")),editCity);
        Assert.assertEquals(getText(By.xpath("//td[text()='State']/following-sibling::td")),editState);
        Assert.assertEquals(getText(By.xpath("//td[text()='Pin']/following-sibling::td")),editPin);
        Assert.assertEquals(getText(By.xpath("//td[text()='Mobile No.']/following-sibling::td")),editTel);
        Assert.assertEquals(getText(By.xpath("//td[text()='Email']/following-sibling::td")),editEmail);
    }

    public void inputText(By by,String text){
        driver.findElement(by).clear();
        driver.findElement(by).sendKeys(text);
    }

    public void clickElement(By by){
        driver.findElement(by).click();
    }

    public String getText(By by){
        return driver.findElement(by).getText();
    }

    public String getAttribute(By by, String attributeName){
        return driver.findElement(by).getAttribute(attributeName);
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
