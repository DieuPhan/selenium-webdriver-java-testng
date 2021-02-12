package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Topic_07_Custom_Dropdown {
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
    public void TC_01_JQuery() {
        driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");
        By dropdownBy = By.cssSelector("#number-button span.ui-selectmenu-text");
        By allItems = By.cssSelector("#number-menu div");
        String selectItem = "19";

        // Select item
        selectDropdownItem(dropdownBy,allItems,selectItem);

        // Verify selected item
        Assert.assertEquals(getText(dropdownBy),selectItem);
    }

    @Test
    public void TC_02_Angular() {
        driver.get("https://ej2.syncfusion.com/angular/demos/?_ga=2.262049992.437420821.1575083417-524628264.1575083417#/material/drop-down-list/data-binding");
        By dropdownBy = By.cssSelector("#games");
        By allItems = By.cssSelector("#games_options li");
        String selectItem = "Football";

        // Select item
        selectDropdownItem(dropdownBy,allItems,selectItem);

        // Verify selected item
        Assert.assertEquals(getTextAngularDropdownSelectedText(),selectItem);
    }

    @Test
    public void TC_03_ReactJS() {
        driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
        By dropdownBy = By.cssSelector("div.dropdown");
        By allItems = By.cssSelector("div.dropdown .item");
        String selectItem = "Christian";

        // Select item
        selectDropdownItem(dropdownBy,allItems,selectItem);

        // Verify selected item
        Assert.assertEquals(getText(dropdownBy),selectItem);
    }

    @Test
    public void TC_04_VueJS() {
        driver.get("https://mikerodham.github.io/vue-dropdowns/");
        By dropdownBy = By.cssSelector("li.dropdown-toggle");
        By allItems = By.cssSelector("ul.dropdown-menu a");
        String selectItem = "Second Option";

        // Select item
        selectDropdownItem(dropdownBy,allItems,selectItem);

        // Verify selected item
        Assert.assertEquals(getText(dropdownBy),selectItem);
    }

    @Test
    public void TC_05_Editable_Dropdown() {
        driver.get("http://indrimuska.github.io/jquery-editable-select/");
        By dropdownBy = By.cssSelector("#default-place input");
        By selectedItem = By.cssSelector("#default-place li.es-visible");
        By allItems = By.cssSelector("#default-place li");
        String selectItem = "Audi";

        //
        inputText(dropdownBy,"a");

        // Select item
        selectDropdownItem(dropdownBy,allItems,selectItem);

        // Verify selected item
        Assert.assertEquals(getText(selectedItem),selectItem);
    }

    @Test
    public void TC_05_Multiple_Select() {
        driver.get("http://multiple-select.wenzhixin.net.cn/templates/template.html?v=189&url=basic.html");
        By dropdownBy = By.xpath("(//button[@class='ms-choice'])[1]");
        By selectedItem = By.xpath("(//button[@class='ms-choice']//span)[1]");
        String[] firstSelectedItems = {"January","February","March", "May"};
        String[] secondSelectedItems = {"January","February","March"};
        By allItems = By.xpath("(//button[@class='ms-choice'])[1]/following-sibling::div//span");

        // Select 4 items
        selectDropdownItems(dropdownBy,allItems,firstSelectedItems);
        Assert.assertTrue(verifyMultipleSelectedItems(selectedItem,firstSelectedItems));

        // Select 3 items
        driver.navigate().refresh();
        selectDropdownItems(dropdownBy,allItems,secondSelectedItems);
        Assert.assertTrue(verifyMultipleSelectedItems(selectedItem,secondSelectedItems));
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
        return (String) js.executeScript("return document.querySelector('#games_hidden option').text");
    }

    public String getText(By by){
        return findElement(by).getText();
    }

    public void selectDropdownItem(By dropdown, By allItems, String selectItem){
        clickElement(dropdown);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(allItems));

        for (WebElement item:findElements(allItems)) {
            if (item.getText().equals(selectItem)){
                if(item.isDisplayed()) {
                    item.click();
                    break;
                }
            }
        }
    }

    public void selectDropdownItems(By dropdown, By allItems, String[] selectItems){
        clickElement(dropdown);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(allItems));

        for (String selectItem:selectItems) {
            for (WebElement item:findElements(allItems)) {
                if(selectItem.equals(item.getText())){
                    item.click();
                    break;
                }
            }
        }
    }

    public boolean verifyMultipleSelectedItems(By dropdown,String[] selectedItems){
        String[] itemDisplayed = getText(dropdown).split(", ");
        if(selectedItems.length<=3 && selectedItems.length>0){
            return Arrays.equals(itemDisplayed,selectedItems);
        } else if(selectedItems.length>3 && selectedItems.length<12){
            return getText(dropdown).equals(selectedItems.length+" of 12 selected");
        } else if(selectedItems.length>=12){
            return getText(dropdown).equals("All selected");
        } else {
            System.out.println("There is not selected item");
            return false;
        }
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
