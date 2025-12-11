package ru.vspochernin.wikitests.web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

// Главная страница RU википедии.
public class WikiMainPage {

    private static final String WIKI_MAIN_PAGE_URL = "https://ru.wikipedia.org/";
    private final WebDriver driver;

    // Поле поиска.
    private final By searchInput = By.id("searchInput");
    // Кнопка поиска.
    private final By searchButton = By.id("searchButton");
    // Ссылка "Случайная статья" в левом меню навигации
    private final By randomArticleLink = By.cssSelector("#n-randompage a");

    public WikiMainPage(WebDriver driver) {
        this.driver = driver;
    }

    // Открыть RU википедию.
    public WikiMainPage open() {
        driver.get(WIKI_MAIN_PAGE_URL);
        return this;
    }

    // Поиск через строку поиска.
    public void search(String query) {
        WebElement input = driver.findElement(searchInput);
        input.clear();
        input.sendKeys(query);
        driver.findElement(searchButton).click();
    }

    // Переход на случайную статью.
    public void openRandomArticle() {
        driver.findElement(randomArticleLink).click();
    }
}
