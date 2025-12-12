package ru.vspochernin.wikitests.mobile.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.vspochernin.wikitests.mobile.pages.WikiMobileArticlePage;
import ru.vspochernin.wikitests.mobile.pages.WikiMobileMainPage;
import ru.vspochernin.wikitests.mobile.pages.WikiMobileSearchPage;

public class WikiMobileTests extends WikiMobileBaseTest {

    @BeforeMethod
    public void prepareTest() {
        dismissOnboardingIfPresent();
        ensureOnMainPage();
    }

    // Дата провайдер для теста 2.
    @DataProvider(name = "searchQueries")
    public Object[][] searchQueries() {
        return new Object[][]{
                {"Appium"},
                {"Selenium"},
                {"Android"}
        };
    }

    // Дата провайдер для теста 3.
    @DataProvider(name = "searchExactQueries")
    public Object[][] searchExactQueries() {
        return new Object[][]{
                {"Nvidia"},
                {"Apple"},
                {"Headphones"}
        };
    }

    // Тест 1: проверка видимости строки поиска на главном экране.
    @Test
    public void appOpens_mainSearchFieldIsVisible() {
        // Открываем главный экран.
        WikiMobileMainPage mainPage = new WikiMobileMainPage(driver);
        // Убеждаемся, что на нем есть строка поиска.
        Assert.assertTrue(mainPage.isSearchContainerDisplayed());
    }

    // Тест 2: наличие результатов при поиске.
    @Test(dataProvider = "searchQueries")
    public void search_withKeyword_showsResultsList(String query) {
        // Открываем главный экран.
        WikiMobileMainPage mainPage = new WikiMobileMainPage(driver);
        // Открываем строку поиска.
        WikiMobileSearchPage searchPage = mainPage.openSearch();
        // Ищем что-то.
        searchPage.typeSearchQuery(query);

        // Проверяем, что есть результаты.
        Assert.assertTrue(searchPage.getResultsCount() > 0);
    }

    // Тест 3: корректный заголовок у первой открытой статьи при поиске.
    @Test(dataProvider = "searchExactQueries")
    public void search_openFirstResult_articleTitleIsAppium(String query) {
        // Открываем главный экран.
        WikiMobileMainPage mainPage = new WikiMobileMainPage(driver);
        // Открываем строку поиска.
        WikiMobileSearchPage searchPage = mainPage.openSearch();
        // Ищем что-то.
        searchPage.typeSearchQuery(query);
        // Открываем первый результат.
        searchPage.openFirstResult();

        WikiMobileArticlePage articlePage = new WikiMobileArticlePage(driver);
        // Ожидаем появления заголовка.
        articlePage.waitForTitle(query);
        String title = articlePage.getTitle(query);

        // Проверяем, что заголовок статьи совпадает с запросом.
        Assert.assertEquals(title, query);
    }
}
