package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Topic_06_Element {
    WebDriver driver;
    String URL = "https://automationfc.github.io/basic-form/index.html";
    By email = By.id("mail");
    By ageUnderEighteen = By.id("under_18");
    By education = By.id("edu");
    By jobRole01 = By.id("job1");
    By jobRole02 = By.id("job2");
    By jobRole03 = By.id("job3");
    By developmentCheckbox = By.id("development");
    By slider01 = By.id("slider-1");
    By slider02 = By.id("slider-2");
    By password = By.cssSelector("[name='user_pass']");
    By radioButtonDisabled = By.id("radio-disabled");
    By biography = By.id("bio");
    By checkboxDisabled = By.id("check-disbaled");
    By languageJava = By.id("java");


    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_Verify_Element_Displayed(){
        driver.get(URL);

        // Input value and log console
        if(isElementDisplayed(email, "Email text box")){
            inputText(email,"Automation Testing");
        }
        if(isElementDisplayed(ageUnderEighteen,"Age Under 18 radio button")){
            click(ageUnderEighteen);
        }
        if(isElementDisplayed(education,"Education text box")){
            inputText(education,"Automation Testing");
        }
    }

    @Test
    public void TC_02_Verify_Element_Disabled_Enabled(){
        driver.navigate().refresh();

        // Verify element enabled
        isElementEnabled(email,"Email text box");
        isElementEnabled(ageUnderEighteen, "Age Under 18 radio button");
        isElementEnabled(education,"Education text box");
        isElementEnabled(jobRole01,"Job Role 01 text box");
        isElementEnabled(jobRole02,"Job Role 02 text box");
        isElementEnabled(jobRole03,"Job Role 03 text box");
        isElementEnabled(developmentCheckbox,"Development checkbox");
        isElementEnabled(slider01,"Slider 01 text box");
        isElementEnabled(slider02,"Slider 02 text box");
        isElementEnabled(password,"Password text box");
        isElementEnabled(radioButtonDisabled,"Radio Button Disabled");
        isElementEnabled(biography,"Biography text box");
        isElementEnabled(checkboxDisabled,"Checkbox Disabled");
    }

    @Test
    public void TC_03_Verify_Element_Selected(){
        driver.navigate().refresh();

        // Check
        click(ageUnderEighteen);
        click(languageJava);

        // Verify element selected
        isElementSelected(ageUnderEighteen,"Age Under 18 radio button");
        isElementSelected(languageJava,"Language Java checkbox");

        // Uncheck
        click(languageJava);

        // Log console
        isElementSelected(languageJava,"Language Java checkbox");
    }

    @Test
    public void TC_04_Register_Function() {
        driver.get("https://login.mailchimp.com/signup/");
        WebElement password = driver.findElement(By.id("new_password"));
        WebElement validateNumber = driver.findElement(By.cssSelector("li.number-char"));
        WebElement validateLowercase = driver.findElement(By.cssSelector("li.lowercase-char"));
        WebElement validateUppercase = driver.findElement(By.cssSelector("li.uppercase-char"));
        WebElement validateSpecial = driver.findElement(By.cssSelector("li.special-char"));
        WebElement validateMinimum = driver.findElement(By.cssSelector("li[class='8-char']"));
        WebElement signUpButton = driver.findElement(By.cssSelector("#create-account"));
        WebElement mailCheckbox = driver.findElement(By.cssSelector("input[name='marketing_newsletter']"));

        // Input username and email
        driver.findElement(By.id("email")).sendKeys("dieuph230893@gmail.com");
        driver.findElement(By.id("new_username")).sendKeys("Drake");

        // Validate number and Sign Up button
        password.sendKeys("12");
        Assert.assertTrue(validateNumber.getAttribute("class").contains("completed"));
        Assert.assertFalse(signUpButton.isEnabled());

        // Validate lowercase Sign Up button
        password.sendKeys("ab");
        Assert.assertTrue(validateLowercase.getAttribute("class").contains("completed"));
        Assert.assertFalse(signUpButton.isEnabled());


        // Validate uppercase Sign Up button
        password.sendKeys("AB");
        Assert.assertTrue(validateUppercase.getAttribute("class").contains("completed"));
        Assert.assertFalse(signUpButton.isEnabled());


        // Validate special char Sign Up button
        password.sendKeys("%#");
        Assert.assertTrue(validateSpecial.getAttribute("class").contains("completed"));
        Assert.assertTrue(signUpButton.isEnabled());


        // Validate minimum char Sign Up button
        Assert.assertTrue(validateMinimum.getAttribute("class").contains("completed"));

        // Validate mail check box selected
        mailCheckbox.click();
        Assert.assertTrue(mailCheckbox.isSelected());
    }

    public boolean isElementDisplayed(By by,String elementName){
        WebElement element = driver.findElement(by);
        if(element.isDisplayed()){
            System.out.println(elementName + " is displayed");
            return true;
        } else {
            System.out.println(elementName + " is not displayed");
        }
        return false;
    }

    public boolean isElementEnabled(By by,String elementName){
        WebElement element = driver.findElement(by);
        if(element.isEnabled()){
            System.out.println(elementName + " is enabled");
            return true;
        } else {
            System.out.println(elementName + " is not enabled");
        }
        return false;
    }

    public boolean isElementSelected(By by,String elementName){
        WebElement element = driver.findElement(by);
        if(element.isSelected()){
            System.out.println(elementName + " is selected");
            return true;
        } else {
            System.out.println(elementName + " is not selected");
        }
        return false;
    }

    public void inputText(By by, String text){
        driver.findElement(by).sendKeys(text);
    }

    public void click(By by){
        driver.findElement(by).click();
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
