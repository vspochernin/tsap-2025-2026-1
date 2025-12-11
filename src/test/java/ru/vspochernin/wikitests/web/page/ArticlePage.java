package ru.vspochernin.wikitests.web.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ArticlePage {

    private final WebDriver driver;
    private final By title = By.id("firstHeading");

    public ArticlePage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTitle() {
        return driver.findElement(title).getText();
    }
}
