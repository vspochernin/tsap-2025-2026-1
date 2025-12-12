package ru.vspochernin.wikitests.core;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public abstract class MobileBaseTest {

    protected AndroidDriver driver;

    @BeforeClass
    public void setUpMobileDriver() throws URISyntaxException, MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();

        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setDeviceName("Android Emulator"); // Любое имя.
        options.setAppPackage("org.wikipedia");
        options.setAppActivity("org.wikipedia.main.MainActivity");
        options.setNoReset(true); // Чтобы не сбрасывать приложение на каждом запуске.

        // Адрес выводится при запуске в терминале команды appium.
        driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterClass(alwaysRun = true)
    public void tearDownMobileDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
