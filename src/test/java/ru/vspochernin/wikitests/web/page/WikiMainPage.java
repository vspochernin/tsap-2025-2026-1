package ru.vspochernin.wikitests.web.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WikiMainPage {

    private final WebDriver driver;

    private final By searchInput = By.id("searchInput");
    private final By searchButton = By.cssSelector("button[type='submit']");

    public WikiMainPage(WebDriver driver) {
        this.driver = driver;
    }

    public WikiMainPage open() {
        driver.get("https://www.wikipedia.org/");
        return this;
    }

    public void search(String query) {
        WebElement input = driver.findElement(searchInput);
        input.clear();
        input.sendKeys(query);
        driver.findElement(searchButton).click();
    }
}
