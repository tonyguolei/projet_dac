package selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateProjectTest {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://localhost:8080/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }

    @Test
    public void testCreateProject() throws Exception {
        String test = null;
        driver.get(baseUrl + "/web/");
        driver.findElement(By.linkText("LOG IN")).click();
        driver.findElement(By.name("mail")).sendKeys("admin@gmail.com");
        driver.findElement(By.name("password")).sendKeys("dac");
        driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
        driver.findElement(By.linkText("Projects")).click();
        driver.findElement(By.linkText("Create new")).click();
        driver.findElement(By.name("title")).clear();
        driver.findElement(By.name("title")).sendKeys("testSelenium");
        /*The textarea of bootstrap can't be selected by selenium. I think they are not compatible. So I select textarea from title and type "tap" 2 times*/
/*        driver.findElement(By.name("title")).sendKeys(Keys.TAB, Keys.TAB, "testSelenium");*/
        driver.findElement(By.name("title")).sendKeys(Keys.TAB, Keys.TAB, "testSelenium",Keys.ENTER);
        driver.findElement(By.name("tags")).clear();
        driver.findElement(By.name("tags")).sendKeys("testSelenium");
        driver.findElement(By.name("endDate")).clear();
        driver.findElement(By.name("endDate")).sendKeys("2015-02-21");
        driver.findElement(By.name("goal")).clear();
        driver.findElement(By.name("goal")).sendKeys("1000");
        driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
        assertEquals("Project created succefully!", driver.findElement(By.cssSelector("div.alert.alert-success")).getText());
        driver.findElement(By.linkText("admin@gmail.com")).click();
        driver.findElement(By.linkText("Logout")).click();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}