package ru.vspochernin.wikitests.mobile.tests;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.List;

// Базовый класс для тестов мобильного приложения.
public abstract class WikiMobileBaseTest {

    protected AndroidDriver driver;

    // Кнопка "Пропустить" на онбординге.
    private final By skipButton = AppiumBy.id("org.wikipedia:id/fragment_onboarding_skip_button");

    // Логотип вики на главной странице.
    private final By mainPageLogo = AppiumBy.id("org.wikipedia:id/main_toolbar_wordmark");

    @BeforeClass
    public void setUpMobileDriver() throws URISyntaxException, MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName("Android Emulator")
                .setAppPackage("org.wikipedia")
                .setAppActivity("org.wikipedia.main.MainActivity");

        driver = new AndroidDriver(new URI("http://127.0.0.1:4723/").toURL(), options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
    }

    // Пропустить онбординг, если он есть (появляется при первом старте приложения).
    protected void dismissOnboardingIfPresent() {

        List<WebElement> buttons = driver.findElements(skipButton);
        if (!buttons.isEmpty() && buttons.getFirst().isDisplayed()) {
            buttons.getFirst().click();
        }
    }


    // Вернуться на главную страницу (жмем назад, пока не оказались на ней).
    protected void ensureOnMainPage() {
        int maxAttempts = 5;

        for (int i = 0; i < maxAttempts; i++) {

            // Проверяем наличие логотипа википедии (что означает нахождение на главной).
            List<WebElement> logos = driver.findElements(mainPageLogo);
            if (!logos.isEmpty() && logos.getFirst().isDisplayed()) {
                return;
            }

            // Если лого все еще нет - пробуем вернуться назад.
            driver.pressKey(new KeyEvent(AndroidKey.BACK));
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDownMobileDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
