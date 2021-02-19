package webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Topic_10_User_Interactions {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;
    Actions actions;
    Alert alert;
    String jsHelper = System.getProperty("user.dir") + "/dragAndDrop/drag_and_drop_helper.js";


    @BeforeClass
    public void beforeClass() {
        driver = new ChromeDriver();
        actions = new Actions(driver);
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver,10);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_Hover_To_Element() {
        driver.get("https://jqueryui.com/resources/demos/tooltip/default.html");

        // Hover to element
        actions.moveToElement(findElement(By.cssSelector("#age")));
        actions.perform();

        // Verify message is displayed
        Assert.assertEquals(getText(By.cssSelector(".ui-tooltip-content")),"We ask for your age only for statistical purposes.");
    }

    @Test
    public void TC_02_Hover_To_Element() {
        driver.get("https://www.myntra.com/");

        // Hover to element
        actions.moveToElement(findElement(By.cssSelector("a[data-group='kids']")));
        actions.perform();

        // Click Kids Home Bath
        clickElement(By.xpath("//a[text()='Home & Bath']"));

        // Verify navigate to Kids Home Bath successfully
        Assert.assertEquals(getText(By.cssSelector(".title-title")),"Kids Home Bath");
    }

    @Test
    public void TC_03_Hover_To_Element() {
        driver.get("https://hn.telio.vn/");

        // Hover to element
        actions.moveToElement(findElement(By.xpath("//main[@id='maincontent']//span[text()='Đồ ăn vặt']")));
        actions.perform();

        // Verify sub menu is displayed
        Assert.assertTrue(findElement(By.xpath("//main[@id='maincontent']//span[text()='Đồ ăn vặt']/../following-sibling::ul")).isDisplayed());
    }

    @Test
    public void TC_04_Click_And_Hold_Element() {
        driver.get("https://jqueryui.com/resources/demos/selectable/display-grid.html");

        // Click and hold
        actions.clickAndHold(findElement(By.xpath("//li[text()='1']")));
        actions.release(findElement(By.xpath("//li[text()='4']")));
        actions.perform();

        // Verify item is selected
        Assert.assertTrue(findElement(By.xpath("//li[contains(@class,'ui-selected') and text()='1']")).isDisplayed());
        Assert.assertTrue(findElement(By.xpath("//li[contains(@class,'ui-selected') and text()='2']")).isDisplayed());
        Assert.assertTrue(findElement(By.xpath("//li[contains(@class,'ui-selected') and text()='3']")).isDisplayed());
        Assert.assertTrue(findElement(By.xpath("//li[contains(@class,'ui-selected') and text()='4']")).isDisplayed());
    }

    @Test
    public void TC_05_Click_And_Select_Element() {
        driver.get("https://jqueryui.com/resources/demos/selectable/display-grid.html");

        // Click and select random item
        actions.keyUp(Keys.CONTROL);
        actions.click(findElement(By.xpath("//li[text()='1']")));
        actions.click(findElement(By.xpath("//li[text()='3']")));
        actions.click(findElement(By.xpath("//li[text()='6']")));
        actions.click(findElement(By.xpath("//li[text()='8']")));
        actions.keyDown(Keys.CONTROL);
        actions.perform();

        // Verify item is selected
        Assert.assertEquals(findElements(By.xpath("//li[contains(@class,'ui-selected')]")).size(),4);
    }

    @Test
    public void TC_06_Double_Click() {
        driver.get("https://automationfc.github.io/basic-form/index.html");

        // Double click button
        actions.doubleClick(findElement(By.xpath("//button[text()='Double click me']")));
        actions.perform();

        // Verify text is displayed
        Assert.assertEquals(getText(By.cssSelector("#demo")),"Hello Automation Guys!");
    }

    @Test
    public void TC_07_Right_Click() {
        driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");

        // Right click button
        actions.contextClick(findElement(By.xpath("//span[text()='right click me']"))).perform();

        // Verify Element is not hovered and visibled
        Assert.assertTrue(findElement(By.cssSelector("li.context-menu-icon-quit:not(.context-menu-visible.context-menu-hover)")).isDisplayed());

        // Hover Element
        actions.moveToElement(findElement(By.xpath("//span[text()='Quit']")));
        actions.perform();

        // Verify Element is hovered and visibled
        Assert.assertTrue(findElement(By.cssSelector(".context-menu-icon-quit.context-menu-hover.context-menu-visible")).isDisplayed());

        // Click Quit
        actions.click(findElement(By.xpath("//span[text()='Quit']")));
        actions.perform();

        // Accept alert
        alert = driver.switchTo().alert();
        alert.accept();

        // Verify Element is not displayed
        Assert.assertFalse(findElement(By.xpath("//span[text()='Quit']")).isDisplayed());
    }

    @Test
    public void TC_08_Drag_And_Drop() {
        driver.get("https://demos.telerik.com/kendo-ui/dragdrop/index");
        acceptCookie();

        // Drag and drop
        actions.dragAndDrop(findElement(By.cssSelector("div#draggable")),findElement(By.cssSelector("div#droptarget")));
        actions.perform();

        // Verify message
        Assert.assertEquals(getText(By.cssSelector("div#droptarget")),"You did great!");

        // Verify background color
        Assert.assertEquals(Color.fromString(findElement(By.cssSelector("div#droptarget")).getCssValue("background-color")).asHex(),"#03a9f4");
    }

    @Test
    public void TC_09_Drag_And_Drop() throws IOException {
        driver.get("https://automationfc.github.io/drag-drop-html5/");

        String sourceRectangleCss = "#column-a";
        String targetRectangleCss = "#column-b";

        Assert.assertEquals(getText(By.cssSelector("#column-a header")),"A");

        String jsHelperContent = getJSFileContent(jsHelper) + "$(\"" + sourceRectangleCss + "\").simulateDragDrop({ dropTarget: \"" + targetRectangleCss + "\"});";
        js.executeScript(jsHelperContent);

        Assert.assertEquals(getText(By.cssSelector("#column-a header")),"B");
    }

    @Test
    public void TC_10_Drag_And_Drop() throws AWTException {
        driver.get("https://automationfc.github.io/drag-drop-html5/");

        String sourceRectangleCss = "#column-a";
        String targetRectangleCss = "#column-b";

        Assert.assertEquals(getText(By.cssSelector("#column-a header")),"A");

        drag_the_and_drop_html5_by_xpath(sourceRectangleCss,targetRectangleCss);

        Assert.assertEquals(getText(By.cssSelector("#column-a header")),"B");
    }

    public WebElement findElement(By by){
        return driver.findElement(by);
    }

    public List<WebElement> findElements(By by){
        return driver.findElements(by);
    }

    public void clickElement(By by){
        findElement(by).click();
    }

    public String getText(By by){
        return findElement(by).getText();
    }

    public void acceptCookie(){
        clickElement(By.cssSelector("button#onetrust-accept-btn-handler"));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("p#onetrust-policy-text")));
    }

    public String getJSFileContent(String file) throws IOException {
        Charset cs = Charset.forName("UTF-8");
        FileInputStream stream = new FileInputStream(file);
        try {
            Reader reader = new BufferedReader(new InputStreamReader(stream, cs));
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[8192];
            int read;
            while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
                builder.append(buffer, 0, read);
            }
            return builder.toString();
        } finally {
            stream.close();
        }
    }

    public void drag_the_and_drop_html5_by_xpath(String sourceLocator, String targetLocator) throws AWTException {

        WebElement source = driver.findElement(By.cssSelector(sourceLocator));
        WebElement target = driver.findElement(By.cssSelector(targetLocator));

        // Setup robot
        Robot robot = new Robot();
        robot.setAutoDelay(500);

        // Get size of elements
        Dimension sourceSize = source.getSize();
        Dimension targetSize = target.getSize();

        // Get center distance
        int xCentreSource = sourceSize.width / 2;
        int yCentreSource = sourceSize.height / 2;
        int xCentreTarget = targetSize.width / 2;
        int yCentreTarget = targetSize.height / 2;

        Point sourceLocation = source.getLocation();
        Point targetLocation = target.getLocation();
        System.out.println(sourceLocation.toString());
        System.out.println(targetLocation.toString());

        // Make Mouse coordinate center of element
        sourceLocation.x += 20 + xCentreSource;
        sourceLocation.y += 110 + yCentreSource;
        targetLocation.x += 20 + xCentreTarget;
        targetLocation.y += 110 + yCentreTarget;

        System.out.println(sourceLocation.toString());
        System.out.println(targetLocation.toString());

        // Move mouse to drag from location
        robot.mouseMove(sourceLocation.x, sourceLocation.y);

        // Click and drag
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseMove(((sourceLocation.x - targetLocation.x) / 2) + targetLocation.x, ((sourceLocation.y - targetLocation.y) / 2) + targetLocation.y);

        // Move to final position
        robot.mouseMove(targetLocation.x, targetLocation.y);

        // Drop
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
