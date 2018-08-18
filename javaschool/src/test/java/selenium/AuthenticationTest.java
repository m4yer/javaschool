package selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;

public class AuthenticationTest {

    private WebDriver driver;

    @Before
    public void init() {
        System.setProperty("webdriver.chrome.driver", "D:/tools/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void authenticationSuccessTest() {
        driver.get(Url.LOGIN);

        driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElement(By.id("password")).sendKeys("admin");

        driver.findElement(By.id("btn-auth-submit")).click();
        assertEquals(driver.getCurrentUrl(), Url.TRIPS);

    }

    @Test
    public void authenticationFailTest() {
        driver.get(Url.LOGIN);

        driver.findElement(By.id("username")).sendKeys("dawldj");
        driver.findElement(By.id("password")).sendKeys("dawdaga2");

        driver.findElement(By.id("btn-auth-submit")).click();
        assertEquals(driver.getCurrentUrl(), Url.LOGIN_FAILED);

    }

    @After
    public void destroy() {
        driver.close();
    }

}
