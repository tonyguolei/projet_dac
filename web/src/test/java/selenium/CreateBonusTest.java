package selenium;

import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CreateBonusTest extends TestCase{
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://localhost:8080/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testCreateBonus() throws Exception {
        driver.get(baseUrl + "/web/");
        driver.findElement(By.linkText("LOG IN")).click();
        driver.findElement(By.name("mail")).clear();
        driver.findElement(By.name("mail")).sendKeys("admin@dac.com");
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("dac");
        driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
        driver.findElement(By.linkText("admin@dac.com")).click();
        driver.findElement(By.linkText("My Profile")).click();
        driver.findElement(By.linkText("testSelenium")).click();
        driver.findElement(By.linkText("Bonus")).click();
        driver.findElement(By.name("title")).clear();
        driver.findElement(By.name("title")).sendKeys("testSeleniumBonus");
        driver.findElement(By.cssSelector("div.form-group.col-sm-6 > input[name=\"value\"]")).clear();
        driver.findElement(By.cssSelector("div.form-group.col-sm-6 > input[name=\"value\"]")).sendKeys("50");
        driver.findElement(By.cssSelector("div.form-group.col-sm-6 > input[name=\"value\"]")).sendKeys(Keys.TAB, Keys.TAB, "testSeleniumBonusDescription", Keys.ENTER);
        driver.findElement(By.xpath("(//button[@type='submit'])[3]")).click();
        assertEquals("Create successfully a bonus!", driver.findElement(By.cssSelector("div.alert.alert-success")).getText());
        driver.findElement(By.linkText("admin@dac.com")).click();
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