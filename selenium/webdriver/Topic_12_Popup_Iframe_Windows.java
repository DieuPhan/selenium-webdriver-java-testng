package webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Topic_12_Popup_Iframe_Windows {
    WebDriver driver;
    WebDriverWait wait;


    @BeforeClass
    public void beforeClass() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver,10);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_Fixed_Popup() {
        driver.get("https://www.zingpoll.com/");

        // Open popup
        clickElement(By.cssSelector("#Loginform"));

        // Verify popup is displayed
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#loginForm"))).isDisplayed());

        // Close popup
        clickElement(By.xpath("//span[text()='Close']/.."));

        // Verify popup is not displayed
        Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#loginForm"))));
    }

    @Test
    public void TC_02_Fixed_Popup() {
        driver.get("https://bni.vn/");

        // Verify popup is displayed
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.sgpb-form-wrapper")))!=null);

        // Close popup
        clickElement(By.cssSelector("[alt='Close']"));
    }

    @Test
    public void TC_03_Random_Popup() {
        driver.get("https://blog.testproject.io/");

        // Close random popup
        closeRandomPopup(By.cssSelector("div.mailch-wrap"),By.cssSelector("#close-mailch"));

        // Search
        inputText(By.cssSelector("#secondary input.search-field"),"selenium");
        clickElement(By.cssSelector("#secondary .glass"));

        // Verify title
        Assert.assertTrue(checkTitle(By.cssSelector(".post-title a"),"Selenium"));
    }

    @Test
    public void TC_04_Random_Popup() {
        driver.get("https://shopee.vn/");

        // Close random popup
        closeRandomPopup(By.cssSelector("[alt='home_popup_banner']"),By.cssSelector(".shopee-popup__close-btn"));

        // Click Element
        clickElement(By.xpath("//div[text()='Bảo Vệ Gia Đình']"));

        // Check title
        Assert.assertTrue(wait.until(ExpectedConditions.titleIs("CÙNG SHOPEE BẢO VỆ GIA ĐÌNH BẠN")));
    }

    @Test
    public void TC_05_Iframe() {
        driver.get("https://kyna.vn/");

        // Verify facebook iframe is displayed
        Assert.assertTrue(findElement(By.cssSelector("iframe[src^='//www.facebook.com']")).isDisplayed());

        // Switch to facebook iframe
        driver.switchTo().frame(findElement(By.cssSelector("iframe[src^='//www.facebook.com']")));

        // Verify like
        Assert.assertTrue(checkLikeNumber(By.xpath("//a[@title='Kyna.vn']/../following-sibling::div"),160));

        driver.switchTo().defaultContent();

        // Verify Web chat iframe is displayed
        Assert.assertTrue(findElement(By.cssSelector("#cs_chat_iframe")).isDisplayed());

        // Switch to facebook iframe
        driver.switchTo().frame("cs_chat_iframe");
        clickElement(By.cssSelector(".border_overlay"));

        // Verify form is displayed
        Assert.assertTrue(findElement(By.cssSelector(".form_container")).isDisplayed());

        driver.switchTo().defaultContent();

        // Search Excel text
        inputText(By.cssSelector("#live-search-bar"),"Excel");
        clickElement(By.cssSelector(".search-button"));

        // Verify title
        checkTitle(By.cssSelector(".content h4"),"Excel");
    }

    @Test
    public void TC_06_Windows_Tab() {
        driver.get("https://automationfc.github.io/basic-form/index.html");
        String parentWindow = driver.getWindowHandle();

        // Open Google window an switch
        clickElement(By.xpath("//a[text()='GOOGLE']"));
        switchToWindowByID(parentWindow);

        // Check title of new window
        Assert.assertEquals(driver.getTitle(),"Google");

        // Switch to parent window
        driver.switchTo().window(parentWindow);

        // Open Facebook window an switch
        clickElement(By.xpath("//a[text()='FACEBOOK']"));
        switchToWindowByTitle("Facebook - Đăng nhập hoặc đăng ký");

        // Check title of new window
        Assert.assertEquals(driver.getTitle(),"Facebook - Log In or Sign Up");

        // Switch to parent window
        driver.switchTo().window(parentWindow);

        // Open Tiki window an switch
        clickElement(By.xpath("//a[text()='TIKI']"));
        switchToWindowByTitle("Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");

        // Check title of new window
        Assert.assertEquals(driver.getTitle(),"Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");

        // Close all windows
        closeAllTabs(parentWindow);
        Assert.assertEquals(driver.getTitle(),"SELENIUM WEBDRIVER FORM DEMO");
    }

    @Test
    public void TC_07_Windows_Tab() {
        driver.get("https://kyna.vn/");
        String parentWindow = driver.getWindowHandle();

        // Open Facebook window an switch
        clickElement(By.cssSelector(".hotline img[alt='facebook']"));
        switchToWindowByID(parentWindow);

        // Check title of new window
        Assert.assertEquals(driver.getTitle(),"Kyna.vn - Home | Facebook");

        // Switch to parent window
        driver.switchTo().window(parentWindow);

        // Open Youtube window an switch
        clickElement(By.cssSelector(".hotline img[alt='youtube']"));
        switchToWindowByTitle("Kyna.vn - YouTube");

        // Check title of new window
        Assert.assertEquals(driver.getTitle(),"Kyna.vn - YouTube");

        // Switch to parent window
        driver.switchTo().window(parentWindow);

        // Open Zalo window an switch
        clickElement(By.cssSelector(".hotline img[alt='zalo']"));
        switchToWindowByTitle("Zalo Official Account");

        // Check title of new window
        Assert.assertEquals(driver.getTitle(),"Zalo Official Account");

        // Close all windows
        closeAllTabs(parentWindow);
        Assert.assertEquals(driver.getTitle(),"Kyna.vn - Học online cùng chuyên gia");
    }

    @Test
    public void TC_08_Windows_Tab() {
        driver.get("http://live.demoguru99.com/index.php/");
        String parentWindow = driver.getWindowHandle();

        // Click Mobile
        clickElement(By.xpath("//a[text()='Mobile']"));

        // Click Add to compare button
        clickElement(By.xpath("//a[text()='Sony Xperia']/../..//a[text()='Add to Compare']"));

        // Verify success message
        Assert.assertEquals(getText(By.cssSelector(".success-msg span")),"The product Sony Xperia has been added to comparison list.");

        // Click Add to compare button
        clickElement(By.xpath("//a[text()='Samsung Galaxy']/../..//a[text()='Add to Compare']"));

        // Verify success message
        Assert.assertEquals(getText(By.cssSelector(".success-msg span")),"The product Samsung Galaxy has been added to comparison list.");

        // Click compare
        clickElement(By.xpath("//span[text()='Compare']"));

        switchToWindowByID(parentWindow);

        // Check title of new window
        Assert.assertEquals(driver.getTitle(),"Products Comparison List - Magento Commerce");

        // Close tab and Switch to parent window
        driver.close();
        driver.switchTo().window(parentWindow);

        // Clear all and accept alert
        clickElement(By.xpath("//a[text()='Clear All']"));
        Alert alert = driver.switchTo().alert();
        alert.accept();

        // Verify success message
        Assert.assertEquals(getText(By.cssSelector(".success-msg span")),"The comparison list was cleared.");
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

    public void closeRandomPopup(By popupBy, By closeButton){
        if(wait.until(ExpectedConditions.visibilityOfElementLocated(popupBy))!=null){
            clickElement(closeButton);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(popupBy));
        }
    }

    public boolean checkTitle(By titleBy, String text){
        for (WebElement element: driver.findElements(titleBy)) {
            if(!element.getText().contains(text)){
                return false;
            }
        }
        return true;
    }

    public boolean checkLikeNumber(By likeNumberBy, int like){
        int likeNumber = Integer.parseInt(findElement(likeNumberBy).getText().split("K")[0]);
        return likeNumber>like;
    }

    public void switchToWindowByID(String parentWindow){
        for(String window:driver.getWindowHandles()){
            if(!window.equals(parentWindow)){
                driver.switchTo().window(window);
                break;
            }
        }
    }

    public void switchToWindowByTitle(String title){
        for(String window:driver.getWindowHandles()){
            driver.switchTo().window(window);
            if(driver.getTitle().equals(title)){
                break;
            }
        }
    }

    public void closeAllTabs(String parentWindow){
        for (String window:driver.getWindowHandles()){
            if(!window.equals(parentWindow)){
                driver.switchTo().window(window).close();
            }
        }
        driver.switchTo().window(parentWindow);
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
