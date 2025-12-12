package ru.vspochernin.wikitests.mobile.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

// Главный экран.
public class WikiMobileMainPage {

    private final AndroidDriver driver;

    // Строка поиска.
    private final By searchContainer = AppiumBy.id("org.wikipedia:id/search_container");

    public WikiMobileMainPage(AndroidDriver driver) {
        this.driver = driver;
    }

    // Проверить, что строка поиска на главном экране видна.
    public boolean isSearchContainerDisplayed() {
        return driver.findElement(searchContainer).isDisplayed();
    }

    // Открыть строку поиска (тапнуть по ней).
    public WikiMobileSearchPage openSearch() {
        driver.findElement(searchContainer).click();
        return new WikiMobileSearchPage(driver);
    }
}
