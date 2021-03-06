package selenium;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import junit.framework.TestCase;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ReportProjectTest extends TestCase{
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
    public void testReportProject() throws Exception {
        driver.get(baseUrl + "/web/");
        driver.findElement(By.linkText("LOG IN")).click();
        driver.findElement(By.name("mail")).clear();
        driver.findElement(By.name("mail")).sendKeys("testSelenium@dac.com");
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("test");
        driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
        driver.findElement(By.linkText("Projects")).click();
        driver.findElement(By.linkText("Browse")).click();
        driver.findElement(By.linkText("testSelenium")).click();
        driver.findElement(By.cssSelector("span.glyphicon.glyphicon-exclamation-sign")).click();
        driver.findElement(By.cssSelector("div.modal-footer > button.btn.btn-primary")).click();
        assertEquals("Project reported succefully!", driver.findElement(By.cssSelector("div.alert.alert-success")).getText());
        driver.findElement(By.linkText("testSelenium@dac.com")).click();
        driver.findElement(By.linkText("Logout")).click();
        driver.findElement(By.linkText("LOG IN")).click();
        driver.findElement(By.name("mail")).clear();
        driver.findElement(By.name("mail")).sendKeys("admin@dac.com");
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("dac");
        driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
        driver.findElement(By.linkText("Projects")).click();
        driver.findElement(By.linkText("Browse")).click();
        List<WebElement> rows = driver.findElement(By.cssSelector("table[class='sortable table table-hover table-striped']")).findElements(By.tagName("tr"));
        // filtering the rows of the table
        Iterator<WebElement> iterator = rows.iterator();
        WebElement row = null;
        while (iterator.hasNext()) {
            WebElement currentRow = iterator.next();
            if (!currentRow.getText().contains("testSelenium")) {
                iterator.remove();
            } else {
                row = currentRow;
            }
        }
        // checking remaining row
        assertEquals(1, rows.size());
        assertEquals("Yes", row.findElement(By.cssSelector("font")).getText());
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