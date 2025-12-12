package ru.vspochernin.wikitests.web.tests;

import java.time.Duration;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

// Базовый класс для тестов веб-сайта.
public abstract class WikiWebBaseTest {

    protected WebDriver driver;

    @BeforeClass
    public void setUpWebDriver() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }

    @AfterClass(alwaysRun = true)
    public void tearDownWebDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
