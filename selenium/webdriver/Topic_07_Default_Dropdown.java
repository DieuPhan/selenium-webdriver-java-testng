package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Topic_07_Default_Dropdown {
    WebDriver driver;

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_Dropdown_List() {
        driver.get("https://automationfc.github.io/basic-form/");

        // Verify Job Role 01 - Single Dropdown doesn't not allow multiple select
        Select jobRole01 = new Select(driver.findElement(By.cssSelector("#job1")));
        Assert.assertFalse(jobRole01.isMultiple());

        // Select by visible text
        jobRole01.selectByVisibleText("Mobile Testing");
        Assert.assertTrue(jobRole01.getFirstSelectedOption().getText().equals("Mobile Testing"));

        // Select by value
        jobRole01.selectByValue("manual");
        Assert.assertTrue(jobRole01.getFirstSelectedOption().getText().equals("Manual Testing"));

        // Select by index
        jobRole01.selectByIndex(9);
        Assert.assertTrue(jobRole01.getFirstSelectedOption().getText().equals("Functional UI Testing"));

        // Verify total option
        Assert.assertTrue(jobRole01.getOptions().size()==10);

        // Verify Job Role 02 - Single Dropdown allow multiple select
        Select jobRole02 = new Select(driver.findElement(By.cssSelector("#job2")));
        Assert.assertTrue(jobRole02.isMultiple());

        // Select multiple item
        ArrayList<String> items = new ArrayList<>();
        items.add("Automation");
        items.add("Mobile");
        items.add("Desktop");

        for (String item:items) {
            jobRole02.selectByVisibleText(item);
        }

        // Verify selected option
        ArrayList<String> selectedItems = new ArrayList<>();
        for (WebElement element: jobRole02.getAllSelectedOptions()) {
            selectedItems.add(element.getText());
        }
        Assert.assertEquals(items,selectedItems);

        // Deselect multiple item
        jobRole02.deselectAll();

        // Verify no selected item
        Assert.assertTrue(jobRole02.getAllSelectedOptions().isEmpty());
    }

    @Test
    public void TC_02_Dropdown_List() {
        By dateOfBirthBy = By.cssSelector("[name='DateOfBirthDay']");
        By monthOfBirthBy = By.cssSelector("[name='DateOfBirthMonth']");
        By yearOfBirthBy = By.cssSelector("[name='DateOfBirthYear']");
        String date = "1";
        String month = "May";
        String year = "1980";
        String email = "drake"+ new Random().nextInt(100000) +"@gmail.com";

        driver.get("https://demo.nopcommerce.com/");

        // Register new ac
        clickElement(By.cssSelector("a.ico-register"));

        // Register new account
        clickElement(By.cssSelector("#gender-male"));
        inputText(By.cssSelector("#FirstName"),"Dieu");
        inputText(By.cssSelector("#LastName"),"Phan");

        Select dateOfBirthDropdown = select(dateOfBirthBy);
        dateOfBirthDropdown.selectByVisibleText(date);
        Assert.assertEquals(dateOfBirthDropdown.getOptions().size(),32);

        Select monthOfBirthDropdown = select(monthOfBirthBy);
        monthOfBirthDropdown.selectByVisibleText(month);
        Assert.assertEquals(monthOfBirthDropdown.getOptions().size(),13);

        Select yearOfBirthDropdown = select(yearOfBirthBy);
        yearOfBirthDropdown.selectByVisibleText(year);
        Assert.assertEquals(yearOfBirthDropdown.getOptions().size(),112);

        inputText(By.cssSelector("#Email"),email);
        inputText(By.cssSelector("#Password"),"123456");
        inputText(By.cssSelector("#ConfirmPassword"),"123456");
        clickElement(By.cssSelector("#register-button"));

        // Verify Register success
        Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Your registration completed']")).isDisplayed());

        // Navigate to My account Page
        clickElement(By.cssSelector("a.ico-account"));

        // Verify selected date, month, year
        dateOfBirthDropdown = select(dateOfBirthBy);
        monthOfBirthDropdown = select(monthOfBirthBy);
        yearOfBirthDropdown = select(yearOfBirthBy);
        Assert.assertEquals(dateOfBirthDropdown.getFirstSelectedOption().getText(),date);
        Assert.assertEquals(monthOfBirthDropdown.getFirstSelectedOption().getText(),month);
        Assert.assertEquals(yearOfBirthDropdown.getFirstSelectedOption().getText(),year);

        // Log out
        clickElement(By.cssSelector("a.ico-logout"));
    }

    public void inputText(By by,String text){
        driver.findElement(by).clear();
        driver.findElement(by).sendKeys(text);
    }

    public Select select(By by){
        return new Select(driver.findElement(by));
    }

    public void clickElement(By by){
        driver.findElement(by).click();
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
