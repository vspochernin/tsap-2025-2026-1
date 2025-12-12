package ru.vspochernin.wikitests.mobile.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

// Экран поиска статей.
public class WikiMobileSearchPage {

    private final AndroidDriver driver;

    // Поле ввода строки поиска.
    private final By searchInput = AppiumBy.id("org.wikipedia:id/search_src_text");

    // Заголовки найденных статей в списке результатов.
    private final By searchResultTitles = AppiumBy.id("org.wikipedia:id/page_list_item_title");

    public WikiMobileSearchPage(AndroidDriver driver) {
        this.driver = driver;
    }

    // Ввести запрос в строку поиска.
    public void typeSearchQuery(String query) {
        driver.findElement(searchInput).sendKeys(query);
    }

    // Количество найденных результатов.
    public int getResultsCount() {
        List<WebElement> results = driver.findElements(searchResultTitles);
        return results.size();
    }

    // Открыть первую статью из результатов поиска.
    public void openFirstResult() {
        List<WebElement> results = driver.findElements(searchResultTitles);
        if (results.isEmpty()) {
            throw new IllegalStateException("Empty search result");
        }
        results.getFirst().click();
    }
}
