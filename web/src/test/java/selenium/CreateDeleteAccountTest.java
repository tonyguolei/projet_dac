package selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

@Ignore
public class CreateDeleteAccountTest {
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
    public void testCreateDeleteAccount() throws Exception {
        driver.get(baseUrl + "/web/");
        driver.findElement(By.linkText("SIGN UP")).click();
        driver.findElement(By.name("mail")).clear();
        driver.findElement(By.name("mail")).sendKeys("testSelenium@dac.com");
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("test");
        driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
        assertEquals("New user created! Please log in.", driver.findElement(By.cssSelector("div.alert.alert-success")).getText());
        driver.findElement(By.name("mail")).clear();
        driver.findElement(By.name("mail")).sendKeys("testSelenium@dac.com");
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("test");
        driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
        driver.findElement(By.linkText("testSelenium@dac.com")).click();
        driver.findElement(By.linkText("Settings")).click();
        driver.findElement(By.xpath("(//input[@id='pass'])[2]")).clear();
        driver.findElement(By.xpath("(//input[@id='pass'])[2]")).sendKeys("test");
        driver.findElement(By.cssSelector("input.btn.btn-danger")).click();
        assertEquals("Your account as been successfully deleted.", driver.findElement(By.cssSelector("div.alert.alert-success")).getText());
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