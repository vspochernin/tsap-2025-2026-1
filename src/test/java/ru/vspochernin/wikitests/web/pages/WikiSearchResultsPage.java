package ru.vspochernin.wikitests.web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

// Страница с результатами поиска.
public class WikiSearchResultsPage {

    private final WebDriver driver;

    // Результаты поиска (список).
    private final By resultItems = By.cssSelector("ul.mw-search-results > li");

    public WikiSearchResultsPage(WebDriver driver) {
        this.driver = driver;
    }

    // Получить количество результатов поиска.
    public int getNumberOfResults() {
        return driver.findElements(resultItems).size();
    }
}