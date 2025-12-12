package ru.vspochernin.wikitests.mobile.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

// Экран открытой статьи.
public class WikiMobileArticlePage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    // Кнопка закрытия (в нашем случае для всплывающего окна).
    private final By closeButton = AppiumBy.id("org.wikipedia:id/closeButton");

    public WikiMobileArticlePage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // Заголовок статьи по точному тексту.
    private By titleByExactText(String expectedTitle) {
        return AppiumBy.xpath("(//android.widget.TextView[@text='" + expectedTitle + "'])[1]");
    }

    // Закрыть всплывающее окно "Introducing Wikimedia Games" через крестик, если оно есть.
    private void dismissWikimediaGamesIfPresent() {
        List<WebElement> buttons = driver.findElements(closeButton);
        if (!buttons.isEmpty() && buttons.getFirst().isDisplayed()) {
            buttons.getFirst().click();
        }
    }

    // Дождаться, пока заголовок статьи с указанным текстом станет видимым.
    public void waitForTitle(String expectedTitle) {
        dismissWikimediaGamesIfPresent(); // В момент открытия статьи в первый раз может вылезти баннер.
        wait.until(ExpectedConditions.visibilityOfElementLocated(titleByExactText(expectedTitle)));
    }

    // Получить текст заголовка.
    public String getTitle(String expectedTitle) {
        return driver.findElement(titleByExactText(expectedTitle)).getText();
    }
}
