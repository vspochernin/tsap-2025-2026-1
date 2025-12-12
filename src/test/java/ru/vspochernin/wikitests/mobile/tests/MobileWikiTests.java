package ru.vspochernin.wikitests.mobile.tests;

import io.appium.java_client.AppiumBy;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.vspochernin.wikitests.core.MobileBaseTest;

public class MobileWikiTests extends MobileBaseTest {

    // Пример мобильного теста (главное, чтобы запустился).
    @Test
    public void appOpens_mainSearchFieldIsVisible() {
        var searchContainer = driver.findElement(AppiumBy.id("org.wikipedia:id/search_container"));

        Assert.assertTrue(searchContainer.isDisplayed());
    }
}
