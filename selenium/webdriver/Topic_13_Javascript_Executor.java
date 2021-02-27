package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Topic_13_Javascript_Executor {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor jsExecutor;


    @BeforeClass
    public void beforeClass() {
        driver = new ChromeDriver();
        jsExecutor = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver,10);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_Javascript_Executor() {
        // Navigate to URL
        navigateToUrlByJS("http://live.demoguru99.com/");

        // Verify domain
        Assert.assertEquals(getDomain(),"live.demoguru99.com");

        // Verify URL
        Assert.assertEquals(getURL(),"http://live.demoguru99.com/");

        // Open MOBILE page
        clickToElementByJS(By.xpath("//a[text()='Mobile']"));

        // Add to cart
        clickToElementByJS(By.xpath("//a[text()='Samsung Galaxy']/../..//span[text()='Add to Cart']"));

        // Verify message
        Assert.assertTrue(areExpectedTextInInnerText("Samsung Galaxy was added to your shopping cart."));

        // Open Customer Service page
        clickToElementByJS(By.xpath("//a[text()='Customer Service']"));

        // Scroll to Element
        scrollToElement(By.cssSelector("#newsletter"));

        // Input Email
        sendkeyToElementByJS(By.cssSelector("#newsletter"),"sendkeyByJS"+new Random().nextInt(9999) +"@gmail.com");

        // Click Subscribe
        clickToElementByJS(By.cssSelector("[title='Subscribe']"));

        // Verify success message
        Assert.assertTrue(areExpectedTextInInnerText("Thank you for your subscription."));

        // Navigate to URL
        navigateToUrlByJS("http://demo.guru99.com/v4/");

        // Verify domain
        Assert.assertEquals(getDomain(),"demo.guru99.com");
    }

    @Test
    public void TC_02_Verify_HTML5_Validation_Message() {
        // Navigate to URL
        navigateToUrlByJS("https://automationfc.github.io/html5/index.html");

        // Click Submit
        clickToElementByJS(By.cssSelector("[name='submit-btn']"));

        // Verify message
        Assert.assertEquals(getElementValidationMessage(By.cssSelector("#fname")),"Please fill out this field.");

        // Input name
        sendkeyToElementByJS(By.cssSelector("#fname"),"Drake");

        // Click Submit
        clickToElementByJS(By.cssSelector("[name='submit-btn']"));

        // Verify message
        Assert.assertEquals(getElementValidationMessage(By.cssSelector("#pass")),"Please fill out this field.");

        // Input pass
        sendkeyToElementByJS(By.cssSelector("#pass"),"123456");

        // Click Submit
        clickToElementByJS(By.cssSelector("[name='submit-btn']"));

        // Verify message
        Assert.assertEquals(getElementValidationMessage(By.cssSelector("#em")),"Please fill out this field.");

        // Input invalid email
        sendkeyToElementByJS(By.cssSelector("#em"),"123!@#$");

        // Click Submit
        clickToElementByJS(By.cssSelector("[name='submit-btn']"));

        // Verify message
        Assert.assertEquals(getElementValidationMessage(By.cssSelector("#em")),"A part following '@' should not contain the symbol '#'.");

        // Input invalid email
        sendkeyToElementByJS(By.cssSelector("#em"),"123@456");

        // Click Submit
        clickToElementByJS(By.cssSelector("[name='submit-btn']"));

        // Verify message
        Assert.assertEquals(getElementValidationMessage(By.cssSelector("#em")),"Please match the requested format.");

        // Input email
        sendkeyToElementByJS(By.cssSelector("#em"),"drake@gmail.com");

        // Click Submit
        clickToElementByJS(By.cssSelector("[name='submit-btn']"));

        // Verify message
        Assert.assertEquals(getElementValidationMessage(By.cssSelector("select")),"Please select an item in the list.");
    }

    @Test
    public void TC_03_Verify_HTML5_Validation_Message() {
        // Navigate to URL
        navigateToUrlByJS("https://login.ubuntu.com/");

        // Accept cookie
        clickToElementByJS(By.xpath("//button[text()='Accept all and visit site']"));

        // Click Login
        clickToElementByJS(By.cssSelector("[data-qa-id='login_button']"));

        // Verify message
        Assert.assertEquals(getElementValidationMessage(By.cssSelector("[data-qa-id='login_form'] #id_email")),"Please fill out this field.");

        // Input invalid email
        sendkeyToElementByJS(By.cssSelector("[data-qa-id='login_form'] #id_email"),"a");

        // Click Login
        clickToElementByJS(By.cssSelector("[data-qa-id='login_button']"));

        // Verify message
        Assert.assertEquals(getElementValidationMessage(By.cssSelector("[data-qa-id='login_form'] #id_email")),"Please include an '@' in the email address. 'a' is missing an '@'.");

    }

    @Test
    public void TC_04_Remove_Attribute() {
        // Navigate to URL
        navigateToUrlByJS("http://demo.guru99.com/");

        // Get UserId and Password
        sendkeyToElementByJS(By.cssSelector("[name='emailid']"),"dieuph230893@gmail.com");
        clickToElementByJS(By.cssSelector("[name='btnLogin']"));
        String userID = getText(By.xpath("//td[text()='User ID :']/following-sibling::td"));
        String loginPassword = getText(By.xpath("//td[text()='Password :']/following-sibling::td"));

        // Login
        navigateToUrlByJS("http://demo.guru99.com/v4/");
        sendkeyToElementByJS(By.cssSelector("[name='uid']"),userID);
        sendkeyToElementByJS(By.cssSelector("[name='password']"),loginPassword);
        clickToElementByJS(By.cssSelector("[name='btnLogin']"));

        // Click New Customer
        clickToElementByJS(By.xpath("//a[text()='New Customer']"));

        // Create customer
        inputText(By.cssSelector("[name='name']"),"Drake");

        removeAttributeInDOM(By.cssSelector("#dob"),"type");
        inputText(By.cssSelector("#dob"),"1993-08-23");

        inputText(By.cssSelector("[name='addr']"),"Botanica");
        inputText(By.cssSelector("[name='city']"),"Ho Chi Minh");
        inputText(By.cssSelector("[name='state']"),"Tan Binh");
        inputText(By.cssSelector("[name='pinno']"),"123456");
        inputText(By.cssSelector("[name='telephoneno']"),"123456789");
        inputText(By.cssSelector("[name='emailid']"),"drake"+ new Random().nextInt(100000) +"@gmail.com");
        inputText(By.cssSelector("[name='password']"),"pass123");
        clickElement(By.cssSelector("[name='sub']"));

        // Verify create Customer success
        Assert.assertTrue(areExpectedTextInInnerText("Customer Registered Successfully!!!"));
    }

    @Test
    public void TC_05_Create_An_Account() {
        // Navigate to URL
        navigateToUrlByJS("http://live.demoguru99.com/");

        // Click My Account
        clickToElementByJS(By.xpath("//div[@id='header-account']//a[text()='My Account']"));

        // Click Create an account
        clickToElementByJS(By.cssSelector("[title='Create an Account']"));

        // Create an Account
        sendkeyToElementByJS(By.cssSelector("#firstname"),"Dieu");
        sendkeyToElementByJS(By.cssSelector("#middlename"),"Hoang");
        sendkeyToElementByJS(By.cssSelector("#lastname"),"Phan");
        sendkeyToElementByJS(By.cssSelector("#email_address"),"drake"+ new Random().nextInt(9999) +"@gmail.com");
        sendkeyToElementByJS(By.cssSelector("#password"),"drake12345");
        sendkeyToElementByJS(By.cssSelector("#confirmation"),"drake12345");
        clickToElementByJS(By.cssSelector(".buttons-set [title='Register']"));

        // Verify success message
        Assert.assertTrue(areExpectedTextInInnerText("Thank you for registering with Main Website Store."));

        // Log out
        clickToElementByJS(By.xpath("//a[text()='Log Out']"));

        // Verify Home page
        Assert.assertTrue(wait.until(ExpectedConditions.titleIs("Home page")));
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

    public String getTextAngularDropdownSelectedText(){
        return (String) jsExecutor.executeScript("return document.querySelector('#games_hidden option').text");
    }

    public String getText(By by){
        return findElement(by).getText();
    }

    public Object executeForBrowser(String javaScript) {
        return jsExecutor.executeScript(javaScript);
    }

    public String getInnerText() {
        return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
    }

    public boolean areExpectedTextInInnerText(String textExpected) {
        String textActual = (String) executeForBrowser("return document.documentElement.innerText.match('" + textExpected + "')[0]");
        return textActual.equals(textExpected);
    }

    public String getDomain(){
        return (String) executeForBrowser("return document.domain");
    }

    public String getURL(){
        return (String) executeForBrowser("return document.URL");
    }

    public void scrollToBottomPage() {
        executeForBrowser("window.scrollBy(0,document.body.scrollHeight)");
    }

    public void navigateToUrlByJS(String url) {
        executeForBrowser("window.location = '" + url + "'");
    }

    public void highlightElement(By by) {
        WebElement element = findElement(by);
        String originalStyle = element.getAttribute("style");
        jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 2px solid red; border-style: dashed;");
        sleepInSecond(1);
        jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
    }

    public void clickToElementByJS(By by) {
        jsExecutor.executeScript("arguments[0].click();", findElement(by));
    }

    public void scrollToElement(By by) {
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", findElement(by));
    }

    public void sendkeyToElementByJS(By by, String value) {
        jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", findElement(by));
    }

    public void removeAttributeInDOM(By by, String attributeRemove) {
        jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", findElement(by));
    }

    public boolean areJQueryAndJSLoadedSuccess() {

        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) jsExecutor.executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    return true;
                }
            }
        };

        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
            }
        };

        return wait.until(jQueryLoad) && wait.until(jsLoad);
    }

    public String getElementValidationMessage(By by) {
        return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", findElement(by));
    }

    public boolean isImageLoaded(By by) {
        boolean status = (boolean) jsExecutor.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", findElement(by));
        if (status) {
            return true;
        } else {
            return false;
        }
    }

    public void sleepInSecond(int second){
        try {
            Thread.sleep(second*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
