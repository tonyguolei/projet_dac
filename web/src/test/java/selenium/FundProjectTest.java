package selenium;

import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FundProjectTest extends TestCase{
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
    public void testFundProject() throws Exception {
        driver.get(baseUrl + "/web/index.jsp");
        driver.findElement(By.linkText("LOG IN")).click();
        driver.findElement(By.name("mail")).clear();
        driver.findElement(By.name("mail")).sendKeys("admin@dac.com");
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("dac");
        driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
        driver.findElement(By.linkText("Projects")).click();
        driver.findElement(By.linkText("Browse")).click();
        driver.findElement(By.linkText("testSelenium")).click();
        driver.findElement(By.name("value")).clear();
        driver.findElement(By.name("value")).sendKeys("50");
        driver.findElement(By.id("btnFund")).click();
        driver.switchTo().frame("stripe_checkout_app");
        driver.findElement(By.id("card_number")).clear();
        driver.findElement(By.id("card_number")).sendKeys("4242");
        driver.findElement(By.id("card_number")).sendKeys("4242");
        driver.findElement(By.id("card_number")).sendKeys("4242");
        driver.findElement(By.id("card_number")).sendKeys("4242");
        driver.findElement(By.id("cc-exp")).clear();
        driver.findElement(By.id("cc-exp")).sendKeys("12");
        driver.findElement(By.id("cc-exp")).sendKeys("17");
        driver.findElement(By.id("cc-csc")).clear();
        driver.findElement(By.id("cc-csc")).sendKeys("123");
        driver.findElement(By.id("submitButton")).click();
        driver.switchTo().defaultContent();
        assertEquals("Project funded!", driver.findElement(By.cssSelector("div.alert.alert-success")).getText());
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
