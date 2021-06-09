package pl.irc;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FrontendTest {

    private static WebDriver driver;
    private static WebDriverWait wait;

    @LocalServerPort
    private int port;

    @BeforeAll
    public static void init() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver(new FirefoxOptions().setHeadless(true));
        wait = new WebDriverWait(driver, 30);
    }

    @Test
    public void dupa() {
        driver.get("http://localhost:" + port + "/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("newMessage")));
        driver.findElement(By.id("newMessage")).click();
        driver.findElement(By.id("newMessage")).sendKeys("Testowa wiadomosc");
        driver.findElement(By.cssSelector("button:nth-child(3)")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div:nth-child(3) > span")));

        final String messageText = driver.findElement(By.cssSelector("div:nth-child(3) > span")).getText();

        assertEquals("Testowa wiadomosc", messageText);
    }

    @AfterAll
    public static void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
