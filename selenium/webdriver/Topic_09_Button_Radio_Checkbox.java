package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Topic_09_Button_Radio_Checkbox {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;


    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver,10);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    //@Test
    public void TC_01_Button() {
        By loginTab = By.cssSelector(".popup-login-tab-login");
        By loginButton = By.cssSelector("button.fhs-btn-login");
        By actualErrorMessageEmail = By.xpath("//input[@id='login_username']/../following-sibling::div[@class='fhs-input-alert']");
        By actualErrorMessagePass = By.xpath("//input[@id='login_password']/../following-sibling::div[@class='fhs-input-alert']");

        driver.get("https://www.fahasa.com/customer/account/create?attempt=1");


        // Navigate to Login tab
        clickElement(loginTab);

        // Verify Login button is disabled
        Assert.assertFalse(findElement(loginButton).isEnabled());

        // Input Email and Pass
        inputText(By.cssSelector("input#login_username"),"dieuph230893@gmail.com");
        inputText(By.cssSelector("input#login_password"),"123456");

        // Verify Login button is enabled
        Assert.assertTrue(findElement(loginButton).isEnabled());

        //Navigate to Login tab again
        driver.navigate().refresh();
        clickElement(loginTab);
        js.executeScript("arguments[0].removeAttribute('disabled')", findElement(loginButton));

        // Click Login button
        clickElement(loginButton);

        // Verify error message
        Assert.assertEquals(getText(actualErrorMessageEmail),"Thông tin này không thể để trống");
        Assert.assertEquals(getText(actualErrorMessagePass),"Thông tin này không thể để trống");
    }

    //@Test
    public void TC_02_Default_Checkbox_Radio() {
        By airConditioningCheckbox = By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input");
        By twoPointZeroPetrol = By.xpath("//label[text()='2.0 Petrol, 147kW']/preceding-sibling::input");

        driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");
        acceptCookie();

        // Check Dual-zone air conditioning checkbox
        check(airConditioningCheckbox);

        // Verify checkbox is selected
        Assert.assertTrue(findElement(airConditioningCheckbox).isSelected());

        // Uncheck Dual-zone air conditioning checkbox
        unCheck(airConditioningCheckbox);

        // Verify checkbox is not selected
        Assert.assertFalse(findElement(airConditioningCheckbox).isSelected());

        driver.get("https://demos.telerik.com/kendo-ui/radiobutton/index");

        // Check 2.0 Petrol radio button
        check(twoPointZeroPetrol);

        // Verify radio button is selected
        Assert.assertTrue(findElement(twoPointZeroPetrol).isSelected());
        check(twoPointZeroPetrol);
    }

    //@Test
    public void TC_03_Custom_Checkbox_Radio() {
        By summerRadioButton = By.cssSelector("[value='Summer']");
        By checkedCheckbox = By.xpath("//span[contains(.,'Checked')]/preceding-sibling::span/input");
        By indeterminateCheckbox = By.xpath("//span[contains(.,'Indeterminate')]/preceding-sibling::span/input");

        driver.get("https://material.angular.io/components/radio/examples");

        // Check Summer radio button
        checkElementByJS(summerRadioButton);

        // Verify radio button is selected
        Assert.assertTrue(findElement(summerRadioButton).isSelected());

        driver.get("https://material.angular.io/components/checkbox/examples");

        // Check Checked and Indeterminate checkbox
        checkElementByJS(checkedCheckbox);
        checkElementByJS(indeterminateCheckbox);

        // Verify checkbox is selected
        Assert.assertTrue(findElement(checkedCheckbox).isDisplayed());
        Assert.assertTrue(findElement(indeterminateCheckbox).isDisplayed());

        // Uncheck Checked and Indeterminate checkbox
        unCheckElementByJS(checkedCheckbox);
        unCheckElementByJS(indeterminateCheckbox);

        // Verify checkbox is not selected
        Assert.assertFalse(findElement(checkedCheckbox).isSelected());
        Assert.assertFalse(findElement(indeterminateCheckbox).isSelected());
    }

    @Test
    public void TC_04_Custom_Checkbox_Radio() {
        By canThoCheckbox = By.cssSelector("div[aria-label='Cần Thơ']");

        driver.get("https://docs.google.com/forms/d/e/1FAIpQLSfiypnd69zhuDkjKgqvpID9kwO29UCzeCVrGGtbNPZXQok0jA/viewform");

        // Verify Can Tho checkbox is not seleted
        Assert.assertTrue(findElement(By.xpath("//div[@aria-label='Cần Thơ' and @aria-checked='false']")).isDisplayed());

        // Check Can Tho radio button
        check(canThoCheckbox);

        // Verify Can Tho checkbox is seleted
        Assert.assertTrue(findElement(By.xpath("//div[@aria-label='Cần Thơ' and @aria-checked='true']")).isDisplayed());
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

    public String getText(By by){
        return findElement(by).getText();
    }

    public void check(By by){
        if(!findElement(by).isSelected()){
            clickElement(by);
        }
    }

    public void  unCheck(By by){
        if(findElement(by).isSelected()){
            clickElement(by);
        }
    }

    public void checkElementByJS(By by){
        if(!findElement(by).isSelected()) {
            js.executeScript("arguments[0].click();", findElement(by));
        }
    }

    public void unCheckElementByJS(By by){
        if(findElement(by).isSelected()) {
            js.executeScript("arguments[0].click();", findElement(by));
        }
    }

    public void acceptCookie(){
        clickElement(By.cssSelector("button#onetrust-accept-btn-handler"));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("p#onetrust-policy-text")));
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
