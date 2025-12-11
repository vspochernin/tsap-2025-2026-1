package ru.vspochernin.wikitests.web.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.vspochernin.wikitests.core.WebBaseTest;
import ru.vspochernin.wikitests.web.pages.ArticlePage;
import ru.vspochernin.wikitests.web.pages.WikiMainPage;

public class WikiSearchTests extends WebBaseTest {

    @Test
    public void search_exactArticle_opensCorrectPage() {
        WikiMainPage mainPage = new WikiMainPage(driver).open();
        mainPage.search("Selenium");

        ArticlePage articlePage = new ArticlePage(driver);
        Assert.assertEquals(articlePage.getTitle(), "Selenium");
    }
}
