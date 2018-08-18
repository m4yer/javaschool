package selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Keys;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SigningUpTest {

    private WebDriver driver;

    @Before
    public void init() {
        System.setProperty("webdriver.chrome.driver", "D:/tools/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void registrationTest1() {
        fillUpFields();
        assertEquals(Url.INDEX, driver.getCurrentUrl());
    }

    @Test
    public void registrationTest2() {
        fillUpFields();
        assertEquals(Url.REGISTER, driver.getCurrentUrl());
    }

    @After
    public void destroy() {
        driver.close();
    }

    private void fillUpFields() {
        driver.get(Url.REGISTER);

        driver.findElement(By.id("username")).sendKeys("parkito");
        driver.findElement(By.id("email")).sendKeys("someTestEmail@gmail.com");
        driver.findElement(By.id("password")).sendKeys("123123123");
        driver.findElement(By.id("rePassword")).sendKeys("123123123");
        driver.findElement(By.id("firstname")).sendKeys("123123123");
        driver.findElement(By.id("lastname")).sendKeys("123123123");
        driver.findElement(By.className("day")).sendKeys("24" + Keys.RETURN);
        driver.findElement(By.className("month")).sendKeys("D" + Keys.RETURN);
        driver.findElement(By.className("year")).sendKeys("1997" + Keys.RETURN);

        driver.findElement(By.id("btn-reg-submit")).click();
        System.out.println(driver.getCurrentUrl());
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

}
