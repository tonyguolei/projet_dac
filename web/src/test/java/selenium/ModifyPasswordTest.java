package selenium;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ModifyPasswordTest {
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
    public void testModifyPassword() throws Exception {
        driver.get(baseUrl + "/web/");
        driver.findElement(By.linkText("LOG IN")).click();
        driver.findElement(By.name("mail")).clear();
        driver.findElement(By.name("mail")).sendKeys("dac@gmail.com");
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("dac");
        driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
        driver.findElement(By.linkText("dac@gmail.com")).click();
        driver.findElement(By.linkText("Settings")).click();
        driver.findElement(By.id("pass")).clear();
        driver.findElement(By.id("pass")).sendKeys("dac");
        driver.findElement(By.id("confpass")).clear();
        driver.findElement(By.id("confpass")).sendKeys("dac");
        driver.findElement(By.cssSelector("input.btn.btn-default")).click();
        assertEquals("Password succesfully modified.", driver.findElement(By.cssSelector("div.alert.alert-success")).getText());
        driver.findElement(By.linkText("dac@gmail.com")).click();
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