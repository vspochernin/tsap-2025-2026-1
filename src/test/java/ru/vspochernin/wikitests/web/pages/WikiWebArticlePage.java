package ru.vspochernin.wikitests.web.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

// Страница статьи.
public class WikiWebArticlePage {

    private static final String SELENIUM_RU_ARTICLE = "https://ru.wikipedia.org/wiki/Selenium";

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Заголовок статьи.
    private final By title = By.id("firstHeading");
    // Блок языков.
    private final By languagesBlock = By.id("p-lang");
    // Выбор английского языка.
    private final By englishLangLink = By.cssSelector("a[lang='en']");

    public WikiWebArticlePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    // Получить заголовок статьи.
    public String getTitle() {
        return driver.findElement(title).getText();
    }

    // Получить язык страницы (из html).
    public String getHtmlLang() {
        return driver.findElement(By.tagName("html")).getAttribute("lang");
    }

    // Переключение на английский.
    public void switchToEnglish() {
        wait.until(ExpectedConditions.presenceOfElementLocated(languagesBlock));
        WebElement enLink = driver.findElement(englishLangLink);
        wait.until(ExpectedConditions.elementToBeClickable(enLink));
        enLink.click();
    }

    public void openSeleniumArticle() {
        driver.get(SELENIUM_RU_ARTICLE);
    }
}
