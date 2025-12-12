package ru.vspochernin.wikitests.web.tests;

import org.testng.annotations.DataProvider;
import ru.vspochernin.wikitests.web.pages.WikiWebArticlePage;
import ru.vspochernin.wikitests.web.pages.WikiWebSearchResultsPage;
import ru.vspochernin.wikitests.web.pages.WikiWebMainPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class WikiWebTests extends WikiWebBaseTest {

    public WebDriverWait webDriverWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Дата провайдер для теста 1.
    @DataProvider(name = "exactSearchQueries")
    public Object[][] exactSearchQueries() {
        return new Object[][]{
                {"Selenium"},
                {"Веб"},
                {"Тест"}
        };
    }

    // Дата провайдер для теста 2.
    @DataProvider(name = "generalSearchQueries")
    private Object[][] generalSearchQueries() {
        return new Object[][]{
                {"selenium webdriver"},
                {"web testing tools"},
                {"тестирование сайтов"}
        };
    }

    // Тест 1: поиск статьи по точному названию (на RU википедии).
    @Test(dataProvider = "exactSearchQueries")
    public void search_exactArticle_opensCorrectPage(String query) {
        // Открываем вики.
        WikiWebMainPage mainPage = new WikiWebMainPage(driver).open();
        // Ищем статью.
        mainPage.search(query);

        // Убеждаемся, что статья открылась (заголовок появился).
        webDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("firstHeading")));

        // Проверяем соответствие заголовка.
        WikiWebArticlePage wikiWebArticlePage = new WikiWebArticlePage(driver);
        Assert.assertEquals(wikiWebArticlePage.getTitle(), query);
    }

    // Тест 2: получение списка результатов по неточному запросу.
    @Test(dataProvider = "generalSearchQueries")
    public void search_generalQuery_showsResultsList(String query) {
        // Открываем вики.
        WikiWebMainPage mainPage = new WikiWebMainPage(driver).open();
        // Делаем неточный запрос.
        mainPage.search(query);

        WikiWebSearchResultsPage resultsPage = new WikiWebSearchResultsPage(driver);
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
        WikiWebMainPage mainPage = new WikiWebMainPage(driver).open();
        String mainPageUrl = driver.getCurrentUrl();
        // Открываем случайную статью.
        mainPage.openRandomArticle();

        // Ждём, пока URL изменится (ушли с главной страницы).
        webDriverWait().until(ExpectedConditions.not(ExpectedConditions.urlToBe(mainPageUrl)));

        WikiWebArticlePage wikiWebArticlePage = new WikiWebArticlePage(driver);
        String title = wikiWebArticlePage.getTitle();

        // Проверяем, что урл изменился.
        Assert.assertNotEquals(driver.getCurrentUrl(), mainPageUrl);

        // Проверяем, что у статьи есть заголовок, и он не пустой.
        Assert.assertTrue(title != null && !title.isBlank());
    }

    // Тест 4: переключить язык.
    @Test
    public void switchLanguage_fromRussianToEnglish_changesLangAndTitle() {
        // Открываем конкретную статью на русском.
        WikiWebArticlePage wikiWebArticlePage = new WikiWebArticlePage(driver);
        wikiWebArticlePage.openSeleniumArticle();
        // Убеждаемся, что статья открылась (заголовок появился).
        webDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("firstHeading")));

        String langBefore = wikiWebArticlePage.getHtmlLang();
        // Переключаем на английскую версию
        wikiWebArticlePage.switchToEnglish();
        // Ждём смены языка.
        webDriverWait().until(d -> wikiWebArticlePage.getHtmlLang().startsWith("en"));

        String langAfter = wikiWebArticlePage.getHtmlLang();
        String urlAfter = driver.getCurrentUrl();

        // Проверяем, что язык до переключения - русский.
        Assert.assertTrue(langBefore.startsWith("ru"));

        // Проверяем, что после переключения язык английский.
        Assert.assertTrue(langAfter.startsWith("en"));

        // Проверяем, что находимся на английском домене.
        Assert.assertTrue(urlAfter.contains("en.wikipedia.org"));
    }
}
