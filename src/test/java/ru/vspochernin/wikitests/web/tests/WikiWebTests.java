package ru.vspochernin.wikitests.web.tests;

import ru.vspochernin.wikitests.core.WebBaseTest;
import ru.vspochernin.wikitests.web.pages.WikiArticlePage;
import ru.vspochernin.wikitests.web.pages.WikiSearchResultsPage;
import ru.vspochernin.wikitests.web.pages.WikiMainPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class WikiWebTests extends WebBaseTest {

    private WebDriverWait webDriverWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Тест 1: поиск статьи по точному названию (на RU википедии).
    @Test
    public void search_exactArticle_opensCorrectPage() {
        // Открываем вики.
        WikiMainPage mainPage = new WikiMainPage(driver).open();
        // Ищем статью.
        mainPage.search("Selenium");

        // Убеждаемся, что статья открылась (заголовок появился).
        webDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("firstHeading")));

        // Проверяем соответствие заголовка.
        WikiArticlePage wikiArticlePage = new WikiArticlePage(driver);
        Assert.assertEquals(wikiArticlePage.getTitle(), "Selenium");
    }

    // Тест 2: получение списка результатов по неточному запросу.
    @Test
    public void search_generalQuery_showsResultsList() {
        // Открываем вики.
        WikiMainPage mainPage = new WikiMainPage(driver).open();
        // Делаем неточный запрос.
        mainPage.search("web testing tools");

        WikiSearchResultsPage resultsPage = new WikiSearchResultsPage(driver);
        // Ждём, пока появится хотя бы один результат.
        webDriverWait().until(d -> resultsPage.getNumberOfResults() > 0);

        String currentUrl = driver.getCurrentUrl();
        // Проверяем, что мы на странице с результатами (в таком случае в урле будет "search=").
        Assert.assertTrue(currentUrl.contains("search="));
        // Проверяем, что список результатов не пустой.
        Assert.assertTrue(resultsPage.getNumberOfResults() > 0);
    }

    // Тест 3: открыть случайную статью.
    @Test
    public void randomArticle_fromMainPage_opensRandomArticle() {
        // Открываем вики.
        WikiMainPage mainPage = new WikiMainPage(driver).open();
        String mainPageUrl = driver.getCurrentUrl();
        // Открываем случайную статью.
        mainPage.openRandomArticle();

        // Ждём, пока URL изменится (ушли с главной страницы).
        webDriverWait().until(ExpectedConditions.not(ExpectedConditions.urlToBe(mainPageUrl)));

        WikiArticlePage wikiArticlePage = new WikiArticlePage(driver);
        String title = wikiArticlePage.getTitle();

        // Проверяем, что урл изменился.
        Assert.assertNotEquals(driver.getCurrentUrl(), mainPageUrl);

        // Проверяем, что у статьи есть заголовок, и он не пустой.
        Assert.assertTrue(title != null && !title.isBlank());
    }

    // Тест 4: переключить язык.
    @Test
    public void switchLanguage_fromRussianToEnglish_changesLangAndTitle() {
        // Открываем конкретную статью на русском.
        WikiArticlePage wikiArticlePage = new WikiArticlePage(driver);
        wikiArticlePage.openSeleniumArticle();
        // Убеждаемся, что статья открылась (заголовок появился).
        webDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("firstHeading")));

        String langBefore = wikiArticlePage.getHtmlLang();
        // Переключаем на английскую версию
        wikiArticlePage.switchToEnglish();
        // Ждём смены языка.
        webDriverWait().until(d -> wikiArticlePage.getHtmlLang().startsWith("en"));

        String langAfter = wikiArticlePage.getHtmlLang();
        String urlAfter = driver.getCurrentUrl();

        // Проверяем, что язык до переключения - русский.
        Assert.assertTrue(langBefore.startsWith("ru"));

        // Проверяем, что после переключения язык английский.
        Assert.assertTrue(langAfter.startsWith("en"));

        // Проверяем, что находимся на английском домене.
        Assert.assertTrue(urlAfter.contains("en.wikipedia.org"));
    }
}
